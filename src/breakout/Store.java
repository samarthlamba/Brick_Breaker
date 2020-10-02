package breakout;

import com.sun.tools.javac.Main;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.Consumer;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

public class Store {

  private final static String HIGHEST_SCORE_FILE = "highestScore.txt";
  private final static String MOVE_TO_NEXT_LEVEL_STRING = "Press N to move to next level";
  private final static String NOT_ENOUGH_MONEY_LEFT_STRING = "You don't have enough points, please press N to continue";
  private final static String STORE_IMAGE_FILE = "image.jpg";
  private static final int COST = 5;
  private final static int BUTTON_SIZE_MULTIPLIER = 6;
  private final static int STORE_DISPLAY_SIZE_MULTIPLIER = 20;
  private final static int ZERO = 0;
  private final double sceneWidth;
  private final double sceneHeight;
  private final Paddle paddleNode;
  private final Ball ballNode;
  private Map<Button, Consumer<Store>> keyMap;
  private int currentScore = 0;
  private Label notEnoughMoney;
  private BorderPane storeDisplayPane;

  /**
   * Initialization for the store class. Creates the store
   * @param width is the size of the game
   * @param height is the height of the game
   * @param paddleNode is the paddleNode being used in the game (to add PowerUps and similar)
   * @param ballNode is the ball object of the game (to slow it down)
   */
  public Store(double width, double height, Paddle paddleNode, Ball ballNode) {
    this.sceneHeight = height;
    this.sceneWidth = width;
    this.ballNode = ballNode;
    this.paddleNode = paddleNode;
    initializePowers();
    initializeButtons();
  }

  private void initializePowers() {
    keyMap = new HashMap<>();
    keyMap.put(new Button("Increase Length"), store -> paddleNode.increaseLength());
    keyMap.put(new Button("Decrease Ball Speed"), store -> ballNode.decreaseSpeed());
    keyMap.put(new Button("Increase Paddle Speed"), store -> paddleNode.speedUp());
    keyMap.put(new Button("Increase Lives"), store -> paddleNode.increaseLives());


  }

  /**
   * gets the current score for the game
   * @return integer value for the current score
   */
  public int getCurrentScore() {
    return this.currentScore;
  }

  /**
   * adds values to the current score as a way to add bonus or subtract points
   * @param amount integer value. Positive to add and negative to subtract
   */
  public void addToCurrentScore(int amount) {
    this.currentScore = this.currentScore + amount;
  }

  /**
   * Combines and showacses the content for the store
   * @return returns borderPane which is all the content for the store
   */
  public BorderPane showStoreContent() {
    storeDisplayPane = new BorderPane();
    try {
      Image image = new Image(STORE_IMAGE_FILE, sceneWidth, sceneHeight, true, false);
      ImageView shop = new ImageView(image);
      shop.setX(sceneWidth / BUTTON_SIZE_MULTIPLIER);
      storeDisplayPane.getChildren().add(shop);
    } catch (Exception e) {
      //we left this as empty because if there is no picture we can still play the game and ignore the addition of picture.
    }
    VBox buttonsBox = new VBox();
    for (Button k : keyMap.keySet()) {
      k.setMinSize(sceneWidth / BUTTON_SIZE_MULTIPLIER, sceneHeight / BUTTON_SIZE_MULTIPLIER);
      buttonsBox.getChildren().add(k);
    }
    storeDisplayPane.setRight(buttonsBox);
    return storeDisplayPane;
  }

  /**
   * Checks to see if the user has enough money and is trying to make purchases. When they have not enough money, outputs that
   * @param root requires the group to change and assert different items
   */
  public void monitorPurchases(BorderPane root) {
    initializeMoneyText(root);
    if (currentScore < COST) {
      notEnoughMoney.setText(NOT_ENOUGH_MONEY_LEFT_STRING);


    }
  }

  private void initializeMoneyText(BorderPane root) {
    notEnoughMoney = new Label(MOVE_TO_NEXT_LEVEL_STRING);
    notEnoughMoney.setAlignment(Pos.CENTER);
    this.storeDisplayPane.getChildren().add(notEnoughMoney);
    notEnoughMoney.setFont(new Font(sceneHeight / STORE_DISPLAY_SIZE_MULTIPLIER));
    notEnoughMoney.setTextFill(Color.BLACK);
    root.setTop(notEnoughMoney);
    BorderPane.setAlignment(notEnoughMoney, Pos.CENTER);
  }

  private void initializeButtons() {
    for (Button k : keyMap.keySet()) {
      System.out.println(currentScore);
      k.setOnAction(event -> {
        if (currentScore >= COST) {
          keyMap.get(k).accept(this);
          this.currentScore = this.currentScore - COST;
        }
      });
    }
  }

  /**
   * Removes all store attributes as added in storeDisplayRoot.
   * @param root the group from the main class
   */
  public void removeAllStoreItems(BorderPane root) {
    root.getChildren().remove(this.storeDisplayPane);

  }

  /**
   * Updates the high score if the current score is larger than the highest score recorded in HIGHEST_SCORE_FILE
   * If file doesn't exist, doesn't update the score.
   */
  public void updateHighScore() {
    try {
      int highScore = getHighScore();
      if (currentScore > highScore) {
        Path pathToFile = Paths
            .get(
                Objects.requireNonNull(Main.class.getClassLoader().getResource(HIGHEST_SCORE_FILE))
                    .toURI());
        PrintWriter prw = new PrintWriter(String.valueOf(pathToFile));
        prw.println(currentScore);
        prw.close();
      }
    } catch (Exception e) {
      System.out.println("High Score file not present");
    }
  }

  /**
   * gets the highest score as recorded in the HIGHEST_SCORE_FILE
   * @return the integer value from the file. If file doesn't exist, returns 0 as the highest score.
   */
  public int getHighScore() {
    try {
      Path pathToFile = Paths
          .get(Objects.requireNonNull(Main.class.getClassLoader().getResource(HIGHEST_SCORE_FILE))
              .toURI());
      List<String> allLines = Files.readAllLines(pathToFile);
      String line = allLines.get(ZERO).trim();
      return Integer.parseInt(line);
    } catch (Exception e) {
      return ZERO;
    }

  }
}

