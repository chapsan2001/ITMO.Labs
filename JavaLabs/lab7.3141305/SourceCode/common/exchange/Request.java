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

public class Request implements Serializable {
    private final String commandName;
    private final String commandParameter;
    private final User user;
    private MusicBand musicBand;

    public Request(User user, String input, Input in, Output out) {
        this.user = user;

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

    public MusicBand getMusicBand() {
        return musicBand;
    }
}
