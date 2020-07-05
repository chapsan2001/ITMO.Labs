package com.lab.client;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.lab.common.CommandNames;
import com.lab.common.adapters.LocalDateDeserializer;
import com.lab.common.adapters.LocalDateSerializer;
import com.lab.common.exchange.Request;
import com.lab.common.exchange.Response;
import com.lab.common.io.Input;
import com.lab.common.io.Output;
import com.lab.common.user.User;

import java.io.IOException;
import java.net.*;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;
import java.time.LocalDate;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

/** Client class */
public class Client {
  private final InetSocketAddress inetSocketAddress;
  private final User user;
  private Gson gson;
  private Input in;
  private Output out;
  private Logger logger;
  private ByteBuffer buffer;
  private DatagramSocket sendSocket;

  public Client(String host, int port) throws UnknownHostException {
    user = new User();
    gson =
        new GsonBuilder()
            .registerTypeAdapter(LocalDate.class, new LocalDateSerializer())
            .registerTypeAdapter(LocalDate.class, new LocalDateDeserializer())
            .create();
    inetSocketAddress = new InetSocketAddress(InetAddress.getByName(host), port);
    buffer = ByteBuffer.allocate(64 * 1024);
    in = new Input();
    out = new Output();
    logger = Logger.getLogger(Client.class.getName());
    logger.setLevel(Level.SEVERE);
  }

  public User getUser() {
    return user;
  }

  public Input getIn() {
    return in;
  }

  public Output getOut() {
    return out;
  }

  public boolean isConnected() {
    return (!sendSocket.isClosed() && sendSocket.isConnected());
  }

  /**
   * Connects to the server
   *
   * @return True if connection is correct
   * @throws IOException if not connected
   */
  public boolean connect() throws IOException {
    sendSocket = new DatagramSocket();
    sendSocket.connect(inetSocketAddress);
    logger.finest("Connection established.");
    return true;
  }

  public void close() {
    sendSocket.close();
    logger.finest("Socket closed successfully");
  }

  public void checkConnection(String input) {
    while (!isConnected()) {
      try {
        connect();
        get(input);
      } catch (IOException e) {
        try {
          Thread.sleep(1000);
        } catch (InterruptedException ex) {
          logger.severe("Error thread interruption");
        }
      }
    }
  }

  public synchronized Response send(Request request) throws IOException {
    connect();

    if (!isConnected()) {
      throw new IOException();
    }

    DatagramChannel channel = DatagramChannel.open();
    SocketAddress address = new InetSocketAddress(0);
    DatagramSocket socket = channel.socket();
    socket.setSoTimeout(100);
    socket.bind(address);

    String data = gson.toJson(request);

    ByteBuffer buffer = ByteBuffer.allocate(64 * 1024);
    buffer.put(data.getBytes());
    buffer.flip();

    channel.send(buffer, inetSocketAddress);

    buffer = ByteBuffer.allocate(64 * 1024);
    DatagramPacket datagramPacket = new DatagramPacket(buffer.array(), buffer.remaining());
    channel.socket().receive(datagramPacket);
    data = new String(datagramPacket.getData()).trim();

    return gson.fromJson(data, Response.class);
  }

  /**
   * One request
   *
   * @param input user input
   */
  public void get(String input) {
    try {
      DatagramChannel channel = DatagramChannel.open();
      SocketAddress address = new InetSocketAddress(0);
      DatagramSocket socket = channel.socket();
      socket.setSoTimeout(5000);
      socket.bind(address);

      ByteBuffer buffer;
      Request request = new Request(user, input, in, out);

      if (request.getCommandName() != null
          && request.getCommandName().equals(CommandNames.CHANGE_LOCALE)) {
        if (request.getCommandParameter() != null) {
          Locale locale;
          if (!request.getCommandParameter().contains("_")) {
            locale = new Locale(request.getCommandParameter());
          } else {
            String[] localeSplit = request.getCommandParameter().split("_");
            locale = new Locale(localeSplit[0], localeSplit[1]);
          }
          Locale.setDefault(locale);
          ResourceBundle.clearCache();
        }
        out.println(
            ResourceBundle.getBundle("Client", Locale.getDefault()).getString("changeLocale"));
        return;
      }

      if (request.getCommandName() != null && request.getCommandName().equals(CommandNames.LOGIN)) {
        out.println(ResourceBundle.getBundle("Client").getString("save"));
        return;
      }

      if (request.getCommandName() != null && request.getCommandName().equals(CommandNames.EXIT)) {
        logger.fine("Closing connection");
        sendSocket.close();
        System.exit(0);
      }

      String data = gson.toJson(request);

      buffer = ByteBuffer.allocate(64 * 1024);
      buffer.put(data.getBytes());
      buffer.flip();

      channel.send(buffer, inetSocketAddress);

      buffer = ByteBuffer.allocate(64 * 1024);
      DatagramPacket datagramPacket = new DatagramPacket(buffer.array(), buffer.remaining());
      channel.socket().receive(datagramPacket);
      data = new String(datagramPacket.getData()).trim();

      Response response = gson.fromJson(data, Response.class);

      out.println();
      out.println(response.getString());
      out.println();

      logger.info(response.getString());

      channel.close();
    } catch (SocketTimeoutException e) {
      close();
    } catch (IOException e) {
      logger.severe("Error. Closing connection");
      close();
    }
  }
}
