package test;

import domain.*;
import org.junit.Before;
import org.junit.Test;
import java.awt.Color;
import static org.junit.Assert.*;

/**
 * Unit tests for Chameleon class.
 *
 * @author Juan David Espitia, Cristian Salamanca
 * @version 1.0
 */
public class ChameleonTest {

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
     * Verifies horizontal movement and energy consumption.
     */
    @Test
    public void shouldMoveHorizontallyAndConsumeEnergy() {
        Chameleon chameleon = new Chameleon(safari, 10, 5);

        safari.ticTac();

        int[] pos = safari.find(chameleon);
        assertNotNull(pos);
        assertEquals(10, pos[0]);
        assertEquals(6, pos[1]);
        assertEquals(95, chameleon.getEnergy());
    }

    /**
     * Verifies color transition when energy drops below 50.
     */
    @Test
    public void shouldChangeColorWhenEnergyDecreases() {
        Chameleon chameleon = new Chameleon(safari, 5, 5);
        chameleon.changeEnergy(-60);

        Color actualColor = chameleon.getColor();

        assertEquals(new Color(255, 165, 0), actualColor);
    }

    /**
     * Verifies that chameleons disappear after exhausting their energy across multiple tics.
     */
    @Test
    public void shouldHandleLifeCycleAndPairOfChameleonsUntilExhaustion() {
        Chameleon juan = new Chameleon(safari, 2, 2);
        Chameleon maria = new Chameleon(safari, 4, 2);

        for (int i = 0; i < 20; i++) {
            safari.ticTac();
        }

        assertNull("Juan should have disappeared when running out of energy", safari.find(juan));
        assertNull("Maria should have disappeared when running out of energy", safari.find(maria));
    }
}
