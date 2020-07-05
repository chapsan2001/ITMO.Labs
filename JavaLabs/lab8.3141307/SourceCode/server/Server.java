package com.lab.server;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.lab.common.adapters.LocalDateDeserializer;
import com.lab.common.adapters.LocalDateSerializer;
import com.lab.common.exchange.Request;
import com.lab.common.exchange.Response;
import com.lab.server.executor.Executor;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.nio.BufferOverflowException;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;
import java.time.LocalDate;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicReference;
import java.util.logging.Level;
import java.util.logging.Logger;

/** Server class */
public class Server {
  private final int port;
  private final ExecutorService readExecutorService;
  private final ExecutorService handleExecutorService;
  private final ExecutorService sendExecutorService;
  private Gson gson;
  private ByteBuffer buffer;
  private boolean running;
  private Executor executor;
  private DatagramChannel channel;
  private Logger logger;

  public Server(int port, String dbUrl, String dbUser, String dbPassword) throws IOException {
    gson =
        new GsonBuilder()
            .registerTypeAdapter(LocalDate.class, new LocalDateSerializer())
            .registerTypeAdapter(LocalDate.class, new LocalDateDeserializer())
            .create();

    readExecutorService = ForkJoinPool.commonPool();
    handleExecutorService = Executors.newFixedThreadPool(2);
    sendExecutorService = Executors.newCachedThreadPool();

    buffer = ByteBuffer.allocate(64 * 1024);
    running = true;
    channel = DatagramChannel.open();
    channel.bind(new InetSocketAddress(port));
    this.port = port;

    this.executor = new Executor(dbUrl, dbUser, dbPassword);
    logger = Logger.getLogger(Server.class.getName());
    logger.setLevel(Level.OFF);
  }

  public Executor getExecutor() {
    return executor;
  }

  public boolean isRunning() {
    return running;
  }

  /** Closes connection */
  public void close() {
    try {
      channel.socket().disconnect();
      channel.close();
    } catch (IOException e) {
      logger.log(Level.SEVERE, "Error closing connection", e);
    }
  }

  /** One request */
  public void process() {
    buffer.clear();
    logger.fine("Waiting command from the client...");
    AtomicReference<SocketAddress> client = new AtomicReference<>();

    Future<Request> futureRequest =
        readExecutorService.submit(
            () -> {
              ByteBuffer byteBuffer = ByteBuffer.allocate(64 * 1024);
              logger.fine("Waiting command from the client...");

              client.set(channel.receive(byteBuffer));
              byteBuffer.flip();
              String data = new String(byteBuffer.array()).trim();
              Request request = gson.fromJson(data, Request.class);

              logger.info(
                  "Client data: "
                      + System.lineSeparator()
                      + "Command name: "
                      + request.getCommandName()
                      + System.lineSeparator()
                      + "Parameter: "
                      + request.getCommandParameter()
                      + System.lineSeparator()
                      + "Music band: "
                      + request.getMusicBand());

              return request;
            });

    while (!futureRequest.isDone()) {
      // Wait till reading is not done.
    }

    Request request;

    try {
      request = futureRequest.get();
    } catch (InterruptedException | ExecutionException e) {
      logger.severe("Fatal error during reading client data. Closing connection");
      close();
      return;
    }

    logger.info(
        "Client data: "
            + System.lineSeparator()
            + "Command name: "
            + request.getCommandName()
            + System.lineSeparator()
            + "Parameter: "
            + request.getCommandParameter()
            + System.lineSeparator()
            + "Music band: "
            + request.getMusicBand());

    executor.getEditor().setMusicBand(request.getMusicBand());

    buffer.clear();

    Future<Response> futureResponse =
        handleExecutorService.submit(() -> executor.executeCommand(request));

    while (!futureResponse.isDone()) {
      // Wait till handling is not done.
    }

    Response response;

    try {
      response = futureResponse.get();
    } catch (InterruptedException | ExecutionException e) {
      logger.severe("Fatal error during work of the client. Closing connection");
      close();
      return;
    }

    String responseData = gson.toJson(response);

    sendExecutorService.execute(
        () -> {
          buffer = ByteBuffer.allocate(64 * 1024);

          try {
            buffer.put(responseData.getBytes());
          } catch (BufferOverflowException e) {
            buffer.put(
                gson.toJson(new Response(response.isCorrect(), "Answer from server is too big."))
                    .getBytes());
          }

          try {
            buffer.flip();
            channel.send(buffer, client.get());
          } catch (IOException e) {
            logger.severe("Error. Closing connection");
            close();
          }
        });
  }

  public boolean isConnected() {
    return !channel.socket().isClosed();
  }
}
