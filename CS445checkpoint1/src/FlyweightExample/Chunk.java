/***************************************************************
* file: Chunk.java
* Group "Generic Group Name"
*       Bryan Commentz
*       Taylor Stiles
*       Zahy Abou-Diab  
* class: CS 445 - Computer Graphics
*
* assignment: CS445-Project
* date last modified: 11/28/2016
*
* purpose: Following the classroom demonstration, this class
* generates the chunk. Uses simplex noise algorithm to generate a height 
* map.  Then sets bottom layer to bedrock, topped with stone until
* 2 blocks down from top. Then a layer of dirt topped with grass.  Water is 
* placed in the lowest points and sandy beaches and lake beds are added 
* around water.  
****************************************************************/ 

package FlyweightExample;

import java.nio.FloatBuffer;
import java.util.Random;
import org.lwjgl.BufferUtils;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL15.*;

import org.newdawn.slick.opengl.Texture;
import org.newdawn.slick.opengl.TextureLoader;
import org.newdawn.slick.util.ResourceLoader;

public class Chunk {
    static final int CHUNK_SIZE = 30;
    static final int CUBE_LENGTH = 2;
    private Block[][][] Blocks;
    private int VBOVertexHandle;
    private int VBOColorHandle;
    private int StartX, StartY, StartZ;
    private Random r;
    private int VBOTextureHandle;
    private Texture texture;

    //render method
    public void render(){
        glPushMatrix();
            glBindBuffer(GL_ARRAY_BUFFER, VBOVertexHandle);
            glVertexPointer(3, GL_FLOAT, 0, 0L);
            glBindBuffer(GL_ARRAY_BUFFER, VBOColorHandle);
            glColorPointer(3,GL_FLOAT, 0, 0L);
            glBindBuffer(GL_ARRAY_BUFFER, VBOTextureHandle);
            glBindTexture(GL_TEXTURE_2D, 1);
            glTexCoordPointer(2,GL_FLOAT,0,0L);
            glDrawArrays(GL_QUADS, 0, CHUNK_SIZE * CHUNK_SIZE * CHUNK_SIZE * 24);
        glPopMatrix();
    }
        
    //Generates the chunck. Uses simplex noise algorithm to generate a height 
    //map.  Then sets bottom layer to bedrock, topped with stone until
    //2 blocks down from top. Then a layer of dirt topped with grass.  Water is 
    //placed in the lowest points and sandy beaches and lake beds are added 
    //arround water.
    public Chunk(int startX, int startY, int startZ, BlockFactory blockFactory) {
        try{texture = TextureLoader.getTexture("PNG",
            ResourceLoader.getResourceAsStream("terrain.png"));
        }
        catch(Exception e)
        {
            System.out.print("Error loading textures!");
        }
        
        VBOColorHandle = glGenBuffers();
        VBOVertexHandle = glGenBuffers();
        VBOTextureHandle = glGenBuffers();
        FloatBuffer VertexPositionData = 
                BufferUtils.createFloatBuffer((CHUNK_SIZE * CHUNK_SIZE *CHUNK_SIZE) * 6 * 12);
        FloatBuffer VertexColorData =
                BufferUtils.createFloatBuffer( (CHUNK_SIZE* CHUNK_SIZE * CHUNK_SIZE) * 6 * 12);
        FloatBuffer VertexTextureData = 
                BufferUtils.createFloatBuffer((CHUNK_SIZE* CHUNK_SIZE *CHUNK_SIZE)* 6 * 12);
        
        Random seed = new Random();
        SimplexNoise n = new SimplexNoise(60, 0.5, seed.nextInt());
        
        int chunkHeight = 10;
        int chunkVariation = 4;
        
        r= new Random();
        Blocks = new Block[CHUNK_SIZE][CHUNK_SIZE][CHUNK_SIZE];
        int[][] height = new int[CHUNK_SIZE][CHUNK_SIZE];
        
        Block block;
        
        for (int x = 0; x < CHUNK_SIZE; x++) {
            for (int z = 0; z < CHUNK_SIZE; z++) {
                height[x][z] = (startY + chunkHeight + (int)( chunkVariation * n.getNoise((int)x,(int)z)));
                
                for (int y = 0; y <= height[x][z]; y++) { 
                    if(y == 0){
                        block = blockFactory.getBlock(Block.BlockType.BlockType_Bedrock);
                        block.setCoords((float)(startX + x * CUBE_LENGTH), 
                                        (float)(y*CUBE_LENGTH+ (int)(CHUNK_SIZE*.8)),
                                        (float)(startZ + z * CUBE_LENGTH));
                        VertexPositionData.put(block.createCube(CUBE_LENGTH));
                        VertexColorData.put(new float[] { 1, 1, 1 });
                        VertexTextureData.put(block.createTexCube());
                    }
                    else if( y < height[x][z] - 1){
                        block = blockFactory.getBlock(Block.BlockType.BlockType_Stone);
                        block.setCoords((float)(startX + x * CUBE_LENGTH), 
                                        (float)(y*CUBE_LENGTH+ (int)(CHUNK_SIZE*.8)),
                                        (float)(startZ + z * CUBE_LENGTH));
                        VertexPositionData.put(block.createCube(CUBE_LENGTH));
                        VertexColorData.put(new float[] { 1, 1, 1 });
                        VertexTextureData.put(block.createTexCube());
                    }
                    else if( y == height[x][z] - 1){
                        block = blockFactory.getBlock(Block.BlockType.BlockType_Dirt);
                        block.setCoords((float)(startX + x * CUBE_LENGTH), 
                                        (float)(y*CUBE_LENGTH+ (int)(CHUNK_SIZE*.8)),
                                        (float)(startZ + z * CUBE_LENGTH));
                        VertexPositionData.put(block.createCube(CUBE_LENGTH));
                        VertexColorData.put(new float[] { 1, 1, 1 });
                        VertexTextureData.put(block.createTexCube());
                    }
                    else if( y == height[x][z]){
                        if(height[x][z] > chunkHeight - (chunkVariation/2)+1){
                            block = blockFactory.getBlock(Block.BlockType.BlockType_Grass);
                            block.setCoords((float)(startX + x * CUBE_LENGTH), 
                                        (float)(y*CUBE_LENGTH+ (int)(CHUNK_SIZE*.8)),
                                        (float)(startZ + z * CUBE_LENGTH));
                            VertexPositionData.put(block.createCube(CUBE_LENGTH));
                            VertexColorData.put(new float[] { 1, 1, 1 });
                            VertexTextureData.put(block.createTexCube());
                        }
                        else{
                            block = blockFactory.getBlock(Block.BlockType.BlockType_Water);
                            block.setCoords((float)(startX + x * CUBE_LENGTH), 
                                        (float)(y*CUBE_LENGTH+ (int)(CHUNK_SIZE*.8)),
                                        (float)(startZ + z * CUBE_LENGTH));
                            VertexPositionData.put(block.createCube(CUBE_LENGTH));
                            VertexColorData.put(new float[] { 1, 1, 1 });
                            VertexTextureData.put(block.createTexCube());
                        }
                    }
                }
            }
        }
        
        VBOColorHandle = glGenBuffers();
        VBOVertexHandle = glGenBuffers();
        VBOTextureHandle = glGenBuffers();
        StartX = startX;
        StartY = startY;
        StartZ = startZ;
        
        VertexColorData.flip();
        VertexPositionData.flip();
        VertexTextureData.flip();
        glBindBuffer(GL_ARRAY_BUFFER, VBOVertexHandle);
        glBufferData(GL_ARRAY_BUFFER, VertexPositionData, GL_STATIC_DRAW);
        glBindBuffer(GL_ARRAY_BUFFER, 0);
        glBindBuffer(GL_ARRAY_BUFFER, VBOColorHandle);
        glBufferData(GL_ARRAY_BUFFER, VertexColorData, GL_STATIC_DRAW);
        glBindBuffer(GL_ARRAY_BUFFER, 0);
        glBindBuffer(GL_ARRAY_BUFFER, VBOTextureHandle);
        glBufferData(GL_ARRAY_BUFFER, VertexTextureData, GL_STATIC_DRAW);
        glBindBuffer(GL_ARRAY_BUFFER, 0);
    }
}

