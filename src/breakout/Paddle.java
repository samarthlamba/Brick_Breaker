package breakout;

import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;

public class Paddle {

  public static final int PADDLE_EDGE = 15;
  public int PADDLE_WIDTH;
  public int PADDLE_HEIGHT;
  private double speed = 20;
  private int gameWidth;
  private Rectangle paddleNode;
  private Paint paddleColor = Color.HOTPINK;
  private int INCREASE_PADDLE_LENGTH = 2;
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

  public void reset() {
    this.speed = 20;
    this.paddleNode.setX(gameWidth/2 - PADDLE_WIDTH/2);
    paddleNode.setArcWidth(PADDLE_EDGE);
    paddleNode.setArcHeight(PADDLE_EDGE);
    paddleNode.setWidth(PADDLE_WIDTH);
    paddleNode.setHeight(PADDLE_HEIGHT);
  }

  /**
   * changes color. Maybe level up?
   *
   * @param color
   */
  public void changeColor(Paint color) {
    paddleColor = color;
  }

  public void moveRight() {
    this.paddleNode.setX(this.paddleNode.getX() + speed);
  }

  public void moveLeft() {
    this.paddleNode.setX(this.paddleNode.getX() - speed);
  }

  public void speedUp() {
    this.speed = this.speed*1.5;
  }
  public int getLives(){
    return this.lives;
  }

  public void increaseLives(){
    this.lives = this.lives + 1;
  }

  public boolean gameOver(){
    return (this.lives <= 0);
  }

  public void decreaseLives(){
    this.lives = this.lives -1;
  }

  public void increaseLength(){
    this.paddleNode.setWidth(this.paddleNode.getWidth()+INCREASE_PADDLE_LENGTH);
  }


  public double getX() {
    return this.paddleNode.getX();
  }

  public void setX(double X) {
    this.paddleNode.setX(X);
  }

  public javafx.geometry.Bounds getBounds() {
    return this.paddleNode.getBoundsInLocal();
  }

}
