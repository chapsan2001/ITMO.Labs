package com.lab.server.command.commands;

import com.lab.common.exchange.Response;
import com.lab.common.user.User;
import com.lab.server.command.Command;
import com.lab.server.executor.Editor;

import java.util.Locale;
import java.util.ResourceBundle;

public class RemoveAnyByNumberOfParticipantsCommand extends Command {
  public RemoveAnyByNumberOfParticipantsCommand(User user, Locale locale, Editor editor) {
    super(user, locale, editor);
  }

  @Override
  public Response execute() {
    if (editor.getValue() == null) {
      return new Response(
          false,
          ResourceBundle.getBundle("RemoveAnyByNumberOfParticipantsCommand", locale)
              .getString("needKey"));
    }

    int num;
    try {
      num = Integer.parseInt(editor.getValue());
    } catch (NumberFormatException e) {
      return new Response(
          false,
          ResourceBundle.getBundle("RemoveAnyByNumberOfParticipantsCommand", locale)
              .getString("wrongFormat"));
    }

    if (editor.getCollection().getSize() > 0) {
      if (editor.getCollection().getEntrySet().stream()
          .noneMatch(el -> el.getValue().getNumberOfParticipants() == num)) {
        return new Response(
            false,
            ResourceBundle.getBundle("RemoveAnyByNumberOfParticipantsCommand", locale)
                .getString("notFound"));
      }

      if (!editor
          .getCollection()
          .remove(
              editor.getCollection().getEntrySet().stream()
                  .filter(el -> el.getValue().getNumberOfParticipants() == num)
                  .findAny()
                  .get()
                  .getKey(),
              user)) {
        return new Response(
            false,
            ResourceBundle.getBundle("RemoveAnyByNumberOfParticipantsCommand", locale)
                    .getString("notOwner")
                + user.getLogin());
      }

      return new Response(
          true,
          ResourceBundle.getBundle("RemoveAnyByNumberOfParticipantsCommand", locale)
              .getString("deleted"));
    }
    return new Response(
        true,
        ResourceBundle.getBundle("RemoveAnyByNumberOfParticipantsCommand", locale)
            .getString("noElements"));
  }
}
