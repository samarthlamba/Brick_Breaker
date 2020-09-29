package breakout;


import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;

/**
 * This class is created at the start of the game to display the rules. It has a single button that
 * starts the game when preseed.
 */
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
    if(background!=null) {
      background.setId("splashBackground");
      group.getChildren().add(background);
    }
    group.getChildren().add(button);
    splashScene = new Scene(group, width, height);
    splashScene.setFill(Color.BLACK);
  }

  /**
   * Gets the display for the splash screen
   * @return the scene the splash screen displays
   */
  public Scene getSplashScene() {
    return splashScene;
  }

  /**
   * Gives an action to the button
   * @param e an event handler for the button to use on action.
   */
  public void setButtonAction(EventHandler e) {
    button.setOnAction(e);
  }

  private ImageView loadImage(String imageName) {
    FileInputStream inputstream;
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
    if(background!=null) {
      background.setFitWidth(gameWidth / 2);
      background.setX(gameWidth / 4);
      background.setFitHeight(gameHeight / 2);
      background.setY(gameHeight / 4);
    }
    return background;
  }

  private Button formatButton() {
    Button button = new Button("");
    ImageView buttonGraphics = loadImage("button.png");
    if(buttonGraphics!=null) {
      button.setGraphic(buttonGraphics);
    }
    else{
      button.setText("Click here!");
      button.setTextFill(Color.WHITE);
    }
    button.setStyle("-fx-color: black");
    button.setLayoutX(gameWidth/3);
    button.setLayoutY(gameHeight-50);
    return button;
  }
}
