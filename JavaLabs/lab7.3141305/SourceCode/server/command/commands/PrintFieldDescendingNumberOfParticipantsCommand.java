package com.lab.server.command.commands;

import com.lab.common.exchange.Response;
import com.lab.common.user.User;
import com.lab.server.command.Command;
import com.lab.server.executor.Editor;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class PrintFieldDescendingNumberOfParticipantsCommand extends Command {
    public PrintFieldDescendingNumberOfParticipantsCommand(User user, Editor editor) {
        super(user, editor);
    }

    @Override
    public Response execute() {
        List<String> response = new ArrayList<>();

        if (editor.getCollection().getSize() > 0) {
            response.add("Количество участников в порядке убываения: ");

            List<Long> nop = new ArrayList<>();
            editor
                    .getCollection()
                    .getEntrySet()
                    .forEach(el -> nop.add(el.getValue().getNumberOfParticipants()));
            nop.sort(Collections.reverseOrder());
            nop.forEach(el -> response.add(String.valueOf(el)));

            return new Response(true, response);
        }
        return new Response(true, "В коллекции нет элементов");
    }
}
