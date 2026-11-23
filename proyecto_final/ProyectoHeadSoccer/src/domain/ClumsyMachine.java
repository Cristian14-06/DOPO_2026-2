package domain;

import java.util.EnumSet;
import java.util.Random;
import java.util.Set;

/**
 * Machine controller implementing the Torpe (clumsy) profile with randomized,
 * erratic movements and delayed reactions.
 *
 * @author Juan Espitia
 * @author Cristian Salamanca
 */
public class ClumsyMachine extends MachineController {
    private static final long serialVersionUID = 1L;
    private static final int MIN_ACTION_TICKS = 12;
    private static final int EXTRA_RANDOM_TICKS = 18;
    private static final double POWER_CHANCE = 0.10;

    private final Random random;
    private int actionTimer = 0;
    private final Set<PlayerAction> bufferedActions = EnumSet.noneOf(PlayerAction.class);

    /**
     * Constructs a clumsy machine controller with a default random generator.
     *
     * @param side Pitch side controlled by this AI
     */
    public ClumsyMachine(Side side) {
        this(side, new Random());
    }

    /**
     * Constructs a clumsy machine controller with an injectable random generator for testing.
     *
     * @param side Pitch side controlled by this AI
     * @param random Random generator instance
     */
    public ClumsyMachine(Side side, Random random) {
        super(side);
        this.random = (random != null) ? random : new Random();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected Set<PlayerAction> chooseActions(MatchView match, PlayerView me, BallView ball, PlayerView rival) {
        if (actionTimer > 0) {
            actionTimer--;
            return EnumSet.copyOf(bufferedActions);
        }

        bufferedActions.clear();
        actionTimer = MIN_ACTION_TICKS + random.nextInt(EXTRA_RANDOM_TICKS);

        int choice = random.nextInt(6);
        switch (choice) {
            case 0:
                bufferedActions.add(PlayerAction.LEFT);
                break;
            case 1:
                bufferedActions.add(PlayerAction.RIGHT);
                break;
            case 2:
                bufferedActions.add(PlayerAction.JUMP);
                break;
            case 3:
                bufferedActions.add(PlayerAction.KICK);
                break;
            case 4:
                // Move towards or away from the ball clumsily
                if (ball.getX() > me.getHeadShape().getX()) {
                    bufferedActions.add(random.nextBoolean() ? PlayerAction.RIGHT : PlayerAction.LEFT);
                } else {
                    bufferedActions.add(random.nextBoolean() ? PlayerAction.LEFT : PlayerAction.RIGHT);
                }
                if (random.nextBoolean()) {
                    bufferedActions.add(PlayerAction.KICK);
                }
                break;
            case 5:
            default:
                // Idle hesitation frame
                break;
        }

        // Randomly attempts to activate power when fully charged
        if (me.getPowerProgress() >= 100.0 && random.nextDouble() < POWER_CHANCE) {
            bufferedActions.add(PlayerAction.POWER);
        }

        return EnumSet.copyOf(bufferedActions);
    }
}
