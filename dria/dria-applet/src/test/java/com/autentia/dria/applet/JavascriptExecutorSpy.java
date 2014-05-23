package com.autentia.dria.applet;

import org.openqa.selenium.JavascriptExecutor;

public class JavascriptExecutorSpy implements JavascriptExecutor {

    private String executedJs;

    @Override
    public Object executeScript(String script, Object... args) {
        executedJs = script;
        return "";
    }

    @Override
    public Object executeAsyncScript(String script, Object... args) {
        return null;
    }

    public String executedJs() {
        return executedJs;
    }
}
