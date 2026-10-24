/**
 * Represents the main body of the slot machine.
 * 
 * @author Juan Espitia
 * @author Cristian Salamanca
 */
public class BodyMachine
{
    private Rectangle body;
    private boolean visible;

    /**
     * Creates the machine body with its default color and position.
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
     * Checks if the body is currently visible.
     */
    public boolean isVisible(){
        return visible;
    }

    /**
     * Makes the machine body visible.
     */
    public void makeVisible()
    {
        visible = true;
         body.makeVisible();
    }


    /**
     * Hides the machine body.
     */
    public void makeInvisible() {
        visible = false;
        body.makeInvisible();
    }

    /**
     * Changes the body color to yellow when there is a jackpot.
     */
    public void changeColor()
    {
       body.changeColor("yellow");
    }

    /**
     * Restores the normal blue color of the body.
     */
    public void normalColor()
    {
        body.changeColor("blue" );
    }
}
