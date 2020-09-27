package breakout;

import static java.lang.Math.abs;

import breakout.blocks.AbstractBlock;
import java.util.List;
import javafx.geometry.BoundingBox;
import javafx.geometry.Bounds;
import javafx.scene.Node;
import javafx.scene.shape.Circle;

public class PhysicsEngine {
  private final Bounds dimensions;
  private final Paddle paddle;
  private List<AbstractBlock> blockList;

  public PhysicsEngine(int width, int height, Paddle paddle, List<AbstractBlock> blockList) {
    this.dimensions = new BoundingBox(0,0,width,height);
    this.paddle = paddle;
    this.blockList = blockList;
  }

  public void ballBounce(Ball ball) {
    checkEdgeCollision(ball);
    checkBlockCollision(ball);
    checkPaddleCollision(ball);
  }

  private AbstractBlock getBlockAtBallPosition(Ball ball){
    for(AbstractBlock eachBlock : this.blockList){
      if(eachBlock.getDisplayObject().getBoundsInLocal().intersects(ball.getBounds())) {
        return eachBlock;
      }
    }
    return null;
  }

  public void setBlockList(Level level) {
    this.blockList = level.getBlockList();
  }

  private void checkPaddleCollision(Ball ball){
    if(collides(ball.getObject(),paddle.getObject()) &&
        ball.getObject().getCenterY() < paddle.getBounds().getMinY()) {

      ball.changeYDirection();
      edgeOfObject(ball, paddle.getObject());
    }
  }
  private void edgeOfObject(Ball ball, Node object){

    Bounds nodeObject = object.getBoundsInLocal();
    double posRelativeToCenterOfObject = abs(nodeObject.getCenterX()- ball.getX());
    if (posRelativeToCenterOfObject>(nodeObject.getWidth()/3)) {
      double normDifference =
          (posRelativeToCenterOfObject - (nodeObject.getWidth() / 3)) / nodeObject.getWidth();
      if (ball.getSpeedX() / 2 < ball.getSpeedY()) {
        ball.changeSpeedX(normDifference * 150);
      }
    }
    else{
       ball.initializeSpeed();
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
    if(atTop(displayObject)){
      ball.changeYDirection();
    }
    if(atRight(displayObject) || atLeft(displayObject)){
      ball.changeXDirection();
    }
    if(atBottom(displayObject)) {
      ball.reset();
      paddle.decreaseLives();
    }
  }

  public boolean collides(Node a, Node b) {
    if (!a.equals(b)) {
      return a.getBoundsInParent().intersects(b.getBoundsInParent());
    }
    return false;
  }

  public boolean atBottom(Node a) {
    Bounds bounds = a.getBoundsInParent();
    return bounds.getMaxY()>=dimensions.getMaxY();
  }
  public boolean atTop(Node a) {
    Bounds bounds = a.getBoundsInParent();
    return bounds.getMinY()<=dimensions.getMinY();
  }
  public boolean atRight(Node a) {
    Bounds bounds = a.getBoundsInParent();
    return bounds.getMaxX()>=dimensions.getMaxX();
  }
  public boolean atLeft(Node a) {
    Bounds bounds = a.getBoundsInParent();
    return bounds.getMinX()<=dimensions.getMinX();
  }

}
