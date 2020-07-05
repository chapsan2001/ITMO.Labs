package com.lab.server.command.commands;

import com.lab.common.exchange.Response;
import com.lab.common.user.User;
import com.lab.server.command.Command;
import com.lab.server.executor.Editor;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;

/** History command class */
public final class HistoryCommand extends Command {
  public HistoryCommand(User user, Locale locale, Editor editor) {
    super(user, locale, editor);
  }

  /**
   * Shows history for last 9 commands
   *
   * @return response and correctness
   */
  @Override
  public Response execute() {
    List<String> response = new ArrayList<>();
    if (editor.getCommandHistory().getCommands().size() > 0) {
      response.add(ResourceBundle.getBundle("HistoryCommand", locale).getString("history"));
      editor.getCommandHistory().getCommands().stream()
          .skip(Math.max(0, editor.getCommandHistory().getCommands().size() - 13))
          .forEach(response::add);
      return new Response(true, response);
    }
    return new Response(
        false, ResourceBundle.getBundle("localized.HistoryCommand", locale).getString("noHistory"));
  }
}
