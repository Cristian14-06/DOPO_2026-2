import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;

/**
 * Unit tests for SlotMachineContest.
 *
 * @author Juan Espitia
 * @author Cristian Salamanca
 */
public class SlotMachineContestCC2Test {

    private SlotMachineContest contest;

    /**
     * Creates a new SlotMachineContest instance before each test.
     */
    @Before
    public void setUp() {
        contest = new SlotMachineContest();
    }

    /**
     * Verifies that solve_base wins with two wheels.
     */
    @Test(timeout = 5000)
    public void solveGanaConDosRuedas() {
        verificarSolveGana(2);
    }

    /**
     * Verifies that solve_base wins with three wheels.
     */
    @Test(timeout = 5000)
    public void solveGanaConTresRuedas() {
        verificarSolveGana(3);
    }

    /**
     * Verifies that solve_base wins with four wheels.
     */
    @Test(timeout = 5000)
    public void solveGanaConCuatroRuedas() {
        verificarSolveGana(4);
    }

    /**
     * Verifies that solve_base wins with five wheels.
     */
    @Test(timeout = 5000)
    public void solveGanaConCincoRuedas() {
        verificarSolveGana(5);
    }

    /**
     * Runs solve_base several times on machines with n wheels and
     * verifies that it always reaches jackpot within the action budget.
     */
    private void verificarSolveGana(int n) {
        for (int intento = 0; intento < 30; intento++) {
            SlotMachine m = new SlotMachine(n);
            int[][] acciones = contest.solve_base(m, n);

            assertEquals(
                "The machine should reach jackpot after solve_base (n=" + n + ", attempt=" + intento + ")",
                1, m.distinctSymbols()
            );
            assertTrue(
                "The action budget of 10000 must not be exceeded (n=" + n + ")",
                acciones.length <= 10000
            );
        }
    }

    /**
     * Verifies that solve_base performs no actions when the machine already shows a jackpot.
     */
    @Test(timeout = 2000)
    public void solveNoHaceNadaSiYaEsJackpot() {
        SlotMachine m = new SlotMachine(3);
        m.placeSymbol(1, "red");
        m.placeSymbol(2, "red");
        m.placeSymbol(3, "red");
        assertEquals(1, m.distinctSymbols());

        int[][] acciones = contest.solve_base(m, 3);

        assertEquals("There should be no actions if the jackpot was already won", 0, acciones.length);
        assertEquals(1, m.distinctSymbols());
    }

    /**
     * Verifies that solve_base resolves the symmetric stuck case (A,C,C,A).
     */
    @Test(timeout = 5000)
    public void solveResuelveConfiguracionSimetricaAtascada() {
        SlotMachine m = crearConfiguracionSimetrica();

        int[][] acciones = contest.solve_base(m, 4);

        assertEquals(
            "It should reach a single distinct symbol even in the symmetric case (A,C,C,A)",
            1, m.distinctSymbols()
        );
        assertTrue("At least one action should have been needed", acciones.length > 0);
    }

    /**
     * Verifies that the actions returned by solve_base can be replayed
     * on a second machine with the same initial configuration.
     */
    @Test(timeout = 5000)
    public void accionesRetornadasSonReplicablesEnOtraMaquinaIdentica() {
        SlotMachine original = crearConfiguracionSimetrica();
        int[][] acciones = contest.solve_base(original, 4);

        SlotMachine copia = crearConfiguracionSimetrica();
        for (int[] accion : acciones) {
            int rueda = accion[0];
            int pasos = accion[1];
            copia.spin(rueda, pasos);
        }

        assertEquals(
            "Replaying the actions returned by solve_base should also make the copy win",
            1, copia.distinctSymbols()
        );
    }

    /**
     * Builds a 4-wheel machine with the symmetric configuration
     * red, blue, blue, red, used as a stuck test case.
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
     * Verifies that every action returned by solve_base has the correct
     * format and a valid wheel index.
     */
    @Test(timeout = 5000)
    public void cadaAccionTieneRuedaValida() {
        SlotMachine m = new SlotMachine(5);
        int[][] acciones = contest.solve_base(m, 5);

        for (int[] accion : acciones) {
            assertEquals(
                "Each action must have exactly 2 values {wheel, steps}",
                2, accion.length
            );
            assertTrue(
                "The wheel index must be between 1 and n",
                accion[0] >= 1 && accion[0] <= 5
            );
        }
    }

    /**
     * Verifies that simulate does not throw exceptions or hang.
     */
    @Test(timeout = 5000)
    public void simulateNoLanzaExcepcionesNiSeCuelga() {
        contest.simulate(3);
    }
}