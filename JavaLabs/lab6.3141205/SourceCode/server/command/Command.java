package com.lab.server.command;

import com.lab.common.exchange.Response;
import com.lab.server.runner.Editor;
import com.lab.server.runner.ExecutorException;
public interface Command {
    Response execute(Editor editor) throws ExecutorException;
}
