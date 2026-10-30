package domain;

/**
 * Custom exception class encapsulating domain error messages for NeoFlix.
 * @author Juan Espitia, Cristian Salamanca
 * @version ECI 2026
 */
public class NeoFlixException extends Exception {

    
    public static final String VALUE_UNKNOWN = "No ha sido posible calcular el valor porque es desconocido";
    public static final String UNKNOWN_VALUE = VALUE_UNKNOWN; 
    public static final String CONTENT_EMPTY = "El  contenido o la información requerida se encuentra vacía";
    public static final String DATA_ERROR = "Los datos del contenido presentan errores o inconsistencias";
    public static final String TO_BE_IMPLEMENTED = "Esta funcionalidad aún no ha sido implementada"; 
    public static final String REPEATED_NAME = "El nombre del contenido ya se encuentra registrado"; 
    public static final String NOT_A_NUMBER = "Los valores ingresados deben ser numéricos"; 
    public static final String OUT_OF_RANGE = "Los valores ingresados están fuera del rango permitido";
    public static final String WRONG_YEAR = "El año no es valido esta vez";
    public static final String EPISODE_NOT_FOUND = "No ha sido posible encontrar el episodio";
    public static final String NOT_A_SERIES = "El contenido indicado no es una serie";
    public static final String CONTENT_NOT_FOUND = "El contenido no fue encontrado";

    /**
     * Constructs a new NeoFlixException with the specified detail message.
     * @param message the detail error message
     */
    public NeoFlixException(String message) {
        super(message);
    }

}