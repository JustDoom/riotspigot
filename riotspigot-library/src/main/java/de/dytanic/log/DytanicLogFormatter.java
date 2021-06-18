package de.dytanic.log;

import de.dytanic.$;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.logging.Formatter;
import java.util.logging.LogRecord;

/**
 * Created by Tareko on 22.01.2018.
 */
final class DytanicLogFormatter extends Formatter {

    private final DateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");

    @Override
    public String format(LogRecord record)
    {
        StringBuilder builder = new StringBuilder();
        if (record.getThrown() != null)
        {
            StringWriter writer = new StringWriter();
            record.getThrown().printStackTrace(new PrintWriter(writer));
            builder.append(writer).append("\n");
        }

        return "[" + dateFormat.format(System.currentTimeMillis()) + $.SPACE_STRING + record.getLevel().getLocalizedName() + "]: " + formatMessage(record) + "\n" + builder.toString();
    }
}