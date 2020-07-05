package com.lab.server.command.commands;

import com.lab.common.exchange.Response;
import com.lab.common.musicBand.MusicBand;
import com.lab.common.musicBand.exceptions.ValidationException;
import com.lab.common.user.User;
import com.lab.server.command.Command;
import com.lab.server.executor.Editor;

import java.util.Locale;
import java.util.ResourceBundle;

/** Insert command class */
public final class InsertKeyCommand extends Command {
  public InsertKeyCommand(User user, Locale locale, Editor editor) {
    super(user, locale, editor);
  }

  /**
   * Inserts new element by key
   *
   * @return response and correctness
   */
  @Override
  public Response execute() {
    if (editor.getValue() == null) {
      return new Response(
          false, ResourceBundle.getBundle("InsertKeyCommand", locale).getString("needKey"));
    }

    int key;
    try {
      key = Integer.parseInt(editor.getValue());
    } catch (NumberFormatException e) {
      return new Response(
          false, ResourceBundle.getBundle("InsertKeyCommand", locale).getString("wrongFormat"));
    }

    if (editor.getCollection().containsKey(key)) {
      return new Response(
          false, ResourceBundle.getBundle("InsertKeyCommand", locale).getString("already"));
    }

    MusicBand musicBand;
    musicBand = editor.getMusicBand();
    if (musicBand == null) {
      return new Response(true);
    }
    musicBand.setOwnerId(user.getId());

    try {
      editor.getCollection().put(key, musicBand);
    } catch (ValidationException e) {
      return new Response(false, e.getMessage());
    }

    return new Response(
        true, ResourceBundle.getBundle("InsertKeyCommand", locale).getString("added"));
  }
}
