package com.bramerlabs.engine.objects.game_objects;

import com.bramerlabs.engine.graphics.Material;
import com.bramerlabs.engine.graphics.Mesh;
import com.bramerlabs.engine.graphics.Vertex;
import com.bramerlabs.engine.math.Vector2f;
import com.bramerlabs.engine.math.Vector3f;
import com.bramerlabs.engine.objects.GameObject;

public class Cube extends GameObject {

    /**
     *
     * @param position - the position of this cube
     * @param rotation - the rotation of this cube
     * @param scale - the scale of this cube
     * @param pathToTexture - the path to the texture of this cube
     */
    public Cube(Vector3f position, Vector3f rotation, Vector3f scale, String pathToTexture) {
        super(new Mesh(new Vertex[] {
                // front face
                new Vertex(new Vector3f(-0.5f, -0.5f,  0.5f), new Vector2f(0.5f, 0.5f)), // 0, 1
                new Vertex(new Vector3f( 0.5f, -0.5f,  0.5f), new Vector2f(1.0f, 0.5f)), // 1, 1
                new Vertex(new Vector3f( 0.5f,  0.5f,  0.5f), new Vector2f(1.0f, 0.0f)), // 1, 0
                new Vertex(new Vector3f(-0.5f,  0.5f,  0.5f), new Vector2f(0.5f, 0.0f)), // 0, 0

                // back face
                new Vertex(new Vector3f(-0.5f, -0.5f, -0.5f), new Vector2f(1.0f, 0.5f)),
                new Vertex(new Vector3f( 0.5f, -0.5f, -0.5f), new Vector2f(0.5f, 0.5f)),
                new Vertex(new Vector3f( 0.5f,  0.5f, -0.5f), new Vector2f(0.5f, 0.0f)),
                new Vertex(new Vector3f(-0.5f,  0.5f, -0.5f), new Vector2f(1.0f, 0.0f)),

                // right face
                new Vertex(new Vector3f( 0.5f, -0.5f,  0.5f), new Vector2f(0.5f, 0.5f)),
                new Vertex(new Vector3f( 0.5f,  0.5f,  0.5f), new Vector2f(0.5f, 0.0f)),
                new Vertex(new Vector3f( 0.5f, -0.5f, -0.5f), new Vector2f(1.0f, 0.5f)),
                new Vertex(new Vector3f( 0.5f,  0.5f, -0.5f), new Vector2f(1.0f, 0.0f)),

                // left face
                new Vertex(new Vector3f(-0.5f, -0.5f,  0.5f), new Vector2f(1.0f, 0.5f)),
                new Vertex(new Vector3f(-0.5f,  0.5f,  0.5f), new Vector2f(1.0f, 0.0f)),
                new Vertex(new Vector3f(-0.5f, -0.5f, -0.5f), new Vector2f(0.5f, 0.5f)),
                new Vertex(new Vector3f(-0.5f,  0.5f, -0.5f), new Vector2f(0.5f, 0.0f)),

                // top face
                new Vertex(new Vector3f( 0.5f,  0.5f,  0.5f), new Vector2f(0.5f, 0.5f)),
                new Vertex(new Vector3f(-0.5f,  0.5f,  0.5f), new Vector2f(0.0f, 0.5f)),
                new Vertex(new Vector3f( 0.5f,  0.5f, -0.5f), new Vector2f(0.5f, 0.0f)),
                new Vertex(new Vector3f(-0.5f,  0.5f, -0.5f), new Vector2f(0.0f, 0.0f)),

                // bottom face
                new Vertex(new Vector3f(-0.5f, -0.5f,  0.5f), new Vector2f(1.0f, 1.0f)),
                new Vertex(new Vector3f( 0.5f, -0.5f,  0.5f), new Vector2f(0.5f, 1.0f)),
                new Vertex(new Vector3f(-0.5f, -0.5f, -0.5f), new Vector2f(1.0f, 0.5f)),
                new Vertex(new Vector3f( 0.5f, -0.5f, -0.5f), new Vector2f(0.5f, 0.5f)),
        }, new int[] {
                // front face
                0, 1, 2,
                2, 3, 0,

                // back face
                5, 4, 7,
                5, 7, 6,

                // right face
                8, 10, 11,
                8, 11, 9,

                // left face
                14, 12, 13,
                14, 13, 15,

                // top face
                17, 16, 18,
                17, 18, 19,

                // bottom face
                20, 22, 23,
                20, 23, 21,
        }, new Material(pathToTexture)), position, rotation, scale);
    }



    /**
     * constructor for specified position with default rotation [0, 0, 0] and default scale [1, 1, 1]
     * @param position - the position of this cube
     * @param pathToTexture - the path to the texture of this cube
     */
    public Cube(Vector3f position, String pathToTexture) {
        super(new Mesh(new Vertex[] {
                // front face
                new Vertex(new Vector3f(-0.5f, -0.5f,  0.5f), new Vector2f(0.5f, 0.5f)), // 0, 1
                new Vertex(new Vector3f( 0.5f, -0.5f,  0.5f), new Vector2f(1.0f, 0.5f)), // 1, 1
                new Vertex(new Vector3f( 0.5f,  0.5f,  0.5f), new Vector2f(1.0f, 0.0f)), // 1, 0
                new Vertex(new Vector3f(-0.5f,  0.5f,  0.5f), new Vector2f(0.5f, 0.0f)), // 0, 0

                // back face
                new Vertex(new Vector3f(-0.5f, -0.5f, -0.5f), new Vector2f(1.0f, 0.5f)),
                new Vertex(new Vector3f( 0.5f, -0.5f, -0.5f), new Vector2f(0.5f, 0.5f)),
                new Vertex(new Vector3f( 0.5f,  0.5f, -0.5f), new Vector2f(0.5f, 0.0f)),
                new Vertex(new Vector3f(-0.5f,  0.5f, -0.5f), new Vector2f(1.0f, 0.0f)),

                // right face
                new Vertex(new Vector3f( 0.5f, -0.5f,  0.5f), new Vector2f(0.5f, 0.5f)),
                new Vertex(new Vector3f( 0.5f,  0.5f,  0.5f), new Vector2f(0.5f, 0.0f)),
                new Vertex(new Vector3f( 0.5f, -0.5f, -0.5f), new Vector2f(1.0f, 0.5f)),
                new Vertex(new Vector3f( 0.5f,  0.5f, -0.5f), new Vector2f(1.0f, 0.0f)),

                // left face
                new Vertex(new Vector3f(-0.5f, -0.5f,  0.5f), new Vector2f(1.0f, 0.5f)),
                new Vertex(new Vector3f(-0.5f,  0.5f,  0.5f), new Vector2f(1.0f, 0.0f)),
                new Vertex(new Vector3f(-0.5f, -0.5f, -0.5f), new Vector2f(0.5f, 0.5f)),
                new Vertex(new Vector3f(-0.5f,  0.5f, -0.5f), new Vector2f(0.5f, 0.0f)),

                // top face
                new Vertex(new Vector3f( 0.5f,  0.5f,  0.5f), new Vector2f(0.5f, 0.5f)),
                new Vertex(new Vector3f(-0.5f,  0.5f,  0.5f), new Vector2f(0.0f, 0.5f)),
                new Vertex(new Vector3f( 0.5f,  0.5f, -0.5f), new Vector2f(0.5f, 0.0f)),
                new Vertex(new Vector3f(-0.5f,  0.5f, -0.5f), new Vector2f(0.0f, 0.0f)),

                // bottom face
                new Vertex(new Vector3f(-0.5f, -0.5f,  0.5f), new Vector2f(1.0f, 1.0f)),
                new Vertex(new Vector3f( 0.5f, -0.5f,  0.5f), new Vector2f(0.5f, 1.0f)),
                new Vertex(new Vector3f(-0.5f, -0.5f, -0.5f), new Vector2f(1.0f, 0.5f)),
                new Vertex(new Vector3f( 0.5f, -0.5f, -0.5f), new Vector2f(0.5f, 0.5f)),
        }, new int[] {
                // front face
                0, 1, 2,
                2, 3, 0,

                // back face
                5, 4, 7,
                5, 7, 6,

                // right face
                8, 10, 11,
                8, 11, 9,

                // left face
                14, 12, 13,
                14, 13, 15,

                // top face
                17, 16, 18,
                17, 18, 19,

                // bottom face
                20, 22, 23,
                20, 23, 21,
        }, new Material(pathToTexture)), position, new Vector3f(0, 0, 0), new Vector3f(1, 1, 1));
    }
}
