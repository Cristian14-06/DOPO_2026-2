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

    /**
     * Verifies that when a storm passes over a prairie cell, it restores Ground upon leaving.
     */
    @Test
    public void shouldRestoreGroundWhenLeavingPrairieTerrain() {
        new Ground(safari, 9, 11);
        new Ground(safari, 8, 12);
        Storm storm = new Storm(safari, 10, 10);

        safari.ticTac();
        assertEquals(storm, safari.get(9, 11));

        safari.ticTac();
        assertEquals(storm, safari.get(8, 12));
        Entity vacatedCell = safari.get(9, 11);
        assertNotNull("Prairie cell vacated by storm must not be null", vacatedCell);
        assertEquals("Ground", vacatedCell.type());
    }
}
