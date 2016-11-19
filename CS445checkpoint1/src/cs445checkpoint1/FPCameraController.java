/***************************************************************
* file: FPCameraController.java
* Group "Generic Group Name"
*       Bryan Commentz
*       Taylor Stiles
*       Zahy Abou-Diab 
* class: CS 445 - Computer Graphics
*
* assignment: Project Checkpoint 2
* date last modified: 11/14/2016
*
* purpose: At this checkpoint the program creates a window with 
* a chunk.  The top layer of the chunk is randomly generated 
* using Simplex Noise. The cubes are textured with the given
* texture map.
* 
* This is largely repurposed, but modified code from 
* the class slides.
****************************************************************/ 
package cs445checkpoint1;

import java.nio.FloatBuffer;
import org.lwjgl.BufferUtils;
import org.lwjgl.util.vector.Vector3f;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.Display;
import static org.lwjgl.opengl.GL11.*;
import org.lwjgl.Sys;

public class FPCameraController {
    private Vector3f position = null;
    private Vector3f lPosition = null;
    private float yaw = 0.0f;
    private float pitch = 0.0f;
    private Chunk chunk;
    
    //this is the constructor
    public FPCameraController(float x, float y, float z){
        position = new Vector3f(x, y, z);
        lPosition = new Vector3f(x, y, -z);
        lPosition.x = 30f;
        lPosition.y = 70f;
        lPosition.z = 30f;
        chunk = new Chunk(0,0,0);
    }
    
    //increment the camera's current yaw rotation
    public void yaw(float amount)
    {
        yaw += amount;
    }
    
    //increment the camera's current yaw rotation
    public void pitch(float amount){
        pitch -= amount;
    }

    //moves the camera forward relative to its current rotation (yaw)
    public void walkForward(float distance){
        float xOffset = distance * (float)Math.sin(Math.toRadians(yaw));
        float zOffset = distance * (float)Math.cos(Math.toRadians(yaw));
        position.x -= xOffset;
        position.z += zOffset;
        //FloatBuffer lightPosition = BufferUtils.createFloatBuffer(4);
        //lightPosition.put(lPosition.x+=xOffset).put(
        //            lPosition.y).put(lPosition.z-=zOffset).put(1.0f).flip();
        //glLight(GL_LIGHT0, GL_POSITION, lightPosition);
    }
    
    //moves the camera backward relative to its current rotation (yaw)
    public void walkBackwards(float distance)
    {
        float xOffset = distance * (float)Math.sin(Math.toRadians(yaw));
        float zOffset = distance * (float)Math.cos(Math.toRadians(yaw));
        position.x += xOffset;
        position.z -= zOffset;
        //FloatBuffer lightPosition = BufferUtils.createFloatBuffer(4);
        //lightPosition.put(lPosition.x-=xOffset).put(
        //            lPosition.y).put(lPosition.z+=zOffset).put(1.0f).flip();
        //glLight(GL_LIGHT0, GL_POSITION, lightPosition);
    }
    
    //strafes the camera left relative to its current rotation (yaw)
    public void strafeLeft(float distance)
    {
        float xOffset = distance * (float)Math.sin(Math.toRadians(yaw-90));
        float zOffset = distance * (float)Math.cos(Math.toRadians(yaw-90));
        position.x -= xOffset;
        position.z += zOffset;
        //FloatBuffer lightPosition = BufferUtils.createFloatBuffer(4);
        //lightPosition.put(lPosition.x+=xOffset).put(
        //            lPosition.y).put(lPosition.z-=zOffset).put(1.0f).flip();
        //glLight(GL_LIGHT0, GL_POSITION, lightPosition);
    }
    
    //strafes the camera right relative to its current rotation (yaw)
    public void strafeRight(float distance)
    {
        float xOffset = distance * (float)Math.sin(Math.toRadians(yaw+90));
        float zOffset = distance * (float)Math.cos(Math.toRadians(yaw+90));
        position.x -= xOffset;
        position.z += zOffset;
        //FloatBuffer lightPosition = BufferUtils.createFloatBuffer(4);
        //lightPosition.put(lPosition.x+=xOffset).put(
        //            lPosition.y).put(lPosition.z-=zOffset).put(1.0f).flip();
        //glLight(GL_LIGHT0, GL_POSITION, lightPosition);
    }
    
    //moves the camera up relative to its current rotation (yaw)
    public void moveUp(float distance)
    {
        position.y -= distance;
    }
    
    //moves the camera down
    public void moveDown(float distance)
    {
        position.y += distance;
    }
    
    //translates and rotates the matrix so that it looks through the camera
    //this does basically what gluLookAt() does
    public void lookThrough()
    {
        //roatate the pitch around the X axis
        glRotatef(pitch, 1.0f, 0.0f, 0.0f);
        //roatate the yaw around the Y axis
        glRotatef(yaw, 0.0f, 1.0f, 0.0f);
        //translate to the position vector's location
        glTranslatef(position.x, position.y, position.z);
        
        FloatBuffer lightPosition = BufferUtils.createFloatBuffer(4);
        lightPosition.put(lPosition.x).put(lPosition.y).put(lPosition.z)
                                        .put(1.0f).flip();
        glLight(GL_LIGHT0, GL_POSITION, lightPosition);
    }
    
    public void gameLoop() {
        
        FPCameraController camera = new FPCameraController(0, 0, 0);
        float dx = 0.0f;
        float dy = 0.0f;
        float dt = 0.0f; //length of frame
        float lastTime = 0.0f; // when the last frame was
        long time = 0;
        float mouseSensitivity = 0.09f;
        float movementSpeed = .35f;
        //hide the mouse
        Mouse.setGrabbed(true);
        
        // keep looping till the display window is closed the ESC key is down
        while (!Display.isCloseRequested() &&
                !Keyboard.isKeyDown(Keyboard.KEY_ESCAPE))
        {
            time = Sys.getTime();
            lastTime = time;
            
            //distance in mouse movement
            //from the last getDX() call.
            dx = Mouse.getDX();
            
            //distance in mouse movement
            //from the last getDY() call.
            dy = Mouse.getDY();
            
            //control camera yaw from x movement of the mouse
            camera.yaw(dx * mouseSensitivity);
            
            //control camera pitch from y movement of the mouse
            camera.pitch(dy * mouseSensitivity);
            
            //when passing in the distance to move
            //we multiply movementSpeed by dt. This is a time scale
            //so if its a slow frame u move more then a fast frame
            //so on a slow computer you move just as fast as on a fast computer
            
            //move forward
            if (Keyboard.isKeyDown(Keyboard.KEY_W))
            {
                camera.walkForward(movementSpeed);
            }
            
            //move backwards
            if (Keyboard.isKeyDown(Keyboard.KEY_S))
            {
                camera.walkBackwards(movementSpeed);
            }
            
            //strafe left
            if (Keyboard.isKeyDown(Keyboard.KEY_A)){
                camera.strafeLeft(movementSpeed);
            }
            
            //strafe right
            if (Keyboard.isKeyDown(Keyboard.KEY_D)){ 
                camera.strafeRight(movementSpeed);
            }
            
            //move up
            if (Keyboard.isKeyDown(Keyboard.KEY_SPACE)){
                camera.moveUp(movementSpeed);
            }
            
            //move down
            if (Keyboard.isKeyDown(Keyboard.KEY_LSHIFT)) {
                camera.moveDown(movementSpeed);
            }
            
            //set the modelview matrix back to the identity
            glLoadIdentity();
            
            //look through the camera before you draw anything
            camera.lookThrough();
            
            glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
            
            //you would draw your scene here.
            chunk.render();
            
            //draw the buffer to the screen
            Display.update();
            Display.sync(60);
        }
        Display.destroy();
    }
        

        
    private void render() {
        try{
            glBegin(GL_QUADS);
                //front
                glColor3f(0.0f,0.0f,1.0f);
                glVertex3f( 1.0f,-1.0f, -1.0f);
                glVertex3f( 1.0f, 1.0f, -1.0f);
                glVertex3f(-1.0f, 1.0f, -1.0f);
                glVertex3f(-1.0f,-1.0f, -1.0f);
                //back
                glColor3f(0.0f,1.0f,0.0f);
                glVertex3f(-1.0f,-1.0f, 1.0f);
                glVertex3f(-1.0f, 1.0f, 1.0f);
                glVertex3f( 1.0f, 1.0f, 1.0f);
                glVertex3f( 1.0f,-1.0f, 1.0f);
                //top
                glColor3f(0.0f,1.0f,1.0f);
                glVertex3f( 1.0f, 1.0f, 1.0f);
                glVertex3f( 1.0f, 1.0f,-1.0f);
                glVertex3f(-1.0f, 1.0f,-1.0f);
                glVertex3f(-1.0f, 1.0f, 1.0f);
                //bottom
                glColor3f(1.0f,0.0f,0.0f);
                glVertex3f( 1.0f,-1.0f,-1.0f);
                glVertex3f( 1.0f,-1.0f, 1.0f);
                glVertex3f(-1.0f,-1.0f, 1.0f);
                glVertex3f(-1.0f,-1.0f,-1.0f);
                //left
                glColor3f(1.0f,0.0f,1.0f);
                glVertex3f( 1.0f,-1.0f,-1.0f);
                glVertex3f( 1.0f, 1.0f,-1.0f);
                glVertex3f(1.0f, 1.0f,1.0f);
                glVertex3f( 1.0f, -1.0f, 1.0f);
                //right
                glColor3f(1.0f,1.0f,0.0f);
                glVertex3f(-1.0f,-1.0f, 1.0f);
                glVertex3f(-1.0f, 1.0f, 1.0f);
                glVertex3f(-1.0f, 1.0f,-1.0f);
                glVertex3f(-1.0f,-1.0f,-1.0f);
            glEnd();
        }catch(Exception e){
        }
    }
}
  