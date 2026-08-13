
/**
 * Write a description of class Pared here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Pared
{
    private int positionX;
    private int positionY;
    private int large;
    private char orientation;
    private Rectangle shape;
    
    public Pared(int positionX, int positionY, int large, char orientation){
        this.positionX = positionX;
        this.positionY = positionY;
        this.large = large;
        this.orientation = orientation;
    }
    
    public void dibujarPared(){
        shape = new Rectangle(positionX*20, positionY*20);
        if(orientation == 'V'){
            shape.changeSize(large*20, 20);
        }else if(orientation == 'H'){
            shape.changeSize(20, large*20);
        }
        
        shape.makeVisible();
    }
    
    public void cColor(String color){
        shape.changeColor(color);
    }
    
    public void colisionP(){
        cColor("grey");
        cColor("black");
    }
    
    
    }
