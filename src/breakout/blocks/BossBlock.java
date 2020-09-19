package breakout.blocks;

import javafx.scene.paint.Color;

public class BossBlock extends BasicBlock {
  private int hitsRemaining;

  public BossBlock(int row, int column, int numRows, int numColumns) {
    super(row, column, numRows, numColumns);
    this.setColors(Color.CRIMSON,Color.BLACK);
    this.hitsRemaining = 5;
  }

  @Override
  public void hit(){
    hitsRemaining -=1;
    if (hitsRemaining<1){
      this.breakBlock();
    }
  }

}
