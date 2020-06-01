package com.lab.server.command;

import com.lab.common.exchange.Response;
import com.lab.common.user.User;
import com.lab.server.executor.Editor;

/**
 * Abstract class for creating commands
 */
public abstract class Command {
    protected final User user;
    protected final Editor editor;

    protected Command(User user, Editor editor) {
        this.user = user;
        this.editor = editor;
    }

    /**
     * Returns response about execution of the command
     *
     * @return Response about execution of the command
     */
    public abstract Response execute();
}
