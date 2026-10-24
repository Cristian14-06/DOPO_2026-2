import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;

/**
 * Unit tests for the SlotMachineContest class.
 *
 * @author Juan Espitia
 * @author Cristian Salamanca
 */
public class SlotMachineContestCC2Test {

    private SlotMachineContest contest;

    /**
     * Initializes the contest instance before each test.
     */
    @Before
    public void setUp() {
         contest = new SlotMachineContest();
    }

    /**
     * Verifies that solveBase wins with two wheels.
     */
    @Test(timeout = 5000)
    public void solveGanaConDosRuedas() {
        verificarSolveGana(2);
    }

    /**
     * Verifies that solveBase wins with three wheels.
     */
    @Test(timeout = 5000)
    public void solveGanaConTresRuedas(){
         verificarSolveGana(3);
    }

    /**
     * Verifies that solveBase wins with four wheels.
     */
    @Test(timeout = 5000)
    public void solveGanaConCuatroRuedas() {
        verificarSolveGana(4);
    }

    /**
     * Verifies that solveBase wins with five wheels.
     */
    @Test(timeout = 5000)
    public void solveGanaConCincoRuedas() {
        verificarSolveGana(5 );
    }

    /*
     * Runs solveBase repeatedly on machines of size n and validates that it wins.
     */
    private void verificarSolveGana(int n) {
        for(int intento = 0; intento < 30; intento++) {
            SlotMachine m = new SlotMachine(n);
             int[][] acciones = contest.solveBase(m, n);

            assertEquals("The machine should reach jackpot after solveBase (n=" + n + ", attempt=" + intento + ")", 1, m.distinctSymbols());
            assertTrue("The action budget of 10000 must not be exceeded (n=" + n + ")", acciones.length <= 10000);
        }
    }

    /**
     * Checks that no spins are made if the machine is already at jackpot.
     */
    @Test(timeout = 2000)
    public void solveNoHaceNadaSiYaEsJackpot() {
        SlotMachine m = new SlotMachine(3);
        m.placeSymbol(1, "red");
         m.placeSymbol(2, "red");
        m.placeSymbol(3, "red");
        assertEquals(1, m.distinctSymbols());

        int[][] acciones = contest.solveBase(m, 3);

        assertEquals("There should be no actions if the jackpot was already won", 0, acciones.length);
        assertEquals(1, m.distinctSymbols());
    }

    /**
     * Verifies that a stuck symmetric configuration is solved.
     */
    @Test(timeout = 5000)
    public void solveResuelveConfiguracionSimetricaAtascada() {
        SlotMachine m = crearConfiguracionSimetrica();

         int[][] acciones = contest.solveBase(m, 4);

        assertEquals("It should reach a single distinct symbol even in the symmetric case (A,C,C,A)", 1, m.distinctSymbols());
        assertTrue("At least one action should have been needed", acciones.length > 0);
    }

    /**
     * Checks that the returned actions can be replayed on another identical machine.
     */
    @Test(timeout = 5000)
    public void accionesRetornadasSonReplicablesEnOtraMaquinaIdentica() {
        SlotMachine original = crearConfiguracionSimetrica();
        int[][] acciones = contest.solveBase(original, 4);

        SlotMachine copia = crearConfiguracionSimetrica();
        for (int[] accion : acciones) {
            int rueda = accion[0];
             int pasos = accion[1];
            copia.spin(rueda, pasos);
        }

        assertEquals("Replaying the actions returned by solveBase should also make the copy win", 1, copia.distinctSymbols());
    }

    /*
     * Builds a 4-wheel machine with a symmetric configuration for testing.
     */
    private SlotMachine crearConfiguracionSimetrica() {
        SlotMachine m = new SlotMachine(4);
        m.placeSymbol(1, "red");
        m.placeSymbol(2, "blue");
         m.placeSymbol(3, "blue");
        m.placeSymbol(4, "red");
        assertEquals(2, m.distinctSymbols());
        return m;
    }

    /**
     * Verifies that every action has a valid format and wheel index.
     */
    @Test(timeout = 5000)
    public void cadaAccionTieneRuedaValida() {
        SlotMachine m = new SlotMachine(5);
        int[][] acciones = contest.solveBase(m, 5);

        for (int[] accion : acciones) {
             assertEquals("Each action must have exactly 2 values {wheel, steps}", 2, accion.length);
            assertTrue("The wheel index must be between 1 and n", accion[0] >= 1 && accion[0] <= 5);
        }
    }

    /**
     * Checks that simulate runs without throwing errors.
     */
    @Test(timeout = 5000)
    public void simulateNoLanzaExcepcionesNiSeCuelga() {
        contest.simulate(3);
    }

    /**
     * Tests direct call to solve and boundary cases of solveBase.
     */
    @Test(timeout = 5000)
    public void solveDirectoYCasosLimite() {
        int[][] acciones = contest.solve(3);
         assertNotNull(acciones);
        assertEquals(0, contest.solveBase(null, 3).length);
        assertEquals(0, contest.solveBase(new SlotMachine(2), 1).length);
        assertEquals(0, contest.solveBase(new SlotMachine(2), 6).length);
    }
}
