package com.lab.server.command.commands;

import com.lab.common.exchange.Response;
import com.lab.common.musicBand.MusicBand;
import com.lab.common.musicBand.exceptions.ValidationException;
import com.lab.common.user.User;
import com.lab.server.command.Command;
import com.lab.server.executor.Editor;

import java.util.Locale;
import java.util.ResourceBundle;

/** Replace if lower command class */
public final class ReplaceIfLowerCommand extends Command {
  public ReplaceIfLowerCommand(User user, Locale locale, Editor editor) {
    super(user, locale, editor);
  }

  /**
   * Replaces the value by key, if the new value is less than the old
   *
   * @return response and correctness
   */
  @Override
  public Response execute() {
    if (editor.getValue() == null) {
      return new Response(
          false, ResourceBundle.getBundle("ReplaceIfLowerCommand", locale).getString("needKey"));
    }

    int key;
    try {
      key = Integer.parseInt(editor.getValue());
    } catch (NumberFormatException e) {
      return new Response(
          false,
          ResourceBundle.getBundle("ReplaceIfLowerCommand", locale).getString("wrongFormat")
              + editor.getValue());
    }

    if (editor.getCollection().getSize() > 0) {
      if (editor.getCollection().containsKey(key)) {
        MusicBand musicBand;
        musicBand = editor.getMusicBand();
        if (musicBand == null) {
          return new Response(true);
        }
        if (editor.getCollection().get(key).compareTo(musicBand) > 0) {
          try {
            if (editor.getCollection().replace(key, musicBand, user)) {
              return new Response(
                  true,
                  ResourceBundle.getBundle("ReplaceIfLowerCommand", locale).getString("replaced"));
            } else {
              return new Response(
                  true,
                  ResourceBundle.getBundle("ReplaceIfLowerCommand", locale).getString("notOwner")
                      + user.getLogin());
            }
          } catch (ValidationException e) {
            return new Response(false, e.getMessage());
          }

        } else {
          return new Response(
              true,
              ResourceBundle.getBundle("ReplaceIfLowerCommand", locale).getString("notReplaced")
                  + key);
        }
      } else {
        return new Response(
            false,
            String.format(
                ResourceBundle.getBundle("ReplaceIfLowerCommand", locale).getString("notFound"),
                key));
      }
    }
    return new Response(
        false, ResourceBundle.getBundle("ReplaceIfLowerCommand", locale).getString("noElements"));
  }
}
