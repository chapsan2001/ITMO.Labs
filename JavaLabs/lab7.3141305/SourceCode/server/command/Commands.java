package com.lab.server.command;

import com.lab.server.command.commands.*;

import java.util.HashMap;

/**
 * Available commands class
 */
public class Commands {
    private final HashMap<String, Class<? extends Command>> commands;

    /**
     * Available commands constructor. Implemented using reflections
     */
    public Commands() {
        commands = new HashMap<>();
        commands.put("help", HelpCommand.class);
        commands.put("info", InfoCommand.class);
        commands.put("show", ShowCommand.class);
        commands.put("insert_key", InsertKeyCommand.class);
        commands.put("update_id", UpdateIdCommand.class);
        commands.put("remove_key", RemoveKeyCommand.class);
        commands.put("clear", ClearCommand.class);
        commands.put("save", SaveCommand.class);
        commands.put("execute_script", ExecuteScriptCommand.class);
        commands.put("clear_files_history", ClearFilesHistoryCommand.class);
        commands.put("exit", ExitCommand.class);
        commands.put("history", HistoryCommand.class);
        commands.put("replace_if_lower", ReplaceIfLowerCommand.class);
        commands.put("remove_lower_key", RemoveLowerKeyCommand.class);
        commands.put(
                "remove_any_by_number_of_participants", RemoveAnyByNumberOfParticipantsCommand.class);
        commands.put("filter_starts_with_name", FilterStartsWithNameCommand.class);
        commands.put(
                "print_field_descending_number_of_participants",
                PrintFieldDescendingNumberOfParticipantsCommand.class);
        commands.put("register", RegisterCommand.class);
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
