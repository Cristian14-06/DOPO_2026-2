/**
 * Represents a rebel wheel that cannot be locked, swapped, or deleted.
 *
 * @author Juan Espitia
 * @author Cristian Salamanca
 */
public class Rebel extends Wheel {

    /**
     * Creates a Rebel wheel with light gray color.
     */
    public Rebel() {
        super();
        getWheel().changeColor("lightGray" );
    }

    /**
     * Indicates if the wheel can be swapped.
     */
    @Override
    public boolean swapeable(){
         return false;
    }

    /**
     * Ignores locking since a Rebel wheel cannot be locked.
     */
    @Override
    public void setLock(boolean status) {
    }

    /**
     * Indicates if the wheel can be deleted from the machine.
     */
    @Override
    public boolean deleteable() {
        return false;
    }

    /**
     * Returns the wheel type.
     */
    @Override
    public String type(){
        return "rebel";
    }

    /**
     * Gets the type of this wheel.
     */
    @Override
    public String getType() {
         return type();
    }
}
