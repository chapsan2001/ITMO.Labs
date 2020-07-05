package com.lab.server.command.commands;

import com.lab.common.exchange.Response;
import com.lab.common.musicBand.MusicBand;
import com.lab.common.musicBand.exceptions.ValidationException;
import com.lab.common.user.User;
import com.lab.server.command.Command;
import com.lab.server.executor.Editor;

import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;

/** Update command class */
public final class UpdateIdCommand extends Command {
  public UpdateIdCommand(User user, Locale locale, Editor editor) {
    super(user, locale, editor);
  }

  /**
   * Updates the value of a collection element whose id is equal to the specified
   *
   * @return response and correctness
   */
  @Override
  public Response execute() {
    if (editor.getValue() == null) {
      return new Response(
          false, ResourceBundle.getBundle("UpdateIdCommand", locale).getString("needID"));
    }

    int id;
    try {
      id = Integer.parseInt(editor.getValue());
    } catch (NumberFormatException e) {
      return new Response(
          false,
          ResourceBundle.getBundle("UpdateIdCommand", locale).getString("wrongIDFormat")
              + editor.getValue());
    }

    Map.Entry<Integer, MusicBand> entry =
        editor.getCollection().getEntrySet().stream()
            .filter(el -> el.getValue().getId() == id)
            .findFirst()
            .orElse(null);

    if (entry == null) {
      return new Response(
          false,
          String.format(
              ResourceBundle.getBundle("UpdateIdCommand", locale).getString("notFound"), id));
    }

    MusicBand musicBand;
    musicBand = editor.getMusicBand();
    if (musicBand == null) {
      return new Response(true);
    }

    try {
      if (editor.getCollection().replace(entry.getKey(), musicBand, user)) {
        return new Response(
            true,
            String.format(
                ResourceBundle.getBundle("UpdateIdCommand", locale).getString("changed"), id));
      } else {
        return new Response(
            true,
            String.format(
                ResourceBundle.getBundle("UpdateIdCommand", locale).getString("notOwner"),
                user.getLogin()));
      }
    } catch (ValidationException e) {
      return new Response(false, e.getMessage());
    }
  }
}
