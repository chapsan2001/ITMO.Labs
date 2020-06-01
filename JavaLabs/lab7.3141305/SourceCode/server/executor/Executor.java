package com.lab.server.executor;

import com.lab.common.exchange.Request;
import com.lab.common.exchange.Response;
import com.lab.common.user.User;
import com.lab.common.user.hashGenerator.exceptions.HashGeneratorException;
import com.lab.server.command.Command;
import com.lab.server.command.Commands;
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

    private Response authenticateUser(User user) {
        if (user.getLogin() == null) {
            return new Response(
                    false,
                    "Данные пользователя не найдены. Введите логин и пароль после аргументов команды.");
        }

        User findUser = editor.getUserDAO().getByLogin(user.getLogin());

        if (findUser == null) {
            return new Response(
                    false,
                    "Пользователь не зарегестрирован. Для регисртрации использоуйте команду register.");
        }

        try {
            if (!findUser
                    .getPassword()
                    .equals(editor.getHashGenerator().generateHashWithPepperAndSalt(user.getPassword()))) {
                return new Response(false, "Неверный пароль пользователя");
            }
        } catch (HashGeneratorException e) {
            return new Response(false, "Внутрення ошибка аутентификации");
        }

        user.setId(findUser.getId());
        return new Response(true);
    }

    /**
     * Returns editor
     *
     * @return Editor
     */
    public Editor getEditor() {
        return editor;
    }

    public Commands getCommands() {
        return commands;
    }

    /**
     * Executes command by its name
     *
     * @param command command name to be executed
     * @param user    concrete user
     * @return Response
     */
    public Response executeCommand(String command, User user) {
        editor.setValue(null);
        String commandName;

        if (command == null) {
            return new Response(
                    false, "Неверная команда. Введите help для отображения списка доступных команд.");
        } else {
            String[] commandAndValue = command.split(" ", 2);
            commandName = commandAndValue[0];
            if (commandAndValue.length == 2) {
                editor.setValue(commandAndValue[1]);
            }
        }

        if (!command.equals("register") && !command.equals("help")) {
            Response authenticationResponse = authenticateUser(user);
            if (!authenticationResponse.isCorrect()) {
                return authenticationResponse;
            }
        }

        Command com;

        try {
            if (!commands.getCommands().containsKey(commandName)) {
                return new Response(
                        false, "Неверная команда. Введите help для отображения списка доступных команд.");
            }

            com = commandFactory.createCommand(commandName, user);
        } catch (CommandFactoryException e) {
            return new Response(false, "Ошибка создания команды");
        }

        return executeCom(com, command);
    }

    /**
     * Executes command by its name
     *
     * @param request request with command name to be executed
     * @return Response
     */
    public Response executeCommand(Request request) {
        if (request.getCommandName() == null) {
            return new Response(
                    false, "Неверная команда. Введите help для отображения списка доступных команд.");
        }

        if (!request.getCommandName().equals("register") && !request.getCommandName().equals("help")) {
            Response authenticationResponse = authenticateUser(request.getUser());
            if (!authenticationResponse.isCorrect()) {
                return authenticationResponse;
            }
        }

        String commandName = request.getCommandName();

        editor.setValue(request.getCommandParameter());

        Command com;

        try {
            if (!commands.getCommands().containsKey(request.getCommandName())) {
                return new Response(
                        false, "Неверная команда. Введите help для отображения списка доступных команд.");
            }

            com = commandFactory.createCommand(request.getCommandName(), request.getUser());
        } catch (CommandFactoryException e) {
            return new Response(false, "Ошибка создания команды");
        }

        return executeCom(com, commandName);
    }

    /**
     * Execute command if it is not null
     *
     * @param com command to execute
     * @return Response
     */
    private Response executeCom(Command com, String commandName) {
        Response commandResponse;
        commandResponse = com.execute();
        if (commandResponse.isCorrect()) {
            editor.getCommandHistory().addCommand(commandName);
        }
        return new Response(true, commandResponse.getResponse());
    }
}
