package breakout.cheats;

import breakout.Ball;
import breakout.Level;
import breakout.Paddle;

public class SpeedUpPaddleCheat extends Cheat {
    public SpeedUpPaddleCheat(Ball ball, Paddle paddle, Level currentLevel) {
        super(ball, paddle);
    }

    @Override
    public void doCheat() {
        this.getPaddle().speedUp();
    }
}
