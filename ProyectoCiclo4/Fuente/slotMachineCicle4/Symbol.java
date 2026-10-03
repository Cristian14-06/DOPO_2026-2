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
     * Creates a new symbol with the specified type and color.
     *
     * @param tipo the type of the symbol
     * @param color the color of the symbol
     */
    public Symbol(Figure figure, String color)
    {
        this.figure = figure;
        this.color = color;
        this.size = 20;
        this.canBeVisible = true;
    }
    
    public int getSize(){
        return size;
    }
    
    public void setSize(int size){
        this.size = size;
    }
    
    public String nature(){
        return "normal";
    }
    
    public boolean getCanBeVisible(){
        return canBeVisible;
    }
    
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
    
    public void decrement(){
        return;
    }

    public void verifyCanBeVisible(){
        return;
    }
    
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
    
    public void makeInvisible(){
        if(figure != null){
            figure.makeInvisible();
        }
    }
    
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
