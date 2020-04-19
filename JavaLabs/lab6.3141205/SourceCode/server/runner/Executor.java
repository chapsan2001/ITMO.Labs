package com.lab.server.runner;

import com.lab.common.exchange.Request;
import com.lab.common.exchange.Response;
import com.lab.common.io.Input;
import com.lab.common.io.Output;
import com.lab.server.command.AvailableCommands;
import com.lab.server.command.Command;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Executor class contains main methods that run others
 */
public final class Executor {
    private Input in;
    private Output out;
    private Editor editor;
    private AvailableCommands availableCommands;

    public Executor() {
        in = new Input();
        out = new Output();
        editor = new Editor();
        availableCommands = new AvailableCommands();
    }

    public Input getIn() {
        return in;
    }

    public void setIn(Input in) {
        this.in = in;
    }

    public Output getOut() {
        return out;
    }

    public void setOut(Output out) {
        this.out = out;
    }

    /**
     * Returns editor
     *
     * @return Editor
     */
    public Editor getEditor() {
        return editor;
    }

    public void setEditor(Editor editor) {
        this.editor = editor;
    }

    /**
     * Executes command by its name
     *
     * @param command command name to be executed
     * @return Response
     */
    public Response executeCommand(String command) {
        editor.setValue(null);
        String commandName;
        if (command == null) {
            commandName = "";
            out.print(System.lineSeparator());
        } else {
            String[] commandAndValue = command.split(" ", 2);
            commandName = commandAndValue[0];
            if (commandAndValue.length == 2) {
                editor.setValue(commandAndValue[1]);
            }
        }
        Command com = availableCommands.getCommand(commandName);
        return executeCom(com, command);
    }

    /**
     * Executes command
     *
     * @param request command to be executed
     * @return Response
     */
    public Response executeCommand(Request request) {
        editor.setValue(request.getCommandParameter());
        Command com = availableCommands.getCommand(request.getCommandName());
        return executeCom(com, request.getCommandName());
    }

    /**
     * Execute command if it is not null
     *
     * @param com command to execute
     * @return Response
     */
    private Response executeCom(Command com, String commandName) {
        List<String> response = new ArrayList<>();
        if (com == null) {
            response.add("Неверная команда. Введите help для отображения списка доступных команд.");
            return new Response(false, response);
        }
        Response commandResponse;
        try {
            commandResponse = com.execute(editor);
        } catch (ExecutorException e) {
            return new Response(false, "Ошибка: " + e.getMessage());
        }
        response.addAll(commandResponse.getResponse());
        if (commandResponse.isCorrect()) {
            editor.getCommandHistory().addCommand(commandName);
        }
        return new Response(true, response);
    }

    /**
     * Returns true if file path is correct
     *
     * @param args arguments with file name
     * @return True if file path is correct
     */
    public boolean setArgs(String[] args) {
        if (args.length == 0) {
            System.err.println("Необходим обязательный аргумент: Полное имя файла данных.");
            return false;
        }
        File file = new File(args[0]);
        if (file.isDirectory()) {
            out.println("Обнаружен путь к директории, а не к файлу.");
            return false;
        }
        if (!file.exists()) {
            out.println("Файл не найден.");
            return false;
        }
        if (!file.canRead()) {
            out.println("Ошибка доступа на чтение.");
            return false;
        }
        if (!file.canWrite()) {
            out.println("Ошибка доступа на запись.");
            return false;
        }
        editor.setDataFilePath(args[0]);
        return true;
    }
}
