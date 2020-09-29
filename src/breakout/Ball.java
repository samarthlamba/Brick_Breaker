package breakout;

import static java.lang.Math.sqrt;

import java.util.Random;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;

/**
 * This class is the main game ball that bounces around the screen breaking blocks
 */
public class Ball {

  private static final double SPEED = 480000;
  private static final double SPEED_DECREMENT = .95;
  private static final int RADIUS_RATIO = 60;
  private final static double TWO = 2.0;
  private final static int CHANGE_DIRECTION = -1;
  private final static int SPEED_MARGINS = 3;
  private final static double THREE = 3;
  private final static double FOURTH = 1 / 4.0;
  private final static double ZERO_SPEED = 0;
  private final Circle ballNode;
  private final int gameWidth;
  private final int gameHeight;
  private int currentXDirection = 1;
  private int currentYDirection = 1;
  private double speedX = sqrt(SPEED / 2);
  private double speedY = sqrt(SPEED / 2);

  /**
   * Initialized based on width of screen and height. Creates the circular ball
   *
   * @param width  width of the scene
   * @param height height of the game scene
   */
  public Ball(int width, int height) {
    int ballRadius = width / RADIUS_RATIO;
    gameWidth = width;
    gameHeight = height;
    ballNode = new Circle(width / TWO, height * THREE * FOURTH, ballRadius);
    ballNode.setId("ball");
    Paint ballColor = Color.RED;
    ballNode.setFill(ballColor);

  }

  /**
   * Called every step to move the ball by its speed
   *
   * @param time How long a step to take
   */
  public void move(double time) {
    double speedDecrease = ZERO_SPEED;
    this.ballNode.setCenterY(this.ballNode.getCenterY() - speedY * time * currentYDirection
        + currentYDirection * speedDecrease);
    this.ballNode.setCenterX(this.ballNode.getCenterX() - speedX * time * currentXDirection +
        speedDecrease * currentXDirection);
  }

  /**
   * Used to decrease the ball's speed. Used by powerups and cheat keys.
   */
  public void decreaseSpeed() {
    this.speedX = this.speedX * SPEED_DECREMENT;
    this.speedY = this.speedY * SPEED_DECREMENT;
  }

  public void reset() {
    ballNode.setCenterX(gameWidth / TWO);
    ballNode.setCenterY(gameHeight * THREE * FOURTH);
    this.speedX = ZERO_SPEED;
    this.speedY = ZERO_SPEED;
  }

  public void randomColor() {
    Random rand = new Random();
    ballNode.setFill(Color.color(rand.nextDouble(), rand.nextDouble(), rand.nextDouble()));
  }

  public double getSpeedX() {
    return this.speedX;
  }

  /**
   * gets the ball's speed in the y direction
   *
   * @return the double of the ball's speed y component
   */
  public double getSpeedY() {
    return this.speedY;
  }

  /**
   * Used to set the ball's speed back to its initial value
   */
  public void reinitializeSpeed() {
    speedX = sqrt(SPEED / TWO);
    speedY = sqrt(SPEED / TWO);
  }

  /**
   * Used to change the ball's x speed by an amount and its y speed so that its x and y speeds still
   * make up its total speed
   *
   * @param change the double value corresponding to the ball's change in x speed
   */
  public void changeSpeedX(double change) {
    if (this.speedY > this.speedX / SPEED_MARGINS) {
      this.speedX = this.speedX + change;
      this.speedY = sqrt(SPEED - this.speedX * this.speedX);
    }
  }

  /**
   * Used to reverse the ball's x direction
   */
  public void changeXDirection() {
    this.currentXDirection = this.currentXDirection * CHANGE_DIRECTION;

  }

  /**
   * Used to reverse the ball's y direction
   */
  public void changeYDirection() {
    this.currentYDirection = this.currentYDirection * CHANGE_DIRECTION;

  }

  /**
   * used to get the node representing the ball's center x coordinate
   *
   * @return the center x coordinate of the circle representing the ball.
   */
  public double getX() {
    return this.ballNode.getCenterX();
  }

  /**
   * used to get the bounds of the circle object representing the ball in javaFX
   *
   * @return the bounds in parent of the circle representing the ball.
   */
  public javafx.geometry.Bounds getBounds() {
    return this.ballNode.getBoundsInParent();
  }

  /**
   * used to get the circle object used to display the ball
   *
   * @return the circle object representing the ball in javaFX
   */
  public Circle getObject() {
    return this.ballNode;
  }
}
