package breakout.cheats;

import breakout.Ball;
import breakout.Level;
import breakout.Paddle;

public abstract class Cheat {
    private Ball ball;
    private Paddle paddle;

    public Cheat(Ball ball, Paddle paddle) {
        this.ball = ball;
        this.paddle = paddle;
    }

    public abstract void doCheat();

    protected Ball getBall() {
        return ball;
    }

    protected Paddle getPaddle() {
        return paddle;
    }
}
