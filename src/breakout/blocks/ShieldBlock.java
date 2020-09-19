package breakout.blocks;

import javafx.scene.paint.Color;

public class ShieldBlock extends BasicBlock{
  private boolean isShielded;

  public ShieldBlock(int row, int column, int numRows, int numColumns) {
    super(row, column, numRows, numColumns);
    this.setColors(Color.BLUE,Color.GREEN);
    this.getDisplayObject().setStrokeWidth(5.00);
    this.isShielded = true;
  }

  @Override
  public void hit(){
    if(!isShielded){
      this.breakBlock();
    }
  }

}
