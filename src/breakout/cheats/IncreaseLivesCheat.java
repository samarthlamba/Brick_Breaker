package breakout.cheats;

import breakout.Ball;
import breakout.Level;
import breakout.Paddle;

public class IncreaseLivesCheat extends Cheat{
    public IncreaseLivesCheat(Ball ball, Paddle paddle, Level currentLevel) {
        super(ball, paddle);
    }

    @Override
    public void doCheat() {
        this.getPaddle().increaseLives();
    }
}
