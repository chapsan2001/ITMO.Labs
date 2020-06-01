package com.lab.common.io;

import java.io.InputStreamReader;
import java.util.NoSuchElementException;
import java.util.Scanner;

/**
 * Input class
 */
public class Input {
    private final Scanner reader;

    /**
     * Default Reader constructor. Takes System.in
     */
    public Input() {
        reader = new Scanner(new InputStreamReader(System.in));
    }

    /**
     * Reader constructor
     *
     * @param reader reader to read from
     */
    public Input(Scanner reader) {
        this.reader = reader;
    }

    /**
     * Reads and processes a line
     *
     * @return Line
     */
    public String readLine() {
        try {
            return reader.nextLine().trim().replaceAll(" +", " ");
        } catch (NoSuchElementException | NullPointerException e) {
            return null;
        }
    }
}
