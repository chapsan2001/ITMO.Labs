package com.lab.common.musicBand;

import com.lab.common.io.Input;
import com.lab.common.io.Output;
import com.lab.common.musicBand.exceptions.ValidationException;

import java.util.Arrays;
import java.util.function.Predicate;

/**
 * MusicBandCreator class creates element using input
 */
public class MusicBandCreator {
    Input in;
    Output out;

    /**
     * MusicBandCreator constructor
     *
     * @param in  console reader
     * @param out console writer
     */
    public MusicBandCreator(Input in, Output out) {
        this.in = in;
        this.out = out;
    }

    /**
     * Prints a message, receives the value and checks its correctness
     *
     * @param message  a message to print
     * @param required whether the parameter is required or not
     * @param lambda   lambda expression to check input correctness
     * @return True if value is set or may be skipped
     */
    public boolean inputValue(String message, boolean required, Predicate<String> lambda) {
        while (true) {
            out.print(message);
            String input = in.readLine();
            if (input == null) {
                out.print(System.lineSeparator() + "Неверный формат. ");
                in = new Input();
            } else if (input.equals("cancel")) {
                out.println("Отмена создания нового элемента");
                return false;
            } else if (input.equals("")) {
                if (required) out.print("Обязательный параметр. ");
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
        out.println(
                "Введите необходимые данные или нажмите Enter для пропуска параметра, если это возможно (введите cancel для отмены)");
        MusicBand musicBand = new MusicBand();

        if (!inputValue(
                "Введите имя: ",
                true,
                input -> {
                    try {
                        musicBand.setName(input);
                        return true;
                    } catch (ValidationException e) {
                        out.print(String.format("%s %s ", "Неверное значение.", e.getMessage()));
                        return false;
                    }
                })) return null;

        Coordinates coordinates = new Coordinates();

        if (!inputValue(
                "Введите x: ",
                true,
                input -> {
                    try {
                        coordinates.setX(Long.parseLong(input));
                        return true;
                    } catch (NumberFormatException e) {
                        out.print("Неверный формат. ");
                        return false;
                    } catch (ValidationException e) {
                        out.print(String.format("%s %s ", "Неверное значение.", e.getMessage()));
                        return false;
                    }
                })) return null;

        if (!inputValue(
                "Введите y: ",
                true,
                input -> {
                    try {
                        coordinates.setY(Float.parseFloat(input));
                        return true;
                    } catch (NumberFormatException e) {
                        out.print("Неверный формат. ");
                        return false;
                    } catch (ValidationException e) {
                        out.print(String.format("%s %s ", "Неверное значение.", e.getMessage()));
                        return false;
                    }
                })) return null;

        try {
            musicBand.setCoordinates(coordinates);
        } catch (ValidationException e) {
            out.print("Неверное значение. " + e.getMessage() + " ");
        }

        if (!inputValue(
                "Введите количество участников: ",
                true,
                input -> {
                    try {
                        musicBand.setNumberOfParticipants(Long.parseLong(input));
                        return true;
                    } catch (NumberFormatException e) {
                        out.print("Неверный формат. ");
                        return false;
                    } catch (ValidationException e) {
                        out.print(String.format("%s %s ", "Неверное значение.", e.getMessage()));
                        return false;
                    }
                })) return null;

        if (!inputValue(
                "Введите жанр " + Arrays.toString(MusicGenre.values()) + ": ",
                false,
                input -> {
                    try {
                        musicBand.setGenre(MusicGenre.valueOf(input));
                        return true;
                    } catch (IllegalArgumentException e) {
                        out.print("Неверное значение. ");
                        return false;
                    }
                })) return null;

        Album bestAlbum = new Album();

        if (!inputValue(
                "Введите название альбома: ",
                true,
                input -> {
                    try {
                        bestAlbum.setName(input);
                        return true;
                    } catch (ValidationException e) {
                        out.print(String.format("%s %s ", "Неверное значение.", e.getMessage()));
                        return false;
                    }
                })) return null;

        if (!inputValue(
                "Введите количество треков: ",
                true,
                input -> {
                    try {
                        bestAlbum.setTracks(Integer.parseInt(input));
                        return true;
                    } catch (NumberFormatException e) {
                        out.print("Неверный формат. ");
                        return false;
                    } catch (ValidationException e) {
                        out.print(String.format("%s %s ", "Неверное значение.", e.getMessage()));
                        return false;
                    }
                })) return null;

        if (!inputValue(
                "Введите количество продаж: ",
                true,
                input -> {
                    try {
                        bestAlbum.setSales(Float.parseFloat(input));
                        return true;
                    } catch (NumberFormatException e) {
                        out.print("Неверный формат. ");
                        return false;
                    } catch (ValidationException e) {
                        out.print(String.format("%s %s ", "Неверное значение.", e.getMessage()));
                        return false;
                    }
                })) return null;

        musicBand.setBestAlbum(bestAlbum);

        return musicBand;
    }
}
