
import java.util.ArrayList;

/**
 * Represents a slot machine composed of wheels, symbols, and a body.
 * The machine supports adding and removing wheels and symbols,
 * positioning symbols, spinning wheels, checking jackpots, and
 * controlling the visibility of the machine.
 * @author juan espitia y cristian salamanca
 */
public class SlotMachine {

    private ArrayList<Wheel> wheels;
    private ArrayList<Symbol> gallery;
    private ArrayList<BodyMachine> body;

    /**
     * Creates a new slot machine with no wheels or symbols.
     * A main body is created automatically.
     */
    public SlotMachine() {
        wheels = new ArrayList<>();
        gallery = new ArrayList<>();
        body = new ArrayList<>();

        BodyMachine principal_body = new BodyMachine();
        body.add(principal_body);
    }

    /**
     * Adds a new wheel at the specified position.
     * The position is adjusted to the valid range if necessary.
     * A maximum of five wheels is allowed.
     *
     * @param pos the position where the new wheel is inserted
     */
    public void addWheel(int pos) {

        if(wheels.size() >= 5) {
            return;
        }

        if(pos < 1) {
            pos = 1;
        }

        if(pos > wheels.size() + 1) {
            pos = wheels.size() + 1;
        }

        Wheel wheel = new Wheel();
        wheels.add(pos - 1, wheel);

        int diameter = 45;
        int spacing = 5;

        int bodyX = -20;
        int bodyWidth = 270;

        int totalWidth = wheels.size() * diameter
                       + (wheels.size() - 1) * spacing;

        int margin = (bodyWidth - totalWidth) / 2;

        for(int i = 0; i < wheels.size(); i++) {

            int x = bodyX + margin + i * (diameter + spacing);
            int y = 65 + (130 - diameter) / 2;

            wheels.get(i).setPosition(x, y);
        }

        for(int i = 0; i < gallery.size(); i++) {
            wheel.addSymbol(i + 1, gallery.get(i));
        }

        isJackpot();
        makeVisible();
    }

    /**
     * Removes the wheel at the specified position.
     * The position is adjusted to the valid range if necessary.
     *
     * @param pos the position of the wheel to remove
     */
    public void delWheel(int pos) {

        if(wheels.size() == 0) {
            return;
        }

        if(pos < 1) {
            pos = 1;
        }

        if(pos > wheels.size()) {
            pos = wheels.size();
        }

        wheels.get(pos - 1).makeInvisible();
        wheels.remove(pos - 1);

        int diameter = 45;
        int spacing = 5;

        int bodyX = -20;
        int bodyWidth = 270;

        int totalWidth = wheels.size() * diameter
                       + (wheels.size() - 1) * spacing;

        int margin = (bodyWidth - totalWidth) / 2;

        for(int i = 0; i < wheels.size(); i++) {

            int x = bodyX + margin + i * (diameter + spacing);
            int y = 65 + (130 - diameter) / 2;

            wheels.get(i).setPosition(x, y);
        }

        isJackpot();
        makeVisible();
    }

    /**
     * Adds a new symbol to the machine at the specified position.
     * Each symbol must have a unique color.
     * A maximum of seven different symbols is allowed.
     *
     * @param pos the position where the symbol is inserted
     * @param color the color assigned to the symbol
     */
    public void addSymbol(int pos, String color) {

        if(gallery.size() >= 7) {
            return;
        }

        if(pos < 1) {
            pos = 1;
        }

        if(pos > gallery.size() + 1) {
            pos = gallery.size() + 1;
        }

        int index = pos - 1;

        for(int i = 0; i < gallery.size(); i++) {

            if(gallery.get(i).getColor().equals(color)) {
                return;
            }
        }

        TypeSymbol type =
            TypeSymbol.values()[gallery.size()
            % TypeSymbol.values().length];

        Symbol newSymbol = new Symbol(type, color);

        for(int i = 0; i < wheels.size(); i++) {
            wheels.get(i).addSymbol(index + 1, newSymbol);
        }

        gallery.add(index, newSymbol);

        isJackpot();
        makeVisible();
    }

    /**
     * Removes a symbol identified by its color.
     *
     * @param color the color of the symbol to remove
     */
    public void delSymbol(String color) {

        for(int i = 0; i < gallery.size(); i++) {

            if(gallery.get(i).getColor().equals(color)) {

                Symbol symbol = gallery.get(i);

                for(int j = 0; j < wheels.size(); j++) {
                    wheels.get(j).delSymbol(symbol);
                }

                gallery.remove(i);

                isJackpot();
                makeVisible();

                return;
            }
        }
    }

    /**
     * Places a symbol on a specific wheel.
     * The wheel position is adjusted to the valid range.
     *
     * @param wheel the wheel where the symbol is placed
     * @param symbol the color of the symbol to place
     */
    public void placeSymbol(int wheel, String symbol) {

        if(wheels.size() == 0) {
            return;
        }

        if(wheel < 1) {
            wheel = 1;
        }

        if(wheel > wheels.size()) {
            wheel = wheels.size();
        }

        Symbol symbolTemp = null;

        for(int i = 0; i < gallery.size(); i++) {

            if(gallery.get(i).getColor().equals(symbol)) {
                symbolTemp = gallery.get(i);
                break;
            }
        }

        if(symbolTemp == null) {
            return;
        }

        wheels.get(wheel - 1).placeSymbol(symbolTemp);

        isJackpot();
        makeVisible();
    }

    /**
     * Spins one specific wheel.
     * The wheel position is adjusted to the valid range.
     *
     * @param wheel the wheel to spin
     */
    public void spin(int wheel) {

        if(wheels.size() == 0) {
            return;
        }

        if(wheel < 1) {
            wheel = 1;
        }

        if(wheel > wheels.size()) {
            wheel = wheels.size();
        }

        wheels.get(wheel - 1).spin();

        isJackpot();
        makeVisible();
    }

    /**
     * Spins all wheels in the machine.
     */
    public void spin() {

        for(int i = 0; i < wheels.size(); i++) {
            wheels.get(i).spin();
        }

        isJackpot();
        makeVisible();
    }

    /**
     * Returns the colors of all symbols currently available
     * in the machine.
     *
     * @return an array containing the colors of all symbols
     */
    public String[] symbols() {

        String[] result = new String[gallery.size()];

        for(int i = 0; i < gallery.size(); i++) {
            result[i] = gallery.get(i).getColor();
        }

        return result;
    }

    /**
     * Returns the number of different symbols currently available.
     *
     * @return the number of different symbols
     */
    public int distinctSymbols() {

        return gallery.size();
    }

    /**
     * Returns the current configuration of the machine.
     * Each element contains the color displayed by the corresponding
     * wheel, or null if no symbol is currently displayed.
     *
     * @return an array containing the current wheel configuration
     */
    public String[] configuration() {

        String[] result = new String[wheels.size()];

        for(int i = 0; i < wheels.size(); i++) {

            Symbol symbol = wheels.get(i).getShownSymbol();

            if(symbol != null) {
                result[i] = symbol.getColor();
            }
            else {
                result[i] = null;
            }
        }

        return result;
    }

    /**
     * Checks whether all wheels currently display the same symbol.
     * If they do, the machine body changes color to indicate a jackpot.
     *
     * @return true if all wheels display the same non-null symbol,
     *         false otherwise
     */
    public boolean isJackpot() {

        if(wheels.size() == 0) {
            body.get(0).normalColor();
            return false;
        }

        Symbol first = wheels.get(0).getShownSymbol();

        if(first == null) {
            body.get(0).normalColor();
            return false;
        }

        for(int i = 1; i < wheels.size(); i++) {

            Symbol current = wheels.get(i).getShownSymbol();

            if(current == null) {
                body.get(0).normalColor();
                return false;
            }

            if(!current.getColor().equals(first.getColor())) {
                body.get(0).normalColor();
                return false;
            }
        }

        body.get(0).changeColor();
        return true;
    }

    /**
     * Makes the body and all wheels visible.
     */
    public void makeVisible() {

        body.get(0).makeVisible();

        for(int i = 0; i < wheels.size(); i++) {
            wheels.get(i).makeVisible();
        }
    }

    /**
     * Makes the body and all wheels invisible and hides the canvas.
     */
    public void makeInvisible() {

        body.get(0).makeInvisible();

        for(int i = 0; i < wheels.size(); i++) {
            wheels.get(i).makeInvisible();
        }

        Canvas.getCanvas().setVisible(false);
    }

    /**
     * Closes the slot machine by making all its components invisible.
     */
    public void exit() {

        makeInvisible();
        System.exit(0);
    }
}

