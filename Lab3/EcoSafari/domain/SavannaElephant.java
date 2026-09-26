package domain;


/**
 * Write a description of class SavannaElephant here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class SavannaElephant extends Elephant{
    public SavannaElephant(EcoSafari habitat, int row, int column){
        super(habitat, row, column);
    }
    
    @Override
    public void tic(){
        moveAnimal(2);
        eat("Shrub", (float)0.4);
        setActed(true);
    }
}