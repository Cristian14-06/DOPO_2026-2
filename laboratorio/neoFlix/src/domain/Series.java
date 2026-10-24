package domain;  
 
import java.util.ArrayList;

/**
 * Class representing a series composed of multiple episodes in NeoFlix.
 * @author Juan Espitia, Cristian Salamanca
 * @version ECI 2026
 */
public class Series extends Content{
   
    private int year;
    private ArrayList<Episode> episodes;
    
    /**
     * Constructs a Series with the specified title and release year.
     * @param title the title of the series
     * @param year the release year of the series
     */
    public Series(String title, int year){
        super(title);
        this.year=year;
        episodes= new ArrayList<Episode>();
    }

    /**
     * Adds an episode to the series.
     * @param e the episode to be added
     */
    public void addEpisode(Episode e){
        episodes.add(e);
    }
       
   /**
    * Returns the calculated rating of the series.
    * @return the average rating of its episodes
    * @throws NeoFlixException CONTENT_EMPTY if the series has no episodes,
    *                          VALUE_UNKNOWN if the rating for any episode is unknown,
    *                          DATA_ERROR if the rating for any episode cannot be calculated due to a data error
    */
   @Override
   public int rating() throws NeoFlixException{
       if (episodes.isEmpty()) {
            throw new NeoFlixException(NeoFlixException.CONTENT_EMPTY );
        }

        int sumRatings = 0;
        for (Episode episode : episodes) {
            sumRatings += episode.rating();
        }

        return sumRatings / episodes.size();
    }

    /**
     * Returns the calculated rating, using the default value for episodes with an unknown rating and ignoring those with errors.
     * @param default_ the default rating to use for episodes with an unknown rating
     * @return the calculated average rating
     * @throws NeoFlixException UNKNOWN_VALUE if the rating cannot be calculated
     */
    public int rating(int default_) throws NeoFlixException {
        if (episodes.isEmpty()) {
            throw new NeoFlixException(NeoFlixException.UNKNOWN_VALUE);
        }
        int sum = 0;
        int validCount = 0;
        for(Episode episode : episodes){
            try{
                sum+= episode.rating();
                validCount++;
            }catch(NeoFlixException e){
                if(NeoFlixException.UNKNOWN_VALUE.equals(e.getMessage())){
                    sum+= default_;
                    validCount++;
                }
            }
        }
        if(validCount == 0){
            throw new NeoFlixException(NeoFlixException.UNKNOWN_VALUE);
        }
        return sum / validCount;

    }

    /**
     * Returns the calculated rating, replacing unknown episode ratings with the average of previous episodes
     * or the average of all valid episodes, depending on the previous parameter.
     * @param previous true to use the average of previous episodes, false to use the global average
     * @return the calculated average rating
     * @throws NeoFlixException CONTENT_EMPTY if the series has no episodes,
     *                          VALUE_UNKNOWN if the rating cannot be calculated
     *                          DATA_ERROR if any episode has a inconsistent data
     */
    public int rating(boolean previous) throws NeoFlixException {
        if (episodes.isEmpty()) {
            throw new NeoFlixException(NeoFlixException.CONTENT_EMPTY);
        }
        int sumParcial = 0;
        int countParcial = 0;
        int sum = 0;
        int count = 0;

        for(Episode episode : episodes){
            try{
              sumParcial+= episode.rating();
              countParcial++;
            }catch(NeoFlixException e){
                continue;
            }
        }    
        if (countParcial == 0) {
            throw new NeoFlixException(NeoFlixException.VALUE_UNKNOWN);
        }

        for(Episode episode : episodes){
            try{
              sum+= episode.rating();
              count++;
            }catch(NeoFlixException e){
              if(NeoFlixException.UNKNOWN_VALUE.equals(e.getMessage())){
                 if(previous){
                     if(count == 0){
                         throw new NeoFlixException(NeoFlixException.VALUE_UNKNOWN);
                     }
                     int temp = sum/count;
                     sum+=temp;
                     count++;
                 }else{
                     int temp = sumParcial/countParcial;
                     sum+=temp;
                     count++;
                 }
              }
            }
        }
        return sum/count;
    }

   /**
    * Returns the calculated popularity of the series as the average of its valid episodes.
    * Episodes with unknown popularity are ignored, and episodes with data errors are propagated.
    * @return the average popularity in [0..100]
    * @throws NeoFlixException CONTENT_EMPTY if the series has no episodes,
    *                          VALUE_UNKNOWN if no episode has a computable popularity,
    *                          DATA_ERROR if any episode has invalid popularity data
    */
   public int popularity() throws NeoFlixException{
       if(episodes.isEmpty()){
           throw new NeoFlixException(NeoFlixException.CONTENT_EMPTY);
       }
       int sum = 0;
       int cont = 0;
       for(Episode e : episodes){
           try{
               sum+=e.popularity();
               cont++;
           }catch(NeoFlixException ex){
               if(ex.getMessage().equals(NeoFlixException.DATA_ERROR)){
                   throw ex;
               }
           }
       }
       if(cont == 0){
           throw new NeoFlixException(NeoFlixException.VALUE_UNKNOWN);  
       }
       return sum/cont;
   }
    
    /**
     * Returns the textual representation of the series and its episodes.
     * @param withIndicators true if popularity and rating indicators should be included
     * @return the formatted string representation
     * @throws NeoFlixException if indicators cannot be calculated
     */
    @Override
    public String data(boolean withIndicators) throws NeoFlixException{
        StringBuffer answer=new StringBuffer();
        answer.append(title+": "+year+ ( withIndicators ? " ( " +popularity()+" - "+ rating()+" )":""));
        for(Episode e: episodes) {
            answer.append("\n\t"+(e.data(withIndicators)));
        }
        return answer.toString();
    } 

}
