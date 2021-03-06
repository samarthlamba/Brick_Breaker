package breakout;

import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;

/**
 * This class is the paddle controlled by the player.
 */
public class Paddle {

  public static final int PADDLE_EDGE = 15;
  private static final int PADDLE_WIDTH_RATIO = 5;
  private static final int PADDLE_HEIGHT_RATIO = 25;
  private static final double PADDLE_OFFSET = 35;
  private static final int INITIAL_SPEED = 20;
  private static final int SPEED_INCREMENT = 2;
  private static final double SPEED_MULTIPLIER = 1.5;
  private static final int LIVES_INCREASE = 1;
  private static final double HALFWAY = 2;
  private static final int ZERO = 0;
  public final int paddleWidth;
  public final int paddleHeight;
  private final int gameWidth;
  private final Rectangle paddleNode;
  private double speed = 20;
  private int lives = 3;


  /**
   * Initialized based on width of screen and height. Creates the rectangular paddle
   *
   * @param gameWidth  size of the scene
   * @param gameHeight height of the scene
   */
  public Paddle(int gameWidth, int gameHeight) {
    this.gameWidth = gameWidth;
    paddleWidth = gameWidth / PADDLE_WIDTH_RATIO;
    paddleHeight = gameHeight / PADDLE_HEIGHT_RATIO;
    paddleNode = new Rectangle(gameWidth / HALFWAY - paddleWidth / HALFWAY,
        gameHeight - paddleHeight
            - gameHeight / PADDLE_OFFSET,
        paddleWidth, paddleHeight);
    paddleNode.setId("paddle");
    Paint paddleColor = Color.HOTPINK;
    paddleNode.setFill(paddleColor);
    paddleNode.setArcWidth(PADDLE_EDGE);
    paddleNode.setArcHeight(PADDLE_EDGE);
    paddleNode.setWidth(paddleWidth);
    paddleNode.setHeight(paddleHeight);
  }

  /**
   * returns the rectangular object. Can be changed to image in the future
   *
   * @return return the paddle node object
   */
  public Rectangle getObject() {
    return this.paddleNode;
  }

  /**
   * Used to reset the paddle back to its initial position, size, and speed.
   */
  public void reset() {
    this.speed = INITIAL_SPEED;
    this.paddleNode.setX(gameWidth / HALFWAY - paddleWidth / HALFWAY);
    paddleNode.setArcWidth(PADDLE_EDGE);
    paddleNode.setArcHeight(PADDLE_EDGE);
    paddleNode.setWidth(paddleWidth);
    paddleNode.setHeight(paddleHeight);
  }

  /**
   * Used to move the paddle right by its current speed
   */
  public void moveRight() {
    this.paddleNode.setX(this.paddleNode.getX() + speed);

    if ((this.paddleNode.getX()) > gameWidth) {
      this.paddleNode.setX(this.paddleNode.getX() - gameWidth - this.paddleNode.getWidth());
    }
  }

  /**
   * Used to move the paddle left by its current speed
   */
  public void moveLeft() {
    this.paddleNode.setX(this.paddleNode.getX() - speed);
    if ((this.paddleNode.getX() + this.paddleNode.getWidth()) < ZERO) {
      this.paddleNode.setX(this.paddleNode.getX() + gameWidth + this.paddleNode.getWidth());
    }
  }

  /**
   * Used to increase the paddle's current speed. Called by powerups and cheats.
   */
  public void speedUp() {
    this.speed = this.speed * SPEED_MULTIPLIER;
  }

  /**
   * Used to get the paddle's current number of lives.
   *
   * @return the int of the number of lives remaning for this paddle.
   */
  public int getLives() {
    return this.lives;
  }

  /**
   * Used to increase the paddle's current number of lives. Called by cheat keys and powerups.
   */
  public void increaseLives() {
    this.lives = this.lives + LIVES_INCREASE;
  }

  /**
   * Used to determine if the game is over, i.e. if the paddle has run out of lives.
   *
   * @return returns if game is over and you lost
   */
  public boolean gameOver() {
    return (this.lives <= ZERO);
  }

  /**
   * used to decrement the paddle's lives; called if the ball makes it past the paddle.
   */
  public void decreaseLives() {
    this.lives = this.lives - LIVES_INCREASE;
  }

  /**
   * Used to increase the paddle's length; called by cheat keys and powerups.
   */
  public void increaseLength() {
    this.paddleNode.setWidth(this.paddleNode.getWidth() + SPEED_INCREMENT);
  }

  /**
   * Used to get the boundaries of the display object representing the paddle in JavaFX.
   *
   * @return the local bounds of the rectangle representing the paddle.
   */
  public javafx.geometry.Bounds getBounds() {
    return this.paddleNode.getBoundsInLocal();
  }

}
