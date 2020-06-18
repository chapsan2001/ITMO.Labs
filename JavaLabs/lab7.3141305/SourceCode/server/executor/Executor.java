package com.lab.server.executor;

import com.lab.common.CommandNames;
import com.lab.common.exchange.Request;
import com.lab.common.exchange.Response;
import com.lab.common.user.User;
import com.lab.common.user.hashGenerator.exceptions.HashGeneratorException;
import com.lab.server.command.Command;
import com.lab.server.command.Commands;
import com.lab.server.command.commands.NotLoggedInHelpCommand;
import com.lab.server.command.factory.CommandFactory;
import com.lab.server.command.factory.CommandFactoryException;
import com.lab.server.storage.dataSource.DataSource;
import com.lab.server.storage.dataSource.database.Database;

/**
 * Executor class contains main methods that run others
 */
public final class Executor {
    private final DataSource dataSource;
    private final Editor editor;
    private final CommandFactory commandFactory;
    private final Commands commands;

    public Executor(String user, String password) {
        commands = new Commands();
        dataSource = new Database(user, password);
        editor = new Editor(dataSource, this);
        commandFactory = new CommandFactory(editor, commands);
    }

    public DataSource getDataSource() {
        return dataSource;
    }

    public Editor getEditor() {
        return editor;
    }

    public Commands getCommands() {
        return commands;
    }

    private Response authenticateUser(User user) {
        if (user == null || user.getLogin() == null || user.getPassword() == null) {
            return new Response(
                    false, "Данные пользователя не найдены. Для авторизации используйте команду login.");
        }

        User findUser = editor.getUserDAO().getByLogin(user.getLogin());

        if (findUser == null) {
            return new Response(
                    false,
                    "Пользователь не зарегестрирован. Для регисртрации использоуйте команду register.");
        }

        try {
            String s =
                    editor
                            .getHashGenerator()
                            .generateHashWithPepperAndSalt(user.getPassword()); // TODO: Debug.
        } catch (HashGeneratorException e) {
            e.printStackTrace();
        }

        String hashedPassword;
        try {
            hashedPassword = editor.getHashGenerator().generateHashWithPepperAndSalt(user.getPassword());
        } catch (HashGeneratorException e) {
            return new Response(false, "Внутрення ошибка аутентификации.");
        }

        if (!findUser.getPassword().equals(hashedPassword)) {
            return new Response(false, "Неверный пароль пользователя.");
        }

        user.setId(findUser.getId());
        return new Response(true);
    }

    /**
     * Executes command using name, parameter, and user.
     *
     * @param command         concrete command.
     * @param commandArgument command argument.
     * @param user            concrete user.
     * @return Response from execution.
     */
    public Response executeCommand(String command, String commandArgument, User user) {
        if (command == null || !commands.getCommands().containsKey(command)) {
            return new Response(
                    false,
                    "Неверная команда. Введите "
                            + CommandNames.HELP
                            + " для отображения списка доступных команд.");
        }

        Response authenticationResponse = authenticateUser(user);

        if (!command.equals(CommandNames.REGISTER)) {
            if (!authenticationResponse.isCorrect() && command.equals(CommandNames.HELP)) {
                return new NotLoggedInHelpCommand(user, editor).execute();
            }

            if (!authenticationResponse.isCorrect()) {
                return authenticationResponse;
            }
        }

        editor.setValue(commandArgument);

        Command com;

        try {
            com = commandFactory.createCommand(command, user);
        } catch (CommandFactoryException e) {
            return new Response(false, "Ошибка создания команды");
        }

        Response commandResponse = com.execute();

        if (commandResponse.isCorrect()) {
            editor.getCommandHistory().addCommand(command);
        }

        return new Response(true, commandResponse.getResponse());
    }

    /**
     * Executes command by request.
     *
     * @param request request with command name to be executed
     * @return Response
     */
    public Response executeCommand(Request request) {
        if (request == null) {
            return new Response(
                    false,
                    "Неверная команда. Введите "
                            + CommandNames.HELP
                            + " для отображения списка доступных команд.");
        }

        return executeCommand(
                request.getCommandName(), request.getCommandParameter(), request.getUser());
    }
}
