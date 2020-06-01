package com.lab.server.command.commands;

import com.lab.common.exchange.Response;
import com.lab.common.user.User;
import com.lab.server.command.Command;
import com.lab.server.executor.Editor;

/**
 * Clear command class
 */
public final class ClearCommand extends Command {
    public ClearCommand(User user, Editor editor) {
        super(user, editor);
    }

    /**
     * Clears the collection
     *
     * @return response and correctness
     */
    @Override
    public Response execute() {
        if (editor.getCollection().getSize() > 0) {
            editor
                    .getCollection()
                    .getEntrySet()
                    .forEach(el -> editor.getCollection().remove(el.getKey(), user));
            return new Response(true, "Коллекция пользователя очищена");
        }
        return new Response(false, "В коллекции нет элементов");
    }
}
