/***************************************************************
* file: Chunk.java
* Group "Generic Group Name"
*       Bryan Commentz
*       Taylor Stiles
*       Zahy Abou-Diab  
* class: CS 445 - Computer Graphics
*
* assignment: Project Checkpoint 2
* date last modified: 11/14/2016
*
* purpose: Following the classroom demonstration, this class
* builds a chunk of random block composition and a jagged top 
* defined by a Simplex Noise map.  
****************************************************************/ 

package cs445checkpoint1;

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
    
    public void rebuildMesh(float startX, float startY, float startZ) {
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
        
        for (float x = 0; x < CHUNK_SIZE; x++) {
            for (float z = 0; z < CHUNK_SIZE; z++) {
                float height = (startY + 10 + (int)( 4 * n.getNoise((int)x,(int)z)));
                //System.out.println(height);
                for(float y = 0; y <= height; y++){
                    VertexPositionData.put(createCube((float)(startX + x * CUBE_LENGTH),
                                                              (float)(y*CUBE_LENGTH+ (int)(CHUNK_SIZE*.8)),
                                                              (float)(startZ + z * CUBE_LENGTH))
                                           );

                    VertexColorData.put( createCubeVertexCol( getCubeColor( Blocks[(int) x] [(int) y][(int) z])));
                    VertexTextureData.put(createTexCube((float) 0, (float)0, Blocks[(int)(x)][(int) (y)][(int) (z)]));
                }
            }
        }

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

    private float[] createCubeVertexCol(float[] CubeColorArray) {
        float[] cubeColors = new float[CubeColorArray.length * 4 * 6];
        for (int i = 0; i < cubeColors.length; i++) {
            cubeColors[i] = CubeColorArray[i %
            CubeColorArray.length];
        }
        return cubeColors;
    }

    public static float[] createCube(float x, float y, float z) {
        int offset = CUBE_LENGTH / 2;
        return new float[] {
            // TOP QUAD
            x + offset, y + offset, z,
            x - offset, y + offset, z,
            x - offset, y + offset, z - CUBE_LENGTH,
            x + offset, y + offset, z - CUBE_LENGTH,
            // BOTTOM QUAD
            x + offset, y - offset, z - CUBE_LENGTH,
            x - offset, y - offset, z - CUBE_LENGTH,
            x - offset, y - offset, z,
            x + offset, y - offset, z,
            // FRONT QUAD
            x + offset, y + offset, z - CUBE_LENGTH,
            x - offset, y + offset, z - CUBE_LENGTH,
            x - offset, y - offset, z - CUBE_LENGTH,
            x + offset, y - offset, z - CUBE_LENGTH,
            // BACK QUAD
            x + offset, y - offset, z,
            x - offset, y - offset, z,
            x - offset, y + offset, z,
            x + offset, y + offset, z,
            // LEFT QUAD
            x - offset, y + offset, z - CUBE_LENGTH,
            x - offset, y + offset, z,
            x - offset, y - offset, z,
            x - offset, y - offset, z - CUBE_LENGTH,
            // RIGHT QUAD
            x + offset, y + offset, z,
            x + offset, y + offset, z - CUBE_LENGTH,
            x + offset, y - offset, z - CUBE_LENGTH,
            x + offset, y - offset, z 
        };
    }

    private float[] getCubeColor(Block block) {
        return new float[] { 1, 1, 1 };
    }

    public static float[] createTexCube(float x, float y, Block block) {
        float offset = (1024f/16)/1024f;
        switch (block.GetID()) {
            case 0://grass
                return new float[] {
                    //Top
                    x + offset*3, y + offset*10,
                    x + offset*2, y + offset*10,
                    x + offset*2, y + offset*9,
                    x + offset*3, y + offset*9,
                    //bottom
                    x + offset*3, y + offset*1,
                    x + offset*2, y + offset*1,
                    x + offset*2, y + offset*0,
                    x + offset*3, y + offset*0,
                    //side
                    x + offset*3, y + offset*0,
                    x + offset*4, y + offset*0,
                    x + offset*4, y + offset*1,
                    x + offset*3, y + offset*1,
                    //side
                    x + offset*4, y + offset*1,
                    x + offset*3, y + offset*1,
                    x + offset*3, y + offset*0,
                    x + offset*4, y + offset*0,
                    //side
                    x + offset*3, y + offset*0,
                    x + offset*4, y + offset*0,
                    x + offset*4, y + offset*1,
                    x + offset*3, y + offset*1,
                    //side
                    x + offset*3, y + offset*0,
                    x + offset*4, y + offset*0,
                    x + offset*4, y + offset*1,
                    x + offset*3, y + offset*1
                };  
            case 1://sand
                return new float[] {
                    //Top
                    x + offset*3, y + offset*2,
                    x + offset*2, y + offset*2,
                    x + offset*2, y + offset*1,
                    x + offset*3, y + offset*1,
                    //bottom
                    x + offset*3, y + offset*2,
                    x + offset*2, y + offset*2,
                    x + offset*2, y + offset*1,
                    x + offset*3, y + offset*1,
                    //side
                    x + offset*3, y + offset*2,
                    x + offset*2, y + offset*2,
                    x + offset*2, y + offset*1,
                    x + offset*3, y + offset*1,
                    //side
                    x + offset*3, y + offset*2,
                    x + offset*2, y + offset*2,
                    x + offset*2, y + offset*1,
                    x + offset*3, y + offset*1,
                    //side
                    x + offset*3, y + offset*2,
                    x + offset*2, y + offset*2,
                    x + offset*2, y + offset*1,
                    x + offset*3, y + offset*1,
                    //side
                    x + offset*3, y + offset*2,
                    x + offset*2, y + offset*2,
                    x + offset*2, y + offset*1,
                    x + offset*3, y + offset*1
                };
            case 2://water 15,13
                return new float[] {
                    //Top
                    x + offset*15, y + offset*13,
                    x + offset*14, y + offset*13,
                    x + offset*14, y + offset*12,
                    x + offset*15, y + offset*12,
                    //bottom
                    x + offset*15, y + offset*13,
                    x + offset*14, y + offset*13,
                    x + offset*14, y + offset*12,
                    x + offset*15, y + offset*12,
                    //side
                    x + offset*14, y + offset*12,
                    x + offset*15, y + offset*12,
                    x + offset*15, y + offset*13,
                    x + offset*14, y + offset*13,
                    //side
                    x + offset*15, y + offset*13,
                    x + offset*14, y + offset*13,
                    x + offset*14, y + offset*12,
                    x + offset*15, y + offset*12,
                    //side
                    x + offset*14, y + offset*12,
                    x + offset*15, y + offset*12,
                    x + offset*15, y + offset*13,
                    x + offset*14, y + offset*13,
                    //side
                    x + offset*14, y + offset*12,
                    x + offset*15, y + offset*12,
                    x + offset*15, y + offset*13,
                    x + offset*14, y + offset*13
                };
            case 3://dirt
                return new float[] {
                    //Top
                    x + offset*3, y + offset*1,
                    x + offset*2, y + offset*1,
                    x + offset*2, y + offset*0,
                    x + offset*3, y + offset*0,
                    //bottom
                    x + offset*3, y + offset*1,
                    x + offset*2, y + offset*1,
                    x + offset*2, y + offset*0,
                    x + offset*3, y + offset*0,
                    //side
                    x + offset*2, y + offset*0,
                    x + offset*3, y + offset*0,
                    x + offset*3, y + offset*1,
                    x + offset*4, y + offset*1,
                    //side
                    x + offset*3, y + offset*1,
                    x + offset*2, y + offset*1,
                    x + offset*2, y + offset*0,
                    x + offset*3, y + offset*0,
                    //side
                    x + offset*2, y + offset*0,
                    x + offset*3, y + offset*0,
                    x + offset*3, y + offset*1,
                    x + offset*2, y + offset*1,
                    //side
                    x + offset*2, y + offset*0,
                    x + offset*3, y + offset*0,
                    x + offset*3, y + offset*1,
                    x + offset*2, y + offset*1
                };
            case 4://stone
                return new float[] {
                    //Top
                    x + offset*2, y + offset*1,
                    x + offset*1, y + offset*1,
                    x + offset*1, y + offset*0,
                    x + offset*2, y + offset*0,
                    //bottom
                    x + offset*2, y + offset*1,
                    x + offset*1, y + offset*1,
                    x + offset*1, y + offset*0,
                    x + offset*2, y + offset*0,
                    //side
                    x + offset*2, y + offset*1,
                    x + offset*1, y + offset*1,
                    x + offset*1, y + offset*0,
                    x + offset*2, y + offset*0,
                    //side
                    x + offset*2, y + offset*1,
                    x + offset*1, y + offset*1,
                    x + offset*1, y + offset*0,
                    x + offset*2, y + offset*0,
                    //side
                    x + offset*2, y + offset*1,
                    x + offset*1, y + offset*1,
                    x + offset*1, y + offset*0,
                    x + offset*2, y + offset*0,
                    //side
                    x + offset*2, y + offset*1,
                    x + offset*1, y + offset*1,
                    x + offset*1, y + offset*0,
                    x + offset*2, y + offset*0
                };
                
            case 5://bedrock
                return new float[] {
                    //Top
                    x + offset*2, y + offset*2,
                    x + offset*1, y + offset*2,
                    x + offset*1, y + offset*1,
                    x + offset*2, y + offset*1,
                    //bottom
                    x + offset*2, y + offset*2,
                    x + offset*1, y + offset*2,
                    x + offset*1, y + offset*1,
                    x + offset*2, y + offset*1,
                    //side
                    x + offset*1, y + offset*1,
                    x + offset*2, y + offset*1,
                    x + offset*2, y + offset*2,
                    x + offset*1, y + offset*2,
                    //side
                    x + offset*2, y + offset*2,
                    x + offset*1, y + offset*2,
                    x + offset*1, y + offset*1,
                    x + offset*2, y + offset*1,
                    //side
                    x + offset*1, y + offset*1,
                    x + offset*2, y + offset*1,
                    x + offset*2, y + offset*2,
                    x + offset*1, y + offset*2,
                    //side
                    x + offset*1, y + offset*1,
                    x + offset*2, y + offset*1,
                    x + offset*2, y + offset*2,
                    x + offset*1, y + offset*2
                };
            default: //dirt
                return new float[] {
                    //Top
                    x + offset*3, y + offset*1,
                    x + offset*2, y + offset*1,
                    x + offset*2, y + offset*0,
                    x + offset*3, y + offset*0,
                    //bottom
                    x + offset*3, y + offset*1,
                    x + offset*2, y + offset*1,
                    x + offset*2, y + offset*0,
                    x + offset*3, y + offset*0,
                    //side
                    x + offset*2, y + offset*0,
                    x + offset*3, y + offset*0,
                    x + offset*3, y + offset*1,
                    x + offset*4, y + offset*1,
                    //side
                    x + offset*3, y + offset*1,
                    x + offset*2, y + offset*1,
                    x + offset*2, y + offset*0,
                    x + offset*3, y + offset*0,
                    //side
                    x + offset*2, y + offset*0,
                    x + offset*3, y + offset*0,
                    x + offset*3, y + offset*1,
                    x + offset*2, y + offset*1,
                    //side
                    x + offset*2, y + offset*0,
                    x + offset*3, y + offset*0,
                    x + offset*3, y + offset*1,
                    x + offset*2, y + offset*1
                };
        }
    }
    
    public Chunk(int startX, int startY, int startZ) {
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
        
        for (int x = 0; x < CHUNK_SIZE; x++) {
            for (int z = 0; z < CHUNK_SIZE; z++) {
                height[x][z] = (startY + chunkHeight + (int)( chunkVariation * n.getNoise((int)x,(int)z)));
                for (int y = 0; y <= height[x][z]; y++) { 
                    if(y == 0){
                        Blocks[x][y][z] = new Block(Block.BlockType.BlockType_Bedrock);
                    }
                    else if( y < height[x][z] - 1){
                        Blocks[x][y][z] = new Block(Block.BlockType.BlockType_Stone);
                    }
                    else if( y == height[x][z] - 1){
                        Blocks[x][y][z] = new Block(Block.BlockType.BlockType_Dirt);
                    }
                    else if( y == height[x][z]){
                        if(height[x][z] > chunkHeight - (chunkVariation/2)+1){
                            Blocks[x][y][z] = new Block(Block.BlockType.BlockType_Grass);
                        }
                        else{
                            Blocks[x][y][z] = new Block(Block.BlockType.BlockType_Water);
                        }
                    }
                }
            }
        }
        
        for (int x = 0; x < CHUNK_SIZE; x++) {
            for (int z = 0; z < CHUNK_SIZE; z++) {
                for (int y = 0; y <= height[x][z]; y++) {
                    
                    if(Blocks[x][y][z].GetID() == 2 ){
                        for(int iX = x-1; iX <= x+1; iX++){
                            if(iX >= 0 && iX < CHUNK_SIZE){
                                for(int iZ = z-1; iZ <= z+1; iZ++){
                                    if(iZ >= 0 && iZ < CHUNK_SIZE){
                                        for(int iY = y-1; iY<=y+1; iY++){
                                            if(iY >= 0 && iY <= height[iX][iZ]){
                                                if(Blocks[iX][iY][iZ].GetID() == 3 ||Blocks[iX][iY][iZ].GetID() == 0){
                                                    Blocks[iX][iY][iZ].setBlockType(Block.BlockType.BlockType_Sand);
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                    
                    VertexPositionData.put(createCube((float)(startX + x * CUBE_LENGTH),
                                          (float)(y*CUBE_LENGTH+ (int)(CHUNK_SIZE*.8)),
                                          (float)(startZ + z * CUBE_LENGTH))
                    );
                    VertexColorData.put( createCubeVertexCol( getCubeColor( Blocks[x][y][z] )));
                    VertexTextureData.put(createTexCube((float) 0, (float)0, Blocks[x][y][z]));
                }
            }
        }
        
        
        VBOColorHandle = glGenBuffers();
        VBOVertexHandle = glGenBuffers();
        VBOTextureHandle = glGenBuffers();
        StartX = startX;
        StartY = startY;
        StartZ = startZ;
        //rebuildMesh(startX, startY, startZ);
        
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

