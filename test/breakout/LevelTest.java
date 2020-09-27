package breakout;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import breakout.blocks.AbstractBlock;
import breakout.blocks.ShieldBlock;
import breakout.powerups.PowerUp;
import com.sun.tools.javac.Main;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class LevelTest {
  private Level testLevel;
  private Circle gameBall;
  private Rectangle gamePaddle;
  private Store store;
  private final Game myGame = new Game();
  private Scene myScene;

  @BeforeEach
  public void setup() throws IOException, URISyntaxException {
    myScene = myGame.setupScene(Game.WIDTH, Game.HEIGHT);
    testLevel = new Level("test.txt");
    Paddle gamePaddle = new Paddle(600, 600);
    Ball gameBall = new Ball(600, 600);
    store = new Store(600, 600, gamePaddle, gameBall);
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

    testLevel.removeBrokenBlocksFromGroup(group, store);

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
  public void testPointsIncrease(){
    int currentScore = store.getCurrentScore();
    store.addToCurrentScore(1);
    assertEquals(currentScore, store.getCurrentScore()-1);
  }

  @Test
  public void storeUpdateScores() throws IOException, URISyntaxException {

    int oldHighScore = Integer.valueOf(readFile());
    store.addToCurrentScore(oldHighScore);

    int newHighScore = store.getCurrentScore();
    store.updateHighScore();
    int scoreInFile = Integer.valueOf(readFile());
    assertEquals(newHighScore, scoreInFile);
    Path pathToFile = Paths.get(Main.class.getClassLoader().getResource("highestScore.txt").toURI());
    PrintWriter prw = new PrintWriter(String.valueOf(pathToFile));
    prw.println(oldHighScore);
    prw.close();
  }
  public String readFile() throws IOException, URISyntaxException {
    Path pathToFile = Paths.get(Main.class.getClassLoader().getResource("highestScore.txt").toURI());
    List<String> allLines = Files.readAllLines(pathToFile);
    String line = allLines.get(0);
    return line;
  }

}
