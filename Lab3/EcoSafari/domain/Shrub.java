package domain;
import java.awt.Color;


/**
 * Write a description of class Shrub here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Shrub extends Organism implements Entity{
    private final EcoSafari habitat;
    private boolean hasActed;
    
    public Shrub(EcoSafari habitat,int row, int column){
        this.habitat=habitat;
        habitat.set((Entity)this, row, column);  
        hasActed=false;
    }
    
    public final Color getColor(){
        return(getEnergy()>60? Color.GREEN: Color.YELLOW);
    }
    
    @Override
    public final boolean isShrub(){
        return true;
    }

    
    public void tic(){
        int[] posicion_inicial = habitat.find(this);
        int fila = posicion_inicial[0];
        int columna = posicion_inicial[1];
    
        if(getEnergy() <= 80 && getEnergy() >= 60){
            if(habitat.isInside(fila - 1, columna) && habitat.get(fila - 1, columna) == null && !hasActed && move(-1, 0)){
                changeEnergy(-10);
                if(getEnergy() == 0){
                    disappear();
                }
            }else if(habitat.isInside(fila + 1, columna) && habitat.get(fila + 1, columna) == null && !hasActed && move(1, 0)){
                changeEnergy(-10);
                if(getEnergy() == 0){
                    disappear();
                }
            }else if(habitat.isInside(fila, columna - 1) && habitat.get(fila, columna - 1) == null && !hasActed && move(0, -1)){
                changeEnergy(-10);
                if(getEnergy() == 0){
                    disappear();
                }
            }else if(habitat.isInside(fila, columna + 1) && habitat.get(fila, columna + 1) == null && !hasActed && move(0, 1)){
                changeEnergy(-10);
                if(getEnergy() == 0){
                    disappear();
                }
            }
            hasActed = true;
        }else{
            if(!hasActed && move(1, 1)){
                changeEnergy(-10);
                if(getEnergy() == 0){
                    disappear();
                }
            }
            hasActed = true;
        }
    
        int[] posicion_actual = habitat.find(this);
    
        if(posicion_actual != null){
            fila = posicion_actual[0];
            columna = posicion_actual[1];
    
            if(habitat.isInside(fila - 1, columna)){
                Entity vecino1 = habitat.get(fila - 1, columna);
                if(vecino1 != null && vecino1.isElephant()){
                    disappear();
                }
            }
    
            if(habitat.isInside(fila + 1, columna)){
                Entity vecino2 = habitat.get(fila + 1, columna);
                if(vecino2 != null && vecino2.isElephant()){
                    disappear();
                }
            }
    
            if(habitat.isInside(fila, columna - 1)){
                Entity vecino3 = habitat.get(fila, columna - 1);
                if(vecino3 != null && vecino3.isElephant()){
                    disappear();
                }
            }
    
            if(habitat.isInside(fila, columna + 1)){
                Entity vecino4 = habitat.get(fila, columna + 1);
                if(vecino4 != null && vecino4.isElephant()){
                    disappear();
                }
            }
        }
    }
    
    public void tac(){
        hasActed = false;
    }
    
    public EcoSafari getHabitat(){
        return habitat;
    }
    
    
}

/**
 * import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

public class ShrubTest {

    @Test
    public void shouldMoveNorthAfterTwoTics() {
        EcoSafari safari = new EcoSafari();
        Shrub shrub = new Shrub(safari, 10, 10);

        shrub.tic();
        shrub.tac();
        shrub.tic();
        shrub.tac();
        shrub.tic();

        assertTrue(safari.get(9, 10) == shrub);
    }

    @Test
    public void shouldDisappearWhenElephantIsAdjacent() {
        EcoSafari safari = new EcoSafari();
        Shrub shrub = new Shrub(safari, 10, 10);
        Elephant elephant = new Elephant(safari, 10, 11);

        shrub.tic();

        assertNull(safari.find(shrub));
    }
}
 */