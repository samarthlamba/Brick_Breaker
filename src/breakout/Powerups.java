package breakout;

import breakout.blocks.AbstractBlock;
import java.util.List;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.paint.Color;
import java.util.Map;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;

public class Powerups {

  private Circle powerup;
  public String chosenPower;
  private final Map <Integer, Paint> powerupColor = Map.of(1, Color.RED, 2, Color.GREEN, 3, Color.BLACK);
  private final Map <Integer, String> powerupOptions= Map.of(1, "increaseLength", 2, "increaseSpeed", 3, "slowBallDown");
  private final int dropSpeed = 1;
  public Powerups(Node BlockToRemove){
    powerup = new Circle(BlockToRemove.getBoundsInLocal().getCenterX(), BlockToRemove.getBoundsInLocal().getCenterY(), 10);///block.getHeight());
    //powerup = new Circle(200, 200, 20);
    System.out.println(powerup);
    randomPowerUp();
  }
  public void randomPowerUp(){
    int randomNumber = (int)(Math.random() * (powerupColor.size()));
    powerup.setFill(powerupColor.get(randomNumber));
    chosenPower = powerupOptions.get(randomNumber);
  }

  public void move(){
    this.powerup.setCenterY(this.powerup.getCenterY()+dropSpeed);
   // System.out.println(this.powerup.getCenterX());
  }

  public void startPowerUp(Paddle p, Ball b, Group currentGroup){
    System.out.println(chosenPower);
    if (chosenPower.equals("increaseLength")){

      increasePaddleLength(p);
    }
    else if (chosenPower.equals("increaseSpeed")){
      increaseSpeed(p);
    }
    else if (chosenPower.equals("slowBallDown")){
      decreaseBallSpeed(b);
    }
    currentGroup.getChildren().remove(this.powerup);
  }
  public void increasePaddleLength(Paddle p){
    p.increaseLength();
  }
  public void increaseSpeed(Paddle p){
    p.speedUp();
  }
  public void decreaseBallSpeed(Ball b){
    b.decreaseSpeed();
  }


  public Circle getObject() {
    return this.powerup;
  }
}
