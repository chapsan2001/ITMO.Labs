package com.lab.server.command.commands;

import com.lab.common.exchange.Response;
import com.lab.common.musicBand.MusicBand;
import com.lab.common.musicBand.exceptions.ValidationException;
import com.lab.common.user.User;
import com.lab.server.command.Command;
import com.lab.server.executor.Editor;

import java.util.Map;

/**
 * Update command class
 */
public final class UpdateIdCommand extends Command {
    public UpdateIdCommand(User user, Editor editor) {
        super(user, editor);
    }

    /**
     * Updates the value of a collection element whose id is equal to the specified
     *
     * @return response and correctness
     */
    @Override
    public Response execute() {
        if (editor.getValue() == null) {
            return new Response(false, "Требуется параметр: id.");
        }

        int id;
        try {
            id = Integer.parseInt(editor.getValue());
        } catch (NumberFormatException e) {
            return new Response(false, "Неверный формат id: " + editor.getValue());
        }

        Map.Entry<Integer, MusicBand> entry =
                editor.getCollection().getEntrySet().stream()
                        .filter(el -> el.getValue().getId() == id)
                        .findFirst()
                        .orElse(null);

        if (entry == null) {
            return new Response(false, "Элемент с id: " + id + " не найден.");
        }

        MusicBand worker;
        worker = editor.getMusicBand();
        if (worker == null) {
            return new Response(true);
        }

        try {
            if (editor.getCollection().replace(entry.getKey(), worker, user)) {
                return new Response(true, "Элемент обновлен по id " + id);
            } else {
                return new Response(true, "Элемент не принажденит пользователю " + user.getLogin());
            }
        } catch (ValidationException e) {
            return new Response(false, e.getMessage());
        }
    }
}
