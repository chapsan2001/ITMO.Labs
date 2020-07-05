package com.lab.server.command.commands;

import com.lab.common.exchange.Response;
import com.lab.common.user.User;
import com.lab.server.command.Command;
import com.lab.server.executor.Editor;

import java.util.Locale;
import java.util.ResourceBundle;

/** Remove key command class */
public final class RemoveKeyCommand extends Command {
  public RemoveKeyCommand(User user, Locale locale, Editor editor) {
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
          false, ResourceBundle.getBundle("RemoveKeyCommand", locale).getString("needKey"));
    }
    if (editor.getCollection().getSize() > 0) {
      try {
        int key = Integer.parseInt(editor.getValue());
        if (editor.getCollection().containsKey(key)) {
          if (editor.getCollection().remove(key, user)) {
            return new Response(
                true, ResourceBundle.getBundle("RemoveKeyCommand", locale).getString("removed"));
          } else {
            return new Response(
                false,
                ResourceBundle.getBundle("RemoveKeyCommand", locale).getString("notOwner")
                    + user.getLogin());
          }
        } else {
          return new Response(
              false,
              String.format(
                  ResourceBundle.getBundle("RemoveKeyCommand", locale).getString("notFound"), key));
        }
      } catch (NumberFormatException e) {
        return new Response(
            false,
            ResourceBundle.getBundle("RemoveKeyCommand", locale).getString("wrongFormat")
                + editor.getValue());
      }
    }
    return new Response(
        false, ResourceBundle.getBundle("RemoveKeyCommand", locale).getString("noElements"));
  }
}
