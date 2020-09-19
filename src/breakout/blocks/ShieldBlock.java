package breakout.blocks;

import java.util.HashMap;
import java.util.Map;
import javafx.scene.paint.Color;

public class ShieldBlock extends BasicBlock{
  private static final int NUM_CYCLES = 1000;
  private boolean isShielded;
  private int cycleCount = 0;
  private static final Map<Boolean,Color> strokeColorMap = new HashMap<>();

  public ShieldBlock(int row, int column, int numRows, int numColumns) {
    super(row, column, numRows, numColumns);
    strokeColorMap.put(true,Color.GREEN);
    strokeColorMap.put(false,Color.NAVY);
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

  public void cycleShields(){
    cycleCount++;
    if (cycleCount > NUM_CYCLES){
      isShielded = !isShielded;
      cycleCount = 0;
      this.setColors(Color.BLUE,strokeColorMap.get(isShielded));
    }
  }
}
