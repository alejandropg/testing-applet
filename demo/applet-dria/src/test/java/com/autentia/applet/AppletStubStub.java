package com.autentia.applet;

import com.autentia.dria.log.Logger;

import java.applet.AppletContext;
import java.applet.AppletStub;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

public class AppletStubStub implements AppletStub {

    private final Map<String, String> stubParameters = new HashMap<String, String>() {{
            put("loggerLevel", Logger.Level.DEBUG.name());
    }};

    @Override
    public boolean isActive() {
        return false;
    }

    @Override
    public URL getDocumentBase() {
        return null;
    }

    @Override
    public URL getCodeBase() {
        return null;
    }

    @Override
    public String getParameter(String name) {
        return stubParameters.get(name);
    }

    @Override
    public AppletContext getAppletContext() {
        return null;
    }

    @Override
    public void appletResize(int width, int height) {

    }
}
