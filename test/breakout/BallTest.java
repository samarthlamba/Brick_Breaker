package breakout;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class BallTest {
  private final int TEST_HEIGHT = 10;
  private final int TEST_WIDTH = 10;

  @Test
  public void testDecreaseBallSpeed(){
    Ball testBall = new Ball(TEST_WIDTH,TEST_HEIGHT);
    double initialBallSpeedX = testBall.getSpeedX();
    double initialBallSpeedY = testBall.getSpeedY();
    testBall.decreaseSpeed();
    assertEquals(initialBallSpeedX*0.95, testBall.getSpeedX());
    assertEquals(initialBallSpeedY*0.95, testBall.getSpeedX());
  }
  @Test
  public void testBallStart(){
    Ball testBall = new Ball(TEST_WIDTH,TEST_HEIGHT);
    double initialSpeedX = testBall.getSpeedX();
    testBall.changeSpeedX(5);

    assertEquals(initialSpeedX + 5, testBall.getSpeedX());
  }

}
