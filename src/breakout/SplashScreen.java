package breakout;


import javafx.animation.Timeline;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class SplashScreen {

  private final int gameWidth;
  private final int gameHeight;
  private final Scene splashScene;
  private Button button;

  public SplashScreen(int width, int height) {
    gameWidth = width;
    gameHeight = height;
    Group group = new Group();
    button = formatButton();
    button.setId("startButton");
    ImageView background = formatBackground();
    background.setId("splashBackground");
    group.getChildren().add(button);
    group.getChildren().add(background);
    splashScene = new Scene(group, width, height);
    splashScene.setFill(Color.BLACK);
  }

  public Scene getSplashScene() {
    return splashScene;
  }

  public void setButtonToStartGame(Timeline t, Stage stage, Scene scene) {
    button.setOnAction(e -> {
      t.play();
      stage.setScene(scene);
    });
  }

  private ImageView loadImage(String imageName) {
    FileInputStream inputstream = null;
    try {
      inputstream = new FileInputStream(String.format("data/%s",imageName));
      Image image = new Image(inputstream);
      return new ImageView(image);
    } catch (FileNotFoundException e) {
      return null;
    }
  }

  private ImageView formatBackground() {
    ImageView background = loadImage("splashScreenDisplay.png");
    background.setFitWidth(gameWidth/2);
    background.setX(gameWidth/4);
    background.setFitHeight(gameHeight/2);
    background.setY(gameHeight/4);
    return background;
  }

  private Button formatButton() {
    Button button = new Button("");
    ImageView buttonGraphics = loadImage("button.png");
    button.setGraphic(buttonGraphics);
    button.setStyle("-fx-color: black");
    button.setLayoutX(gameWidth/4 - 100);
    button.setLayoutY(gameHeight-50);
    return button;
  }
}
