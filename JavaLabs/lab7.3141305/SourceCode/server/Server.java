package com.lab.server;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.lab.common.LoggerSetup;
import com.lab.common.adapters.LocalDateDeserializer;
import com.lab.common.adapters.LocalDateSerializer;
import com.lab.common.exchange.Request;
import com.lab.common.exchange.Response;
import com.lab.server.executor.Executor;

import java.io.File;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetSocketAddress;
import java.nio.BufferOverflowException;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;
import java.time.LocalDate;
import java.util.concurrent.*;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Server class
 */
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

    public Server(int port, String dbUser, String dbPassword) throws IOException {
        gson =
                new GsonBuilder()
                        .registerTypeAdapter(LocalDate.class, new LocalDateSerializer())
                        .registerTypeAdapter(LocalDate.class, new LocalDateDeserializer())
                        .create();

        readExecutorService = ForkJoinPool.commonPool();
        handleExecutorService = Executors.newFixedThreadPool(2);
        sendExecutorService = Executors.newCachedThreadPool();

        buffer = ByteBuffer.allocate(8192);
        running = true;
        channel = DatagramChannel.open();
        channel.bind(new InetSocketAddress(port));
        this.port = port;

        this.executor = new Executor(dbUser, dbPassword);
        logger = Logger.getLogger(Server.class.getName());
        LoggerSetup.setupLogger(logger, "server" + File.separator + LocalDate.now() + "_log.log");
    }

    public Executor getExecutor() {
        return executor;
    }

    public boolean isRunning() {
        return running;
    }

    /**
     * Closes connection
     */
    public void close() {
        try {
            channel.socket().disconnect();
            channel.close();
        } catch (IOException e) {
            logger.log(Level.SEVERE, "Ошибка закрытия соединения", e);
        }
    }

    /**
     * One request
     */
    public void process() {
        buffer.clear();
        DatagramPacket receivePacket = new DatagramPacket(buffer.array(), buffer.remaining());
        logger.fine("Ожидание команды от клиента");

        Future<Request> futureRequest =
                readExecutorService.submit(
                        () -> {
                            channel.socket().receive(receivePacket);
                            String data = new String(receivePacket.getData(), 0, receivePacket.getLength());
                            return gson.fromJson(data, Request.class);
                        });

        while (!futureRequest.isDone()) {
            // Wait till reading is not done.
        }

        Request request;
        try {
            request = futureRequest.get();
        } catch (InterruptedException | ExecutionException e) {
            logger.severe("Фатальная ошибка при чтении данных клиента. Закрытие соединения");
            close();
            return;
        }

        logger.info(
                "Данные клиента: "
                        + System.lineSeparator()
                        + "Имя комманды: "
                        + request.getCommandName()
                        + System.lineSeparator()
                        + "Параметр: "
                        + request.getCommandParameter()
                        + System.lineSeparator()
                        + "Музыкальная группа: "
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
            logger.severe("Фатальная ошибка при обработке данных клиента. Закрытие соединения");
            close();
            return;
        }

        String responseData = gson.toJson(response);

        sendExecutorService.execute(
                () -> {
                    try {
                        buffer.put(responseData.getBytes());
                    } catch (BufferOverflowException e) {
                        buffer.put(
                                gson.toJson(new Response(response.isCorrect(), "Ответ от сервера слишком большой."))
                                        .getBytes());
                    }
                    buffer.flip();
                    DatagramPacket sendPacket =
                            new DatagramPacket(
                                    buffer.array(),
                                    buffer.remaining(),
                                    receivePacket.getAddress(),
                                    receivePacket.getPort());

                    try {
                        channel.socket().send(sendPacket);
                    } catch (IOException e) {
                        logger.severe("Ошибка. Закрытие соединения");
                        close();
                    }
                });
    }

    public boolean isConnected() {
        return !channel.socket().isClosed();
    }
}
