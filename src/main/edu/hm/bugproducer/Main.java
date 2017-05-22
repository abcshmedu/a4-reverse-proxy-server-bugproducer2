package edu.hm.bugproducer;

/**
 * Start the application without an AppServer like tomcat.
 *
 * @author <a mailto:axel.boettcher@hm.edu>Axel B&ouml;ttcher</a>
 */
public class Main {

    public static final int PORT = 8084;

    /**
     * Deploy local directories using Jetty without needing a container-based deployment.
     *
     * @param args unused
     * @throws Exception might throw for several reasons.
     */
    public static void main(String... args) throws Exception {
        JettyStarter jettyStarter = new JettyStarter();
        jettyStarter.startJetty();
    }

}
