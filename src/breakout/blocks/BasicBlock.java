package breakout.blocks;

import breakout.Game;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class BasicBlock extends AbstractBlock {

  private Rectangle displayObject;
  private final int blockHeight;

  public BasicBlock(int row, int column, int numRows, int numColumns) {
    super(row, column, numRows, numColumns);
    final int blockWidth = Game.WIDTH / numColumns;
    blockHeight = Game.HEIGHT / (2 * numRows);
    this.displayObject = new Rectangle(column * blockWidth, row * blockHeight,
        blockWidth, blockHeight);
    this.setColors(Color.YELLOW,Color.BLACK);
  }

  @Override
  public void hit() {
    this.breakBlock();
  }

  public void setColors(Color fill, Color stroke) {
    this.displayObject.setFill(fill);
    this.displayObject.setStroke(stroke);
  }

  public Rectangle getDisplayObject() {
    return this.displayObject;
  }

  public int getHeight() {
    return this.blockHeight;
  }
}
