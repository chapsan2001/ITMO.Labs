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
import java.time.LocalDate;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Client class
 */
public class Client {
    private final InetSocketAddress inetSocketAddress;
    private final User user;
    private Gson gson;
    private Input in;
    private Output out;
    private Logger logger;
    private ByteBuffer buffer;
    private DatagramSocket socket;

    public Client(String host, int port) throws UnknownHostException {
        user = new User();
        gson =
                new GsonBuilder()
                        .registerTypeAdapter(LocalDate.class, new LocalDateSerializer())
                        .registerTypeAdapter(LocalDate.class, new LocalDateDeserializer())
                        .create();
        inetSocketAddress = new InetSocketAddress(InetAddress.getByName(host), port);
        buffer = ByteBuffer.allocate(8192);
        in = new Input();
        out = new Output();
        logger = Logger.getLogger(Client.class.getName());
        logger.setLevel(Level.SEVERE);
    }

    public Input getIn() {
        return in;
    }

    public Output getOut() {
        return out;
    }

    public boolean isConnected() {
        return (!socket.isClosed() && socket.isConnected());
    }

    /**
     * Connects to the server
     *
     * @return True if connection is correct
     * @throws IOException if not connected
     */
    public boolean connect() throws IOException {
        socket = new DatagramSocket();
        socket.connect(inetSocketAddress);
        socket.setSoTimeout(100);
        logger.finest("Соединение установлено.");
        return true;
    }

    public void close() {
        socket.close();
        logger.finest("Сокет успешно закрыт");
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
                    logger.severe("Ошибка приостановки выполнения потока");
                }
            }
        }
    }

    /**
     * One request
     *
     * @param input user input
     */
    public void get(String input) {
        try {
            buffer.clear();
            Request request = new Request(user, input, in, out);

            if (request.getCommandName() != null
                    && request.getCommandName().equals(CommandNames.LOGIN)) {
                out.println("Данные сохранены.");
                return;
            }

            if (request.getCommandName() != null && request.getCommandName().equals(CommandNames.EXIT)) {
                out.println("Завершение работы.");
                logger.fine("Закрытие соединения");
                socket.close();
                System.exit(0);
            }

            String data = gson.toJson(request);
            buffer.put(data.getBytes());
            buffer.flip();
            DatagramPacket sendPacket =
                    new DatagramPacket(buffer.array(), buffer.remaining(), inetSocketAddress);
            socket.send(sendPacket);

            buffer.clear();
            DatagramPacket receivePacket = new DatagramPacket(buffer.array(), buffer.remaining());
            socket.receive(receivePacket);
            data = new String(receivePacket.getData(), 0, receivePacket.getLength());
            Response response = gson.fromJson(data, Response.class);

            out.println();
            out.println(response.getString());
            out.println();

            logger.info(
                    "Данные клиента: " + request.getCommandName() + " " + request.getCommandParameter());
            logger.info(response.getString());
        } catch (SocketTimeoutException e) {
            close();
        } catch (IOException e) {
            logger.severe("Ошибка. Закрытие соединения");
            close();
        }
    }
}
