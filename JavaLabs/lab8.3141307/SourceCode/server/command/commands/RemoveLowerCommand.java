package com.lab.server.command.commands;

import com.lab.common.exchange.Response;
import com.lab.common.user.User;
import com.lab.server.command.Command;
import com.lab.server.executor.Editor;

import java.util.Locale;
import java.util.ResourceBundle;

/** Remove key command class */
public final class RemoveLowerCommand extends Command {
  public RemoveLowerCommand(User user, Locale locale, Editor editor) {
    super(user, locale, editor);
  }

  /**
   * Removes an item from the collection by key
   *
   * @return response and correctness
   */
  @Override
  public Response execute() {
    if (editor.getValue() == null) {
      return new Response(
          false, ResourceBundle.getBundle("RemoveLowerCommand", locale).getString("needKey"));
    }

    int key;
    try {
      key = Integer.parseInt(editor.getValue());
    } catch (NumberFormatException e) {
      return new Response(
          false, ResourceBundle.getBundle("RemoveLowerCommand", locale).getString("wrongFormat"));
    }

    if (editor.getCollection().getSize() > 0) {
      if (editor.getCollection().getEntrySet().stream().noneMatch(el -> el.getKey() < key)) {
        return new Response(
            false, ResourceBundle.getBundle("RemoveLowerCommand", locale).getString("notRemoved"));
      }

      editor.getCollection().getEntrySet().removeIf(el -> el.getKey() < key);
      return new Response(
          true, ResourceBundle.getBundle("RemoveLowerCommand", locale).getString("removed"));
    }
    return new Response(
        false, ResourceBundle.getBundle("RemoveLowerCommand", locale).getString("noElements"));
  }
}
