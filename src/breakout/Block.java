package breakout;

public class Block {

  private String blockType;
  private int column;
  private int row;
  private int numRows;
  private int numColumns;

  public Block(String blockType, int row, int column, int numRows, int numColumns) {
    this.blockType = blockType;
    this.row = row;
    this.column = column;
    this.numRows = numRows;
    this.numColumns = numColumns;
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
}
