package com.lab.server.command.commands;

import com.lab.common.exchange.Response;
import com.lab.common.user.User;
import com.lab.server.command.Command;
import com.lab.server.executor.Editor;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;

/** Show command class */
public final class ShowCommand extends Command {
  public ShowCommand(User user, Locale locale, Editor editor) {
    super(user, locale, editor);
  }

  /**
   * Outputs to the standard output stream all the elements of the collection in a string
   * representation
   *
   * @return response and correctness
   */
  @Override
  public Response execute() {
    List<String> response = new ArrayList<>();
    if (editor.getCollection().getSize() > 0) {
      response.add(ResourceBundle.getBundle("ShowCommand", locale).getString("elements"));
      editor
          .getCollection()
          .getEntrySet()
          .forEach(el -> response.add(+el.getKey() + ": " + el.getValue()));
      return new Response(true, response);
    }
    return new Response(
        false, ResourceBundle.getBundle("ShowCommand", locale).getString("noElements"));
  }
}
