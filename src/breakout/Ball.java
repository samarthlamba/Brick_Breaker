package breakout;

import static java.lang.Math.sqrt;

import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;

import java.util.Random;

public class Ball {

  public final double ballRadius;
  private static final double SPEED = 480000;
  private final Circle ballNode;
  private int currentXDirection = 1;
  private int currentYDirection = 1;
  private final int initialWidth;
  private final int initialHeight;
  private double speedX = sqrt(SPEED /2);
  private double speedY = sqrt(SPEED /2);
  private static final double BALL_RADIUS_MULTIPLIER = 1/60;
  private static final double BALL_SPEED_DECREASE_MULTIPLIER = 0.9;
  private static final int NEGATIVE_DIRECTION = -1;


  /**
   * Initialized based on width of screen and height. Creates the circular ball
   *
   * @param width
   * @param height
   */
  public Ball(int width, int height) {
    ballRadius = width* BALL_RADIUS_MULTIPLIER;
    initialWidth = width;
    initialHeight = height;
    ballNode = new Circle(width / 2, height / 2, ballRadius);
    ballNode.setId("ball");
    Paint ballColor = Color.RED;
    ballNode.setFill(ballColor);

  }

  public void move(double time) {
    this.ballNode.setCenterY(this.ballNode.getCenterY() - speedY * time * currentYDirection);
    this.ballNode.setCenterX(this.ballNode.getCenterX() - speedX * time * currentXDirection);
  }

  public void decreaseSpeed(){
    this.speedX = this.speedX*BALL_SPEED_DECREASE_MULTIPLIER;
    this.speedY = this.speedY*BALL_SPEED_DECREASE_MULTIPLIER;
  }

  public void initializeSpeed(){
    this.speedX = sqrt(SPEED /2);
    this.speedY = sqrt(SPEED /2);
  }


  public void reset() {
    ballNode.setCenterX(initialWidth / 2);
    ballNode.setCenterY(initialHeight / 2);
    this.speedX = 0;
    this.speedY = 0;
  }

  public void randomColor() {
    Random rand = new Random();
    ballNode.setFill(Color.color(rand.nextDouble(),rand.nextDouble(),rand.nextDouble()));
  }

  public double getSpeedX(){
    return this.speedX;
  }
  public double getSpeedY(){
    return this.speedY;
  }


  public void changeSpeedX(double change) {
    if (this.speedY > this.speedX / 3) {
      this.speedX = this.speedX + change;
      this.speedY = sqrt(this.SPEED - this.speedX * this.speedX);
    }
  }

  public void changeXDirection() {
    this.currentXDirection = this.currentXDirection * NEGATIVE_DIRECTION;

  }

  public void changeYDirection() {
    this.currentYDirection = this.currentYDirection * NEGATIVE_DIRECTION;

  }

  public double getX() {
    return this.ballNode.getCenterX();

  }

  public javafx.geometry.Bounds getBounds() {
    return this.ballNode.getBoundsInParent();
  }

  /**
   * returns the rectangular object. Can be changed to image in the future
   *
   * @return
   */
  public Circle getObject() {
    return this.ballNode;

  }
}
