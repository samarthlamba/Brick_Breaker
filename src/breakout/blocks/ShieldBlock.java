package breakout.blocks;

import javafx.scene.paint.Color;

public class ShieldBlock extends BasicBlock{

  public ShieldBlock(int row, int column, int numRows, int numColumns) {
    super(row, column, numRows, numColumns);
    this.setColors(Color.BLUE,Color.BLACK);
  }
}
