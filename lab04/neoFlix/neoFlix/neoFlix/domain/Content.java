package domain;

/**
 * Abstract class representing a general catalog content in NeoFlix.
 * @author Juan Espitia, Cristian Salamanca
 * @version ECI 2026
 */
public abstract class Content{
    protected String title;

    /**
     * Constructs a new Content with the specified title.
     * @param title the title of the content
     */
    public Content(String title){
        this.title=title;
    }
    
    /**
     * Returns the title of the content.
     * @return the title of the content
     */
    public String getTitle(){
        return title;
    }

    /**
     * Returns the rating [0..10].
     * @return the calculated rating
     * @throws NeoFlixException if the rating cannot be calculated
     */    
    public abstract int rating() throws NeoFlixException;
    
    /**
     * Returns the popularity [0..100].
     * @return the calculated popularity
     * @throws NeoFlixException if the popularity cannot be calculated
     */    
    public abstract int popularity() throws NeoFlixException;
    
    /**
     * Returns the textual representation of the content.
     * @param withIndicators true if indicators should be included
     * @return the formatted string representation
     * @throws NeoFlixException if the data cannot be displayed
     */    
    public abstract String data(boolean withIndicators) throws NeoFlixException;

}
