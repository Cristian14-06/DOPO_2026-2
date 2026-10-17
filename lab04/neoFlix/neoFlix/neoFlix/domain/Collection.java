package domain;
import java.util.TreeMap;

/**
 * Class representing a themed collection of contents in NeoFlix.
 * @author Juan Espitia, Cristian Salamanca
 * @version ECI 2026
 */
public class Collection extends Content{

    private String theme;
    private TreeMap<String, Content> contents;
    
    /**
     * Constructs a Collection with the specified title and theme.
     * @param title the title of the collection
     * @param theme the common theme of the collection
     */
    public Collection(String title, String theme){
        super(title);
        this.theme=theme;
        contents= new TreeMap<String,Content>();
    }
    
    /**
     * Returns the calculated popularity of the collection.
     * @return the calculated popularity
     * @throws NeoFlixException TO_BE_IMPLEMENTED as this feature is under construction
     */
    @Override
    public int popularity() throws NeoFlixException{
        throw new NeoFlixException(NeoFlixException.TO_BE_IMPLEMENTED);
    }

    /**
     * Returns the calculated rating of the collection.
     * @return the calculated rating
     * @throws NeoFlixException TO_BE_IMPLEMENTED as this feature is under construction
     */
    @Override
    public int rating() throws NeoFlixException{
        throw new NeoFlixException(NeoFlixException.TO_BE_IMPLEMENTED);
    }
    
    /**
     * Returns the textual representation of the collection.
     * @param withIndicators true if indicators should be included
     * @return the formatted string representation
     * @throws NeoFlixException TO_BE_IMPLEMENTED as this feature is under construction
     */
    @Override
    public String data(boolean withIndicators) throws NeoFlixException{
        throw new NeoFlixException(NeoFlixException.TO_BE_IMPLEMENTED);
    }
    
}
