import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class SlotMachineCC2Test{

     
   
    // PRUEBAS DE ADICIÓN Y CONTEO DE SÍMBOLOS
  
    @Test
    public void addSymbolEsBaShouldAddSymbol() {
        SlotMachine machine = new SlotMachine();
    
        assertEquals(0, machine.distinctSymbols());
        
        machine.addSymbol(1, "red");
        
        assertEquals(1, machine.distinctSymbols());
    }

    // PRUEBAS DE ELIMINACIÓN DE SÍMBOLOS
  
    @Test
    public void addSymbolEsBaShouldNotAddRepeatedSymbol() {
        SlotMachine machine = new SlotMachine();

        machine.addSymbol(1, "red");
        machine.addSymbol(2, "red");

        assertEquals(1, machine.distinctSymbols());
    }
    
    @Test
    public void delSymbolEsBaShouldRemoveSymbol() {
        SlotMachine machine = new SlotMachine();

        machine.addSymbol(1, "red");
        machine.addSymbol(2, "blue");

        assertEquals(2, machine.distinctSymbols());

        machine.delSymbol("red");

        assertEquals(1, machine.distinctSymbols());
    }
   
    // PRUEBAS DE INTERCAMBIO DE RUEDAS (SWAP)
    
    @Test
    public void swapEsBaShouldSwapingWheels(){
        SlotMachine machine = new SlotMachine();

        machine.addWheel(1);
        machine.addWheel(2);

        machine.addSymbol(1, "red");
        machine.addSymbol(2, "blue");

        machine.placeSymbol(1, "red");
        machine.placeSymbol(2, "blue");

        assertArrayEquals(
            new String[]{"red", "blue"},
            machine.configuration()
        );

        machine.swap(1, 2);

        assertArrayEquals(
            new String[]{"blue", "red"},
            machine.configuration()
        );      
    }
 
    // PRUEBAS DE GIRO DE CONFIGURACIÓN (SPIN)
    
    @Test
    public void spinConfigurationEsBaShouldBeTheSame() {
        SlotMachine machine = new SlotMachine();

        machine.addWheel(1);
        machine.addWheel(2);
        machine.addWheel(3);

        
        machine.addSymbol(1, "red");
        machine.addSymbol(2, "green");
        machine.addSymbol(3, "blue");

        machine.spin(new String[]{"green", "red", "blue"});

        assertArrayEquals(
            new String[]{"green", "red", "blue"},
            machine.configuration()
        );        
    }
   }
