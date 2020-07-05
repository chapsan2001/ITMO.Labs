package com.lab.server.command.commands;

import com.lab.common.exchange.Response;
import com.lab.common.user.User;
import com.lab.server.command.Command;
import com.lab.server.executor.Editor;

import java.util.Locale;
import java.util.ResourceBundle;

/** Exit command class */
public final class ExitCommand extends Command {
  public ExitCommand(User user, Locale locale, Editor editor) {
    super(user, locale, editor);
  }

  /**
   * Stops program execution
   *
   * @return response and correctness
   */
  @Override
  public Response execute() {
    editor.setRunning(false);
    return new Response(true, ResourceBundle.getBundle("ExitCommand", locale).getString("exit"));
  }
}
