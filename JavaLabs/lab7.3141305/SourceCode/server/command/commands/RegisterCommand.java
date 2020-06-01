package com.lab.server.command.commands;

import com.lab.common.exchange.Response;
import com.lab.common.user.User;
import com.lab.common.user.hashGenerator.exceptions.HashGeneratorException;
import com.lab.server.command.Command;
import com.lab.server.executor.Editor;

/**
 * Info command class
 */
public final class RegisterCommand extends Command {
    public RegisterCommand(User user, Editor editor) {
        super(user, editor);
    }

    /**
     * Shows info about collection
     *
     * @return Response and correctness
     */
    @Override
    public Response execute() {
        if (user == null) {
            return new Response(false);
        }

        if (editor.getUserDAO().getByLogin(user.getLogin()) != null) {
            return new Response(false, "Пользователь уже зарегестрирован");
        }

        try {
            user.setPassword(editor.getHashGenerator().generateHashWithPepperAndSalt(user.getPassword()));
        } catch (HashGeneratorException e) {
            return new Response(true, "Внутренняя ошибка регистрации");
        }

        editor.getUserDAO().insert(user);

        return new Response(true, "Пользователь успешно зарегестрирован");
    }
}
