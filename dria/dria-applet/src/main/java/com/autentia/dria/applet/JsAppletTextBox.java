package com.autentia.dria.applet;

import com.autentia.dria.TextBox;
import org.openqa.selenium.JavascriptExecutor;

class JsAppletTextBox extends JsAppletComponent implements TextBox {

    public JsAppletTextBox(JavascriptExecutor jsExecutor, StringBuilder js, String name) {
        super(jsExecutor, js, "textBox", name);
    }

    @Override
    public void setText(String text) {
        js.append(".setText('").append(text).append("');");
        executeJs();
    }

    @Override
    public String text() {
        js.insert(0, "var outTxt = ");
        js.append(".text(); return outTxt;");
        return executeJs().toString();
    }

}
