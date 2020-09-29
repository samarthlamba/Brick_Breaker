package breakout;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import breakout.blocks.AbstractBlock;
import breakout.blocks.ShieldBlock;
import breakout.level.BasicLevel;
import breakout.level.DescendLevel;
import breakout.level.Level;
import breakout.level.ScrambleBlockLevel;
import breakout.powerups.PowerUp;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import javafx.scene.Node;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class LevelTest {
  private Level testLevel;

  @BeforeEach
  public void setup() throws IOException, URISyntaxException {
    testLevel = new BasicLevel("test.txt");
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
    assertEquals(Color.YELLOW,rectanglesToDraw.get(0).getFill());
    assertEquals(Color.BLUE,rectanglesToDraw.get(1).getFill());
    assertEquals(Color.CRIMSON,rectanglesToDraw.get(2).getFill());
  }

  @Test
  public void testRemoveBrokenBlocksFromGroup() {
    final List<AbstractBlock> blockList = testLevel.getBlockList();
    final int initialSize = blockList.size();
    AbstractBlock basicBlock = testLevel.getBlockList().get(0);

    BorderPane group = new BorderPane();
    group.getChildren().add(basicBlock.getDisplayObject());
    basicBlock.hit();
    basicBlock.update();
    assertTrue(basicBlock.isBroken());
    assertEquals(1,group.getChildren().size());

    testLevel.removeBrokenBlocksFromGroup(group);

    assertEquals(0,group.getChildren().size());
    assertEquals(initialSize-1,blockList.size());
    assertFalse(blockList.contains(basicBlock));
    assertFalse(group.getChildren().contains(basicBlock.getDisplayObject()));
  }


  @Test
  public void testCycleAllShieldBlocks() {
    ShieldBlock shieldBlock = (ShieldBlock) testLevel.getBlockList().get(1);
    shieldBlock.hit();
    assertFalse(shieldBlock.isBroken());
    for(int k=0; k < 1001;k++) {
      testLevel.updateAllBlocks();
    }
    shieldBlock.hit();
    shieldBlock.update();
    assertTrue(shieldBlock.isBroken());
  }

  @Test
  public void testSpawnPowerUps() {
    BorderPane group = new BorderPane();
    List<PowerUp> currentPowerUps = new ArrayList<>();
    List<AbstractBlock> blockList = testLevel.getBlockList();
    blockList.forEach(block -> {
      block.hit();
      block.update();
    });
    testLevel.spawnPowerUps(group,currentPowerUps);
    assertEquals(1,group.getChildren().size());
    assertEquals(1,currentPowerUps.size());
  }
  @Test
  public void testPowerUpMove(){
    BorderPane group = new BorderPane();
    List<PowerUp> currentPowerUps = new ArrayList<>();
    List<AbstractBlock> blockList = testLevel.getBlockList();
    blockList.forEach(block -> {
      block.hit();
      block.update();
    });
    testLevel.spawnPowerUps(group,currentPowerUps);
    PowerUp testPowerUp = currentPowerUps.get(0);
    double initialPostion = testPowerUp.getDisplayCircle().getCenterY();
    testPowerUp.move();
    assertEquals(initialPostion+1, testPowerUp.getDisplayCircle().getCenterY());
  }

  @Test
  public void testDescendLevelMovesDown() throws IOException, URISyntaxException {
    final Level descend = new DescendLevel("test.txt");
    final List<Double> initialXPos = descend.getBlockList().stream()
        .map(block -> block.getDisplayObjectX()).collect(
        Collectors.toList());
    final List<Double> initialYPos = descend.getBlockList().stream()
        .map(block -> block.getDisplayObjectY()).collect(
            Collectors.toList());
    for(int k=0; k<10001 ;k++) {
      descend.updateLevel();
    }
    for(int j=0; j<descend.getBlockList().size();j++) {
      final AbstractBlock thisBlock = descend.getBlockList().get(j);
      assertEquals(initialXPos.get(j),thisBlock.getDisplayObjectX());
      assertTrue(initialYPos.get(j) < thisBlock.getDisplayObjectY());
    }
  }

  @Test
  public void testScrambleBlockLevelChangesPosition() throws IOException, URISyntaxException {
    final Level scramble = new ScrambleBlockLevel("test.txt");
    final List<Double> initialXPos = scramble.getBlockList().stream()
        .map(block -> block.getDisplayObjectX()).collect(
            Collectors.toList());
    final List<Double> initialYPos = scramble.getBlockList().stream()
        .map(block -> block.getDisplayObjectY()).collect(
            Collectors.toList());
    for(int k=0; k<1001 ;k++) {
      scramble.updateLevel();
    }
    for(int j=0; j<scramble.getBlockList().size()-1;j++) {
      final AbstractBlock thisBlock = scramble.getBlockList().get(j);
      assertEquals(initialXPos.get(j+1),thisBlock.getDisplayObjectX());
      assertEquals(initialYPos.get(j+1),thisBlock.getDisplayObjectY());
    }
    final AbstractBlock finalBlock = scramble.getBlockList().get(scramble.getBlockList().size()-1);
    assertEquals(initialXPos.get(0),finalBlock.getDisplayObjectX());
    assertEquals(initialYPos.get(0),finalBlock.getDisplayObjectY());
  }

}
