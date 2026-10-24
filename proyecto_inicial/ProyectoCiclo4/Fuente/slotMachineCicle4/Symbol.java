/**
 * Represents a symbol of the slot machine.
 * Each symbol is defined by a type and a color.
 * The color identifies the symbol and distinguishes it
 * from the other symbols in the machine.
 *
 * The visual representation of the symbol is handled by
 * the wheel that displays it.
 *
 * @author Juan Espitia
 * @author Cristian Salamanca
 * @version 1.0
 */
public class Symbol
{
    protected Figure figure;
    private String color;
    private int size;
    private boolean canBeVisible;

    /**
     * Creates a new symbol with the specified figure and color.
     *
     * @param figure the figure shape of the symbol
     * @param color the color of the symbol
     */
    public Symbol(Figure figure, String color)
    {
        this.figure = figure;
        this.color = color;
        this.size = 20;
        this.canBeVisible = true;
    }
    
    /**
     * Returns the current size of this symbol.
     *
     * @return the size in pixels
     */
    public int getSize(){
        return size;
    }
    
    /**
     * Sets the size of this symbol.
     *
     * @param size the new size in pixels
     */
    public void setSize(int size){
        this.size = size;
    }
    
    /**
     * Returns the nature/kind of this symbol.
     *
     * @return the string "normal"
     */
    public String nature(){
        return "normal";
    }
    
    /**
     * Returns whether this symbol is currently allowed to be visible.
     *
     * @return true if visible, false otherwise
     */
    public boolean getCanBeVisible(){
        return canBeVisible;
    }
    
    /**
     * Updates whether this symbol can be visible.
     *
     * @param state true to allow visibility, false to hide
     */
    public void setCanBeVisible(boolean state){
        canBeVisible = state;
    }


    /**
     * Returns the type of this symbol.
     *
     * @return the type of the symbol
     */
    public String getType()
    {
        return figure.type();
    }


    /**
     * Returns the color of this symbol.
     *
     * @return the color of the symbol
     */
    public String getColor()
    {
        return color;
    }

    /**
     * Creates an independent copy of this symbol, including its current state.
     * Subclasses should override this method to preserve their concrete type.
     *
     * @return an independent copy of this symbol
     */
    public Symbol copy()
    {
        Figure newFigure = (figure != null) ? figure.copy() : null;
        Symbol copy = new Symbol(newFigure, color);
        copy.setSize(size);
        copy.setCanBeVisible(canBeVisible);
        return copy;
    }
    
    /**
     * Decrements the size or state of this symbol if applicable.
     * Default implementation does nothing.
     */
    public void decrement(){
        return;
    }

    /**
     * Verifies and updates whether this symbol is allowed to be visible.
     * Default implementation does nothing.
     */
    public void verifyCanBeVisible(){
        return;
    }
    
    /**
     * Renders this symbol at the specified coordinates if visible.
     *
     * @param x the horizontal position
     * @param y the vertical position
     */
    public void makeVisible(int x,int y){
        if(!canBeVisible || figure == null){
            return;
        }
        
        figure.makeVisible();
        figure.changeSize(new int[]{size,size});
        figure.changeColor(color);
        figure.setPosition(x,y);
        figure.draw();
        
        if(hasContorn()){
            figure.drawContorno();
        }
    }
    
    /**
     * Hides the graphical representation of this symbol.
     */
    public void makeInvisible(){
        if(figure != null){
            figure.makeInvisible();
        }
    }
    
    /**
     * Indicates whether this symbol should draw an outline contour.
     *
     * @return true if an outline contour should be drawn, false otherwise
     */
    protected boolean hasContorn(){
        return false;
    }
    
    /**
     * Returns a textual representation of this symbol.
     * The representation contains its type and color.
     *
     * @return a string containing the type and color
     */
    public String toString()
    {
        return figure.type() + " (" + color + ")";
    }
}
