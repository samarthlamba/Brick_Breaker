package breakout.level;

import java.io.IOException;
import java.net.URISyntaxException;

/**
 * Basic level that has no special mechanic.
 */
public class BasicLevel extends Level{

  public BasicLevel(String fileSource) throws IOException, URISyntaxException {
    super(fileSource);
  }

  @Override
  protected void doLevelMechanic() {

  }
}
