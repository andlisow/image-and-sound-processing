package main.java.pl.lodz.p.ftims.poid.exercise3;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author alisowsk
 */
public class App {
    private static final Logger LOG = LoggerFactory.getLogger(App.class);

    public static void main (String[] args){
        LOG.info("Application has started");
        new MainWindow();
        LOG.info("Application has finished");
    }
}
