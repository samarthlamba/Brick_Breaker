package breakout;

import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class Block {

  private String blockType;
  private int column;
  private int row;
  private int numRows;
  private int numColumns;
  private Rectangle block;

  public Block(String blockType, int row, int column, int numRows, int numColumns) {
    this.blockType = blockType;
    this.row = row;
    this.column = column;
    this.numRows = numRows;
    this.numColumns = numColumns;
    final int blockWidth = Game.WIDTH / numColumns;
    final int blockHeight = Game.HEIGHT / (2*numRows);
    this.block = new Rectangle(column*blockWidth, row*blockHeight,
        blockWidth, blockHeight);
    if(!this.blockType.equals("0")) {
      this.block.setStroke(Color.BLACK);
      this.block.setFill(Color.AQUA);
    }
  }

  public String getBlockType() {
    return blockType;
  }

  public int getColumn() {
    return column;
  }

  public int getRow() {
    return row;
  }

  public int getNumRows() {
    return numRows;
  }

  public int getNumColumns() {
    return numColumns;
  }

  public Rectangle getObject() {
    return this.block;
  }
}
