package com.lab.server.command.commands;

import com.lab.common.exchange.Response;
import com.lab.common.user.User;
import com.lab.server.command.Command;
import com.lab.server.executor.Editor;

/**
 * Remove key command class
 */
public final class RemoveLowerKeyCommand extends Command {
    public RemoveLowerKeyCommand(User user, Editor editor) {
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

        int key;
        try {
            key = Integer.parseInt(editor.getValue());
        } catch (NumberFormatException e) {
            return new Response(false, "Неверный формат ключа");
        }

        if (editor.getCollection().getSize() > 0) {
            if (editor.getCollection().getEntrySet().stream().filter(el -> el.getKey() < key).count()
                    == 0) {
                return new Response(false, "Элементов для удаления не найдено.");
            }

            editor.getCollection().getEntrySet().removeIf(el -> el.getKey() < key);
            return new Response(true, "Элементы пользователя, меньше введенного, удалены");
        }
        return new Response(false, "В коллекции нет элементов");
    }
}
