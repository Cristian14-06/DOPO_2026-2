import javax.swing.*;
import java.awt.*;
import java.util.List;
import java.util.*;

/**
 * Class that allows drawing simple graphical figures on a window.
 *
 * @author Juan Espitia
 * @author Cristian Salamanca
 */
public final class Canvas{
    private static Canvas canvasSingleton;

    /**
     * Returns the single instance of the canvas.
     */
    public static Canvas getCanvas(){
        if(canvasSingleton == null) {
             canvasSingleton = new Canvas("BlueJ Shapes Demo", 300, 300, Color.white);
        }
        canvasSingleton.setVisible(true);
        return canvasSingleton;
    }

    private JFrame frame;
    private CanvasPane canvas;
    private Graphics2D graphic;
    private Color backgroundColour;
    private Image canvasImage;
    private List <Object> objects;
    private HashMap <Object,ShapeDescription> shapes;
    
    /*
     * Creates a new Canvas with title, dimensions, and background color.
     */
    private Canvas(String title, int width, int height, Color bgColour){
        frame = new JFrame();
        canvas = new CanvasPane();
         frame.setContentPane(canvas);
        frame.setTitle(title);
        canvas.setPreferredSize(new Dimension(width, height));
        backgroundColour = bgColour;
        frame.pack();
         objects = new ArrayList <Object>();
        shapes = new HashMap <Object,ShapeDescription>();
    }

    /**
     * Changes the visibility of the canvas window.
     */
    public void setVisible(boolean visible){
        if(graphic == null) {
            Dimension size = canvas.getSize();
            canvasImage = canvas.createImage(size.width, size.height);
             graphic = (Graphics2D)canvasImage.getGraphics();
            graphic.setColor(backgroundColour);
            graphic.fillRect(0, 0, size.width, size.height);
            graphic.setColor(Color.black);
        }
        frame.setVisible(visible);
    }

    /**
     * Draws a filled shape onto the canvas.
     */
    public void draw(Object referenceObject, String color, Shape shape){
        objects.remove(referenceObject);
         objects.add(referenceObject);
        shapes.put(referenceObject, new ShapeDescription(shape, color, true));
        redraw();
    }
    
    /**
     * Draws the outline of a shape onto the canvas.
     */
    public void draw2(Object referenceObject, String color, Shape shape){
        ShapeDescription existing = shapes.get(referenceObject);
        if (existing != null) {
            existing.setOutlineColor(color);
             existing.setShape(shape);
        } else {
            objects.remove(referenceObject);
            objects.add(referenceObject);
            ShapeDescription desc = new ShapeDescription(shape, color, false);
            desc.setOutlineColor(color);
            shapes.put(referenceObject, desc);
        }
        redraw();
    }
 
    /**
     * Erases a specific shape from the screen.
     */
    public void erase(Object referenceObject){
        objects.remove(referenceObject);
         shapes.remove(referenceObject);
        redraw();
    }

    /**
     * Sets the current drawing color on the canvas.
     */
    public void setForegroundColor(String colorString){
        if(colorString == null) {
            graphic.setColor(Color.black);
            return;
        }
        String color = colorString.toLowerCase().trim();
        if(color.equals("red"))
            graphic.setColor(Color.red);
        else if(color.equals("black"))
            graphic.setColor(Color.black);
        else if(color.equals("blue"))
            graphic.setColor(Color.blue);
        else if(color.equals("yellow"))
            graphic.setColor(Color.yellow);
        else if(color.equals("green"))
            graphic.setColor(Color.green);
        else if(color.equals("magenta"))
            graphic.setColor(Color.magenta);
        else if(color.equals("white"))
            graphic.setColor(Color.white);
        else if(color.equals("orange"))
            graphic.setColor(Color.orange);
        else if(color.equals("gray") || color.equals("grey"))
            graphic.setColor(Color.gray);
        else if(color.equals("darkgray") || color.equals("dark_gray") || color.equals("dark gray"))
            graphic.setColor(Color.darkGray);
        else if(color.equals("lightgray") || color.equals("light_gray") || color.equals("light gray"))
            graphic.setColor(Color.lightGray);
        else if(color.equals("cyan"))
            graphic.setColor(Color.cyan);
        else if(color.equals("pink"))
            graphic.setColor(Color.pink);
        else {
            try {
                graphic.setColor(Color.decode(colorString));
            } catch (Exception e) {
                graphic.setColor(Color.black);
            }
        }
    }

    /**
     * Pauses execution for the given milliseconds.
     */
    public void wait(int milliseconds){
        try{
            Thread.sleep(milliseconds);
        } catch (Exception e){
        }
    }

    /*
     * Redraws all shapes currently registered on the canvas.
     */
    private void redraw(){
        erase();
        for(Iterator i=objects.iterator(); i.hasNext(); ) {
             shapes.get(i.next()).draw(graphic);
        }
        canvas.repaint();
    }
       
    /*
     * Clears the whole canvas background without repainting.
     */
    private void erase(){
        Color original = graphic.getColor();
        graphic.setColor(backgroundColour);
         Dimension size = canvas.getSize();
        graphic.fill(new java.awt.Rectangle(0, 0, size.width, size.height));
        graphic.setColor(original);
    }

    /*
     * Inner panel where the canvas image is rendered.
     */
    private final class CanvasPane extends JPanel{
        /**
         * Paints the canvas image onto the component.
         */
        public void paint(Graphics g){
            g.drawImage(canvasImage, 0, 0, null);
        }
    }
    
    /*
     * Inner class that stores the shape, color, and fill mode of each figure.
     */
    private class ShapeDescription{
        private Shape shape;
        private String colorString;
        private boolean filled;
        private String outlineColor;

        /**
         * Creates a filled shape description by default.
         */
        public ShapeDescription(Shape shape, String color){
            this(shape, color, true);
        }

        /**
         * Creates a shape description specifying whether it is filled.
         */
        public ShapeDescription(Shape shape, String color, boolean filled){
            this.shape = shape;
             this.colorString = color;
            this.filled = filled;
            this.outlineColor = null;
        }

        /**
         * Sets the outline color of the shape.
         */
        public void setOutlineColor(String outlineColor){
            this.outlineColor = outlineColor;
        }

        /**
         * Updates the stored geometric shape.
         */
        public void setShape(Shape shape){
            this.shape = shape;
        }

        /**
         * Draws the shape onto the given graphics context.
         */
        public void draw(Graphics2D graphic){
            if(filled){
                setForegroundColor(colorString);
                graphic.fill(shape);
            }
            if(outlineColor != null){
                setForegroundColor(outlineColor);
                graphic.draw(shape);
            } else if(!filled){
                setForegroundColor(colorString);
                graphic.draw(shape);
            } else {
                setForegroundColor(colorString);
                graphic.draw(shape);
            }
        }
    }

}
