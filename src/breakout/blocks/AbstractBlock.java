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

  /**
   * Called when the block is hit by the ball.
   */
  public abstract void hit();

  /**
   * Called every step to update the block's status as needed
   */
  public abstract void update();

  /**
   * Used to get the node used to display the block on screen
   * @return a Node displayed by JavaFX.
   */
  public abstract Node getDisplayObject();

  /**
   * Called on destruction of the block to determine if it has a powerUp
   * @return true if the block should drop a powerup.
   */
  public boolean containsPowerUp() {
    return (Math.random() * 100) < POWERUP_CHANCE;
  }

  /**
   * used to get column this block is in
   * @return the int of column this block is in
   */
  public int getColumn() {
    return this.column;
  }

  /**
   * used to get row this block is in
   * @return the int of row this block is in
   */
  public int getRow() {
    return this.row;
  }

  /**
   * used to get the number of rows on the level this block is in.
   * The number of rows is used to determine height of the block.
   * @return the int of number of rows on the level
   */
  public int getNumRows() {
    return this.numRows;
  }

  /**
   * used to get the number of columns on the level this block is in.
   * The number of columns is used to determine width of the block.
   * @return the number of columns on the level
   */
  public int getNumColumns() {
    return this.numColumns;
  }

  /**
   * Used to check if the block has been broken so far
   * @return true if the block is broken, false else
   */
  public boolean isBroken() {
    return this.isBroken;
  }

  /**
   * Called to break the block regardless of other variables. Used for cheats/powerups
   */
  public void breakBlock() {
    this.isBroken = true;
  }

  /**
   * Called to spawn a powerup from this block. The list of available powerups here should be
   * updated if new powerups are added.
   * @return the powerup chosen randomly from the list of available powerups.
   */
  public PowerUp spawnPowerUp() {
    Node node = getDisplayObject();
    List<PowerUp> availablePowerUps = List.of(new DecreaseBallSpeedPowerUp(node),
        new IncreasePaddleSizePowerUp(node),
        new IncreasePaddleSpeedPowerUp(node));
    int randomNumber = (int) (Math.random() * (availablePowerUps.size()));
    return availablePowerUps.get(randomNumber);
  }


}
