package com.lab.server.command.commands;

import com.lab.common.exchange.Response;
import com.lab.server.command.Command;
import com.lab.server.runner.Editor;
import com.lab.server.runner.ExecutorException;

public final class ClearFilesHistoryCommand implements Command {
    @Override
    public Response execute(Editor editor) throws ExecutorException {
        if (!editor.getFromFile()) {
            if (editor.getFilesHashes().size() == 0) {
                return new Response(false, "История исполненных файлов со скриптами отсутствует.");
            }
            editor.clearFilesHashes();
            return new Response(true, "История исполненных файлов со скриптами очищена.");
        }
        throw new ExecutorException(
                "История исполненных файлов со скриптами не может быть очищена с помощью скрипта из файла");
    }
}
