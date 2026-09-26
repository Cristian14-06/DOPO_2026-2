package test;

import domain.*;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class ChameleonTest {

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
    public void deberiaMoverseHorizontalmenteYConsumirEnergia() {
        Chameleon chameleon = new Chameleon(safari, 10, 5);

        safari.ticTac();

        int[] pos = safari.find(chameleon);
        assertNotNull(pos);
        assertEquals(10, pos[0]);
        assertEquals(6, pos[1]);
        assertEquals(95, chameleon.getEnergy());
    }

    @Test
    public void deberiaCambiarDeColorAlBajarEnergia() {
        Chameleon chameleon = new Chameleon(safari, 5, 5);
        chameleon.changeEnergy(-60);

        java.awt.Color colorActual = chameleon.getColor();

        assertEquals(java.awt.Color.YELLOW, colorActual);
    }
    
    @Test
    public void deberiaManejarCicloDeVidaYParejaDeCamaleonesHastaAgotamiento() {
        Chameleon juan = new Chameleon(safari, 2, 2);
        Chameleon maria = new Chameleon(safari, 4, 2);

        for (int i = 0; i < 20; i++) {
            safari.ticTac();
        }

        assertNull("Juan debió desaparecer al agotar energía", safari.find(juan));
        assertNull("Maria debió desaparecer al agotar energía", safari.find(maria));
    }
}
