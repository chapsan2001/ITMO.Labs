package com.lab.server.command.commands;

import com.lab.common.exchange.Response;
import com.lab.server.command.Command;
import com.lab.server.runner.Editor;

import java.util.ArrayList;
import java.util.List;

public final class ShowCommand implements Command {
    @Override
    public Response execute(Editor editor) {
        List<String> response = new ArrayList<>();
        if (editor.getCollection().getSize() > 0) {
            response.add("Элементы коллекции в строковом представлении: ");
            editor
                    .getCollection()
                    .getEntrySet()
                    .forEach(el -> response.add(+el.getKey() + ": " + el.getValue()));
            return new Response(true, response);
        }
        return new Response(false, "В коллекции нет элементов.");
    }
}
