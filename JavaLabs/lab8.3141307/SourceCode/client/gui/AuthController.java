package com.lab.client.gui;

import com.lab.client.Client;
import com.lab.common.CommandNames;
import com.lab.common.exchange.Request;
import com.lab.common.exchange.Response;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.Locale;
import java.util.ResourceBundle;

public final class AuthController implements Initializable {
  private Client client;

  @FXML private Button okButton;
  @FXML private Button exitButton;
  @FXML private TabPane tabbedPanel;
  @FXML private TextField loginTextField;
  @FXML private PasswordField passwordTextField;
  @FXML private TextField newLoginTextField;
  @FXML private PasswordField newPasswordTextField;
  @FXML private Tab loginTab;
  @FXML private Tab registerTab;
  @FXML private Label loginLabel;
  @FXML private Label passwordLabel;
  @FXML private Label newLoginLabel;
  @FXML private Label newPasswordLabel;
  @FXML private ComboBox<String> localeComboBox;

  @Override
  public void initialize(URL location, ResourceBundle resources) {
    this.localeComboBox.setItems(
        FXCollections.observableArrayList("Русский", "Српски", "Hrvatski", "English (Australia)"));
    initLocaleComboBox();
    changeLocale();
  }

  public void setClient(Client client) {
    this.client = client;
  }

  private void initLocaleComboBox() {
    Locale locale = Locale.getDefault();

    if (locale.equals(new Locale("ru")) || locale.equals(new Locale("ru", "RU"))) {
      localeComboBox.getSelectionModel().selectFirst();
    } else if (locale.equals(new Locale("sr"))) {
      localeComboBox.getSelectionModel().select(1);
    } else if (locale.equals(new Locale("hr"))) {
      localeComboBox.getSelectionModel().select(2);
    } else if (locale.equals(new Locale("en", "AU"))) {
      localeComboBox.getSelectionModel().selectLast();
    } else {
      localeComboBox.getSelectionModel().selectFirst();
    }
  }

  public void onLocaleComboBoxChange() {
    if (localeComboBox.getSelectionModel().getSelectedIndex() == 0) {
      Locale.setDefault(new Locale("ru"));
    } else if (localeComboBox.getSelectionModel().getSelectedIndex() == 1) {
      Locale.setDefault(new Locale("sr"));
    } else if (localeComboBox.getSelectionModel().getSelectedIndex() == 2) {
      Locale.setDefault(new Locale("hr"));
    } else if (localeComboBox.getSelectionModel().getSelectedIndex() == 3) {
      Locale.setDefault(new Locale("en", "AU"));
    } else {
      Locale.setDefault(new Locale("ru"));
    }
  }

  public void changeLocale() {
    onLocaleComboBoxChange();
    ResourceBundle resourceBundle = ResourceBundle.getBundle("AuthDialog");

    loginTab.setText(resourceBundle.getString("loginTab"));
    registerTab.setText(resourceBundle.getString("registerTab"));

    loginLabel.setText(resourceBundle.getString("loginLabel"));
    passwordLabel.setText(resourceBundle.getString("passwordLabel"));
    newLoginLabel.setText(resourceBundle.getString("newLoginLabel"));
    newPasswordLabel.setText(resourceBundle.getString("newPasswordLabel"));

    exitButton.setText(resourceBundle.getString("exitButton"));
  }

  @FXML
  public void onOK() {
    Request request;

    if (tabbedPanel.getSelectionModel().getSelectedIndex() == 0) {
      String login = loginTextField.getText();
      login = login.isEmpty() ? null : login;

      String password = passwordTextField.getText();
      password = password.isEmpty() ? null : password;

      client.getUser().setLogin(login);
      client.getUser().setPassword(password);
      request = new Request(CommandNames.LOGIN, null, client.getUser(), Locale.getDefault(), null);
    } else {
      String newLogin = newLoginTextField.getText();
      newLogin = newLogin.isEmpty() ? null : newLogin;

      String newPassword = newPasswordTextField.getText();
      newPassword = newPassword.isEmpty() ? null : newPassword;

      client.getUser().setLogin(newLogin);
      client.getUser().setPassword(newPassword);
      request =
          new Request(CommandNames.REGISTER, null, client.getUser(), Locale.getDefault(), null);
    }

    Response response = null;
    int counter = 0;
    do {
      try {
        response = client.send(request);
        counter++;
      } catch (IOException e) {
        if (counter > 10) {
          Alert alert =
              new Alert(
                  Alert.AlertType.CONFIRMATION,
                  ResourceBundle.getBundle("AuthDialog").getString("reconnect"),
                  ButtonType.YES,
                  ButtonType.NO,
                  ButtonType.CANCEL);
          alert.setTitle(ResourceBundle.getBundle("AppFrame").getString("confirmation"));
          alert.setHeaderText(ResourceBundle.getBundle("AppFrame").getString("confirmation"));
          alert.showAndWait();
          if (alert.getResult() == ButtonType.NO || alert.getResult() == ButtonType.CANCEL) {
            onExit();
          }
          counter = 0;
        }
      }

      if (response != null && !response.isCorrect()) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION, response.getString(), ButtonType.OK);
        alert.setTitle(ResourceBundle.getBundle("AppFrame").getString("information"));
        alert.setHeaderText(ResourceBundle.getBundle("AppFrame").getString("information"));
        alert.showAndWait();
        return;
      }
      counter++;
    } while (response == null);

    Stage stage = (Stage) okButton.getScene().getWindow();
    stage.close();
  }

  @FXML
  public void onExit() {
    Stage stage = (Stage) exitButton.getScene().getWindow();
    stage.close();
    System.exit(0);
  }
}
