package breakout.blocks;

public abstract class AbstractBlock {

  private int column;
  private int row;
  private int numRows;
  private int numColumns;

  public AbstractBlock(int row, int column, int numRows, int numColumns) {
    this.row = row;
    this.column = column;
    this.numRows = numRows;
    this.numColumns = numColumns;
  }

  public abstract void hit();

  public int getColumn() {
    return this.column;
  }

  public int getRow() {
    return this.row;
  }

  public int getNumRows() {
    return this.numRows;
  }

  public int getNumColumns() {
    return this.numColumns;
  }
}
