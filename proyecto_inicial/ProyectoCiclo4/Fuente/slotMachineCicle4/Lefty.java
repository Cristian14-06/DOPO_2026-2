
/**
 * Represents a Lefty wheel in the slot machine.
 * When spun, if there is a wheel directly to its left, it copies its visible symbol.
 * If no wheel exists to its left, it behaves like a normal wheel.
 *
 * @author Juan Espitia
 * @author Cristian Salamanca
 * @version 2.0
 */
public class Lefty extends Wheel {

    private Wheel left;

    /**
     * Constructs a new Lefty wheel with an orange background.
     */
    public Lefty() {
        super();
        getWheel().changeColor("orange");
        left = null;
    }

    /**
     * Constructs a new Lefty wheel with an assigned left neighbor wheel.
     *
     * @param left the wheel positioned to the left
     */
    public Lefty(Wheel left) {
        super();
        getWheel().changeColor("orange");
        this.left = left;
    }

    /**
     * Updates the reference to the wheel positioned directly to the left.
     *
     * @param left the wheel to the left, or null if this is the first wheel
     */
    public void setLeft(Wheel left) {
        this.left = left;
    }

    /**
     * Spins the wheel. If a left neighbor exists, copies its visible symbol;
     * otherwise, performs a normal random spin.
     */
    @Override
    public void spin() {
        if (left == null) {
            super.spin();
            return;
        }
        Symbol temp = left.getShownSymbol();
        placeSymbol(temp);
    }

    /**
     * Spins the wheel by a given number of steps. If a left neighbor exists,
     * copies its visible symbol; otherwise, performs a normal step spin.
     *
     * @param steps the number of steps to rotate
     */
    @Override
    public void spin(int steps) {
        if (left == null) {
            super.spin(steps);
            return;
        }
        Symbol temp = left.getShownSymbol();
        placeSymbol(temp);
    }

    /**
     * Spins the wheel to display a given color. If a left neighbor exists,
     * copies its visible symbol; otherwise, performs a normal spin by color.
     *
     * @param color the target symbol color
     */
    @Override
    public void spin(String color) {
        if (left == null) {
            super.spin(color);
            return;
        }
        Symbol temp = left.getShownSymbol();
        placeSymbol(temp);
    }

    /**
     * Returns the type identifier for this wheel.
     *
     * @return the string "lefty"
     */
    @Override
    public String type() {
        return "lefty";
    }

    /**
     * Returns the type identifier for this wheel.
     *
     * @return the string "lefty"
     */
    @Override
    public String getType() {
        return type();
    }
}