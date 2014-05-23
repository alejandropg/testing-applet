package com.autentia.dria.swing;

import com.autentia.dria.Button;

import javax.swing.*;
import java.lang.reflect.InvocationTargetException;

public class SwingButton implements Button {
    public static final int SIMULATED_PRESS_TIME = 0;

    private final AbstractButton buttonComponent;

    public SwingButton(AbstractButton buttonComponent) {
        this.buttonComponent = buttonComponent;
    }

    @Override
    public void click() {
        try {
            SwingUtilities.invokeAndWait(new Runnable() {
                @Override
                public void run() {
                    buttonComponent.doClick(SIMULATED_PRESS_TIME);
                }
            });
        } catch (Exception e) {
            throw new IllegalStateException(e);
        }
    }
}
