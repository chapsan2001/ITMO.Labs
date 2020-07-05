package com.lab.client;

import com.lab.client.gui.AppFrame;
import com.lab.common.io.Input;
import javafx.application.Application;

import java.util.ResourceBundle;

public class App {
  /**
   * Starts client
   *
   * @param args no args required
   */
  public static void main(String[] args) {
    try {
      if (args.length == 1 && args[0].equals("console")) {
        String input;
        Client client = new Client("localhost", 8012);
        if (client.connect()) {
          client.getOut().println(ResourceBundle.getBundle("ClientApp").getString("started"));
          while (client.isConnected()) {
            client.getOut().print(ResourceBundle.getBundle("ClientApp").getString("inputCommand"));
            input = new Input().readLine();
            client.get(input);
            client.checkConnection(input);
          }
          client.close();
        }
      } else {
        Application.launch(AppFrame.class);
      }
    } catch (Throwable e) {
      System.err.println(ResourceBundle.getBundle("ClientApp").getString("fatal") + e.getMessage());
    }
  }
}
