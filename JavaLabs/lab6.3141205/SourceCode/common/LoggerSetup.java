package com.lab.common;

import java.io.File;
import java.io.IOException;
import java.util.logging.*;

public class LoggerSetup {
    public static void setupLogger(Logger logger, String fileName) {
        logger.setUseParentHandlers(false);
        for (Handler handler : logger.getHandlers()) {
            logger.removeHandler(handler);
        }
        try {
            File logFile = new File("logs" + File.separator + fileName);
            File directory = new File(logFile.getParent());
            if (!directory.exists()) {
                directory.mkdirs();
            }
            Handler consoleHandler = new ConsoleHandler();
            FileHandler fileHandler = new FileHandler(logFile.getAbsolutePath());
            logger.addHandler(consoleHandler);
            logger.addHandler(fileHandler);
            consoleHandler.setLevel(Level.SEVERE);
            fileHandler.setLevel(Level.ALL);
            logger.setLevel(Level.ALL);
        } catch (IOException exception) {
            logger.log(Level.SEVERE, "Ошибка при создании FileHandler", exception);
        }
    }
}
