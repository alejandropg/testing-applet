package com.autentia.dria.applet;

import com.autentia.dria.RiaDriver;
import org.junit.Test;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

public class JsAppletDriverTest {

    private final JavascriptExecutorSpy jsExecutorSpy = new JavascriptExecutorSpy();
    private final RiaDriver driver = new JsAppletDriver(jsExecutorSpy, "anApplet");

    @Test
    public void given_an_applet__When_set_text__Then_execute_js_to_set_text_by_LiveConnect() throws Exception {
        driver.textBox("aTxtArea").setText("Dummy data");

        assertThat(jsExecutorSpy.executedJs(), is("anApplet.getDriver().textBox('aTxtArea').setText('Dummy data');"));
    }

    @Test
    public void given_an_applet__When_get_text__Then_execute_js_to_get_text_by_LiveConnect() throws Exception {
        driver.textBox("aTxtArea").text();

        assertThat(jsExecutorSpy.executedJs(), is("var outTxt = anApplet.getDriver().textBox('aTxtArea').text(); return outTxt;"));
    }

    @Test
    public void given_an_applet__When_click_button__Then_execute_js_to_get_click_button_by_LiveConnect() throws Exception {
        driver.button("aButton").click();

        assertThat(jsExecutorSpy.executedJs(), is("anApplet.getDriver().button('aButton').click();"));
    }
}
