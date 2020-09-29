package breakout.blocks;

import javafx.scene.paint.Color;

/**
 * Variation of basic block that is slightly tougher, requiring only 3 hits.
 */
public class BossBlock extends BasicBlock {

  public BossBlock(int row, int column, int numRows, int numColumns) {
    super(row, column, numRows, numColumns);
    this.setColors(Color.CRIMSON,Color.BLACK);
    this.setHitsRemaining(3);
  }
}
