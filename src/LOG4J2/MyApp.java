/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package LOG4J2;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

/**
 *
 * @author fernando.m.souza
 */
public class MyApp {

    // Define a static logger variable so that it references the
    // Logger instance named "MyApp".
    private static final Logger logger = LogManager.getLogger(MyApp.class);

    public static void main(final String... args) {

        // Set up a simple configuration that logs on the console.
        logger.trace("Entering application.");
        logger.error("Didn't do it.");
//        Bar bar = new Bar();
//        if (!bar.doIt()) {
//            logger.error("Didn't do it.");
//        }
        logger.trace("Exiting application.");
    }

}
