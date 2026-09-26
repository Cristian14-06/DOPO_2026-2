package domain;
import java.awt.Color;


//Include the documentation
public class Elephant extends Animal{

    
    public Elephant(EcoSafari habitat,int row, int column){
        super(habitat, row, column);
    }


    
    public final Color getColor(){
        return(getEnergy()>=80? Color.DARK_GRAY: Color.LIGHT_GRAY);
    }
    
    public final String type(){
        return "Elephant";
    }

    public final int shape(){
        return Entity.ROUND;
    }

    @Override
    protected void moveAnimal(int steps){
        if(!getActed() && move(steps, steps)){
            changeEnergy(-10);
            if(getEnergy() == 0){
                disappear();
            }
        }
    }
    
    public void tic(){
        moveAnimal(1);
        eat("Shrub",  (float)0.10);
        setActed(true);
    }
    
    public void tac(){
        setActed(false);
    }    
}
