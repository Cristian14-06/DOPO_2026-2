package test;

import domain.*;
import org.junit.Before;
import org.junit.Test;
import java.awt.Color;
import static org.junit.Assert.*;

/**
 * Unit tests for Shrub class.
 *
 * @author Juan David Espitia, Cristian Salamanca
 * @version 1.0
 */
public class ShrubTest {

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
     * Verifies that the shrub turns from green to yellow upon aging (energy <= 60).
     */
    @Test
    public void shouldChangeColorFromGreenToYellowWhenAging() {
        Shrub shrub = new Shrub(safari, 5, 5);
        assertEquals(new Color(34, 139, 34), shrub.getColor());

        shrub.changeEnergy(-40);
        assertEquals(new Color(204, 153, 0), shrub.getColor());
    }

    /**
     * Verifies that the shrub moves North first when the northern cell is free.
     */
    @Test
    public void shouldPrioritizeMovementNorthWhenEmpty() {
        Shrub shrub = new Shrub(safari, 10, 10);
        shrub.changeEnergy(-20);

        safari.ticTac();

        int[] pos = safari.find(shrub);
        assertNotNull(pos);
        assertEquals(9, pos[0]);
        assertEquals(10, pos[1]);
    }

    /**
     * Verifies that East has priority over West when North and South cells are blocked.
     */
    @Test
    public void shouldPrioritizeEastOverWestWhenNorthAndSouthBlocked() {
        Shrub shrub = new Shrub(safari, 10, 10);
        new Ground(safari, 9, 10);
        new Ground(safari, 11, 10);
        shrub.changeEnergy(-20);

        safari.ticTac();

        int[] pos = safari.find(shrub);
        assertNotNull(pos);
        assertEquals(10, pos[0]);
        assertEquals(11, pos[1]);
    }

    /**
     * Verifies that shrubs can only be consumed by Elephants.
     */
    @Test
    public void shouldBeEatenOnlyByElephant() {
        Shrub shrub = new Shrub(safari, 5, 5);
        Elephant elephant = new Elephant(safari, 0, 0);
        Lion lion = new Lion(safari, 1, 1);
        Zebra zebra = new Zebra(safari, 2, 2);

        assertTrue(shrub.canBeEaten(elephant));
        assertFalse(shrub.canBeEaten(lion));
        assertFalse(shrub.canBeEaten(zebra));
        assertFalse(shrub.canBeEaten(null));
    }
}
