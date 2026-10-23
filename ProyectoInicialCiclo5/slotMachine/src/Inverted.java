/**
 * Represents an inverted wheel that spins in the opposite direction.
 *
 * @author Juan Espitia
 * @author Cristian Salamanca
 */
public class Inverted extends Wheel {

    /**
     * Creates an inverted wheel with cyan color.
     */
    public Inverted() {
        super();
         getWheel().changeColor("cyan");
    }

    /**
     * Spins the wheel in the opposite direction of the given steps.
     */
    @Override
    public void spin(int steps){
        super.spin(-steps );
    }

    /**
     * Returns the wheel type.
     */
    @Override
    public String type() {
         return "inverted";
    }

    /**
     * Gets the wheel type.
     */
    @Override
    public String getType() {
        return type();
    }
}
