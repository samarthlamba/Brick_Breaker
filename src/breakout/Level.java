package breakout;

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
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.layout.BorderPane;
import javafx.scene.shape.Circle;

public class Level {

  private final List<AbstractBlock> blockList = new ArrayList<>();
  private final String levelId;

  public Level(String fileSource) throws IOException, URISyntaxException {
    this.levelId = fileSource;
    Path pathToFile = Paths.get(ClassLoader.getSystemResource(fileSource).toURI());
    List<String> allLines = Files.readAllLines(pathToFile);
    convertLinesToBlocks(allLines);
  }

  public List<AbstractBlock> getBlockList() {
    return this.blockList;
  }

  public void updateAllBlocks() {
    blockList.stream().forEach(block -> block.update());
  }

  public List<Node> getObjectsToDraw() {
    List<Node> objectsToDraw =  this.blockList.stream()
        .map(block -> block.getDisplayObject())
        .collect(Collectors.toList());
    if(objectsToDraw==null){
      return Collections.EMPTY_LIST;
    }
    return objectsToDraw;
  }

  public void removeBrokenBlocksFromGroup(BorderPane group, Store store){
    List<AbstractBlock> blocksToRemove = getBrokenBlocks();
    List<Node> nodesToRemove = blocksToRemove.stream()
        .map(block -> block.getDisplayObject())
        .collect(Collectors.toList());
    group.getChildren().removeAll(nodesToRemove);
    removeBrokenBlocks();
    store.addToCurrentScore(blocksToRemove.size());
  }

  public void spawnPowerUps(BorderPane group,List<PowerUp> currentPowerUps) {
    List<AbstractBlock> brokenBlocks = getBrokenBlocks();
    List<PowerUp> powerUps = new ArrayList<>();
    for (AbstractBlock each: brokenBlocks) {
      if(each.containsPowerUp()) {
        powerUps.add(each.spawnPowerUp());
      }
    }
    List<Circle> powerUpNodes = powerUps.stream().map(powerUp -> powerUp.getDisplayCircle()).collect(Collectors.toList());
    currentPowerUps.addAll(powerUps);
    group.getChildren().addAll(powerUpNodes);
  }

  private List<AbstractBlock> getBrokenBlocks() {
    List<AbstractBlock> brokenBlocks = this.getBlockList().stream()
        .filter(block -> block.isBroken()).collect(
            Collectors.toList());
    return brokenBlocks;
  }
  private void removeBrokenBlocks(){
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
      AbstractBlock thisBlock = createBlockOfCorrectType(blockType,row,column,numberRows,blockPositionsInRow.size());
      if(thisBlock != null) {
        blockList.add(thisBlock);
      }
    }
  }

  private AbstractBlock createBlockOfCorrectType(String blockType, int row, int column, int numberRows, int numberColumns) {
    if(blockType.equals("1")){
      return new BasicBlock(row, column, numberRows, numberColumns);
    }
    if(blockType.equals("S")){
      return new ShieldBlock(row,column,numberColumns,numberColumns);
    }
    if(blockType.equals("B")){
      return new BossBlock(row, column, numberRows, numberColumns);
    }
    return null;
  }

  public String getLevelId() {
    return levelId;
  }
}
