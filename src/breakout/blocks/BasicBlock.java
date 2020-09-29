package breakout.blocks;

import breakout.Game;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

/**
 * Baisc block that takes 1 hit and then is broken. Has a Rectangle for its DisplayObject.
 */
public class BasicBlock extends AbstractBlock {

  private final int blockHeight;
  private Rectangle displayObject;
  private int hitsRemaining;
  private Color fill;
  private Color stroke;

  public BasicBlock(int row, int column, int numRows, int numColumns) {
    super(row, column, numRows, numColumns);
    final int blockWidth = Game.WIDTH / numColumns;
    blockHeight = Game.HEIGHT / (2 * numRows);
    this.displayObject = new Rectangle(column * blockWidth, row * blockHeight,
        blockWidth, blockHeight);
    this.setColors(Color.YELLOW, Color.BLACK);
    this.setHitsRemaining(1);
  }

  @Override
  public double getDisplayObjectX() {
    return this.displayObject.getX();
  }

  @Override
  public void setDisplayObjectX(double xPos) {
    this.displayObject.setX(xPos);
  }

  @Override
  public double getDisplayObjectY() {
    return this.displayObject.getY();
  }

  @Override
  public void setDisplayObjectY(double yPos) {
    this.displayObject.setY(yPos);
  }

  @Override
  public void hit() {
    hitsRemaining--;

    //Color lighterColor = new Color.color(this.fill.getRed(), this.fill.getGreen(), this.fill.getBlue());
    this.setColors(this.fill.darker(), this.stroke);

  }

  @Override
  public void update() {
    if (hitsRemaining < 1) {
      breakBlock();
    }
  }

  /**
   * Used to set the color palette of the block
   *
   * @param fill   the inside of the block
   * @param stroke the border of the block
   */
  public void setColors(Color fill, Color stroke) {
    this.fill = fill;
    this.stroke = stroke;
    this.displayObject.setFill(fill);
    this.displayObject.setStroke(stroke);
  }

  /**
   * Basic blocks display to javaFx with a Rectangle object
   *
   * @return the rectangle used to display this block on JavaFX
   */
  public Rectangle getDisplayObject() {
    return this.displayObject;
  }

  protected void setHitsRemaining(int hitsRemaining) {
    this.hitsRemaining = hitsRemaining;
  }
}
