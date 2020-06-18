package com.lab.server.command.commands;

import com.lab.common.CommandNames;
import com.lab.common.exchange.Response;
import com.lab.common.user.User;
import com.lab.server.command.Command;
import com.lab.server.executor.Editor;

import java.util.ArrayList;
import java.util.List;

/**
 * Help command class
 */
public final class HelpCommand extends Command {
    public HelpCommand(User user, Editor editor) {
        super(user, editor);
    }

    /**
     * Shows information about all available commands
     *
     * @return response and correctness
     */
    @Override
    public Response execute() {
        List<String> response = new ArrayList<>();

        response.add(">>>ДОСТУПНЫЕ КОМАНДЫ<<<");
        response.add(CommandNames.HELP + ": вывести справку по доступным командам");
        response.add(
                CommandNames.INFO
                        + ": вывести в стандартный поток вывода информацию о коллекции (тип, дата инициализации, количество элементов и т.д.)");
        response.add(
                CommandNames.SHOW
                        + ": вывести в стандартный поток вывода все элементы коллекции в строковом представлении");
        response.add(CommandNames.INSERT_KEY + " {element}: добавить новый элемент с заданным ключом");
        response.add(
                CommandNames.UPDATE_ID
                        + " {element}: обновить значение элемента коллекции, id которого равен заданному");
        response.add(CommandNames.REMOVE_KEY + " {key}: удалить элемент из коллекции по его ключу");
        response.add(CommandNames.CLEAR + ": очистить коллекцию");
        response.add(CommandNames.SAVE + ": сохранить коллекцию в файл");
        response.add(
                CommandNames.EXECUTE_SCRIPT
                        + " {file_name}: считать и исполнить скрипт из указанного файла. В скрипте содержатся команды в таком же виде, в котором их вводит пользователь в интерактивном режиме.");
        response.add(
                CommandNames.EXIT
                        + ": завершить программу без сохранения в файл (Комбинация клавиш CTRL+D так же завершает выполнение программы)");
        response.add(CommandNames.HISTORY + ": вывести последние 14 команд (без их аргументов)");
        response.add(
                CommandNames.REPLACE_IF_LOWER
                        + " {element}: заменить значение по ключу, если новое значение меньше старого");
        response.add(
                CommandNames.REMOVE_LOWER_KEY
                        + " {key}: удалить из коллекции все элементы, ключ которых меньше, чем заданный");
        response.add(
                CommandNames.REMOVE_ANY_BY_NUMBER_OF_PARTICIPANTS
                        + " {numberOfParticipants}: удалить из коллекции один элемент, значение поля numberOfParticipants которого эквивалентно заданному");
        response.add(
                CommandNames.FILTER_STARTS_WITH_NAME
                        + " {name}: вывести элементы, значение поля name которых начинается с заданной подстроки");
        response.add(
                CommandNames.PRINT_FIELD_DESCENDING_NUMBER_OF_PARTICIPANTS
                        + " {numberOfParticipants}: вывести значения поля numberOfParticipants в порядке убывания");
        response.add(CommandNames.REGISTER + " : зарегестрироваться, как новый пользователь");
        response.add(CommandNames.LOGIN + " : авторизваться, данные пользователя сохраняются");
        response.add("");
        response.add(
                "формат команд, для которых необходима авторизация: команда аргумент логин пароль");

        response.add("");
        response.add("Сделано на Уралмаше с любовью для Купчино.");
        response.add("2020 (с) chapsan2001");

        return new Response(true, response);
    }
}
