package domain;
import java.awt.Color;

/**
 * Write a description of class Cactus here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Cactus extends Organism implements Entity
{
    private final EcoSafari habitat;
    private boolean hasActed;
    public Cactus(EcoSafari habitat, int row, int column){
        
        this.habitat = habitat;
        habitat.set((Entity) this, row, column);
        hasActed = false;
    
    }
    
        public final Color getColor() {
        return Color.GREEN;
    }

    /**
     * Returns the type identifier.
     *
     * @return "Shrub"
     */
    public final String type() {
        return "Cactus";
    }
    
    public void tic(){
        hasActed = true;
    }   
    
    
        public void tac(){
        hasActed = false;
    }   
    
    public EcoSafari getHabitat(){
        return habitat;
    }
    
    
}