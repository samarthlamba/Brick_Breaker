package breakout;

import static org.junit.jupiter.api.Assertions.assertEquals;

import breakout.blocks.BasicBlock;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.List;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import org.junit.jupiter.api.Test;

public class LevelTest {

  @Test
  public void readsSimpleFileTest() throws IOException, URISyntaxException {
    final Level testReader = new Level("test.txt");
    final List<BasicBlock> blocksFromReader = testReader.getBasicBlockList();
    BasicBlock block = blocksFromReader.get(0);
    assertEquals(0, block.getRow());
    assertEquals(1, block.getColumn());
    assertEquals(2, block.getNumRows());
    assertEquals(2, block.getNumColumns());
  }

  @Test
  public void testGetObjectsToDraw() throws IOException, URISyntaxException {
    final Level testReader = new Level("test.txt");
    final List<Rectangle> rectanglesToDraw = testReader.getRectanglesToDraw();
    assertEquals(3, rectanglesToDraw.size());
    assertEquals(Color.BROWN,rectanglesToDraw.get(0).getFill());
    assertEquals(Color.BLUE,rectanglesToDraw.get(1).getFill());
    assertEquals(Color.RED,rectanglesToDraw.get(2).getFill());
  }
}
