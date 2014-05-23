package com.autentia.dria.swing;

import com.autentia.dria.RiaDriver;
import com.autentia.dria.TextBox;
import com.autentia.dria.applet.DrivenApplet;
import com.autentia.dria.log.Logger;

import javax.swing.*;
import javax.swing.text.JTextComponent;
import java.awt.*;
import java.util.HashMap;
import java.util.Map;
import java.util.NoSuchElementException;

public class SwingDriver implements RiaDriver {

    private static final String DUPLICATE_COMPONENT_MSG = "{0} with name '{1}' is already registered. First component is kept, new component is discarded.";
    private static final Logger logger = new Logger();

    private final Map<String, TextBox> registeredInputTexts = new HashMap<String, TextBox>();
    private final Map<String, com.autentia.dria.Button> registeredButtons = new HashMap<String, com.autentia.dria.Button>();
    private int componentDeep = 0;

    public SwingDriver(Container container) {
        if (!(container instanceof DrivenApplet)) {
            throw new IllegalArgumentException("Container should implements interface " + DrivenApplet.class.getName());
        }
        registerComponentsByNameFrom(container);
    }

    private void registerComponentsByNameFrom(Container container) {
        for (Component component : container.getComponents()) {
            register(component);

            if (component instanceof Container) {
                final Container childContainer = (Container) component;
                componentDeep++;
                registerComponentsByNameFrom(childContainer);
                componentDeep--;
            }
        }
    }

    private void register(Component component) {
        final String name = component.getName();

        logger.debug("{0}{1} - {2}", prepareIndentation(), name, component.getClass().getName());

        if (name == null) {
            return;
        }

        if (component instanceof JTextComponent) {
            addComponent(registeredInputTexts, name, new SwingTextBox((JTextComponent) component));

        } else if (component instanceof AbstractButton) {
            addComponent(registeredButtons, name, new SwingButton((AbstractButton) component));
        }
    }

    private <T> void addComponent(Map<String, T> map, String componentName, T driverToAdd) {
        if (map.containsKey(componentName)) {
            logger.warn(DUPLICATE_COMPONENT_MSG, driverToAdd.getClass().getSimpleName(), componentName);
            return;
        }
        map.put(componentName, driverToAdd);
    }

    private String prepareIndentation() {
        final int spacesToAdd = componentDeep * 4;
        final StringBuilder indentation = new StringBuilder(spacesToAdd);
        for (int i = 0; i < spacesToAdd; i++) {
            indentation.append(' ');
        }
        return indentation.toString();
    }

    @Override
    public TextBox textBox(String name) {
        final TextBox textBox = registeredInputTexts.get(name);
        if (textBox == null) {
            throw new NoSuchElementException("There is no InputText with name: " + name);
        }
        return textBox;
    }

    @Override
    public com.autentia.dria.Button button(String name) {
        final com.autentia.dria.Button button = registeredButtons.get(name);
        if (button == null) {
            throw new NoSuchElementException("There is no Button with name: " + name);
        }
        return button;
    }

}
