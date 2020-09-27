package breakout;

import static java.lang.Math.sqrt;

import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;

import java.util.Random;

public class Ball {

  public final int ballRadius;
  private static final double SPEED = 480000;
  private final Circle ballNode;
  private int currentXDirection = 1;
  private int currentYDirection = 1;
  private final int initialWidth;
  private final int initialHeight;
  private double speedX = sqrt(SPEED /2);
  private double speedY = sqrt(SPEED /2);

  /**
   * Initialized based on width of screen and height. Creates the circular ball
   *
   * @param width
   * @param height
   */
  public Ball(int width, int height) {
    ballRadius = width / 60;
    initialWidth = width;
    initialHeight = height;
    ballNode = new Circle(width / 2, height / 2, ballRadius);
    ballNode.setId("ball");
    Paint ballColor = Color.RED;
    ballNode.setFill(ballColor);

  }

  /**
   * Called every step to move the ball by its speed
   * @param time How long a step to take
   */
  public void move(double time) {
    double speedDecrease = 0;
    this.ballNode.setCenterY(this.ballNode.getCenterY() - speedY * time * currentYDirection+ currentYDirection* speedDecrease);
    this.ballNode.setCenterX(this.ballNode.getCenterX() - speedX * time * currentXDirection +
        speedDecrease *currentXDirection);
  }

  /**
   * Used to decrease the ball's speed. Used by powerups and cheat keys.
   */
  public void decreaseSpeed(){
    this.speedX = this.speedX*0.95; //need to fix, temporary. was goin backwards when changing speed
    this.speedY = this.speedY*0.95;
  }

  public void start(){
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
  /**
   * gets the ball's speed in the y direction
   * @return the double of the ball's speed y component
   */
  public double getSpeedY(){
    return this.speedY;
  }

  /**
   * Used to set the ball's speed back to its initial value
   */
  public void reinitializeSpeed(){
    speedX = sqrt(SPEED /2);
    speedY = sqrt(SPEED /2);
  }

  /**
   * Used to change the ball's x speed by an amount and its y speed so that its x and y speeds
   * still make up its total speed
   * @param change the double value corresponding to the ball's change in x speed
   */
  public void changeSpeedX(double change) {
    if (this.speedY > this.speedX / 3) {
      this.speedX = this.speedX + change;
      this.speedY = sqrt(this.SPEED - this.speedX * this.speedX);
    }
  }

  /**
   * Used to reverse the ball's x direction
   */
  public void changeXDirection() {
    this.currentXDirection = this.currentXDirection * -1;

  }

  /**
   * Used to reverse the ball's y direction
   */
  public void changeYDirection() {
    this.currentYDirection = this.currentYDirection * -1;

  }

  /**
   * used to get the node representing the ball's center x coordinate
   * @return the center x coordinate of the circle representing the ball.
   */
  public double getX() {
    return this.ballNode.getCenterX();
  }

  /**
   * used to get the bounds of the circle object representing the ball in javaFX
   * @return the bounds in parent of the circle representing the ball.
   */
  public javafx.geometry.Bounds getBounds() {
    return this.ballNode.getBoundsInParent();
  }

  /**
   * used to get the circle object used to display the ball
   * @return the circle object representing the ball in javaFX
   */
  public Circle getObject() {
    return this.ballNode;
  }
}
