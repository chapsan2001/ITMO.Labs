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
public final class NotLoggedInHelpCommand extends Command {
    public NotLoggedInHelpCommand(User user, Editor editor) {
        super(user, editor);
    }

    /**
     * Shows information about all available commands
     *
     * @return Response and correctness
     */
    @Override
    public Response execute() {
        List<String> response = new ArrayList<>();

        response.add(CommandNames.HELP + " : вывести справку по доступным командам");
        response.add(CommandNames.REGISTER + " : зарегестрироваться, как новый пользователь");
        response.add(CommandNames.LOGIN + " : авторизваться, данные пользователя сохраняются");

        return new Response(true, response);
    }
}
