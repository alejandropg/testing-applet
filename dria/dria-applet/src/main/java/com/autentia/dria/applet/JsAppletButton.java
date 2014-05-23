package com.autentia.dria.applet;

import com.autentia.dria.Button;
import org.openqa.selenium.JavascriptExecutor;

class JsAppletButton extends JsAppletComponent implements Button {

    public JsAppletButton(JavascriptExecutor jsExecutor, StringBuilder js, String name) {
        super(jsExecutor, js, "button", name);
    }

    @Override
    public void click() {
        js.append(".click();");
        executeJs();
    }
}
