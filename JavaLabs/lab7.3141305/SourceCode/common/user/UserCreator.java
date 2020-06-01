package com.lab.common.user;

import com.lab.common.io.Input;
import com.lab.common.io.Output;

import java.util.function.Predicate;
import java.util.regex.Pattern;

/**
 * MusicCreator class creates element using input
 */
public class UserCreator {
    private final Output out;
    private Input in;

    public UserCreator(Input in, Output out) {
        this.in = in;
        this.out = out;
    }

    /**
     * Prints a message, receives the value and checks its correctness
     *
     * @param message  a message to print
     * @param required whether the parameter is required or not
     * @param lambda   lambda expression to check input correctness
     * @return True if value is set or may be skipped
     */
    public boolean inputValue(String message, boolean required, Predicate<String> lambda) {
        while (true) {
            out.print(message);
            String input = in.readLine();
            if (input == null) {
                out.print(System.lineSeparator() + "Неверный формат. ");
                in = new Input();
            } else if (input.equals("cancel")) {
                out.print("Отмена создания нового элемента");
                return false;
            } else if (input.equals("")) {
                if (required) out.print("Обязательный параметр. ");
                else return true;
            } else if (lambda.test(input)) {
                return true;
            }
        }
    }

    /**
     * Creates new element of the collection
     *
     * @return New element if add parameter is true else returns void
     */
    public User create() {
        out.println(
                "Введите необходимые данные или нажмите Enter для пропуска параметра, если это возможно (введите cancel для отмены)");
        User user = new User();

        if (!inputValue(
                "Введите логин: ",
                true,
                input -> {
                    if (Pattern.matches("[0-9А-Яа-яA-Za-z- ]+$", input)) {
                        user.setLogin(input);
                        return true;
                    }

                    out.print("Неверное значение. ");
                    return false;
                })) return null;

        if (!inputValue(
                "Введите пароль: ",
                true,
                input -> {
                    if (Pattern.matches("[0-9A-Za-z]+$", input)) {
                        user.setPassword(input);
                        return true;
                    }

                    out.print("Неверное значение. ");
                    return false;
                })) return null;

        return user;
    }
}
