package domain;

public interface Collidable {
    default void collideWithBall(Ball ball) {}
    default void collideWithHead(Head head) {}
    default void collideWithBody(Body body) {}
    default void collideWithFoot(Foot foot) {}
}
