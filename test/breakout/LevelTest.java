package breakout;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.List;
import javafx.scene.shape.Rectangle;
import org.junit.jupiter.api.Test;

public class LevelTest {

  @Test
  public void readsSimpleFileTest() throws IOException, URISyntaxException {
    final Level testReader = new Level("test.txt");
    final List<Block> blocksFromReader = testReader.getBlockList();
    Block block = blocksFromReader.get(2);
    assertEquals("1", block.getBlockType());
    assertEquals(1, block.getRow());
    assertEquals(0, block.getColumn());
    assertEquals(2, block.getNumRows());
    assertEquals(2, block.getNumColumns());
  }

  @Test
  public void testGetObjectsToDraw() throws IOException, URISyntaxException {
    final Level testReader = new Level("test.txt");
    final List<Rectangle> nonAirBlocks = testReader.getBlockObjectsToDraw();
    assertEquals(3, nonAirBlocks.size());
  }
}
