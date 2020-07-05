package com.lab.server.command.commands;

import com.lab.common.CommandNames;
import com.lab.common.exchange.Response;
import com.lab.common.user.User;
import com.lab.server.command.Command;
import com.lab.server.executor.Editor;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;

/** Help command class */
public final class HelpCommand extends Command {
  public HelpCommand(User user, Locale locale, Editor editor) {
    super(user, locale, editor);
  }

  /**
   * Shows information about all available commands
   *
   * @return response and correctness
   */
  @Override
  public Response execute() {
    List<String> response = new ArrayList<>();

    response.add(ResourceBundle.getBundle("HelpCommand", locale).getString("available"));
    response.add("");
    response.add(
        CommandNames.HELP
            + ' '
            + ResourceBundle.getBundle("HelpCommand", locale).getString("help"));
    response.add(
        CommandNames.INFO
            + ' '
            + ResourceBundle.getBundle("HelpCommand", locale).getString("info"));
    response.add(
        CommandNames.SHOW
            + ' '
            + ResourceBundle.getBundle("HelpCommand", locale).getString("show"));
    response.add(
        CommandNames.INSERT_KEY
            + ' '
            + ResourceBundle.getBundle("HelpCommand", locale).getString("insertKey"));
    response.add(
        CommandNames.UPDATE_ID
            + ' '
            + ResourceBundle.getBundle("HelpCommand", locale).getString("updateId"));
    response.add(
        CommandNames.REMOVE_KEY
            + ' '
            + ResourceBundle.getBundle("HelpCommand", locale).getString("removeKey"));
    response.add(
        CommandNames.CLEAR
            + ' '
            + ResourceBundle.getBundle("HelpCommand", locale).getString("clear"));
    response.add(
        CommandNames.SAVE
            + ' '
            + ResourceBundle.getBundle("HelpCommand", locale).getString("save"));
    response.add(
        CommandNames.EXECUTE_SCRIPT
            + ' '
            + ResourceBundle.getBundle("HelpCommand", locale).getString("executeScript"));
    response.add(
        CommandNames.EXIT
            + ' '
            + ResourceBundle.getBundle("HelpCommand", locale).getString("exit"));
    response.add(
        CommandNames.HISTORY
            + ' '
            + ResourceBundle.getBundle("HelpCommand", locale).getString("history"));
    response.add(
        CommandNames.REPLACE_IF_LOWER
            + ' '
            + ResourceBundle.getBundle("HelpCommand", locale).getString("replaceIfLower"));
    response.add(
        CommandNames.REMOVE_LOWER
            + ' '
            + ResourceBundle.getBundle("HelpCommand", locale).getString("removeLowerKey"));
    response.add(
        CommandNames.REMOVE_ANY_BY_NUMBER_OF_PARTICIPANTS
            + ' '
            + ResourceBundle.getBundle("HelpCommand", locale)
                .getString("removeAnyByNumberOfParticipants"));
    response.add(
        CommandNames.FILTER_STARTS_WITH_NAME
            + ' '
            + ResourceBundle.getBundle("HelpCommand", locale).getString("filterStartsWithName"));
    response.add(
        CommandNames.PRINT_FIELD_DESCENDING_NUMBER_OF_PARTICIPANTS
            + ' '
            + ResourceBundle.getBundle("HelpCommand", locale)
                .getString("printFieldDescendingNumberOfParticipants"));
    response.add(
        CommandNames.REGISTER
            + ' '
            + ResourceBundle.getBundle("HelpCommand", locale).getString("help"));
    response.add(
        CommandNames.LOGIN
            + ' '
            + ResourceBundle.getBundle("HelpCommand", locale).getString("login"));
    response.add("");
    response.add(ResourceBundle.getBundle("HelpCommand", locale).getString("footer"));
    response.add(ResourceBundle.getBundle("HelpCommand", locale).getString("copyrighting"));

    return new Response(true, response);
  }
}
