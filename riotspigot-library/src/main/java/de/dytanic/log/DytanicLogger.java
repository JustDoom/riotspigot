package de.dytanic.log;

import de.dytanic.$;
import jline.UnsupportedTerminal;
import jline.console.ConsoleReader;
import lombok.Getter;

import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.logging.*;

/**
 * Created by Tareko on 20.01.2018.
 */
@Getter
public final class DytanicLogger extends Logger implements AutoCloseable {

    private static DytanicLogger instance;

    private static final Path LOG_DIRECTORY = Paths.get("logs");

    private ConsoleReader consoleReader;

    public DytanicLogger()
    {
        super("DytanicSpigot-Logger", null);

        for(Handler handler : getHandlers()) removeHandler(handler);

        try
        {
            init();
        }catch (Exception ex) {
            log(Level.WARNING, "Error by initial Logger", ex);
        }

        instance = this;
    }

    private void init() throws Exception
    {
        if(!Files.exists(LOG_DIRECTORY)) Files.createDirectory(LOG_DIRECTORY);

        initialConsoleHandler();
        initialFileHandler();
    }

    @Override
    public void close() throws Exception
    {
        for(Handler handler : getHandlers()) handler.close();
    }

    private void initialConsoleHandler() throws Exception
    {
        try {
            consoleReader = new ConsoleReader(System.in, System.out);
        }catch (Exception ex) {
            consoleReader = new ConsoleReader(System.in, System.out, new UnsupportedTerminal());
        }

        consoleReader.setExpandEvents(false);
        setupHandler(new DytanicConsoleHandler(consoleReader));
    }

    private void initialFileHandler() throws Exception
    {
        setupHandler(new FileHandler("logs/spigot.log", 8000000, 10, true));
    }

    private void setupHandler(Handler handler) throws Exception
    {
        handler.setFormatter(new DytanicLogFormatter());
        handler.setEncoding(StandardCharsets.UTF_8.name());
        handler.setLevel(Level.ALL);
        addHandler(handler);
    }

    public static DytanicLogger getInstance()
    {
        if(instance == null) instance = new DytanicLogger();

        return instance;
    }

    /*= ------------------------------------- =*/

    public void warn(String msg)
    {
        warning(msg);
    }

    public void warn(String msg, Throwable ex)
    {
        if (msg == null) msg = $.EMPTY_STRING;
        log(Level.WARNING, msg, ex);
    }

    public void error(String msg)
    {
        if (msg == null) msg = $.EMPTY_STRING;
        log(Level.SEVERE, msg);
    }

    public void error(String msg, Throwable ex)
    {
        if (msg == null) msg = $.EMPTY_STRING;
        log(Level.SEVERE, msg, ex);
    }

    public void error(Throwable throwable)
    {
        log(Level.SEVERE, $.EMPTY_STRING, throwable);
    }

    public void fatal(String msg, Throwable ex)
    {
        if (msg == null) msg = $.EMPTY_STRING;
        log(Level.SEVERE, msg, ex);
    }

    public void fatal(String msg)
    {
        if (msg == null) msg = $.EMPTY_STRING;
        log(Level.SEVERE, msg);
    }

    public void fatal(Throwable throwable)
    {
        log(Level.SEVERE, "an error", throwable);
    }

    public void warn(String msg, Object... objects)
    {
        log(Level.WARNING, msg, objects);
    }

    public void info(String msg, Object... objects)
    {
        log(Level.INFO, msg, objects);
    }

    public boolean isDebugEnabled()
    {
        return false;
    }

    public void debug(String msg)
    {
        log(Level.FINE, msg);
    }

    public void debug(String msg, Object... objects)
    {
        log(Level.FINE, msg, objects);
    }

    public void log(org.apache.logging.log4j.Level level, String msg)
    {
        log(Level.INFO, msg);
    }

    public void log(org.apache.logging.log4j.Level level, String msg, Throwable throwable)
    {
        log(Level.SEVERE, msg, throwable);
    }

}