package com.lab.server.command.commands;

import com.lab.common.exchange.Response;
import com.lab.common.musicBand.MusicBand;
import com.lab.common.musicBand.exceptions.ValidationException;
import com.lab.common.user.User;
import com.lab.server.command.Command;
import com.lab.server.executor.Editor;

import java.util.Map;

/**
 * Save command class
 */
public final class SaveCommand extends Command {
    public SaveCommand(User user, Editor editor) {
        super(user, editor);
    }

    /**
     * Saves collection to file
     *
     * @return response and correctness
     */
    @Override
    public Response execute() {
        if (editor.getCollection().getSize() > 0) {
            try {
                for (Map.Entry<Integer, MusicBand> musicBand : editor.getCollection().getEntrySet()) {
                    editor.getCollection().replace(musicBand.getKey(), musicBand.getValue(), user);
                }
            } catch (ValidationException e) {
                return new Response(false, "Внутренняя ошибка сохранения.");
            }

            return new Response(true, "Сохранение успешно произведено.");
        }
        return new Response(false, "В коллекции нет элементов.");
    }
}
