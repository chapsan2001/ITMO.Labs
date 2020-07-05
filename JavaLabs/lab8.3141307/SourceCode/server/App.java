package com.lab.server;

import java.util.ResourceBundle;

public class App {
  /**
   * Starts server
   *
   * @param args name of the file with data
   */
  public static void main(String[] args) {
    if (args.length != 3) {
      System.err.println(ResourceBundle.getBundle("ServerApp").getString("wrongArguments"));
      System.exit(1);
    }

    try {
      Server server = new Server(8012, args[0], args[1], args[2]);
      System.out.println(ResourceBundle.getBundle("ServerApp").getString("serverStarted"));
      String[] clientArgs = new String[1];
      clientArgs[0] = "console";
      new Thread(() -> com.lab.client.App.main(clientArgs)).start();
      while (server.isRunning()) {
        if (server.isConnected()) {
          server.process();
        }
      }
      server.close();
    } catch (Throwable e) {
      e.printStackTrace();
      System.err.println(
          ResourceBundle.getBundle("ServerApp").getString("fatalError") + e.getMessage());
    }
  }
}
