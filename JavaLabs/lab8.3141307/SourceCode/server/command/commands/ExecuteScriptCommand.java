package com.lab.server.command.commands;

import com.lab.common.CommandNames;
import com.lab.common.CommandUtils;
import com.lab.common.exchange.Response;
import com.lab.common.io.Input;
import com.lab.common.user.User;
import com.lab.server.command.Command;
import com.lab.server.executor.Editor;

import java.io.File;
import java.io.FileNotFoundException;
import java.net.URL;
import java.nio.file.AccessDeniedException;
import java.util.*;
import java.util.regex.Pattern;

/** Execute script command class */
public final class ExecuteScriptCommand extends Command {
  public ExecuteScriptCommand(User user, Locale locale, Editor editor) {
    super(user, locale, editor);
  }

  /**
   * Execute script from file (local or net)
   *
   * @return Response and correctness
   */
  @Override
  public Response execute() {
    if (editor.getValue() == null) {
      return new Response(
          false, ResourceBundle.getBundle("ExecuteScriptCommand", locale).getString("needPath"));
    }
    Input in;
    if (Pattern.matches(
        "^(https?|ftp|file)://[-a-zA-Z0-9+&@#/%?=~_|!:,.;]*[-a-zA-Z0-9+&@#/%=~_|]",
        editor.getValue())) {
      try {
        in = new Input(new Scanner(new URL(editor.getValue().replaceAll("\\\\", "")).openStream()));
      } catch (AccessDeniedException ex) {
        return new Response(
            false,
            ResourceBundle.getBundle("ExecuteScriptCommand", locale).getString("accessError"));
      } catch (FileNotFoundException ex) {
        return new Response(
            false, ResourceBundle.getBundle("ExecuteScriptCommand", locale).getString("notFound"));
      } catch (Exception ex) {
        return new Response(
            false,
            ResourceBundle.getBundle("ExecuteScriptCommand", locale).getString("errorReading"));
      }
    } else if (Pattern.matches("(\\\\?([^/]*[/])*)([^/]+)", editor.getValue())) {
      try {
        in = new Input(new Scanner(new File(editor.getValue().replaceAll("\\\\", ""))));
      } catch (Exception ex) {
        return new Response(
            false,
            ResourceBundle.getBundle("ExecuteScriptCommand", locale).getString("errorReading"));
      }
    } else
      return new Response(
          false, ResourceBundle.getBundle("ExecuteScriptCommand", locale).getString("wrongName"));
    File file = new File(editor.getValue());
    if (editor.getFilesHashes().contains(file.getName().hashCode())) {
      return new Response(
          false,
          String.format(
              ResourceBundle.getBundle("ExecuteScriptCommand", locale).getString("recursion"),
              CommandNames.CLEAR_FILES_HISTORY));
    } else editor.getFilesHashes().add(file.getName().hashCode());
    editor.setIn(in);

    editor.setFromFile(true);
    editor.getExecutor().getEditor().update(editor);

    List<String> response = new ArrayList<>();
    for (String input = in.readLine(); input != null; input = in.readLine()) {
      editor.getExecutor().getEditor().setFromFile(true);

      String commandName = CommandUtils.getCommandName(input);
      String commandParameter = CommandUtils.getCommandArgument(input);

      response.addAll(
          editor
              .getExecutor()
              .executeCommand(commandName, commandParameter, user, locale)
              .getResponse());
    }

    editor.update(editor.getExecutor().getEditor());
    editor.setFromFile(false);

    return new Response(true, response);
  }
}
