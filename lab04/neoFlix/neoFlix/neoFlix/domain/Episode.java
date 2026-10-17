package domain;

/**
 * Class representing a series episode in NeoFlix.
 * @author Juan Espitia, Cristian Salamanca
 * @version ECI 2026
 */
public class Episode extends Audiovisual{
    private Series series;
    
    /**
     * Constructs an Episode with the specified title and associated series.
     * @param title the title of the episode
     * @param series the series to which this episode belongs
     */
    public Episode(String title, Series series){
        super(title);
        this.series=series;
    }
    
    /**
     * Constructs an Episode with full metrics and associated series.
     * @param title the title of the episode
     * @param series the series to which this episode belongs
     * @param attempts the number of viewing attempts
     * @param completed the number of completed viewings
     * @param votes the total number of votes received
     * @param sumVotes the total sum of all vote scores
     */
    public Episode(String title, Series series, int attempts, int completed, int votes, int sumVotes){
        super(title, attempts, completed, votes, sumVotes);
        this.series=series;
    }

}