package com.lab.server;

public class App {
    /**
     * Starts server
     *
     * @param args name of the file with data
     */
    public static void main(String[] args) {
        try {
            Server server = new Server(4356);
            if (server.getExecutor().setArgs(args)) {
                System.out.println(server.getExecutor().getEditor().load().getString());
                System.out.println("Сервер успешно запущен.");
                new Thread(() -> com.lab.client.App.main(null)).start();
                while (server.isRunning()) {
                    if (server.isConnected()) {
                        server.process();
                    }
                }
                server.close();
            }
        } catch (Throwable e) {
            e.printStackTrace();
            System.err.println("Фатальная ошибка при работе сервера: " + e.getMessage());
        }
    }
}
