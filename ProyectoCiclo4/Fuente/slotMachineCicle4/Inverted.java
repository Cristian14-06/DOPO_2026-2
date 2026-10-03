/**
 * Represents an inverted wheel in the slot machine.
 * An inverted wheel rotates in the opposite direction when spun by steps,
 * and is visually distinguished by a cyan border/background.
 *
 * @author Juan Espitia
 * @author Cristian Salamanca
 * @version 1.0
 */
public class Inverted extends Wheel {

    /**
     * Constructs an Inverted wheel with cyan color.
     */
    public Inverted() {
        super();
        getWheel().changeColor("cyan");
    }

    /**
     * Spins the wheel in the reverse direction of the requested steps.
     *
     * @param steps number of steps to rotate
     */
    @Override
    public void spin(int steps) {
        super.spin(-steps);
    }

    @Override
    public String type() {
        return "inverted";
    }

    @Override
    public String getType() {
        return type();
    }
}
