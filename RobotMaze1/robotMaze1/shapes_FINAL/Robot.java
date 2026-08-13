
/**
 * Write a description of class Robot here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Robot
{
    // instance variables - replace the example below with your own
    private boolean visible;
    private int xPosition;
    private int yPosition;
    private int pVida;
    public char direction;
    private Rectangle shape;

    /**
     * Constructor for objects of class Robot
     */
    public Robot(int xPosition, int yPosition, char direction)
    {
        visible = false;
        pVida = 10;
        this.xPosition = xPosition;
        this.yPosition = yPosition;
        this.direction = direction;
        shape = new Rectangle(xPosition*20, yPosition*20);
    }
    
    public void makeVisible(){
        if (visible == false){
            visible = true;
            shape.makeVisible();
            
        }
    }
    
    public void makeInvisible(){
        if(visible==true){
            visible=false;
        }
    }
    
    public int[] coordinates(){
        int[] position = new int[2];
        position[0] = xPosition;
        position[1] = yPosition;
        return position;
        
    }
    
    public void color(){
        shape.changeColor("yellow");
    }
    
    public char direction(){
        return direction;
    }
    
    public void move(){
        char actualDirection = direction();
        
        switch(actualDirection){
            case 'N':
                shape.moveUp();
                yPosition--;
                break;
                
            case 'S':
                shape.moveDown();
                yPosition++;
                break;
            
            case 'E':
                shape.moveRight();
                xPosition++;
                break;
            
            case 'W':
                shape.moveLeft();
                xPosition--;
                break;
        }
    }
    
    private void menosVida(){
        pVida-=1;
    }
    
    public void colision(){
        shape.changeColor("red");
        menosVida();
        shape.changeColor("green");
    }
    /**
     * An example of a method - replace this comment with your own
     * 
     * @param  y   a sample parameter for a method
     * @return     the sum of x and y 
     */

}