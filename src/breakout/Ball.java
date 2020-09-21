package breakout;

import static java.lang.Math.sqrt;

import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;

public class Ball {

  public static final int PADDLE_EDGE = 15;
  public int BALL_RADIUS;
  private double speed = 480000;
  private Circle ball;
  private Paint ballColor = Color.RED;
  private int currentXDirection = 1;
  private int currentYDirection = 1;
  private int initialWidth;
  private int initialHeight;
  private int sceneWidth;
  private int sceneHeight;
  private double speedDecrease = 0;
  private double speedX = sqrt(speed/2);
  private double speedY = sqrt(speed/2);

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

  public void move(double time) {
    this.ball.setCenterY(this.ball.getCenterY() - speedY * time * currentYDirection+ currentYDirection*speedDecrease);
    this.ball.setCenterX(this.ball.getCenterX() - speedX * time * currentXDirection +speedDecrease*currentXDirection);
  }

  public void decreaseSpeed(){
    this.speedDecrease = 0.02; //need to fix, temporary. was goin backwards when changing speed
  }

  public void start(){
    this.speedX = sqrt(speed/2);
    this.speedY = sqrt(speed/2);
  }


  public void reset() {
    ball.setCenterX(initialWidth / 2);
    ball.setCenterY(initialHeight / 2);
    this.speedX = 0;
    this.speedY = 0;
  }

  public double getSpeedX(){
    return this.speedX;
  }
  public double getSpeedY(){
    return this.speedY;
  }

  public void reinitializeSpeed(){
    speedX = sqrt(speed/2);
    speedY = sqrt(speed/2);
  }
  public void changeSpeedY(double change){
    this.speedY = this.speedY + change;
    this.speedX = sqrt(this.speed-this.speedY*this.speedY);
  }
  public void changeSpeedX(double change){
    this.speedX = this.speedX + change;
    this.speedY = sqrt(this.speed-this.speedX*this.speedX);
  }

  public void changeXDirection() {
    this.currentXDirection = this.currentXDirection * -1;

  }

  public void changeYDirection() {
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
