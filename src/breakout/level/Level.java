package breakout.level;

import breakout.Store;
import breakout.blocks.AbstractBlock;
import breakout.blocks.BasicBlock;
import breakout.blocks.BossBlock;
import breakout.blocks.ShieldBlock;
import breakout.powerups.PowerUp;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import javafx.scene.Node;
import javafx.scene.layout.BorderPane;
import javafx.scene.shape.Circle;

/**
 * This class represents a configuration of blocks that has some special mechanic. Block config is
 * read from the file on the constructor.
 */
public abstract class Level {

  private final static int MAX_CYCLES = 1000;
  private final List<AbstractBlock> blockList = new ArrayList<>();
  private final String levelId;
  protected int cycleCount = 0;

  public Level(String fileSource) throws IOException, URISyntaxException {
    this.levelId = fileSource;
    Path pathToFile = Paths.get(ClassLoader.getSystemResource(fileSource).toURI());
    List<String> allLines = Files.readAllLines(pathToFile);
    convertLinesToBlocks(allLines);
  }

  /**
   * Called in step method to make a level do its special mechanic periodically.
   */
  public void updateLevel() {
    cycleCount++;
    if (isTimeToUpdate()) {
      doLevelMechanic();
      cycleCount = 0;
    }
  }

  protected abstract void doLevelMechanic();

  /**
   * Used to get the list of blocks currently in the level.
   *
   * @return a list of AbstractBlock objects currently in the level.
   */
  public List<AbstractBlock> getBlockList() {
    return this.blockList;
  }

  /**
   * Calls the .update() method on every AbstractBlock in the level. This will mark them as broken
   * if they are out of hits, cycle shields, or perform any other necessary functions
   */
  public void updateAllBlocks() {
    blockList.stream().forEach(AbstractBlock::update);
  }

  /**
   * Returns a list of the nodes corresponding to AbstractBlock objects curently in the level.
   *
   * @return a list of nodes representing blocks in the level
   */
  public List<Node> getObjectsToDraw() {
    List<Node> objectsToDraw = this.blockList.stream()
        .map(AbstractBlock::getDisplayObject)
        .collect(Collectors.toList());
    if (objectsToDraw == null) {
      return Collections.EMPTY_LIST;
    }
    return objectsToDraw;
  }

  private boolean isTimeToUpdate() {
    return cycleCount > MAX_CYCLES;
  }


  /**
   * Final method called on Level each step. Removes the display objects for each broken block from
   * the given group, then remvoes the blocks from the list.
   *
   * @param group The collection of nodes to remove display objects from
   */
  public void removeBrokenBlocksFromGroup(BorderPane group) {
    List<AbstractBlock> blocksToRemove = getBrokenBlocks();
    List<Node> nodesToRemove = blocksToRemove.stream()
        .map(AbstractBlock::getDisplayObject)
        .collect(Collectors.toList());
    group.getChildren().removeAll(nodesToRemove);
    removeBrokenBlocks();
  }

  /**
   * Called to add the number of broken blocks to the store's score.
   *
   * @param store the store to give points to.
   */
  public void addScoreToStore(Store store) {
    List<AbstractBlock> brokenBlocks = getBrokenBlocks();
    store.addToCurrentScore(brokenBlocks.size());
  }

  /**
   * Called from game step function to spawn powerups (add them to list of current powerups) at each
   * broken block and add their display objects to the given group
   *
   * @param group           a collection of nodes to add display objects to
   * @param currentPowerUps a list of current powerups to add newly spawned ones to
   */
  public void spawnPowerUps(BorderPane group, List<PowerUp> currentPowerUps) {
    List<AbstractBlock> brokenBlocks = getBrokenBlocks();
    List<PowerUp> powerUps = new ArrayList<>();
    for (AbstractBlock each : brokenBlocks) {
      if (each.containsPowerUp()) {
        powerUps.add(each.spawnPowerUp());
      }
    }
    List<Circle> powerUpNodes = powerUps.stream().map(PowerUp::getDisplayCircle)
        .collect(Collectors.toList());
    currentPowerUps.addAll(powerUps);
    group.getChildren().addAll(powerUpNodes);
  }

  private List<AbstractBlock> getBrokenBlocks() {
    return this.getBlockList().stream()
        .filter(AbstractBlock::isBroken).collect(
            Collectors.toList());
  }

  private void removeBrokenBlocks() {
    List<AbstractBlock> blocksToRemove = getBrokenBlocks();
    this.getBlockList().removeAll(blocksToRemove);
  }

  private void convertLinesToBlocks(List<String> allLines) {
    for (int row = 0; row < allLines.size(); row++) {
      String line = allLines.get(row);
      String[] blockPositions = line.split(",");
      List<String> blockPositionsAsList = Arrays.asList(blockPositions);
      convertSingleLineToRow(blockPositionsAsList, row, allLines.size());
    }
  }

  private void convertSingleLineToRow(List<String> blockPositionsInRow, int row, int numberRows) {
    for (int column = 0; column < blockPositionsInRow.size(); column++) {
      String blockType = blockPositionsInRow.get(column);
      AbstractBlock thisBlock = createBlockOfCorrectType(blockType, row, column, numberRows,
          blockPositionsInRow.size());
      if (thisBlock != null) {
        blockList.add(thisBlock);
      }
    }
  }

  private AbstractBlock createBlockOfCorrectType(String blockType, int row, int column,
      int numberRows, int numberColumns) {
    if (blockType.equals("1")) {
      return new BasicBlock(row, column, numberRows, numberColumns);
    }
    if (blockType.equals("S")) {
      return new ShieldBlock(row, column, numberRows, numberColumns);
    }
    if (blockType.equals("B")) {
      return new BossBlock(row, column, numberRows, numberColumns);
    }
    return null;
  }

  /**
   * Used for testing to compare two levels by their ID
   *
   * @return the string used to create the levle, their "ID"
   */
  public String getLevelId() {
    return levelId;
  }
}
