package breakout;

import breakout.powerups.PowerUp;
import java.util.*;
import java.util.function.Consumer;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.geometry.Rectangle2D;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.text.Font;
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
  private Paddle paddleNode;
  private Ball ballNode;
  private int level = 1;
  private Label lives;
  private ImageView shop;
  private Label score;
  private Label winLoss;
  private PhysicsEngine physicsEngine;
  private boolean isPaused = false;
  private boolean showStore = false;
  private Store store;

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
    paddleNode = new Paddle(width, height);
    ballNode = new Ball(width, height);
    store = new Store(width, height, paddleNode, ballNode);
    initializeText();
    root.getChildren().add(paddleNode.getObject());
    root.getChildren().add(ballNode.getObject());
    root.setCenter(winLossInitializeText());
    root.setBottom(score);
    this.currentGroup = root;
    setLevel(levelList.get(0));
    root.setTop(lives);
    this.physicsEngine = new PhysicsEngine(WIDTH, HEIGHT, paddleNode, currentLevel.getBlockList());
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
    if(!isPaused) {
      updateBallAndPaddle(elapsedTime);
      updateBlocks();
      updatePowerUps();
      updateStatusText();

    }
    if(showStore){
      store.monitorPurchases(currentGroup);
    }
  }

  private void handleMouseInput(double x, double y) {
    ballNode.start();
  }


  private void handleKeyInput(KeyCode code) {
    initializeKeyMap();
    if(keyMap.containsKey(code)) {
      keyMap.get(code).accept(this);
    }
    if (showStore == true && code.equals(KeyCode.N))
    {
      changeStoreStatus();
      nextLevel();
      winLoss.setText("Level Cleared!");
      winLoss.setVisible(true);
      System.out.println("changed");
      }

      }


  private void updateStatusText() {
    lives.setText(String.format("Lives left: %d", paddleNode.getLives()));
    score.setText(String.format("Score: %d", store.getCurrentScore()));
    if (paddleNode.gameOver()) {
      winLoss.setText("You lose");
      winLoss.setVisible(true);
      store.updateHighScore();
    }
  }


  private void showStoreItems(){

    Image image = new Image("image.jpg", WIDTH, HEIGHT, true, false);
    shop = new ImageView(image);
    shop.setX(WIDTH/6);
    currentGroup.getChildren().add(shop);
    currentGroup.setCenter(store.showStoreContent());
  }

  private void removeStoreComponents(){
    currentGroup.getChildren().remove(lives);
    store.removeAllStoreItems(currentGroup);
    currentGroup.getChildren().remove(shop);
  }

  public void nextLevel() {
    level++;
    removeStoreComponents();
    if(level-1< levelList.size()){
      setLevel(levelList.get(level-1));
      physicsEngine.setBlockList(currentLevel);
      ballNode.reset();
    }
    currentGroup.setTop(lives);
  }

  private void updateBallAndPaddle(double elapsedTime) {
    physicsEngine.ballBounce(ballNode);
    ballNode.move(elapsedTime);
  }

  private void updateBlocks() {
    currentLevel.updateAllBlocks();
    currentLevel.spawnPowerUps(currentGroup,currentPowerUps);
    currentLevel.removeBrokenBlocksFromGroup(currentGroup, store);
    if (currentLevel.getBlockList().isEmpty() && showStore==false) {
      changeStoreStatus();
      showStoreItems();
    }
    }



  private void updatePowerUps() {
    List<PowerUp> copyOfCurrentPowerUps = List.copyOf(currentPowerUps);
    for (PowerUp powerUp : copyOfCurrentPowerUps) {
      Node powerupCircle = powerUp.getDisplayCircle();
      powerUp.move();
      if (physicsEngine.collides(powerupCircle, paddleNode.getObject())) {
        powerUp.doPowerUp(paddleNode, ballNode);
        currentPowerUps.remove(powerUp);
        currentGroup.getChildren().remove(powerupCircle);
      }
      if (physicsEngine.atBottom(powerupCircle)) {
        currentPowerUps.remove(powerUp);
        currentGroup.getChildren().remove(powerupCircle);
      }
    }
  }

  private void initializeText() {
    VBox vbox = new VBox(2);
    vbox.setAlignment(Pos.BOTTOM_LEFT);
    String livesString = String.format("Lives left: %d", paddleNode.getLives());
    lives = new Label(livesString);
    score = new Label(
        String.format("Score: %d", store.getCurrentScore()));
    lives.setFont(new Font(HEIGHT / 30));
    score.setFont(new Font(HEIGHT / 30));
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

  private void changeStoreStatus() {
    showStore = !showStore;
  }

  private void initializeKeyMap() {
    if(keyMap == null) {
      keyMap = new HashMap<>();
      keyMap.put(KeyCode.L,game -> paddleNode.increaseLives());
      keyMap.put(KeyCode.R,game -> {
        paddleNode.reset();
        ballNode.reset();});
      keyMap.put(KeyCode.S,game -> paddleNode.speedUp());
      keyMap.put(KeyCode.SPACE,game -> game.pause());
      keyMap.put(KeyCode.RIGHT,game -> paddleNode.moveRight());
      keyMap.put(KeyCode.LEFT,game -> paddleNode.moveLeft());
      keyMap.put(KeyCode.D,game -> currentLevel.getBlockList().get(0).breakBlock());
      keyMap.put(KeyCode.DIGIT1, game -> game.setLevel(levelList.get(0)));
      keyMap.put(KeyCode.DIGIT2, game -> game.setLevel(levelList.get(1)));
      keyMap.put(KeyCode.DIGIT3, game -> game.setLevel(levelList.get(2)));
    }
  }


  public Ball getBall() {
    return ballNode;
  }

  public Paddle getPaddle() {
    return paddleNode;
  }

  public Level getCurrentLevel() {
    return currentLevel;
  }
}
