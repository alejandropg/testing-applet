package com.autentia.applet.dria;

import com.autentia.applet.DemoApplet;
import com.autentia.dria.applet.DrivenApplet;
import com.autentia.dria.swing.SwingDriver;

public class DriaDrivenDemoApplet extends DemoApplet implements DrivenApplet {

    private SwingDriver driver;

    @Override
    public void init() {
        super.init();
        driver = new SwingDriver(this);
    }

    @Override
    public SwingDriver getDriver() {
        return driver;
    }
}
