import java.util.ArrayList;

/**
 * Represents a wheel of the machine that stores symbols and displays one at a time.
 *
 * @author Juan Espitia
 * @author Cristian Salamanca
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

    /**
     * Creates an empty wheel with its default initial values.
     */
    public Wheel() {
        symbols = new ArrayList<>();
         currentSymbol = 0;

        diameter = 45;
        xPosition = 20;
        yPosition = 15;
         visible = false;

        wheel = new Circle();
        wheel.changeSize(diameter);
        wheel.changeColor("gray" );

        lock = false;
    }

    /**
     * Indicates if the wheel allows swapping position with another.
     */
    public boolean swapeable(){
        return true;
    }
    
    /**
     * Indicates if the wheel can be deleted.
     */
    public boolean deleteable(){
         return true;
    }
    
    /**
     * Returns the wheel type.
     */
    public String type() {
        return "normal";
    }

    /**
     * Gets the string with the wheel type.
     */
    public String getType() {
        return type();
    }
    
    /**
     * Returns the graphical circle of the wheel.
     */
    public Circle getWheel(){
         return wheel;
    }
    
    /**
     * Adds a symbol at the given position inside the wheel.
     */
    public boolean addSymbol(int pos, Symbol figure) {
        if (figure == null) {
            return false;
        }

        if(pos < 1) pos = 1;
        if (pos > symbols.size() + 1) pos = symbols.size() + 1;

         symbols.add(pos - 1, figure);
        refresh();
        return true;
    }

    /**
     * Updates the lock state of the wheel.
     */
    public void setLock(boolean status) {
        this.lock = status;
    }

    /**
     * Returns whether the wheel is currently locked.
     */
    public boolean getLock() {
         return lock;
    }

    /**
     * Removes the symbol at the given index.
     */
    public boolean delSymbol(int index) {
        if (index < 0 || index >= symbols.size()) {
            return false;
        }

        symbols.get(index).makeInvisible();
         symbols.remove(index);

        if(symbols.isEmpty() || currentSymbol >= symbols.size()) {
            currentSymbol = 0;
        }

        refresh();
        return true;
    }

    /**
     * Searches and removes a symbol matching in type and color.
     */
    public boolean delSymbol(Symbol figure) {
        if (figure == null) {
            return false;
        }
        for(int i = 0; i < symbols.size(); i++) {
            Symbol current = symbols.get(i);
            if (current.getType().equalsIgnoreCase(figure.getType()) && current.getColor().equalsIgnoreCase(figure.getColor())) {
                 return delSymbol(i);
            }
        }
        return false;
    }

    /**
     * Returns the number of symbols in the wheel.
     */
    public int getNumberSymbols() {
        return symbols.size();
    }

    /**
     * Gets the symbol located at the requested index.
     */
    public Symbol getSymbol(int index) {
        if(index < 0 || index >= symbols.size()) {
             return null;
        }
        return symbols.get(index);
    }

    /**
     * Returns the index of the current symbol.
     */
    public int getCurrentSymbol() {
        return currentSymbol;
    }

    /**
     * Directly sets the index of the current symbol and refreshes.
     */
    public void setCurrentSymbol(int value) {
        if (value < 0 || value >= symbols.size()) {
            return;
        }
         this.currentSymbol = value;
        refresh();
    }

    /**
     * Indicates if the wheel reached the maximum of 20 symbols.
     */
    public boolean isFull() {
        return symbols.size() >= 20;
    }

    /**
     * Returns the symbol currently being displayed.
     */
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
     * Spins the wheel by a given number of steps.
     */
    public void spin(int steps) {
        if(lock || symbols.isEmpty()) {
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
     * Spins the wheel until finding the symbol of the given color.
     */
    public void spin(String color) {
        if (lock) {
            return;
        }
        if (symbols.isEmpty()) {
             return;
        }
        if(color == null || color.trim().isEmpty()) {
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
    
    /*
     * Applies size decrement to the current symbol.
     */
    protected void decrement(){
        if (!symbols.isEmpty()) {
            symbols.get(getCurrentSymbol()).decrement();
        }
    }
    
    /*
     * Verifies visibility of the current symbol when it is Shy.
     */
    private void shy(){
        if (!symbols.isEmpty()) {
             symbols.get(getCurrentSymbol()).verifyCanBeVisible();
        }
    }

    /**
     * Changes the wheel position on the canvas.
     */
    public void setPosition(int x, int y) {
        this.xPosition = x;
        this.yPosition = y;
         wheel.setPosition(x, y);
        refresh();
    }

    /**
     * Makes the wheel and its current symbol visible.
     */
    public void makeVisible() {
        visible = true;
        wheel.makeVisible();
         refresh();
    }

    /**
     * Hides the wheel and all its symbols.
     */
    public void makeInvisible() {
        visible = false;
        wheel.makeInvisible();
        hideSymbol();
    }

    /*
     * Updates on screen the shown symbol if the wheel is visible.
     */
    protected void refresh() {
        if (visible) {
             showSymbol(getShownSymbol());
        }
    }

    /*
     * Shows centered the figure of the given symbol.
     */
    private void showSymbol(Symbol symbol) {
        hideSymbol();

        if (symbol == null || !symbol.getCanBeVisible()) {
            return;
        }

        int size = symbol.getSize();
         int targetX = xPosition + (diameter - size) / 2;
        int targetY = yPosition + (diameter - size) / 2;

        symbol.makeVisible(targetX, targetY );
    }

    /*
     * Makes all symbols of the wheel invisible.
     */
    private void hideSymbol() {
        for(int i = 0; i < symbols.size(); i++) {
            symbols.get(i).makeInvisible();
        }
    }

    /**
     * Places as current the symbol matching the given one.
     */
    public void placeSymbol(Symbol symbol) {
        if (symbol == null) {
            return;
        }
        for (int i = 0; i < symbols.size(); i++) {
            Symbol candidate = symbols.get(i);
            if (candidate.getType().equalsIgnoreCase(symbol.getType()) && candidate.getColor().equalsIgnoreCase(symbol.getColor())) {
                 currentSymbol = i;
                shy();
                refresh();
                return;
            }
        }
    }
}
