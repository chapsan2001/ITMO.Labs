package com.lab.server.command.commands;

import com.lab.common.exchange.Response;
import com.lab.common.musicBand.MusicBand;
import com.lab.common.musicBand.exceptions.ValidationException;
import com.lab.common.user.User;
import com.lab.server.command.Command;
import com.lab.server.executor.Editor;

/**
 * Insert command class
 */
public final class InsertKeyCommand extends Command {
    public InsertKeyCommand(User user, Editor editor) {
        super(user, editor);
    }

    /**
     * Inserts new element by key
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

        if (editor.getCollection().containsKey(key)) {
            return new Response(false, "Элемент с данным ключем уже существует");
        }

        MusicBand musicBand;
        musicBand = editor.getMusicBand();
        if (musicBand == null) {
            return new Response(true);
        }
        musicBand.setOwnerId(user.getId());

        try {
            editor.getCollection().put(key, musicBand);
        } catch (ValidationException e) {
            return new Response(false, e.getMessage());
        }

        return new Response(true, "Добавление нового элемента с ключем " + key + " прошло успешно");
    }
}
