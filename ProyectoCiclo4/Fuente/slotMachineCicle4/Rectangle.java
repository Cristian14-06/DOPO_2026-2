import java.awt.*;

/**
 * A rectangle that can be manipulated and that draws itself on a canvas.
 * Inherits common positional and movement behavior from Figure.
 * 
 * @author  Michael Kolling and David J. Barnes (Modified)
 * @author  Juan Espitia
 * @author  Cristian Salamanca
 * @version 2.0
 */
public class Rectangle extends Figure {

    public static int EDGES = 4;
    private int height;
    private int width;

    /**
     * Create a new rectangle at default position with default color.
     */
    public Rectangle() {
        this(30, 40, 70, 15, "magenta");
    }

    /**
     * Create a new rectangle with specific dimensions, position, and color.
     *
     * @param height height of the rectangle
     * @param width width of the rectangle
     * @param xPosition horizontal position
     * @param yPosition vertical position
     * @param color fill color
     */
    public Rectangle(int height, int width, int xPosition, int yPosition, String color) {
        super(xPosition, yPosition, color);
        this.height = height;
        this.width = width;
    }
    
    @Override
    public String type(){
        return "rectangle";
    }

    /**
     * Change the size to the new dimensions.
     *
     * @param newHeight the new height in pixels (>= 0)
     * @param newWidth the new width in pixels (>= 0)
     */
    public void changeSize(int newHeight, int newWidth) {
        erase();
        height = newHeight;
        width = newWidth;
        draw();
    }

    /**
     * Polymorphic size change implementation from Figure.
     *
     * @param heightWidth array containing [height, width]
     */
    @Override
    public void changeSize(int[] heightWidth) {
        if (heightWidth != null && heightWidth.length >= 2) {
            changeSize(heightWidth[0], heightWidth[1]);
        }
    }

    /**
     * Draw the rectangle with current specifications on screen.
     */
    @Override
    protected void draw() {
        if (isVisible) {
            Canvas canvas = Canvas.getCanvas();
            canvas.draw(this, color,
                new java.awt.Rectangle(xPosition, yPosition, width, height));
            canvas.wait(10);
        }
    }

    /**
     * Draw the outline of the rectangle on screen.
     */
    @Override
    public void drawContorno() {
        if (isVisible) {
            Canvas canvas = Canvas.getCanvas();
            canvas.draw2(this, "black",
                new java.awt.Rectangle(xPosition, yPosition, width, height));
            canvas.wait(10);
        }
    }

    @Override
    public Figure copy() {
        Rectangle newRect = new Rectangle(this.height, this.width, this.xPosition, this.yPosition, this.color);
        newRect.isVisible = this.isVisible;
        return newRect;
    }
}
