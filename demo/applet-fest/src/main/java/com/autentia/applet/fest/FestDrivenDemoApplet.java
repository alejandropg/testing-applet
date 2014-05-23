package com.autentia.applet.fest;

import com.autentia.applet.DemoApplet;
import com.autentia.dria.log.Logger;
import com.autentia.fest.AppletFixtureFactory;
import com.autentia.fest.DrivenApplet;
import org.fest.swing.fixture.FrameFixture;

/**
 * java.policy
 * <pre>
 * grant signedBy "demoCert1", codeBase "http://localhost:8080/" {
 *     permission java.awt.AWTPermission "accessEventQueue";
 * };
 * </pre>
 */
public class FestDrivenDemoApplet extends DemoApplet implements DrivenApplet {

    private static final Logger logger = new Logger();

    private FrameFixture driver;

    @Override
    public void init() {
        super.init();

        logger.debug("Begin - {0}::init() ", FestDrivenDemoApplet.class.getSimpleName());

        driver = new AppletFixtureFactory(this).build();

        logger.debug("End - {0}::init() ", FestDrivenDemoApplet.class.getSimpleName());
    }

    @Override
    public FrameFixture getDriver() {
        return driver;
    }

    @Override
    public void start() {
        logger.debug("Begin - {0}::start() ", FestDrivenDemoApplet.class.getSimpleName());

        super.start();
        driver.show();

        logger.debug("End - {0}::start() ", FestDrivenDemoApplet.class.getSimpleName());
    }

    @Override
    public void stop() {
        logger.debug("Begin - {0}::stop() ", FestDrivenDemoApplet.class.getSimpleName());

        driver.cleanUp();
        super.stop();

        logger.debug("End - {0}::stop() ", FestDrivenDemoApplet.class.getSimpleName());
    }
}
