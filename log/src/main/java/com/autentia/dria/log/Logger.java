package com.autentia.dria.log;

import java.text.MessageFormat;
import java.util.Locale;

import static com.autentia.dria.log.Logger.Level.*;

public class Logger {

    public enum Level {ERROR, WARN, INFO, DEBUG}

    static Level activeLevel = INFO;

    public static void init(String level) {
        try {
            final String upperCaseLevel = level.toUpperCase(Locale.US);
            final Level activeLevel = Level.valueOf(upperCaseLevel);
            init(activeLevel);

        } catch (RuntimeException ignored) {
            // If cannot read parameter, use Logger default configuration.
        }
    }

    public static void init(Level level) {
        if (level != null) {
            activeLevel = level;
        }
    }

    public void error(String message, Object... arguments) {
        log(ERROR, message, arguments);
    }

    public void warn(String message, Object... arguments) {
        log(WARN, message, arguments);
    }

    public void info(String message, Object... arguments) {
        log(INFO, message, arguments);
    }

    public void debug(String message, Object... arguments) {
        log(DEBUG, message, arguments);
    }

    private void log(Level level, String msg, Object... arguments) {
        if (level.ordinal() <= Logger.activeLevel.ordinal()) {
            final String pattern = preparePattern(level, msg);
            final String formattedMessage = formatMessage(pattern, arguments);
            System.out.println(formattedMessage);
        }
    }

    private String preparePattern(Level level, String msg) {
        return formatMessage("[{0}] - {1}", level, msg);
    }

    private String formatMessage(String msg, Object... arguments) {
        try {
            return MessageFormat.format(msg, arguments);
        } catch (IllegalArgumentException e) {
            final StringBuilder errorMsg = new StringBuilder("Cannot format message: ").append(msg);
            for (Object obj: arguments) {
                errorMsg.append(" | ").append(obj);
            }

            System.err.println(errorMsg);
        }
        return "";
    }
}
