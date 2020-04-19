package com.lab.server.command;

import com.lab.server.command.commands.*;

import java.util.HashMap;

public class AvailableCommands {
    private HashMap<String, Command> commands;
    public AvailableCommands() {
        commands = new HashMap<>();
        commands.put("help", new HelpCommand());
        commands.put("info", new InfoCommand());
        commands.put("show", new ShowCommand());
        commands.put("insert_key", new InsertKeyCommand());
        commands.put("update_id", new UpdateIdCommand());
        commands.put("remove_key", new RemoveKeyCommand());
        commands.put("clear", new ClearCommand());
        commands.put("save", new SaveCommand());
        commands.put("execute_script", new ExecuteScriptCommand());
        commands.put("clear_files_history", new ClearFilesHistoryCommand());
        commands.put("exit", new ExitCommand());
        commands.put("history", new HistoryCommand());
        commands.put("replace_if_lower", new ReplaceIfLowerCommand());
        commands.put("remove_lower_key", new RemoveLowerKeyCommand());
        commands.put("remove_any_by_number_of_participants", new RemoveAnyByNumberOfParticipantsCommand());
        commands.put("filter_starts_with_name", new FilterStartsWithNameCommand());
        commands.put("print_field_descending_number_of_participants", new PrintFieldDescendingNumberOfParticipantsCommand());
    }
    public HashMap<String, Command> getCommands() {
        return commands;
    }
    public Command getCommand(String key) {
        return commands.get(key);
    }
}
