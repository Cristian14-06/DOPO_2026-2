package domain;

import domain.*;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class CamaleonTest {

    private EcoSafari safari;

    @Before
    public void setUp() {
        safari = new EcoSafari();
        // Limpieza del tablero
        for (int r = 0; r < safari.getSize(); r++) {
            for (int c = 0; c < safari.getSize(); c++) {
                safari.set(null, r, c);
            }
        }
    }

    @Test
    public void deberiaMoverseHorizontalmenteYConsumirEnergia() {
        // Given: Un camaleón recién creado en la celda (10, 5) con 100 de energía
        Camaleon camaleon = new Camaleon(safari, 10, 5);

        // When: Se avanza la simulación en un paso
        safari.ticTac();

        // Then: Debe estar en la celda (10, 6) y su energía debe ser 95
        int[] pos = safari.find(camaleon);
        assertNotNull(pos);
        assertEquals(10, pos[0]);
        assertEquals(6, pos[1]);
        assertEquals(95, camaleon.getEnergy());
    }

    @Test
    public void deberiaCambiarDeColorAlBajarEnergia() {
        // Given: Un camaleón en (5, 5) cuya energía se reduce manualmente por debajo de 50
        Camaleon camaleon = new Camaleon(safari, 5, 5);
        camaleon.changeEnergy(-60); // Energía pasa a 40

        // When: Se consulta su color
        java.awt.Color colorActual = camaleon.getColor();

        // Then: Su color debe ser amarillo
        assertEquals(java.awt.Color.YELLOW, colorActual);
        
    }
    
    @Test
    public void deberiaManejarCicloDeVidaYParejaDeCamaleonesHastaAgotamiento() {
        // Given: Dos camaleones nombrados Juan y Maria posicionados en (2, 2) y (4, 2)
        Camaleon juan = new Camaleon(safari, 2, 2);
        Camaleon maria = new Camaleon(safari, 4, 2);

        // When: Se ejecutan 20 ciclos ticTac (20 * 5 = 100 de energía consumida)
        for (int i = 0; i < 20; i++) {
            safari.ticTac();
        }

        // Then: Ambos camaleones se quedan sin energía y desaparecen del mapa
        assertNull("Juan debió desaparecer al agotar energía", safari.find(juan));
        assertNull("Maria debió desaparecer al agotar energía", safari.find(maria));
    }
}

