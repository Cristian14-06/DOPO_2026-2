/**
 * Contest class to solve and simulate finding the jackpot.
 *
 * @author Juan Espitia
 * @author Cristian Salamanca
 */
public class SlotMachineContest implements FindJackpot {

    /**
     * Solves the problem for a machine of size n and returns the steps.
     */
    public int[][] solve(int n) {
        SlotMachine machine = new SlotMachine(n);
         int[][] result = solveBase(machine, n);
        return result;
    }

    /**
     * Visually simulates the jackpot solution for a machine of size n.
     */
    public void simulate(int n){
        SlotMachine machine = new SlotMachine(n );
        machine.makeVisible();
         int[][] result = solveBase(machine, n);
    }
}
