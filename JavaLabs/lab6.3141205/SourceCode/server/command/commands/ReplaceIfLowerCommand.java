package com.lab.server.command.commands;

import com.lab.common.exchange.Response;
import com.lab.common.musicBand.MusicBand;
import com.lab.common.musicBand.exceptions.ValidationException;
import com.lab.server.command.Command;
import com.lab.server.runner.Editor;
import com.lab.server.runner.ExecutorException;

public final class ReplaceIfLowerCommand implements Command {
    @Override
    public Response execute(Editor editor) throws ExecutorException {
        if (editor.getValue() == null) {
            throw new ExecutorException("Не введен ключ.");
        }
        int key;
        try {
            key = Integer.parseInt(editor.getValue());
        } catch (NumberFormatException e) {
            throw new ExecutorException("Неверный формат ключа: " + editor.getValue());
        }
        if (editor.getCollection().getSize() > 0) {
            if (editor.getCollection().containsKey(key)) {
                MusicBand musicBand;
                try {
                    musicBand = editor.getMusicBand();
                } catch (ValidationException e) {
                    throw new ExecutorException(e);
                }
                if (musicBand == null) {
                    return new Response(true);
                }
                if (editor.getCollection().get(key).compareTo(musicBand) > 0) {
                    editor.getCollection().replace(key, musicBand);
                    return new Response(true, "Элемент заменен по ключу " + key);
                } else {
                    return new Response(
                            true, "Значение элемента больше или равно значению элементу по ключу " + key);
                }
            } else {
                return new Response(false, "Элемент с ключом " + key + " не найден.");
            }
        }
        return new Response(false, "В коллекции нет элементов.");
    }
}
