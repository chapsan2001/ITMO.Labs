package com.lab.server.command;

import com.lab.common.exchange.Response;
import com.lab.common.user.User;
import com.lab.server.executor.Editor;

import java.util.Locale;

/** Abstract class for creating commands */
public abstract class Command {
  protected final User user;
  protected final Editor editor;
  protected final Locale locale;

  protected Command(User user, Locale locale, Editor editor) {
    this.user = user;
    this.locale = locale;
    this.editor = editor;
  }

  /**
   * Returns response about execution of the command
   *
   * @return Response about execution of the command
   */
  public abstract Response execute();
}
