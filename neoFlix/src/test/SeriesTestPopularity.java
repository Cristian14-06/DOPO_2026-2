package test;

import domain.Episode;
import domain.NeoFlixException;
import domain.Series;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Unit tests for the popularity calculation in the Series class.
 * 
 * @author Juan Espitia, Cristian Salamanca
 */
public class SeriesTestPopularity {

    private Series series;

    /**
     * Initializes a default Series instance before each test.
     */
    @Before
    public void setUp() {
        series = new Series("The Neo Ninja", 2020);
    }

    /**
     * Verifies the average popularity calculation when all episodes are valid.
     * 
     * @throws NeoFlixException if the popularity calculation fails unexpectedly
     */
    @Test
    public void shouldCalculatePopularityWithValidEpisodes() throws NeoFlixException {
        series.addEpisode(new Episode("Ep 1", series, 100, 80, 50, 400));
        series.addEpisode(new Episode("Ep 2", series, 100, 60, 40, 240));

        int result = series.popularity();
        assertEquals(70, result);
    }

    /**
     * Verifies that episodes with unknown popularity (zero attempts) are ignored
     * and the average is calculated with the remaining valid episodes.
     * 
     * @throws NeoFlixException if the popularity calculation fails unexpectedly
     */
    @Test
    public void shouldIgnoreEpisodesWithUnknownPopularity() throws NeoFlixException {
        series.addEpisode(new Episode("Ep 1", series, 100, 90, 50, 400));
        series.addEpisode(new Episode("Ep 2", series, 0, 0, 0, 0));
        series.addEpisode(new Episode("Ep 3", series, 100, 70, 40, 280));

        int result = series.popularity();
        assertEquals(80, result);
    }

    /**
     * Verifies that a CONTENT_EMPTY exception is thrown if the series has no episodes.
     */
    @Test
    public void shouldThrowExceptionWhenSeriesIsEmpty() {
        try {
            series.popularity();
            fail("Debio lanzar NeoFlixException por serie vacia.");
        } catch (NeoFlixException e) {
            assertEquals(NeoFlixException.CONTENT_EMPTY, e.getMessage());
        }
    }

    /**
     * Verifies that a VALUE_UNKNOWN exception is thrown if no episode has computable popularity data.
     */
    @Test
    public void shouldThrowExceptionWhenAllEpisodesHaveUnknownPopularity() {
        series.addEpisode(new Episode("Ep 1", series, 0, 0, 0, 0));
        series.addEpisode(new Episode("Ep 2", series, 0, 0, 0, 0));

        try {
            series.popularity();
            fail("Debio lanzar NeoFlixException porque ningun episodio tiene reproducciones.");
        } catch (NeoFlixException e) {
            assertEquals(NeoFlixException.VALUE_UNKNOWN, e.getMessage());
        }
    }

    /**
     * Verifies that a DATA_ERROR exception is propagated if an episode has corrupted data
     * (completed greater than attempts).
     */
    @Test
    public void shouldPropagateExceptionWhenAnEpisodeContainsInvalidPopularityData() {
        series.addEpisode(new Episode("Ep 1", series, 100, 80, 50, 400));
        series.addEpisode(new Episode("Ep 2", series, 120, 199, 50, 400));
        series.addEpisode(new Episode("Ep 3", series, 100, 70, 40, 280));

        try {
            series.popularity();
            fail("Debio propagar NeoFlixException.DATA_ERROR por episodio con completed > attempts.");
        } catch (NeoFlixException e) {
            assertEquals(NeoFlixException.DATA_ERROR, e.getMessage());
        }
    }
}
