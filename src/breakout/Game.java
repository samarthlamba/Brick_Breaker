package breakout;

import breakout.blocks.AbstractBlock;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;


public class Game extends Application {

  public static final String TITLE = "Ultimate Breakout Game";
  public static final int WIDTH = 600;
  public static final int HEIGHT = 400;
  public static final int FRAMES_PER_SECOND = 120;
  public static final double SECOND_DELAY = 1.0 / FRAMES_PER_SECOND;
  public static final Paint BACKGROUND = Color.AZURE;
  public static final Paint HIGHLIGHT = Color.OLIVEDRAB;

  private Scene myScene;
  private Group currentGroup;
  private Level currentLevel;
  private Paddle gamePaddle;
  private Ball gameBall;
  private int level = 1;
  private Text lives;
  private Text winLoss;


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

    String livesString = "Lives left: " + gamePaddle.getLives();
    lives = new Text(10, height-height/30, livesString);
    lives.setFont(new Font(height/30));
    winLoss = new Text(width/2, height/2, "You won");
    //winLoss.setTextAlignment(TextAlignment.CENTER);
    winLoss.setFont(new Font(height/10));
    winLoss.setVisible(false);
    root.getChildren().add(winLoss);
    root.getChildren().add(gamePaddle.getObject());
    root.getChildren().add(gameBall.getObject());
    root.getChildren().add(lives);

    this.currentGroup = root;
    setLevel("level1.txt");
    // make some shapes and set their properties

    // create a place to see the shapes
    Scene scene = new Scene(root, width, height, background);
    // respond to input
    scene.setOnKeyPressed(e -> handleKeyInput(e.getCode()));
    scene.setOnMouseClicked(e -> handleMouseInput(e.getX(), e.getY()));
    return scene;
  }

  public void setLevel(String fileSource){
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
    if(currentLevel!=null) {
      currentGroup.getChildren().removeAll(currentLevel.getObjectsToDraw());
    }
    if(!blocksForLevel.isEmpty()){
      currentGroup.getChildren().addAll(blocksForLevel);
    }
    this.currentLevel = level;
  }

  /**
   * // Handle the game's "rules" for every "moment"
   *
   * @param elapsedTime
   */
  void step(double elapsedTime) {
    updateShape(elapsedTime);
    updateBlocks();
  }

  private void handleMouseInput (double x, double y) {
    System.out.println("keypad");
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
  }

  public void updateShape(double elapsedTime) {
    gameBall.move(elapsedTime, gamePaddle);
    checkBlockCollision(elapsedTime);
    lives.setText("Lives left: " + gamePaddle.getLives());
    if (gamePaddle.gameOver()) {
      winLoss.setText("You lose");
      winLoss.setVisible(true);

    }
  }

  public void updateBlocks(){
    List<AbstractBlock> brokenBlocks = currentLevel.removeBrokenBlocks();
    List<Node> nodesToRemove = brokenBlocks.stream()
        .map(block -> block.getDisplayObject())
        .collect(Collectors.toList());
    currentLevel.cycleAllShieldBlocks();
    for (Node k : nodesToRemove){
      //random.ra
      k.getBoundsInLocal();
    }
    currentGroup.getChildren().removeAll(nodesToRemove);
    currentLevel.cycleAllShieldBlocks();
  }

  private void checkBlockCollision(double elapsedTime) {
    AbstractBlock blockHit = currentLevel.getBlockAtBallPosition(gameBall);
    if (blockHit!=null){
      blockHit.hit();
      gameBall.changeXDirection(elapsedTime);
      gameBall.changeYDirection(elapsedTime);
    }
  }
}
