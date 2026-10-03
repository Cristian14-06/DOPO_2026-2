/**
 * Write a description of class Ephemeral here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Ephemeral extends Symbol {

    public Ephemeral(Figure figure, String color) {
        super(figure, color);
    }
    
    @Override
    public String nature() {
        return "ephemeral";
    }
    
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

    @Override
    public Symbol copy() {
        Figure newFigure = (figure != null) ? figure.copy() : null;
        Ephemeral copy = new Ephemeral(newFigure, getColor());
        copy.setSize(getSize());
        copy.setCanBeVisible(getCanBeVisible());
        return copy;
    }
}
