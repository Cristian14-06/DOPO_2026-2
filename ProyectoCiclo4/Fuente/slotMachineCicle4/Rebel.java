
/**
 * Represents a Rebel wheel in the slot machine.
 * A Rebel wheel cannot be locked, swapped, or deleted from the machine.
 * It is visually distinguished by a light gray color.
 *
 * @author Juan Espitia
 * @author Cristian Salamanca
 * @version 2.0
 */
public class Rebel extends Wheel {

    /**
     * Constructs a new Rebel wheel with a light gray color.
     */
    public Rebel() {
        super();
        getWheel().changeColor("lightGray");
    }

    /**
     * Indicates whether this wheel can be swapped with another.
     *
     * @return false, as Rebel wheels cannot be swapped
     */
    @Override
    public boolean swapeable() {
        return false;
    }

    /**
     * Attempts to set the lock status. Rebel wheels refuse to be locked.
     *
     * @param status the requested lock status (ignored)
     */
    @Override
    public void setLock(boolean status) {
        return;
    }

    /**
     * Indicates whether this wheel can be deleted from the machine.
     *
     * @return false, as Rebel wheels cannot be deleted
     */
    @Override
    public boolean deleteable() {
        return false;
    }

    /**
     * Returns the type identifier for this wheel.
     *
     * @return the string "rebel"
     */
    @Override
    public String type() {
        return "rebel";
    }

    /**
     * Returns the type identifier for this wheel.
     *
     * @return the string "rebel"
     */
    @Override
    public String getType() {
        return type();
    }
}