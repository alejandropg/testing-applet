package com.autentia.applet.dria;

import org.fest.swing.edt.FailOnThreadViolationRepaintManager;
import org.fest.swing.edt.GuiActionRunner;
import org.fest.swing.edt.GuiQuery;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.NoSuchElementException;

import com.autentia.applet.AppletStubStub;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.Assert.assertThat;

public class DriaDrivenDemoAppletTest {

    private final DriaDrivenDemoApplet applet = createAppletInEDT();

    private DriaDrivenDemoApplet createAppletInEDT() {
        return GuiActionRunner.execute(new GuiQuery<DriaDrivenDemoApplet>() {
            @Override
            protected DriaDrivenDemoApplet executeInEDT() throws Throwable {
                return new DriaDrivenDemoApplet();
            }
        });
    }

    @BeforeClass
    public static void setUpOnce() {
        FailOnThreadViolationRepaintManager.install();
    }

    @Before
    public void setUp() throws Exception {
        applet.setStub(new AppletStubStub());
        applet.init();
    }

    @Test
    public void given_new_applet__When_init__Then_all_components_with_name_are_registered() throws Exception {
        assertThat(applet.getDriver().textBox("inTxtArea"), notNullValue());
        assertThat(applet.getDriver().textBox("outTxtArea"), notNullValue());
        assertThat(applet.getDriver().button("btn"), notNullValue());
    }

    @Test(expected = NoSuchElementException.class)
    public void given_a_registered_applet__When_retrieve_an_InputText_with_a_wrong_name__Then_throw_exception() throws Exception {
        applet.getDriver().textBox("non existing");
    }

    @Test(expected = NoSuchElementException.class)
    public void given_wrong_name__When_retrieve_a_Button__Then_throw_exception() throws Exception {
        applet.getDriver().button("non existing");
    }

    @Test
    public void given_a_button__When_click_it__Then_execute_corresponding_action() throws Exception {
        applet.getDriver().button("btn").click();
        assertThat(applet.getDriver().textBox("outTxtArea").text(), is("Hola Mundo!"));
    }

    @Test
    public void given_an_InputText__When_text_is_written_in_it__Then_text_should_be_kept_in_the_component() throws Exception {
        applet.getDriver().textBox("inTxtArea").setText("Texto cambiado!");
        applet.getDriver().button("btn").click();
        assertThat(applet.getDriver().textBox("outTxtArea").text(), is("Texto cambiado!"));
    }

}
