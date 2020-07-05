package com.lab.client.gui;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ResourceBundle;

public class AppFrame extends Application {
  public static void main(String[] args) {
    launch(args);
  }

  @Override
  public void start(Stage primaryStage) {
    FXMLLoader loader = new FXMLLoader(getClass().getResource("AppFrame.fxml"));
    Parent root = null;

    try {
      root = loader.load();
    } catch (IOException e) {
      System.err.println(ResourceBundle.getBundle("ClientApp").getString("fatal") + e.getMessage());
      System.exit(0);
    }

    Scene scene = new Scene(root);

    primaryStage.setScene(scene);
    primaryStage.sizeToScene();
    primaryStage.show();
  }
}
