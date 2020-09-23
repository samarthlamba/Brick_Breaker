package breakout.powerups;

import breakout.Ball;
import breakout.Paddle;
import javafx.scene.Node;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;

public class DecreaseBallSpeedPowerUp extends PowerUp {

  public DecreaseBallSpeedPowerUp(Node blockToRemove) {
    super(blockToRemove);
  }

  @Override
  public void doPowerUp(Paddle paddle, Ball ball) {
    ball.decreaseSpeed();
  }

  @Override
  Paint getFill() {
    return Color.BLACK;
  }

}
