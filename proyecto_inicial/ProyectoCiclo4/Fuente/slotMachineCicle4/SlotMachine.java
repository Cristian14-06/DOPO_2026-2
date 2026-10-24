import java.util.ArrayList;
import java.util.HashMap;

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
     * Creates a new slot machine with n wheels and n symbols, initialized randomly.
     *
     * @param n the number of wheels and symbols (between 1 and 5)
     */
     public SlotMachine(int n){
        wheels = new ArrayList<>();
        gallery = new ArrayList<>();
        body = new ArrayList<>();
        BodyMachine principal_body = new BodyMachine();
        body.add(principal_body); 

        if(n < 1 || n > 5){
            SlotMachineDialog.showError(body.get(0).isVisible(), "Numero de ruedas y simbolos invalido");
            return;
        }

        String[] colors = {"red", "blue", "yellow", "green", "magenta", "orange", "cyan"};

        for(int i = 0; i < n; i++){
            addWheel(i+1);
            addSymbol(i+1, colors[i % colors.length]);
        }
        spin();
    }

    /**
     * Adds a new wheel at the specified position.
     * The position is adjusted to the valid range if necessary.
     * A maximum of five wheels is allowed.
     *
     * @param pos the position where the new wheel is inserted
     */
    public void addWheel(int pos) {
        addWheel("normal", pos);
    }
    
    /**
     * Adds a new wheel of a specific type at the specified position.
     *
     * @param type the type of wheel ("normal", "lefty", "rebel", "inverted")
     * @param pos the position where the new wheel is inserted
     */
    public void addWheel(String type, int pos) {
        int max_wheel = 5;
        
        if (wheels.size() >= max_wheel) { 
            SlotMachineDialog.showError(body.get(0).isVisible(), "No se pueden agregar más de " + max_wheel + " ruedas.");
            return; 
        }

        if (pos < 1) {
            pos = 1;
        }

        if (pos > wheels.size() + 1) {
            pos = wheels.size() + 1;
        }
        
        Wheel wheel = new Wheel();
        if (type.equalsIgnoreCase("lefty")) {
            wheel = new Lefty();
        } else if (type.equalsIgnoreCase("rebel")) {
            wheel = new Rebel();
        } else if (type.equalsIgnoreCase("inverted")) {
            wheel = new Inverted();
        }
        wheels.add(pos - 1, wheel);

        for (int i = 0; i < wheels.size(); i++) {
            if (wheels.get(i).type().equalsIgnoreCase("lefty")) {
                ((Lefty) wheels.get(i)).setLeft(i > 0 ? wheels.get(i - 1) : null);
            }
        }
        
        int body_x = 20;
        int body_width = 250;
        int diameter = 45;
        int spacing = 5;

        int totalWidth = wheels.size() * diameter
                       + (wheels.size() - 1) * spacing;
        int margin = (body_width - totalWidth) / 2;

        for (int i = 0; i < wheels.size(); i++) {
            int x = body_x + margin + i * (diameter + spacing);
            int y = 80 + (130 - diameter) / 2;
            wheels.get(i).setPosition(x, y);
        }

        for (int i = 0; i < gallery.size(); i++) {
            wheel.addSymbol(i + 1, gallery.get(i).copy());
        }

        isJackpot();
        if (body.get(0).isVisible()) {
            body.get(0).makeVisible();
            for (int i = 0; i < wheels.size(); i++) {
                wheels.get(i).makeVisible();
            }
        }  
    }

    /**
     * Removes the wheel at the specified position.
     * The position is adjusted to the valid range if necessary.
     *
     * @param pos the position of the wheel to remove
     */
    public void delWheel(int pos) {

        if (wheels.isEmpty()) { 
            SlotMachineDialog.showError(body.get(0).isVisible(), "No hay ruedas para eliminar.");
            return; 
        }

        if(pos < 1) {
            pos = 1;
        }

        if(pos > wheels.size()) {
            pos = wheels.size();
        }
        
        if(!wheels.get(pos - 1).deleteable()){
            SlotMachineDialog.showError(body.get(0).isVisible(), "La rueda a eliminar es Rebel, no es posible eliminarla.");
            return; 
        }

        wheels.get(pos - 1).makeInvisible();
        wheels.remove(pos - 1);

        for (int i = 0; i < wheels.size(); i++) {
            if (wheels.get(i).type().equalsIgnoreCase("lefty")) {
                ((Lefty) wheels.get(i)).setLeft(i > 0 ? wheels.get(i - 1) : null);
            }
        }
        
                
        int body_x = 20;
        int body_width = 250;
        int diameter = 45;
        int spacing = 5;

        int totalWidth = wheels.size() * diameter
                       + (wheels.size() - 1) * spacing;
        int margin = (body_width - totalWidth) / 2;

        for (int i = 0; i < wheels.size(); i++) {
            int x = body_x + margin + i * (diameter + spacing);
            int y = 80 + (130 - diameter) / 2;
            wheels.get(i).setPosition(x, y);
        }

        isJackpot();
        if (body.get(0).isVisible()) {
            body.get(0).makeVisible();
            for (int i = 0; i < wheels.size(); i++) {
                wheels.get(i).makeVisible();
            }
        }
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
        addSymbol("normal", pos, color);
    }
    
    /**
     * Adds a new symbol of a specific type to the machine at the specified position.
     * Each symbol must have a unique color.
     * A maximum of seven different symbols is allowed.
     *
     * @param type the type of symbol ("normal", "ephemeral", "shy")
     * @param pos the position where the symbol is inserted
     * @param color the color assigned to the symbol
     */
    public void addSymbol(String type, int pos, String color) {
        int max_symbol = 7;

        if (gallery.size() >= max_symbol) { 
            SlotMachineDialog.showError(body.get(0).isVisible(), "No se pueden agregar más de " + max_symbol + " símbolos.");
            return; 
        }

        if (pos < 1) {
            pos = 1;
        }

        if (pos > gallery.size() + 1) {
            pos = gallery.size() + 1;
        }

        int index = pos - 1;

        for (int i = 0; i < gallery.size(); i++) {
            if (gallery.get(i).getColor().equalsIgnoreCase(color)) {
                return;
            }
        }

        int shapeIndex = gallery.size() % 3;
        
        Figure fig;
        
        if (shapeIndex == 0) {
            fig = new Circle(20, 0, 0, color);
        } else if (shapeIndex == 1) {
            fig = new Triangle(20, 20, 0, 0, color);
        } else {
            fig = new Rectangle(20, 20, 0, 0, color);
        }

        Symbol newSymbol;
        if (type.equalsIgnoreCase("ephemeral")) {
            newSymbol = new Ephemeral(fig, color);
        } else if (type.equalsIgnoreCase("shy")) {
            newSymbol = new Shy(fig, color);
        } else {
            newSymbol = new Symbol(fig, color);
        }

        for (int i = 0; i < wheels.size(); i++) {
            wheels.get(i).addSymbol(index + 1, newSymbol.copy());
        }

        gallery.add(index, newSymbol);

        isJackpot();
        if (body.get(0).isVisible()) {
            body.get(0).makeVisible();
            for (int i = 0; i < wheels.size(); i++) {
                wheels.get(i).makeVisible();
            }
        }
    }

    /**
     * Removes a symbol identified by its color.
     *
     * @param color the color of the symbol to remove
     */
    public void delSymbol(String color) {

        for(int i = 0; i < gallery.size(); i++) {
            if(gallery.get(i).getColor().equals(color)) {

                for(int j = 0; j < wheels.size(); j++) {
                    wheels.get(j).delSymbol(i);
                }
                gallery.remove(i);
                isJackpot();
                if (body.get(0).isVisible()) {
                    makeVisible();
                }

                return;
            }
        }
        
        SlotMachineDialog.showWarning(body.get(0).isVisible(), "No existe un símbolo con el color \"" + color + "\".", "Símbolo no encontrado");
    }

    /**
     * Places a symbol on a specific wheel.
     * The wheel position is adjusted to the valid range.
     *
     * @param wheel the wheel where the symbol is placed
     * @param symbol the color of the symbol to place
     */
    public void placeSymbol(int wheel, String symbol) {

        if(wheels.isEmpty()){
            SlotMachineDialog.showWarning(body.get(0).isVisible(), "No hay ruedas en la máquina.", "Máquina vacía");
            return;
        }

        if(wheel < 1) {
            wheel = 1;
        }else if (wheel >wheels.size()){
            wheel = wheels.size();
        }

        Symbol symbolTemp = null;
        for(int i = 0; i < gallery.size(); i++) {

            if(gallery.get(i).getColor().equals(symbol)) {
                symbolTemp = gallery.get(i);
                break;
            }
        }

        if(symbolTemp == null){
            SlotMachineDialog.showWarning(body.get(0).isVisible(), "No existe un símbolo con el color \"" + symbol + "\".", "Símbolo no encontrado");
            return;
        }

        wheels.get(wheel - 1).placeSymbol(symbolTemp);

        isJackpot();
        if (body.get(0).isVisible()) {
            body.get(0).makeVisible();
            for (int i = 0; i < wheels.size(); i++) {
                wheels.get(i).makeVisible();
            }
        }
    }

    /**
     * Spins one specific wheel.
     * The wheel position is adjusted to the valid range.
     *
     * @param wheel the wheel to spin
     */
    public void spin(int wheel) {

        if(wheels.isEmpty()){
            SlotMachineDialog.showWarning(body.get(0).isVisible(), "No hay ruedas para girar.", "Operación no permitida");
            return;         
        }

        if(wheel < 1) {
            wheel = 1;
        }else if (wheel >wheels.size()){
            wheel = wheels.size();
        }
        
        if(wheels.get(wheel-1).getLock()){
            return;
        }

        wheels.get(wheel - 1).spin();

        isJackpot();
        if (body.get(0).isVisible()) {
            body.get(0).makeVisible();
            for (int i = 0; i < wheels.size(); i++) {
                wheels.get(i).makeVisible();
            }
        }
    }

    /**
     * Spins all wheels in the machine.
     */
    public void spin() {

        for(int i = 0; i < wheels.size(); i++) {
            if(!wheels.get(i).getLock()){
                wheels.get(i).spin();    
            }
            
        }

        isJackpot();
        if (body.get(0).isVisible()) {
            body.get(0).makeVisible();
            for (int i = 0; i < wheels.size(); i++) {
                wheels.get(i).makeVisible();
            }
        }
    }
    
    /**
     * Spins a specific wheel by a given number of steps.
     *
     * @param wheel the wheel to spin
     * @param steps the number of steps to rotate the wheel
     */
    public void spin(int wheel, int steps){
        if (wheels.isEmpty()) {
            SlotMachineDialog.showWarning(body.get(0).isVisible(), "No hay ruedas para girar.", "Operación no permitida");
            return;
        }
        if (wheel < 1 || wheel > wheels.size()) {
            SlotMachineDialog.showWarning(body.get(0).isVisible(), "La rueda " + wheel + " no existe.", "Rueda no encontrada");
            return;
        }
        wheels.get(wheel-1).spin(steps);
        isJackpot();
        if (body.get(0).isVisible()) {
            body.get(0).makeVisible();
            for (int i = 0; i < wheels.size(); i++) {
                wheels.get(i).makeVisible();
            }
        }
        
    }
    
    /**
     * Spins the wheels setting specific symbols on them according to the provided array.
     * Remaining wheels without a specified symbol spin randomly.
     *
     * @param setSymbols an array of symbol colors to place on the corresponding wheels
     */
    public void spin(String[] setSymbols){
        if(setSymbols.length > wheels.size()){
            SlotMachineDialog.showWarning(body.get(0).isVisible(), "Hay más símbolos indicados que ruedas disponibles.", "Símbolos excedidos");
            return;
        }
    
        for(int i = 0; i < setSymbols.length; i++){
            wheels.get(i).spin(setSymbols[i]);
        }
        
        if(wheels.size() > setSymbols.length){
            for(int i = setSymbols.length; i < wheels.size(); i++){
                wheels.get(i).spin();
            }
        }
        isJackpot();
        if (body.get(0).isVisible()) {
            body.get(0).makeVisible();
            for (int i = 0; i < wheels.size(); i++) {
                wheels.get(i).makeVisible();
            }
        }
        
    }
    
    /**
     * Swaps the positions and current symbols of two specified wheels.
     *
     * @param wheel1 the position of the first wheel to swap
     * @param wheel2 the position of the second wheel to swap
     */
    public void swap(int wheel1, int wheel2){
        if (wheels.isEmpty()) {
            SlotMachineDialog.showWarning(body.get(0).isVisible(), "No hay ruedas para intercambiar.", "Operación no permitida");
            return;
        }
        
        if (wheel1 < 1 || wheel1 > wheels.size() || wheel2 < 1 || wheel2 > wheels.size()) {
            SlotMachineDialog.showWarning(body.get(0).isVisible(), "Alguna de las ruedas indicadas no existe.", "Rueda no encontrada");
            return;
        }
        
        if(!wheels.get(wheel1 - 1).swapeable() || !wheels.get(wheel2 - 1).swapeable()){
            SlotMachineDialog.showWarning(body.get(0).isVisible(), "Alguna de las ruedas indicadas no tiene la posibilidad de hacer swap", "Rueda de tipo Rebel");
            return;            
        }
        
        int symbol1;
        int symbol2;
        Wheel temp = wheels.get(wheel1-1);
        symbol1 = temp.getCurrentSymbol();
        symbol2 = wheels.get(wheel2-1).getCurrentSymbol();
        
        wheels.set(wheel1-1, wheels.get(wheel2-1));
        wheels.set(wheel2-1, temp);
        
        wheels.get(wheel1-1).setCurrentSymbol(symbol2);
        wheels.get(wheel2-1).setCurrentSymbol(symbol1);

        for (int i = 0; i < wheels.size(); i++) {
            if (wheels.get(i).type().equalsIgnoreCase("lefty")) {
                ((Lefty) wheels.get(i)).setLeft(i > 0 ? wheels.get(i - 1) : null);
            }
        }
        
        int body_x = 20;
        int body_width = 250;
        int diameter = 45;
        int spacing = 5;

        int totalWidth = wheels.size() * diameter
                       + (wheels.size() - 1) * spacing;
        int margin = (body_width - totalWidth) / 2;

        for (int i = 0; i < wheels.size(); i++) {
            int x = body_x + margin + i * (diameter + spacing);
            int y = 80 + (130 - diameter) / 2;
            wheels.get(i).setPosition(x, y);
        }
        
        isJackpot();
        if (body.get(0).isVisible()) {
            body.get(0).makeVisible();
            for (int i = 0; i < wheels.size(); i++) {
                wheels.get(i).makeVisible();
            }
        }
    }
    
    /**
     * Locks a specific wheel to prevent it from spinning.
     *
     * @param wheel the position of the wheel to lock
     */
    public void lock(int wheel){
        if(wheels.isEmpty()){
            SlotMachineDialog.showWarning(body.get(0).isVisible(), "No hay ruedas para bloquear.", "Operación no permitida");
            return;
        }
        
        if (wheel < 1 || wheel > wheels.size()) {
            SlotMachineDialog.showWarning(body.get(0).isVisible(), "La rueda " + wheel + " no existe.", "Rueda no encontrada");
            return;
        }
        
        wheels.get(wheel-1).setLock(true);
        
    }
    
    /**
     * Unlocks a specific wheel to allow it to spin again.
     *
     * @param wheel the position of the wheel to unlock
     */
    public void unlock(int wheel) {
        if (wheels.isEmpty()) {
            SlotMachineDialog.showWarning(body.get(0).isVisible(), "No hay ruedas para desbloquear.", "Operación no permitida");
            return;
        }
        
        if (wheel < 1 || wheel > wheels.size()) {
            SlotMachineDialog.showWarning(body.get(0).isVisible(), "La rueda " + wheel + " no existe.", "Rueda no encontrada");
            return;
        }
        
        wheels.get(wheel-1).setLock(false);
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
        if (wheels.isEmpty()) {
            return gallery.size();
        }

        ArrayList<String> distinct = new ArrayList<>();
        for (int i = 0; i < wheels.size(); i++) {
            Symbol s = wheels.get(i).getShownSymbol();
            if (s != null && !distinct.contains(s.getColor())) {
                distinct.add(s.getColor());
            }
        }
        return distinct.size();
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
