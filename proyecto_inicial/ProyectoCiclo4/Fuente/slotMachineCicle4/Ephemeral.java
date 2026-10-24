/**
 * Represents an Ephemeral symbol in the slot machine.
 * On each spin, its size decrements by 1 until it becomes a dot of 1 pixel.
 * It is visually distinguished by an outline contour.
 *
 * @author Juan Espitia
 * @author Cristian Salamanca
 * @version 2.0
 */
public class Ephemeral extends Symbol {

    /**
     * Constructs a new Ephemeral symbol with the specified figure and color.
     *
     * @param figure the underlying geometric figure
     * @param color the color of the symbol
     */
    public Ephemeral(Figure figure, String color) {
        super(figure, color);
    }
    
    /**
     * Returns the nature identifier for this symbol.
     *
     * @return the string "ephemeral"
     */
    @Override
    public String nature() {
        return "ephemeral";
    }
    
    /**
     * Decrements the size of this symbol by 1 pixel until reaching 1 pixel.
     */
    @Override
    public void decrement() {
        if (getSize() > 1) {
            setSize(getSize() - 1);
        }
    }
    
    @Override 
    protected boolean hasContorn() {
        return true;
    }

    /**
     * Creates an independent copy of this Ephemeral symbol preserving its concrete type and state.
     *
     * @return an independent clone of this Ephemeral symbol
     */
    @Override
    public Symbol copy() {
        Figure newFigure = (figure != null) ? figure.copy() : null;
        Ephemeral copy = new Ephemeral(newFigure, getColor());
        copy.setSize(getSize());
        copy.setCanBeVisible(getCanBeVisible());
        return copy;
    }
}
