package domain;

import domain.*;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class TormentaTest {

    private EcoSafari safari;

    @Before
    public void setUp() {
        safari = new EcoSafari();
        // Limpia casillas iniciales si alguna entidad fue creada automáticamente
        for (int r = 0; r < safari.getSize(); r++) {
            for (int c = 0; c < safari.getSize(); c++) {
                safari.set(null, r, c);
            }
        }
    }

    @Test
    public void deberiaMoverseDiagonalNoresteConWrapAround() {
        // Given: Una tormenta en la esquina superior izquierda (0, 0)
        Tormenta thor = new Tormenta(safari, 0, 0);

        // When: Se ejecuta un ciclo ticTac
        safari.ticTac();

        // Then: Debe moverse circularmente a (SIZE-1, 1) -> (24, 1)
        int[] pos = safari.find(thor);
        assertNotNull(pos);
        assertEquals(24, pos[0]);
        assertEquals(1, pos[1]);
    }

        @Test
    public void deberiaDestruirElefanteEnSuCentro() {
        // Given: Una tormenta en (5, 5) y un elefante en (3, 5)
        // El elefante se moverá a (4, 6) en su turno, justo a donde llegará la tormenta
        Tormenta thor = new Tormenta(safari, 5, 5);
        Elephant elefante = new Elephant(safari, 3, 5);
    
        // When: Se avanza la simulación
        safari.ticTac();
    
        // Then: El elefante es interceptado y destruido en (4, 6)
        assertNull(safari.find(elefante));
        int[] posTormenta = safari.find(thor);
        assertNotNull(posTormenta);
        assertEquals(4, posTormenta[0]);
        assertEquals(6, posTormenta[1]);
    }
}
