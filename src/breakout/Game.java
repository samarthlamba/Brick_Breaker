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
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
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
import javafx.scene.input.KeyCode;
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
  private Scene myScene;
  private Group currentGroup;
  private Level currentLevel;
  private Paddle gamePaddle;
  private Ball gameBall;
  private int level = 1;
  private Label lives;
  private Label score;
  private Label winLoss;
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
    Rectangle2D screenBounds = Screen.getPrimary().getVisualBounds();
    this.WIDTH = (int)(screenBounds.getWidth()*0.8);
    this.HEIGHT = (int)(screenBounds.getHeight()*0.8);
    myScene = setupScene(WIDTH, HEIGHT, BACKGROUND);
    System.out.println(screenBounds);

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

    this.currentGroup = root;
    setLevel(levelList.get(0));
    root.getChildren().addAll(initializeText());
    root.getChildren().addAll(winLossInitializeText());

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
      updateHighScore();
    }
  }

  public void updateHighScore() {
    try {
      Path pathToFile = Paths.get(Main.class.getClassLoader().getResource("highestScore.txt").toURI());
      List<String> allLines = Files.readAllLines(pathToFile);
      String line = allLines.get(0);
      System.out.println(currentScore + "  " + (line));
      if (Integer.valueOf(currentScore) > Integer.valueOf(line)){
        System.out.println("eu");

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

  private GridPane initializeText() {
    GridPane gridPane = new GridPane();
    VBox vbox = new VBox(2);
    vbox.setAlignment(Pos.BOTTOM_LEFT);
    String livesString = String.format("Lives left: %d", gamePaddle.getLives());
    lives = new Label(livesString);
    score = new Label(
        String.format("Score: %d", this.currentScore));
    lives.setFont(new Font(HEIGHT / 30));
    score.setFont(new Font(HEIGHT / 30));
    vbox.getChildren().addAll(List.of(lives,score));
    System.out.println(vbox.getLayoutX());
    gridPane.getChildren().add(vbox);
    gridPane.setAlignment(Pos.CENTER);
    return gridPane;
  }

  private VBox winLossInitializeText() {
    VBox vbox = new VBox(10);
    winLoss = new Label("You won");
    vbox.getChildren().add(winLoss);
    winLoss.setAlignment(Pos.CENTER);
    winLoss.setFont(new Font(HEIGHT / 10));
    winLoss.setVisible(true);

    vbox.setLayoutX(WIDTH/2);
    vbox.setLayoutY(HEIGHT/2);
    return vbox;

  }


  public Ball getBall() {
    return gameBall;
  }

  public Level getCurrentLevel() {
    return currentLevel;
  }
}
