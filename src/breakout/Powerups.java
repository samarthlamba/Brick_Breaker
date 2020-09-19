package breakout;

import breakout.blocks.AbstractBlock;
import javafx.scene.paint.Color;
import java.util.Map;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;

public class Powerups {

  private AbstractBlock block;
  private Circle powerup;
  public String chosenPower;
  private final Map <Integer, Paint> powerupColor = Map.of(1, Color.RED, 2, Color.GREEN, 3, Color.BLACK);
  private final Map <Integer, String> powerupOptions= Map.of(1, "increaseLength", 2, "increaseSpeed", 3, "slowBallDown");
  public Powerups(AbstractBlock b, int X, int Y){
    block = b;
    powerup = new Circle(X, Y, 200);///block.getHeight());
  }
  public void randomPowerUp(){
    int randomNumber = (int)Math.random() * (powerupColor.size() + 1);
    powerup.setFill(powerupColor.get(randomNumber));
    chosenPower = powerupOptions.get(randomNumber);
  }

  public String getChosenPower(){
    return this.chosenPower;
  }

  public void startPowerUp(Paddle p, Ball b){
    if (chosenPower.equals("increaseLength")){
      increasePaddleLength(p);
    }
    else if (chosenPower.equals("increaseSpeed")){
      increaseSpeed(p);
    }
    else if (chosenPower.equals("slowBallDown")){
      decreaseBallSpeed(b);
    }
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


}
