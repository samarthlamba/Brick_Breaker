package breakout;

import breakout.blocks.AbstractBlock;
import breakout.blocks.BasicBlock;
import breakout.blocks.BossBlock;
import breakout.blocks.ShieldBlock;
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

public class Level {

  private final List<AbstractBlock> blockList = new ArrayList<>();

  public Level(String fileSource) throws IOException, URISyntaxException {
    Path pathToFile = Paths.get(ClassLoader.getSystemResource(fileSource).toURI());
    List<String> allLines = Files.readAllLines(pathToFile);
    convertLinesToBlocks(allLines);
  }

  public List<AbstractBlock> getBlockList() {
    return this.blockList;
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

  public AbstractBlock getBlockAtBallPosition(Ball ball){
    for(AbstractBlock eachBlock : this.getBlockList()){
      if(eachBlock.getDisplayObject().getBoundsInLocal().intersects(ball.getBounds())) {
        return eachBlock;
      }
    }
    return null;
  }

  public void cycleAllShieldBlocks() {
    List<ShieldBlock> shieldBlocks = this.getBlockList().stream()
        .filter(block -> block instanceof ShieldBlock)
        .map(block -> (ShieldBlock) block)
        .collect(Collectors.toList());
    shieldBlocks.stream().forEach(shieldBlock -> shieldBlock.cycleShields());
  }
  public List<AbstractBlock> removeBrokenBlocks(){
    List<AbstractBlock> brokenBlocks = this.getBlockList().stream()
        .filter(block -> block.isBroken()).collect(
        Collectors.toList());
    this.blockList.removeAll(brokenBlocks);
    return brokenBlocks;
  }

  public void cycleAllShieldBlocks() {
    List<ShieldBlock> shieldBlocks = this.getBlockList().stream()
        .filter(block -> block instanceof ShieldBlock)
        .map(block -> (ShieldBlock) block)
        .collect(Collectors.toList());
    shieldBlocks.stream().forEach(shieldBlock -> shieldBlock.cycleShields());
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
}
