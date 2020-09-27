package breakout;

import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;

public class Paddle {

  public static final int PADDLE_EDGE = 15;
  public final double paddleWidth;
  public final double paddleHeight;
  private double speed = 20;
  private final int gameWidth;
  private final Rectangle paddleNode;
  private int lives = 3;
  private static final double PADDLE_WIDTH_MULTIPLIER = 0.2;
  private static final double PADDLE_HEIGHT_MULTIPLIER = 1/25;
  private static final double PADDLE_LOCATION_Y_OFFSET = 1/35;
  private static final int INITIAL_SPEED_PADDLE = 20;
  private static final double SPEED_UP_MULTIPLIER = 1.5;
  private static final int LIVES_CHANGE_AMOUNT = 1;
  private static final int INCREASE_PADDLE_LENGTH = 2;

  /**
   * Initialized based on width of screen and height. Creates the rectangular paddle
   *
   * @param gameWidth
   * @param gameHeight
   */
  public Paddle(int gameWidth, int gameHeight) {
    this.gameWidth = gameWidth;
    paddleWidth = gameWidth*PADDLE_WIDTH_MULTIPLIER;
    paddleHeight = gameHeight* PADDLE_HEIGHT_MULTIPLIER;
    paddleNode = new Rectangle(gameWidth / 2 - paddleWidth / 2, gameHeight - paddleHeight
        - gameHeight / PADDLE_LOCATION_Y_OFFSET,
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
   * @return
   */
  public Rectangle getObject() {
    return this.paddleNode;
  }

  /**
   * Used to reset the paddle back to its initial position, size, and speed.
   */
  public void reset() {
    this.speed = INITIAL_SPEED_PADDLE;
    this.paddleNode.setX(gameWidth/2 - paddleWidth /2);
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

    if ((this.paddleNode.getX())> gameWidth){
      this.paddleNode.setX(this.paddleNode.getX()-gameWidth-this.paddleNode.getWidth());
    }
  }

  /**
   * Used to move the paddle left by its current speed
   */
  public void moveLeft() {
    this.paddleNode.setX(this.paddleNode.getX() - speed);
    System.out.println(this.paddleNode.getX() + "    " + gameWidth);
    if ((this.paddleNode.getX()+this.paddleNode.getWidth())<0){
      this.paddleNode.setX(this.paddleNode.getX()+gameWidth+ this.paddleNode.getWidth());
    }
  }

  /**
   * Used to increase the paddle's current speed. Called by powerups and cheats.
   */
  public void speedUp() {
    this.speed = this.speed*SPEED_UP_MULTIPLIER;
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
    this.lives = this.lives + LIVES_CHANGE_AMOUNT;
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
    this.lives = this.lives - LIVES_CHANGE_AMOUNT;
  }

  /**
   * Used to increase the paddle's length; called by cheat keys and powerups.
   */
  public void increaseLength(){
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
