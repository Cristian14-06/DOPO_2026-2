package test;

import domain.*;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class StormTest {

    private EcoSafari safari;

    @Before
    public void setUp() {
        safari = new EcoSafari();
        for (int r = 0; r < safari.getSize(); r++) {
            for (int c = 0; c < safari.getSize(); c++) {
                safari.set(null, r, c);
            }
        }
    }

    @Test
    public void deberiaMoverseDiagonalNoresteConWrapAround() {
        Storm thor = new Storm(safari, 0, 0);

        safari.ticTac();

        int[] pos = safari.find(thor);
        assertNotNull(pos);
        assertEquals(24, pos[0]);
        assertEquals(1, pos[1]);
    }

    @Test
    public void deberiaDestruirElefanteEnSuCentro() {
        Storm thor = new Storm(safari, 5, 5);
        Elephant elefante = new Elephant(safari, 3, 5);

        safari.ticTac();

        assertNull(safari.find(elefante));
        int[] posTormenta = safari.find(thor);
        assertNotNull(posTormenta);
        assertEquals(4, posTormenta[0]);
        assertEquals(6, posTormenta[1]);
    }
}
