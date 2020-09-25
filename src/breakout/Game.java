package breakout;

import breakout.powerups.PowerUp;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;


public class Game extends Application {

  public static final String TITLE = "Ultimate Breakout Game";
  public static final int WIDTH = 1200;
  public static final int HEIGHT = 800;
  public static final int FRAMES_PER_SECOND = 120;
  public static final double SECOND_DELAY = 1.0 / FRAMES_PER_SECOND;
  public static final Paint BACKGROUND = Color.AZURE;
  private final List<PowerUp> currentPowerUps = new ArrayList<>();
  private List<String> levelList = List.of("level1.txt","level2.txt","level3.txt");
  private Scene myScene;
  private Group currentGroup;
  private Level currentLevel;
  private Paddle gamePaddle;
  private Ball gameBall;
  private int level = 1;
  private Text lives;
  private Text score;
  private Text winLoss;
  private PhysicsEngine physicsEngine;
  private int currentScore = 0;

  /**
   * Start the program.
   */
  public static void main(String[] args) {
    launch(args);
  }


  @Override
  public void start(Stage primaryStage) {
    myScene = setupScene(WIDTH, HEIGHT, BACKGROUND);

    primaryStage.setScene(myScene);
    primaryStage.setTitle(TITLE);
    primaryStage.show();
    KeyFrame frame = new KeyFrame(Duration.seconds(SECOND_DELAY), e -> step(SECOND_DELAY));
    Timeline animation = new Timeline();
    animation.setCycleCount(Timeline.INDEFINITE);
    animation.getKeyFrames().add(frame);
    animation.play();
  }

  Scene setupScene(int width, int height, Paint background) {
    // create one top level collection to organize the things in the scene
    Group root = new Group();
    gamePaddle = new Paddle(width, height);
    gameBall = new Ball(width, height);

    root.getChildren().add(gamePaddle.getObject());
    root.getChildren().add(gameBall.getObject());
    root.getChildren().addAll(initializeText());

    this.currentGroup = root;
    setLevel(levelList.get(0));
    this.physicsEngine = new PhysicsEngine(WIDTH, HEIGHT, gamePaddle, currentLevel.getBlockList());
    // make some shapes and set their properties

    // create a place to see the shapes
    Scene scene = new Scene(root, width, height, background);
    // respond to input
    scene.setOnKeyPressed(e -> handleKeyInput(e.getCode()));
    scene.setOnMouseClicked(e -> handleMouseInput(e.getX(), e.getY()));
    return scene;
  }

  public void setLevel(String fileSource) {
    Level level = null;
    List<Node> blocksForLevel;
    try {
      level = new Level(fileSource);
      blocksForLevel = level.getObjectsToDraw();
    } catch (IOException e) {
      blocksForLevel = Collections.emptyList();
    } catch (URISyntaxException e) {
      blocksForLevel = Collections.emptyList();
    }
    if (currentLevel != null) {
      currentGroup.getChildren().removeAll(currentLevel.getObjectsToDraw());
    }
    if (!blocksForLevel.isEmpty()) {
      currentGroup.getChildren().addAll(blocksForLevel);
    }
    this.currentLevel = level;
  }

  public void setLevelList(List<String> listOfLevels) {
    this.levelList = listOfLevels;
    setLevel(listOfLevels.get(0));
  }

  /**
   * // Handle the game's "rules" for every "moment"
   *
   * @param elapsedTime
   */
  void step(double elapsedTime) {
    updateBallAndPaddle(elapsedTime);
    updateBlocks();
    updatePowerUps();
    updateStatusTest();
  }

  private void handleMouseInput(double x, double y) {
    gameBall.start();
  }


  private void handleKeyInput(KeyCode code) {
    if (code.equals(KeyCode.LEFT)) {
      gamePaddle.moveLeft();
    }
    if (code.equals(KeyCode.RIGHT)) {
      gamePaddle.moveRight();
    }
    if (code.equals(KeyCode.S)) {
      gamePaddle.speedUp();
    }
    if (code.equals(KeyCode.L)) {
      gamePaddle.increaseLives();
    }
    if (code.equals(KeyCode.R)) {
      gameBall.reset();
    }
  }

  private void updateStatusTest() {
    lives.setText(String.format("Lives left: %d", gamePaddle.getLives()));
    score.setText(String.format("Score: %d", this.currentScore));
    if (currentLevel.getBlockList().isEmpty()) {
      nextLevel();
      winLoss.setText("Level Cleared!");
      winLoss.setVisible(true);
    }
    if (gamePaddle.gameOver()) {
      winLoss.setText("You lose");
      winLoss.setVisible(true);
    }
  }

  private void nextLevel() {
    level++;
    if(level-1< levelList.size()){
      setLevel(levelList.get(level-1));
      physicsEngine.setBlockList(currentLevel);
      gameBall.reset();
    }
  }

  private void updateBallAndPaddle(double elapsedTime) {
    physicsEngine.ballBounce(gameBall);
    gameBall.move(elapsedTime);
  }

  private void updateBlocks() {
    currentLevel.updateAllBlocks();
    currentLevel.spawnPowerUps(currentGroup,currentPowerUps);
    currentLevel.removeBrokenBlocksFromGroup(currentGroup);
  }


  private void updatePowerUps() {
    List<PowerUp> copyOfCurrentPowerUps = List.copyOf(currentPowerUps);
    for (PowerUp powerUp : copyOfCurrentPowerUps) {
      Node powerupCircle = powerUp.getDisplayCircle();
      powerUp.move();
      if (physicsEngine.collides(powerupCircle, gamePaddle.getObject())) {
        powerUp.doPowerUp(gamePaddle,gameBall);
        currentPowerUps.remove(powerUp);
        currentGroup.getChildren().remove(powerupCircle);
      }
      if (physicsEngine.atBottom(powerupCircle)) {
        currentPowerUps.remove(powerUp);
        currentGroup.getChildren().remove(powerupCircle);
      }
    }
  }

  private List<Text> initializeText() {
    String livesString = String.format("Lives left: %d", gamePaddle.getLives());
    lives = new Text(10, HEIGHT - HEIGHT / 30, livesString);
    score = new Text(10, HEIGHT - HEIGHT / 30 - HEIGHT / 30,
        String.format("Score: %d", this.currentScore));
    lives.setFont(new Font(HEIGHT / 30));
    score.setFont(new Font(HEIGHT / 30));
    winLoss = new Text(WIDTH / 2, HEIGHT / 2, "You won");
    //winLoss.setTextAlignment(TextAlignment.CENTER);
    winLoss.setFont(new Font(HEIGHT / 10));
    winLoss.setVisible(false);
    return List.of(lives,score,winLoss);
  }

  public Ball getBall() {
    return gameBall;
  }

  public Level getCurrentLevel() {
    return currentLevel;
  }
}
