package domain;

import java.util.ArrayList;
import java.util.TreeMap;

/**
 * Main domain class managing the NeoFlix catalog of series and episodes.
 * @author Juan Espitia, Cristian Salamanca
 * @version ECI 2026
 */
public class NeoFlix{
    private ArrayList<Content> contents;
    private TreeMap<String,Episode> episodes;

    /**
     * Constructs a new NeoFlix catalog and loads initial sample data.
     */
    public NeoFlix(){
        contents = new ArrayList<Content>();
        episodes = new TreeMap<String,Episode>();
        addSomeSeries();
    }

    /*
     * Adds sample series and episodes to the NeoFlix catalog.
     */
    private void addSomeSeries(){
        
        String [][] episodes = {
        {"The Hidden Village", "199", "120", "50", "430"},
        {"The First Mission", "140", "126", "60", "510"},
        {"The Rival", "199", "120", "50", "430"},
        {"The Tournament", "180", "162", "90", "810"},
        {"The Final Battle", "200", "190", "120", "1080"}};
        StringBuffer titles=new StringBuffer();
        for (String [] e : episodes){
            titles.append(e[0]+"\n");
        }
        
        for (String [] e: episodes){
            try
            {
                addEpisode(e[0],"?", e[1],e[2],e[3],e[4]);
            }
            catch (NeoFlixException nfe)
            {
                nfe.printStackTrace();
            }
        }
        String [][] series = {{"The Neo Ninja", "2020", titles.toString()}};
        for (String [] s: series){
            try
            {
                addSeries(s[0],s[1],s[2]);
            }
            catch (NeoFlixException nfe)
            {
                nfe.printStackTrace();
            }
        }


    }

    /**
     * Consults a content in the catalog by its title (case-insensitive).
     * @param name the title of the content to search for
     * @return the matching Content object, or null if not found
     */
    public Content consult(String name){
        Content c=null;
        for(int i=0;i<contents.size() && c == null;i++){
            if (contents.get(i).getTitle().compareToIgnoreCase(name)==0) 
               c=contents.get(i);
        }
        return c;
    }

    /**
     * Adds a new episode to the catalog after validating business preconditions.
     * @param title the title of the episode
     * @param series the title of the series to which the episode belongs
     * @param attempts the number of viewing attempts
     * @param completed the number of completed viewings
     * @param votes the total number of votes received
     * @param sumVotes the total sum of all vote scores
     * @throws NeoFlixException CONTENT_EMPTY if the title is empty,
     *                          REPEATED_NAME if a content with the same title already exists,
     *                          NOT_A_NUMBER if any metric is not a valid integer,
     *                          OUT_OF_RANGE if the metrics violate domain invariants,
     *                          NOT_A_SERIES if the referenced content is not a series
     */
    public void addEpisode(String title, String series, String attempts, String completed, String votes, String sumVotes) throws NeoFlixException{
        if(title.trim().equals("")){
            throw new NeoFlixException(NeoFlixException.CONTENT_EMPTY);    
        }
        
        if(consult(title) != null){
            throw new NeoFlixException(NeoFlixException.REPEATED_NAME);
        }
        int att, comp, vot, sumVot;
        try{
            att = Integer.parseInt(attempts);
            comp = Integer.parseInt(completed);
            vot = Integer.parseInt(votes);
            sumVot = Integer.parseInt(sumVotes);
        }catch(NumberFormatException ne){
            throw new NeoFlixException(NeoFlixException.NOT_A_NUMBER);
        }
        if(att < 0 || comp < 0 || vot < 0 || sumVot < 0 || sumVot > vot * 10 || comp > att){
            throw new NeoFlixException(NeoFlixException.OUT_OF_RANGE);
        }

        Series s;
        
        try{
           s = (Series)consult(series); 
        }catch(ClassCastException cce){
            throw new NeoFlixException(NeoFlixException.NOT_A_SERIES);
        }
        Episode e=new Episode(title,s, att, comp, vot, sumVot);
        contents.add(e);
        episodes.put(title.toUpperCase(),e); 
    }
    
    /**
     * Adds a new series to the catalog along with its associated episodes.
     * @param title the title of the series
     * @param year the release year of the series
     * @param theEpisodes the titles of the episodes separated by newlines
     * @throws NeoFlixException CONTENT_EMPTY if the title or episodes list is empty,
     *                          REPEATED_NAME if a content with the same title already exists,
     *                          NOT_A_NUMBER if the year is not a valid integer,
     *                          WRONG_YEAR if the year is prior to 1888,
     *                          EPISODE_NOT_FOUND if any specified episode does not exist
     */
    public void addSeries(String title, String year, String theEpisodes) throws NeoFlixException{ 
        if(title.trim().equals("") || theEpisodes.trim().equals("")){
            throw new NeoFlixException(NeoFlixException.CONTENT_EMPTY);    
        }
        
        if(consult(title) != null){
            throw new NeoFlixException(NeoFlixException.REPEATED_NAME);
        }
        int y;
        try{
            y = Integer.parseInt(year);
        }catch(NumberFormatException ne){
            throw new NeoFlixException(NeoFlixException.NOT_A_NUMBER);
        }
        if(y < 1888){
            throw new NeoFlixException(NeoFlixException.WRONG_YEAR);
        }
        Series s = new Series(title, y);
        String [] aEpisodes= theEpisodes.split("\n");
        for (String te : aEpisodes){
            String epTitle = te.trim();
            if(!epTitle.isEmpty()){
                if(episodes.get(epTitle.toUpperCase()) == null){
                    throw new NeoFlixException(NeoFlixException.EPISODE_NOT_FOUND);
                }else{
                    s.addEpisode(episodes.get(epTitle.toUpperCase()));
                }
            }
        }
        contents.add(s);
    }

    /**
     * Returns the contents whose title starts with the given prefix.
     * @param prefix the search prefix
     * @return the list of matching contents
     */
    public ArrayList<Content> select(String prefix){
        ArrayList <Content> answers=new ArrayList<Content>();
        prefix=prefix.toUpperCase();
        for(int i=0;i<contents.size();i++){
            if(contents.get(i+1).getTitle().toUpperCase().startsWith(prefix)){
                answers.add(contents.get(i));
            }   
        }
        return answers;
    }

    /**
     * Returns the textual representation of the selected contents.
     * @param selected the list of selected contents
     * @param withIndicators true if indicators should be included
     * @return the formatted string of selected contents
     */
    public String data(ArrayList<Content> selected, boolean withIndicators){
        StringBuffer answer=new StringBuffer();
        answer.append(selected.size()+ " elementos\n");
        for(Content c : selected) {
            try{
                answer.append('>' + c.data(withIndicators));
                answer.append("\n");
            }catch(NeoFlixException e){
                answer.append("**** "+e.getMessage());
            }
        }    
        return answer.toString();
    }
    
    /**
     * Returns the textual data of contents matching a prefix, including indicators.
     * @param prefix the search prefix
     * @return the formatted search results
     */ 
    public String search(String prefix){
        return data(select(prefix),true);
    }
    
    /**
     * Returns the textual representation of all contents in the catalog.
     * @return the formatted catalog listing
     */    
    public String toString(){
        
        return data(contents,false);
    }
    
    /**
     * Returns the total number of contents in the catalog.
     * @return the number of contents
     */
    public int numberContents(){
        return contents.size();
    }

}
