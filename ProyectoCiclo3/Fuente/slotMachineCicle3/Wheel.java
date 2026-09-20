import java.util.ArrayList;
import javax.swing.JOptionPane;

/**
 * Represents a wheel of the slot machine.
 * A wheel contains a collection of symbols and displays one symbol at a time.
 *
 * @author Juan Espitia
 * @author Cristian Salamanca
 * @version 1.1
 */
public class Wheel {
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

    private boolean lock;

    public Wheel() {
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

        lock = false;
    }

    public boolean addSymbol(int pos, Symbol figure) {
        if (figure == null) {
            JOptionPane.showMessageDialog(null, "No se puede agregar un símbolo vacío.", "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }

        if (pos < 1) pos = 1;
        if (pos > symbols.size() + 1) pos = symbols.size() + 1;

        symbols.add(pos - 1, figure);
        refresh();
        return true;
    }

    public void setLock(boolean status) {
        this.lock = status;
    }

    public boolean getLock() {
        return lock;
    }

    public boolean delSymbol(int index) {
        if (index < 0 || index >= symbols.size()) {
            return false;
        }

        symbols.remove(index);

        if (symbols.isEmpty() || currentSymbol >= symbols.size()) {
            currentSymbol = 0;
        }

        refresh();
        return true;
    }

    public boolean delSymbol(Symbol figure) {
        for (int i = 0; i < symbols.size(); i++) {
            if (symbols.get(i) == figure) {
                return delSymbol(i);
            }
        }
        return false;
    }

    public int getNumberSymbols() {
        return symbols.size();
    }

    public Symbol getSymbol(int index) {
        if (index < 0 || index >= symbols.size()) {
            return null;
        }
        return symbols.get(index);
    }

    public int getCurrentSymbol() {
        return currentSymbol;
    }

    public void setCurrentSymbol(int value) {
        if (value < 0 || value >= symbols.size()) {
            JOptionPane.showMessageDialog(null, "La posición indicada no es válida.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        this.currentSymbol = value;
        refresh();
    }

    public boolean isFull() {
        return symbols.size() >= 20;
    }

    public Symbol getShownSymbol() {
        if (symbols.isEmpty()) {
            return null;
        }
        return symbols.get(currentSymbol);
    }

    /**
     * Spins the wheel to a random position if not locked.
     */
    public void spin() {
        if (lock || symbols.isEmpty()) {
            return;
        }

        int positions = (int) (Math.random() * symbols.size());
        currentSymbol = (currentSymbol + positions) % symbols.size();
        refresh();
    }

    /**
     * Spins the wheel by a specific number of steps.
     */
    public void spin(int steps) {
        if (lock) {
            return;
        }
        if (symbols.isEmpty()) {
            JOptionPane.showMessageDialog(null, "No se puede girar la rueda porque no tiene símbolos.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        int size = symbols.size();

        currentSymbol = ((currentSymbol + steps) % size + size) % size;
        refresh();
    }

    /**
     * Spins the wheel until a symbol of the given color is displayed.
     */
    public void spin(String color) {
        if (lock) {
            return;
        }
        if (symbols.isEmpty()) {
            JOptionPane.showMessageDialog(null, "No se puede buscar un color porque la rueda está vacía.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        if (color == null || color.trim().isEmpty()) {
            JOptionPane.showMessageDialog(null, "El color no puede estar vacío.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        int startPosition = currentSymbol;
        while (!symbols.get(currentSymbol).getColor().equalsIgnoreCase(color)) {
            currentSymbol = (currentSymbol + 1) % symbols.size();
            if (currentSymbol == startPosition) {
                JOptionPane.showMessageDialog(null, "No existe ningún símbolo con el color: " + color, "Color no encontrado", JOptionPane.WARNING_MESSAGE);
                return;
            }
        }
        refresh();
    }

    public void setPosition(int x, int y) {
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

    public void makeVisible() {
        visible = true;
        wheel.makeVisible();
        refresh();
    }

    public void makeInvisible() {
        visible = false;
        wheel.makeInvisible();
        hideSymbol();
    }

    private void refresh() {
        if (visible) {
            showSymbol(getShownSymbol());
        }
    }

    private void showSymbol(Symbol symbol) {
        hideSymbol();

        if (symbol == null) {
            return;
        }

        int size = 20;
        int x = xPosition + (diameter - size) / 2;
        int y = yPosition + (diameter - size) / 2;

        if (symbol.getType() == TypeSymbol.CIRCLE) {
            int finalX = x + 75;
            int finalY = y + 16;

            symbolCircle.moveHorizontal(finalX - circleX);
            symbolCircle.moveVertical(finalY - circleY);

            circleX = finalX;
            circleY = finalY;

            symbolCircle.changeColor(symbol.getColor());
            symbolCircle.makeVisible();
        } else if (symbol.getType() == TypeSymbol.TRIANGLE) {
            int finalX = x - 65;
            int finalY = y + 40;

            symbolTriangle.moveHorizontal(finalX - triangleX);
            symbolTriangle.moveVertical(finalY - triangleY);

            triangleX = finalX;
            triangleY = finalY;

            symbolTriangle.changeColor(symbol.getColor());
            symbolTriangle.makeVisible();
        } else {
            int finalX = x + 15;
            int finalY = y + 15;

            symbolRectangle.moveHorizontal(finalX - rectangleX);
            symbolRectangle.moveVertical(finalY - rectangleY);

            rectangleX = finalX;
            rectangleY = finalY;

            symbolRectangle.changeColor(symbol.getColor());
            symbolRectangle.makeVisible();
        }
    }

    private void hideSymbol() {
        symbolCircle.makeInvisible();
        symbolTriangle.makeInvisible();
        symbolRectangle.makeInvisible();
    }

    public void placeSymbol(Symbol symbol) {
        for (int i = 0; i < symbols.size(); i++) {
            if (symbols.get(i) == symbol) {
                currentSymbol = i;
                refresh();
                return;
            }
        }
    }
}
