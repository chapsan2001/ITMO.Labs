package com.lab.server.command.commands;

import com.lab.common.CommandNames;
import com.lab.common.CommandUtils;
import com.lab.common.exchange.Response;
import com.lab.common.io.Input;
import com.lab.common.user.User;
import com.lab.server.command.Command;
import com.lab.server.executor.Editor;

import java.io.File;
import java.io.FileNotFoundException;
import java.net.URL;
import java.nio.file.AccessDeniedException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Pattern;

/**
 * Execute script command class
 */
public final class ExecuteScriptCommand extends Command {
    public ExecuteScriptCommand(User user, Editor editor) {
        super(user, editor);
    }

    /**
     * Execute script from file (local or net)
     *
     * @return Response and correctness
     */
    @Override
    public Response execute() {
        if (editor.getValue() == null) {
            return new Response(false, "Требуется параметр: имя файла");
        }
        Input in;
        if (Pattern.matches(
                "^(https?|ftp|file)://[-a-zA-Z0-9+&@#/%?=~_|!:,.;]*[-a-zA-Z0-9+&@#/%=~_|]",
                editor.getValue())) {
            editor.getOut().println("Выполнение скрипта из файла (сеть): ");
            try {
                in = new Input(new Scanner(new URL(editor.getValue().replaceAll("\\\\", "")).openStream()));
            } catch (AccessDeniedException ex) {
                return new Response(false, "Доступ к файлу ограничен");
            } catch (FileNotFoundException ex) {
                return new Response(false, "Файл не найден");
            } catch (Exception ex) {
                return new Response(false, "Ошибка чтения файла");
            }
        } else if (Pattern.matches("(\\\\?([^/]*[/])*)([^/]+)", editor.getValue())) {
            editor.getOut().println("Выполнение скрипта из файла (локально): ");
            try {
                in = new Input(new Scanner(new File(editor.getValue().replaceAll("\\\\", ""))));
            } catch (Exception ex) {
                return new Response(false, "Ошибка чтения файла");
            }
        } else return new Response(false, "Неверное имя файла");
        File file = new File(editor.getValue());
        if (editor.getFilesHashes().contains(file.getName().hashCode())) {
            return new Response(
                    false,
                    "Скрипт уже исполнялся. Введите "
                            + CommandNames.CLEAR_FILES_HISTORY
                            + " для очистки истории исполненных файлов со скриптами");
        } else editor.getFilesHashes().add(file.getName().hashCode());
        editor.setIn(in);

        editor.setFromFile(true);
        editor.getExecutor().getEditor().update(editor);

        List<String> response = new ArrayList<>();
        for (String input = in.readLine(); input != null; input = in.readLine()) {
            editor.getExecutor().getEditor().setFromFile(true);

            String commandName = CommandUtils.getCommandName(input);
            String commandParameter = CommandUtils.getCommandArgument(input);

            response.addAll(
                    editor.getExecutor().executeCommand(commandName, commandParameter, user).getResponse());
        }

        editor.update(editor.getExecutor().getEditor());
        editor.setFromFile(false);

        return new Response(true, response);
    }
}
