import java.awt.*;
import java.awt.geom.*;

/**
 * A circle that can be manipulated and that draws itself on a canvas.
 * Inherits common positional and movement behavior from Figure.
 * 
 * @author  Michael Kolling and David J. Barnes
 * @author  Juan Espitia
 * @author  Cristian Salamanca
 * @version 2.0
 */
public class Circle extends Figure {

    public static final double PI = 3.1416;
    private int diameter;

    /**
     * Create a new circle at default position with default color.
     */
    public Circle() {
        this(30, 20, 15, "blue");
    }

    /**
     * Create a new circle with specific diameter, position, and color.
     *
     * @param diameter diameter of the circle
     * @param xPosition horizontal position
     * @param yPosition vertical position
     * @param color fill color
     */
    public Circle(int diameter, int xPosition, int yPosition, String color) {
        super(xPosition, yPosition, color);
        this.diameter = diameter;
    }
    
    @Override
    public String type(){
        return "circle";
    }

    /**
     * Change the size.
     * @param newDiameter the new size in pixels (>= 0).
     */
    public void changeSize(int newDiameter) {
        erase();
        diameter = newDiameter;
        draw();
    }

    /**
     * Polymorphic size change implementation from Figure.
     *
     * @param changes array containing [diameter]
     */
    @Override
    public void changeSize(int[] changes) {
        if (changes != null && changes.length >= 1) {
            changeSize(changes[0]);
        }
    }

    /**
     * Draw the circle with current specifications on screen.
     */
    @Override
    protected void draw() {
        if (isVisible) {
            Canvas canvas = Canvas.getCanvas();
            canvas.draw(this, color,
                new Ellipse2D.Double(xPosition, yPosition, diameter, diameter));
            canvas.wait(10);
        }
    }

    /**
     * Draw the outline of the circle on screen.
     */
    @Override
    public void drawContorno() {
        if (isVisible) {
            Canvas canvas = Canvas.getCanvas();
            canvas.draw2(this, "black",
                new Ellipse2D.Double(xPosition, yPosition, diameter, diameter));
            canvas.wait(10);
        }
    }

    @Override
    public Figure copy() {
        Circle newCircle = new Circle(this.diameter, this.xPosition, this.yPosition, this.color);
        newCircle.isVisible = this.isVisible;
        return newCircle;
    }
}