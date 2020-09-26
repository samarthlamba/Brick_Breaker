package breakout;

import breakout.powerups.PowerUp;
import com.sun.tools.javac.Main;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.function.Consumer;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.geometry.HPos;
import javafx.geometry.Pos;
import javafx.geometry.Rectangle2D;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.util.Duration;


public class Game extends Application {

  public static final String TITLE = "Ultimate Breakout Game";
  public static int WIDTH = 1200;
  public static int HEIGHT = 800;
  public static final int FRAMES_PER_SECOND = 120;
  public static final double SECOND_DELAY = 1.0 / FRAMES_PER_SECOND;
  public static final Paint BACKGROUND = Color.AZURE;
  private final List<PowerUp> currentPowerUps = new ArrayList<>();
  private List<String> levelList = List.of("level1.txt","level2.txt","level3.txt");
  private Map<KeyCode, Consumer<Game>> keyMap;
  private Scene myScene;
  private BorderPane currentGroup;
  private Level currentLevel;
  private Paddle gamePaddle;
  private Ball gameBall;
  private int level = 1;
  private Label lives;
  private Label score;
  private Label winLoss;
  private PhysicsEngine physicsEngine;
  private boolean isPaused = false;
  private int currentScore = 0;

  /**
   * Start the program.
   */
  public static void main(String[] args) {
    launch(args);
  }

  @Override
  public void start(Stage primaryStage) {
    Rectangle2D screenBounds = Screen.getPrimary().getVisualBounds();
    this.WIDTH = (int)(screenBounds.getWidth()*0.8);
    this.HEIGHT = (int)(screenBounds.getHeight()*0.8);
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
    BorderPane root = new BorderPane();
    gamePaddle = new Paddle(width, height);
    gameBall = new Ball(width, height);
    initializeText();
    root.getChildren().add(gamePaddle.getObject());
    root.getChildren().add(gameBall.getObject());
    root.setCenter(winLossInitializeText());
    root.setBottom(score);
    this.currentGroup = root;
    setLevel(levelList.get(0));
    root.setTop(lives);
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
    } catch (Exception e) {
      blocksForLevel = Collections.emptyList();
    }
    if (currentLevel != null) {
      currentGroup.getChildren().removeAll(currentLevel.getObjectsToDraw());
    }
    if (!blocksForLevel.isEmpty()) {
      currentGroup.getChildren().addAll(blocksForLevel);
    }
    if(physicsEngine != null) {
      physicsEngine.setBlockList(level);
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
    if(!isPaused) {
      updateBallAndPaddle(elapsedTime);
      updateBlocks();
      updatePowerUps();
      updateStatusTest();
    }
  }

  private void handleMouseInput(double x, double y) {
    gameBall.start();
  }


  private void handleKeyInput(KeyCode code) {
    initializeKeyMap();
    if(keyMap.containsKey(code)) {
      keyMap.get(code).accept(this);
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
      updateHighScore();
    }
  }

  public void updateHighScore() {
    try {
      Path pathToFile = Paths.get(Main.class.getClassLoader().getResource("highestScore.txt").toURI());
      List<String> allLines = Files.readAllLines(pathToFile);
      String line = allLines.get(0);
      if (Integer.valueOf(currentScore) > Integer.valueOf(line)){
        PrintWriter prw = new PrintWriter(String.valueOf(pathToFile));
        prw.println(currentScore);
        prw.close();
      }
    }
    catch (Exception e) {
      System.out.println("High Score file not present");
      e.printStackTrace();
    }
    }


  private void nextLevel() {
    level++;
    if(level-1< levelList.size()){
      setLevel(levelList.get(level-1));
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

  private VBox initializeText() {
    GridPane gridPane = new GridPane();
    VBox vbox = new VBox(2);
    vbox.setAlignment(Pos.BOTTOM_LEFT);
    String livesString = String.format("Lives left: %d", gamePaddle.getLives());
    lives = new Label(livesString);
    score = new Label(
        String.format("Score: %d", this.currentScore));
    lives.setFont(new Font(HEIGHT / 30));
    score.setFont(new Font(HEIGHT / 30));
    vbox.getChildren().addAll(lives, score);
    return vbox;
  }

  private Label winLossInitializeText() {
    winLoss = new Label("You won");
    winLoss.setFont(new Font(HEIGHT / 10));
    winLoss.setVisible(false);

    return winLoss;
  }

  private void pause() {
    isPaused = !isPaused;
  }

  private void initializeKeyMap() {
    if(keyMap == null) {
      keyMap = new HashMap<>();
      keyMap.put(KeyCode.L,game -> gamePaddle.increaseLives());
      keyMap.put(KeyCode.R,game -> {gamePaddle.reset();gameBall.reset();});
      keyMap.put(KeyCode.S,game -> gamePaddle.speedUp());
      keyMap.put(KeyCode.SPACE,game -> game.pause());
      keyMap.put(KeyCode.RIGHT,game -> gamePaddle.moveRight());
      keyMap.put(KeyCode.LEFT,game -> gamePaddle.moveLeft());
      keyMap.put(KeyCode.D,game -> currentLevel.getBlockList().get(0).breakBlock());
      keyMap.put(KeyCode.DIGIT1, game -> game.setLevel(levelList.get(0)));
      keyMap.put(KeyCode.DIGIT2, game -> game.setLevel(levelList.get(1)));
      keyMap.put(KeyCode.DIGIT3, game -> game.setLevel(levelList.get(2)));
      keyMap.put(KeyCode.P,game -> gamePaddle.increaseLength());
      keyMap.put(KeyCode.Z,game -> gameBall.randomColor());
    }
  }


  public Ball getBall() {
    return gameBall;
  }

  public Paddle getPaddle() {
    return gamePaddle;
  }

  public Level getCurrentLevel() {
    return currentLevel;
  }
}
