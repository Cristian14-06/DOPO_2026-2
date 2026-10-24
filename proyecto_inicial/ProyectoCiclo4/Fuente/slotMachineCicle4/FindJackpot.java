import java.util.ArrayList;

/**
 * Interface that provides a shared algorithm to reach the jackpot on a
 * SlotMachine, using only spin(wheel, steps) and distinctSymbols() as
 * feedback. The algorithm maintains a set of candidate relative states
 * consistent with the observed distinct-symbol counts and narrows it
 * down, spin by spin, until a single winning configuration is found.
 *
 * @author Juan Espitia
 * @author Cristian Salamanca
 */
public interface FindJackpot
{
    /**
     * Attempts to bring all wheels of the given machine to the same
     * symbol, using only spin(wheel, steps) and distinctSymbols().
     * Returns the list of actions actually performed on the machine,
     * each represented as {wheel, steps}.
     *
     * @param m the machine to operate on
     * @param n the number of wheels and symbols of the machine
     * @return the sequence of actions performed to reach the jackpot
     */
    public default int[][] solve_base(SlotMachine m, int n) {
        if (m == null || n <= 1 || n > 5) {
            return new int[0][0];
        }

        ArrayList<int[]> actions = new ArrayList<>();
        int k = m.distinctSymbols();
        if (k == 1) {
            return new int[0][0];
        }

        java.util.List<int[]> candidates = new ArrayList<>();
        int totalStates = 1;
        for (int i = 1; i < n; i++) {
            totalStates *= n;
        }

        for (int s = 0; s < totalStates; s++) {
            int[] st = new int[n];
            st[0] = 0;
            int temp = s;
            for (int i = 1; i < n; i++) {
                st[i] = temp % n;
                temp /= n;
            }
            if (countDistinct(st) == k) {
                candidates.add(st);
            }
        }

        while (k > 1) {
            if (candidates.size() == 1) {
                int[] cand = candidates.get(0);
                for (int w = 1; w < n; w++) {
                    int diff = (cand[0] - cand[w] + n) % n;
                    if (diff != 0) {
                        m.spin(w + 1, diff);
                        actions.add(new int[]{w + 1, diff});
                    }
                }
                k = m.distinctSymbols();
                break;
            }

            int bestW = 0;
            int bestStep = 1;
            int minMaxGroup = Integer.MAX_VALUE;

            for (int w = 0; w < n; w++) {
                for (int step = 1; step < n; step++) {
                    int[] groupSizes = new int[n + 1];
                    for (int[] cand : candidates) {
                        int[] nextSt = cand.clone();
                        nextSt[w] = (nextSt[w] + step) % n;
                        groupSizes[countDistinct(nextSt)]++;
                    }
                    int maxG = 0;
                    for (int g : groupSizes) {
                        if (g > maxG) {
                            maxG = g;
                        }
                    }
                    if (maxG < minMaxGroup) {
                        minMaxGroup = maxG;
                        bestW = w;
                        bestStep = step;
                    }
                }
            }

            m.spin(bestW + 1, bestStep);
            actions.add(new int[]{bestW + 1, bestStep});
            k = m.distinctSymbols();
            if (k == 1) {
                break;
            }

            java.util.List<int[]> nextCandidates = new ArrayList<>();
            for (int[] cand : candidates) {
                cand[bestW] = (cand[bestW] + bestStep) % n;
                if (countDistinct(cand) == k) {
                    nextCandidates.add(cand);
                }
            }
            candidates = nextCandidates;
        }

        return actions.toArray(new int[0][]);
    }

    /**
     * Counts the number of distinct values in the given array.
     *
     * @param arr the array of relative wheel positions
     * @return the number of distinct values found in arr
     */
    private static int countDistinct(int[] arr) {
        boolean[] seen = new boolean[10];
        int count = 0;
        for (int v : arr) {
            if (!seen[v]) {
                seen[v] = true;
                count++;
            }
        }
        return count;
    }
}