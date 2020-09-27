package breakout.powerups;

import breakout.Ball;
import breakout.Paddle;
import javafx.geometry.Bounds;
import javafx.scene.Node;

import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;

public abstract class PowerUp {

  private final Circle displayCircle;
  private final int dropSpeed = 1;
  public PowerUp(Node blockSpawnedFrom){
    Bounds blockBounds = blockSpawnedFrom.getBoundsInLocal();
    this.displayCircle = new Circle(blockBounds.getCenterX(), blockBounds.getCenterY(), blockBounds.getWidth()/10);///block.getHeight());
    this.displayCircle.setFill(getFill());
  }

  public void move(){
    this.displayCircle.setCenterY(this.displayCircle.getCenterY()+dropSpeed);
  }

  public abstract void doPowerUp(Paddle paddle, Ball ball);

  abstract Paint getFill() ;

  public Circle getDisplayCircle() {
    return this.displayCircle;
  }
}
