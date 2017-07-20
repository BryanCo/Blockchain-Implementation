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

package FlyweightExample;

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

    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }

    public float getZ() {
        return z;
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
    
    public float[] createCube(int cubeLength) {
        int offset = cubeLength / 2;
        return new float[] {
            // TOP QUAD
            x + offset, y + offset, z,
            x - offset, y + offset, z,
            x - offset, y + offset, z - cubeLength,
            x + offset, y + offset, z - cubeLength,
            // BOTTOM QUAD
            x + offset, y - offset, z - cubeLength,
            x - offset, y - offset, z - cubeLength,
            x - offset, y - offset, z,
            x + offset, y - offset, z,
            // FRONT QUAD
            x + offset, y + offset, z - cubeLength,
            x - offset, y + offset, z - cubeLength,
            x - offset, y - offset, z - cubeLength,
            x + offset, y - offset, z - cubeLength,
            // BACK QUAD
            x + offset, y - offset, z,
            x - offset, y - offset, z,
            x - offset, y + offset, z,
            x + offset, y + offset, z,
            // LEFT QUAD
            x - offset, y + offset, z - cubeLength,
            x - offset, y + offset, z,
            x - offset, y - offset, z,
            x - offset, y - offset, z - cubeLength,
            // RIGHT QUAD
            x + offset, y + offset, z,
            x + offset, y + offset, z - cubeLength,
            x + offset, y - offset, z - cubeLength,
            x + offset, y - offset, z 
        };
    }
    
    //Applies texture to cube according to its type.
    public float[] createTexCube() {
        float offset = (1024f/16)/1024f;
        float XVAL = 0.0f;
        float YVAL = 0.0f;
        switch (Type.BlockID) {
            case 0://grass
                return new float[] {
                    //Top
                    XVAL+ offset*3, YVAL+ offset*10,
                    XVAL+ offset*2, YVAL+ offset*10,
                    XVAL+ offset*2, YVAL+ offset*9,
                    XVAL+ offset*3, YVAL+ offset*9,
                    //bottom
                    XVAL+ offset*3, YVAL+ offset*1,
                    XVAL+ offset*2, YVAL+ offset*1,
                    XVAL+ offset*2, YVAL+ offset*0,
                    XVAL+ offset*3, YVAL+ offset*0,
                    //side
                    XVAL+ offset*3, YVAL+ offset*0,
                    XVAL+ offset*4, YVAL+ offset*0,
                    XVAL+ offset*4, YVAL+ offset*1,
                    XVAL+ offset*3, YVAL+ offset*1,
                    //side
                    XVAL+ offset*4, YVAL+ offset*1,
                    XVAL+ offset*3, YVAL+ offset*1,
                    XVAL+ offset*3, YVAL+ offset*0,
                    XVAL+ offset*4, YVAL+ offset*0,
                    //side
                    XVAL+ offset*3, YVAL+ offset*0,
                    XVAL+ offset*4, YVAL+ offset*0,
                    XVAL+ offset*4, YVAL+ offset*1,
                    XVAL+ offset*3, YVAL+ offset*1,
                    //side
                    XVAL+ offset*3, YVAL+ offset*0,
                    XVAL+ offset*4, YVAL+ offset*0,
                    XVAL+ offset*4, YVAL+ offset*1,
                    XVAL+ offset*3, YVAL+ offset*1
                };  
            case 1://sand
                return new float[] {
                    //Top
                    XVAL+ offset*3, YVAL+ offset*2,
                    XVAL+ offset*2, YVAL+ offset*2,
                    XVAL+ offset*2, YVAL+ offset*1,
                    XVAL+ offset*3, YVAL+ offset*1,
                    //bottom
                    XVAL+ offset*3, YVAL+ offset*2,
                    XVAL+ offset*2, YVAL+ offset*2,
                    XVAL+ offset*2, YVAL+ offset*1,
                    XVAL+ offset*3, YVAL+ offset*1,
                    //side
                    XVAL+ offset*3, YVAL+ offset*2,
                    XVAL+ offset*2, YVAL+ offset*2,
                    XVAL+ offset*2, YVAL+ offset*1,
                    XVAL+ offset*3, YVAL+ offset*1,
                    //side
                    XVAL+ offset*3, YVAL+ offset*2,
                    XVAL+ offset*2, YVAL+ offset*2,
                    XVAL+ offset*2, YVAL+ offset*1,
                    XVAL+ offset*3, YVAL+ offset*1,
                    //side
                    XVAL+ offset*3, YVAL+ offset*2,
                    XVAL+ offset*2, YVAL+ offset*2,
                    XVAL+ offset*2, YVAL+ offset*1,
                    XVAL+ offset*3, YVAL+ offset*1,
                    //side
                    XVAL+ offset*3, YVAL+ offset*2,
                    XVAL+ offset*2, YVAL+ offset*2,
                    XVAL+ offset*2, YVAL+ offset*1,
                    XVAL+ offset*3, YVAL+ offset*1
                };
            case 2://water 15,13
                return new float[] {
                    //Top
                    XVAL+ offset*15, YVAL+ offset*13,
                    XVAL+ offset*14, YVAL+ offset*13,
                    XVAL+ offset*14, YVAL+ offset*12,
                    XVAL+ offset*15, YVAL+ offset*12,
                    //bottom
                    XVAL+ offset*15, YVAL+ offset*13,
                    XVAL+ offset*14, YVAL+ offset*13,
                    XVAL+ offset*14, YVAL+ offset*12,
                    XVAL+ offset*15, YVAL+ offset*12,
                    //side
                    XVAL+ offset*14, YVAL+ offset*12,
                    XVAL+ offset*15, YVAL+ offset*12,
                    XVAL+ offset*15, YVAL+ offset*13,
                    XVAL+ offset*14, YVAL+ offset*13,
                    //side
                    XVAL+ offset*15, YVAL+ offset*13,
                    XVAL+ offset*14, YVAL+ offset*13,
                    XVAL+ offset*14, YVAL+ offset*12,
                    XVAL+ offset*15, YVAL+ offset*12,
                    //side
                    XVAL+ offset*14, YVAL+ offset*12,
                    XVAL+ offset*15, YVAL+ offset*12,
                    XVAL+ offset*15, YVAL+ offset*13,
                    XVAL+ offset*14, YVAL+ offset*13,
                    //side
                    XVAL+ offset*14, YVAL+ offset*12,
                    XVAL+ offset*15, YVAL+ offset*12,
                    XVAL+ offset*15, YVAL+ offset*13,
                    XVAL+ offset*14, YVAL+ offset*13
                };
            case 3://dirt
                return new float[] {
                    //Top
                    XVAL+ offset*3, YVAL+ offset*1,
                    XVAL+ offset*2, YVAL+ offset*1,
                    XVAL+ offset*2, YVAL+ offset*0,
                    XVAL+ offset*3, YVAL+ offset*0,
                    //bottom
                    XVAL+ offset*3, YVAL+ offset*1,
                    XVAL+ offset*2, YVAL+ offset*1,
                    XVAL+ offset*2, YVAL+ offset*0,
                    XVAL+ offset*3, YVAL+ offset*0,
                    //side
                    XVAL+ offset*2, YVAL+ offset*0,
                    XVAL+ offset*3, YVAL+ offset*0,
                    XVAL+ offset*3, YVAL+ offset*1,
                    XVAL+ offset*4, YVAL+ offset*1,
                    //side
                    XVAL+ offset*3, YVAL+ offset*1,
                    XVAL+ offset*2, YVAL+ offset*1,
                    XVAL+ offset*2, YVAL+ offset*0,
                    XVAL+ offset*3, YVAL+ offset*0,
                    //side
                    XVAL+ offset*2, YVAL+ offset*0,
                    XVAL+ offset*3, YVAL+ offset*0,
                    XVAL+ offset*3, YVAL+ offset*1,
                    XVAL+ offset*2, YVAL+ offset*1,
                    //side
                    XVAL+ offset*2, YVAL+ offset*0,
                    XVAL+ offset*3, YVAL+ offset*0,
                    XVAL+ offset*3, YVAL+ offset*1,
                    XVAL+ offset*2, YVAL+ offset*1
                };
            case 4://stone
                return new float[] {
                    //Top
                    XVAL+ offset*2, YVAL+ offset*1,
                    XVAL+ offset*1, YVAL+ offset*1,
                    XVAL+ offset*1, YVAL+ offset*0,
                    XVAL+ offset*2, YVAL+ offset*0,
                    //bottom
                    XVAL+ offset*2, YVAL+ offset*1,
                    XVAL+ offset*1, YVAL+ offset*1,
                    XVAL+ offset*1, YVAL+ offset*0,
                    XVAL+ offset*2, YVAL+ offset*0,
                    //side
                    XVAL+ offset*2, YVAL+ offset*1,
                    XVAL+ offset*1, YVAL+ offset*1,
                    XVAL+ offset*1, YVAL+ offset*0,
                    XVAL+ offset*2, YVAL+ offset*0,
                    //side
                    XVAL+ offset*2, YVAL+ offset*1,
                    XVAL+ offset*1, YVAL+ offset*1,
                    XVAL+ offset*1, YVAL+ offset*0,
                    XVAL+ offset*2, YVAL+ offset*0,
                    //side
                    XVAL+ offset*2, YVAL+ offset*1,
                    XVAL+ offset*1, YVAL+ offset*1,
                    XVAL+ offset*1, YVAL+ offset*0,
                    XVAL+ offset*2, YVAL+ offset*0,
                    //side
                    XVAL+ offset*2, YVAL+ offset*1,
                    XVAL+ offset*1, YVAL+ offset*1,
                    XVAL+ offset*1, YVAL+ offset*0,
                    XVAL+ offset*2, YVAL+ offset*0
                };
                
            case 5://bedrock
                return new float[] {
                    //Top
                    XVAL+ offset*2, YVAL+ offset*2,
                    XVAL+ offset*1, YVAL+ offset*2,
                    XVAL+ offset*1, YVAL+ offset*1,
                    XVAL+ offset*2, YVAL+ offset*1,
                    //bottom
                    XVAL+ offset*2, YVAL+ offset*2,
                    XVAL+ offset*1, YVAL+ offset*2,
                    XVAL+ offset*1, YVAL+ offset*1,
                    XVAL+ offset*2, YVAL+ offset*1,
                    //side
                    XVAL+ offset*1, YVAL+ offset*1,
                    XVAL+ offset*2, YVAL+ offset*1,
                    XVAL+ offset*2, YVAL+ offset*2,
                    XVAL+ offset*1, YVAL+ offset*2,
                    //side
                    XVAL+ offset*2, YVAL+ offset*2,
                    XVAL+ offset*1, YVAL+ offset*2,
                    XVAL+ offset*1, YVAL+ offset*1,
                    XVAL+ offset*2, YVAL+ offset*1,
                    //side
                    XVAL+ offset*1, YVAL+ offset*1,
                    XVAL+ offset*2, YVAL+ offset*1,
                    XVAL+ offset*2, YVAL+ offset*2,
                    XVAL+ offset*1, YVAL+ offset*2,
                    //side
                    XVAL+ offset*1, YVAL+ offset*1,
                    XVAL+ offset*2, YVAL+ offset*1,
                    XVAL+ offset*2, YVAL+ offset*2,
                    XVAL+ offset*1, YVAL+ offset*2
                };
            default: //dirt
                return new float[] {
                    //Top
                    XVAL+ offset*3, YVAL+ offset*1,
                    XVAL+ offset*2, YVAL+ offset*1,
                    XVAL+ offset*2, YVAL+ offset*0,
                    XVAL+ offset*3, YVAL+ offset*0,
                    //bottom
                    XVAL+ offset*3, YVAL+ offset*1,
                    XVAL+ offset*2, YVAL+ offset*1,
                    XVAL+ offset*2, YVAL+ offset*0,
                    XVAL+ offset*3, YVAL+ offset*0,
                    //side
                    XVAL+ offset*2, YVAL+ offset*0,
                    XVAL+ offset*3, YVAL+ offset*0,
                    XVAL+ offset*3, YVAL+ offset*1,
                    XVAL+ offset*4, YVAL+ offset*1,
                    //side
                    XVAL+ offset*3, YVAL+ offset*1,
                    XVAL+ offset*2, YVAL+ offset*1,
                    XVAL+ offset*2, YVAL+ offset*0,
                    XVAL+ offset*3, YVAL+ offset*0,
                    //side
                    XVAL+ offset*2, YVAL+ offset*0,
                    XVAL+ offset*3, YVAL+ offset*0,
                    XVAL+ offset*3, YVAL+ offset*1,
                    XVAL+ offset*2, YVAL+ offset*1,
                    //side
                    XVAL+ offset*2, YVAL+ offset*0,
                    XVAL+ offset*3, YVAL+ offset*0,
                    XVAL+ offset*3, YVAL+ offset*1,
                    XVAL+ offset*2, YVAL+ offset*1
                };
        }
    }
}