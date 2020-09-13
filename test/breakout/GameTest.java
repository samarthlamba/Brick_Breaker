package breakout;

import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import util.DukeApplicationTest;
import org.junit.jupiter.api.Test;

import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.*;

public class GameTest extends DukeApplicationTest {
    private final Game myGame = new Game();
    private Scene myScene;
    private Circle gameBall;
    private Rectangle gamePaddle;

    /**
     * Start special test version of application that does not animate on its own before each test.
     * <p>
     * Automatically called @BeforeEach by TestFX.
     */
    @Override
    public void start(Stage stage) {
        // create game's scene with all shapes in their initial positions and show it
        myScene = myGame.setupScene(Game.WIDTH, Game.HEIGHT, Game.BACKGROUND);
        stage.setScene(myScene);
        stage.show();

        // find individual items within game by ID (must have been set in your code using setID())
        gameBall = lookup("#ball").query();
        gamePaddle = lookup("#paddle").query();
    }

    @Test
    public void testInitialPositions() {
        assertEquals(Game.WIDTH / 2, gameBall.getCenterX());
        assertEquals(Game.HEIGHT / 2, gameBall.getCenterY());
        assertEquals(Game.WIDTH / 60, gameBall.getRadius());

        assertEquals(Game.WIDTH / 2 - Game.WIDTH / 10, gamePaddle.getX());
        assertEquals(Game.HEIGHT - Game.HEIGHT / 25 - Game.HEIGHT / 35, gamePaddle.getY());
    }

    @Test
    public void testInitialBallVelocity() {
        final double initialXPos = gameBall.getCenterX();
        final double initialYPos = gameBall.getCenterY();
        assertEquals(Game.WIDTH / 2, initialXPos);
        assertEquals(Game.HEIGHT / 2, initialYPos);

        myGame.step(1.00);

        assertEquals(initialXPos - 120, gameBall.getCenterX(), .1);
        assertEquals(initialYPos - 120, gameBall.getCenterY(), .1);
    }

    @Test
    public void testPaddleMoveOnKeyPress() {
        final double initialXPos = gamePaddle.getX();
        final double initialYPos = gamePaddle.getY();
        assertEquals(Game.WIDTH / 2 - Game.WIDTH / 10, initialXPos);
        assertEquals(Game.HEIGHT - Game.HEIGHT / 25 - Game.HEIGHT / 35, initialYPos);

        press(myScene,KeyCode.RIGHT);

        assertEquals(initialXPos + 5, gamePaddle.getX());
        assertEquals(initialYPos, gamePaddle.getY());

        press(myScene,KeyCode.LEFT);

        assertEquals(initialXPos, gamePaddle.getX());
        assertEquals(initialYPos, gamePaddle.getY());

        press(myScene,KeyCode.LEFT);

        assertEquals(initialXPos-5, gamePaddle.getX());
        assertEquals(initialYPos, gamePaddle.getY());
    }

    @Test
    public void testBallBouncesOnPaddleCollision() {
        gamePaddle.setY(Game.HEIGHT / 2);
        gamePaddle.setX(Game.HEIGHT / 2);
        final double initialXPos = gamePaddle.getX() + 120;
        final double initialYPos = gamePaddle.getY() + 120;
        gameBall.setCenterX(initialXPos);
        gameBall.setCenterY(initialYPos);

        myGame.step(1.00);
        myGame.step(1.00);

        assertEquals(initialXPos - 240, gameBall.getCenterX());
        assertEquals(initialYPos, gameBall.getCenterY());
    }

    @Test
    public void testBallBounceOnCorner() {
        gameBall.setCenterX(119);
        gameBall.setCenterY(119);

        myGame.step(1.00);
        myGame.step(1.00);

        assertEquals(119,gameBall.getCenterX());
        assertEquals(119,gameBall.getCenterY());
    }
}
