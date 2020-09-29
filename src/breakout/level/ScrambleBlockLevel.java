package breakout.level;

import breakout.blocks.AbstractBlock;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.List;

public class ScrambleBlockLevel extends Level{

  public ScrambleBlockLevel(String fileSource) throws IOException, URISyntaxException {
    super(fileSource);
  }

  @Override
  protected void doLevelMechanic() {
    List<AbstractBlock> blockList = this.getBlockList();
    if(blockList.size()>1) {
      final double firstXPos = blockList.get(0).getDisplayObjectX();
      final double firstYPos = blockList.get(0).getDisplayObjectY();
      for (int k = 0; k < blockList.size() - 1; k++) {
        AbstractBlock thisBlock = blockList.get(k);
        AbstractBlock nextBlock = blockList.get(k + 1);
        thisBlock.setDisplayObjectX(nextBlock.getDisplayObjectX());
        thisBlock.setDisplayObjectY(nextBlock.getDisplayObjectY());
      }
      AbstractBlock finalBlock = blockList.get(blockList.size() - 1);
      finalBlock.setDisplayObjectX(firstXPos);
      finalBlock.setDisplayObjectY(firstYPos);
    }
  }
}
