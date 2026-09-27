package domain;

/**
 * Custom domain exception for EcoSafari domain rule violations.
 *
 * @author Juan David Espitia, Cristian Salamanca
 * @version 1.0
 */
public class EcoSafariException extends Exception {

    public static final String OUT_OF_BOUNDS = "Position is out of EcoSafari bounds.";
    public static final String CELL_OCCUPIED = "The cell is already occupied.";
    public static final String ENTITY_NOT_FOUND = "Entity was not found in the EcoSafari.";

    /**
     * Constructs a new EcoSafariException with the specified detail message.
     *
     * @param message the detail error message
     */
    public EcoSafariException(String message) {
        super(message);
    }
}