package com.lab.common.exchange;

import com.lab.common.io.Input;
import com.lab.common.io.Output;
import com.lab.common.musicBand.MusicBand;
import com.lab.common.musicBand.MusicBandCreator;

import java.io.Serializable;

public class Request implements Serializable {
    private String commandName;
    private MusicBand musicBand;
    private String commandParameter;
    public Request(String command) {
        if (command == null) {
            commandName = null;
            commandParameter = null;
        } else {
            String[] values = command.split(" ", 2);
            commandName = values[0];
            if (values.length == 2) commandParameter = values[1];

            if (commandName.equals("insert_key")
                    || commandName.equals("update_id")
                    || commandName.equals("replace_if_lower")) {
                musicBand = new MusicBandCreator(new Input(), new Output()).create();
            }
        }
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
