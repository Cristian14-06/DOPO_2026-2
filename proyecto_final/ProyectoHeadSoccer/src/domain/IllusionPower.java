
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
        super("Ilusión", 30.0, 2.5, PlayerEffect.CONFUSED);
        this.fakeBalls = new ArrayList<>();
        this.illusionTicksRemaining = 0;
    }

    @Override
    public void affectBall(Ball ball, Side side) {
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
        updateClones(ball);
    }
}
