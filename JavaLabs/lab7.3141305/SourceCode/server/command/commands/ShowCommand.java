package com.lab.server.command.commands;

import com.lab.common.exchange.Response;
import com.lab.common.user.User;
import com.lab.server.command.Command;
import com.lab.server.executor.Editor;

import java.util.ArrayList;
import java.util.List;

/**
 * Show command class
 */
public final class ShowCommand extends Command {
    public ShowCommand(User user, Editor editor) {
        super(user, editor);
    }

    /**
     * Outputs to the standard output stream all the elements of the collection in a string
     * representation
     *
     * @return response and correctness
     */
    @Override
    public Response execute() {
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
