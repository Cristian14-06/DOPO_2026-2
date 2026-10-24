/**
 * Represents a Lefty wheel that copies the symbol from the wheel to its left.
 *
 * @author Juan Espitia
 * @author Cristian Salamanca
 */
public class Lefty extends Wheel {

    private Wheel left;

    /**
     * Creates a Lefty wheel with default orange color.
     */
    public Lefty() {
        super();
        getWheel().changeColor("orange");
         left = null;
    }

    /**
     * Creates a Lefty wheel assigning its left neighbor wheel.
     */
    public Lefty(Wheel left){
        super();
        getWheel().changeColor("orange" );
        this.left = left;
    }

    /**
     * Updates the reference to the wheel located on the left.
     */
    public void setLeft(Wheel left) {
         this.left = left;
    }

    /**
     * Spins the wheel copying its neighbor or spinning normally if it has no neighbor.
     */
    @Override
    public void spin() {
        if (getLock() || symbols.isEmpty()) {
            return;
        }
        if(left == null) {
             super.spin();
            return;
        }
        Symbol temp = left.getShownSymbol();
        placeSymbol(temp);
         decrement();
        refresh();
    }

    /**
     * Spins the wheel a number of steps or copies its left neighbor.
     */
    @Override
    public void spin(int steps) {
        if(getLock() || symbols.isEmpty()){
            return;
        }
        if (left == null) {
            super.spin(steps);
            return;
        }
        Symbol temp = left.getShownSymbol();
         placeSymbol(temp);
        decrement();
        refresh();
    }

    /**
     * Spins the wheel looking for a color or copies the left symbol.
     */
    @Override
    public void spin(String color){
        if (getLock() || symbols.isEmpty()) {
             return;
        }
        if (left == null){
            super.spin(color);
            return;
        }
        Symbol temp = left.getShownSymbol();
        placeSymbol(temp );
        decrement();
         refresh();
    }

    /**
     * Returns the wheel type.
     */
    @Override
    public String type() {
        return "lefty";
    }

    /**
     * Gets the type identifier of the wheel.
     */
    @Override
    public String getType(){
        return type();
    }
}
