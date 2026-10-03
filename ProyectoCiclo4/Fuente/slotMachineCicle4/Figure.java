/**
 * Abstract class representing a geometric figure that can be manipulated
 * and drawn on the Canvas.
 *
 * Provides common movement, positioning, visibility, and color capabilities.
 *
 * @author Juan Espitia
 * @author Cristian Salamanca
 * @version 1.0
 */
public abstract class Figure {
    protected int xPosition;
    protected int yPosition;
    protected String color;
    protected boolean isVisible;

    /**
     * Default constructor for Figure.
     */
    public Figure() {
        this(0, 0, "black");
    }

    /**
     * Parameterized constructor for Figure.
     *
     * @param xPosition initial horizontal position
     * @param yPosition initial vertical position
     * @param color initial color
     */
    public Figure(int xPosition, int yPosition, String color) {
        this.xPosition = xPosition;
        this.yPosition = yPosition;
        this.color = color;
        this.isVisible = false;
    }
    

    
    public abstract String type();

    /**
     * Makes this figure visible and draws it on canvas.
     */
    public void makeVisible() {
        isVisible = true;
        draw();
    }

    /**
     * Makes this figure invisible and erases it from canvas.
     */
    public void makeInvisible() {
        erase();
        isVisible = false;
    }

    /**
     * Moves the figure to an absolute (x, y) position.
     *
     * @param x new horizontal coordinate
     * @param y new vertical coordinate
     */
    public void setPosition(int x, int y) {
        erase();
        xPosition = x;
        yPosition = y;
        draw();
    }

    public int getXPosition() {
        return xPosition;
    }

    public int getYPosition() {
        return yPosition;
    }

    public String getColor() {
        return color;
    }

    public boolean isVisible() {
        return isVisible;
    }

    public void moveRight() {
        moveHorizontal(20);
    }

    public void moveLeft() {
        moveHorizontal(-20);
    }

    public void moveUp() {
        moveVertical(-20);
    }

    public void moveDown() {
        moveVertical(20);
    }

    public void moveHorizontal(int distance) {
        erase();
        xPosition += distance;
        draw();
    }

    public void moveVertical(int distance) {
        erase();
        yPosition += distance;
        draw();
    }

    public void slowMoveHorizontal(int distance) {
        int delta = (distance < 0) ? -1 : 1;
        distance = Math.abs(distance);

        for (int i = 0; i < distance; i++) {
            xPosition += delta;
            draw();
        }
    }

    public void slowMoveVertical(int distance) {
        int delta = (distance < 0) ? -1 : 1;
        distance = Math.abs(distance);

        for (int i = 0; i < distance; i++) {
            yPosition += delta;
            draw();
        }
    }

    public void changeColor(String newColor) {
        color = newColor;
        draw();
    }

    /**
     * Erases the figure from the canvas.
     */
    protected void erase() {
        if (isVisible) {
            Canvas canvas = Canvas.getCanvas();
            canvas.erase(this);
        }
    }

    /**
     * Polymorphic size change accepting an array of parameters.
     *
     * @param changes size parameters
     */
    public abstract void changeSize(int[] changes);

    /**
     * Draws the filled figure on the canvas.
     */
    protected abstract void draw();

    /**
     * Draws the contour/outline of the figure on the canvas.
     */
    public abstract void drawContorno();

    /**
     * Creates an independent copy of this figure with the same dimensions,
     * position, and color.
     *
     * @return a new Figure instance identical in state to this one
     */
    public abstract Figure copy();
}