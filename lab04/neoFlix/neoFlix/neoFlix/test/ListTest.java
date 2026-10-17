package test;

import domain.*;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

/**
 * Reverse-engineering unit tests for the catalog listing functionality
 * in the domain layer (NeoFlix.toString).
 * 
 * @author Juan Espitia, Cristian Salamanca
 */
public class ListTest {

    private NeoFlix neoFlix;

    /**
     * Initializes a default NeoFlix catalog instance before each test.
     */
    @Before
    public void setUp() {
        neoFlix = new NeoFlix();
    }

    /**
     * Verifies that listing the initial catalog contains the expected number
     * of elements and all preloaded titles.
     */
    @Test
    public void shouldListInitialCatalog() {
        String listing = neoFlix.toString();
        
        assertNotNull(listing);
        assertTrue("Debe iniciar indicando 6 elementos", listing.startsWith("6 elementos"));
        assertTrue("Debe incluir el primer episodio", listing.contains("The Hidden Village"));
        assertTrue("Debe incluir el segundo episodio", listing.contains("The First Mission"));
        assertTrue("Debe incluir el tercer episodio", listing.contains("The Rival"));
        assertTrue("Debe incluir el cuarto episodio", listing.contains("The Tournament"));
        assertTrue("Debe incluir el quinto episodio", listing.contains("The Final Battle"));
        assertTrue("Debe incluir la serie inicial con su año", listing.contains("The Neo Ninja: 2020"));
    }

    /**
     * Verifies that after adding a new episode, the catalog listing updates
     * the element count and includes the new episode title.
     * 
     * @throws NeoFlixException if adding the episode fails unexpectedly
     */
    @Test
    public void shouldUpdateListAfterAddingEpisode() throws NeoFlixException {
        neoFlix.addEpisode("The Awakening", "The Neo Ninja", "100", "85", "40", "320");
        String listing = neoFlix.toString();
        
        assertTrue("Debe reflejar 7 elementos tras adicionar un episodio", listing.startsWith("7 elementos"));
        assertTrue("Debe incluir el nuevo episodio en el listado", listing.contains("The Awakening"));
    }

    /**
     * Verifies that after adding a new series, the catalog listing updates
     * the element count and includes the formatted series entry.
     * 
     * @throws NeoFlixException if adding the series fails unexpectedly
     */
    @Test
    public void shouldUpdateListAfterAddingSeries() throws NeoFlixException {
        String episodios = "The Hidden Village\nThe First Mission";
        neoFlix.addSeries("Cyber Quest", "2025", episodios);
        String listing = neoFlix.toString();
        
        assertTrue("Debe reflejar 7 elementos tras adicionar una serie", listing.startsWith("7 elementos"));
        assertTrue("Debe incluir el título y año de la nueva serie", listing.contains("Cyber Quest: 2025"));
    }

    /**
     * Verifies that each listed element maintains the '>' prefix format
     * according to the data/toString specification.
     */
    @Test
    public void shouldFormatEntriesWithPrefix() {
        String listing = neoFlix.toString();
        
        assertTrue("Los episodios deben tener el prefijo '>'", listing.contains(">The Hidden Village"));
        assertTrue("Las series deben tener el prefijo '>'", listing.contains(">The Neo Ninja: 2020"));
    }
}
