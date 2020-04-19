package com.lab.server.runner;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonSyntaxException;
import com.lab.common.adapters.LocalDateDeserializer;
import com.lab.common.adapters.LocalDateSerializer;
import com.lab.common.exchange.Response;
import com.lab.common.io.Input;
import com.lab.common.io.Output;
import com.lab.common.musicBand.MusicBand;
import com.lab.common.musicBand.MusicBandCreator;
import com.lab.common.musicBand.exceptions.ValidationException;
import com.lab.server.collection.Collection;
import com.lab.server.command.CommandsHistory;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Scanner;

/**
 * Editor class contains data to be passed to command
 */
public final class Editor {
    private Input in;
    private Output out;
    private MusicBand musicBand;
    private String value;
    private boolean running;
    private boolean fromFile;
    private String dataFilePath;
    private Collection collection;
    private List<Integer> filesHashes;
    private CommandsHistory commandsHistory;

    public Editor() {
        value = null;
        running = true;
        fromFile = false;
        in = new Input();
        out = new Output();
        collection = new Collection();
        filesHashes = new ArrayList<>();
        commandsHistory = new CommandsHistory();
    }

    public String getDataFilePath() {
        return dataFilePath;
    }

    /**
     * Sets data file path
     *
     * @param dataFilePath data file path
     */
    public void setDataFilePath(String dataFilePath) {
        this.dataFilePath = dataFilePath;
    }

    /**
     * Метод, отвечающий за заполнение коллекции из файла.
     *
     * @return Response about correctness
     */
    public Response load() {
        try {
            File file = new File(dataFilePath);
            Scanner fileScanner = new Scanner(new FileInputStream(file));
            fileScanner.useDelimiter("\\Z");
            String data = fileScanner.next();
            Gson gson =
                    new GsonBuilder()
                            .registerTypeAdapter(LocalDate.class, new LocalDateSerializer())
                            .registerTypeAdapter(LocalDate.class, new LocalDateDeserializer())
                            .create();
            setCollection(gson.fromJson(data, Collection.class));
        } catch (IOException e) {
            return new Response(false, "Ошибка чтения данных");
        } catch (NoSuchElementException | JsonSyntaxException e) {
            return new Response(false, "Неверный формат данных");
        }
        return new Response(true, "Файл загружен");
    }

    public MusicBand getMusicBand() throws ValidationException {
        if (fromFile) {
            musicBand = new MusicBandCreator(in, out).create();
        }
        musicBand.setId(collection.getSize() + 1);
        musicBand.setCreationDate(LocalDate.now());
        return musicBand;
    }

    public void setMusicBand(MusicBand musicBand) {
        this.musicBand = musicBand;
    }

    /**
     * Returns true if read is from file
     *
     * @return True if read is from file
     */
    public boolean getFromFile() {
        return fromFile;
    }

    /**
     * Sets if read is from file
     *
     * @param fromFile true if from file
     */
    public void setFromFile(boolean fromFile) {
        this.fromFile = fromFile;
    }

    /**
     * Returns entered value
     *
     * @return Entered value
     */
    public String getValue() {
        return value;
    }

    /**
     * Sets the value entered
     *
     * @param value New entered value
     */
    public void setValue(String value) {
        this.value = value;
    }

    /**
     * Returns files hashes to catch recursion
     *
     * @return Files hashes
     */
    public List<Integer> getFilesHashes() {
        return filesHashes;
    }

    /**
     * Returns input
     *
     * @return Input
     */
    public Input getIn() {
        return in;
    }

    /**
     * Sets input
     *
     * @param in new input
     */
    public void setIn(Input in) {
        this.in = in;
    }

    /**
     * Returns output
     *
     * @return Output
     */
    public Output getOut() {
        return out;
    }

    /**
     * Sets output
     *
     * @param out new output
     */
    public void setOut(Output out) {
        this.out = out;
    }

    /**
     * Clears files hashes list
     */
    public void clearFilesHashes() {
        filesHashes.clear();
    }

    /**
     * Returns running parameter Program stops if running parameter become false
     *
     * @return Running parameter
     */
    public boolean isRunning() {
        return running;
    }

    /**
     * Sets running parameter
     *
     * @param running new running parameter value
     */
    public void setRunning(boolean running) {
        this.running = running;
    }

    /**
     * Returns collection to work with
     *
     * @return Collection to work with
     */
    public Collection getCollection() {
        return collection;
    }

    private void setCollection(Collection collection) {
        this.collection = collection;
    }

    /**
     * Returns history of correctly executed commands
     *
     * @return History of correctly executed commands
     */
    public CommandsHistory getCommandHistory() {
        return commandsHistory;
    }
}
