package com.lab.server.executor;

import com.lab.common.io.Input;
import com.lab.common.io.Output;
import com.lab.common.musicBand.MusicBand;
import com.lab.common.musicBand.MusicBandCreator;
import com.lab.common.user.hashGenerator.HashGenerator;
import com.lab.common.user.hashGenerator.SHA1Generator;
import com.lab.server.command.CommandsHistory;
import com.lab.server.storage.collection.Collection;
import com.lab.server.storage.dao.daos.UserDAO;
import com.lab.server.storage.dataSource.DataSource;

import java.util.ArrayList;
import java.util.List;

/**
 * Editor class contains data to be passed to command
 */
public final class Editor {
    private final UserDAO userDAO;
    private Input in;
    private Output out;
    private MusicBand musicBand;
    private String value;
    private boolean running;
    private boolean fromFile;
    private Executor executor;
    private Collection collection;
    private List<Integer> filesHashes;
    private HashGenerator hashGenerator;
    private CommandsHistory commandsHistory;

    public Editor(DataSource dataSource, Executor executor) {
        value = null;
        running = true;
        fromFile = false;
        this.executor = executor;
        in = new Input();
        out = new Output();
        userDAO = new UserDAO(dataSource);
        collection = new Collection(dataSource);
        filesHashes = new ArrayList<>();
        hashGenerator = new SHA1Generator();
        commandsHistory = new CommandsHistory();
    }

    public void update(Editor editor) {
        this.in = editor.in;
        this.out = editor.out;
        this.musicBand = editor.musicBand;
        this.value = editor.value;
        this.running = editor.running;
        this.fromFile = editor.fromFile;
        this.collection = editor.collection;
        this.filesHashes = editor.filesHashes;
        this.commandsHistory = editor.commandsHistory;
    }

    public Executor getExecutor() {
        return executor;
    }

    public HashGenerator getHashGenerator() {
        return hashGenerator;
    }

    public UserDAO getUserDAO() {
        return userDAO;
    }

    public MusicBand getMusicBand() {
        if (fromFile) {
            musicBand = new MusicBandCreator(in, out).create();
        }

        return musicBand;
    }

    public void setMusicBand(MusicBand musicBand) {
        this.musicBand = musicBand;
    }

    public boolean isFromFile() {
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

    /**
     * Returns history of correctly executed commands
     *
     * @return History of correctly executed commands
     */
    public CommandsHistory getCommandHistory() {
        return commandsHistory;
    }
}
