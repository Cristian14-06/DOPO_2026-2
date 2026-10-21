package domain;

/**
 * Class representing a movie in NeoFlix.
 * @author Juan Espitia, Cristian Salamanca
 * @version ECI 2026
 */
public class Movie extends Audiovisual{
    private int budge;
    
    /**
     * Constructs a Movie with the specified title and budget.
     * @param title the title of the movie
     * @param budge the production budget of the movie
     */
    public Movie(String title, int budge){
        super(title);
        this.budge=budge;
    }

}