package com.genocide.fbrowser;


import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.nio.ByteBuffer;
import java.nio.ShortBuffer;

import javax.microedition.khronos.opengles.GL10;

public class EndPoints {
     float vertices[] = {
            1f, 1f,
            1f, -1f,
            -1f, -1f,
            -1, 1f
    };

    private FloatBuffer vertBuff;

    private short[] pIndex = {0, 1, 2, 3};

    private ShortBuffer pBuff;

    public EndPoints(){
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
    }
    public void draw(GL10 gl) {
        gl.glFrontFace(GL10.GL_CW);
        gl.glEnableClientState(GL10.GL_VERTEX_ARRAY);
        gl.glEnable(GL10.GL_POINT_SIZE);
        gl.glPointSize(5);
        gl.glColor4f(.2f, .5f, .2f, .3f);
        gl.glVertexPointer(2, GL10.GL_FLOAT, 0, vertBuff);
        gl.glDrawElements(GL10.GL_POINTS, pIndex.length,GL10.GL_UNSIGNED_SHORT,pBuff);
        gl.glDisableClientState(GL10.GL_VERTEX_ARRAY);
    }
}
