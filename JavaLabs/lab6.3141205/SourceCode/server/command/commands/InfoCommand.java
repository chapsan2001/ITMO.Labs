package com.lab.server.command.commands;

import com.lab.common.exchange.Response;
import com.lab.server.command.Command;
import com.lab.server.runner.Editor;

import java.util.ArrayList;
import java.util.List;

public final class InfoCommand implements Command {
    @Override
    public Response execute(Editor editor) {
        List<String> response = new ArrayList<>();
        response.add("Тип коллекции: " + editor.getCollection().getCollectionType());
        response.add("Количество элементов: " + editor.getCollection().getSize());
        response.add("Дата инициализации: " + editor.getCollection().getInitDate());
        return new Response(true, response);
    }
}
