package domain;

import java.util.logging.Logger;
import java.util.logging.Level;
import java.util.logging.FileHandler;
import java.util.logging.SimpleFormatter;


public class DOPOSoccerLog {
    public static void record(Exception e) {
        try {
            Logger logger = Logger.getLogger("DOPOSoccer");
            logger.setUseParentHandlers(false);

            FileHandler file = new FileHandler("DOPOSoccer.log", true);
            file.setFormatter(new SimpleFormatter());
            
            logger.addHandler(file);
            logger.log(Level.SEVERE, e.toString(), e);
            file.close();
            logger.removeHandler(file);

        } catch (Exception error) {
            error.printStackTrace();
        }
    }
}
