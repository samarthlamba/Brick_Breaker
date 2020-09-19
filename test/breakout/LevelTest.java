package breakout;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import breakout.blocks.AbstractBlock;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import javafx.scene.Node;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class LevelTest {
  private Level testLevel;

  @BeforeEach
  public void setup() throws IOException, URISyntaxException {
    testLevel = new Level("test.txt");
  }

  @Test
  public void readsSimpleFileTest() {
    final List<AbstractBlock> blocksFromReader = testLevel.getBlockList();
    AbstractBlock block = blocksFromReader.get(0);
    assertEquals(0, block.getRow());
    assertEquals(1, block.getColumn());
    assertEquals(2, block.getNumRows());
    assertEquals(2, block.getNumColumns());
  }

  @Test
  public void testGetObjectsToDraw() {
    final List<Rectangle> rectanglesToDraw = new ArrayList<>();
    for (Node node : testLevel.getObjectsToDraw()) {
      if (node instanceof Rectangle) {
        Rectangle nodeAsRectangle = (Rectangle) node;
        rectanglesToDraw.add(nodeAsRectangle);
      }
    }
    assertEquals(3, rectanglesToDraw.size());
    assertEquals(Color.BURLYWOOD,rectanglesToDraw.get(0).getFill());
    assertEquals(Color.BLUE,rectanglesToDraw.get(1).getFill());
    assertEquals(Color.CRIMSON,rectanglesToDraw.get(2).getFill());
  }

  @Test
  public void testGetBrokenBlocks() {
    final List<AbstractBlock> blockList = testLevel.getBlockList();
    final int initialSize = blockList.size();
    AbstractBlock basicBlock = testLevel.getBlockList().get(0);
    basicBlock.hit();
    assertTrue(basicBlock.isBroken());
    final List<AbstractBlock> brokenBlocks = testLevel.removeBrokenBlocks();
    assertEquals(1,brokenBlocks.size());
    assertEquals(initialSize-1,blockList.size());
    assertFalse(blockList.contains(basicBlock));
  }
}
