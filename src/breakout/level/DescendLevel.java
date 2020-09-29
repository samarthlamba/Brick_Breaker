package breakout.level;

import breakout.Game;
import java.io.IOException;
import java.net.URISyntaxException;

public class DescendLevel extends Level{
  private static final double DESCENT_RATE = .1;

  public DescendLevel(String fileSource) throws IOException, URISyntaxException {
    super(fileSource);
  }

  @Override
  protected void doLevelMechanic() {
    getBlockList().stream().forEach(block -> {
      block.setDisplayObjectY(block.getDisplayObjectY()+Game.HEIGHT*DESCENT_RATE);
    });
  }

}
