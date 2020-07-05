package com.lab.server.command.commands;

import com.lab.common.exchange.Response;
import com.lab.common.user.User;
import com.lab.server.command.Command;
import com.lab.server.executor.Editor;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;

public class FilterStartsWithNameCommand extends Command {
  public FilterStartsWithNameCommand(User user, Locale locale, Editor editor) {
    super(user, locale, editor);
  }

  @Override
  public Response execute() {
    if (editor.getValue() == null) {
      return new Response(
          false,
          ResourceBundle.getBundle("FilterStartsWithNameCommand", locale).getString("needName"));
    }

    List<String> response = new ArrayList<>();

    if (editor.getCollection().getSize() > 0) {
      response.add(
          ResourceBundle.getBundle("FilterStartsWithNameCommand", locale).getString("filter")
              + ": "
              + editor.getValue());

      List<String> musicBands = new ArrayList<>();
      editor.getCollection().getEntrySet().stream()
          .filter(el -> el.getValue().getName().startsWith(editor.getValue()))
          .forEach(el -> musicBands.add(el.getKey() + ": " + el.getValue()));

      if (musicBands.size() == 0) {
        return new Response(
            false,
            ResourceBundle.getBundle("FilterStartsWithNameCommand", locale).getString("notFound"));
      }

      response.addAll(musicBands);

      return new Response(true, response);
    }
    return new Response(
        true,
        ResourceBundle.getBundle("FilterStartsWithNameCommand", locale).getString("notFound"));
  }
}
