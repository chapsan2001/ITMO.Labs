package com.lab.client;

public class App {
    public static void main(String[] args) {
        try {
            String input;
            Client client = new Client("localhost", 4356);
            if (client.connect()) {
                while (client.isConnected()) {
                    client.getOut().print("Введите команду: ");
                    input = client.getIn().readLine();
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
