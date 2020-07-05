package com.lab.server.command.commands;

import com.lab.common.exchange.Response;
import com.lab.common.user.User;
import com.lab.server.command.Command;
import com.lab.server.executor.Editor;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;

/** Info command class */
public final class InfoCommand extends Command {
  public InfoCommand(User user, Locale locale, Editor editor) {
    super(user, locale, editor);
  }

  /**
   * Shows info about collection
   *
   * @return response and correctness
   */
  @Override
  public Response execute() {
    List<String> response = new ArrayList<>();

    response.add(
        ResourceBundle.getBundle("InfoCommand", locale).getString("type")
            + editor.getCollection().getCollectionType());
    response.add(
        ResourceBundle.getBundle("InfoCommand", locale).getString("size")
            + editor.getCollection().getSize());
    response.add(
        ResourceBundle.getBundle("InfoCommand", locale).getString("date")
            + editor.getCollection().getInitDate());

    return new Response(true, response);
  }
}
