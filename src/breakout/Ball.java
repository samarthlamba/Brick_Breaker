package breakout;

import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;

public class Ball {
  private int speed = 120;
  private Circle ball;
  private Paint ballColor = Color.RED;
  public static final int PADDLE_EDGE = 15;
  public int BALL_RADIUS;
  private int currentXDirection = 1;
  private int currentYDirection = 1;

  /**
   * Initialized based on width of screen and height. Creates the circular ball
   *
   * @param width
   * @param height
   */
  public Ball(int width, int height){
    BALL_RADIUS = width/60;
    ball = new Circle(width/2, height/2, BALL_RADIUS);
    ball.setFill(ballColor);

  }
  public void move(double time){
    this.ball.setCenterY(this.ball.getCenterY()-speed*time*currentYDirection);
    this.ball.setCenterX(this.ball.getCenterX()-speed*time*currentXDirection);
  }
  public void changeXDirection(double time){
      this.currentXDirection = this.currentXDirection*-1;

  }
  public void changeYDirection(double time){
    this.currentYDirection = this.currentYDirection*-1;

  }
  public double getX(){
    return this.ball.getCenterX();

  }
  public double getY(){
    return this.ball.getCenterY();

  }
  public javafx.geometry.Bounds getBounds(){
    return this.ball.getBoundsInParent();
  }
  /**
   * returns the rectangular object. Can be changed to image in the future
   * @return
   */
  public Circle getObject(){
    return this.ball;

  }
}
