package breakout.blocks;

import breakout.Game;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class BasicBlock extends AbstractBlock {
  private Rectangle displayRectangle;
  
  public BasicBlock(int row, int column, int numRows, int numColumns) {
    super(row, column, numRows, numColumns);
    final int blockWidth = Game.WIDTH / numColumns;
    final int blockHeight = Game.HEIGHT / (2*numRows);
    this.displayRectangle = new Rectangle(column*blockWidth,row*blockHeight,
        blockWidth, blockHeight);
    this.setColors(Color.BROWN,Color.BLACK);
  }

  @Override
  public void hit() {
  }

  public void setColors(Color fill, Color stroke){
    this.displayRectangle.setFill(fill);
    this.displayRectangle.setStroke(stroke);
  }

  public Rectangle getDisplayRectangle(){
    return this.displayRectangle;
  }
}
