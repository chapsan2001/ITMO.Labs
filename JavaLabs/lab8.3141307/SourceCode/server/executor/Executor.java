package com.lab.server.executor;

import com.lab.common.CommandNames;
import com.lab.common.exchange.Request;
import com.lab.common.exchange.Response;
import com.lab.common.user.User;
import com.lab.common.user.hashGenerator.exceptions.HashGeneratorException;
import com.lab.server.command.Command;
import com.lab.server.command.Commands;
import com.lab.server.command.commands.NotLoggedInHelpCommand;
import com.lab.server.command.factory.CommandFactory;
import com.lab.server.command.factory.CommandFactoryException;
import com.lab.server.storage.dataSource.DataSource;
import com.lab.server.storage.dataSource.database.Database;

import java.util.Locale;
import java.util.ResourceBundle;

/** Executor class contains main methods that run others */
public final class Executor {
  private final DataSource dataSource;
  private final Editor editor;
  private final CommandFactory commandFactory;
  private final Commands commands;

  public Executor(String url, String user, String password) {
    commands = new Commands();
    dataSource = new Database(url, user, password);
    editor = new Editor(dataSource, this);
    commandFactory = new CommandFactory(editor, commands);
  }

  public Editor getEditor() {
    return editor;
  }

  public Commands getCommands() {
    return commands;
  }

  private Response authenticateUser(User user, Locale locale) {
    if (user == null || user.getLogin() == null || user.getPassword() == null) {
      return new Response(
          false, ResourceBundle.getBundle("Executor", locale).getString("userDataNotFound"));
    }

    User findUser = editor.getUserDAO().getByLogin(user.getLogin());

    if (findUser == null) {
      return new Response(
          false, ResourceBundle.getBundle("Executor", locale).getString("notRegistered"));
    }

    String hashedPassword;
    try {
      hashedPassword = editor.getHashGenerator().generateHashWithPepperAndSalt(user.getPassword());
    } catch (HashGeneratorException e) {
      return new Response(
          false, ResourceBundle.getBundle("Executor", locale).getString("internalAuthError"));
    }

    if (!findUser.getPassword().equals(hashedPassword)) {
      return new Response(
          false, ResourceBundle.getBundle("Executor", locale).getString("wrongPassword"));
    }

    user.setId(findUser.getId());
    return new Response(true);
  }

  /**
   * Executes command using name, parameter, and user.
   *
   * @param command concrete command.
   * @param commandArgument command argument.
   * @param user concrete user.
   * @param locale user locale
   * @return Response from execution.
   */
  public Response executeCommand(String command, String commandArgument, User user, Locale locale) {
    if (command == null || !commands.getCommands().containsKey(command) || locale == null) {
      return new Response(
          false,
          String.format(
              ResourceBundle.getBundle("Executor").getString("wrongCommand"), CommandNames.HELP));
    }

    Response authenticationResponse = authenticateUser(user, locale);

    if (!command.equals(CommandNames.REGISTER)) {
      if (!authenticationResponse.isCorrect() && command.equals(CommandNames.HELP)) {
        return new NotLoggedInHelpCommand(user, locale, editor).execute();
      }

      if (!authenticationResponse.isCorrect()) {
        return authenticationResponse;
      }
    }

    editor.setValue(commandArgument);

    Command com;

    try {
      com = commandFactory.createCommand(command, user, locale);
    } catch (CommandFactoryException e) {
      return new Response(false, ResourceBundle.getBundle("Executor").getString("errorCreating"));
    }

    Response commandResponse = com.execute();

    if (commandResponse.isCorrect() && !command.equals(CommandNames.UPDATE_TABLE)) {
      editor.getCommandHistory().addCommand(command);
    }

    editor.clearFilesHashes();
    return commandResponse;
  }

  /**
   * Executes command by request.
   *
   * @param request request with command name to be executed
   * @return Response
   */
  public Response executeCommand(Request request) {
    if (request == null) {
      return new Response(
          false,
          String.format(
              ResourceBundle.getBundle("Executor").getString("wrongCommand"), CommandNames.HELP));
    }

    return executeCommand(
        request.getCommandName(),
        request.getCommandParameter(),
        request.getUser(),
        request.getLocale());
  }
}
