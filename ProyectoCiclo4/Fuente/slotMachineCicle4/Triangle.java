import java.awt.*;

/**
 * A triangle that can be manipulated and that draws itself on a canvas.
 * Inherits common positional and movement behavior from Figure.
 * 
 * @author  Michael Kolling and David J. Barnes
 * @author  Juan Espitia
 * @author  Cristian Salamanca
 * @version 2.0
 */
public class Triangle extends Figure {

    public static int VERTICES = 3;

    private int height;
    private int width;

    /**
     * Create a new triangle at default position with default color.
     */
    public Triangle() {
        this(30, 40, 140, 15, "green");
    }

    /**
     * Create a new triangle with specific dimensions, position, and color.
     *
     * @param height height of the triangle
     * @param width width of the triangle
     * @param xPosition horizontal position
     * @param yPosition vertical position
     * @param color fill color
     */
    public Triangle(int height, int width, int xPosition, int yPosition, String color) {
        super(xPosition, yPosition, color);
        this.height = height;
        this.width = width;
    }
    
    @Override
    public String type(){
        return "triangle";
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
     * @param changes array containing [height, width]
     */
    @Override
    public void changeSize(int[] changes) {
        if (changes != null && changes.length >= 2) {
            changeSize(changes[0], changes[1]);
        }
    }

    /**
     * Draw the triangle with current specifications on screen.
     */
    @Override
    protected void draw() {
        if (isVisible) {
            Canvas canvas = Canvas.getCanvas();
            int[] xpoints = { xPosition + (width / 2), xPosition + width, xPosition };
            int[] ypoints = { yPosition, yPosition + height, yPosition + height };
            canvas.draw(this, color, new Polygon(xpoints, ypoints, 3));
            canvas.wait(10);
        }
    }

    /**
     * Draw the outline of the triangle on screen.
     */
    @Override
    public void drawContorno() {
        if (isVisible) {
            Canvas canvas = Canvas.getCanvas();
            int[] xpoints = { xPosition + (width / 2), xPosition + width, xPosition };
            int[] ypoints = { yPosition, yPosition + height, yPosition + height };
            canvas.draw2(this, "black", new Polygon(xpoints, ypoints, 3));
            canvas.wait(10);
        }
    }

    @Override
    public Figure copy() {
        Triangle newTri = new Triangle(this.height, this.width, this.xPosition, this.yPosition, this.color);
        newTri.isVisible = this.isVisible;
        return newTri;
    }
}
