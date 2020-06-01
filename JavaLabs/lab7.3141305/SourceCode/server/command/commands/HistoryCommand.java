package com.lab.server.command.commands;

import com.lab.common.exchange.Response;
import com.lab.common.user.User;
import com.lab.server.command.Command;
import com.lab.server.executor.Editor;

import java.util.ArrayList;
import java.util.List;

/**
 * History command class
 */
public final class HistoryCommand extends Command {
    public HistoryCommand(User user, Editor editor) {
        super(user, editor);
    }

    /**
     * Shows history for last 9 commands
     *
     * @return response and correctness
     */
    @Override
    public Response execute() {
        List<String> response = new ArrayList<>();
        if (editor.getCommandHistory().getCommands().size() > 0) {
            response.add("История комманд: ");
            editor.getCommandHistory().getCommands().stream()
                    .skip(Math.max(0, editor.getCommandHistory().getCommands().size() - 13))
                    .forEach(response::add);
            return new Response(true, response);
        }
        return new Response(false, "История комманд отсутствует");
    }
}
