
/**
 * Write a description of class Rebel here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Rebel extends Wheel{
    public Rebel(){
        super();
        getWheel().changeColor("lightGray");
    }
    
    @Override
    public boolean swapeable(){
        return false;
    }
    
    @Override
    public void setLock(boolean status) {
        return;
    }
    
    @Override
    public boolean deleteable(){
        return false;
    }

    @Override
    public String type() {
        return "rebel";
    }

    @Override
    public String getType() {
        return type();
    }
}