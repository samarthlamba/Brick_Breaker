package breakout.blocks;

import javafx.scene.Node;

public abstract class AbstractBlock {

  private int column;
  private int row;
  private int numRows;
  private int numColumns;
  private boolean isBroken;

  public AbstractBlock(int row, int column, int numRows, int numColumns) {
    this.row = row;
    this.column = column;
    this.numRows = numRows;
    this.numColumns = numColumns;
    this.isBroken = false;
  }

  public abstract void hit();

  public abstract Node getDisplayObject();

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

  public boolean isBroken() {
    return this.isBroken;
  }

  void breakBlock(){
    this.isBroken = true;
  }



}
