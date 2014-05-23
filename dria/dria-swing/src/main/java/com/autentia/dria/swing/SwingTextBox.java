package com.autentia.dria.swing;

import com.autentia.dria.TextBox;

import javax.swing.text.JTextComponent;

public class SwingTextBox implements TextBox {
    private final JTextComponent textComponent;

    public SwingTextBox(JTextComponent textComponent) {
        this.textComponent = textComponent;
    }

    @Override
    public void setText(String text) {
        textComponent.setText(text);
    }

    @Override
    public String text() {
        return textComponent.getText();
    }
}
