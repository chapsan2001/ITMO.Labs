package com.lab.server.command.commands;

import com.lab.common.exchange.Response;
import com.lab.common.user.User;
import com.lab.server.command.Command;
import com.lab.server.executor.Editor;

/**
 * Clear files history command class
 */
public final class ClearFilesHistoryCommand extends Command {
    public ClearFilesHistoryCommand(User user, Editor editor) {
        super(user, editor);
    }

    /**
     * Clears history of executes files with scripts
     *
     * @return response and correctness
     */
    @Override
    public Response execute() {
        if (!editor.isFromFile()) {
            if (editor.getFilesHashes().size() == 0) {
                return new Response(false, "История исполненных файлов со скриптами отсутствует.");
            }
            editor.clearFilesHashes();
            return new Response(true, "История исполненных файлов со скриптами очищен.");
        }
        return new Response(
                false,
                "История исполненных файлов со скриптами не может быть очищена с помощью скрипта из файла");
    }
}
