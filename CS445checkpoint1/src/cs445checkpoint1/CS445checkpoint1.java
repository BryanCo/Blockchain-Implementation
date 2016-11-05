/***************************************************************
* file: CS445checkpoint1.java
* Group "Generic Group Name
*       Bryan Commentz
*       Taylor Stiles
*       Zahy Abou-Diab  
* class: CS 445 - Computer Graphics
*
* assignment: Project Checkpoint 1
* date last modified: 10/31/2016
*
* purpose: At this point the program creates a window with a 
* multicolored cube and a movable camera.
* This is strongly based on the code given in class slides. 
****************************************************************/ 

package cs445checkpoint1;

import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import static org.lwjgl.opengl.GL11.*;
import org.lwjgl.util.glu.GLU;

public class CS445checkpoint1 {
    
    private FPCameraController fp = new FPCameraController(0f,0f,0f);
    private DisplayMode displayMode;

    
    //Provides the big picture flow of the program.
    public void start() {
        try {
            createWindow();
            initGL();
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
    }
    
    //This is the Main method
    public static void main(String[] args) {
        CS445checkpoint1 cubeWindow = new CS445checkpoint1();
        cubeWindow.start();
    }
    
}