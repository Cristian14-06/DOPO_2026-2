import java.awt.*;
import java.awt.geom.*;

/**
 * Represents a circle that can be manipulated and drawn on the canvas.
 * 
 * @author Juan Espitia
 * @author Cristian Salamanca
 */
public class Circle extends Figure {

    public static final double PI = 3.1416;
    private int diameter;

    /**
     * Creates a circle with default position and color.
     */
    public Circle() {
        this(30, 20, 15, "blue" );
    }

    /**
     * Creates a circle with specific diameter, position, and color.
     */
    public Circle(int diameter, int xPosition, int yPosition, String color) {
        super(xPosition, yPosition, color);
         this.diameter = diameter;
    }
    
    /**
     * Returns the figure type.
     */
    @Override
    public String type(){
        return "circle";
    }

    /**
     * Changes the circle diameter.
     */
    public void changeSize(int newDiameter) {
        erase();
         diameter = newDiameter;
        draw();
    }

    /**
     * Changes the size receiving an array with the new diameter.
     */
    @Override
    public void changeSize(int[] changes){
        if(changes != null && changes.length >= 1) {
            changeSize(changes[0]);
        }
    }

    /*
     * Draws the filled circle on screen if it is visible.
     */
    @Override
    protected void draw() {
        if (isVisible) {
             Canvas canvas = Canvas.getCanvas();
            canvas.draw(this, color, new Ellipse2D.Double(xPosition, yPosition, diameter, diameter));
            canvas.wait(10);
        }
    }

    /**
     * Draws the circle outline on screen.
     */
    @Override
    public void drawContorno() {
        if(isVisible){
            Canvas canvas = Canvas.getCanvas();
            canvas.draw2(this, "black", new Ellipse2D.Double(xPosition, yPosition, diameter, diameter) );
             canvas.wait(10);
        }
    }

    /**
     * Creates an exact copy of this circle.
     */
    @Override
    public Figure copy() {
        Circle newCircle = new Circle(this.diameter, this.xPosition, this.yPosition, this.color);
         newCircle.isVisible = this.isVisible;
        return newCircle;
    }
}
