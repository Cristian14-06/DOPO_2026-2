import javax.swing.JOptionPane;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Acceptance test to verify the behavior of SlotMachine.
 *
 * @author Juan Espitia
 * @author Cristian Salamanca
 */
public class SlotMachineAcceptanceTest {

    /**
     * Main method to run the acceptance test manually.
     */
    public static void main(String[] args) {
        SlotMachineAcceptanceTest prueba = new SlotMachineAcceptanceTest();
         prueba.acceptanceTest();
    }

    /**
     * Runs the full acceptance flow showing the different wheel and symbol types.
     */
    @Test
    public void acceptanceTest() {
        SlotMachine machine = new SlotMachine();
        machine.makeVisible();

        JOptionPane.showMessageDialog(null, "Se van a agregar los 4 tipos de ruedas: normal, lefty, rebel e inverted.");
        machine.addWheel("normal", 1);
         machine.addWheel("lefty", 2);
        machine.addWheel("rebel", 3);
        machine.addWheel("inverted", 4 );

        JOptionPane.showMessageDialog(null, "Se van a agregar los 3 tipos de simbolos: normal (red), ephemeral (green) y shy (magenta).");
        machine.addSymbol("normal", 1, "red");
        machine.addSymbol("ephemeral", 2, "green");
         machine.addSymbol("shy", 3, "magenta");

        machine.placeSymbol(1, "red");
        machine.placeSymbol(2, "green");
        machine.placeSymbol(3, "magenta");
         machine.placeSymbol(4, "green");

        JOptionPane.showMessageDialog(null, "Se va a girar la rueda 1 un paso (a green) y luego la rueda 2 (Lefty) para ver que copia a la rueda 1.");
        machine.spin(1, 1);
        machine.spin(2, 1);
        assertEquals(machine.configuration()[0], machine.configuration()[1]);

        JOptionPane.showMessageDialog(null, "Se va a bloquear la rueda 2 (Lefty) y girar la rueda 4 (Inverted) en sentido contrario.");
        machine.lock(2);
         machine.placeSymbol(1, "red");
        machine.spin(2, 1);
        assertEquals("green", machine.configuration()[1]);
        machine.unlock(2);

        machine.placeSymbol(4, "red");
        machine.spin(4, 1);
        assertEquals("magenta", machine.configuration()[3] );

        JOptionPane.showMessageDialog(null, "Se va a intentar eliminar y bloquear la rueda 3 (Rebel) para ver que no se deja.");
        machine.delWheel(3);
         machine.lock(3);
        assertEquals(4, machine.configuration().length);

        JOptionPane.showMessageDialog(null, "Finalmente se van a poner todas las ruedas en red para llegar al Jackpot.");
        machine.placeSymbol(1, "red");
        machine.spin(2, 1);
        machine.placeSymbol(3, "red");
         machine.placeSymbol(4, "red");

        assertTrue(machine.isJackpot());
        JOptionPane.showMessageDialog(null, "Jackpot alcanzado, la maquina cambio a color amarillo.");
        machine.makeInvisible();
    }
}
