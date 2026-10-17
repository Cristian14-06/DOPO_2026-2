package domain;    

import java.util.logging.Logger;
import java.util.logging.FileHandler;
import java.util.logging.SimpleFormatter;
import java.util.logging.Level;

/**
 * Utility class for recording unexpected application exceptions into a log file.
 * @author Juan Espitia, Cristian Salamanca
 * @version ECI 2026
 */
public class Log{
    public static String name="NeoSeries";
    
    /**
     * Records an exception stack trace into the persistent log file.
     * @param e the exception to be logged
     */
    public static void record(Exception e){
        try{
            Logger logger=Logger.getLogger(name);
            logger.setUseParentHandlers(false);
            FileHandler file=new FileHandler(name+".log",true);
            file.setFormatter(new SimpleFormatter());
            logger.addHandler(file);
            logger.log(Level.SEVERE,e.toString(),e);
            file.close();
        }catch (Exception oe){
            oe.printStackTrace();
            System.exit(0);
        }
    }
}

    
