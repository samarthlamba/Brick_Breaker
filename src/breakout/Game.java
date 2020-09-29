package breakout;

import breakout.level.BasicLevel;
import breakout.level.DescendLevel;
import breakout.level.Level;
import breakout.level.ScrambleBlockLevel;
import breakout.powerups.PowerUp;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.geometry.Rectangle2D;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.text.Font;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.util.Duration;

/**
 * Main game loop.
 */
public class Game extends Application {

  public static final String TITLE = "Ultimate Breakout Game";
  public static int WIDTH = 1200;
  public static int HEIGHT = 800;
  public static final int FRAMES_PER_SECOND = 120;
  public static final double SECOND_DELAY = 1.0 / FRAMES_PER_SECOND;
  private static final Font SUMMARY_FONT_SIZE = new Font(HEIGHT / 30.0);
  public static final Paint BACKGROUND = Color.AZURE;
  private final List<PowerUp> currentPowerUps = new ArrayList<>();
  private List<String> levelList = List.of("level1.txt", "level2.txt", "level3.txt");
  //we are ignoring this error as this allows for maximum flexibility
  private Map<KeyCode, Consumer<Game>> keyMap;
  private BorderPane currentGroup;
  private Level currentLevel;
  private Paddle gamePaddle;
  private Ball gameBall;
  private int onLevelInt = 0;
  private Label lives;
  private Label score;
  private PhysicsEngine physicsEngine;
  private boolean isPaused = false;
  private boolean showStore = false;
  private Store store;
  private Label levelLabel;
  private Timeline animation;

  /**
   * Start the program.
   */
  public static void main(String[] args) {
    launch(args);
  }


  /**
   * Used to start the game. Called once when the game is launched
   * @param primaryStage the stage the game will be run in
   */
  @Override
  public void start(Stage primaryStage) {
    Rectangle2D screenBounds = Screen.getPrimary().getVisualBounds();
    WIDTH = (int) (screenBounds.getWidth() * 0.8);
    HEIGHT = (int) (screenBounds.getHeight() * 0.8);
    Scene myScene = setupScene(WIDTH, HEIGHT);
    SplashScreen splashScreen = new SplashScreen(WIDTH,HEIGHT);
    primaryStage.setScene(splashScreen.getSplashScene());
    primaryStage.setTitle(TITLE);
    primaryStage.show();
    KeyFrame frame = new KeyFrame(Duration.seconds(SECOND_DELAY), e -> step(SECOND_DELAY));
    animation = new Timeline();
    animation.setCycleCount(Timeline.INDEFINITE);
    animation.getKeyFrames().add(frame);
    splashScreen.setButtonAction(e -> {
      primaryStage.setScene(myScene);
      animation.play();
    });
  }

  Scene setupScene(int width, int height) {
    // create one top level collection to organize the things in the scene
    BorderPane root = new BorderPane();
    gamePaddle = new Paddle(width, height);
    gameBall = new Ball(width, height);
    store = new Store(width, height, gamePaddle, gameBall);

    root.getChildren().add(gamePaddle.getObject());
    root.getChildren().add(gameBall.getObject());

    this.currentGroup = root;
    setLevel(levelList.get(0));
    root.setBottom(initializeStatusText());
    this.physicsEngine = new PhysicsEngine(WIDTH, HEIGHT, gamePaddle, currentLevel.getBlockList());
    // make some shapes and set their properties

    // create a place to see the shapes
    Scene scene = new Scene(root, width, height, Game.BACKGROUND);
    // respond to input
    scene.setOnKeyPressed(e -> handleKeyInput(e.getCode()));
    scene.setOnMouseClicked(e -> handleMouseInput());

    return scene;
  }

  /**
   * Used to set the current level to the specified level. If the level cannot be created,
   * will set the blocks for the level to an empty list, so no blocks will be drawn
   * @param fileSource the source of the level format
   */
  public void setLevel(String fileSource) {
    Level level = null;
    List<Node> blocksForLevel;
    try {
      level = pickLevelType(fileSource);
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
      assert level != null;
      physicsEngine.setBlockList(level);
    }
    this.currentLevel = level;
  }

  /**
   * Called to change the list of levels the game progresses through. Also sets the current level
   * to the first in the list.
   * @param listOfLevels a List of strings corresponding to text files of levels in /data folder.
   */
  public void setLevelList(List<String> listOfLevels) {
    this.levelList = listOfLevels;
    setLevel(listOfLevels.get(0));
  }

  /**
   * // Handle the game's "rules" for every "moment"
   *
   * @param elapsedTime amount of time gone
   */
  void step(double elapsedTime) {
    if (!isPaused) {
      updateBallAndPaddle(elapsedTime);
      updateBlocks();
      updatePowerUps();
      updateStatusText();

    }
    if (showStore) {
      store.monitorPurchases(currentGroup);
    }
  }

  private void handleMouseInput() {
    gameBall.reinitializeSpeed();
  }


  private void handleKeyInput(KeyCode code) {
    initializeKeyMap();
    if (keyMap.containsKey(code)) {
      keyMap.get(code).accept(this);
    }
    if (showStore && code.equals(KeyCode.N)) {
      changeStoreStatus();
      nextLevel();
    }
  }

  private void updateStatusText() {
    lives.setText(String.format("Lives left: %d", gamePaddle.getLives()));
    score.setText(String.format("Score: %d", store.getCurrentScore()));
    levelLabel.setText(String.format("Your level: %d", onLevelInt+1));
    if (gamePaddle.gameOver() || gamePaddle.getLives() <= 0) {
      Label finalLabel = new Label("So close, yet so far, friend");
      finalLabel.setFont(new Font(HEIGHT/10.0));
      currentGroup.setCenter(finalLabel);
      store.updateHighScore();
    }
  }



  private void showStoreItems() {
    currentGroup.setCenter(store.showStoreContent());
  }

  private void removeStoreComponents() {
    currentGroup.getChildren().remove(lives);
    store.removeAllStoreItems(currentGroup);
  }

  /**
   * Used to move the game to the next level.
   */
  public void nextLevel() {
    onLevelInt++;
    System.out.println(onLevelInt);
    removeStoreComponents();
    if (onLevelInt < levelList.size()) {
      setLevel(levelList.get(onLevelInt));
      physicsEngine.setBlockList(currentLevel);

    }
    currentGroup.setTop(lives);
  }

  private void updateBallAndPaddle(double elapsedTime) {
    physicsEngine.ballBounce(gameBall);
    gameBall.move(elapsedTime);
  }

  private void updateBlocks() {
    currentLevel.updateLevel();
    currentLevel.updateAllBlocks();
    currentLevel.spawnPowerUps(currentGroup, currentPowerUps);
    currentLevel.addScoreToStore(store);
    currentLevel.removeBrokenBlocksFromGroup(currentGroup);
    physicsEngine.checkForBlocksAtBottom();
    inciteCutScene();
  }
  private void inciteCutScene(){
    if (onLevelInt > 0 && onLevelInt+1>= levelList.size() && currentLevel.getBlockList().isEmpty()){

      Label finalLabel = new Label("Oh my god! You won friend");
      finalLabel.setFont(new Font(HEIGHT/10.0));
      currentGroup.setCenter(finalLabel);

      store.updateHighScore();
      animation.stop();
    }
    else if (currentLevel.getBlockList().isEmpty() && !showStore && currentPowerUps.isEmpty()) {
      changeStoreStatus();
      showStoreItems();
    }
  }

  private void updatePowerUps() {
    List<PowerUp> copyOfCurrentPowerUps = List.copyOf(currentPowerUps);
    for (PowerUp powerUp : copyOfCurrentPowerUps) {
      Node powerupCircle = powerUp.getDisplayCircle();
      powerUp.move();
      if (physicsEngine.collides(powerupCircle, gamePaddle.getObject())) {
        powerUp.doPowerUp(gamePaddle, gameBall);
        removePowerUp(powerUp);
      }
      if (physicsEngine.atBottom(powerupCircle)) {
        removePowerUp(powerUp);
      }
    }
  }

  private void removePowerUp(PowerUp powerUptoRemove){
    Node powerupCircle = powerUptoRemove.getDisplayCircle();
    currentPowerUps.remove(powerUptoRemove);
    currentGroup.getChildren().remove(powerupCircle);
  }

  private VBox initializeStatusText() {
    VBox gameStats = new VBox();
    String livesString = String.format("Lives left: %d", gamePaddle.getLives());
    String highestScoreString = String.format("Highest Score: %d", store.getHighScore());
    lives = new Label(livesString);
    Label highestScore = new Label(highestScoreString);
    score = new Label(String.format("Your Score: %d", store.getCurrentScore()));
    levelLabel = new Label(String.format("Your level: %d", onLevelInt+1));
    levelLabel.setFont(SUMMARY_FONT_SIZE);
    lives.setFont(SUMMARY_FONT_SIZE);
    score.setFont(SUMMARY_FONT_SIZE);
    highestScore.setFont(SUMMARY_FONT_SIZE);
    gameStats.getChildren().addAll(lives, score, highestScore, levelLabel);
    return gameStats;
  }


  private void pause() {
    isPaused = !isPaused;
  }

  private void changeStoreStatus() {
    showStore = !showStore;
  }

  private void initializeKeyMap() {
    if (keyMap == null) {
      keyMap = new HashMap<>();
      keyMap.put(KeyCode.L,game -> gamePaddle.increaseLives());
      keyMap.put(KeyCode.R,game -> {
        gamePaddle.reset();
        gameBall.reset();});
      keyMap.put(KeyCode.S,game -> gamePaddle.speedUp());
      keyMap.put(KeyCode.SPACE, Game::pause);
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

  private Level pickLevelType(String fileSource) throws Exception {
    Map<Integer, Level> levelTypeMap = new HashMap<>();
    levelTypeMap.put(0,new BasicLevel(fileSource));
    levelTypeMap.put(1,new ScrambleBlockLevel(fileSource));
    levelTypeMap.put(2,new DescendLevel(fileSource));
    return levelTypeMap.get(onLevelInt%levelTypeMap.size());
  }

  /**
   * Gets the game ball. Used for testing
   *
   * @return the active Ball object
   */
  public Ball getBall() {
    return gameBall;
  }

  /**
   * Gets the game paddle. Used for testing
   *
   * @return the active Paddle object controlled by the player
   */
  public Paddle getPaddle() {
    return gamePaddle;
  }

  /**
   * Gets the current level. Used for testing
   *
   * @return the current Level object.
   */
  public Level getCurrentLevel() {
    return currentLevel;
  }

  public int getOnLevelInt(){
    return onLevelInt;
  }
}
