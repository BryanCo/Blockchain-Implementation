/***************************************************************
* file: Block.java
* Group "Generic Group Name"
*       Bryan Commentz
*       Taylor Stiles
*       Zahy Abou-Diab  
* class: CS 445 - Computer Graphics
*
* assignment: CS445-Project
* date last modified: 11/28/2016
*
* purpose: This class defines the Block object.
****************************************************************/ 

package CS445_Project;

public class Block {

    private boolean IsActive;
    private BlockType Type;
    private float x,y,z;
    
    
    public enum BlockType
    {
        BlockType_Grass(0),
        BlockType_Sand(1),
        BlockType_Water(2),
        BlockType_Dirt(3),
        BlockType_Stone(4),
        BlockType_Bedrock(5);
        private int BlockID;
        
        BlockType (int i) {
            BlockID =i;
        }
        public int GetID(){
            return BlockID;
        }
        public void SetID (int i){
         BlockID = i;
        }
    }

    //This is the Constructor 
    public Block(BlockType type){
        Type= type;
    }
    
    //Added to allow changing the block type
    public void setBlockType(BlockType type){
        Type= type;
    }
    
    //move or place the block
    public void setCoords(float x, float y, float z){
        this.x = x;
        this.y = y;
        this.z = z;
    }

    //returns active state
    public boolean IsActive() {
        return IsActive;
    }
    
    //allows active state to be changed 
    public void SetActive(boolean active){
        IsActive=active;
    }
    
    //returns the blocks type
    public int GetID(){
        return Type.GetID();
    }
}