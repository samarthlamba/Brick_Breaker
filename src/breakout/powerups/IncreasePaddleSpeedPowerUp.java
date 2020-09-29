package breakout.powerups;

import breakout.Ball;
import breakout.Paddle;
import javafx.scene.Node;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;

/**
 * Powerup that increases paddle speed
 */
public class IncreasePaddleSpeedPowerUp extends PowerUp {

  public IncreasePaddleSpeedPowerUp(Node blockToRemove) {
    super(blockToRemove);
  }

  @Override
  public void doPowerUp(Paddle paddle, Ball ball) {
    paddle.speedUp();
  }

  @Override
  Paint getFill() {
    return Color.GREEN;
  }

}
