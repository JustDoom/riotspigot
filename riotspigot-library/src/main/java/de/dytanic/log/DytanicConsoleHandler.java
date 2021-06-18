package de.dytanic.log;

import jline.console.ConsoleReader;
import lombok.AllArgsConstructor;

import java.io.IOException;
import java.util.logging.Handler;
import java.util.logging.LogRecord;

/**
 * Created by Tareko on 22.01.2018.
 */
@AllArgsConstructor
final class DytanicConsoleHandler extends Handler {

    private ConsoleReader consoleReader;

    @Override
    public void publish(LogRecord record)
    {
        if(isLoggable(record))
        {
            final String format = getFormatter().format(record);
            DytanicAsyncPrintStream.asyncQueue.offer(new Runnable() {
                @Override
                public void run()
                {
                    try
                    {
                        consoleReader.print(ConsoleReader.RESET_LINE + format);
                        consoleReader.drawLine();
                        consoleReader.flush();
                    } catch (IOException ignored)
                    {
                    }
                }
            });
        }
    }

    @Override
    public void flush()
    {
    }

    @Override
    public void close() throws SecurityException
    {
        try
        {
            consoleReader.killLine();
        } catch (IOException ignored)
        {
        }
    }

}