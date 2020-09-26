package breakout.blocks;

import breakout.powerups.DecreaseBallSpeedPowerUp;
import breakout.powerups.IncreasePaddleSizePowerUp;
import breakout.powerups.IncreasePaddleSpeedPowerUp;
import breakout.powerups.PowerUp;
import java.util.List;
import javafx.scene.Node;

public abstract class AbstractBlock {

  private int column;
  private int row;
  private int numRows;
  private int numColumns;
  private boolean isBroken;
  private static final int POWERUP_CHANCE = 100;

  public AbstractBlock(int row, int column, int numRows, int numColumns) {
    this.row = row;
    this.column = column;
    this.numRows = numRows;
    this.numColumns = numColumns;
    this.isBroken = false;
  }

  public abstract void hit();

  public abstract void update();

  public abstract Node getDisplayObject();

  public boolean containsPowerUp() {
    final boolean result = (Math.random() * 100) < POWERUP_CHANCE;
    return result;
  }

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

  public void breakBlock(){
    this.isBroken = true;
  }

  public PowerUp spawnPowerUp() {
    Node node = getDisplayObject();
    List<PowerUp> availablePowerUps = List.of(new DecreaseBallSpeedPowerUp(node),
        new IncreasePaddleSizePowerUp(node)
        ,new IncreasePaddleSpeedPowerUp(node));
    int randomNumber = (int)(Math.random() * (availablePowerUps.size()));
    return availablePowerUps.get(randomNumber);
  }



}
