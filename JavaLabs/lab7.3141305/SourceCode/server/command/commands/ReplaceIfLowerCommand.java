package com.lab.server.command.commands;

import com.lab.common.exchange.Response;
import com.lab.common.musicBand.MusicBand;
import com.lab.common.musicBand.exceptions.ValidationException;
import com.lab.common.user.User;
import com.lab.server.command.Command;
import com.lab.server.executor.Editor;

/**
 * Replace if lower command class
 */
public final class ReplaceIfLowerCommand extends Command {
    public ReplaceIfLowerCommand(User user, Editor editor) {
        super(user, editor);
    }

    /**
     * Replaces the value by key, if the new value is less than the old
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
            return new Response(false, "Неверный формат ключа: " + editor.getValue());
        }

        if (editor.getCollection().getSize() > 0) {
            if (editor.getCollection().containsKey(key)) {
                MusicBand musicBand;
                musicBand = editor.getMusicBand();
                if (musicBand == null) {
                    return new Response(true);
                }
                if (editor.getCollection().get(key).compareTo(musicBand) > 0) {
                    try {
                        if (editor.getCollection().replace(key, musicBand, user)) {
                            return new Response(true, "Элемент заменен по ключу " + key);
                        } else {
                            return new Response(true, "Элемент не принадлежит пользователю " + user.getLogin());
                        }
                    } catch (ValidationException e) {
                        return new Response(false, e.getMessage());
                    }

                } else {
                    return new Response(
                            true, "Значение элемента больше или равно значению элементу по ключу " + key);
                }
            } else {
                return new Response(false, "Элемент с ключем " + key + " не найден");
            }
        }
        return new Response(false, "В коллекции нет элементов");
    }
}
