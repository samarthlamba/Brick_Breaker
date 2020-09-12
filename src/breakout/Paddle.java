package breakout;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;
import javafx.stage.Stage;
import javafx.util.Duration;
public class Paddle{
  private int speed;
  private int width;
  private int height;
  private int x;
  private Rectangle paddle;
  private Paint paddleColor = Color.HOTPINK;


  public Paddle(int X, int width, int height){
      x = X;
      paddle = new Rectangle(width/2, height);
      paddle.setFill(paddleColor);
  }
  public Rectangle getShape(){
    return this.paddle;

  }

  public void changeColor(Paint color){
    paddleColor = color;
  }

}
