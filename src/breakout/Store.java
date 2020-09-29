package breakout;

import com.sun.tools.javac.Main;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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
  private final double sceneWidth;
  private final double sceneHeight;
  private final int COST = 5;
  private final Paddle paddleNode;
  private final Ball ballNode;
  private Map<Button, Consumer<Store>> keyMap;
  private int currentScore = 0;
  private Label notEnoughMoney;
  private BorderPane group;

  public Store(double width, double height, Paddle paddleNode, Ball ballNode) {
    this.sceneHeight = height;
    this.sceneWidth = width;
    this.ballNode = ballNode;
    this.paddleNode = paddleNode;
    initializePowers();

  }

  private void initializePowers() {
    keyMap = new HashMap<>();
    keyMap.put(new Button("Increase Length"), store -> paddleNode.increaseLength());
    keyMap.put(new Button("Decrease Ball Speed"), store -> ballNode.decreaseSpeed());
    keyMap.put(new Button("Increase Paddle Speed"), store -> paddleNode.speedUp());
    keyMap.put(new Button("Increase Lives"), store -> paddleNode.increaseLives());


  }

  public int getCurrentScore() {
    return this.currentScore;
  }

  public void addToCurrentScore(int amount) {
    this.currentScore = this.currentScore + amount;
  }


  public BorderPane showStoreContent() {
    BorderPane root = new BorderPane();
    try {
      Image image = new Image(STORE_IMAGE_FILE, sceneWidth, sceneHeight, true, false);
      ImageView shop = new ImageView(image);
      shop.setX(sceneWidth / 6);
      root.getChildren().add(shop);
    }
    catch (Exception e){

    }
    int position = 0;
    VBox buttonsBox = new VBox();
    for (Button k : keyMap.keySet()) {
      k.setMinSize(sceneWidth / 6, sceneHeight / 6);
      buttonsBox.getChildren().add(k);
    }
    root.setRight(buttonsBox);
    this.group = root;
    return root;
  }

  public void monitorPurchases(BorderPane root) {
    for (Button k : keyMap.keySet()) {
      System.out.println(currentScore);
      k.setOnAction(event -> {
        if (currentScore >= COST) {
          keyMap.get(k).accept(this);
          this.currentScore = this.currentScore - COST;
        }
      });
    }
    String moveToNextLevel = MOVE_TO_NEXT_LEVEL_STRING;
    notEnoughMoney = new Label(moveToNextLevel);
    notEnoughMoney.setAlignment(Pos.CENTER);
    group.getChildren().add(notEnoughMoney);
    notEnoughMoney.setFont(new Font(sceneHeight / 20));
    notEnoughMoney.setTextFill(Color.BLACK);
    root.setTop(notEnoughMoney);
    BorderPane.setAlignment(notEnoughMoney, Pos.CENTER);
    if (currentScore < COST) {
      String notEnoughMoneyText = NOT_ENOUGH_MONEY_LEFT_STRING;
      notEnoughMoney.setText(notEnoughMoneyText);


    }
  }

  public void removeAllStoreItems(BorderPane root) {
    root.getChildren().remove(group);

  }

  public void updateHighScore() { //test this? Might be weird testing it
    try {
      int highScore = getHighScore();
      System.out.println(highScore + "   " + currentScore);
      if (currentScore > highScore) {
        Path pathToFile = Paths
            .get(Main.class.getClassLoader().getResource(HIGHEST_SCORE_FILE).toURI());
        PrintWriter prw = new PrintWriter(String.valueOf(pathToFile));
        prw.println(currentScore);
        prw.close();
      }
    } catch (Exception e) {
      System.out.println("High Score file not present");
      e.printStackTrace();
    }
  }

  public int getHighScore() {
    try {
      Path pathToFile = Paths
          .get(Main.class.getClassLoader().getResource(HIGHEST_SCORE_FILE).toURI());
      List<String> allLines = Files.readAllLines(pathToFile);
      String line = allLines.get(0);
      return Integer.parseInt(line);
    } catch (Exception e) {
      return 0;
    }

  }
}

