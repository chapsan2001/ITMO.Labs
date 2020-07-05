package com.lab.common.io;

import java.io.BufferedOutputStream;
import java.io.IOException;

/** Output class */
public class Output {
  private final BufferedOutputStream writer;

  /** Default Writer constructor. Takes System.out */
  public Output() {
    writer = new BufferedOutputStream(System.out);
  }

  /**
   * Writer constructor
   *
   * @param writer writer to write to
   */
  public Output(BufferedOutputStream writer) {
    this.writer = writer;
  }

  /**
   * Prints message with and new line
   *
   * @param message message to print
   */
  public void println(String message) {
    try {
      byte[] buffer = (message + System.lineSeparator()).getBytes();
      writer.write(buffer, 0, buffer.length);
      writer.flush();
    } catch (IOException ignored) {
    }
  }

  /** Prints and new line */
  public void println() {
    try {
      byte[] buffer = System.lineSeparator().getBytes();
      writer.write(buffer, 0, buffer.length);
      writer.flush();
    } catch (IOException ignored) {
    }
  }

  /**
   * Prints message
   *
   * @param message message to print
   */
  public void print(String message) {
    try {
      byte[] buffer = message.getBytes();
      writer.write(buffer, 0, buffer.length);
      writer.flush();
    } catch (IOException ignored) {
    }
  }
}
