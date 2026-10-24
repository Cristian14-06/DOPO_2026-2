/**
 * Represents an ephemeral symbol whose size decreases on each spin.
 *
 * @author Juan Espitia
 * @author Cristian Salamanca
 */
public class Ephemeral extends Symbol {

    /**
     * Creates a new Ephemeral symbol with reduced initial size.
     */
    public Ephemeral(Figure figure, String color) {
        super(figure, color);
         setSize(18);
    }
    
    /**
     * Returns the symbol nature.
     */
    @Override
    public String nature(){
        return "ephemeral";
    }
    
    /**
     * Decreases the symbol size by 1 pixel down to 1.
     */
    @Override
    public void decrement() {
        if(getSize() > 1){
             setSize(getSize() - 1);
        }
    }
    
    /*
     * Indicates if the symbol has a black outline.
     */
    @Override 
    protected boolean hasContorn() {
        return true;
    }

    /**
     * Creates an independent copy of the Ephemeral symbol.
     */
    @Override
    public Symbol copy() {
        Figure newFigure = (figure != null) ? figure.copy() : null;
        Ephemeral copy = new Ephemeral(newFigure, getColor() );
         copy.setSize(getSize());
        copy.setCanBeVisible(getCanBeVisible());
        return copy;
    }
}
