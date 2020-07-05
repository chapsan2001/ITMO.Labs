package com.lab.server.command.commands;

import com.lab.common.exchange.Response;
import com.lab.common.user.User;
import com.lab.common.user.hashGenerator.exceptions.HashGeneratorException;
import com.lab.server.command.Command;
import com.lab.server.executor.Editor;

import java.util.Locale;
import java.util.ResourceBundle;

/** Info command class */
public final class RegisterCommand extends Command {
  public RegisterCommand(User user, Locale locale, Editor editor) {
    super(user, locale, editor);
  }

  /**
   * Shows info about collection
   *
   * @return Response and correctness
   */
  @Override
  public Response execute() {
    if (user == null) {
      return new Response(false);
    }

    if (editor.getUserDAO().getByLogin(user.getLogin()) != null) {
      return new Response(
          false, ResourceBundle.getBundle("RegisterCommand", locale).getString("already"));
    }

    try {
      user.setPassword(editor.getHashGenerator().generateHashWithPepperAndSalt(user.getPassword()));
    } catch (HashGeneratorException e) {
      return new Response(
          true, ResourceBundle.getBundle("RegisterCommand", locale).getString("error"));
    }

    editor.getUserDAO().insert(user);

    return new Response(
        true, ResourceBundle.getBundle("RegisterCommand", locale).getString("register"));
  }
}
