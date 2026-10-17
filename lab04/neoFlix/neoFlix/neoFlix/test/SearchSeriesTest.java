package test;


import domain.*;
import static org.junit.Assert.*;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * Unit tests for searching content in the NeoFlix catalog.
 *
 * @author Juan Espitia, Cristian Salamanca
 */
public class SearchSeriesTest
{
    /**
     * Verifies that searching for a prefix triggers an IndexOutOfBoundsException due to the defect in select.
     */
    @Test
    public void shouldThrowError(){
        NeoFlix neo = new NeoFlix();
        try{
           neo.search("T");
           fail("No lanso excepcion,  error");
        }catch(IndexOutOfBoundsException iofbe){
            
        }
        
    }
}