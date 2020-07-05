package com.lab.server.command.commands;

import com.lab.common.CommandNames;
import com.lab.common.exchange.Response;
import com.lab.common.user.User;
import com.lab.server.command.Command;
import com.lab.server.executor.Editor;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;

/** Help command class */
public final class NotLoggedInHelpCommand extends Command {
  public NotLoggedInHelpCommand(User user, Locale locale, Editor editor) {
    super(user, locale, editor);
  }

  /**
   * Shows information about all available commands
   *
   * @return Response and correctness
   */
  @Override
  public Response execute() {
    List<String> response = new ArrayList<>();

    response.add(
        CommandNames.HELP
            + " : "
            + ResourceBundle.getBundle("NotLoggedInHelpCommand", locale).getString("help"));
    response.add(
        CommandNames.REGISTER
            + " : "
            + ResourceBundle.getBundle("NotLoggedInHelpCommand", locale).getString("register"));
    response.add(
        CommandNames.LOGIN
            + " : "
            + ResourceBundle.getBundle("NotLoggedInHelpCommand", locale).getString("login"));
    response.add(
        CommandNames.EXIT
            + " : "
            + ResourceBundle.getBundle("NotLoggedInHelpCommand", locale).getString("exit"));

    return new Response(true, response);
  }
}
