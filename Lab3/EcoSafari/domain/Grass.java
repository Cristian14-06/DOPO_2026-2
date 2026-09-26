package domain;
import java.awt.Color;


/**
 * Write a description of class Grass here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Grass extends Organism implements Entity{
    private EcoSafari habitat;
    private boolean hasActed;
    
    public Grass(EcoSafari habitat, int row, int column){
        this.habitat = habitat;
        habitat.set((Entity)this, row, column);
        hasActed = false;
    }
    
    public final String type(){
        return "Grass";
    }
    
    public final Color getColor(){
        return (getEnergy()>=40? Color.GREEN : Color.yellow);
    }
    
    public final EcoSafari getHabitat(){
        return habitat;
    }
    
    public void tic(){
        changeEnergy(-5);
        hasActed = true;
    }
    
    public final void tac(){
        hasActed = false;
    }

}