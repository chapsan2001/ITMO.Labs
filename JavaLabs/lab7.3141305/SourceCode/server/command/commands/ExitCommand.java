package com.lab.server.command.commands;

import com.lab.common.exchange.Response;
import com.lab.common.user.User;
import com.lab.server.command.Command;
import com.lab.server.executor.Editor;

/**
 * Exit command class
 */
public final class ExitCommand extends Command {
    public ExitCommand(User user, Editor editor) {
        super(user, editor);
    }

    /**
     * Stops program execution
     *
     * @return response and correctness
     */
    @Override
    public Response execute() {
        editor.setRunning(false);
        return new Response(true, "Завершение работы программы");
    }
}
