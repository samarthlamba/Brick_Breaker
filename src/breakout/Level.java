package breakout;

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

  private final List<Block> blockList = new ArrayList<>();

  public Level(String fileSource) throws IOException, URISyntaxException {
    Path pathToFile = Paths.get(ClassLoader.getSystemResource(fileSource).toURI());
    List<String> allLines = Files.readAllLines(pathToFile);
    convertLinesToBlocks(allLines);
  }

  public List<Block> getBlockList() {
    return this.blockList;
  }

  public List<Rectangle> getBlockObjectsToDraw() {
    return this.blockList.stream()
        .filter(block -> !block.getBlockType().equals("0"))
        .map(block -> block.getObject()).collect(Collectors.toList());
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
      Block thisBlock = new Block(blockType, row, column, numberRows, blockPositionsInRow.size());
      blockList.add(thisBlock);
    }
  }
}
