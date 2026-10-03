import java.util.ArrayList;

/**
 * Represents a wheel of the slot machine.
 * A wheel contains a collection of symbols and displays one symbol at a time.
 *
 * @author Juan Espitia
 * @author Cristian Salamanca
 * @version 1.1
 */
public class Wheel {
    protected ArrayList<Symbol> symbols;
    private int currentSymbol;

    private int diameter;
    private int xPosition;
    private int yPosition;
    private boolean visible;

    protected Circle wheel;
    private boolean lock;
    protected String type;

    public Wheel() {
        symbols = new ArrayList<>();
        currentSymbol = 0;

        diameter = 45;
        // The base shapes start at (20, 15); use that as the wheel's
        // initial logical origin so setPosition(x, y) means the wheel's
        // actual top-left corner on the canvas.
        xPosition = 20;
        yPosition = 15;
        visible = false;

        wheel = new Circle();
        wheel.changeSize(diameter);
        wheel.changeColor("gray");

        lock = false;
        type = "normal";
    }

    public boolean swapeable(){
        return true;
    }
    
    public boolean deleteable(){
        return true;
    }
    
    public String type() {
        return "normal";
    }

    public String getType() {
        return type();
    }
    
    public Circle getWheel(){
        return wheel;
    }
    
    
    public boolean addSymbol(int pos, Symbol figure) {
        if (figure == null) {
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
        if (figure == null) {
            return false;
        }
        for (int i = 0; i < symbols.size(); i++) {
            Symbol current = symbols.get(i);
            if (current.getType().equalsIgnoreCase(figure.getType())
                    && current.getColor().equalsIgnoreCase(figure.getColor())) {
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
        decrement();
        shy();
        refresh();
    }

    /**
     * Spins the wheel by a specific number of steps.
     */
    public void spin(int steps) {
        if (lock || symbols.isEmpty()) {
            return;
        }
        
        int size = symbols.size();
        int direction = steps >= 0 ? 1 : -1;
        int totalSteps = Math.abs(steps);

        if (visible) {
            for (int i = 0; i < totalSteps; i++) {
                currentSymbol = ((currentSymbol + direction) % size + size) % size;
                refresh();
                Canvas.getCanvas().wait(80);
            }
        } else {
            currentSymbol = ((currentSymbol + steps) % size + size) % size;
        }

        decrement();
        shy();
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
            return;
        }
        if (color == null || color.trim().isEmpty()) {
            return;
        }

        int startPosition = currentSymbol;
        while (!symbols.get(currentSymbol).getColor().equalsIgnoreCase(color)) {
            currentSymbol = (currentSymbol + 1) % symbols.size();
            if (currentSymbol == startPosition) {
                return;
            }
        }
        decrement();
        shy();
        refresh();
    }
    
    private void decrement(){
        for(Symbol symbol : symbols){
            symbol.decrement();
        }
    }
    
    private void shy(){
        symbols.get(getCurrentSymbol()).verifyCanBeVisible();
    }

    public void setPosition(int x, int y) {
        this.xPosition = x;
        this.yPosition = y;
        wheel.setPosition(x, y);
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

        if (symbol == null || !symbol.getCanBeVisible()) {
            return;
        }

        int size = symbol.getSize();
        int targetX = xPosition + (diameter - size) / 2;
        int targetY = yPosition + (diameter - size) / 2;

        symbol.makeVisible(targetX, targetY);
        }
    

    private void hideSymbol() {
        Symbol s = getShownSymbol();
        if (s != null) {
            s.makeInvisible();
        }
    }

    public void placeSymbol(Symbol symbol) {
        if (symbol == null) {
            return;
        }
        for (int i = 0; i < symbols.size(); i++) {
            Symbol candidate = symbols.get(i);
            if (candidate.getType().equalsIgnoreCase(symbol.getType())
                    && candidate.getColor().equalsIgnoreCase(symbol.getColor())) {
                currentSymbol = i;
                refresh();
                return;
            }
        }
    }
}
