package breakout;

import static org.junit.jupiter.api.Assertions.assertEquals;

import breakout.blocks.AbstractBlock;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import javafx.scene.Node;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import org.junit.jupiter.api.Test;

public class LevelTest {

  @Test
  public void readsSimpleFileTest() throws IOException, URISyntaxException {
    final Level testReader = new Level("test.txt");
    final List<AbstractBlock> blocksFromReader = testReader.getBlockList();
    AbstractBlock block = blocksFromReader.get(0);
    assertEquals(0, block.getRow());
    assertEquals(1, block.getColumn());
    assertEquals(2, block.getNumRows());
    assertEquals(2, block.getNumColumns());
  }

  @Test
  public void testGetObjectsToDraw() throws IOException, URISyntaxException {
    final Level testReader = new Level("test.txt");
    final List<Rectangle> rectanglesToDraw = new ArrayList<>();
    for (Node node : testReader.getObjectsToDraw()) {
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
}
