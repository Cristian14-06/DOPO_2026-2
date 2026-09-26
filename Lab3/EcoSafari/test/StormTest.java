package test;

import domain.*;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Unit tests for Storm class.
 *
 * @author Juan David Espitia, Cristian Salamanca
 * @version 1.0
 */
public class StormTest {

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
     * Verifies northeast movement with toroidal wrap-around at grid edges.
     */
    @Test
    public void shouldMoveDiagonalNortheastWithWrapAround() {
        Storm thor = new Storm(safari, 0, 0);

        safari.ticTac();

        int[] pos = safari.find(thor);
        assertNotNull(pos);
        assertEquals(24, pos[0]);
        assertEquals(1, pos[1]);
    }

    /**
     * Verifies that the storm destroys any entity occupying its center cell.
     */
    @Test
    public void shouldDestroyElephantAtItsCenter() {
        Storm thor = new Storm(safari, 5, 5);
        Elephant elephant = new Elephant(safari, 3, 5);

        safari.ticTac();

        assertNull(safari.find(elephant));
        int[] posStorm = safari.find(thor);
        assertNotNull(posStorm);
        assertEquals(4, posStorm[0]);
        assertEquals(6, posStorm[1]);
    }
}
