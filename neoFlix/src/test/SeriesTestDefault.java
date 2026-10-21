package test;

import domain.Episode;
import domain.NeoFlixException;
import domain.Series;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Unit tests for the Series rating calculation using a default value for unknown episodes.
 * 
 * @author Juan Espitia, Cristian Salamanca
 */
public class SeriesTestDefault {

    private Series series;

    /**
     * Initializes a default Series instance before each test.
     */
    @Before
    public void setUp() {
        series = new Series("The Neo Ninja", 2020);
    }

    /**
     * Verifies that episodes with unknown ratings are replaced with the given default value.
     * 
     * @throws NeoFlixException if the rating calculation fails unexpectedly
     */
    @Test
    public void shouldCalculateRatingReplacingUnknownWithDefaultValue() throws NeoFlixException {
       
        series.addEpisode(new Episode("Ep 1", series, 100, 80, 50, 400));
        
        series.addEpisode(new Episode("Ep 2", series, 100, 80, 40, 240));
        
        series.addEpisode(new Episode("Ep 3", series, 100, 80, 0, 0));

        
        int result = series.rating(10);
        assertEquals(8, result);
    }

    /**
     * Verifies that episodes with corrupted data are ignored when calculating the rating with a default value.
     * 
     * @throws NeoFlixException if the rating calculation fails unexpectedly
     */
    @Test
    public void shouldIgnoreEpisodesWithDataErrorInDefaultRating() throws NeoFlixException {
        
        series.addEpisode(new Episode("Ep 1", series, 100, 80, 50, 400));
        
        series.addEpisode(new Episode("Ep 2", series, 100, 80, 0, 0));
        
        series.addEpisode(new Episode("Ep 3", series, 100, 80, 10, 200));

        
        int result = series.rating(4);
        assertEquals(6, result);
    }

    /**
     * Verifies that an exception is thrown when the series has no episodes.
     */
    @Test
    public void shouldThrowExceptionWhenSeriesIsEmptyForDefaultRating() {
        try {
            series.rating(5);
            fail("Debió lanzar NeoFlixException por serie vacía.");
        } catch (NeoFlixException e) {
            assertEquals(NeoFlixException.UNKNOWN_VALUE, e.getMessage());
        }
    }

    /**
     * Verifies that an exception is thrown when all episodes in the series have corrupted data.
     */
    @Test
    public void shouldThrowExceptionWhenNoValidEpisodesForDefaultRating() {
       
        series.addEpisode(new Episode("Ep Corrupto", series, 100, 80, 10, 200));

        try {
            series.rating(5);
            fail("Debió lanzar NeoFlixException porque no hay ningún episodio computable.");
        } catch (NeoFlixException e) {
            assertEquals(NeoFlixException.UNKNOWN_VALUE, e.getMessage());
        }
    }
}
