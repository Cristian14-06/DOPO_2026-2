package test;

import domain.*;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Unit tests for predation rules across EcoSafari entities.
 *
 * @author Juan David Espitia, Cristian Salamanca
 * @version 1.0
 */
public class PredationTest {

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
     * Verifies that when an Elephant eats a Shrub, the cell is replaced by Ground.
     */
    @Test
    public void shouldReplaceShrubWithGroundWhenEatenByElephant() {
        Elephant elephant = new Elephant(safari, 10, 10);
        Shrub shrub = new Shrub(safari, 10, 11);

        elephant.tic();

        assertNull(safari.find(shrub));
        Entity entityAtCell = safari.get(10, 11);
        assertNotNull(entityAtCell);
        assertTrue(entityAtCell instanceof Ground);
        assertEquals("Ground", entityAtCell.type());
    }

    /**
     * Verifies that when a Zebra eats Grass, the cell is replaced by Ground and Zebra recovers energy.
     */
    @Test
    public void shouldReplaceGrassWithGroundWhenEatenByZebra() {
        Zebra zebra = new Zebra(safari, 5, 5);
        Grass grass = new Grass(safari, 5, 6);
        zebra.changeEnergy(-30);
        int energyBefore = zebra.getEnergy();

        zebra.tic();

        assertNull(safari.find(grass));
        Entity entityAtCell = safari.get(5, 6);
        assertNotNull(entityAtCell);
        assertTrue(entityAtCell instanceof Ground);
        assertEquals("Ground", entityAtCell.type());
        assertTrue(zebra.getEnergy() > energyBefore);
    }

    /**
     * Verifies that when a Lion eats a Zebra, the cell is replaced by Ground and Lion gains energy.
     */
    @Test
    public void shouldReplaceZebraWithGroundWhenEatenByLion() {
        Lion lion = new Lion(safari, 8, 8);
        Zebra zebra = new Zebra(safari, 8, 9);
        lion.changeEnergy(-40);
        int energyBefore = lion.getEnergy();

        lion.tic();

        assertNull(safari.find(zebra));
        Entity entityAtCell = safari.get(8, 9);
        assertNotNull(entityAtCell);
        assertTrue(entityAtCell instanceof Ground);
        assertEquals("Ground", entityAtCell.type());
        assertTrue(lion.getEnergy() > energyBefore);
    }

    /**
     * Verifies that predators cannot eat incompatible entities.
     */
    @Test
    public void shouldNotEatEntitiesThatCannotBeEaten() {
        Lion lion = new Lion(safari, 15, 15);
        Elephant elephant = new Elephant(safari, 15, 16);

        lion.tic();

        assertNotNull(safari.find(elephant));
        assertEquals(elephant, safari.get(15, 16));
        assertFalse(elephant.canBeEaten(lion));
    }

    /**
     * Verifies null safety when checking predation eligibility.
     */
    @Test
    public void shouldReturnFalseWhenPredatorIsNull() {
        Shrub shrub = new Shrub(safari, 18, 18);
        Grass grass = new Grass(safari, 19, 19);
        Zebra zebra = new Zebra(safari, 20, 20);
        Elephant elephant = new Elephant(safari, 21, 21);

        assertFalse(shrub.canBeEaten(null));
        assertFalse(grass.canBeEaten(null));
        assertFalse(zebra.canBeEaten(null));
        assertFalse(elephant.canBeEaten(null));
    }
}
