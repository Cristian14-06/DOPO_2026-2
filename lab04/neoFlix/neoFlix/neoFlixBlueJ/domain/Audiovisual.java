package domain;  

/**
 * Abstract class representing an audiovisual unit in NeoFlix.
 * @author Juan Espitia, Cristian Salamanca
 * @version ECI 2026
 */
public abstract class Audiovisual extends Content{
    
    private int attempts;
    private int completed;
    private int votes;
    private int sumVotes; 
    

    /**
     * Constructs an Audiovisual content with full viewing and voting metrics.
     * @param title the title of the audiovisual content
     * @param attempts the number of viewing attempts
     * @param completed the number of completed viewings
     * @param votes the total number of votes received
     * @param sumVotes the total sum of all vote scores
     */
    public Audiovisual(String title, int attempts, int completed, int votes, int sumVotes){
        super(title);
        this.attempts=attempts;
        this.completed=completed;
        this.votes=votes;
        this.sumVotes=sumVotes;
    }
    
    /**
     * Constructs an Audiovisual content with the specified title and zero initial metrics.
     * @param title the title of the audiovisual content
     */
    public Audiovisual(String title){
        this(title,0,0,0,0);
    }
    
    /**
     * Calculates and returns the popularity percentage [0..100].
     * @return the calculated popularity
     * @throws NeoFlixException VALUE_UNKNOWN if attempts is zero,
     *                          DATA_ERROR if completed is greater than attempts
     */
    @Override
    public int popularity() throws NeoFlixException{
       if (attempts == 0) throw new NeoFlixException(NeoFlixException.VALUE_UNKNOWN);
       if (completed > attempts) throw new NeoFlixException(NeoFlixException.DATA_ERROR);
       return (completed*100)/attempts;
    }    
    
    /**
     * Calculates and returns the average rating [0..10].
     * @return the calculated rating
     * @throws NeoFlixException VALUE_UNKNOWN if votes is zero,
     *                          DATA_ERROR if sumVotes exceeds ten times the number of votes
     */
    @Override
    public int rating() throws NeoFlixException{
       if (votes == 0) throw new NeoFlixException(NeoFlixException.VALUE_UNKNOWN);
       if (sumVotes>votes*10) throw new NeoFlixException(NeoFlixException.DATA_ERROR);
       return sumVotes/votes;
    }  
    
    /**
     * Returns the textual representation of the audiovisual content.
     * @param withIndicators true if popularity and rating indicators should be included
     * @return the formatted string representation
     */
    @Override
    public String data(boolean withIndicators){
        String theData= title;
        if (withIndicators){
            try{
                theData+=" ( " +popularity()+" - "+ rating()+" )";
            } catch (NeoFlixException e){
                theData += "*** Indicadores incompletos";
            }
        }
        return theData;
    }
}
