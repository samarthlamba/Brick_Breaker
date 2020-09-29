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
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

public class Store {

  private final double sceneWidth;
  private final double sceneHeight;
  private final int COST = 5;
  private Map<Button, Consumer<Store>> keyMap;
  private int currentScore = 0;
  private final Paddle paddleNode;
  private final Ball ballNode;
  private Label notEnoughMoney;
  private final static String highestScoreFile = "highestScore.txt";
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

  public int getCurrentScore(){
    return this.currentScore;
  }

  public void addToCurrentScore(int amount){
    this.currentScore = this.currentScore + amount;
  }


  public GridPane showStoreContent() {
    GridPane root = new GridPane();
    root.setAlignment(Pos.CENTER);
    int position = 0;
    for (Button k : keyMap.keySet()){
      k.setMinSize(sceneWidth /8, sceneHeight /8);
      root.add(k, position,position,6,6);
      position = position +6;
    }
    return root;
  }

  public void monitorPurchases(BorderPane root) {
      for (Button k : keyMap.keySet()) {
          System.out.println(currentScore);
          k.setOnAction(event -> { if(currentScore>= COST){
            keyMap.get(k).accept(this);
            this.currentScore = this.currentScore - COST;
          }});
      }

    String moveToNextLevel = "Press N to move to next level";
    notEnoughMoney = new Label(moveToNextLevel);
      notEnoughMoney.setAlignment(Pos.CENTER);
    notEnoughMoney.setFont(new Font(sceneHeight /20));
    notEnoughMoney.setTextFill(Color.WHITE);
    root.setTop(notEnoughMoney);
    BorderPane.setAlignment(notEnoughMoney, Pos.CENTER);
    if (currentScore < COST) {
      String notEnoughMoneyText = "You don't have enough points, please press N to continue";
      notEnoughMoney.setText(notEnoughMoneyText);



    }
  }

  public void removeAllStoreItems(BorderPane root)
  {
    root.getChildren().remove(notEnoughMoney);
    root.getChildren().remove(showStoreContent());

  }

  public void updateHighScore() { //test this? Might be weird testing it
    try {
      int highScore = getHighScore();
      System.out.println(highScore + "   " + currentScore);
      if (currentScore > highScore){
        Path pathToFile = Paths
            .get(Main.class.getClassLoader().getResource(highestScoreFile).toURI());
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

  public int getHighScore(){
    try {
      Path pathToFile = Paths
          .get(Main.class.getClassLoader().getResource(highestScoreFile).toURI());
      List<String> allLines = Files.readAllLines(pathToFile);
      String line = allLines.get(0);
      return Integer.parseInt(line);
    }
    catch (Exception e){
      return 0;
    }

  }
}

