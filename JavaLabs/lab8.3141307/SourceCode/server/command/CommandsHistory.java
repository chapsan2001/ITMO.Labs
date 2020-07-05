package com.lab.server.command;

import java.util.ArrayList;
import java.util.List;

/** History of commands that executed correctly */
public final class CommandsHistory {
  private int maxSize;
  private List<String> commands;

  /** Default commands history constructor */
  public CommandsHistory() {
    maxSize = 100;
    commands = new ArrayList<>();
  }

  /**
   * Commands history constructor
   *
   * @param maxNumber maximum size of the history
   */
  public CommandsHistory(int maxNumber) {
    this.maxSize = maxNumber;
    commands = new ArrayList<>();
  }

  /**
   * Returns commands history
   *
   * @return Commands history
   */
  public List<String> getCommands() {
    return commands;
  }

  /**
   * Adds command to history
   *
   * @param command command
   */
  public void addCommand(String command) {
    if (commands.size() > maxSize) {
      commands.remove(0);
    }
    commands.add(command);
  }
}
