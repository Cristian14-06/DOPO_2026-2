package test;

import domain.*;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Unit tests for EcoSafariException.
 *
 * @author Juan David Espitia, Cristian Salamanca
 * @version 1.0
 */
public class EcoSafariExceptionTest {

    /* Habitat fixture for tests */
    private EcoSafari safari;

    /**
     * Initializes a clean habitat before each test.
     */
    @Before
    public void setUp() {
        safari = new EcoSafari();
    }

    /**
     * Verifies that placing an entity out of bounds throws EcoSafariException with OUT_OF_BOUNDS message.
     */
    @Test
    public void shouldThrowExceptionWhenPlacingOutOfBounds() {
        try {
            Elephant elephant = new Elephant(safari, 0, 0);
            safari.addEntity(elephant, -1, 5);
            fail("Expected EcoSafariException for out of bounds position");
        } catch (EcoSafariException e) {
            assertEquals(EcoSafariException.OUT_OF_BOUNDS, e.getMessage());
        }
    }

    /**
     * Verifies that placing an entity on an occupied cell throws EcoSafariException with CELL_OCCUPIED message.
     */
    @Test
    public void shouldThrowExceptionWhenCellIsOccupied() {
        try {
            Elephant elephant1 = new Elephant(safari, 7, 7);
            Elephant elephant2 = new Elephant(safari, 0, 0);
            safari.addEntity(elephant2, 7, 7);
            fail("Expected EcoSafariException for occupied cell");
        } catch (EcoSafariException e) {
            assertEquals(EcoSafariException.CELL_OCCUPIED, e.getMessage());
        }
    }

    /**
     * Verifies that querying the position of an unplaced entity throws EcoSafariException with ENTITY_NOT_FOUND.
     */
    @Test
    public void shouldThrowExceptionWhenEntityNotFound() {
        try {
            EcoSafari otherSafari = new EcoSafari();
            Elephant orphan = new Elephant(otherSafari, 3, 3);
            safari.getPosition(orphan);
            fail("Expected EcoSafariException when entity is not found");
        } catch (EcoSafariException e) {
            assertEquals(EcoSafariException.ENTITY_NOT_FOUND, e.getMessage());
        }
    }
}
