package com.lab.server.command.commands;

import com.lab.common.exchange.Response;
import com.lab.common.musicBand.MusicBand;
import com.lab.common.musicBand.exceptions.ValidationException;
import com.lab.server.command.Command;
import com.lab.server.runner.Editor;
import com.lab.server.runner.ExecutorException;

public final class InsertKeyCommand implements Command {
    @Override
    public Response execute(Editor editor) throws ExecutorException {
        if (editor.getValue() == null) {
            throw new ExecutorException("Не введен ключ.");
        }
        int key;
        try {
            key = Integer.parseInt(editor.getValue());
        } catch (NumberFormatException e) {
            throw new ExecutorException("Неверный формат ключа.", e);
        }
        if (editor.getCollection().containsKey(key)) {
            return new Response(false, "Элемент с данным ключом уже существует.");
        }
        MusicBand musicBand;
        try {
            musicBand = editor.getMusicBand();
        } catch (ValidationException e) {
            throw new ExecutorException(e);
        }
        if (musicBand == null) {
            return new Response(true);
        }
        editor.getCollection().put(key, musicBand);
        return new Response(true, "Добавление нового элемента с ключом " + key + " прошло успешно.");
    }
}
