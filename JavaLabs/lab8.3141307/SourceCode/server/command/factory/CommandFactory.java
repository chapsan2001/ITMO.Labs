package com.lab.server.command.factory;

import com.lab.common.user.User;
import com.lab.server.command.Command;
import com.lab.server.command.Commands;
import com.lab.server.executor.Editor;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.Locale;

public class CommandFactory {
  private final Editor editor;
  private final Commands commands;

  public CommandFactory(Editor editor, Commands commands) {
    this.editor = editor;
    this.commands = commands;
  }

  public Command createCommand(String commandName, User user, Locale locale)
      throws CommandFactoryException {
    Class<? extends Command> commandClass = commands.getCommand(commandName);

    Command command;
    try {
      Constructor<? extends Command> commandConstructor =
          commandClass.getConstructor(User.class, Locale.class, Editor.class);
      command = commandConstructor.newInstance(user, locale, editor);
    } catch (NoSuchMethodException
        | IllegalAccessException
        | InstantiationException
        | InvocationTargetException e) {
      throw new CommandFactoryException(e);
    }

    return command;
  }
}
