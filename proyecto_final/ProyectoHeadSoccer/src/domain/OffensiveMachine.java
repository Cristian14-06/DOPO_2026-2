package domain;

import java.util.EnumSet;
import java.util.Set;

/**
 * Machine controller implementing the Ofensiva (offensive) profile prioritizing scoring.
 * Pursues the ball across the pitch, positions behind it, jumps, kicks, and executes powers.
 *
 * @author Juan Espitia
 * @author Cristian Salamanca
 */
public class OffensiveMachine extends MachineController {
    private static final long serialVersionUID = 1L;
    private static final double ATTACK_OFFSET = 16.0;
    private static final double BACKTRACK_OFFSET = 26.0;
    private static final double MOVE_TOLERANCE = 4.0;
    private static final double JUMP_MAX_DX = 32.0;
    private static final double JUMP_HEAD_MARGIN_Y = 15.0;
    private static final double KICK_MAX_DX = 30.0;
    private static final double KICK_MAX_HEIGHT = 70.0;
    private static final double KICK_DIRECTION_MARGIN = -12.0;
    private static final double POWER_BALL_DISTANCE = 50.0;
    private static final double FULL_POWER = 100.0;

    /**
     * Constructs an offensive machine controller for the given pitch side.
     *
     * @param side Pitch side controlled by this AI
     */
    public OffensiveMachine(Side side) {
        super(side);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected Set<PlayerAction> chooseActions(MatchView match, PlayerView me, BallView ball, PlayerView rival) {
        Set<PlayerAction> actions = EnumSet.noneOf(PlayerAction.class);
        double myX = me.getHeadShape().getX();
        double bx = ball.getX();

        // Check if ball is behind the player relative to their attack direction
        boolean ballBehindMe = (bx - myX) * side.dir() < 0;

        // Position behind the ball facing opponent's goal to push it forward
        double targetX = ballBehindMe
                ? bx - side.dir() * BACKTRACK_OFFSET
                : bx - side.dir() * ATTACK_OFFSET;

        moveToward(myX, targetX, MOVE_TOLERANCE, actions);

        // Jump for header if ball is at head level or above
        if (shouldJumpForBall(me, ball, JUMP_MAX_DX, JUMP_HEAD_MARGIN_Y)) {
            actions.add(PlayerAction.JUMP);
        }

        // Kick if ball is within range and positioned to shoot towards opponent
        if (shouldKickBall(match, me, ball, KICK_MAX_DX, KICK_MAX_HEIGHT)) {
            if ((bx - myX) * side.dir() > KICK_DIRECTION_MARGIN) {
                actions.add(PlayerAction.KICK);
            }
        }

        // Trigger super power shot if charged and close to the ball
        if (me.getPowerProgress() >= FULL_POWER) {
            double distBall = Math.hypot(myX - bx, me.getHeadShape().getY() - ball.getY());
            if (distBall <= POWER_BALL_DISTANCE && (bx - myX) * side.dir() >= 0) {
                actions.add(PlayerAction.POWER);
            }
        }

        return actions;
    }
}
