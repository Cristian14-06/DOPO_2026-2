package test;
import domain.*;
import org.junit.Test;
import static org.junit.Assert.*;

public class HungrySnakeTest{

   @Test
   public void shouldStartWithSizeOne(){
        HungrySnake juegoPrueba = new HungrySnake();
        assertEquals(1, juegoPrueba.getTamano());
        assertTrue(juegoPrueba.isInGame());
   }

   @Test
   public void shouldStartInTheCenter(){
        HungrySnake juegoPrueba = new HungrySnake();
        assertArrayEquals(new int[]{12, 12} ,juegoPrueba.getCulebra().get(0));
   }

   @Test
   public void shouldStartWithOneFruit(){
        HungrySnake juegoPrueba = new HungrySnake();
        assertEquals(1, juegoPrueba.getFrutas().size());
        assertEquals(0, juegoPrueba.getObstaculos().size());
   }

   @Test
   public void shouldMoveRight(){
        HungrySnake juegoPrueba = new HungrySnake();
        juegoPrueba.move('D');
        assertArrayEquals(new int[]{12, 13}, juegoPrueba.getCulebra().get(0));
   }

   @Test
   public void shouldNotTurnBack(){
        HungrySnake juegoPrueba = new HungrySnake();
        juegoPrueba.move('D');
        juegoPrueba.move('A');
        assertArrayEquals(new int[]{12, 14}, juegoPrueba.getCulebra().get(0));
   }

   @Test
   public void shouldDieWhenHittingWall(){
        HungrySnake juegoPrueba = new HungrySnake();
        int tamano = juegoPrueba.getTamanoTablero();
        for(int i = 0; i < (tamano/2) + 1; i++){
            juegoPrueba.move('D');
        }
        assertFalse(juegoPrueba.isInGame());
   }

   @Test
   public void shouldNotMoveWhenDead(){
        HungrySnake juegoPrueba = new HungrySnake();
        int tamano = juegoPrueba.getTamanoTablero();
        for(int i = 0; i < (tamano/2) + 1; i++){
            juegoPrueba.move('D');
        }
        assertFalse(juegoPrueba.isInGame());
        int[] antes = juegoPrueba.getCulebra().get(0);
        juegoPrueba.move('W');
        assertArrayEquals(antes, juegoPrueba.getCulebra().get(0));
   }

   @Test
   public void shouldIgnoreInvalidKey(){
        HungrySnake juegoPrueba = new HungrySnake();
        juegoPrueba.move('X');
        assertArrayEquals(new int[]{12, 12}, juegoPrueba.getCulebra().get(0));
        assertEquals(1, juegoPrueba.getTamano());
        assertTrue(juegoPrueba.isInGame());
   }

   @Test
   public void shouldAcceptLowercase(){
       HungrySnake juegoPrueba = new HungrySnake();
       juegoPrueba.move('d');
       assertArrayEquals(new int[]{12, 13}, juegoPrueba.getCulebra().get(0));
   }

   @Test
   public void shouldPlaceFruitInsideBoard(){
        for(int i = 0; i < 100; i++){
            HungrySnake juegoPrueba = new HungrySnake();
            int tamano = juegoPrueba.getTamanoTablero();
            int[] fruta = juegoPrueba.getFrutas().get(0);
            assertTrue(fruta[0] < tamano && fruta[1] < tamano && fruta[0] >= 0 && fruta[1] >= 0);
        }
   }

   @Test
   public void shouldGrowWhenEatingFruit(){
        HungrySnake juegoPrueba = new HungrySnake();
        int[] fruta = juegoPrueba.getFrutas().get(0);
        while(juegoPrueba.getCulebra().get(0)[0] > fruta[0]){
            juegoPrueba.move('W');
        }
        while(juegoPrueba.getCulebra().get(0)[0] < fruta[0]){
            juegoPrueba.move('S');
        }
        while(juegoPrueba.getCulebra().get(0)[1] > fruta[1]){
            juegoPrueba.move('A');
        }
        while(juegoPrueba.getCulebra().get(0)[1] < fruta[1]){
            juegoPrueba.move('D');
        }
        assertEquals(2, juegoPrueba.getTamano());
   }

}
