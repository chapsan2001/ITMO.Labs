package com.lab.server.command;

import com.lab.common.CommandNames;
import com.lab.server.command.commands.*;

import java.util.HashMap;

/** Available commands class */
public class Commands {
  private final HashMap<String, Class<? extends Command>> commands;

  /** Available commands constructor. Implemented using reflections */
  public Commands() {
    commands = new HashMap<>();
    commands.put(CommandNames.HELP, HelpCommand.class);
    commands.put(CommandNames.INFO, InfoCommand.class);
    commands.put(CommandNames.SHOW, ShowCommand.class);
    commands.put(CommandNames.INSERT_KEY, InsertKeyCommand.class);
    commands.put(CommandNames.UPDATE_ID, UpdateIdCommand.class);
    commands.put(CommandNames.REMOVE_KEY, RemoveKeyCommand.class);
    commands.put(CommandNames.CLEAR, ClearCommand.class);
    commands.put(CommandNames.SAVE, SaveCommand.class);
    commands.put(CommandNames.EXECUTE_SCRIPT, ExecuteScriptCommand.class);
    commands.put(CommandNames.CLEAR_FILES_HISTORY, ClearFilesHistoryCommand.class);
    commands.put(CommandNames.EXIT, ExitCommand.class);
    commands.put(CommandNames.HISTORY, HistoryCommand.class);
    commands.put(CommandNames.REPLACE_IF_LOWER, ReplaceIfLowerCommand.class);
    commands.put(CommandNames.REMOVE_LOWER, RemoveLowerCommand.class);
    commands.put(
        CommandNames.REMOVE_ANY_BY_NUMBER_OF_PARTICIPANTS,
        RemoveAnyByNumberOfParticipantsCommand.class);
    commands.put(CommandNames.FILTER_STARTS_WITH_NAME, FilterStartsWithNameCommand.class);
    commands.put(
        CommandNames.PRINT_FIELD_DESCENDING_NUMBER_OF_PARTICIPANTS,
        PrintFieldDescendingNumberOfParticipantsCommand.class);
    commands.put(CommandNames.LOGIN, LoginCommand.class);
    commands.put(CommandNames.REGISTER, RegisterCommand.class);
    commands.put(CommandNames.UPDATE_TABLE, UpdateTableCommand.class);
  }

  /**
   * Returns available commands
   *
   * @return Available commands
   */
  public HashMap<String, Class<? extends Command>> getCommands() {
    return commands;
  }

  /**
   * Returns command by its key
   *
   * @param key key to get command
   * @return Command by key
   */
  public Class<? extends Command> getCommand(String key) {
    return commands.get(key);
  }
}
