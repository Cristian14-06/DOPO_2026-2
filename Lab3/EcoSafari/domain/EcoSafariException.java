package domain;

/**
 * Custom domain exception for EcoSafari domain rule violations.
 *
 * @author Juan David Espitia, Cristian Salamanca
 * @version 1.0
 */
public class EcoSafariException extends Exception {

    /** Error message when a position is outside grid boundaries */
    public static final String OUT_OF_BOUNDS = "Position is out of EcoSafari bounds.";

    /** Error message when attempting to place an entity on an already occupied cell */
    public static final String CELL_OCCUPIED = "The cell is already occupied.";

    /** Error message when an entity is not found in the habitat */
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