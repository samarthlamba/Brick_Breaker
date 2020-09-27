package breakout;

import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;

public class Paddle {

  public static final int PADDLE_EDGE = 15;
  public final int paddleWidth;
  public final int paddleHeight;
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
    paddleWidth = gameWidth / 5;
    paddleHeight = gameHeight / 25;
    paddleNode = new Rectangle(gameWidth / 2 - paddleWidth / 2, gameHeight - paddleHeight
        - gameHeight / 35,
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
    this.speed = 20;
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
    int INCREASE_PADDLE_LENGTH = 2;
    this.paddleNode.setWidth(this.paddleNode.getWidth()+ INCREASE_PADDLE_LENGTH);
  }


  public javafx.geometry.Bounds getBounds() {
    return this.paddleNode.getBoundsInLocal();
  }

}
