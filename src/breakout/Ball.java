package breakout;

import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;

public class Ball {

  public static final int PADDLE_EDGE = 15;
  public int BALL_RADIUS;
  private double speed = 120;
  private Circle ball;
  private Paint ballColor = Color.RED;
  private int currentXDirection = 1;
  private int currentYDirection = 1;
  private int initialWidth;
  private int initialHeight;
  private int sceneWidth;
  private int sceneHeight;
  private double speedDecrease = 0;


  /**
   * Initialized based on width of screen and height. Creates the circular ball
   *
   * @param width
   * @param height
   */
  public Ball(int width, int height) {
    sceneWidth = width;
    sceneHeight = height;
    BALL_RADIUS = width / 60;
    initialWidth = width;
    initialHeight = height;
    ball = new Circle(width / 2, height / 2, BALL_RADIUS);
    ball.setId("ball");
    ball.setFill(ballColor);

  }

  public void move(double time, Paddle p) {
    this.ball.setCenterY(this.ball.getCenterY() - speed * time * currentYDirection+ currentXDirection*speedDecrease);
    this.ball.setCenterX(this.ball.getCenterX() - speed * time * currentXDirection +speedDecrease*currentXDirection);
    if (this.ball.getCenterX()> sceneWidth|| this.ball.getCenterX() < 0) {

      changeXDirection(time);
    }
    if (this.ball.getCenterY() < 0) {
      changeYDirection(time);
    }
    if (this.ball.getCenterY() > sceneHeight) {
      reset();
      p.decreaseLives();
    }
    if (p.getBounds().intersects(this.ball.getBoundsInLocal())) { //local or parent???
      changeYDirection(time);
    }

  }

  public void decreaseSpeed(){
    this.speed = this.speed*0.88;
    this.speedDecrease = 0.2;
  }

  public void start(){
    this.speed = 120;
  }


  public void reset() {
    ball.setCenterX(initialWidth / 2);
    ball.setCenterY(initialHeight / 2);
    this.speed = 0;
  }

  public void changeXDirection(double time) {
    this.currentXDirection = this.currentXDirection * -1;

  }

  public void changeYDirection(double time) {
    this.currentYDirection = this.currentYDirection * -1;

  }

  public double getX() {
    return this.ball.getCenterX();

  }

  public double getY() {
    return this.ball.getCenterY();

  }

  public javafx.geometry.Bounds getBounds() {
    return this.ball.getBoundsInParent();
  }

  /**
   * returns the rectangular object. Can be changed to image in the future
   *
   * @return
   */
  public Circle getObject() {
    return this.ball;

  }
}
