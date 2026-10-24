import java.awt.*;

/**
 * Represents a triangle that can be manipulated and drawn on the canvas.
 * 
 * @author Juan Espitia
 * @author Cristian Salamanca
 */
public class Triangle extends Figure {

    public static int VERTICES = 3;

    private int height;
    private int width;

    /**
     * Creates a triangle at its default position and color.
     */
    public Triangle() {
        this(30, 40, 140, 15, "green");
    }

    /**
     * Creates a triangle with specific height, width, position, and color.
     */
    public Triangle(int height, int width, int xPosition, int yPosition, String color) {
        super(xPosition, yPosition, color );
         this.height = height;
        this.width = width;
    }
    
    /**
     * Returns the figure type name.
     */
    @Override
    public String type(){
        return "triangle";
    }

    /**
     * Modifies the height and width of the triangle.
     */
    public void changeSize(int newHeight, int newWidth) {
        erase();
         height = newHeight;
        width = newWidth;
        draw();
    }

    /**
     * Changes the size receiving an array with the dimensions.
     */
    @Override
    public void changeSize(int[] changes){
        if (changes != null && changes.length >= 2) {
             changeSize(changes[0], changes[1]);
        }
    }

    /*
     * Draws the filled triangle on the canvas.
     */
    @Override
    protected void draw() {
        if(isVisible) {
            Canvas canvas = Canvas.getCanvas();
            int[] xpoints = { xPosition + (width / 2), xPosition + width, xPosition };
             int[] ypoints = { yPosition, yPosition + height, yPosition + height };
            canvas.draw(this, color, new Polygon(xpoints, ypoints, 3));
            canvas.wait(10);
        }
    }

    /**
     * Draws the black outline of the triangle on the canvas.
     */
    @Override
    public void drawContorno() {
        if (isVisible){
            Canvas canvas = Canvas.getCanvas();
            int[] xpoints = { xPosition + (width / 2), xPosition + width, xPosition };
            int[] ypoints = { yPosition, yPosition + height, yPosition + height };
             canvas.draw2(this, "black", new Polygon(xpoints, ypoints, 3) );
            canvas.wait(10);
        }
    }

    /**
     * Creates an independent copy of the current triangle.
     */
    @Override
    public Figure copy() {
        Triangle newTri = new Triangle(this.height, this.width, this.xPosition, this.yPosition, this.color);
        newTri.isVisible = this.isVisible;
         return newTri;
    }
}
