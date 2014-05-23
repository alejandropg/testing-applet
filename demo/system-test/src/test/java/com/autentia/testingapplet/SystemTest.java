package com.autentia.testingapplet;

import com.autentia.dria.RiaDriver;
import com.autentia.dria.applet.JsAppletDriver;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxProfile;

import java.util.Arrays;
import java.util.Collection;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static org.junit.runners.Parameterized.Parameters;

@RunWith(Parameterized.class)
public class SystemTest {

    private static final WebDriver webDriver = new FirefoxDriver(buildTestingProfile());

    private static FirefoxProfile buildTestingProfile() {
        final FirefoxProfile profile = new FirefoxProfile();
        profile.setPreference("plugin.state.java", 2); // El 'Java Applet Plug-in' siempre activo para no necesitar 'click-to-play'.
        return profile;
    }

    @BeforeClass
    public static void setUpOnce() throws Exception {
        webDriver.get("http://localhost:8080/system-test.html");
        assertThat(webDriver.getTitle(), is("Testing Applet"));
    }

    @AfterClass
    public static void tearDownOnce() throws Exception {
        webDriver.quit();
    }

    @Parameters
    public static Collection<Object[]> data() {
        final Object[][] data = new Object[][] { { "driaApplet" }, { "festApplet" } };
        return Arrays.asList(data);
    }

    private final RiaDriver appletDriver;

    public SystemTest(String appletName) {
        appletDriver = new JsAppletDriver((JavascriptExecutor) webDriver, appletName);
    }

    @Test
    public void given_an_applet_with_text_as_input__When_click_button__Then_text_is_added_to_the_output() throws Exception {

        appletDriver.textBox("inTxtArea").setText("Desde el test de Java con Selenium");

        appletDriver.button("btn").click();

        final String outTxt = appletDriver.textBox("outTxtArea").text();
        assertThat(outTxt, is("Desde el test de Java con Selenium"));
    }

}
