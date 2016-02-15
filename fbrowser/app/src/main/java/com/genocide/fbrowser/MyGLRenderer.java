package com.genocide.fbrowser;

import android.opengl.GLSurfaceView;
import android.opengl.GLU;

import java.util.Random;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

public class MyGLRenderer implements GLSurfaceView.Renderer {

    //Create objects
    private EndPoints mshape;
    private MyDot mdot;

    @Override
    public void onSurfaceCreated(GL10 gl, EGLConfig config) {
        //In case we need to generate a random number
        Random randomNum = new Random();

        //Set the 'background' color
        gl.glClearColor(.6f, .8f, .5f, .1f);
        gl.glClearDepthf(1f);

        //Call constructor
        mshape = new EndPoints();
        mdot = new MyDot();
    }

    @Override
    public void onDrawFrame(GL10 gl) {
        gl.glDisable(GL10.GL_DITHER);
        gl.glClear(GL10.GL_COLOR_BUFFER_BIT | GL10.GL_DEPTH_BUFFER_BIT);

        //Set view
        gl.glMatrixMode(GL10.GL_MODELVIEW);
        gl.glLoadIdentity();
        GLU.gluLookAt(gl, 0, 0, -3, 0f, 0f, 0f, 0f, 1.0f, 0f);

        //Create a new dot inside function
        MyDot happy;
        happy = new MyDot();

        //This draws the endpoints
        mshape.draw(gl);

        //Here is an example of setting the coordinates
        //and assigning a name.
        mdot.setupVertices(-.75f, -.75f, "one");
        mdot.print();
        mdot.draw(gl);

        mdot.setupVertices(0, 0, "two");
        mdot.print();
        mdot.draw(gl);

        happy.setupVertices(.75f, .75f, "happy");
        happy.draw(gl);
        happy.print();
    }

    @Override
    public void onSurfaceChanged(GL10 gl, int width, int height) {
        //This function is for when the screen goes into landscape view
        gl.glViewport(0, 0, width, height);
        float ratio = (float) width/height;
        gl.glMatrixMode(GL10.GL_PROJECTION);
        gl.glLoadIdentity();
        gl.glFrustumf(-ratio,ratio,-1,.5f,1,20);
    }
}