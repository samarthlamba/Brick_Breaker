package breakout;

import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;

public class Paddle {

  public static final int PADDLE_EDGE = 15;
  public int PADDLE_WIDTH;
  public int PADDLE_HEIGHT;
  private double speed = 20;
  private final int gameWidth;
  private final Rectangle paddleNode;
  private int lives = 3;


  /**
   * Initialized based on width of screen and height. Creates the rectangular paddle
   *
   * @param gameWidth
   * @param gameHeight
   */
  public Paddle(int gameWidth, int gameHeight) {
    this.gameWidth = gameWidth;
    PADDLE_WIDTH = gameWidth / 5;
    PADDLE_HEIGHT = gameHeight / 25;
    paddleNode = new Rectangle(gameWidth / 2 - PADDLE_WIDTH / 2, gameHeight - PADDLE_HEIGHT - gameHeight / 35,
        PADDLE_WIDTH, PADDLE_HEIGHT);
    paddleNode.setId("paddle");
    Paint paddleColor = Color.HOTPINK;
    paddleNode.setFill(paddleColor);
    paddleNode.setArcWidth(PADDLE_EDGE);
    paddleNode.setArcHeight(PADDLE_EDGE);
    paddleNode.setWidth(PADDLE_WIDTH);
    paddleNode.setHeight(PADDLE_HEIGHT);
  }

  /**
   * returns the rectangular object. Can be changed to image in the future
   *
   * @return
   */
  public Rectangle getObject() {
    return this.paddleNode;
  }

  /**
   * Used to reset the paddle back to its initial position, size, and speed.
   */
  public void reset() {
    this.speed = 20;
    this.paddleNode.setX(gameWidth/2 - PADDLE_WIDTH/2);
    paddleNode.setArcWidth(PADDLE_EDGE);
    paddleNode.setArcHeight(PADDLE_EDGE);
    paddleNode.setWidth(PADDLE_WIDTH);
    paddleNode.setHeight(PADDLE_HEIGHT);
  }

  /**
   * Used to move the paddle right by its current speed
   */
  public void moveRight() {
    this.paddleNode.setX(this.paddleNode.getX() + speed);
  }

  /**
   * Used to move the paddle left by its current speed
   */
  public void moveLeft() {
    this.paddleNode.setX(this.paddleNode.getX() - speed);
  }

  /**
   * Used to increase the paddle's current speed. Called by powerups and cheats.
   */
  public void speedUp() {
    this.speed = this.speed*1.5;
  }

  /**
   * Used to get the paddle's current number of lives.
   * @return the int of the number of lives remaning for this paddle.
   */
  public int getLives(){
    return this.lives;
  }

  /**
   * Used to increase the paddle's current number of lives. Called by cheat keys and powerups.
   */
  public void increaseLives(){
    this.lives = this.lives + 1;
  }

  /**
   * Used to determine if the game is over, i.e. if the paddle has run out of lives.
   * @return
   */
  public boolean gameOver(){
    return (this.lives <= 0);
  }

  /**
   * used to decrement the paddle's lives; called if the ball makes it past the paddle.
   */
  public void decreaseLives(){
    this.lives = this.lives -1;
  }

  /**
   * Used to increase the paddle's length; called by cheat keys and powerups.
   */
  public void increaseLength(){
    int INCREASE_PADDLE_LENGTH = 2;
    this.paddleNode.setWidth(this.paddleNode.getWidth()+ INCREASE_PADDLE_LENGTH);
  }

  /**
   * Used to get the boundaries of the display object representing the paddle in JavaFX.
   * @return the local bounds of the rectangle representing the paddle.
   */
  public javafx.geometry.Bounds getBounds() {
    return this.paddleNode.getBoundsInLocal();
  }

}
