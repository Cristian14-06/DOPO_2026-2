import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Collaborative unit tests for Ciclo 4.
 *
 * @author Juan Espitia
 * @author Cristian Salamanca
 */
public class SlotMachineCC4Test {

    /**
     * Verifies that Lefty copies the symbol from its left neighbor wheel.
     */
    @Test
    public void accordingEsSaShouldCopyLeftNeighborWhenLeftySpins() {
        SlotMachine machine = new SlotMachine();
        machine.addWheel("normal", 1);
         machine.addWheel("lefty", 2);

        machine.addSymbol("normal", 1, "red");
        machine.addSymbol("normal", 2, "blue");

        machine.placeSymbol(1, "red");
        machine.placeSymbol(2, "blue" );

        machine.spin(2);
        assertEquals("red", machine.configuration()[1]);
    }

    /**
     * Verifies that a Rebel wheel cannot be deleted or swapped.
     */
    @Test
    public void accordingEsSaShouldNotAllowRebelWheelToBeDeletedOrSwapped(){
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

    /**
     * Verifies that the Inverted wheel spins in the opposite direction.
     */
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

    /**
     * Checks that jackpot is detected when all wheels match.
     */
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
