package com.autentia.fest;

import org.fest.swing.fixture.FrameFixture;

/**
 * Interfaz que debe implementar el Applet que quermos manejar en los tests para exponer hacia fuera
 * el driver que se usará desde JavaScript para simular el comportamiento del usuario.
 * <p/>
 * Ejemplo de uso:
 * <pre>
 * public class AppletDrivenInTests extends DemoApplet implements DrivenApplet {
 *
 *     private FrameFixture driver;
 *
 *     @Override
 *     public void init() {
 *         super.init();
 *         driver = new AppletFixtureFactory(this).build();
 *     }
 *
 *     @Override
 *     public void start() {
 *         super.start();
 *         driver.show();
 *     }
 *
 *     @Override
 *     public void stop() {
 *         driver.cleanUp();
 *         super.stop();
 *     }
 *
 *     @Override
 *     public FrameFixture getDriver() {
 *         return driver;
 *     }
 * }
 * </pre>
 */
public interface DrivenApplet {
    FrameFixture getDriver();
}
