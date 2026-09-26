package test;

import domain.*;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Unit tests for SavannaElephant class.
 *
 * @author Juan David Espitia, Cristian Salamanca
 * @version 1.0
 */
public class SavannaElephantTest {

    /* Habitat fixture for tests */
    private EcoSafari safari;

    /**
     * Initializes a clean habitat before each test.
     */
    @Before
    public void setUp() {
        safari = new EcoSafari();
        for (int r = 0; r < safari.getSize(); r++) {
            for (int c = 0; c < safari.getSize(); c++) {
                safari.set(null, r, c);
            }
        }
    }

    /**
     * Verifies that the savanna elephant moves 2 diagonal steps per tick.
     */
    @Test
    public void shouldMoveTwoStepsPerTick() {
        SavannaElephant savannaElephant = new SavannaElephant(safari, 4, 4);

        safari.ticTac();

        int[] pos = safari.find(savannaElephant);
        assertNotNull(pos);
        assertEquals(6, pos[0]);
        assertEquals(6, pos[1]);
        assertEquals(90, savannaElephant.getEnergy());
    }

    /**
     * Verifies that the savanna elephant recovers 40% energy upon eating a shrub.
     */
    @Test
    public void shouldRecoverFortyPercentEnergyWhenEatingShrub() {
        SavannaElephant savannaElephant = new SavannaElephant(safari, 4, 4);
        savannaElephant.changeEnergy(-50);
        int initialEnergy = savannaElephant.getEnergy();

        Shrub shrub = new Shrub(safari, 6, 7);

        safari.ticTac();

        assertNull("Shrub should be eaten", safari.find(shrub));
        assertEquals(56, savannaElephant.getEnergy());
    }

    /**
     * Verifies the class inheritance hierarchy.
     */
    @Test
    public void shouldInheritFromElephantAndAnimal() {
        SavannaElephant savannaElephant = new SavannaElephant(safari, 0, 0);

        assertTrue(savannaElephant instanceof Elephant);
        assertTrue(savannaElephant instanceof Animal);
        assertEquals("Elephant", savannaElephant.type());
    }
}
