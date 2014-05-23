package com.autentia.applet.fest;

import org.fest.swing.applet.AppletViewer;
import org.fest.swing.edt.FailOnThreadViolationRepaintManager;
import org.fest.swing.edt.GuiActionRunner;
import org.fest.swing.edt.GuiQuery;
import org.fest.swing.exception.ComponentLookupException;
import org.fest.swing.fixture.FrameFixture;
import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import java.lang.reflect.InvocationTargetException;

import static javax.swing.SwingUtilities.invokeAndWait;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.Assert.assertThat;

public class FestDrivenDemoAppletTest {

    private FestDrivenDemoApplet applet;
    private AppletViewer viewer;
    private FrameFixture driver;

    @BeforeClass
    public static void setUpOnce() {
        FailOnThreadViolationRepaintManager.install();
    }

    @Before
    public void setUp() throws Exception {
        applet = createAppletInEDT();

        viewer = AppletViewer.newViewer(applet);

        driver = applet.getDriver();
        driver.show();
    }

    private FestDrivenDemoApplet createAppletInEDT() {
        return GuiActionRunner.execute(new GuiQuery<FestDrivenDemoApplet>() {
            @Override
            protected FestDrivenDemoApplet executeInEDT() throws Throwable {
                return new FestDrivenDemoApplet();
            }
        });
    }

    @After
    public void tearDown() throws Exception {
        driver.cleanUp();
        unloadAppletInEDT();
    }

    private void unloadAppletInEDT() throws InvocationTargetException, InterruptedException {
        invokeAndWait(new Runnable() {
            @Override
            public void run() {
                viewer.unloadApplet();
            }
        });
    }

    @Test
    public void given_new_applet__whenInit__then_all_components_with_name_are_registered() throws Exception {
        assertThat(applet.getDriver().textBox("inTxtArea"), notNullValue());
        assertThat(applet.getDriver().textBox("outTxtArea"), notNullValue());
        assertThat(applet.getDriver().button("btn"), notNullValue());
    }

    @Test(expected = ComponentLookupException.class)
    public void given_a_registered_applet__when_when_retrieve_an_InputText_with_a_wrong_name__then_throw_exception() throws Exception {
        applet.getDriver().textBox("non existing");
    }

    @Test(expected = ComponentLookupException.class)
    public void given_wrong_name__when_retrieve_a_Button__then_throw_exception() throws Exception {
        applet.getDriver().button("non existing");
    }

    @Test
    public void given_a_button__when_click_it__then_execute_corresponding_action() throws Exception {
        applet.getDriver().button("btn").click();
        assertThat(applet.getDriver().textBox("outTxtArea").text(), is("Hola Mundo!"));
    }

    @Test
    public void given_an_InputText__when_text_is_written_in_it__then_text_should_be_kept_in_the_component() throws Exception {
        applet.getDriver().textBox("inTxtArea").setText("Texto cambiado!");
        applet.getDriver().button("btn").click();
        assertThat(applet.getDriver().textBox("outTxtArea").text(), is("Texto cambiado!"));
    }
}
