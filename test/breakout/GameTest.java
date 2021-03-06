package breakout;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import breakout.level.BasicLevel;
import breakout.level.DescendLevel;
import breakout.level.Level;
import breakout.level.ScrambleBlockLevel;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.List;
import java.util.stream.Collectors;

import breakout.blocks.AbstractBlock;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import org.junit.jupiter.api.Test;
import util.DukeApplicationTest;

public class GameTest extends DukeApplicationTest {

  private final Game myGame = new Game();
  private Scene myScene;
  private Circle gameBall;
  private Rectangle gamePaddle;

  /**
   * Start special test version of application that does not animate on its own before each test.
   * <p>
   * Automatically called @BeforeEach by TestFX.
   */
  @Override
  public void start(Stage stage) {
    // create game's scene with all shapes in their initial positions and show it
    myScene = myGame.setupScene(Game.WIDTH, Game.HEIGHT);
    myGame.setLevelList(List.of("empty.txt"));
    stage.setScene(myScene);
    stage.show();

    // find individual items within game by ID (must have been set in your code using setID())
    gameBall = lookup("#ball").query();
    gamePaddle = lookup("#paddle").query();
  }

  @Test
  public void testInitialPositions() {
    assertEquals(Game.WIDTH / 2.0, gameBall.getCenterX());
    assertEquals(3*Game.HEIGHT / 4.0, gameBall.getCenterY());
    assertEquals(Game.WIDTH / 60.0, gameBall.getRadius());

    assertEquals(Game.WIDTH / 2.0 - Game.WIDTH / 10.0, gamePaddle.getX());
    assertEquals(Game.HEIGHT - Game.HEIGHT / 25.0 - Game.HEIGHT / 35.0, gamePaddle.getY());
  }

  @Test
  public void testInitialBallVelocity() {
    final double initialXVelocity = myGame.getBall().getSpeedX();
    final double initialYVelocity = myGame.getBall().getSpeedY();
    final double initialXPos = gameBall.getCenterX();
    final double initialYPos = gameBall.getCenterY();
    assertEquals(Game.WIDTH / 2, initialXPos);
    assertEquals(3*Game.HEIGHT / 4, initialYPos);

    javafxRun(() -> myGame.step(1.00));

    assertEquals(initialXPos - initialXVelocity, gameBall.getCenterX(), .1);
    assertEquals(initialYPos - initialYVelocity, gameBall.getCenterY(), .1);
  }

  @Test
  public void testPaddleMoveOnKeyPress() {
    final double initialXPos = gamePaddle.getX();
    final double initialYPos = gamePaddle.getY();
    assertEquals(Game.WIDTH / 2.0 - Game.WIDTH / 10.0, initialXPos);
    assertEquals(Game.HEIGHT - Game.HEIGHT / 25.0 - Game.HEIGHT / 35.0, initialYPos);

    press(myScene, KeyCode.RIGHT);

    assertEquals(initialXPos + 20, gamePaddle.getX());
    assertEquals(initialYPos, gamePaddle.getY());

    press(myScene, KeyCode.LEFT);

    assertEquals(initialXPos, gamePaddle.getX());
    assertEquals(initialYPos, gamePaddle.getY());

    press(myScene, KeyCode.LEFT);

    assertEquals(initialXPos - 20, gamePaddle.getX());
    assertEquals(initialYPos, gamePaddle.getY());
  }


  @Test
  public void testSKeySpeedUpPaddle() {
    final double initialXPos = gamePaddle.getX();
    final double initialYPos = gamePaddle.getY();

    press(myScene, KeyCode.RIGHT);

    assertEquals(initialXPos + 20, gamePaddle.getX());
    assertEquals(initialYPos, gamePaddle.getY());

    gamePaddle.setX(initialXPos);
    gamePaddle.setY(initialYPos);

    press(myScene, KeyCode.S);
    press(myScene, KeyCode.RIGHT);

    assertEquals(initialXPos + 30, gamePaddle.getX());
    assertEquals(initialYPos, gamePaddle.getY());
  }

  @Test
  public void testRKeyResetPos() {
    final double initialBallX = gameBall.getCenterX();
    final double initialBallY = gameBall.getCenterY();
    final double initialPaddleX = gamePaddle.getX();
    gameBall.setCenterX(Math.random());
    gameBall.setCenterY(Math.random());
    gamePaddle.setX(Math.random());

    press(myScene,KeyCode.R);

    assertEquals(initialBallX,gameBall.getCenterX());
    assertEquals(initialBallY,gameBall.getCenterY());
    assertEquals(initialPaddleX,gamePaddle.getX());
  }

  @Test
  public void testRKeyResetPaddleDimensions() {
    final double initialWidth = gamePaddle.getWidth();
    final double initialHeight= gamePaddle.getHeight();
    final double initialArcWidth = gamePaddle.getArcWidth();
    final double initialArcheight = gamePaddle.getArcHeight();
    gamePaddle.setArcWidth(Math.random());
    gamePaddle.setArcHeight(Math.random());
    gamePaddle.setWidth(Math.random());
    gamePaddle.setHeight(Math.random());

    press(myScene,KeyCode.R);

    assertEquals(initialWidth,gamePaddle.getWidth());
    assertEquals(initialArcWidth,gamePaddle.getArcWidth());
    assertEquals(initialHeight,gamePaddle.getHeight());
    assertEquals(initialArcheight,gamePaddle.getArcHeight());
  }

  @Test
  public void testLKeyIncreaseLives() {
    final Paddle paddle = myGame.getPaddle();
    final int initialLives = paddle.getLives();
    paddle.decreaseLives();

    press(myScene,KeyCode.L);

    assertEquals(initialLives,paddle.getLives());
  }

  @Test
  public void testSpaceKeyPause() {
    final double initialBallXPos = gameBall.getCenterX();
    final double initialBallYPos = gameBall.getCenterY();
    final double initialPaddleXPos = gamePaddle.getX();

    press(myScene,KeyCode.SPACE);
    press(myScene,KeyCode.RIGHT);
    javafxRun(() -> myGame.step(1.00));

    assertEquals(initialBallXPos,gameBall.getCenterX());
    assertEquals(initialBallYPos,gameBall.getCenterY());
    assertNotEquals(initialPaddleXPos,gamePaddle.getX());
  }

  @Test
  public void testDKeyDeletesBlock() {
    javafxRun(() -> myGame.setLevel("level1.txt"));
    final Level currentLevel = myGame.getCurrentLevel();
    List<AbstractBlock> brokenBlocks = currentLevel.getBlockList().stream()
            .filter(AbstractBlock::isBroken).collect(Collectors.toList());

    press(myScene,KeyCode.D);

    List<AbstractBlock> newlyBrokenBlocks = currentLevel.getBlockList().stream()
            .filter(AbstractBlock::isBroken).collect(Collectors.toList());
    assertNotEquals(brokenBlocks,newlyBrokenBlocks);
  }

  @Test
  public void test123ChangeLevel() throws IOException, URISyntaxException {
    javafxRun(() -> myGame.setLevelList(List.of("level1.txt","level2.txt","level3.txt")));
    final Level level1 = new BasicLevel("level1.txt");
    final Level level2 = new BasicLevel("level2.txt");
    final Level level3 = new BasicLevel("level3.txt");

    press(myScene,KeyCode.DIGIT2);
    assertEquals(level2.getLevelId(),myGame.getCurrentLevel().getLevelId());

    press(myScene,KeyCode.DIGIT3);
    assertEquals(level3.getLevelId(),myGame.getCurrentLevel().getLevelId());

    press(myScene,KeyCode.DIGIT1);
    assertEquals(level1.getLevelId(),myGame.getCurrentLevel().getLevelId());
  }

  @Test
  public void testPIncreasesPaddleWidth() {
    final double initialWidth = myGame.getPaddle().getObject().getWidth();

    press(myScene,KeyCode.P);

    assertTrue(myGame.getPaddle().getObject().getWidth() > initialWidth);
  }

  @Test
  public void testZChangeBallColor() {
    final Paint initialColor = gameBall.getFill();

    press(myScene,KeyCode.Z);

    assertNotEquals(initialColor,gameBall.getFill());
  }

  @Test
  public void ballTouchesEdgeOfPaddle(){
    System.out.println(myGame.getCurrentLevel().getLevelId());
    gameBall.setCenterX(gamePaddle.getBoundsInLocal().getCenterX() - gamePaddle.getWidth()/2.5);
    gameBall.setCenterY(gamePaddle.getBoundsInLocal().getCenterY()-gamePaddle.getHeight());
    double speedXBeforeImpact = myGame.getBall().getSpeedX();
    System.out.println(gameBall.getCenterX());
    javafxRun(() -> myGame.step(1.00/120));
    System.out.println(gameBall.getCenterX());
    assertTrue(speedXBeforeImpact < (myGame.getBall().getSpeedX()));
  }

  @Test
  public void testLevelMovesToNextInList() {
    javafxRun(() -> myGame.setLevelList(List.of("empty.txt", "testBlocksRemoved.txt")));
    Level firstlevel = myGame.getCurrentLevel();

    javafxRun(() -> myGame.step(1.00));
    press(myScene, KeyCode.N);
    Level secondLevel = myGame.getCurrentLevel();
    assertNotEquals(secondLevel, firstlevel);
  }


  @Test
  public void testNextLevelChangesLevelType(){
    javafxRun(() -> myGame.setLevelList(List.of("test.txt","test.txt","test.txt")));
    Level firstlevel = myGame.getCurrentLevel();

    javafxRun(() -> myGame.nextLevel());
    Level secondLevel = myGame.getCurrentLevel();

    javafxRun(() -> myGame.nextLevel());
    Level thirdLevel = myGame.getCurrentLevel();

    assertTrue(firstlevel instanceof BasicLevel);
    assertTrue(secondLevel instanceof ScrambleBlockLevel);
    assertTrue(thirdLevel instanceof DescendLevel);
  }


  @Test
  public void testBlocksAtBottomCauseLoss() {
    javafxRun(() -> myGame.setLevelList(List.of("test.txt","test.txt","test.txt")));
    javafxRun(() -> myGame.nextLevel());
    javafxRun(() -> myGame.nextLevel());
    assertTrue(myGame.getCurrentLevel() instanceof DescendLevel);
    for(int k = 0; k<10000;k++) {
      myGame.getCurrentLevel().updateLevel();
    }
    javafxRun(() -> myGame.step(1.00));

    assertTrue(myGame.getPaddle().getLives() <3);
  }
  @Test
  public void testLevelDisplay(){
    int currentLevel = myGame.getOnLevelInt();
    javafxRun(() -> myGame.nextLevel());
    assertEquals(currentLevel+1, myGame.getOnLevelInt());
  }
}
