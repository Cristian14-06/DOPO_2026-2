/**
 * Represents a shy symbol that toggles its visibility when selected.
 *
 * @author Juan Espitia
 * @author Cristian Salamanca
 */
public class Shy extends Symbol {

    /**
     * Creates a Shy symbol with the given figure and color.
     */
    public Shy(Figure figure, String color) {
        super(figure, color );
    }
    
    /**
     * Returns the symbol nature.
     */
    @Override
    public String nature() {
         return "shy";
    }
    
    /**
     * Inverts the visibility state of the symbol when selected.
     */
    @Override
    public void verifyCanBeVisible(){
        setCanBeVisible(!getCanBeVisible());
    }
    
    /*
     * Indicates if it should be drawn with an outline.
     */
    @Override 
    protected boolean hasContorn() {
        return true;
    }

    /**
     * Creates an independent copy of the Shy symbol.
     */
    @Override
    public Symbol copy(){
        Figure newFigure = (figure != null) ? figure.copy() : null;
        Shy copy = new Shy(newFigure, getColor());
        copy.setSize(getSize() );
         copy.setCanBeVisible(getCanBeVisible());
        return copy;
    }
}
