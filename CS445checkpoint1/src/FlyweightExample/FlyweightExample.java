/***************************************************************
* file: CS445_Project.java
* Group "Generic Group Name"
*       Bryan Commentz
*       Taylor Stiles
*       Zahy Abou-Diab  
* class: CS 445 - Computer Graphics
*
* assignment: CS445-Project
* date last modified: 11/28/2016
*
* purpose: This program creates a window with a randomly generated
* "Minecraft" style terrain.  
* 
* Additional Features: 
* 1) Support for multiple chunks.
* 2) The light source slowly moves to create a waxing and waning day/night cycle.  
* 3) The user can use the IJKL keys to move the light source.
****************************************************************/ 

package FlyweightExample;

import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import static org.lwjgl.opengl.GL11.*;
import org.lwjgl.util.glu.GLU;
import java.nio.FloatBuffer;
import org.lwjgl.BufferUtils;

public class FlyweightExample {
    
    private FPCameraController fp;
    private DisplayMode displayMode;
    private FloatBuffer lightPosition;
    private FloatBuffer whiteLight;

    
    //Provides the big picture flow of the program.
    public void start() {
        try {
            createWindow();
            initGL();
            fp = new FPCameraController(0f,0f,0f);
            fp.gameLoop();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    //creates the window to be used.
    private void createWindow() throws Exception{
        Display.setFullscreen(false);
        DisplayMode d[] = Display.getAvailableDisplayModes();
        for (int i = 0; i < d.length; i++) {
            if (d[i].getWidth() == 640
                && d[i].getHeight() == 480
                && d[i].getBitsPerPixel() == 32) 
            {
                displayMode = d[i];
                break;
            }
        }
        Display.setDisplayMode(displayMode);
        Display.setTitle("Flyweight Example");
        Display.create();
    }
    
    //Initializes OpenGL
    private void initGL() {
        glClearColor(0.0f, 0.0f, 0.0f, 0.0f);
        glMatrixMode(GL_PROJECTION);
        glLoadIdentity();
        GLU.gluPerspective(100.0f, (float)displayMode.getWidth()/(float)
        displayMode.getHeight(), 0.1f, 300.0f);
        glMatrixMode(GL_MODELVIEW);
        glHint(GL_PERSPECTIVE_CORRECTION_HINT, GL_NICEST);
        glEnableClientState(GL_VERTEX_ARRAY);
        glEnableClientState(GL_COLOR_ARRAY);
        
        //this line instructs openGL to consider the polygon's 
        //distance from the user and only render the surface closest 
        //to the user.
        glEnable(GL_DEPTH_TEST);
        
        glEnable(GL_TEXTURE_2D);
        glEnableClientState (GL_TEXTURE_COORD_ARRAY);
        
        initLightArrays();
        glLight(GL_LIGHT0, GL_POSITION, lightPosition); //sets our light’s position
        glLight(GL_LIGHT0, GL_SPECULAR, whiteLight);//sets our specular light
        glLight(GL_LIGHT0, GL_DIFFUSE, whiteLight);//sets our diffuse light
        glLight(GL_LIGHT0, GL_AMBIENT, whiteLight);//sets our ambient light
        glEnable(GL_LIGHTING);//enables our lighting
        glEnable(GL_LIGHT0);//enables light0
    }
    
    private void initLightArrays() {
        lightPosition = BufferUtils.createFloatBuffer(4);
        lightPosition.put(0.0f).put(0.0f).put(0.0f).put(1.0f).flip();
        whiteLight = BufferUtils.createFloatBuffer(4);
        whiteLight.put(1.0f).put(1.0f).put(1.0f).put(1.0f).flip();
    }

    
    //This is the Main method
    public static void main(String[] args) {
        FlyweightExample cubeWindow = new FlyweightExample();
        cubeWindow.start();
    }
    
}