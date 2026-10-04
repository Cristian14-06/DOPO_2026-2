/**
 * Represents a Shy symbol in the slot machine.
 * It toggles its visibility between visible and invisible every time it is selected in a wheel.
 * It is visually distinguished by an outline contour.
 *
 * @author Juan Espitia
 * @author Cristian Salamanca
 * @version 2.0
 */
public class Shy extends Symbol {

    /**
     * Constructs a new Shy symbol with the specified figure and color.
     *
     * @param figure the underlying geometric figure
     * @param color the color of the symbol
     */
    public Shy(Figure figure, String color) {
        super(figure, color);
    }
    
    /**
     * Returns the nature identifier for this symbol.
     *
     * @return the string "shy"
     */
    @Override
    public String nature() {
        return "shy";
    }
    
    /**
     * Inverts the visibility state of this symbol whenever it is selected.
     */
    @Override
    public void verifyCanBeVisible() {
        setCanBeVisible(!getCanBeVisible());
    }
    
    @Override 
    protected boolean hasContorn() {
        return true;
    }

    /**
     * Creates an independent copy of this Shy symbol preserving its concrete type and state.
     *
     * @return an independent clone of this Shy symbol
     */
    @Override
    public Symbol copy() {
        Figure newFigure = (figure != null) ? figure.copy() : null;
        Shy copy = new Shy(newFigure, getColor());
        copy.setSize(getSize());
        copy.setCanBeVisible(getCanBeVisible());
        return copy;
    }
}
