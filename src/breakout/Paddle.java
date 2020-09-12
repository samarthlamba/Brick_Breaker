package breakout;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;

public class Paddle{
  private int speed = 5;
  private int width;
  private int height;
  private Rectangle paddle;
  private Paint paddleColor = Color.HOTPINK;
  public static final int PADDLE_EDGE = 15;
  public int PADDLE_WIDTH;
  public int PADDLE_HEIGHT;

  /**
   * Initialized based on width of screen and height. Creates the rectangular paddle
   *
   * @param width
   * @param height
   */
  public Paddle(int width, int height){
      PADDLE_WIDTH= width/5;
      PADDLE_HEIGHT = height/25;
      paddle = new Rectangle(width/2-PADDLE_WIDTH/2, height-PADDLE_HEIGHT-height/35, PADDLE_WIDTH, PADDLE_HEIGHT);
      paddle.setFill(paddleColor);
    paddle.setArcWidth(PADDLE_EDGE);
    paddle.setArcHeight(PADDLE_EDGE);
    paddle.setWidth(PADDLE_WIDTH);
    paddle.setHeight(PADDLE_HEIGHT);
  }

  /**
   * returns the rectangular object. Can be changed to image in the future
   * @return
   */
  public Rectangle getObject(){
    return this.paddle;

  }

  /**
   * changes color. Maybe level up?
   * @param color
   */
  public void changeColor(Paint color){
    paddleColor = color;
  }
  public void moveRight(){
    this.paddle.setX(this.paddle.getX() + speed);
  }
  public void moveLeft(){
    this.paddle.setX(this.paddle.getX() - speed);
  }

  public void speedUp(){
    this.speed = this.speed + 5;
    System.out.println(speed);
  }

  public void setX(double X){
    this.paddle.setX(X);
  }
  public double getX(){
    return this.paddle.getX();
  }

}
