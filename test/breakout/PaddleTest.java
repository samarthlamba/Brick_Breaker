package breakout;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class PaddleTest {
  private static final int TEST_HEIGHT = 10;
  private static final int TEST_WIDTH = 10;
  private static final Paddle testPaddle = new Paddle(TEST_WIDTH,TEST_HEIGHT);

  @Test
  public void testIncreasePaddleLength(){
    double initialWidth = testPaddle.getBounds().getWidth();
    testPaddle.increaseLength();
    assertEquals(initialWidth+2, testPaddle.getBounds().getWidth());
  }

  @Test
  public void testIncreasePaddleLives(){
    int initialLives = testPaddle.getLives();
    testPaddle.increaseLives();
    assertEquals(initialLives+1, testPaddle.getLives());
  }

}
