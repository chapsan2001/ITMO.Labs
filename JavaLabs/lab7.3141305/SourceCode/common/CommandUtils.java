package com.lab.common;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class CommandUtils {
    public static List<String> getSubStrings(String userInput) {
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

    public static String getCommandName(String userInput) {
        String commandName;

        List<String> splitCommand = getSubStrings(userInput);
        if (splitCommand.size() > 0) {
            commandName = splitCommand.get(0);
        } else {
            commandName = null;
        }

        return commandName;
    }

    public static String getCommandArgument(String userInput) {
        String commandArgument;

        List<String> splitCommand = getSubStrings(userInput);
        if (splitCommand.size() > 1) {
            commandArgument =
                    splitCommand.stream().skip(1).map(String::valueOf).collect(Collectors.joining());
        } else {
            commandArgument = null;
        }

        return commandArgument;
    }
}
