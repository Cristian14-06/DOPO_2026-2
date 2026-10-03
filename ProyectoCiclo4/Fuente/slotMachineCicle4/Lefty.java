
/**
 * Write a description of class Lefty here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Lefty extends Wheel{
     private Wheel left;
     
     public Lefty(){
         super();
         getWheel().changeColor("orange");
         left = null;
     }
     
     public Lefty(Wheel left){
         super();
         getWheel().changeColor("orange");
         this.left = left;
     }
     
     public void setLeft(Wheel left){
         this.left = left;
     }
     
     @Override
     public void spin(){
         if(left == null){
             super.spin();
             return;
         }
         Symbol temp = left.getShownSymbol();
         placeSymbol(temp);
     }

     @Override
     public void spin(int steps){
         if(left == null){
             super.spin(steps);
             return;
         }
         Symbol temp = left.getShownSymbol();
         placeSymbol(temp);
     }
     
     @Override
     public String type() {
         return "lefty";
     }

     @Override
     public String getType() {
         return type();
     }
}