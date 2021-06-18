package de.dytanic.log;

import de.dytanic.$;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by Tareko on 22.01.2018.
 */
public final class DytanicOutputStream extends ByteArrayOutputStream {

    private Level level;

    private Logger logger;

    public DytanicOutputStream(Level level, Logger logger)
    {
        this.level = level;
        this.logger = logger;
    }

    @Override
    public void flush() throws IOException
    {
        String input = toString(StandardCharsets.UTF_8.name());
        super.reset();

        if(!input.isEmpty() && !input.equals(System.lineSeparator())) logger.logp(level, $.EMPTY_STRING, $.EMPTY_STRING, input);
    }
}