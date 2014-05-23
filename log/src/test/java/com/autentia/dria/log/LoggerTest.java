package com.autentia.dria.log;

import com.autentia.dria.log.Logger;
import org.junit.BeforeClass;
import org.junit.Test;

import static com.autentia.dria.log.Logger.Level.DEBUG;
import static com.autentia.dria.log.Logger.Level.WARN;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

public class LoggerTest {

    @BeforeClass
    public static void setUpOnce() throws Exception {
        Logger.init(DEBUG);
    }

    @Test
    public void given_null_level__When_init__Then_do_not_fail_and_use_default_config() throws Exception {
        Logger.init((String)null);
        assertThat(Logger.activeLevel, is(DEBUG));

        Logger.init((Logger.Level)null);
        assertThat(Logger.activeLevel, is(DEBUG));
    }

    @Test
    public void given_wrong_level__When_init__Then_do_not_fail_and_use_default_config() throws Exception {
        Logger.init("wrong level");
        assertThat(Logger.activeLevel, is(DEBUG));
    }

    @Test
    public void given_lowercase_level__When_init__Then_do_not_fail_and_use_it() throws Exception {
        Logger.init("warn");
        assertThat(Logger.activeLevel, is(WARN));

        // Restore previous level
        Logger.init(DEBUG);
    }

    @Test
    public void printMessageInConsole() throws Exception {
        final Logger logger = new Logger();
        logger.error("Hola mundo! {0} inside!", "Error");
        logger.warn("Hola mundo! {0} inside!", "Warn");
        logger.info("Hola mundo! {0} inside!", "Info");
        logger.debug("Hola mundo! {0} inside!", "Debug");
    }
}
