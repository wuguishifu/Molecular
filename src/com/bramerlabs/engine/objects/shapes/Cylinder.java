package com.bramerlabs.engine.objects.shapes;

import com.bramerlabs.engine.graphics.Mesh;
import com.bramerlabs.engine.graphics.Vertex;
import com.bramerlabs.engine.math.Triangle;
import com.bramerlabs.engine.math.Vector3f;
import com.bramerlabs.engine.objects.RenderObject;

import java.util.ArrayList;

public class Cylinder extends RenderObject {

    // the position of this cylinder
    private Vector3f position;

    // the rotation of this cylinder
    private static Vector3f rotation = new Vector3f(0);

    // the scale of this cylinder
    private static Vector3f scale = new Vector3f(1);

    /**
     * default constructor for specified values
     *
     * @param mesh     - the mesh that this object is made of
     * @param position - the position of this object
     * @param rotation - the rotation of this object
     * @param scale    - the scale of this object
     */
    public Cylinder(Mesh mesh, Vector3f position, Vector3f rotation, Vector3f scale) {
        super(mesh, position, rotation, scale);
    }

    public static Cylinder makeCylinder(Vector3f p1, Vector3f p2, Vector3f color, float radius) {
        Vector3f position = Vector3f.midpoint(p1, p2);
        ArrayList<Triangle> triangles = generateTriangles(p1, p2, radius, color, 120);

        Mesh mesh = generateMesh(triangles, color);
        return new Cylinder(mesh, position, rotation, scale);
    }

    private static Mesh generateMesh(ArrayList<Triangle> triangles, Vector3f color) {
        // create the vertex array
        Vertex[] vertices = new Vertex[triangles.size() * 3];
        for (int i = 0; i < triangles.size(); i++) {
            Triangle t = triangles.get(i);
            vertices[3 * i] = new Vertex(t.getV1(), color, t.getV1());
            vertices[3 * i + 1] = new Vertex(t.getV2(), color, t.getV2());
            vertices[3 * i + 2] = new Vertex(t.getV3(), color, t.getV3());
        }

        int[] indices = new int[triangles.size() * 3];
        for (int i = 0; i < triangles.size() * 3; i++) {
            indices[i] = i;
        }

        return new Mesh(vertices, indices);
    }

    /**
     * generates the triangles making up this cylinder
     */
    private static ArrayList<Triangle> generateTriangles(Vector3f p1, Vector3f p2, float radius, Vector3f color, int smoothness) {
        Circle[] circles = generateCircles(p1, p2, radius, color, smoothness);
        ArrayList<Triangle> faces = new ArrayList<>();


        ArrayList<Vector3f> v1 = circles[0].getVertices();
        ArrayList<Vector3f> v2 = circles[1].getVertices();

        for (int i = 0; i < v1.size()-1; i++) {
            faces.add(new Triangle(v2.get(i), v1.get(i+1), v1.get(i)));
            faces.add(new Triangle(v2.get(i+1), v1.get(i+1), v2.get(i)));
        }
        faces.add(new Triangle(v2.get(v1.size()-1), v1.get(0), v1.get(v1.size()-1)));
        faces.add(new Triangle(v2.get(0), v1.get(0), v2.get(v2.size()-1)));

        return faces;
    }

    /**
     * generates the circles making up this cylinder
     */
    private static Circle[] generateCircles(Vector3f p1, Vector3f p2, float radius, Vector3f color, int smoothness) {
        Vector3f normal = Vector3f.subtract(p1, p2);
        Circle c1 = new Circle(p1, radius, normal, smoothness);
        Circle c2 = new Circle(p2, radius, normal, smoothness);

        return new Circle[]{c1, c2};
    }
}
