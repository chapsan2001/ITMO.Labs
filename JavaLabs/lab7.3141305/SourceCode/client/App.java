package com.lab.client;

import com.lab.common.io.Input;

public class App {
    /**
     * Starts client
     *
     * @param args no args required
     */
    public static void main(String[] args) {
        try {
            String input;
            Client client = new Client("localhost", 8012);
            if (client.connect()) {
                client.getOut().println("Клиент запущен.");
                while (client.isConnected()) {
                    client.getOut().print("Введите команду: ");
                    input = new Input().readLine();
                    client.get(input);
                    client.checkConnection(input);
                }
                client.close();
            }
        } catch (Throwable e) {
            System.err.println("Фатальная ошибка при работе клиента: " + e.getMessage());
        }
    }
}
