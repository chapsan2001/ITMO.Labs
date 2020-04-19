package com.lab.server.command.commands;

import com.lab.common.exchange.Response;
import com.lab.common.io.Input;
import com.lab.server.command.Command;
import com.lab.server.runner.Editor;
import com.lab.server.runner.Executor;
import com.lab.server.runner.ExecutorException;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStreamReader;
import java.net.URL;
import java.nio.file.AccessDeniedException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Pattern;

public final class ExecuteScriptCommand implements Command {
    @Override
    public Response execute(Editor editor) throws ExecutorException {
        List<String> response = new ArrayList<>();
        if (editor.getValue() == null) {
            throw new ExecutorException("Требуется параметр: имя файла");
        }
        Input in;
        if (Pattern.matches(
                "^(https?|s?ftp|file)://[-a-zA-Z0-9+&@#/%?=~_|!:,.;]*[-a-zA-Z0-9+&@#/%=~_|]",
                editor.getValue())) {
            response.add("Выполнение скрипта из файла (сеть): ");
            try {
                in =
                        new Input(
                                new Scanner(
                                        new InputStreamReader(
                                                new URL(editor.getValue().replaceAll("\\\\", "")).openStream())));
            } catch (AccessDeniedException e) {
                throw new ExecutorException("Доступ к файлу ограничен");
            } catch (FileNotFoundException e) {
                throw new ExecutorException("Файл не найден");
            } catch (Exception e) {
                throw new ExecutorException("Ошибка чтения файла");
            }
        } else if (Pattern.matches("(\\\\?([^/]*[/])*)([^/]+)", editor.getValue())) {
            response.add("Выполнение скрипта из файла (локально): ");
            try {
                in =
                        new Input(
                                new Scanner(
                                        new InputStreamReader(
                                                new FileInputStream(editor.getValue().replaceAll("\\\\", "")))));
            } catch (Exception e) {
                throw new ExecutorException("Ошибка чтения файла");
            }
        } else {
            throw new ExecutorException("Неверное имя файла");
        }
        File file = new File(editor.getValue());
        if (editor.getFilesHashes().contains(file.getName().hashCode())) {
            throw new ExecutorException(
                    "Скрипт уже исполнялся. Введите clear_files_history для очистки истории исполненных файлов со скриптами");
        } else {
            editor.getFilesHashes().add(file.getName().hashCode());
        }
        Executor executor = new Executor();
        editor.setIn(in);
        executor.setEditor(editor);
        editor.setFromFile(true);
        for (String command = in.readLine(); command != null; command = in.readLine()) {
            editor.setFromFile(true);
            response.addAll(executor.executeCommand(command).getResponse());
        }
        editor.setFromFile(false);
        return new Response(true, response);
    }
}
