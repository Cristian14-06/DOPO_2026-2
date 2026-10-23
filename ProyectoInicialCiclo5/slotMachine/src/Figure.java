/**
 * Abstract class representing a geometric figure that can be manipulated on the canvas.
 *
 * @author Juan Espitia
 * @author Cristian Salamanca
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
        this(0, 0, "black" );
    }

    /**
     * Creates a figure with initial coordinates and color.
     */
    public Figure(int xPosition, int yPosition, String color) {
        this.xPosition = xPosition;
         this.yPosition = yPosition;
        this.color = color;
        this.isVisible = false;
    }
    
    /**
     * Returns the figure type.
     */
    public abstract String type();

    /**
     * Makes the figure visible and draws it.
     */
    public void makeVisible() {
        isVisible = true;
         draw();
    }

    /**
     * Makes the figure invisible and erases it from the canvas.
     */
    public void makeInvisible(){
        erase();
        isVisible = false;
    }

    /**
     * Moves the figure to a specific (x, y) position.
     */
    public void setPosition(int x, int y) {
        erase();
        xPosition = x;
         yPosition = y;
        draw();
    }

    /**
     * Returns the horizontal X coordinate.
     */
    public int getXPosition() {
        return xPosition;
    }

    /**
     * Returns the vertical Y coordinate.
     */
    public int getYPosition(){
        return yPosition;
    }

    /**
     * Returns the current color of the figure.
     */
    public String getColor() {
         return color;
    }

    /**
     * Indicates if the figure is visible.
     */
    public boolean isVisible() {
        return isVisible;
    }

    /**
     * Moves the figure one step to the right.
     */
    public void moveRight() {
        moveHorizontal(20 );
    }

    /**
     * Moves the figure one step to the left.
     */
    public void moveLeft(){
        moveHorizontal(-20);
    }

    /**
     * Moves the figure upward.
     */
    public void moveUp() {
         moveVertical(-20);
    }

    /**
     * Moves the figure downward.
     */
    public void moveDown() {
        moveVertical(20);
    }

    /**
     * Moves the figure horizontally by the given distance.
     */
    public void moveHorizontal(int distance) {
        erase();
        xPosition += distance;
         draw();
    }

    /**
     * Moves the figure vertically by the given distance.
     */
    public void moveVertical(int distance){
        erase();
         yPosition += distance;
        draw();
    }

    /**
     * Slowly moves the figure horizontally.
     */
    public void slowMoveHorizontal(int distance) {
        int delta = (distance < 0) ? -1 : 1;
        distance = Math.abs(distance);

        for(int i = 0; i < distance; i++) {
             xPosition += delta;
            draw();
        }
    }

    /**
     * Slowly moves the figure vertically.
     */
    public void slowMoveVertical(int distance) {
        int delta = (distance < 0) ? -1 : 1;
         distance = Math.abs(distance);

        for (int i = 0; i < distance; i++){
            yPosition += delta;
            draw();
        }
    }

    /**
     * Changes the figure color and redraws it.
     */
    public void changeColor(String newColor) {
        color = newColor;
         draw();
    }

    /*
     * Erases the figure from the canvas if it is visible.
     */
    protected void erase() {
        if(isVisible) {
            Canvas canvas = Canvas.getCanvas();
             canvas.erase(this);
        }
    }

    /**
     * Changes the size of the figure according to the given parameters.
     */
    public abstract void changeSize(int[] changes);

    /*
     * Draws the filled figure on the canvas.
     */
    protected abstract void draw();

    /**
     * Draws the outline of the figure on the canvas.
     */
    public abstract void drawContorno();

    /**
     * Creates an independent copy of the figure.
     */
    public abstract Figure copy();
}
