package breakout.cheats;

import breakout.Ball;
import breakout.Level;
import breakout.Paddle;

public class ResetCheat extends Cheat{
    public ResetCheat(Ball ball, Paddle paddle, Level currentLevel) {
        super(ball, paddle);
    }

    @Override
    public void doCheat() {
        this.getBall().reset();
        this.getPaddle().reset();
    }
}
