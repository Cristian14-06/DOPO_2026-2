import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Shared collaborative unit tests for Ciclo 4 (SlotMachineCC4Test).
 * Authors: Juan Espitia, Cristian Salamanca (EsSa).
 *
 * @author Juan Espitia
 * @author Cristian Salamanca
 * @version 1.0
 */
public class SlotMachineCC4Test {

    /** Tests according to authors EsSa */

    @Test
    public void accordingEsSaShouldCopyLeftNeighborWhenLeftySpins() {
        SlotMachine machine = new SlotMachine();
        machine.addWheel("normal", 1);
        machine.addWheel("lefty", 2);

        machine.addSymbol("normal", 1, "red");
        machine.addSymbol("normal", 2, "blue");

        machine.placeSymbol(1, "red");
        machine.placeSymbol(2, "blue");

        machine.spin(2);
        assertEquals("red", machine.configuration()[1]);
    }

    @Test
    public void accordingEsSaShouldNotAllowRebelWheelToBeDeletedOrSwapped() {
        SlotMachine machine = new SlotMachine();
        machine.addWheel("rebel", 1);
        machine.addWheel("normal", 2);

        machine.addSymbol("normal", 1, "red");
        machine.addSymbol("normal", 2, "blue");

        machine.placeSymbol(1, "red");
        machine.placeSymbol(2, "blue");

        machine.delWheel(1);
        assertEquals(2, machine.configuration().length);

        machine.swap(1, 2);
        assertArrayEquals(new String[]{"red", "blue"}, machine.configuration());
    }

    @Test
    public void accordingEsSaShouldSpinInvertedWheelInOppositeDirection() {
        SlotMachine machine = new SlotMachine();
        machine.addWheel("inverted", 1);

        machine.addSymbol("normal", 1, "red");
        machine.addSymbol("normal", 2, "blue");
        machine.addSymbol("normal", 3, "green");

        machine.placeSymbol(1, "red");
        machine.spin(1, 1);

        assertEquals("green", machine.configuration()[0]);
    }

    @Test
    public void accordingEsSaShouldMaintainJackpotWhenAllWheelsMatch() {
        SlotMachine machine = new SlotMachine();
        machine.addWheel(1);
        machine.addWheel(2);
        machine.addWheel(3);

        machine.addSymbol(1, "red");
        machine.placeSymbol(1, "red");
        machine.placeSymbol(2, "red");
        machine.placeSymbol(3, "red");

        assertTrue(machine.isJackpot());
    }
}
