package breakout;

import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class BlockReaderTest {
    @Test
    public void readsSimpleFileTest() throws IOException, URISyntaxException {
        final BlockReader testReader = new BlockReader("test.txt");
        final List<Block> blocksFromReader = testReader.getBlockList();
        Block block = blocksFromReader.get(2);
        assertEquals("1",block.getBlockType());
        assertEquals(1,block.getRow());
        assertEquals(0,block.getColumn());
        assertEquals(2,block.getNumRows());
        assertEquals(2,block.getNumColumns());
    }
}
