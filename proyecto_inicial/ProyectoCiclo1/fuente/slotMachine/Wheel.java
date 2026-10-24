import java.util.ArrayList;

/**
 * Represents a wheel of the slot machine.
 * A wheel contains a collection of symbols and displays one symbol
 * at a time. The wheel can be positioned, spun, displayed, hidden,
 * and configured with different symbols.
 *
 * @author Juan Espitia
 * @author Cristian Salamanca
 * @version 1.0
 */
public class Wheel
{
    private ArrayList<Symbol> symbols;
    private int currentSymbol;

    private int diameter;
    private int xPosition;
    private int yPosition;
    private boolean visible;

    private Circle wheel;
    private Circle symbolCircle;
    private Triangle symbolTriangle;
    private Rectangle symbolRectangle;

    private int circleX;
    private int circleY;

    private int triangleX;
    private int triangleY;

    private int rectangleX;
    private int rectangleY;


    /**
     * Creates a new empty wheel with its default dimensions
     * and graphical components.
     */
    public Wheel()
    {
        symbols = new ArrayList<>();
        currentSymbol = 0;

        diameter = 45;
        xPosition = 0;
        yPosition = 0;
        visible = false;

        wheel = new Circle();
        wheel.changeSize(diameter);
        wheel.changeColor("gray");

        symbolCircle = new Circle();
        symbolTriangle = new Triangle();
        symbolRectangle = new Rectangle();

        symbolCircle.changeSize(20);
        symbolTriangle.changeSize(20, 20);
        symbolRectangle.changeSize(20, 20);

        circleX = 70;
        circleY = 15;

        triangleX = 50;
        triangleY = 40;

        rectangleX = 70;
        rectangleY = 15;
    }


    /**
     * Adds a symbol to the wheel at the specified position.
     * The position is adjusted to the valid range if necessary.
     *
     * @param pos the position where the symbol will be inserted
     * @param figure the symbol to add to the wheel
     * @return true after the symbol has been added
     */
    public boolean addSymbol(int pos, Symbol figure)
    {
        if(pos < 1) {
            pos = 1;
        }

        if(pos > symbols.size() + 1) {
            pos = symbols.size() + 1;
        }

        symbols.add(pos - 1, figure);

        refresh();

        return true;
    }


    /**
     * Removes a symbol from the wheel using its index.
     *
     * @param index the zero-based index of the symbol to remove
     * @return true if the symbol was removed, false if the index is invalid
     */
    public boolean delSymbol(int index)
    {
        if(index < 0 || index >= symbols.size()) {
            return false;
        }

        symbols.remove(index);

        if(symbols.isEmpty()) {
            currentSymbol = 0;
        }
        else if(currentSymbol >= symbols.size()) {
            currentSymbol = 0;
        }

        refresh();

        return true;
    }


    /**
     * Removes a specific symbol object from the wheel.
     *
     * @param figure the symbol to remove
     * @return true if the symbol was found and removed, false otherwise
     */
    public boolean delSymbol(Symbol figure)
    {
        for(int i = 0; i < symbols.size(); i++) {

            if(symbols.get(i) == figure) {
                return delSymbol(i);
            }
        }

        return false;
    }


    /**
     * Returns the number of symbols currently contained in the wheel.
     *
     * @return the number of symbols
     */
    public int getNumberSymbols()
    {
        return symbols.size();
    }


    /**
     * Returns the symbol at the specified index.
     *
     * @param index the zero-based index of the requested symbol
     * @return the symbol at the specified index, or null if the index is invalid
     */
    public Symbol getSymbol(int index)
    {
        if(index < 0 || index >= symbols.size()) {
            return null;
        }

        return symbols.get(index);
    }


    /**
     * Determines whether the wheel has reached its maximum capacity.
     *
     * @return true if the wheel contains 20 or more symbols,
     *         false otherwise
     */
    public boolean isFull()
    {
        return symbols.size() >= 20;
    }


    /**
     * Returns the symbol currently displayed by the wheel.
     *
     * @return the currently displayed symbol, or null if the wheel is empty
     */
    public Symbol getShownSymbol()
    {
        if(symbols.isEmpty()) {
            return null;
        }

        return symbols.get(currentSymbol);
    }


    /**
     * Spins the wheel by randomly selecting a new symbol
     * from the symbols currently contained in it.
     */
    public void spin()
    {
        if(symbols.isEmpty()) {
            return;
        }

        int positions =
            (int)(Math.random() * symbols.size());

        currentSymbol =
            (currentSymbol + positions) % symbols.size();

        refresh();
    }


    /**
     * Changes the position of the entire wheel and its graphical symbols.
     * All graphical components are moved by the same displacement.
     *
     * @param x the new horizontal position of the wheel
     * @param y the new vertical position of the wheel
     */
    public void setPosition(int x, int y)
    {
        int differenceX = x - xPosition;
        int differenceY = y - yPosition;

        wheel.moveHorizontal(differenceX);
        wheel.moveVertical(differenceY);

        symbolCircle.moveHorizontal(differenceX);
        symbolCircle.moveVertical(differenceY);

        symbolTriangle.moveHorizontal(differenceX);
        symbolTriangle.moveVertical(differenceY);

        symbolRectangle.moveHorizontal(differenceX);
        symbolRectangle.moveVertical(differenceY);

        xPosition = x;
        yPosition = y;

        circleX += differenceX;
        circleY += differenceY;

        triangleX += differenceX;
        triangleY += differenceY;

        rectangleX += differenceX;
        rectangleY += differenceY;

        refresh();
    }


    /**
     * Makes the wheel visible and displays its current symbol.
     */
    public void makeVisible()
    {
        visible = true;

        wheel.makeVisible();

        refresh();
    }


    /**
     * Makes the wheel invisible and hides its current symbol.
     */
    public void makeInvisible()
    {
        visible = false;

        wheel.makeInvisible();

        hideSymbol();
    }


    /*
     * Updates the graphical representation of the wheel
     * according to its current state.
     */
    private void refresh()
    {
        if(visible) {
            showSymbol(getShownSymbol());
        }
    }


    /*
     * Displays the specified symbol using the appropriate graphical
     * shape and color.
     *
     * @param symbol the symbol to display
     */
    private void showSymbol(Symbol symbol)
    {
        hideSymbol();

        if(symbol == null) {
            return;
        }

        int size = 20;

        int x = xPosition + (diameter - size) / 2;
        int y = yPosition + (diameter - size) / 2;

        int offsetX = 0;
        int offsetY = 0;

        if(symbol.getType() == TypeSymbol.CIRCLE) {

            offsetX = 75;
            offsetY = 16;

            int finalX = x + offsetX;
            int finalY = y + offsetY;

            symbolCircle.moveHorizontal(finalX - circleX);
            symbolCircle.moveVertical(finalY - circleY);

            circleX = finalX;
            circleY = finalY;

            symbolCircle.changeColor(symbol.getColor());
            symbolCircle.makeVisible();
        }

        else if(symbol.getType() == TypeSymbol.TRIANGLE) {

            offsetX = -65;
            offsetY = 40;

            int finalX = x + offsetX;
            int finalY = y + offsetY;

            symbolTriangle.moveHorizontal(finalX - triangleX);
            symbolTriangle.moveVertical(finalY - triangleY);

            triangleX = finalX;
            triangleY = finalY;

            symbolTriangle.changeColor(symbol.getColor());
            symbolTriangle.makeVisible();
        }

        else {

            offsetX = 15;
            offsetY = 15;

            int finalX = x + offsetX;
            int finalY = y + offsetY;

            symbolRectangle.moveHorizontal(finalX - rectangleX);
            symbolRectangle.moveVertical(finalY - rectangleY);

            rectangleX = finalX;
            rectangleY = finalY;

            symbolRectangle.changeColor(symbol.getColor());
            symbolRectangle.makeVisible();
        }
    }


    /*
     * Hides all possible graphical symbols associated with the wheel.
     */
    private void hideSymbol()
    {
        symbolCircle.makeInvisible();
        symbolTriangle.makeInvisible();
        symbolRectangle.makeInvisible();
    }


    /**
     * Places a specific symbol in the currently visible position
     * if the symbol belongs to this wheel.
     *
     * @param symbol the symbol to display
     */
    public void placeSymbol(Symbol symbol)
    {
        for(int i = 0; i < symbols.size(); i++) {

            if(symbols.get(i) == symbol) {

                currentSymbol = i;

                refresh();

                return;
            }
        }
    }
}
