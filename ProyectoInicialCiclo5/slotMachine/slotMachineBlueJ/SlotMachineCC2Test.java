import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Shared unit tests for Ciclo 2.
 *
 * @author Juan Espitia
 * @author Cristian Salamanca
 */
public class SlotMachineCC2Test {

    /**
     * Verifies that a symbol is added and counted properly.
     */
    @Test
    public void addSymbolEsBaShouldAddSymbol() {
        SlotMachine machine = new SlotMachine();
         assertEquals(0, machine.distinctSymbols());
        machine.addSymbol(1, "red");
        assertEquals(1, machine.distinctSymbols());
    }

    /**
     * Verifies that adding a repeated symbol is not allowed.
     */
    @Test
    public void addSymbolEsBaShouldNotAddRepeatedSymbol(){
        SlotMachine machine = new SlotMachine();
        machine.addSymbol(1, "red");
         machine.addSymbol(2, "red" );
        assertEquals(1, machine.distinctSymbols());
    }

    /**
     * Verifies removing a symbol by its color.
     */
    @Test
    public void delSymbolEsBaShouldRemoveSymbol() {
        SlotMachine machine = new SlotMachine();
        machine.addSymbol(1, "red");
        machine.addSymbol(2, "blue");
         assertEquals(2, machine.distinctSymbols());
        machine.delSymbol("red");
        assertEquals(1, machine.distinctSymbols());
    }

    /**
     * Checks swapping positions between two wheels.
     */
    @Test
    public void swapEsBaShouldSwapingWheels() {
        SlotMachine machine = new SlotMachine();
        machine.addWheel(1);
         machine.addWheel(2);
        machine.addSymbol(1, "red");
        machine.addSymbol(2, "blue");
        machine.placeSymbol(1, "red");
        machine.placeSymbol(2, "blue");

        assertArrayEquals(new String[]{"red", "blue"}, machine.configuration());

        machine.swap(1, 2);

        assertArrayEquals(new String[]{"blue", "red"}, machine.configuration());
    }

    /**
     * Verifies that spinning to a configuration sets the given colors.
     */
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

        assertArrayEquals(new String[]{"green", "red", "blue"}, machine.configuration());
    }
}
