package com.lab.common.exchange;

import com.lab.common.io.Input;
import com.lab.common.io.Output;
import com.lab.common.musicBand.MusicBand;
import com.lab.common.musicBand.MusicBandCreator;
import com.lab.common.user.User;
import com.lab.common.user.UserCreator;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Request implements Serializable {
    private String commandName;
    private MusicBand musicBand;
    private String commandParameter;
    private User user;

    public Request(String input, Input in, Output out) {
        user = new User();

        if (input == null || input.isEmpty()) {
            commandName = null;
            commandParameter = null;
        } else {
            List<String> values = getSubStrings(input);
            try {
                if (values.size() > 0) {
                    commandName = values.get(0).trim();
                    if (values.size() == 3) {
                        user.setLogin(values.get(1).trim());
                        user.setPassword(values.get(2).trim());
                    } else {
                        commandParameter = values.get(1).trim();
                        user.setLogin(values.get(2).trim());
                        user.setPassword(values.get(3).trim());
                    }
                }
            } catch (IndexOutOfBoundsException | NullPointerException e) {
                // If user decided not to input some values.
            }

            if (commandName != null) {
                if (commandName.equals("insert_key")
                        || commandName.equals("update_id")
                        || commandName.equals("replace_if_lower")) {
                    musicBand = new MusicBandCreator(in, out).create();
                }

                if (commandName.equals("register")) {
                    user = new UserCreator(in, out).create();
                }
            }
        }
    }

    private List<String> getSubStrings(String userInput) {
        List<String> matchList = new ArrayList<>();
        Matcher regexMatcher = Pattern.compile("[^\\s\"']+|\"([^\"]*)\"|'([^']*)'").matcher(userInput);

        while (regexMatcher.find()) {
            if (regexMatcher.group(1) != null) {
                matchList.add(regexMatcher.group(1));
            } else if (regexMatcher.group(2) != null) {
                matchList.add(regexMatcher.group(2));
            } else {
                matchList.add(regexMatcher.group());
            }
        }

        return matchList;
    }

    public User getUser() {
        return user;
    }

    public String getCommandName() {
        return commandName;
    }

    public String getCommandParameter() {
        return commandParameter;
    }

    public MusicBand getMusicBand() {
        return musicBand;
    }
}
