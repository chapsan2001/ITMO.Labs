package com.lab.server.command.commands;

import com.lab.common.exchange.Response;
import com.lab.server.command.Command;
import com.lab.server.runner.Editor;
import com.lab.server.runner.ExecutorException;

public final class RemoveKeyCommand implements Command {
    @Override
    public Response execute(Editor editor) throws ExecutorException {
        if (editor.getValue() == null) {
            throw new ExecutorException("Не введен ключ.");
        }
        if (editor.getCollection().getSize() > 0) {
            try {
                int key = Integer.parseInt(editor.getValue());
                if (editor.getCollection().containsKey(key)) {
                    editor.getCollection().remove(key);
                    return new Response(true, "Элемент удален по ключу " + key);
                } else {
                    throw new ExecutorException("Элемент с ключом " + key + " не найден.");
                }
            } catch (NumberFormatException e) {
                throw new ExecutorException("Неверый формат ключа: " + editor.getValue(), e);
            }
        }
        return new Response(false, "В коллекции нет элементов.");
    }
}
