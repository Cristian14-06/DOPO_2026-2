import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Comprehensive unit tests for Ciclo 4 features in SlotMachine.
 * Tests cover all wheel types (normal, lefty, rebel, inverted)
 * and symbol types (normal, ephemeral, shy) in invisible mode.
 *
 * @author Juan Espitia
 * @author Cristian Salamanca
 * @version 1.0
 */
public class SlotMachineC4Test {

    /** Tests for Wheel types: Lefty */

    @Test
    public void shouldCopyLeftNeighborWhenLeftySpins() {
        SlotMachine machine = new SlotMachine();
        machine.addWheel("normal", 1);
        machine.addWheel("lefty", 2);

        machine.addSymbol("normal", 1, "red");
        machine.addSymbol("normal", 2, "blue");

        machine.placeSymbol(1, "red");
        machine.placeSymbol(2, "blue");

        assertArrayEquals(new String[]{"red", "blue"}, machine.configuration());

        machine.spin(2);

        assertEquals("red", machine.configuration()[1]);
    }

    @Test
    public void shouldSpinNormallyWhenLeftyHasNoLeftNeighbor() {
        SlotMachine machine = new SlotMachine();
        machine.addWheel("lefty", 1);
        machine.addSymbol("normal", 1, "red");
        machine.addSymbol("normal", 2, "blue");

        machine.placeSymbol(1, "red");
        assertEquals("red", machine.configuration()[0]);

        machine.spin(1, 1);
        assertEquals("blue", machine.configuration()[0]);
    }

    @Test
    public void shouldUpdateLeftyNeighborAfterWheelDeletion() {
        SlotMachine machine = new SlotMachine();
        machine.addWheel("normal", 1);
        machine.addWheel("normal", 2);
        machine.addWheel("lefty", 3);

        machine.addSymbol("normal", 1, "red");
        machine.addSymbol("normal", 2, "blue");

        machine.placeSymbol(1, "red");
        machine.placeSymbol(2, "blue");
        machine.placeSymbol(3, "red");

        machine.delWheel(1);

        assertEquals(2, machine.configuration().length);

        machine.spin(2);
        assertEquals(machine.configuration()[0], machine.configuration()[1]);
    }

    @Test
    public void shouldUpdateLeftyNeighborAfterSwap() {
        SlotMachine machine = new SlotMachine();
        machine.addWheel("normal", 1);
        machine.addWheel("normal", 2);
        machine.addWheel("lefty", 3);

        machine.addSymbol("normal", 1, "red");
        machine.addSymbol("normal", 2, "blue");
        machine.addSymbol("normal", 3, "green");

        machine.placeSymbol(1, "red");
        machine.placeSymbol(2, "blue");
        machine.placeSymbol(3, "green");

        machine.swap(2, 3);

        machine.spin(2);
        assertEquals("red", machine.configuration()[1]);
    }

    /** Tests for Wheel types: Rebel */

    @Test
    public void shouldNotAllowRebelWheelToBeDeleted() {
        SlotMachine machine = new SlotMachine();
        machine.addWheel("rebel", 1);
        machine.addWheel("normal", 2);

        machine.addSymbol("normal", 1, "red");

        assertEquals(2, machine.configuration().length);
        machine.delWheel(1);
        assertEquals(2, machine.configuration().length);
    }

    @Test
    public void shouldNotAllowRebelWheelToBeSwapped() {
        SlotMachine machine = new SlotMachine();
        machine.addWheel("rebel", 1);
        machine.addWheel("normal", 2);

        machine.addSymbol("normal", 1, "red");
        machine.addSymbol("normal", 2, "blue");

        machine.placeSymbol(1, "red");
        machine.placeSymbol(2, "blue");

        machine.swap(1, 2);

        assertArrayEquals(new String[]{"red", "blue"}, machine.configuration());
    }

    @Test
    public void shouldNotLockRebelWheel() {
        SlotMachine machine = new SlotMachine();
        machine.addWheel("rebel", 1);
        machine.addSymbol("normal", 1, "red");
        machine.addSymbol("normal", 2, "blue");

        machine.placeSymbol(1, "red");
        machine.lock(1);

        machine.spin(1, 1);
        assertEquals("blue", machine.configuration()[0]);
    }

    /** Tests for Wheel types: Inverted */

    @Test
    public void shouldSpinInvertedWheelInOppositeDirection() {
        SlotMachine machine = new SlotMachine();
        machine.addWheel("inverted", 1);

        machine.addSymbol("normal", 1, "red");
        machine.addSymbol("normal", 2, "blue");
        machine.addSymbol("normal", 3, "green");

        machine.placeSymbol(1, "red");

        machine.spin(1, 1);
        assertEquals("green", machine.configuration()[0]);
    }

    /** Tests for Symbol types: Ephemeral */

    @Test
    public void shouldDecrementEphemeralSymbolSizeOnSpin() {
        SlotMachine machine = new SlotMachine();
        machine.addWheel("normal", 1);
        machine.addSymbol("ephemeral", 1, "red");

        machine.placeSymbol(1, "red");
        assertEquals("red", machine.configuration()[0]);

        for (int i = 0; i < 5; i++) {
            machine.spin(1, 1);
        }
        assertEquals("red", machine.configuration()[0]);
    }

    /** Tests for Symbol types: Shy */

    @Test
    public void shouldToggleShySymbolVisibilityWhenPlaced() {
        SlotMachine machine = new SlotMachine();
        machine.addWheel("normal", 1);
        machine.addSymbol("shy", 1, "red");

        machine.placeSymbol(1, "red");
        assertEquals("red", machine.configuration()[0]);
    }

    /** Negative and boundary tests */

    @Test
    public void shouldNotExceedMaximumWheels() {
        SlotMachine machine = new SlotMachine();
        for (int i = 1; i <= 5; i++) {
            machine.addWheel(i);
        }
        assertEquals(5, machine.configuration().length);

        machine.addWheel(6);
        assertEquals(5, machine.configuration().length);
    }

    @Test
    public void shouldNotExceedMaximumSymbols() {
        SlotMachine machine = new SlotMachine();
        machine.addWheel(1);

        String[] colors = {"red", "blue", "yellow", "green", "magenta", "orange", "cyan"};
        for (int i = 0; i < colors.length; i++) {
            machine.addSymbol(i + 1, colors[i]);
        }
        assertEquals(7, machine.symbols().length);

        machine.addSymbol(8, "pink");
        assertEquals(7, machine.symbols().length);
    }

    @Test
    public void shouldNotAddDuplicateSymbolColors() {
        SlotMachine machine = new SlotMachine();
        machine.addSymbol(1, "red");
        machine.addSymbol(2, "red");
        assertEquals(1, machine.symbols().length);
    }
}
