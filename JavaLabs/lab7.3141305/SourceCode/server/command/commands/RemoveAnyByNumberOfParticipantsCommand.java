package com.lab.server.command.commands;

import com.lab.common.exchange.Response;
import com.lab.common.user.User;
import com.lab.server.command.Command;
import com.lab.server.executor.Editor;

public class RemoveAnyByNumberOfParticipantsCommand extends Command {
    public RemoveAnyByNumberOfParticipantsCommand(User user, Editor editor) {
        super(user, editor);
    }

    @Override
    public Response execute() {
        if (editor.getValue() == null) {
            return new Response(false, "Требурется параметр: количество участников");
        }

        int num;
        try {
            num = Integer.parseInt(editor.getValue());
        } catch (NumberFormatException e) {
            return new Response(false, "Неверный формат количества участников");
        }

        if (editor.getCollection().getSize() > 0) {
            if (editor.getCollection().getEntrySet().stream()
                    .noneMatch(el -> el.getValue().getNumberOfParticipants() == num)) {
                return new Response(
                        false, "В коллекции не найдено элементов с указанным количеством участников.");
            }

            if (!editor
                    .getCollection()
                    .remove(
                            editor.getCollection().getEntrySet().stream()
                                    .filter(el -> el.getValue().getNumberOfParticipants() == num)
                                    .findAny()
                                    .get()
                                    .getKey(),
                            user)) {
                return new Response(false, "Элемент не принадлежит пользователю: " + user.getLogin());
            }

            return new Response(
                    true,
                    "Музыкальная группа с количеством участников " + editor.getValue() + " успешно удалена.");
        }
        return new Response(true, "В коллекции нет элементов");
    }
}
