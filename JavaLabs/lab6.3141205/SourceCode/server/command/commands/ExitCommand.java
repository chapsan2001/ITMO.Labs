package com.lab.server.command.commands;

import com.lab.common.exchange.Response;
import com.lab.server.command.Command;
import com.lab.server.runner.Editor;

public final class ExitCommand implements Command {
    @Override
    public Response execute(Editor editor) {
        editor.setRunning(false);
        return new Response(true, "Завершение работы программы");
    }
}
