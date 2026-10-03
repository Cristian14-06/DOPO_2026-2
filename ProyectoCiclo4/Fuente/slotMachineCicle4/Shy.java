/**
 * Write a description of class Shy here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Shy extends Symbol {

    public Shy(Figure figure, String color) {
        super(figure, color);
    }
    
    @Override
    public String nature() {
        return "shy";
    }
    
    @Override
    public void verifyCanBeVisible() {
        setCanBeVisible(!getCanBeVisible());
    }
    
    @Override 
    protected boolean hasContorn() {
        return true;
    }

    @Override
    public Symbol copy() {
        Figure newFigure = (figure != null) ? figure.copy() : null;
        Shy copy = new Shy(newFigure, getColor());
        copy.setSize(getSize());
        copy.setCanBeVisible(getCanBeVisible());
        return copy;
    }
}
