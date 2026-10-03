import javax.swing.JOptionPane;

/**
 * Utility class to display messages and dialogs to the user
 * only when the slot machine simulator is visible.
 *
 * This fulfills the usability requirement:
 * "Si la acción no se puede realizar se le debe presentar un mensaje
 * especial al usuario, usando un JOptionPane, sólo si el simulador está visible."
 *
 * @author Juan Espitia
 * @author Cristian Salamanca
 * @version 1.0
 */
public class SlotMachineDialog {

    /**
     * Displays an error message dialog only if the simulator is visible.
     *
     * @param isVisible whether the simulator is currently visible
     * @param message the error message to display
     */
    public static void showError(boolean isVisible, String message) {
        if (isVisible) {
            JOptionPane.showMessageDialog(
                null,
                message,
                "Error",
                JOptionPane.ERROR_MESSAGE
            );
        }
    }

    /**
     * Displays a warning message dialog only if the simulator is visible.
     *
     * @param isVisible whether the simulator is currently visible
     * @param message the warning message to display
     * @param title the title for the dialog
     */
    public static void showWarning(boolean isVisible, String message, String title) {
        if (isVisible) {
            JOptionPane.showMessageDialog(
                null,
                message,
                title,
                JOptionPane.WARNING_MESSAGE
            );
        }
    }

    /**
     * Displays an information message dialog only if the simulator is visible.
     *
     * @param isVisible whether the simulator is currently visible
     * @param message the information message to display
     * @param title the title for the dialog
     */
    public static void showInformation(boolean isVisible, String message, String title) {
        if (isVisible) {
            JOptionPane.showMessageDialog(
                null,
                message,
                title,
                JOptionPane.INFORMATION_MESSAGE
            );
        }
    }
}
