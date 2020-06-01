package com.lab.server;

public class App {
    /**
     * Starts server
     *
     * @param args name of the file with data
     */
    public static void main(String[] args) {
        if (args.length != 2) {
            System.err.println("Неверные аргументы. Необходим логин и пароль базы данных");
            System.exit(1);
        }

        try {
            Server server = new Server(8012, args[0], args[1]);
            System.out.println("Сервер успешно запущен");
            new Thread(() -> com.lab.client.App.main(null)).start();
            while (server.isRunning()) {
                if (server.isConnected()) {
                    server.process();
                }
            }
            server.close();
        } catch (Throwable e) {
            e.printStackTrace();
            System.err.println("Фатальная ошибка при работе сервера: " + e.getMessage());
        }
    }
}
