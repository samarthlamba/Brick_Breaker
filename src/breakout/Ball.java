package breakout;

import static java.lang.Math.sqrt;

import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;

import java.util.Random;

public class Ball {

  public static final int PADDLE_EDGE = 15;
  public int BALL_RADIUS;
  private double speed = 480000;
  private Circle ballNode;
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
    ballNode = new Circle(width / 2, height / 2, BALL_RADIUS);
    ballNode.setId("ball");
    ballNode.setFill(ballColor);

  }

  public void move(double time) {
    this.ballNode.setCenterY(this.ballNode.getCenterY() - speedY * time * currentYDirection+ currentYDirection*speedDecrease);
    this.ballNode.setCenterX(this.ballNode.getCenterX() - speedX * time * currentXDirection +speedDecrease*currentXDirection);
  }

  public void decreaseSpeed(){
    this.speedX = this.speedX*0.95; //need to fix, temporary. was goin backwards when changing speed
    this.speedY = this.speedY*0.95;
  }

  public void start(){
    this.speedX = sqrt(speed/2);
    this.speedY = sqrt(speed/2);
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
    return this.ballNode.getCenterX();

  }

  public double getY() {
    return this.ballNode.getCenterY();

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
