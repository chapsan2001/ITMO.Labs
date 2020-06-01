package com.lab.server.command.commands;

import com.lab.common.exchange.Response;
import com.lab.common.user.User;
import com.lab.server.command.Command;
import com.lab.server.executor.Editor;

/**
 * Remove key command class
 */
public final class RemoveKeyCommand extends Command {
    public RemoveKeyCommand(User user, Editor editor) {
        super(user, editor);
    }

    /**
     * Removes an item from the collection by key
     *
     * @return response and correctness
     */
    @Override
    public Response execute() {
        if (editor.getValue() == null) {
            return new Response(false, "Требуется параметр: ключ");
        }
        if (editor.getCollection().getSize() > 0) {
            try {
                int key = Integer.parseInt(editor.getValue());
                if (editor.getCollection().containsKey(key)) {
                    if (editor.getCollection().remove(key, user)) {
                        return new Response(true, "Элемент удален по ключу " + key);
                    } else {
                        return new Response(
                                false,
                                "Элемент с ключем: " + key + " не принадлежит пользователю: " + user.getLogin());
                    }
                } else {
                    return new Response(false, "Элемент с ключем " + key + " не найден");
                }
            } catch (NumberFormatException e) {
                return new Response(false, "Неверый формат ключа: " + editor.getValue());
            }
        }
        return new Response(false, "В коллекции нет элементов");
    }
}
