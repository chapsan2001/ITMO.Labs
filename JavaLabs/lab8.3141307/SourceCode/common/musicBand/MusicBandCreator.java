package com.lab.common.musicBand;

import com.lab.common.io.Input;
import com.lab.common.io.Output;
import com.lab.common.musicBand.exceptions.ValidationException;

import java.util.Arrays;
import java.util.ResourceBundle;
import java.util.function.Predicate;

/** MusicBandCreator class creates element using input */
public class MusicBandCreator {
  Input in;
  Output out;

  /**
   * MusicBandCreator constructor
   *
   * @param in console reader
   * @param out console writer
   */
  public MusicBandCreator(Input in, Output out) {
    this.in = in;
    this.out = out;
  }

  /**
   * Prints a message, receives the value and checks its correctness
   *
   * @param message a message to print
   * @param required whether the parameter is required or not
   * @param lambda lambda expression to check input correctness
   * @return True if value is set or may be skipped
   */
  public boolean inputValue(String message, boolean required, Predicate<String> lambda) {
    while (true) {
      out.print(message);
      String input = in.readLine();
      if (input == null) {
        out.print(
            System.lineSeparator()
                + ResourceBundle.getBundle("MusicBandCreator").getString("format"));
        in = new Input();
      } else if (input.equals("cancel")) {
        out.println(ResourceBundle.getBundle("MusicBandCreator").getString("cancel"));
        return false;
      } else if (input.isEmpty()) {
        if (required) out.print(ResourceBundle.getBundle("MusicBandCreator").getString("required"));
        else return true;
      } else if (lambda.test(input)) {
        return true;
      }
    }
  }

  /**
   * Creates new element of the collection
   *
   * @return New element if add parameter is true else returns void
   */
  public MusicBand create() {
    out.println(ResourceBundle.getBundle("MusicBandCreator").getString("input"));
    MusicBand musicBand = new MusicBand();

    if (!inputValue(
        ResourceBundle.getBundle("MusicBandCreator").getString("name"),
        true,
        input -> {
          try {
            musicBand.setName(input);
            return true;
          } catch (ValidationException e) {
            out.print(
                String.format(
                    "%s %s ",
                    ResourceBundle.getBundle("MusicBandCreator").getString("wrong"),
                    e.getMessage()));
            return false;
          }
        })) return null;

    Coordinates coordinates = new Coordinates();

    if (!inputValue(
        ResourceBundle.getBundle("MusicBandCreator").getString("coordinates.x"),
        true,
        input -> {
          try {
            coordinates.setX(Long.parseLong(input));
            return true;
          } catch (NumberFormatException e) {
            out.print(ResourceBundle.getBundle("MusicBandCreator").getString("format"));
            return false;
          } catch (ValidationException e) {
            out.print(
                String.format(
                    "%s %s ",
                    ResourceBundle.getBundle("MusicBandCreator").getString("wrong"),
                    e.getMessage()));
            return false;
          }
        })) return null;

    if (!inputValue(
        ResourceBundle.getBundle("MusicBandCreator").getString("coordinates.y"),
        true,
        input -> {
          try {
            coordinates.setY(Float.parseFloat(input));
            return true;
          } catch (NumberFormatException e) {
            out.print(ResourceBundle.getBundle("MusicBandCreator").getString("format"));
            return false;
          } catch (ValidationException e) {
            out.print(
                String.format(
                    "%s %s ",
                    ResourceBundle.getBundle("MusicBandCreator").getString("wrong"),
                    e.getMessage()));
            return false;
          }
        })) return null;

    try {
      musicBand.setCoordinates(coordinates);
    } catch (ValidationException e) {
      out.print("Неверное значение. " + e.getMessage() + " ");
    }

    if (!inputValue(
        ResourceBundle.getBundle("MusicBandCreator").getString("numberOfParticipants"),
        true,
        input -> {
          try {
            musicBand.setNumberOfParticipants(Long.parseLong(input));
            return true;
          } catch (NumberFormatException e) {
            out.print(ResourceBundle.getBundle("MusicBandCreator").getString("format"));
            return false;
          } catch (ValidationException e) {
            out.print(
                String.format(
                    "%s %s ",
                    ResourceBundle.getBundle("MusicBandCreator").getString("wrong"),
                    e.getMessage()));
            return false;
          }
        })) return null;

    if (!inputValue(
        ResourceBundle.getBundle("MusicBandCreator").getString("genre")
            + Arrays.toString(MusicGenre.values())
            + ": ",
        false,
        input -> {
          try {
            musicBand.setGenre(MusicGenre.valueOf(input));
            return true;
          } catch (IllegalArgumentException e) {
            out.print(ResourceBundle.getBundle("MusicBandCreator").getString("wrong"));
            return false;
          }
        })) return null;

    Album bestAlbum = new Album();

    if (!inputValue(
        ResourceBundle.getBundle("MusicBandCreator").getString("album"),
        true,
        input -> {
          try {
            bestAlbum.setName(input);
            return true;
          } catch (ValidationException e) {
            out.print(
                String.format(
                    "%s %s ",
                    ResourceBundle.getBundle("MusicBandCreator").getString("wrong"),
                    e.getMessage()));
            return false;
          }
        })) return null;

    if (!inputValue(
        ResourceBundle.getBundle("MusicBandCreator").getString("numberOfTracks"),
        true,
        input -> {
          try {
            bestAlbum.setTracks(Integer.parseInt(input));
            return true;
          } catch (NumberFormatException e) {
            out.print("Неверный формат. ");
            return false;
          } catch (ValidationException e) {
            out.print(
                String.format(
                    "%s %s ",
                    ResourceBundle.getBundle("MusicBandCreator").getString("wrong"),
                    e.getMessage()));
            return false;
          }
        })) return null;

    if (!inputValue(
        ResourceBundle.getBundle("MusicBandCreator").getString("numberOfSales"),
        true,
        input -> {
          try {
            bestAlbum.setSales(Float.parseFloat(input));
            return true;
          } catch (NumberFormatException e) {
            out.print(ResourceBundle.getBundle("MusicBandCreator").getString("format"));
            return false;
          } catch (ValidationException e) {
            out.print(
                String.format(
                    "%s %s ",
                    ResourceBundle.getBundle("MusicBandCreator").getString("wrong"),
                    e.getMessage()));
            return false;
          }
        })) return null;

    musicBand.setBestAlbum(bestAlbum);

    return musicBand;
  }
}
