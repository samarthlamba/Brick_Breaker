package breakout;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class BlockReader {

  private final List<Block> blockList = new ArrayList<>();

  public BlockReader(String fileSource) throws IOException, URISyntaxException {
    Path pathToFile = Paths.get(ClassLoader.getSystemResource(fileSource).toURI());
    List<String> allLines = Files.readAllLines(pathToFile);
    convertLinesToBlocks(allLines);
  }

  public List<Block> getBlockList() {
    return this.blockList;
  }

  private void convertLinesToBlocks(List<String> allLines) {
    allLines.stream().forEach(line -> {
      final int row = allLines.indexOf(line);
      String[] blockPositions = line.split(",");
      List<String> blockPositionsAsList = Arrays.asList(blockPositions);
      convertSingleLineToRow(blockPositionsAsList, row, allLines.size());
    });
  }

  private void convertSingleLineToRow(List<String> blockPositionsInRow, int row, int numberRows) {
    blockPositionsInRow.stream().forEach(blockType -> {
      final int column = blockPositionsInRow.indexOf(blockType);
      Block thisBlock = new Block(blockType, row, column, numberRows, blockPositionsInRow.size());
      blockList.add(thisBlock);
    });
  }
}
