package breakout;

import static java.lang.Math.abs;

import breakout.blocks.AbstractBlock;
import breakout.level.Level;
import java.util.List;
import javafx.geometry.BoundingBox;
import javafx.geometry.Bounds;
import javafx.scene.Node;
import javafx.scene.shape.Circle;

/**
 * This class is used to detect collision and interaction between various game objects.
 */
public class PhysicsEngine {

  private final Bounds dimensions;
  private final Paddle paddle;
  private List<AbstractBlock> blockList;

  public PhysicsEngine(int width, int height, Paddle paddle, List<AbstractBlock> blockList) {
    this.dimensions = new BoundingBox(0, 0, width, height);
    this.paddle = paddle;
    this.blockList = blockList;
  }

  /**
   * Called in updateBallAndPaddle() to determine if the ball should bounce
   *
   * @param ball the ball moving in the game
   */
  public void ballBounce(Ball ball) {
    checkEdgeCollision(ball);
    checkBlockCollision(ball);
    checkPaddleCollision(ball);
  }

  public void checkForBlocksAtBottom() {
    blockList.stream().forEach(
        block -> {  //we were more familiar with stream and forEach vs stream made little difference so left this error
          if (atBottom(block.getDisplayObject())) {
            paddle.decreaseLives();
          }
        });
  }

  private AbstractBlock getBlockAtBallPosition(Ball ball) {
    for (AbstractBlock eachBlock : this.blockList) {
      if (eachBlock.getDisplayObject().getBoundsInParent().intersects(ball.getBounds())) {
        return eachBlock;
      }
    }
    return null;
  }

  /**
   * Sets the list of blocks the physics engine is detecting collision for
   *
   * @param level the level of the game
   */
  public void setBlockList(Level level) {
    this.blockList = level.getBlockList();
  }

  private void checkPaddleCollision(Ball ball) {
    if (collides(ball.getObject(), paddle.getObject()) &&
        ball.getObject().getCenterY() < paddle.getBounds().getMinY()) {

      ball.changeYDirection();
      edgeOfObject(ball, paddle.getObject());
    }
  }

  private void edgeOfObject(Ball ball, Node object) {

    Bounds nodeObject = object.getBoundsInLocal();
    double posRelativeToCenterOfObject = abs(nodeObject.getCenterX() - ball.getX());
    if (posRelativeToCenterOfObject > (nodeObject.getWidth() / 3)) {
      double normDifference =
          (posRelativeToCenterOfObject - (nodeObject.getWidth() / 3)) / nodeObject.getWidth();
      if (ball.getSpeedX() / 2 < ball.getSpeedY()) {
        ball.changeSpeedX(normDifference * 150);
      }
    } else {
      ball.reinitializeSpeed();
    }
  }

  private void checkBlockCollision(Ball ball) {
    AbstractBlock blockHit = this.getBlockAtBallPosition(ball);
    if (blockHit != null) {
      blockHit.hit();
      ball.changeYDirection();
      edgeOfObject(ball, blockHit.getDisplayObject());
    }
  }

  private void checkEdgeCollision(Ball ball) {
    Circle displayObject = ball.getObject();
    if (atTop(displayObject)) {
      ball.changeYDirection();
    }
    if (atRight(displayObject) || atLeft(displayObject)) {
      ball.changeXDirection();
    }
    if (atBottom(displayObject)) {
      ball.reset();
      paddle.decreaseLives();
    }
  }

  /**
   * Used to determine if two nodes collide
   *
   * @param a a Node object
   * @param b a different node object.
   * @return True if the nodes are different and their bounds in parent intersect, false else.
   */
  public boolean collides(Node a, Node b) {
    if (!a.equals(b)) {
      return a.getBoundsInParent().intersects(b.getBoundsInParent());
    }
    return false;
  }

  /**
   * Used to determine if a node is at the bottom of the screen
   *
   * @param a a node object
   * @return true if the node is at or below the bottom of the screen.
   */
  public boolean atBottom(Node a) {
    Bounds bounds = a.getBoundsInParent();
    return bounds.getMaxY() >= dimensions.getMaxY();
  }

  private boolean atTop(Node a) {
    Bounds bounds = a.getBoundsInParent();
    return bounds.getMinY() <= dimensions.getMinY();
  }

  private boolean atRight(Node a) {
    Bounds bounds = a.getBoundsInParent();
    return bounds.getMaxX() >= dimensions.getMaxX();
  }

  private boolean atLeft(Node a) {
    Bounds bounds = a.getBoundsInParent();
    return bounds.getMinX() <= dimensions.getMinX();
  }
}
