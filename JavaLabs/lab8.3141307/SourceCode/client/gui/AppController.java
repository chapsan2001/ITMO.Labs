package com.lab.client.gui;

import com.google.gson.Gson;
import com.lab.client.Client;
import com.lab.client.gui.musicBandCanvas.*;
import com.lab.client.gui.musicBandsTable.UpdateMusicBandsTableTask;
import com.lab.common.CommandNames;
import com.lab.common.exchange.Request;
import com.lab.common.exchange.Response;
import com.lab.common.musicBand.MusicBand;
import com.lab.common.musicBand.MusicGenre;
import javafx.animation.AnimationTimer;
import javafx.animation.Timeline;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Region;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.net.UnknownHostException;
import java.time.LocalDate;
import java.util.Locale;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.concurrent.atomic.AtomicReference;

public final class AppController implements Initializable {
  public static final double W = 400;
  public static final double H = 400;

  private UserCircle userCircle;
  private MusicBandCircle musicBandCircle;
  private CoordinatesRect coordinatesCircle;
  private AlbumRect albumRect;

  private Client client;
  private FileChooser fileChooser;

  @FXML private TableView<MusicBand> musicBandTable;
  @FXML private TableColumn<MusicBand, Integer> idColumn;
  @FXML private TableColumn<MusicBand, Long> ownerIdColumn;
  @FXML private TableColumn<MusicBand, Integer> keyColumn;
  @FXML private TableColumn<MusicBand, String> nameColumn;
  @FXML private TableColumn<MusicBand, Long> coordinatesXColumn;
  @FXML private TableColumn<MusicBand, Float> coordinatesYColumn;
  @FXML private TableColumn<MusicBand, LocalDate> creationDateColumn;
  @FXML private TableColumn<MusicBand, Long> numberOfParticipantsColumn;
  @FXML private TableColumn<MusicBand, MusicGenre> genreColumn;
  @FXML private TableColumn<MusicBand, String> albumNameColumn;
  @FXML private TableColumn<MusicBand, Integer> albumTracksColumn;
  @FXML private TableColumn<MusicBand, Float> albumSalesColumn;

  @FXML private Label musicBandsLabel;
  @FXML private Button okButton;
  @FXML private Button exitButton;
  @FXML private Button drawButton;
  @FXML private ComboBox<String> commandsComboBox;
  @FXML private ComboBox<String> localeComboBox;

  @Override
  public void initialize(URL location, ResourceBundle resources) {
    try {
      this.client = new Client("localhost", 8012);
    } catch (UnknownHostException e) {
      System.err.println(ResourceBundle.getBundle("ClientApp").getString("fatal") + e.getMessage());
      System.exit(0);
    }
    auth();
    client.getUser().setLogin("User");
    client.getUser().setPassword("User");
    this.localeComboBox.setItems(
        FXCollections.observableArrayList("Русский", "Српски", "Hrvatski", "English (Australia)"));
    this.commandsComboBox.setItems(
        FXCollections.observableArrayList(
            CommandNames.CLEAR,
            CommandNames.CLEAR_FILES_HISTORY,
            CommandNames.EXECUTE_SCRIPT,
            CommandNames.FILTER_STARTS_WITH_NAME,
            CommandNames.HELP,
            CommandNames.HISTORY,
            CommandNames.INFO,
            CommandNames.INSERT_KEY,
            CommandNames.PRINT_FIELD_DESCENDING_NUMBER_OF_PARTICIPANTS,
            CommandNames.REMOVE_ANY_BY_NUMBER_OF_PARTICIPANTS,
            CommandNames.REMOVE_KEY,
            CommandNames.REMOVE_LOWER,
            CommandNames.REPLACE_IF_LOWER,
            CommandNames.SAVE,
            CommandNames.SHOW,
            CommandNames.UPDATE_ID));
    initLocaleComboBox();
    fileChooser = new FileChooser();
    fileChooser.setInitialDirectory(new File("."));
    initMusicBandsTable();
    Thread tableUpdater = new Thread(new UpdateMusicBandsTableTask(this, client, new Gson(), 1000));
    try {
      tableUpdater.start();
    } catch (RuntimeException e) {
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
        System.exit(0);
      }
    }
    changeLocale();
  }

  public void changeLocale() {
    onLocaleComboBoxChange();
    ResourceBundle resourceBundle = ResourceBundle.getBundle("AppFrame");
    musicBandsLabel.setText(resourceBundle.getString("label"));
    exitButton.setText(resourceBundle.getString("exit"));
    drawButton.setText(resourceBundle.getString("draw"));

    idColumn.setText(resourceBundle.getString("id"));
    ownerIdColumn.setText(resourceBundle.getString("ownerId"));
    keyColumn.setText(resourceBundle.getString("key"));
    nameColumn.setText(resourceBundle.getString("name"));
    coordinatesXColumn.setText(resourceBundle.getString("coordinatesX"));
    coordinatesYColumn.setText(resourceBundle.getString("coordinatesY"));
    creationDateColumn.setText(resourceBundle.getString("creationDate"));
    numberOfParticipantsColumn.setText(resourceBundle.getString("numberOfParticipants"));
    genreColumn.setText(resourceBundle.getString("genre"));
    albumNameColumn.setText(resourceBundle.getString("albumName"));
    albumTracksColumn.setText(resourceBundle.getString("tracks"));
    albumSalesColumn.setText(resourceBundle.getString("sales"));
  }

  @FXML
  public void onDraw() {
    MusicBand musicBand = musicBandTable.getSelectionModel().getSelectedItem();

    if (musicBand == null) {
      Alert alert =
          new Alert(
              Alert.AlertType.CONFIRMATION,
              ResourceBundle.getBundle("AppFrame").getString("first"),
              ButtonType.OK);
      alert.setTitle(ResourceBundle.getBundle("AppFrame").getString("confirmation"));
      alert.setHeaderText(ResourceBundle.getBundle("AppFrame").getString("confirmation"));
      alert.showAndWait();
      return;
    }

    this.userCircle = new UserCircle();
    this.musicBandCircle = new MusicBandCircle();
    this.coordinatesCircle =
        new CoordinatesRect(musicBand.getCoordinates().getX(), musicBand.getCoordinates().getY());
    this.albumRect = new AlbumRect();

    Timeline timeline = new Timeline();
    timeline.setAutoReverse(true);
    timeline.setCycleCount(Timeline.INDEFINITE);

    final Canvas canvas = new Canvas(W, H);
    AnimationTimer timer =
        new AnimationTimer() {
          @Override
          public void handle(long now) {
            GraphicsContext gc = canvas.getGraphicsContext2D();
            gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
            musicBandCircle.move();
            musicBandCircle.draw(gc);
            coordinatesCircle.move();
            coordinatesCircle.draw(gc);
            userCircle.move();
            userCircle.draw(gc);
            albumRect.move();
            albumRect.draw(gc);
          }
        };

    BorderPane borderPane = new BorderPane();
    borderPane.setCenter(canvas);

    borderPane.setOnMouseClicked(
        e -> {
          if (coordinatesCircle.containsCircle(e.getX(), e.getY())) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION, formCircleInfo(coordinatesCircle));
            alert.setTitle(ResourceBundle.getBundle("AppFrame").getString("information"));
            alert.setHeaderText(ResourceBundle.getBundle("AppFrame").getString("information"));
            alert.show();
          } else if (userCircle.containsCircle(e.getX(), e.getY())) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION, formCircleInfo(userCircle));
            alert.setTitle(ResourceBundle.getBundle("AppFrame").getString("information"));
            alert.setHeaderText(ResourceBundle.getBundle("AppFrame").getString("information"));
            alert.show();
          } else if (albumRect.containsRect(e.getX(), e.getY())) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION, formCircleInfo(albumRect));
            alert.setTitle(ResourceBundle.getBundle("AppFrame").getString("information"));
            alert.setHeaderText(ResourceBundle.getBundle("AppFrame").getString("information"));
            alert.show();
          } else if (musicBandCircle.containsCircle(e.getX(), e.getY())) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION, formCircleInfo(musicBandCircle));
            alert.setTitle(ResourceBundle.getBundle("AppFrame").getString("information"));
            alert.setHeaderText(ResourceBundle.getBundle("AppFrame").getString("information"));
            alert.show();
          }
        });

    Stage stage = new Stage();
    stage.setScene(new Scene(borderPane));
    timer.start();
    timeline.play();
    stage.showAndWait();
    timeline.stop();
    timer.stop();
  }

  private String formCircleInfo(Shape shape) {
    return shape.getClass().getSimpleName()
        + System.lineSeparator()
        + "x: "
        + shape.getX()
        + System.lineSeparator()
        + "y: "
        + shape.getY();
  }

  public void auth() {
    FXMLLoader loader = new FXMLLoader(getClass().getResource("AuthDialog.fxml"));
    Parent root = null;

    try {
      root = loader.load();
    } catch (IOException e) {
      System.err.println(ResourceBundle.getBundle("ClientApp").getString("fatal") + e.getMessage());
      System.exit(0);
    }

    Stage stage = new Stage();
    stage.setScene(new Scene(root));
    stage.setResizable(false);
    stage.initModality(Modality.APPLICATION_MODAL);
    AuthController controller = loader.getController();
    controller.setClient(client);
    stage.showAndWait();
  }

  public MusicBand create() {
    FXMLLoader loader = new FXMLLoader(getClass().getResource("NewMusicBandDialog.fxml"));
    Parent root = null;

    try {
      root = loader.load();
    } catch (IOException e) {
      System.err.println(ResourceBundle.getBundle("ClientApp").getString("fatal") + e.getMessage());
      System.exit(0);
    }

    Stage stage = new Stage();
    stage.setScene(new Scene(root));
    stage.setResizable(false);
    stage.initModality(Modality.APPLICATION_MODAL);
    NewMusicBandController controller = loader.getController();
    controller.changeLocale();
    stage.showAndWait();
    return controller.getMusicBand();
  }

  @FXML
  public void onExit() {
    Stage stage = (Stage) exitButton.getScene().getWindow();
    stage.close();
    System.exit(0);
  }

  private void initLocaleComboBox() {
    Locale locale = Locale.getDefault();

    if (locale.equals(new Locale("ru")) || locale.equals(new Locale("ru", "RU"))) {
      localeComboBox.getSelectionModel().selectFirst();
    } else if (locale.equals(new Locale("sr"))) {
      localeComboBox.getSelectionModel().select(2);
    } else if (locale.equals(new Locale("hr"))) {
      localeComboBox.getSelectionModel().select(3);
    } else if (locale.equals(new Locale("en", "AU"))) {
      localeComboBox.getSelectionModel().select(4);
    } else {
      localeComboBox.getSelectionModel().select(1);
    }
  }

  private void initMusicBandsTable() {
    idColumn.setCellValueFactory(
        cellData -> new ReadOnlyObjectWrapper<>(cellData.getValue().getId()));
    ownerIdColumn.setCellValueFactory(
        cellData -> new ReadOnlyObjectWrapper<>(cellData.getValue().getOwnerId()));
    keyColumn.setCellValueFactory(
        cellData -> new ReadOnlyObjectWrapper<>(cellData.getValue().getKey()));
    nameColumn.setCellValueFactory(
        cellData -> new ReadOnlyObjectWrapper<>(cellData.getValue().getName()));
    coordinatesXColumn.setCellValueFactory(
        cellData -> new ReadOnlyObjectWrapper<>(cellData.getValue().getCoordinates().getX()));
    coordinatesYColumn.setCellValueFactory(
        cellData -> new ReadOnlyObjectWrapper<>(cellData.getValue().getCoordinates().getY()));
    creationDateColumn.setCellValueFactory(
        cellData -> new ReadOnlyObjectWrapper<>(cellData.getValue().getCreationDate()));
    numberOfParticipantsColumn.setCellValueFactory(
        cellData -> new ReadOnlyObjectWrapper<>(cellData.getValue().getNumberOfParticipants()));
    genreColumn.setCellValueFactory(
        cellData -> new ReadOnlyObjectWrapper<>(cellData.getValue().getGenre()));
    albumNameColumn.setCellValueFactory(
        cellData -> new ReadOnlyObjectWrapper<>(cellData.getValue().getBestAlbum().getName()));
    albumTracksColumn.setCellValueFactory(
        cellData -> new ReadOnlyObjectWrapper<>(cellData.getValue().getBestAlbum().getTracks()));
    albumSalesColumn.setCellValueFactory(
        cellData -> new ReadOnlyObjectWrapper<>(cellData.getValue().getBestAlbum().getSales()));
  }

  @FXML
  public void onOK() {
    String command = commandsComboBox.getSelectionModel().getSelectedItem();

    if (command == null) {
      command = CommandNames.HELP;
    }

    executeCommand(command);
  }

  private void executeCommand(String command) {
    Request request;

    if (command.equals(CommandNames.EXECUTE_SCRIPT)) {
      File file = fileChooser.showOpenDialog(okButton.getScene().getWindow());
      if (file == null) {
        return;
      }
      request = new Request(command, file.getPath(), client.getUser(), Locale.getDefault(), null);
    } else if (command.equals(CommandNames.FILTER_STARTS_WITH_NAME)) {
      TextInputDialog dialog = new TextInputDialog();
      dialog.setContentText(ResourceBundle.getBundle("AppController").getString("name"));
      dialog.setTitle(ResourceBundle.getBundle("AppFrame").getString("input"));
      dialog.setHeaderText(ResourceBundle.getBundle("AppFrame").getString("input"));
      Optional<String> result = dialog.showAndWait();
      AtomicReference<String> name = new AtomicReference<>();
      result.ifPresent(name::set);
      request = new Request(command, name.get(), client.getUser(), Locale.getDefault(), null);
    } else if (command.equals(CommandNames.INSERT_KEY)
        || command.equals(CommandNames.REMOVE_LOWER)
        || command.equals(CommandNames.REPLACE_IF_LOWER)) {
      TextInputDialog dialog = new TextInputDialog();
      dialog.setContentText(ResourceBundle.getBundle("AppController").getString("key"));
      dialog.setTitle(ResourceBundle.getBundle("AppFrame").getString("input"));
      dialog.setHeaderText(ResourceBundle.getBundle("AppFrame").getString("input"));
      Optional<String> result = dialog.showAndWait();
      AtomicReference<String> key = new AtomicReference<>();
      result.ifPresent(key::set);
      MusicBand musicBand = create();
      if (musicBand == null) {
        return;
      }
      request = new Request(command, key.get(), client.getUser(), Locale.getDefault(), musicBand);
    } else if (command.equals(CommandNames.REMOVE_ANY_BY_NUMBER_OF_PARTICIPANTS)) {
      TextInputDialog dialog = new TextInputDialog();
      dialog.setContentText(ResourceBundle.getBundle("AppController").getString("number"));
      dialog.setTitle(ResourceBundle.getBundle("AppFrame").getString("input"));
      dialog.setHeaderText(ResourceBundle.getBundle("AppFrame").getString("input"));
      Optional<String> result = dialog.showAndWait();
      AtomicReference<String> numberOgParticipants = new AtomicReference<>();
      result.ifPresent(numberOgParticipants::set);
      request =
          new Request(
              command, numberOgParticipants.get(), client.getUser(), Locale.getDefault(), null);
    } else if (command.equals(CommandNames.REMOVE_KEY)) {
      TextInputDialog dialog = new TextInputDialog();
      dialog.setContentText(ResourceBundle.getBundle("AppController").getString("key"));
      dialog.setTitle(ResourceBundle.getBundle("AppFrame").getString("input"));
      dialog.setHeaderText(ResourceBundle.getBundle("AppFrame").getString("input"));
      Optional<String> result = dialog.showAndWait();
      AtomicReference<String> key = new AtomicReference<>();
      result.ifPresent(key::set);
      request = new Request(command, key.get(), client.getUser(), Locale.getDefault(), null);
    } else if (command.equals(CommandNames.UPDATE_ID)) {
      TextInputDialog dialog = new TextInputDialog();
      dialog.setContentText(ResourceBundle.getBundle("AppController").getString("id"));
      dialog.setTitle(ResourceBundle.getBundle("AppFrame").getString("input"));
      dialog.setHeaderText(ResourceBundle.getBundle("AppFrame").getString("input"));
      Optional<String> result = dialog.showAndWait();
      AtomicReference<String> id = new AtomicReference<>();
      result.ifPresent(id::set);
      MusicBand musicBand = create();
      if (musicBand == null) {
        return;
      }
      request = new Request(command, id.get(), client.getUser(), Locale.getDefault(), musicBand);
    } else {
      request = new Request(command, null, client.getUser(), Locale.getDefault(), null);
    }

    Response response = null;
    do {
      try {
        response = client.send(request);
      } catch (IOException e) {
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
          System.exit(0);
        }
      }
    } while (response == null);

    Alert alert = new Alert(Alert.AlertType.INFORMATION, response.getString(), ButtonType.OK);
    alert.setTitle(ResourceBundle.getBundle("AppFrame").getString("information"));
    alert.setHeaderText(ResourceBundle.getBundle("AppFrame").getString("information"));
    alert.getDialogPane().setMinSize(Region.USE_PREF_SIZE, Region.USE_PREF_SIZE);
    alert.getDialogPane().setMinWidth(700);
    alert.showAndWait();
  }

  private void onLocaleComboBoxChange() {
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

  public void updateTable(ObservableList<MusicBand> musicBands) {
    ObservableList<TablePosition> selected =
        FXCollections.observableArrayList(musicBandTable.getSelectionModel().getSelectedCells());
    ObservableList<TableColumn<MusicBand, ?>> sorted =
        FXCollections.observableArrayList(musicBandTable.getSortOrder());
    musicBandTable.setItems(musicBands);
    selected.forEach(
        el -> {
          if (el.getRow() < musicBandTable.getItems().size())
            musicBandTable.getSelectionModel().clearAndSelect(el.getRow(), el.getTableColumn());
        });
    musicBandTable.getSortOrder().addAll(sorted);
    musicBandTable.sort();
  }
}
