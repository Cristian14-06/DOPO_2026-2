import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Shared unit tests for Ciclo 2.
 *
 * @author Juan Espitia
 * @author Cristian Salamanca
 * @version 2.0
 */
public class SlotMachineCC2Test {

    /** Tests for adding and counting symbols */

    @Test
    public void addSymbolEsBaShouldAddSymbol() {
        SlotMachine machine = new SlotMachine();
        assertEquals(0, machine.distinctSymbols());
        machine.addSymbol(1, "red");
        assertEquals(1, machine.distinctSymbols());
    }

    /** Tests for symbol removal and duplicate handling */

    @Test
    public void addSymbolEsBaShouldNotAddRepeatedSymbol() {
        SlotMachine machine = new SlotMachine();
        machine.addSymbol(1, "red");
        machine.addSymbol(2, "red");
        assertEquals(1, machine.distinctSymbols());
    }

    @Test
    public void delSymbolEsBaShouldRemoveSymbol() {
        SlotMachine machine = new SlotMachine();
        machine.addSymbol(1, "red");
        machine.addSymbol(2, "blue");
        assertEquals(2, machine.distinctSymbols());
        machine.delSymbol("red");
        assertEquals(1, machine.distinctSymbols());
    }

    /** Tests for wheel swapping */

    @Test
    public void swapEsBaShouldSwapingWheels() {
        SlotMachine machine = new SlotMachine();
        machine.addWheel(1);
        machine.addWheel(2);
        machine.addSymbol(1, "red");
        machine.addSymbol(2, "blue");
        machine.placeSymbol(1, "red");
        machine.placeSymbol(2, "blue");

        assertArrayEquals(
            new String[]{"red", "blue"},
            machine.configuration()
        );

        machine.swap(1, 2);

        assertArrayEquals(
            new String[]{"blue", "red"},
            machine.configuration()
        );
    }

    /** Tests for spinning to a configuration */

    @Test
    public void spinConfigurationEsBaShouldBeTheSame() {
        SlotMachine machine = new SlotMachine();
        machine.addWheel(1);
        machine.addWheel(2);
        machine.addWheel(3);

        machine.addSymbol(1, "red");
        machine.addSymbol(2, "green");
        machine.addSymbol(3, "blue");

        machine.spin(new String[]{"green", "red", "blue"});

        assertArrayEquals(
            new String[]{"green", "red", "blue"},
            machine.configuration()
        );
    }
}
