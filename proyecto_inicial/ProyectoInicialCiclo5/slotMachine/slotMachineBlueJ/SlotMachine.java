import java.util.ArrayList;

/**
 * Represents the slot machine composed of wheels, symbols, and a body.
 * 
 * @author Juan Espitia
 * @author Cristian Salamanca
 */
public class SlotMachine {
    
    private ArrayList<Wheel> wheels;
    private ArrayList<Symbol> gallery;
    private ArrayList<BodyMachine> body;

    /**
     * Creates an empty machine with its main body initialized.
     */
    public SlotMachine() {
        wheels = new ArrayList<>();
         gallery = new ArrayList<>();
        body = new ArrayList<>();

        BodyMachine principalBody = new BodyMachine();
        body.add(principalBody);
    }
    
    /**
     * Creates a machine with n wheels and n symbols randomly initialized.
     */
     public SlotMachine(int n){
        wheels = new ArrayList<>();
        gallery = new ArrayList<>();
        body = new ArrayList<>();
        BodyMachine principalBody = new BodyMachine();
         body.add(principalBody); 

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
     * Adds a normal wheel at the given position.
     */
    public void addWheel(int pos) {
        addWheel("normal", pos );
    }
    
    /**
     * Adds a wheel of the specified type at the given position.
     */
    public void addWheel(String type, int pos) {
        int maxWheel = 5;
        
        if (wheels.size() >= maxWheel) { 
            SlotMachineDialog.showError(body.get(0).isVisible(), "No se pueden agregar más de " + maxWheel + " ruedas.");
            return; 
        }

        if (pos < 1) {
            pos = 1;
        }

        if(pos > wheels.size() + 1) {
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
        
        int bodyX = 20;
         int bodyWidth = 250;
        int diameter = 45;
        int spacing = 5;

        int totalWidth = wheels.size() * diameter + (wheels.size() - 1) * spacing;
        int margin = (bodyWidth - totalWidth) / 2;

        for (int i = 0; i < wheels.size(); i++) {
            int x = bodyX + margin + i * (diameter + spacing);
            int y = 80 + (130 - diameter) / 2;
             wheels.get(i).setPosition(x, y);
        }

        for(int i = 0; i < gallery.size(); i++) {
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
     * Removes the wheel located at the given position.
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
        wheels.remove(pos - 1 );

        for (int i = 0; i < wheels.size(); i++) {
            if (wheels.get(i).type().equalsIgnoreCase("lefty")) {
                ((Lefty) wheels.get(i)).setLeft(i > 0 ? wheels.get(i - 1) : null);
            }
        }
        
                
        int bodyX = 20;
        int bodyWidth = 250;
        int diameter = 45;
         int spacing = 5;

        int totalWidth = wheels.size() * diameter + (wheels.size() - 1) * spacing;
        int margin = (bodyWidth - totalWidth) / 2;

        for (int i = 0; i < wheels.size(); i++) {
            int x = bodyX + margin + i * (diameter + spacing);
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
     * Adds a normal symbol at the given position and color.
     */
    public void addSymbol(int pos, String color) {
        addSymbol("normal", pos, color);
    }
    
    /**
     * Adds a symbol of the specified type, position, and color.
     */
    public void addSymbol(String type, int pos, String color) {
        int maxSymbol = 7;

        if (gallery.size() >= maxSymbol) { 
            SlotMachineDialog.showError(body.get(0).isVisible(), "No se pueden agregar más de " + maxSymbol + " símbolos.");
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
                SlotMachineDialog.showError(body.get(0).isVisible(), "Ya existe un símbolo con el color \"" + color + "\".");
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
     * Removes the symbol with the given color from the entire machine.
     */
    public void delSymbol(String color) {

        for(int i = 0; i < gallery.size(); i++) {
            if(gallery.get(i).getColor().equalsIgnoreCase(color)) {

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
     * Places the symbol of the given color on the selected wheel.
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

            if(gallery.get(i).getColor().equalsIgnoreCase(symbol)) {
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
     * Spins only the wheel located at the given position.
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
     * Spins all unlocked wheels of the machine.
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
     * Spins a specific wheel by the given number of steps.
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
     * Spins the wheels setting the colors from the given array.
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
     * Swaps the position and state of two wheels in the machine.
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
        
        int bodyX = 20;
        int bodyWidth = 250;
         int diameter = 45;
        int spacing = 5;

        int totalWidth = wheels.size() * diameter + (wheels.size() - 1) * spacing;
        int margin = (bodyWidth - totalWidth) / 2;

        for (int i = 0; i < wheels.size(); i++) {
            int x = bodyX + margin + i * (diameter + spacing);
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
        if (!wheels.get(wheel - 1).getLock()) {
            SlotMachineDialog.showWarning(body.get(0).isVisible(), "La rueda indicada es Rebel, no es posible bloquearla.", "Rueda de tipo Rebel");
        }
    }
    
    /**
     * Unlocks the given wheel so it can spin again.
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
     * Returns an array with the colors of all symbols in the machine.
     */
    public String[] symbols() {

        String[] result = new String[gallery.size()];

        for(int i = 0; i < gallery.size(); i++) {
             result[i] = gallery.get(i).getColor();
        }

        return result;
    }

    /**
     * Returns the count of distinct symbols currently displayed.
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
     * Returns an array with the current color configuration of the wheels.
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
     * Checks if all wheels show the same symbol (jackpot).
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
     * Makes the body and all wheels of the machine visible.
     */
    public void makeVisible() {

        body.get(0).makeVisible();

        for(int i = 0; i < wheels.size(); i++) {
             wheels.get(i).makeVisible();
        }
    }

    /**
     * Hides the body, the wheels, and the canvas of the machine.
     */
    public void makeInvisible() {

        body.get(0).makeInvisible();

        for(int i = 0; i < wheels.size(); i++) {
            wheels.get(i).makeInvisible();
        }

         Canvas.getCanvas().setVisible(false);
    }

    /**
     * Closes the simulator hiding its elements.
     */
    public void exit() {
        makeInvisible();
        System.exit(0);
    }

}
