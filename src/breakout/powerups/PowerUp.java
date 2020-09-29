package breakout.powerups;

import breakout.Ball;
import breakout.Paddle;
import javafx.geometry.Bounds;
import javafx.scene.Node;

import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;

/**
 * This class is an abstract powerup. All powerups are circles with a color that drop down the screen
 * and do something on a game object (ball or paddle, currently).
 */
public abstract class PowerUp {

  private final Circle displayCircle;
  private final int dropSpeed = 1; //we left this due to the extendability of this in the future
  public PowerUp(Node blockSpawnedFrom){
    Bounds blockBounds = blockSpawnedFrom.getBoundsInLocal();
    this.displayCircle = new Circle(blockBounds.getCenterX(), blockBounds.getCenterY(), blockBounds.getWidth()/10);///block.getHeight());
    this.displayCircle.setFill(getFill());
  }

  /**
   * Used to drop the powerup from its spawn location to the bottom of the screen
   */
  public void move(){
    this.displayCircle.setCenterY(this.displayCircle.getCenterY()+dropSpeed);
  }

  /**
   * Performs the powerup on the requried object. If a powerup is added that acts on an object other
   * than the paddle or ball, it should be added to the arguments.
   * @param paddle a Paddle object from the game to be powered up
   * @param ball a ball object from the game to be powered up
   */
  public abstract void doPowerUp(Paddle paddle, Ball ball);

  abstract Paint getFill() ;

  /**
   * Used to get the circle representing the powerup in JavaFX.
   * @return a Circle node representing the powerup.
   */
  public Circle getDisplayCircle() {
    return this.displayCircle;
  }
}
