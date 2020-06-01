package com.lab.server.command.commands;

import com.lab.common.exchange.Response;
import com.lab.common.user.User;
import com.lab.server.command.Command;
import com.lab.server.executor.Editor;

import java.util.ArrayList;
import java.util.List;

/**
 * Info command class
 */
public final class InfoCommand extends Command {
    public InfoCommand(User user, Editor editor) {
        super(user, editor);
    }

    /**
     * Shows info about collection
     *
     * @return response and correctness
     */
    @Override
    public Response execute() {
        List<String> response = new ArrayList<>();

        response.add("Информация о коллекции: " + editor.getCollection().getCollectionType());
        response.add("Количество элементов: " + editor.getCollection().getSize());
        response.add("Дата инициализации: " + editor.getCollection().getInitDate());

        return new Response(true, response);
    }
}
