/***************************************************************
* file: CS445checkpoint1.java
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
****************************************************************/ 

package cs445checkpoint1;

import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import static org.lwjgl.opengl.GL11.*;
import org.lwjgl.util.glu.GLU;

public class CS445checkpoint1 {
    
    private FPCameraController fp;
    private DisplayMode displayMode;

    
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
        Display.setTitle("CS 445 Project Checkpoint 1");
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
    }
    
    //This is the Main method
    public static void main(String[] args) {
        CS445checkpoint1 cubeWindow = new CS445checkpoint1();
        cubeWindow.start();
    }
    
}