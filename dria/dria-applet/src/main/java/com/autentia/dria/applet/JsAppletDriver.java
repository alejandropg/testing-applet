package com.autentia.dria.applet;

import com.autentia.dria.Button;
import com.autentia.dria.RiaDriver;
import com.autentia.dria.TextBox;
import org.openqa.selenium.JavascriptExecutor;

public class JsAppletDriver implements RiaDriver {
    private final JavascriptExecutor jsExecutor;
    private final String appletName;

    public JsAppletDriver(JavascriptExecutor jsExecutor, String appletName) {
        this.jsExecutor = jsExecutor;
        this.appletName = appletName;
    }

    @Override
    public TextBox textBox(String name) {
        final StringBuilder js = createJs();
        return new JsAppletTextBox(jsExecutor, js, name);
    }

    @Override
    public Button button(String name) {
        final StringBuilder js = createJs();
        return new JsAppletButton(jsExecutor, js, name);
    }

    private StringBuilder createJs() {
        return new StringBuilder(appletName + ".getDriver()");
    }
}
