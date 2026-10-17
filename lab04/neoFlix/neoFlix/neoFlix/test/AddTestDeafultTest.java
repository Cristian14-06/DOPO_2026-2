package test;
import domain.*;
import static org.junit.Assert.*;
import org.junit.Test;
import org.junit.Before;





/**
 * Unit tests for adding episodes and series to the NeoFlix catalog.
 *
 * @author Juan Espitia, Cristian Salamanca
 */
public class AddTestDeafultTest{

   /**
    * Verifies that valid episodes can be created and added to the NeoFlix catalog.
    * 
    * @throws NeoFlixException if adding an episode fails unexpectedly
    */
   @Test
   public void shouldCreateAEpisode() throws NeoFlixException{
       NeoFlix neoFlix = new NeoFlix();
       
       
       neoFlix.addEpisode("The Awakening", "The Neo Ninja", "100", "85", "40", "320");
       neoFlix.addEpisode("The Secret Gate", "The Neo Ninja", "120", "90", "50", "425");
       neoFlix.addEpisode("The Final Stand", "The Neo Ninja", "150", "110", "60", "540");
       assertEquals(9, neoFlix.numberContents());
       assertEquals("The Awakening", neoFlix.consult("The Awakening").getTitle());
       
   }
   
   /**
    * Verifies that a valid series can be created and added to the NeoFlix catalog.
    * 
    * @throws NeoFlixException if adding the series fails unexpectedly
    */
   @Test
   public void shouldCreateASeries() throws NeoFlixException{
       NeoFlix neoFlix = new NeoFlix();
       
       
       String titulo = "Cyber Quest";
       String anio = "2025";
       String episodios = "The Hidden Village\nThe First Mission\nThe Rival";
       
       neoFlix.addSeries(titulo, anio, episodios);
       
       assertEquals(7, neoFlix.numberContents());
       
   }

   /**
    * Verifies that an exception is thrown when adding an episode with an existing title.
    */
   @Test
   public void shouldThrowExceptionIfNameAlreadyExists(){
       NeoFlix neoFlix = new NeoFlix();
       try {
           neoFlix.addEpisode("The Hidden Village", "The Neo Ninja", "100", "80", "20", "160");
           fail("Debio lanzar excepcion por nombre repetido");
       } catch (NeoFlixException e) {
           assertEquals(NeoFlixException.REPEATED_NAME, e.getMessage());
       }
   }

   /**
    * Verifies that an exception is thrown when a numeric field for an episode is not a valid number.
    */
   @Test
   public void shouldThrowExceptionIfValueIsNotANumber(){
       NeoFlix neoFlix = new NeoFlix();
       try {
           neoFlix.addEpisode("New Episode", "The Neo Ninja", "abc", "80", "20", "160");
           fail("Debio lanzar excepcion por valor no numerico");
       } catch (NeoFlixException e) {
           assertEquals(NeoFlixException.NOT_A_NUMBER, e.getMessage());
       }
   }

   /**
    * Verifies that an exception is thrown when completed views exceed attempts.
    */
   @Test
   public void shouldThrowExceptionIfValuesAreOutOfRange(){
       NeoFlix neoFlix = new NeoFlix();
       try {
           neoFlix.addEpisode("New Episode", "The Neo Ninja", "100", "150", "20", "160");
           fail("Debio lanzar excepcion por valores fuera de rango");
       } catch (NeoFlixException e) {
           assertEquals(NeoFlixException.OUT_OF_RANGE, e.getMessage());
       }
   }
   
   /**
    * Verifies that an exception is thrown when the total score exceeds ten times the number of votes.
    */
   @Test
   public void shouldThrowExceptionIfValuesAreOutOfRange2(){
       NeoFlix neoFlix = new NeoFlix();
       try {
           neoFlix.addEpisode("New Episode 2", "", "100", "80", "20", "300");
           fail("Debio lanzar excepcion por valores fuera de rango");
       } catch (NeoFlixException e) {
           assertEquals(NeoFlixException.OUT_OF_RANGE, e.getMessage());
       }
   }

   /**
    * Verifies that an exception is thrown when the release year of a series is outside the valid range.
    */
   @Test
   public void shouldThrowExceptionIfYearIsInvalid(){
       NeoFlix neoFlix = new NeoFlix();
       try {
           neoFlix.addSeries("Ancient Series", "1500", "The Hidden Village");
           fail("Debio lanzar excepcion por ano invalido");
       } catch (NeoFlixException e) {
           assertEquals(NeoFlixException.WRONG_YEAR, e.getMessage());
       }
   }

   /**
    * Verifies that an exception is thrown when the title of an episode is empty or whitespace.
    */
   @Test
   public void shouldThrowExceptionIfTitleIsEmpty(){
       NeoFlix neoFlix = new NeoFlix();
       try {
           neoFlix.addEpisode("   ", "The Neo Ninja", "100", "80", "20", "160");
           fail("Debio lanzar excepcion por titulo vacio");
       } catch (NeoFlixException e) {
           assertEquals(NeoFlixException.CONTENT_EMPTY, e.getMessage());
       }
   }
   
   /**
    * Verifies that an exception is thrown when the specified series name corresponds to an episode instead of a series.
    */
   @Test
   public void shouldThrowAErrorIfSeriesisntASeries(){
       NeoFlix neoflix = new NeoFlix();
       try
       {
           neoflix.addEpisode("Nuevo Episodio", "The Rival", "100", "80", "20", "160");
           fail("debio haber saltado NOT_A_SERIES");
       }catch (NeoFlixException nfe){
           assertEquals(NeoFlixException.NOT_A_SERIES, nfe.getMessage());
       }
       
   }
   
   /**
    * Verifies that an exception is thrown when adding a series with an episode that does not exist.
    */
   @Test
   public void shouldThrowAErrorIfEpisodeDoesntexists(){
       NeoFlix neoflix = new NeoFlix();
       try
       {
           neoflix.addSeries("Nueva serie", "2026", "el regreso del chavo del ocho");
           fail("debio haber saltado EPISODE_NOT_FOUND");
       }catch (NeoFlixException nfe){
           assertEquals(NeoFlixException.EPISODE_NOT_FOUND, nfe.getMessage());
       }
       
   }
   
   /**
    * Verifies that an exception is thrown when adding a series with an empty list of episodes.
    */
   @Test
   public void shouldThrowAErrorBecauseOfAddSeries1(){
       NeoFlix neoflix = new NeoFlix();
       try
       {
           neoflix.addSeries("New Series", "2020", "   ");
           fail("debio haber saltado CONTENT_EMPTY");
       }catch (NeoFlixException nfe){
           assertEquals(NeoFlixException.CONTENT_EMPTY, nfe.getMessage());
       }
   }
   
   /**
    * Verifies that an exception is thrown when adding a series whose name already exists.
    */
   @Test
   public void shouldThrowAErrorBecauseOfAddSeries2(){
       NeoFlix neoflix = new NeoFlix();
       try
       {
           neoflix.addSeries("The Neo Ninja", "2020", "The Rival");
           fail("debio haber saltado REPEATED_NAME");
       }catch (NeoFlixException nfe){
           assertEquals(NeoFlixException.REPEATED_NAME, nfe.getMessage());
       }
   }
   
   /**
    * Verifies that an exception is thrown when the release year of a series is not a valid number.
    */
   @Test
   public void shouldThrowAErrorBecauseOfAddSeries3(){
       NeoFlix neoflix = new NeoFlix();
       try
       {
           neoflix.addSeries("betty la fea", "holaa", "The Rival");
           fail("debio haber saltado NOT_A_NUMBER");
       }catch (NeoFlixException nfe){
           assertEquals(NeoFlixException.NOT_A_NUMBER, nfe.getMessage());
       }
   }
}