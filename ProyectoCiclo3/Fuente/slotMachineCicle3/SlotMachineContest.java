/**
 * Contest wrapper for the jackpot-solving algorithm shared through
 * FindJackpot. Exposes solve(n), which computes and returns the sequence
 * of actions needed to win on a hidden machine, and simulate(n), which
 * runs the same algorithm on a visible machine so the process can be watched.
 *
 * @author Juan Espitia
 * @author Cristian Salamanca
 */
public class SlotMachineContest implements FindJackpot {

    /**
     * Creates a new, invisible machine with n wheels and returns the
     * sequence of actions needed to reach the jackpot.
     *
     * @param n the number of wheels and symbols of the machine
     * @return the sequence of actions performed to reach the jackpot
     */
    public int[][] solve(int n) {
        SlotMachine machine = new SlotMachine(n);
        int[][] result = solve_base(machine, n);
        return result;
    }

    /**
     * Creates a new, visible machine with n wheels and runs the jackpot
     * algorithm on it so the process can be watched.
     *
     * @param n the number of wheels and symbols of the machine
     */
    public void simulate(int n) {
        SlotMachine machine = new SlotMachine(n);
        machine.makeVisible();
        int[][] result = solve_base(machine, n);
    }
}
