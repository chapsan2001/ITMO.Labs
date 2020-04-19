package com.lab.server.command.commands;

import com.lab.common.exchange.Response;
import com.lab.common.musicBand.MusicBand;
import com.lab.common.musicBand.exceptions.ValidationException;
import com.lab.server.command.Command;
import com.lab.server.runner.Editor;
import com.lab.server.runner.ExecutorException;

import java.util.Map;

public final class UpdateIdCommand implements Command {
    @Override
    public Response execute(Editor editor) throws ExecutorException {
        if (editor.getValue() == null) {
            throw new ExecutorException("Не указан id.");
        }
        int id;
        try {
            id = Integer.parseInt(editor.getValue());
        } catch (NumberFormatException e) {
            throw new ExecutorException("Неверный формат id: " + editor.getValue(), e);
        }
        Map.Entry<Integer, MusicBand> entry =
                editor.getCollection().getEntrySet().stream()
                        .filter(el -> el.getValue().getId() == id)
                        .findFirst()
                        .orElse(null);
        if (entry == null) {
            throw new ExecutorException("Элемент с id: " + id + " не найден.");
        }
        MusicBand worker;
        try {
            worker = editor.getMusicBand();
        } catch (ValidationException e) {
            throw new ExecutorException(e);
        }
        if (worker == null) {
            return new Response(true);
        }
        editor.getCollection().put(entry.getKey(), worker);
        return new Response(true, "Элемент обновлен по id " + id);
    }
}
