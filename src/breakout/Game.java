package breakout;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Collections;
import java.util.List;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
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
  public void start(Stage primaryStage) throws Exception {
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
    lives = new Text(10, height/30, livesString);
    lives.setFont(new Font(height/30));
    winLoss = new Text(width/2, height/2, "You won");

    //winLoss.setTextAlignment(TextAlignment.CENTER);
    winLoss.setFont(new Font(height/10));
    winLoss.setVisible(false);

    setupLevel(root);
    root.getChildren().add(gamePaddle.getObject());
    root.getChildren().add(gameBall.getObject());
    root.getChildren().add(lives);
    root.getChildren().add(winLoss);
    // make some shapes and set their properties

    // create a place to see the shapes
    Scene scene = new Scene(root, width, height, background);
    // respond to input
    scene.setOnKeyPressed(e -> handleKeyInput(e.getCode()));
    scene.setOnMouseClicked(e -> handleMouseInput(e.getX(), e.getY()));
    return scene;
  }

  /**
   * // Handle the game's "rules" for every "moment"
   *
   * @param elapsedTime
   */
  void step(double elapsedTime) {
    updateShape(elapsedTime);
  }

  private void setupLevel(Group root) {
    Level level = null;
    List<Rectangle> blocksForLevel;
    try {
      level = new Level("level1.txt");
      blocksForLevel = level.getBlockObjectsToDraw();
    } catch (IOException e) {
      blocksForLevel = Collections.emptyList();
    } catch (URISyntaxException e) {
      blocksForLevel = Collections.emptyList();
    }
    root.getChildren().addAll(blocksForLevel);

  }
  private void handleKeyInput(KeyCode code) {
    switch (code) {
      case LEFT -> gamePaddle.moveLeft();
      case RIGHT -> gamePaddle.moveRight();
      case S -> gamePaddle.speedUp();
      case L -> gamePaddle.increaseLives();
    }
  }

    private void handleMouseInput (double x, double y) {
    System.out.println("keypad");
      gameBall.start();
  }

  public void updateShape(double elapsedTime) {
    gameBall.move(elapsedTime, gamePaddle);
    lives.setText("Lives left: " + gamePaddle.getLives());
    if (gamePaddle.gameOver()){
      winLoss.setText("You lose");
      winLoss.setVisible(true);

    }

  }
}
