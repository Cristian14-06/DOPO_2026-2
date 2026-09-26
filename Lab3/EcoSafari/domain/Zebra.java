package domain;
import java.awt.Color;


/**
 * Write a description of class Zebra here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Zebra extends Animal{

    
    public Zebra(EcoSafari habitat, int row, int column){
        super(habitat, row, column);
    }
    

    public final Color getColor(){
        return Color.BLACK;
    }
    
    public final String type(){
        return "Zebra";
    }
    
    public void tic(){
        moveAnimal(2);
        eat("Grass", (float)0.25);
        setActed(true);

    }
    
    public void tac(){
        setActed(false);
    }
    
    
}