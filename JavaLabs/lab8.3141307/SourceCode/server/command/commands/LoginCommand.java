package com.lab.server.command.commands;

import com.lab.common.exchange.Response;
import com.lab.common.user.User;
import com.lab.common.user.hashGenerator.exceptions.HashGeneratorException;
import com.lab.server.command.Command;
import com.lab.server.executor.Editor;

import java.util.Locale;
import java.util.ResourceBundle;

/** Info command class */
public final class LoginCommand extends Command {
  public LoginCommand(User user, Locale locale, Editor editor) {
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

    try {
      User found = editor.getUserDAO().getByLogin(user.getLogin());
      if (found == null
          || !found
              .getPassword()
              .equals(
                  editor.getHashGenerator().generateHashWithPepperAndSalt(user.getPassword()))) {
        return new Response(
            false, ResourceBundle.getBundle("LoginCommand", locale).getString("wrongPassword"));
      }
    } catch (HashGeneratorException e) {
      return new Response(
          true, ResourceBundle.getBundle("LoginCommand", locale).getString("error"));
    }

    return new Response(
        true, ResourceBundle.getBundle("LoginCommand", locale).getString("loggedIn"));
  }
}
