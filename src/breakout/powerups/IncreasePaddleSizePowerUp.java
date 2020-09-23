package breakout.powerups;

import breakout.Ball;
import breakout.Paddle;
import javafx.scene.Node;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;

public class IncreasePaddleSizePowerUp extends PowerUp {

  public IncreasePaddleSizePowerUp(Node blockToRemove) {
    super(blockToRemove);
  }

  @Override
  public void doPowerUp(Paddle paddle, Ball ball) {
    paddle.increaseLength();
  }

  @Override
  Paint getFill() {
    return Color.RED;
  }


}
