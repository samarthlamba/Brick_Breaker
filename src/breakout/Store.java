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
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;

public class Store {

  private double SCENEWIDTH;
  private double SCENEHEIGHT;
  private final int COST = 5;
  private Map<Button, Consumer<Store>> keyMap;
  private int currentScore = 0;
  private Paddle paddleNode;
  private Ball ballNode;
  private String notEnoughMoneyText = "You don't have enough points, please press N to continue";
  private String moveToNextLevel = "Press N to move to next level";
  private Label notEnoughMoney;
  public Store(double width, double height, Paddle paddleNode, Ball ballNode) {
    this.SCENEHEIGHT = height;
    this.SCENEWIDTH = width;
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
      k.setMinSize(SCENEWIDTH/8, SCENEHEIGHT/8);
      root.add(k, 0+position,position,6,6);
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

    notEnoughMoney = new Label(moveToNextLevel);
    notEnoughMoney.setFont(new Font(30));
    root.setTop(notEnoughMoney);
    root.setAlignment(notEnoughMoney, Pos.CENTER);
    if (currentScore < COST) {
      notEnoughMoney.setText(notEnoughMoneyText);



    }
  }

  public void removeAllStoreItems(BorderPane root)
  {
    root.getChildren().remove(notEnoughMoney);
    root.getChildren().remove(keyMap.keySet());
  }

  public void updateHighScore() { //test this? Might be weird testing it
    try {
      Path pathToFile = Paths.get(Main.class.getClassLoader().getResource("highestScore.txt").toURI());
      List<String> allLines = Files.readAllLines(pathToFile);
      String line = allLines.get(0);
      if (Integer.valueOf(currentScore) > Integer.valueOf(line)){
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
}

