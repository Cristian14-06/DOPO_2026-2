
/**
 * Represents the main body of the slot machine.
 * The body provides the visual frame that contains the wheels
 * and changes its color when a jackpot occurs.
 *
 * @author Juan Espitia
 * @author Cristian Salamanca
 * @version 1.0
 */
public class BodyMachine
{
    private Rectangle body;
    private boolean visible;


    /**
     * Creates a new slot machine body with its default size,
     * position, and blue color.
     */
    public BodyMachine()
    {
        body = new Rectangle();
        body.changeSize(130, 250);
        body.moveHorizontal(-50);
        body.moveVertical(65);
        body.changeColor("blue");

        visible = false;
    }


    /**
     * Makes the slot machine body visible.
     */
    public void makeVisible()
    {
        body.makeVisible();
    }


    /**
     * Makes the slot machine body invisible.
     */
    public void makeInvisible()
    {
        body.makeInvisible();
    }


    /**
     * Changes the body color to yellow to indicate a jackpot.
     */
    public void changeColor()
    {
        body.changeColor("yellow");
    }


    /**
     * Restores the body to its normal blue color.
     */
    public void normalColor()
    {
        body.changeColor("blue");
    }
}

      
