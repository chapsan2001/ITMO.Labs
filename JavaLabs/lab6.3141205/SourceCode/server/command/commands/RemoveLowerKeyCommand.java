package com.lab.server.command.commands;

import com.lab.common.exchange.Response;
import com.lab.server.command.Command;
import com.lab.server.runner.Editor;
import com.lab.server.runner.ExecutorException;

public final class RemoveLowerKeyCommand implements Command {
    @Override
    public Response execute(Editor editor) throws ExecutorException {
        if (editor.getValue() == null) {
            throw new ExecutorException("Не введен ключ.");
        }
        int key;
        try {
            key = Integer.parseInt(editor.getValue());
        } catch (NumberFormatException e) {
            throw new ExecutorException("Неверный формат ключа.");
        }
        if (editor.getCollection().getSize() > 0) {
            if (editor.getCollection().getEntrySet().stream().filter(el -> el.getKey() < key).count()
                    == 0) {
                return new Response(false, "Элементов для удаления не найдено.");
            }
            editor.getCollection().getEntrySet().removeIf(el -> el.getKey() < key);
            return new Response(true, "Элементы удалены.");
        }
        return new Response(false, "В коллекции нет элементов.");
    }
}
