package com.lab.server.command.commands;

import com.lab.common.exchange.Response;
import com.lab.common.musicBand.MusicBand;
import com.lab.server.command.Command;
import com.lab.server.runner.Editor;
import com.lab.server.runner.ExecutorException;

import java.util.Map;
import java.util.stream.Stream;

public class RemoveAnyByNumberOfParticipantsCommand implements Command {
    @Override
    public Response execute(Editor editor) throws ExecutorException {
        if (editor.getValue() == null) {
            throw new ExecutorException("Не введено количество участников.");
        }
        int num;
        try {
            num = Integer.parseInt(editor.getValue());
        } catch (NumberFormatException e) {
            throw new ExecutorException("Неверный формат количества участников.");
        }
        if (editor.getCollection().getSize() > 0) {
            Stream<Map.Entry<Integer, MusicBand>> stream =
                    editor.getCollection().getEntrySet().stream()
                            .filter(el -> el.getValue().getNumberOfParticipants() == num);
            if (editor.getCollection().getEntrySet().stream()
                    .noneMatch(el -> el.getValue().getNumberOfParticipants() == num)) {
                return new Response(
                        false, "В коллекции не найдено элементов с указанным количеством участников.");
            }
            editor
                    .getCollection()
                    .remove(
                            editor.getCollection().getEntrySet().stream()
                                    .filter(el -> el.getValue().getNumberOfParticipants() == num)
                                    .findAny()
                                    .get()
                                    .getKey());
            return new Response(
                    true,
                    "Музыкальная группа с количеством участников " + editor.getValue() + " успешно удалена.");
        }
        return new Response(true, "В коллекции нет элементов.");
    }
}
