package domain;
import java.awt.Color;


/**
 * Write a description of class Lion here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Lion extends Animal{
    private EcoSafari habitat;
    private boolean hasActed;
    
    public Lion(EcoSafari habitat, int row, int column){
        this.habitat = habitat;
        habitat.set((Entity)this, row, column);
        hasActed = false;
    }
    
    public EcoSafari getHabitat(){
        return habitat;
    }
    
    public final Color getColor(){
        return Color.ORANGE;
    }
    
    public void tic(){
        moveAnimal(1);
        eat("Zebra", (float)0.25);
    
        hasActed = true;

    }
    
    public void tac(){
        hasActed = false;
    }
}