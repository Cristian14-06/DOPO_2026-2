package test;

import domain.*;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Unit tests for Prairie rules.
 *
 * @author Juan David Espitia, Cristian Salamanca
 * @version 1.0
 */
public class PrairieTest {

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
     * Verifies that animals move over Ground and exchange cell positions.
     */
    @Test
    public void shouldMoveAnimalsOverGroundAndSwapPositions() {
        new Ground(safari, 10, 10);
        new Ground(safari, 9, 10);
        Lion lion = new Lion(safari, 10, 10);

        safari.ticTac();

        assertEquals(lion, safari.get(9, 10));
        assertNotNull(safari.get(10, 10));
        assertEquals("Ground", safari.get(10, 10).type());
    }

    /**
     * Verifies that Zebras advance 2 steps over Ground tiles.
     */
    @Test
    public void shouldMoveZebraTwoStepsOverGround() {
        new Ground(safari, 15, 10);
        new Ground(safari, 14, 10);
        new Ground(safari, 13, 10);
        Zebra zebra = new Zebra(safari, 15, 10);

        safari.ticTac();

        assertEquals(zebra, safari.get(13, 10));
        assertNotNull(safari.get(15, 10));
        assertEquals("Ground", safari.get(15, 10).type());
    }

    /**
     * Verifies that two animals of the same species separated by a Ground tile reproduce.
     */
    @Test
    public void shouldReproduceAnimalWhenSeparatedByGroundCell() {
        Zebra zebra1 = new Zebra(safari, 10, 9);
        Zebra zebra2 = new Zebra(safari, 10, 11);
        Ground middleGround = new Ground(safari, 10, 10);

        middleGround.tic();

        Entity entityAtMiddle = safari.get(10, 10);
        assertNotNull(entityAtMiddle);
        assertEquals("Zebra", entityAtMiddle.type());
    }

    /**
     * Verifies Rule 9: when an animal dies from running out of energy, its cell is replaced by Ground.
     */
    @Test
    public void shouldReplaceAnimalWithGroundWhenDyingOfEnergy() {
        new Ground(safari, 10, 10);
        new Ground(safari, 9, 10);
        Lion lion = new Lion(safari, 10, 10);
        lion.changeEnergy(-90);

        safari.ticTac();

        assertNull("Dead lion should not be found in habitat", safari.find(lion));
        Entity cellAfterDeath = safari.get(9, 10);
        assertNotNull("Cell should be replaced by Ground upon animal death", cellAfterDeath);
        assertEquals("Ground", cellAfterDeath.type());
    }
}
