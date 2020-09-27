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

  public void reset() {
    this.speed = INITIAL_SPEED_PADDLE;
    this.paddleNode.setX(gameWidth/2 - paddleWidth /2);
    paddleNode.setArcWidth(PADDLE_EDGE);
    paddleNode.setArcHeight(PADDLE_EDGE);
    paddleNode.setWidth(paddleWidth);
    paddleNode.setHeight(paddleHeight);
  }


  public void moveRight() {
    this.paddleNode.setX(this.paddleNode.getX() + speed);

    if ((this.paddleNode.getX())> gameWidth){
      this.paddleNode.setX(this.paddleNode.getX()-gameWidth-this.paddleNode.getWidth());
    }
  }

  public void moveLeft() {
    this.paddleNode.setX(this.paddleNode.getX() - speed);
    System.out.println(this.paddleNode.getX() + "    " + gameWidth);
    if ((this.paddleNode.getX()+this.paddleNode.getWidth())<0){
      this.paddleNode.setX(this.paddleNode.getX()+gameWidth+ this.paddleNode.getWidth());
    }
  }

  public void speedUp() {
    this.speed = this.speed*SPEED_UP_MULTIPLIER;
  }
  public int getLives(){
    return this.lives;
  }

  public void increaseLives(){
    this.lives = this.lives + LIVES_CHANGE_AMOUNT;
  }

  public boolean gameOver(){
    return (this.lives <= 0);
  }

  public void decreaseLives(){
    this.lives = this.lives - LIVES_CHANGE_AMOUNT;
  }

  public void increaseLength(){
    this.paddleNode.setWidth(this.paddleNode.getWidth()+ INCREASE_PADDLE_LENGTH);
  }


  public javafx.geometry.Bounds getBounds() {
    return this.paddleNode.getBoundsInLocal();
  }

}
