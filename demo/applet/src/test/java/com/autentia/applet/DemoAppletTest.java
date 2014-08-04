package com.autentia.applet;

import org.fest.swing.applet.AppletViewer;
import org.fest.swing.edt.FailOnThreadViolationRepaintManager;
import org.fest.swing.exception.ComponentLookupException;
import org.fest.swing.fixture.FrameFixture;
import org.fest.swing.launcher.AppletLauncher;
import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.Assert.assertThat;

public class DemoAppletTest {

    private AppletViewer viewer;
    private FrameFixture driver;

    @BeforeClass
    public static void setUpOnce() {
        FailOnThreadViolationRepaintManager.install();
    }

    @Before
    public void setUp() throws Exception {
        viewer = AppletLauncher.applet(DemoApplet.class).start();
        driver = new FrameFixture(viewer);
        driver.show();
    }


    @After
    public void tearDown() throws Exception {
        viewer.unloadApplet();
        driver.cleanUp();
    }

    @Test
    public void given_new_applet__when_init__then_all_components_with_name_are_registered() throws Exception {
        assertThat(driver.textBox("inTxtArea"), notNullValue());
        assertThat(driver.textBox("outTxtArea"), notNullValue());
        assertThat(driver.button("btn"), notNullValue());
    }

    @Test(expected = ComponentLookupException.class)
    public void given_a_registered_applet__when_retrieve_an_InputText_with_a_wrong_name__then_throw_exception() throws Exception {
        driver.textBox("non existing");
    }

    @Test(expected = ComponentLookupException.class)
    public void given_wrong_name__when_retrieve_a_Button__then_throw_exception() throws Exception {
        driver.button("non existing");
    }

    @Test
    public void given_a_button__when_click_it__then_execute_corresponding_action() throws Exception {
        driver.button("btn").click();
        assertThat(driver.textBox("outTxtArea").text(), is("Hola Mundo!"));
    }

    @Test
    public void given_an_InputText__when_text_is_written_in_it__then_text_should_be_kept_in_the_component() throws Exception {
        driver.textBox("inTxtArea").setText("Texto cambiado!");
        driver.button("btn").click();
        assertThat(driver.textBox("outTxtArea").text(), is("Texto cambiado!"));
    }
}
