package com.lab.server.command.commands;

import com.lab.common.exchange.Response;
import com.lab.server.command.Command;
import com.lab.server.runner.Editor;

public final class ClearCommand implements Command {
    @Override
    public Response execute(Editor editor) {
        if (editor.getCollection().getSize() > 0) {
            editor.getCollection().clear();
            return new Response(true, "Коллекция очищена");
        }
        return new Response(false, "В коллекции нет элементов");
    }
}
