package com.lab.client.gui.musicBandsTable;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;
import com.lab.client.Client;
import com.lab.client.gui.AppController;
import com.lab.common.CommandNames;
import com.lab.common.exchange.Request;
import com.lab.common.exchange.Response;
import com.lab.common.musicBand.MusicBand;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public final class UpdateMusicBandsTableTask implements Runnable {
  private final AppController appController;
  private final Gson gson;
  private final Client client;
  private final long timeout;

  public UpdateMusicBandsTableTask(
      AppController appController, Client client, Gson gson, long timeout) {
    this.appController = appController;
    this.gson = gson;
    this.client = client;
    this.timeout = timeout;
  }

  private ObservableList<MusicBand> getRows(String jsonString) {
    try {
      List<MusicBand> musicBands =
          gson.fromJson(jsonString, new TypeToken<ArrayList<MusicBand>>() {}.getType());
      return FXCollections.observableArrayList(musicBands);
    } catch (JsonSyntaxException | ClassCastException | NullPointerException e) {
      return null;
    }
  }

  @Override
  public void run() {
    while (true) {
      Response response;
      do {
        try {
          response =
              client.send(
                  new Request(
                      CommandNames.UPDATE_TABLE,
                      null,
                      client.getUser(),
                      Locale.getDefault(),
                      null));
        } catch (IOException e) {
          throw new RuntimeException(e);
        }
      } while (response == null);

      if (response == null) {
        try {
          client.connect();
        } catch (IOException e) {
          continue;
        }

        continue;
      }

      if (response.isCorrect()) {
        ObservableList<MusicBand> rows = getRows(response.getString());

        if (rows != null) {
          Platform.runLater(() -> appController.updateTable(rows));
        }
      }

      try {
        Thread.sleep(timeout);
      } catch (InterruptedException e) {
        /* no-op */
      }
    }
  }
}
