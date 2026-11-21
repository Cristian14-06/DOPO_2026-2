
package domain;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Juan Espitia
 * @author Cristian Salamanca
 */
public class IllusionPower extends Power {
    private List<Circle> fakeBalls;
    private int illusionTicksRemaining;

    public IllusionPower() {
<<<<<<< HEAD
        super("Ilusión", 30.0, 2.5, PlayerEffect.CONFUSED);
=======
        super("Ilusión", 2.5);
>>>>>>> b6f49dc0f20b13fe73c109c51ef4638bef4c9d74
        this.fakeBalls = new ArrayList<>();
        this.illusionTicksRemaining = 0;
    }

    @Override
    protected void shapeShot(Ball ball, Side side) {
        if (ball == null) return;

        double dir = (side == Side.LEFT) ? 1.0 : -1.0;
        ball.setHorizontalSpeed(18.0 * dir);
        ball.setVerticalSpeed(-5.0);

        fakeBalls.clear();
        illusionTicksRemaining = (int) (effectDurationSeconds * 60);

        double bx = ball.getCircle().getX();
        double by = ball.getCircle().getY();
        double r = ball.getCircle().getRadius();

        for (int i = 0; i < 7; i++) {
            double angle = i * (2 * Math.PI / 7);
            double fx = bx + Math.cos(angle) * (18 + i * 4);
            double fy = by + Math.sin(angle) * (15 + i * 3);

            fakeBalls.add(new Circle(fx, fy, r));
        }
    }

    public void updateClones(Ball realBall) {
        if (illusionTicksRemaining > 0 && realBall != null) {
            illusionTicksRemaining--;

            double bx = realBall.getCircle().getX();
            double by = realBall.getCircle().getY();

            for (int i = 0; i < fakeBalls.size(); i++) {
                Circle c = fakeBalls.get(i);
                double offsetAngle = (i * 0.9)
                        + (illusionTicksRemaining * 0.1);

                c.setX(bx + Math.cos(offsetAngle)
                        * (20 + (i % 3) * 8));

                c.setY(by + Math.sin(offsetAngle)
                        * (14 + (i % 2) * 6));
            }
        } else {
            fakeBalls.clear();
        }
    }

    public List<Circle> getFakeBalls() {
        return getDecoyBalls();
    }

<<<<<<< HEAD
    @Override
    public List<Circle> getDecoyBalls() {
        List<Circle> copies = new ArrayList<>();

        if (hasActiveIllusion()) {
            for (Circle ball : fakeBalls) {
                copies.add(new Circle(
                    ball.getX(),
                    ball.getY(),
                    ball.getRadius()
                ));
            }
        }

        return copies;
    }

    public boolean hasActiveIllusion() {
        return illusionTicksRemaining > 0
                && !fakeBalls.isEmpty();
    }

    @Override
    public void updateShot(Ball ball) {
=======
    /**
     * Checks whether illusion decoy balls are currently active.
     *
     * @return True if illusion is active
     */
    public boolean hasActiveIllusion() {
        return illusionTicksRemaining > 0 && !fakeBalls.isEmpty();
    }

    /**
     * Returns the base power damage for Ilusión.
     *
     * @return 30.0
     */
    @Override
    public double getBaseDamage() {
        return 30.0;
    }

    /**
     * Creates the CONFUSED effect applied on impact.
     *
     * @return ConfusedEffect instance
     */
    @Override
    public Effect createEffect() {
        return new ConfusedEffect();
    }

    /**
     * Updates decoy clone positions during each simulation tick.
     *
     * @param ball Real ball in play
     */
    @Override
    protected void updateActiveShot(Ball ball){
>>>>>>> b6f49dc0f20b13fe73c109c51ef4638bef4c9d74
        updateClones(ball);
    }
}
