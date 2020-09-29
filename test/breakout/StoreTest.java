package breakout;

import static org.junit.jupiter.api.Assertions.assertEquals;

import breakout.level.BasicLevel;
import breakout.level.Level;
import com.sun.tools.javac.Main;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import javafx.embed.swing.JFXPanel;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class StoreTest {
  private Level testLevel;
  private Store store;
  @BeforeEach
  public void setup() throws IOException, URISyntaxException {
    JFXPanel panel = new JFXPanel();
    testLevel = new BasicLevel("test.txt");
    Paddle gamePaddle = new Paddle(600, 600);
    Ball gameBall = new Ball(600, 600);
    store = new Store(600, 600, gamePaddle, gameBall);
  }


  @Test
  public void testPointsIncrease(){
    int currentScore = store.getCurrentScore();
    store.addToCurrentScore(1);
    assertEquals(currentScore, store.getCurrentScore()-1);
  }

  @Test
  public void storeUpdateScores() throws IOException, URISyntaxException {

    int oldHighScore = Integer.valueOf(readFile().trim());
    store.addToCurrentScore(oldHighScore);

    int newHighScore = store.getCurrentScore();
    store.updateHighScore();
    int scoreInFile = Integer.valueOf(readFile());
    assertEquals(newHighScore, scoreInFile);
    Path pathToFile = Paths.get(Main.class.getClassLoader().getResource("highestScore.txt").toURI());
    PrintWriter prw = new PrintWriter(String.valueOf(pathToFile));
    prw.println(oldHighScore);
    prw.close();
  }
  public String readFile() throws IOException, URISyntaxException {
    Path pathToFile = Paths.get(Main.class.getClassLoader().getResource("highestScore.txt").toURI());
    List<String> allLines = Files.readAllLines(pathToFile);
    String line = allLines.get(0);
    return line;
  }

}
