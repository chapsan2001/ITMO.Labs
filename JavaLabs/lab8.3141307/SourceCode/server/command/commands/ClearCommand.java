package com.lab.server.command.commands;

import com.lab.common.exchange.Response;
import com.lab.common.user.User;
import com.lab.server.command.Command;
import com.lab.server.executor.Editor;

import java.util.Locale;
import java.util.ResourceBundle;

/** Clear command class */
public final class ClearCommand extends Command {
  public ClearCommand(User user, Locale locale, Editor editor) {
    super(user, locale, editor);
  }

  /**
   * Clears the collection
   *
   * @return response and correctness
   */
  @Override
  public Response execute() {
    if (editor.getCollection().getSize() > 0) {
      editor
          .getCollection()
          .getEntrySet()
          .forEach(el -> editor.getCollection().remove(el.getKey(), user));
      return new Response(
          true, ResourceBundle.getBundle("ClearCommand", locale).getString("clear"));
    }
    return new Response(
        false, ResourceBundle.getBundle("ClearCommand", locale).getString("noElements"));
  }
}
