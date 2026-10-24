import java.awt.*;

/**
 * Represents a rectangle that can be moved and drawn on the canvas.
 * 
 * @author Juan Espitia
 * @author Cristian Salamanca
 */
public class Rectangle extends Figure {

    public static int EDGES = 4;
    private int height;
    private int width;

    /**
     * Creates a rectangle with default values.
     */
    public Rectangle() {
         this(30, 40, 70, 15, "magenta");
    }

    /**
     * Creates a rectangle with given dimensions, position, and color.
     */
    public Rectangle(int height, int width, int xPosition, int yPosition, String color){
        super(xPosition, yPosition, color );
        this.height = height;
         this.width = width;
    }
    
    /**
     * Returns the geometric figure type.
     */
    @Override
    public String type(){
        return "rectangle";
    }

    /**
     * Changes the height and width of the rectangle.
     */
    public void changeSize(int newHeight, int newWidth) {
        erase();
        height = newHeight;
         width = newWidth;
        draw();
    }

    /**
     * Changes the dimensions from an array with height and width.
     */
    @Override
    public void changeSize(int[] heightWidth) {
        if(heightWidth != null && heightWidth.length >= 2){
            changeSize(heightWidth[0], heightWidth[1]);
        }
    }

    /*
     * Draws the rectangle on the canvas when visible.
     */
    @Override
    protected void draw() {
        if (isVisible) {
            Canvas canvas = Canvas.getCanvas();
             canvas.draw(this, color, new java.awt.Rectangle(xPosition, yPosition, width, height));
            canvas.wait(10);
        }
    }

    /**
     * Draws the black outline of the rectangle.
     */
    @Override
    public void drawContorno(){
        if (isVisible) {
            Canvas canvas = Canvas.getCanvas();
            canvas.draw2(this, "black", new java.awt.Rectangle(xPosition, yPosition, width, height) );
             canvas.wait(10);
        }
    }

    /**
     * Returns an independent copy of the rectangle.
     */
    @Override
    public Figure copy() {
        Rectangle newRect = new Rectangle(this.height, this.width, this.xPosition, this.yPosition, this.color);
        newRect.isVisible = this.isVisible;
         return newRect;
    }
}
