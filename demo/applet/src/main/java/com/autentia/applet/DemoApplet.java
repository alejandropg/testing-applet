package com.autentia.applet;

import com.autentia.dria.log.Logger;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Applet sencillo con una area de entrada de texto, un botón, y un área de salida de texto. Cuando se pulsa el botón,
 * el texto en el área de entrada se mueve al final del texto del área de salida.
 * <p/>
 * Documentación de referencia y ejemplos:
 * - http://docs.oracle.com/javase/tutorial/deployment/applet/index.html
 * - http://docs.oracle.com/javase/tutorial/deployment/applet/examplesIndex.html
 * - http://docs.oracle.com/javase/7/docs/technotes/guides/jweb/security/manifest.html
 * - http://docs.oracle.com/javase/8/docs/technotes/guides/jweb/security/manifest.html
 * - http://www.oracle.com/technetwork/java/javase/7u-relnotes-515228.html
 * - https://jdk6.java.net/plugin2/liveconnect/
 * - https://blogs.oracle.com/java-platform-group/entry/liveconnect_changes_in_7u45 (October 2013)
 * - https://blogs.oracle.com/java-platform-group/entry/new_security_requirements_for_rias (in 7u51 (January 2014))
 * - http://java.com/en/download/help/appsecuritydialogs.xml#revokecheck
 */
public class DemoApplet extends JApplet implements ActionListener {

    private static final Logger logger = new Logger();

    private JTextArea inTxtArea;
    private JTextArea outTxtArea;

    @Override
    public void init() {
        super.init();

        Logger.init(getParameter("loggerLevel"));

        logger.debug("Begin - {0}::init() ", DemoApplet.class.getSimpleName());

        try {
            SwingUtilities.invokeAndWait(new Runnable() {
                public void run() {
                    setName("DemoApplet");
                    createGUI();
                }
            });
        } catch (Exception e) {
            logger.error("Cannot create applets GUI");
            e.printStackTrace();
        }

        logger.debug("End - {0}::init() ", DemoApplet.class.getSimpleName());
    }

    private void createGUI() {
        getContentPane().setName("contentPane");
        setLayout(new BoxLayout(getContentPane(), BoxLayout.Y_AXIS));

        inTxtArea = buildInTxtArea();
        outTxtArea = buildOutTxtArea();

        add(inTxtArea);
        add(Box.createVerticalStrut(20));
        add(buildAddTextButton());
        add(Box.createVerticalStrut(20));
        add(outTxtArea);
    }

    private JTextArea buildInTxtArea() {
        final JTextArea txtArea = new JTextArea();
        txtArea.setName("inTxtArea");
        txtArea.setText("Hola Mundo!");
        return txtArea;
    }

    private JTextArea buildOutTxtArea() {
        final JTextArea txtArea = new JTextArea();
        txtArea.setName("outTxtArea");
        txtArea.setEditable(false);
        return txtArea;
    }

    private JButton buildAddTextButton() {
        final JButton btn = new JButton("Añadir el texto");
        btn.setName("btn");
        btn.setAlignmentX(Component.CENTER_ALIGNMENT);
        btn.setActionCommand("addText");
        btn.addActionListener(this);
        return btn;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e == null || "addText".equals(e.getActionCommand())) {
//        if ("addText".e   quals(e.getActionCommand())) {

            final String newText = inTxtArea.getText();
            outTxtArea.append(newText);
            inTxtArea.setText("");
        }
    }
}
