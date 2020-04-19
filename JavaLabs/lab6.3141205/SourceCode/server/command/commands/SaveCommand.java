package com.lab.server.command.commands;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.lab.common.adapters.LocalDateDeserializer;
import com.lab.common.adapters.LocalDateSerializer;
import com.lab.common.exchange.Response;
import com.lab.server.command.Command;
import com.lab.server.runner.Editor;
import com.lab.server.runner.ExecutorException;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.time.LocalDate;

public final class SaveCommand implements Command {
    @Override
    public Response execute(Editor editor) throws ExecutorException {
        if (editor.getCollection().getSize() > 0) {
            File sf = new File(editor.getDataFilePath());
            if (sf.isDirectory()) {
                throw new ExecutorException("Неверный путь к файлу.");
            }
            if (!sf.exists()) {
                try {
                    if (!sf.createNewFile()) {
                        throw new ExecutorException("Ошбика создания файла: ");
                    }
                } catch (IOException e) {
                    throw new ExecutorException("Ошбика создания файла: ", e);
                }
            }
            FileOutputStream fileOutputStream;
            try {
                fileOutputStream = new FileOutputStream(sf);
            } catch (FileNotFoundException e) {
                throw new ExecutorException("Неверный путь к файлу", e);
            }
            GsonBuilder gsonBuilder =
                    new GsonBuilder()
                            .setPrettyPrinting()
                            .registerTypeAdapter(LocalDate.class, new LocalDateDeserializer())
                            .registerTypeAdapter(LocalDate.class, new LocalDateSerializer());
            Gson gson = gsonBuilder.create();
            String json = gson.toJson(editor.getCollection());
            try {
                fileOutputStream.write(json.getBytes());
            } catch (IOException e) {
                throw new ExecutorException("Ошибка записи данных.", e);
            }
            return new Response(true, "Сохранение успешно произведено.");
        }
        return new Response(false, "В коллекции нет элементов.");
    }
}
