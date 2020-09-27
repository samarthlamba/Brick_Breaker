package breakout;

import static org.junit.jupiter.api.Assertions.*;

import breakout.powerups.PowerUp;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.stream.Collectors;
import javafx.scene.shape.Circle;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class PowerUpTest {
  private PowerUp testPowerUp;
  @BeforeEach
  public void setup() throws IOException, URISyntaxException {
    Circle powerUpNodes = new Circle(20);
    
  }
  @Test
  void move() {

    //assertEquals()
  }

  @Test
  void doPowerUp() {
  }

  @Test
  void getFill() {
  }

  @Test
  void getDisplayCircle() {
  }
}