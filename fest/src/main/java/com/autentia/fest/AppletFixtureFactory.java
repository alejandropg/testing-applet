package com.autentia.fest;

import com.autentia.dria.log.Logger;
import org.fest.swing.fixture.Containers;
import org.fest.swing.fixture.FrameFixture;

import javax.swing.*;

/**
 * Esta clase se encarga de crear la fixture que se usará como driver para simular la interacción con el Applet.
 * Se tiene que usar desde dentro del propio applet, de forma que se cree la fixture en el método init del applet
 * y se destruya en el método destroy del applet. Ejemplo de uso:
 *
 * @see com.autentia.fest.DrivenApplet
 */
public class AppletFixtureFactory {

    private static final Logger logger = new Logger();

    private final JApplet targetApplet;
    private FrameFixture fixture;

    public AppletFixtureFactory(JApplet targetApplet) {
        this.targetApplet = targetApplet;
    }

    public FrameFixture build() {
        logger.debug("Begin - {0}::build() ", AppletFixtureFactory.class.getSimpleName());

        try {
            SwingUtilities.invokeAndWait(new Runnable() {
                public void run() {
                    fixture = Containers.frameFixtureFor(targetApplet);
                    targetApplet.setName("FEST driven " + targetApplet.getName());
                }
            });
        } catch (Exception e) {
            logger.error("Cannot create applet's GUI fixture.");
            e.printStackTrace();
        }

        logger.debug("End - {0}::build() ", AppletFixtureFactory.class.getSimpleName());
        return fixture;
    }
}
