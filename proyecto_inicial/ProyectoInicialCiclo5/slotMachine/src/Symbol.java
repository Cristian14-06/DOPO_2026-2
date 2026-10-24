/**
 * Represents a symbol inside the wheels of the slot machine.
 *
 * @author Juan Espitia
 * @author Cristian Salamanca
 */
public class Symbol
{
    protected Figure figure;
    private String color;
    private int size;
    private boolean canBeVisible;

    /**
     * Creates a symbol with the given figure and color.
     */
    public Symbol(Figure figure, String color)
    {
        this.figure = figure;
         this.color = color;
        this.size = 20;
        this.canBeVisible = true;
    }
    
    /**
     * Gets the current size of the symbol.
     */
    public int getSize(){
        return size;
    }
    
    /**
     * Sets the size of the symbol.
     */
    public void setSize(int size){
         this.size = size;
    }
    
    /**
     * Returns the nature of the symbol.
     */
    public String nature(){
        return "normal";
    }
    
    /**
     * Indicates if the symbol is allowed to be shown.
     */
    public boolean getCanBeVisible(){
        return canBeVisible;
    }
    
    /**
     * Updates the allowed visibility state of the symbol.
     */
    public void setCanBeVisible(boolean state){
         canBeVisible = state;
    }

    /**
     * Returns the geometric type of the associated figure.
     */
    public String getType()
    {
        return figure.type();
    }

    /**
     * Returns the color that identifies the symbol.
     */
    public String getColor()
    {
        return color;
    }

    /**
     * Creates an independent copy of the current symbol.
     */
    public Symbol copy()
    {
        Figure newFigure = (figure != null) ? figure.copy() : null;
        Symbol copy = new Symbol(newFigure, color );
         copy.setSize(size);
        copy.setCanBeVisible(canBeVisible);
        return copy;
    }
    
    /**
     * Decreases the symbol size when applicable according to its type.
     */
    public void decrement(){
    }

    /**
     * Verifies and updates whether the symbol can be visible.
     */
    public void verifyCanBeVisible(){
    }
    
    /**
     * Draws the symbol at the given coordinates if it can be seen.
     */
    public void makeVisible(int x,int y){
        if(!canBeVisible || figure == null){
            return;
        }
        
        figure.makeVisible();
         figure.changeSize(new int[]{size,size});
        figure.changeColor(color);
        figure.setPosition(x,y );
        figure.draw();
        
        if(hasContorn()){
             figure.drawContorno();
        }
    }
    
    /**
     * Hides the graphical representation of the symbol.
     */
    public void makeInvisible(){
        if(figure != null){
            figure.makeInvisible();
        }
    }
    
    /*
     * Indicates if this symbol should draw an outline.
     */
    protected boolean hasContorn(){
        return false;
    }
    
    /**
     * Returns a string with the type and color of the symbol.
     */
    public String toString()
    {
        return figure.type() + " (" + color + ")";
    }
}
