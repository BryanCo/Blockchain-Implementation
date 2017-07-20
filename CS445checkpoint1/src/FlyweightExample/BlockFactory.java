package FlyweightExample;

/**
 *
 * @author Bryan
 */
import java.util.HashMap;

public class BlockFactory {
    private HashMap<Block.BlockType, Block> blockMap = new HashMap();
    
    public Block getBlock(Block.BlockType type){
        Block block = blockMap.get(type);
        
        if(block == null) {
            block = new Block(type);
            blockMap.put(type, block);
            System.out.println("Creating block type : " + type);
        }
        return block;
    }
}