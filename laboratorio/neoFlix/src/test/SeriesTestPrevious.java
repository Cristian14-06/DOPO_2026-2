package test;

import domain.Episode;
import domain.NeoFlixException;
import domain.Series;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Unit tests for the Series rating calculation using previous or global episode averages.
 * 
 * @author Juan Espitia, Cristian Salamanca
 */
public class SeriesTestPrevious {

    private Series series;

    /**
     * Initializes a default Series instance before each test.
     */
    @Before
    public void setUp() {
        series = new Series("The Neo Ninja", 2020);
    }

    /**
     * Verifies that the rating is calculated using the average of previous episodes when previous is true.
     * 
     * @throws NeoFlixException if the rating calculation fails unexpectedly
     */
    @Test
    public void shouldCalculateRatingUsingPreviousEpisodesAverageWhenPreviousIsTrue() throws NeoFlixException {
 
        series.addEpisode(new Episode("Ep 1", series, 100, 80, 50, 400));
        series.addEpisode(new Episode("Ep 2", series, 100, 80, 40, 240));
        series.addEpisode(new Episode("Ep 3", series, 100, 80, 0, 0));

        int result = series.rating(true);
        assertEquals(7, result);
    }

    /**
     * Verifies that the rating is calculated using the global average of valid episodes when previous is false.
     * 
     * @throws NeoFlixException if the rating calculation fails unexpectedly
     */
    @Test
    public void shouldCalculateRatingUsingGlobalAverageWhenPreviousIsFalse() throws NeoFlixException {
        
        series.addEpisode(new Episode("Ep 1", series, 100, 80, 50, 500));
        series.addEpisode(new Episode("Ep 2", series, 100, 80, 0, 0));
        series.addEpisode(new Episode("Ep 3", series, 100, 80, 40, 240));

       
        int result = series.rating(false);
        assertEquals(8, result);
    }

    /**
     * Verifies that an exception is thrown when calculating the rating of an empty series.
     */
    @Test
    public void shouldThrowExceptionWhenSeriesIsEmptyForPreviousRating() {
        try {
            series.rating(false);
            fail("Debió lanzar NeoFlixException al calcular sobre una serie sin episodios.");
        } catch (NeoFlixException e) {
            assertEquals(NeoFlixException.CONTENT_EMPTY, e.getMessage());
        }
    }

    /**
     * Verifies that an exception is thrown when no episode in the series has a known rating.
     */
    @Test
    public void shouldThrowExceptionWhenNoEpisodeHasRatingForPrevious() {
        series.addEpisode(new Episode("Ep 1", series, 100, 80, 0, 0));
        series.addEpisode(new Episode("Ep 2", series, 100, 80, 0, 0));

        try {
            series.rating(false);
            fail("Debió lanzar NeoFlixException.VALUE_UNKNOWN cuando ningún episodio tiene votos.");
        } catch (NeoFlixException e) {
            assertEquals(NeoFlixException.VALUE_UNKNOWN, e.getMessage());
        }
    }

    /**
     * Verifies that an exception is thrown when the first episode has an unknown rating and previous is true.
     */
    @Test
    public void shouldThrowExceptionWhenFirstEpisodeIsUnknownAndPreviousIsTrue() {
        series.addEpisode(new Episode("Ep 1", series, 100, 80, 0, 0));
        series.addEpisode(new Episode("Ep 2", series, 100, 80, 50, 400));

        try {
            series.rating(true);
            fail("Debió lanzar NeoFlixException.VALUE_UNKNOWN cuando el primer episodio no tiene votos y previous es true.");
        } catch (NeoFlixException e) {
            assertEquals(NeoFlixException.VALUE_UNKNOWN, e.getMessage());
        }
    }
    
    /**
     * Verifies that episodes with corrupted data are ignored during the rating calculation.
     * 
     * @throws NeoFlixException if the rating calculation fails unexpectedly
     */
    @Test
    public void shouldIgnoreEpisodesWithDataErrorForPreviousRating() throws NeoFlixException {
        series.addEpisode(new Episode("Ep 1", series, 100, 80, 50, 400)); 
        series.addEpisode(new Episode("Ep 2", series, 100, 80, 10, 200)); 
        series.addEpisode(new Episode("Ep 3", series, 100, 80, 40, 240)); 
        assertEquals(7, series.rating(false));
    }
}