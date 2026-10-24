import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Unit tests for SlotMachine features.
 *
 * @author Juan Espitia
 * @author Cristian Salamanca
 */
public class SlotMachineC4Test {

    /**
     * Verifies that Lefty copies the left neighbor wheel when spinning.
     */
    @Test
    public void shouldCopyLeftNeighborWhenLeftySpins() {
        SlotMachine machine = new SlotMachine();
        machine.addWheel("normal", 1);
         machine.addWheel("lefty", 2);

        machine.addSymbol("normal", 1, "red");
        machine.addSymbol("normal", 2, "blue");

        machine.placeSymbol(1, "red");
        machine.placeSymbol(2, "blue" );

        assertArrayEquals(new String[]{"red", "blue"}, machine.configuration());

        machine.spin(2);

        assertEquals("red", machine.configuration()[1]);
    }

    /**
     * Verifies that Lefty spins normally when it has no wheel to its left.
     */
    @Test
    public void shouldSpinNormallyWhenLeftyHasNoLeftNeighbor(){
        SlotMachine machine = new SlotMachine();
        machine.addWheel("lefty", 1);
        machine.addSymbol("normal", 1, "red");
         machine.addSymbol("normal", 2, "blue");

        machine.placeSymbol(1, "red");
        assertEquals("red", machine.configuration()[0]);

        machine.spin(1, 1);
        assertEquals("blue", machine.configuration()[0]);
    }

    /**
     * Verifies that Lefty updates its neighbor after deleting a wheel.
     */
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

    /**
     * Verifies that Lefty updates its left reference after a swap.
     */
    @Test
    public void shouldUpdateLeftyNeighborAfterSwap() {
        SlotMachine machine = new SlotMachine();
        machine.addWheel("normal", 1);
        machine.addWheel("normal", 2);
        machine.addWheel("lefty", 3 );

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

    /**
     * Checks that a Rebel wheel cannot be deleted.
     */
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

    /**
     * Checks that a Rebel wheel cannot be swapped.
     */
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

    /**
     * Checks that a Rebel wheel ignores locking.
     */
    @Test
    public void shouldNotLockRebelWheel(){
        SlotMachine machine = new SlotMachine();
        machine.addWheel("rebel", 1);
        machine.addSymbol("normal", 1, "red");
        machine.addSymbol("normal", 2, "blue");

        machine.placeSymbol(1, "red");
         machine.lock(1);

        machine.spin(1, 1);
        assertEquals("blue", machine.configuration()[0]);
    }

    /**
     * Verifies that the Inverted wheel spins in the opposite direction.
     */
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

    /**
     * Verifies that the Ephemeral symbol reduces its size when spinning.
     */
    @Test
    public void shouldDecrementEphemeralSymbolSizeOnSpin() {
        SlotMachine machine = new SlotMachine();
        machine.addWheel("normal", 1);
        machine.addSymbol("ephemeral", 1, "red");

        machine.placeSymbol(1, "red" );
        assertEquals("red", machine.configuration()[0]);

        for(int i = 0; i < 5; i++) {
             machine.spin(1, 1);
        }
        assertEquals("red", machine.configuration()[0]);
    }

    /**
     * Verifies that the Shy symbol toggles its visibility when placed.
     */
    @Test
    public void shouldToggleShySymbolVisibilityWhenPlaced() {
        SlotMachine machine = new SlotMachine();
        machine.addWheel("normal", 1);
         machine.addSymbol("shy", 1, "red");

        machine.placeSymbol(1, "red");
        assertEquals("red", machine.configuration()[0]);
    }

    /**
     * Checks that the maximum of 5 wheels in the machine is not exceeded.
     */
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

    /**
     * Checks that the limit of 7 symbols is not exceeded.
     */
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

    /**
     * Verifies that symbols with duplicate colors are not added.
     */
    @Test
    public void shouldNotAddDuplicateSymbolColors() {
        SlotMachine machine = new SlotMachine();
        machine.addSymbol(1, "red");
         machine.addSymbol(2, "red");
        assertEquals(1, machine.symbols().length);
    }

    /**
     * Verifies that a locked Lefty wheel does not spin in any mode.
     */
    @Test
    public void shouldNotSpinLockedLeftyWheel() {
        SlotMachine machine = new SlotMachine();
        machine.addWheel("normal", 1);
        machine.addWheel("lefty", 2);
        machine.addSymbol("normal", 1, "red");
        machine.addSymbol("normal", 2, "blue");

        machine.placeSymbol(1, "red");
         machine.placeSymbol(2, "blue");
        machine.lock(2);

        machine.spin(2);
        assertEquals("blue", machine.configuration()[1]);

        machine.spin(2, 1);
        assertEquals("blue", machine.configuration()[1]);

        machine.spin(new String[]{"red", "red"});
        assertEquals("blue", machine.configuration()[1]);

        machine.unlock(2);
         machine.spin(2, 1);
        assertEquals("red", machine.configuration()[1]);
    }

    /**
     * Tests all spin modes of Lefty with and without a left neighbor.
     */
    @Test
    public void shouldSpinLeftyInAllModesWithAndWithoutNeighbor() {
        SlotMachine machine = new SlotMachine();
        machine.addWheel("lefty", 1);
        machine.addSymbol("ephemeral", 1, "red");
        machine.addSymbol("normal", 2, "blue");

        machine.placeSymbol(1, "red");
         machine.spin(1);
        assertNotNull(machine.configuration()[0]);

        machine.spin(new String[]{"blue"});
        assertEquals("blue", machine.configuration()[0]);

        machine.addWheel("lefty", 2);
        machine.placeSymbol(1, "red");
        machine.placeSymbol(2, "blue");
        machine.spin(new String[]{"red", "blue"});
         assertEquals("red", machine.configuration()[1]);

        Lefty customLefty = new Lefty(new Wheel());
        assertEquals("lefty", customLefty.getType());
    }

    /**
     * Verifies locking and unlocking a normal wheel across all spin modes.
     */
    @Test
    public void shouldLockAndUnlockNormalWheelInAllSpinModes() {
        SlotMachine machine = new SlotMachine();
        machine.addWheel("normal", 1);
        machine.addWheel("normal", 2);
        machine.addSymbol("normal", 1, "red");
         machine.addSymbol("normal", 2, "blue");

        machine.placeSymbol(1, "red");
        machine.placeSymbol(2, "red");
        machine.lock(1);

        machine.spin(1);
        assertEquals("red", machine.configuration()[0]);

        machine.spin(1, 1);
        assertEquals("red", machine.configuration()[0]);

        machine.spin();
        assertEquals("red", machine.configuration()[0]);

        machine.spin(new String[]{"blue"});
         assertEquals("red", machine.configuration()[0]);

        machine.unlock(1);
        machine.spin(1, 1);
        assertEquals("blue", machine.configuration()[0]);
    }

    /**
     * Tests direct operations on Ephemeral, Shy, and normal symbols.
     */
    @Test
    public void shouldExerciseEphemeralShyAndSymbolOperations() {
        SlotMachine machine = new SlotMachine();
        machine.addWheel("normal", 1);
        machine.addWheel("rebel", 2);
        machine.addWheel("inverted", 3);

        machine.addSymbol("ephemeral", 1, "red");
         machine.addSymbol("shy", 2, "blue");
        machine.addSymbol("normal", 3, "green");

        machine.placeSymbol(1, "red");
        machine.placeSymbol(2, "blue");
        machine.placeSymbol(3, "green");

        machine.makeVisible();
        machine.spin(1, 1);
         machine.spin(2, 1);
        machine.makeInvisible();

        Ephemeral eph = new Ephemeral(new Circle(20, 0, 0, "red"), "red");
        assertEquals("ephemeral", eph.nature());
        assertTrue(eph.hasContorn());
        for (int i = 0; i < 25; i++) {
            eph.decrement();
        }
        assertEquals(1, eph.getSize());

        Shy shy = new Shy(new Rectangle(20, 20, 0, 0, "blue"), "blue");
        assertEquals("shy", shy.nature());
         assertTrue(shy.hasContorn());
        assertTrue(shy.getCanBeVisible());
        shy.verifyCanBeVisible();
        assertFalse(shy.getCanBeVisible());

        Symbol norm = new Symbol(new Triangle(20, 20, 0, 0, "green"), "green");
        assertEquals("normal", norm.nature());
        assertFalse(norm.hasContorn());
        assertTrue(norm.toString().contains("green"));
    }

    /**
     * Directly tests helper methods and validations of Wheel.
     */
    @Test
    public void shouldExerciseWheelMethodsDirectly() {
        Wheel w = new Wheel();
        Rebel r = new Rebel();
         Inverted inv = new Inverted();

        assertEquals("normal", w.getType());
        assertEquals("rebel", r.getType());
        assertEquals("inverted", inv.getType());

        assertFalse(w.addSymbol(1, null));
        Symbol s1 = new Symbol(new Circle(20, 0, 0, "red"), "red");
        Symbol s2 = new Symbol(new Rectangle(20, 20, 0, 0, "blue"), "blue");
        assertTrue(w.addSymbol(0, s1));
        assertTrue(w.addSymbol(10, s2));
        assertEquals(2, w.getNumberSymbols());
         assertNotNull(w.getSymbol(0));
        assertNull(w.getSymbol(-1));
        assertNull(w.getSymbol(10));
        assertFalse(w.isFull());

        w.setCurrentSymbol(-1);
        w.setCurrentSymbol(10);
        w.setCurrentSymbol(1);
        assertEquals(1, w.getCurrentSymbol());

        w.spin("");
        w.spin("nonexistent");
         w.placeSymbol(null);

        assertFalse(w.delSymbol(-1));
        assertFalse(w.delSymbol(10));
        assertFalse(w.delSymbol(null));
        assertTrue(w.delSymbol(s1));
    }

    /**
     * Tests boundary and empty machine cases in SlotMachine.
     */
    @Test
    public void shouldHandleBoundaryAndEmptyCasesInSlotMachine() {
        SlotMachine invalidLow = new SlotMachine(0);
        assertEquals(0, invalidLow.configuration().length);

        SlotMachine invalidHigh = new SlotMachine(6);
         assertEquals(0, invalidHigh.configuration().length);

        SlotMachine empty = new SlotMachine();
        empty.delWheel(1);
        empty.placeSymbol(1, "red");
        empty.spin(1);
        empty.spin(1, 2);
        empty.swap(1, 2);
        empty.lock(1);
        empty.unlock(1);
        assertFalse(empty.isJackpot());

        empty.addWheel(0);
         empty.addWheel(10);
        assertFalse(empty.isJackpot());

        empty.addSymbol(0, "red");
        empty.addSymbol(10, "blue");
        empty.placeSymbol(0, "RED");
        empty.placeSymbol(10, "BLUE");
        empty.placeSymbol(1, "unknown");
        empty.delSymbol("unknown");

        empty.spin(0);
        empty.spin(10);
        empty.spin(0, 1);
         empty.spin(10, 1);
        empty.spin(new String[]{"red", "blue", "green"});
        empty.spin(new String[]{"red"});

        empty.swap(0, 1);
        empty.swap(1, 10);
        empty.lock(0);
        empty.lock(10);
        empty.unlock(0);
        empty.unlock(10);

        empty.delSymbol("RED");
         empty.delWheel(0);
        empty.delWheel(10);
        assertEquals(0, empty.configuration().length);
    }
}
