package com.autentia.dria.applet;

import org.openqa.selenium.JavascriptExecutor;

abstract class JsAppletComponent {

    final JavascriptExecutor jsExecutor;
    final StringBuilder js;

    JsAppletComponent(JavascriptExecutor jsExecutor, StringBuilder js, String componentType, String componentName) {
        this.jsExecutor = jsExecutor;
        this.js = js;
        js.append('.').append(componentType).append("('").append(componentName).append("')");
    }

    Object executeJs() {
        return jsExecutor.executeScript(js.toString());
    }
}
