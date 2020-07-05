package com.lab.server.command.commands;

import com.google.gson.Gson;
import com.lab.common.exchange.Response;
import com.lab.common.user.User;
import com.lab.server.command.Command;
import com.lab.server.executor.Editor;

import java.util.ArrayList;
import java.util.Locale;

/** Show command class */
public final class UpdateTableCommand extends Command {
  public UpdateTableCommand(User user, Locale locale, Editor editor) {
    super(user, locale, editor);
  }

  /**
   * Outputs to the standard output stream all the elements of the collection in a string
   * representation
   *
   * @return Response and correctness
   */
  @Override
  public Response execute() {
    return new Response(
        true,
        new Gson()
            .toJson(new ArrayList<>(editor.getCollection().getRootCollectionCopy().values())));
  }
}
