package breakout;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;
import javafx.stage.Stage;
import javafx.util.Duration;
public class Game extends Application{
  public static final String TITLE = "Ultimate Breakout Game";
  public static final int WIDTH = 600;
  public static final int HEIGHT = 400;
  public static final int FRAMES_PER_SECOND = 60;
  public static final double SECOND_DELAY = 1.0 / FRAMES_PER_SECOND;
  public static final Paint BACKGROUND = Color.AZURE;
  public static final Paint HIGHLIGHT = Color.OLIVEDRAB;

  private Scene myScene;
  @Override
  public void start(Stage primaryStage) throws Exception {
    myScene = setupScene(WIDTH, HEIGHT, BACKGROUND);
    primaryStage.setScene(myScene);
    primaryStage.setTitle(TITLE);
    primaryStage.show();
  }
  Scene setupScene (int width, int height, Paint background) {
    // create one top level collection to organize the things in the scene
    Group root = new Group();
    Paddle gamePaddle = new Paddle(width/2, width, height);
    // make some shapes and set their properties


    // create a place to see the shapes
    Scene scene = new Scene(root, width, height, background);
    // respond to input
    //scene.setOnKeyPressed(e -> handleKeyInput(e.getCode()));
    //scene.setOnMouseClicked(e -> handleMouseInput(e.getX(), e.getY()));
    return scene;
  }
}
