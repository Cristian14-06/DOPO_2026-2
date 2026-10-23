import javax.swing.JOptionPane;

/**
 * Utility class to show message dialogs when the machine is visible.
 * 
 * @author Juan Espitia
 * @author Cristian Salamanca
 */
public class SlotMachineDialog {

    /**
     * Shows an error dialog if the simulator is visible.
     */
    public static void showError(boolean isVisible, String message) {
        if (isVisible) {
             JOptionPane.showMessageDialog(null, message, "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * Shows a warning dialog to the user if the machine is visible.
     */
    public static void showWarning(boolean isVisible, String message, String title){
        if(isVisible) {
            JOptionPane.showMessageDialog(null, message, title, JOptionPane.WARNING_MESSAGE );
        }
    }

    /**
     * Shows an information dialog if the simulator is visible.
     */
    public static void showInformation(boolean isVisible, String message, String title) {
        if (isVisible){
           JOptionPane.showMessageDialog(null, message, title, JOptionPane.INFORMATION_MESSAGE);
        }
    }
}
