package com.lab.server.command.commands;

import com.lab.common.exchange.Response;
import com.lab.server.command.Command;
import com.lab.server.runner.Editor;
import com.lab.server.runner.ExecutorException;

import java.util.ArrayList;
import java.util.List;

public class FilterStartsWithNameCommand implements Command {

    @Override
    public Response execute(Editor editor) throws ExecutorException {
        if (editor.getValue() == null) {
            throw new ExecutorException("Требуется параметр: name");
        }
        List<String> response = new ArrayList<>();
        if (editor.getCollection().getSize() > 0) {
            response.add("Элементы, имя которых начинается с подстроки " + editor.getValue() + ": ");
            List<String> musicBands = new ArrayList<>();
            editor.getCollection().getEntrySet().stream()
                    .filter(el -> el.getValue().getName().startsWith(editor.getValue()))
                    .forEach(el -> musicBands.add(+el.getKey() + ": " + el.getValue()));
            if (musicBands.size() == 0) {
                return new Response(
                        false,
                        "В коллекции не найдено элементов, имена которых начинаются с введенной подстроки.");
            }
            response.addAll(musicBands);
            return new Response(true, response);
        }
        return new Response(true, "В коллекции нет элементов.");
    }
}
