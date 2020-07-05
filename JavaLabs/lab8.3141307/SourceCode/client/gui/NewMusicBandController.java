package com.lab.client.gui;

import com.lab.common.musicBand.Album;
import com.lab.common.musicBand.Coordinates;
import com.lab.common.musicBand.MusicBand;
import com.lab.common.musicBand.MusicGenre;
import com.lab.common.musicBand.exceptions.ValidationException;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

public class NewMusicBandController implements Initializable {
  private MusicBand musicBand;

  @FXML private TextField nameTextField;
  @FXML private TextField coordinatesXTextField;
  @FXML private TextField coordinatesYTextField;
  @FXML private TextField numberOfParticipantsTextField;
  @FXML private ComboBox<String> genreComboBox;
  @FXML private TextField albumNameTextField;
  @FXML private TextField albumTracksTextField;
  @FXML private TextField albumSalesTextField;

  @FXML private Label nameLabel;
  @FXML private Label coordinatesXLabel;
  @FXML private Label coordinatesYLabel;
  @FXML private Label numberOfParticipantsLabel;
  @FXML private Label genreLabel;
  @FXML private Label albumNameLabel;
  @FXML private Label albumTracksLabel;
  @FXML private Label albumSalesLabel;

  @FXML private Button okButton;

  @Override
  public void initialize(URL location, ResourceBundle resources) {
    changeLocale();
    this.genreComboBox.setItems(
        FXCollections.observableArrayList(
            (Collection<? extends String>)
                Arrays.asList(MusicGenre.values()).stream()
                    .map(el -> el.toString())
                    .collect(Collectors.toCollection(ArrayList::new))));
  }

  public void changeLocale() {
    ResourceBundle resourceBundle = ResourceBundle.getBundle("NewMusicBand");

    nameLabel.setText(resourceBundle.getString("name"));
    coordinatesXLabel.setText(resourceBundle.getString("x"));
    coordinatesYLabel.setText(resourceBundle.getString("y"));
    numberOfParticipantsLabel.setText(resourceBundle.getString("number"));
    genreLabel.setText(resourceBundle.getString("genre"));
    albumNameLabel.setText(resourceBundle.getString("albumName"));
    albumTracksLabel.setText(resourceBundle.getString("tracks"));
    albumSalesLabel.setText(resourceBundle.getString("sales"));
  }

  @FXML
  public void onOK() {
    musicBand = new MusicBand();

    try {
      musicBand.setName(nameTextField.getText());
    } catch (ValidationException e) {
      new Alert(Alert.AlertType.WARNING, e.getMessage()).show();
      return;
    }

    Coordinates coordinates = new Coordinates();
    try {
      String xString =
          coordinatesXTextField.getText().isEmpty() ? null : coordinatesXTextField.getText();
      coordinates.setX(Long.parseLong(xString));
    } catch (NumberFormatException | NullPointerException | ValidationException e) {
      new Alert(
              Alert.AlertType.WARNING,
              ResourceBundle.getBundle("NewMusicBand").getString("wrong")
                  + coordinatesXLabel.getText()
                  + e.getLocalizedMessage())
          .show();
      return;
    }

    try {
      String yString =
          coordinatesYTextField.getText().isEmpty() ? null : coordinatesYTextField.getText();
      coordinates.setY(Float.parseFloat(yString));
    } catch (NumberFormatException | NullPointerException | ValidationException e) {
      new Alert(
              Alert.AlertType.WARNING,
              ResourceBundle.getBundle("NewMusicBand").getString("wrong")
                  + coordinatesYLabel.getText()
                  + e.getLocalizedMessage())
          .show();
      return;
    }

    try {
      musicBand.setCoordinates(coordinates);
    } catch (ValidationException e) {
      /* no-op */
    }

    try {
      String numberString =
          numberOfParticipantsTextField.getText().isEmpty()
              ? null
              : numberOfParticipantsTextField.getText();
      musicBand.setNumberOfParticipants(Long.parseLong(numberString));
    } catch (NumberFormatException | NullPointerException | ValidationException e) {
      new Alert(
              Alert.AlertType.WARNING,
              ResourceBundle.getBundle("NewMusicBand").getString("wrong")
                  + numberOfParticipantsLabel.getText()
                  + e.getLocalizedMessage())
          .show();
      return;
    }

    try {
      String genreString = genreComboBox.getSelectionModel().getSelectedItem();
      musicBand.setGenre(MusicGenre.valueOf(genreString));
    } catch (NullPointerException e) {
      /* no-op */
    } catch (IllegalArgumentException e) {
      new Alert(
              Alert.AlertType.WARNING,
              ResourceBundle.getBundle("NewMusicBand").getString("wrong") + genreLabel.getText())
          .show();
      return;
    }

    Album album = new Album();
    try {
      album.setName(albumNameTextField.getText());
    } catch (ValidationException e) {
      new Alert(
              Alert.AlertType.WARNING,
              ResourceBundle.getBundle("NewMusicBand").getString("wrong")
                  + albumNameLabel.getText()
                  + e.getLocalizedMessage())
          .show();
      return;
    }

    try {
      String tracksString =
          albumTracksTextField.getText().isEmpty() ? null : albumTracksTextField.getText();
      album.setTracks(Integer.parseInt(tracksString));
    } catch (NumberFormatException | NullPointerException | ValidationException e) {
      new Alert(
              Alert.AlertType.WARNING,
              ResourceBundle.getBundle("NewMusicBand").getString("wrong")
                  + albumTracksLabel.getText()
                  + e.getLocalizedMessage())
          .show();
      return;
    }

    try {
      String salesString =
          albumSalesTextField.getText().isEmpty() ? null : albumSalesTextField.getText();
      album.setSales(Float.parseFloat(salesString));
    } catch (NumberFormatException | NullPointerException | ValidationException e) {
      new Alert(
              Alert.AlertType.WARNING,
              ResourceBundle.getBundle("NewMusicBand").getString("wrong")
                  + albumSalesLabel.getText()
                  + e.getLocalizedMessage())
          .show();
      return;
    }
    musicBand.setBestAlbum(album);

    Stage stage = (Stage) okButton.getScene().getWindow();
    stage.close();
  }

  @FXML
  public void onCancel() {
    musicBand = null;
    Stage stage = (Stage) okButton.getScene().getWindow();
    stage.close();
  }

  MusicBand getMusicBand() {
    return musicBand;
  }
}
