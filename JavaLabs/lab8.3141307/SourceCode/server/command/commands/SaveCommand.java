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

/** Save command class */
public final class SaveCommand extends Command {
  public SaveCommand(User user, Locale locale, Editor editor) {
    super(user, locale, editor);
  }

  /**
   * Saves collection to file
   *
   * @return response and correctness
   */
  @Override
  public Response execute() {
    if (editor.getCollection().getSize() > 0) {
      try {
        for (Map.Entry<Integer, MusicBand> musicBand : editor.getCollection().getEntrySet()) {
          editor.getCollection().replace(musicBand.getKey(), musicBand.getValue(), user);
        }
      } catch (ValidationException e) {
        return new Response(
            false,
            ResourceBundle.getBundle("SaveCommand", locale).getString("internalError")
                + e.getMessage());
      }

      return new Response(true, ResourceBundle.getBundle("SaveCommand", locale).getString("save"));
    }
    return new Response(
        false, ResourceBundle.getBundle("SaveCommand", locale).getString("noElements"));
  }
}
