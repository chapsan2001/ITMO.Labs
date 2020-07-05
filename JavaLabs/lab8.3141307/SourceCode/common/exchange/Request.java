package com.lab.common.exchange;

import com.lab.common.CommandNames;
import com.lab.common.CommandUtils;
import com.lab.common.io.Input;
import com.lab.common.io.Output;
import com.lab.common.musicBand.MusicBand;
import com.lab.common.musicBand.MusicBandCreator;
import com.lab.common.user.User;
import com.lab.common.user.UserCreator;

import java.io.Serializable;
import java.util.Locale;

public class Request implements Serializable {
  private final String commandName;
  private final String commandParameter;
  private final User user;
  private final Locale locale;
  private MusicBand musicBand;

  public Request(
      String commandName, String commandParameter, User user, Locale locale, MusicBand musicBand) {
    this.commandName = commandName;
    this.commandParameter = commandParameter;
    this.user = user;
    this.locale = locale;
    this.musicBand = musicBand;
  }

  public Request(User user, String input, Input in, Output out) {
    this.user = user;
    this.locale = Locale.getDefault();

    if (input == null) {
      commandName = null;
      commandParameter = null;
    } else {
      commandName = CommandUtils.getCommandName(input);
      commandParameter = CommandUtils.getCommandArgument(input);

      if (commandName != null) {
        if (commandName.equals(CommandNames.INSERT_KEY)
            || commandName.equals(CommandNames.UPDATE_ID)
            || commandName.equals(CommandNames.REPLACE_IF_LOWER)) {
          this.musicBand = new MusicBandCreator(in, out).create();
        } else if (commandName.equals(CommandNames.REGISTER)
            || commandName.equals(CommandNames.LOGIN)) {
          User newUser = new UserCreator(in, out).create();
          if (newUser != null) {
            this.user.setLogin(newUser.getLogin());
            this.user.setPassword(newUser.getPassword());
          }
        }
      }
    }
  }

  public String getCommandName() {
    return commandName;
  }

  public String getCommandParameter() {
    return commandParameter;
  }

  public User getUser() {
    return user;
  }

  public Locale getLocale() {
    return locale;
  }

  public MusicBand getMusicBand() {
    return musicBand;
  }
}
