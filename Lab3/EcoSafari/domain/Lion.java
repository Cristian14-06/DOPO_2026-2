package domain;
import java.awt.Color;


/**
 * Write a description of class Lion here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Lion extends Animal{

    
    public Lion(EcoSafari habitat, int row, int column){
        super(habitat, row, column);
    }
    
    

    
    public final Color getColor(){
        return Color.ORANGE;
    }
    
    public final String type(){
        return "Lion";
    }
    
    public void tic(){
        moveAnimal(1);
        eat("Zebra", (float)0.25);
    
        setActed(true);

    }
    
    public void tac(){
        setActed(false);
    }
}