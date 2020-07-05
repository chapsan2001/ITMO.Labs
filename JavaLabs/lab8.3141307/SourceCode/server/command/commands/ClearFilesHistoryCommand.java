package com.lab.server.command.commands;

import com.lab.common.exchange.Response;
import com.lab.common.user.User;
import com.lab.server.command.Command;
import com.lab.server.executor.Editor;

import java.util.Locale;
import java.util.ResourceBundle;

/** Clear files history command class */
public final class ClearFilesHistoryCommand extends Command {
  public ClearFilesHistoryCommand(User user, Locale locale, Editor editor) {
    super(user, locale, editor);
  }

  /**
   * Clears history of executes files with scripts
   *
   * @return Response and correctness
   */
  @Override
  public Response execute() {
    if (!editor.isFromFile()) {
      if (editor.getFilesHashes().size() == 0) {
        return new Response(
            false,
            ResourceBundle.getBundle("ClearFilesHistoryCommand", locale).getString("noElements"));
      }
      editor.clearFilesHashes();
      return new Response(
          true, ResourceBundle.getBundle("ClearFilesHistoryCommand", locale).getString("cleared"));
    }
    return new Response(
        false, ResourceBundle.getBundle("ClearFilesHistoryCommand", locale).getString("error"));
  }
}
