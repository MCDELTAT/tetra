package com.genocide.fbrowser;

import android.util.Log;

import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.nio.ByteBuffer;
import java.nio.ShortBuffer;
import java.util.Random;

import javax.microedition.khronos.opengles.GL10;

public class MyDot {
    //Declare some variables
    String OrgName;
    Random randomNum = new Random();

    //This is a points coordinates. It is set to random
    //positive integers as default.
    float vertices[] = {
            randomNum.nextFloat(), randomNum.nextFloat()

    };

    //Create buffers
    private FloatBuffer vertBuff;

    private short[] pIndex = {0};

    private ShortBuffer pBuff;

    public void setupVertices(float x, float y, String name){
        //Set the vertices and give it a name
        //The first coordinate would plot the negation of the input
        //so that is why it is multiplied by -1
        vertices[0]=x*(-1);
        vertices[1]=y;
        OrgName=name;

        //The code is the same as the MyDot class because this needs to be
        //done again when we input coordinates. Otherwise, the coordinates
        //would be the default ones we set.
        ByteBuffer bBuff = ByteBuffer.allocateDirect(vertices.length * 4);
        bBuff.order(ByteOrder.nativeOrder());
        vertBuff = bBuff.asFloatBuffer();
        vertBuff.put(vertices);
        vertBuff.position(0);

        ByteBuffer pbBuff = ByteBuffer.allocateDirect(pIndex.length * 2);
        pbBuff.order(ByteOrder.nativeOrder());
        pBuff = pbBuff.asShortBuffer();
        pBuff.put(pIndex);
        pBuff.position(0);
        Log.d("HI","Dot class again.");
    }

    public void print(){
        Log.d("NAME",OrgName);
    }

    public MyDot(){

        ByteBuffer bBuff = ByteBuffer.allocateDirect(vertices.length * 4);
        bBuff.order(ByteOrder.nativeOrder());
        vertBuff = bBuff.asFloatBuffer();
        vertBuff.put(vertices);
        vertBuff.position(0);

        ByteBuffer pbBuff = ByteBuffer.allocateDirect(pIndex.length * 2);
        pbBuff.order(ByteOrder.nativeOrder());
        pBuff = pbBuff.asShortBuffer();
        pBuff.put(pIndex);
        pBuff.position(0);

        Log.d("HI","Dot class.");
    }
    public void draw(GL10 gl, float one, float two, float three, float four) {
        //FrontFace is set to clockwise
        gl.glFrontFace(GL10.GL_CW);

        gl.glEnableClientState(GL10.GL_VERTEX_ARRAY);

        //Set size of our point
        gl.glEnable(GL10.GL_POINT_SIZE);
        gl.glPointSize(12);

        //Set color
        //gl.glColor4f(.72f, .35f, .2f, .15f);
        gl.glColor4f(one, two, three, four);
        //Drawing the point
        gl.glVertexPointer(2, GL10.GL_FLOAT, 0, vertBuff);
        gl.glDrawElements(GL10.GL_POINTS, pIndex.length,GL10.GL_UNSIGNED_SHORT,pBuff);

        gl.glDisableClientState(GL10.GL_VERTEX_ARRAY);
    }
}