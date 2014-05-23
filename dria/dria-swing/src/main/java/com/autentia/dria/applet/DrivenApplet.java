package com.autentia.dria.applet;

import com.autentia.dria.swing.SwingDriver;

/**
 * Interfaz que debe implementar el Applet que quermos manejar en los tests para exponer hacia fuera
 * el driver que se usará desde JavaScript para simular el comportamiento del usuario.
 * <p/>
 * Ejemplo de uso:
 * <pre>
 * public class DriaDrivenDemoApplet extends DemoApplet implements DrivenApplet {
 *
 *     private RiaDriver driver;
 *
 *     @Override
 *     public void init() {
 *         super.init();
 *         driver = new RiaDriver(this);
 *     }
 *
 *
 *     @Override
 *     public RiaDriver getDriver() {
 *         return driver;
 *     }
 * }
 * </pre>
 */
public interface DrivenApplet {
    SwingDriver getDriver();
}
