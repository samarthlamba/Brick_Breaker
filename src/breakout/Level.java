package breakout;

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
import java.util.List;
import java.util.stream.Collectors;
import javafx.scene.shape.Rectangle;

public class Level {

  private final List<BasicBlock> basicBlockList = new ArrayList<>();

  public Level(String fileSource) throws IOException, URISyntaxException {
    Path pathToFile = Paths.get(ClassLoader.getSystemResource(fileSource).toURI());
    List<String> allLines = Files.readAllLines(pathToFile);
    convertLinesToBlocks(allLines);
  }

  public List<BasicBlock> getBasicBlockList() {
    return this.basicBlockList;
  }

  public List<Rectangle> getRectanglesToDraw() {
    return this.basicBlockList.stream()
        .map(block -> block.getDisplayRectangle())
        .collect(Collectors.toList());
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
      BasicBlock thisBlock = createBlockOfCorrectType(blockType,row,column,numberRows,blockPositionsInRow.size());
      if(thisBlock != null) {
        basicBlockList.add(thisBlock);
      }
    }
  }

  private BasicBlock createBlockOfCorrectType(String blockType, int row, int column, int numberRows, int numberColumns) {
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
