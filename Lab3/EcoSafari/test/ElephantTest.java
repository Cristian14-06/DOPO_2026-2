package test;

import domain.*;
import org.junit.Before;
import org.junit.Test;
import java.awt.Color;
import static org.junit.Assert.*;

/**
 * Unit tests for Elephant class.
 *
 * @author Juan David Espitia, Cristian Salamanca
 * @version 1.0
 */
public class ElephantTest {

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
     * Verifies that the elephant moves diagonally and decreases energy by 10.
     */
    @Test
    public void shouldMoveDiagonallyAndDecreaseEnergy() {
        Elephant elephant = new Elephant(safari, 5, 5);

        safari.ticTac();

        int[] pos = safari.find(elephant);
        assertNotNull(pos);
        assertEquals(6, pos[0]);
        assertEquals(6, pos[1]);
        assertEquals(90, elephant.getEnergy());
    }

    /**
     * Verifies that the elephant disappears when its energy reaches zero.
     */
    @Test
    public void shouldDisappearWhenEnergyReachesZero() {
        Elephant elephant = new Elephant(safari, 0, 0);

        for (int i = 0; i < 10; i++) {
            safari.ticTac();
        }

        assertNull("Elephant should disappear when energy is exhausted", safari.find(elephant));
    }

    /**
     * Verifies that the elephant eats adjacent shrubs and recovers 10% energy.
     */
    @Test
    public void shouldEatAdjacentShrubAndGainTenPercentEnergy() {
        Elephant elephant = new Elephant(safari, 4, 4);
        Shrub shrub = new Shrub(safari, 5, 6);

        safari.ticTac();

        assertNull("Shrub should be eaten by elephant", safari.find(shrub));
        assertEquals(99, elephant.getEnergy());
    }

    /**
     * Verifies that the elephant changes color when its energy is below 80.
     */
    @Test
    public void shouldChangeColorWhenEnergyIsLow() {
        Elephant elephant = new Elephant(safari, 2, 2);
        Color highEnergyColor = elephant.getColor();
        assertEquals(new Color(112, 128, 144), highEnergyColor);

        elephant.changeEnergy(-30);
        Color lowEnergyColor = elephant.getColor();
        assertEquals(new Color(190, 190, 195), lowEnergyColor);
    }
}
