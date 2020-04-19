package com.lab.server.command.commands;

import com.lab.common.exchange.Response;
import com.lab.server.command.Command;
import com.lab.server.runner.Editor;

import java.util.ArrayList;
import java.util.List;

public final class HelpCommand implements Command {
    @Override
    public Response execute(Editor editor) {
        List<String> response = new ArrayList<>();
        response.add(">>>ДОСТУПНЫЕ КОМАНДЫ<<<");
        response.add("help: вывести справку по доступным командам");
        response.add(
                "info: вывести в стандартный поток вывода информацию о коллекции (тип, дата инициализации, количество элементов и т.д.)");
        response.add(
                "show: вывести в стандартный поток вывода все элементы коллекции в строковом представлении");
        response.add("insert_key {element}: добавить новый элемент с заданным ключом");
        response.add(
                "update_id {element}: обновить значение элемента коллекции, id которого равен заданному");
        response.add("remove_key {key}: удалить элемент из коллекции по его ключу");
        response.add("clear: очистить коллекцию");
        response.add("save: сохранить коллекцию в файл");
        response.add(
                "execute_script {file_name}: считать и исполнить скрипт из указанного файла. В скрипте содержатся команды в таком же виде, в котором их вводит пользователь в интерактивном режиме.");
        response.add(
                "exit: завершить программу без сохранения в файл (Комбинация клавиш CTRL+D так же завершает выполнение программы)");
        response.add("history: вывести последние 14 команд (без их аргументов)");
        response.add(
                "replace_if_lower {element}: заменить значение по ключу, если новое значение меньше старого");
        response.add(
                "remove_lower_key {key}: удалить из коллекции все элементы, ключ которых меньше, чем заданный");
        response.add(
                "remove_any_by_number_of_participants {numberOfParticipants}: удалить из коллекции один элемент, значение поля numberOfParticipants которого эквивалентно заданному");
        response.add(
                "filter_starts_with_name {name}: вывести элементы, значение поля name которых начинается с заданной подстроки");
        response.add(
                "print_field_descending_number_of_participants {numberOfParticipants}: вывести значения поля numberOfParticipants в порядке убывания");
        response.add("");
        response.add("Сделано на Уралмаше с любовью для Купчино.");
        response.add("2020 (с) chapsan2001");
        return new Response(true, response);
    }
}
