package com.bramerlabs.engine.io.picking;

import com.bramerlabs.engine.graphics.Shader;
import com.bramerlabs.engine.io.window.Window;
import com.bramerlabs.engine.math.Matrix4f;
import com.bramerlabs.engine.math.Vector3f;
import com.bramerlabs.engine.graphics.Camera;
import com.bramerlabs.engine.objects.RenderObject;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL15;
import org.lwjgl.opengl.GL30;

public class CPRenderer {

    // the shader program
    private Shader shader;

    // the window to render to
    private Window window;

    /**
     * default constructor
     * @param window - the specified window to render to
     * @param shader - the shader program to use to render
     */
    public CPRenderer(Window window, Shader shader) {
        this.window = window;
        this.shader = shader;
    }

    /**
     * renders the mesh
     * @param object - the object to be rendered
     * @param camera - the camera perspective
     */
    public void renderMesh(RenderObject object, Camera camera) {
        GL30.glBindVertexArray(object.getMesh().getVAO());
        GL30.glEnableVertexAttribArray(0); // the vertex position array
        GL15.glBindBuffer(GL15.GL_ELEMENT_ARRAY_BUFFER, object.getMesh().getIBO());

        shader.bind();
        shader.setUniform("model", Matrix4f.transform(object.getPosition(), object.getRotation(), object.getScale()));
        shader.setUniform("view", Matrix4f.view(camera.getPosition(), camera.getRotation()));
        shader.setUniform("projection", window.getProjectionMatrix());

        // create the color picking color
        int r = object.getID();
        int g = 0;
        int b = 0;
        Vector3f color = Vector3f.divide(new Vector3f(r, g, b), new Vector3f(255));

        shader.setUniform("pickingColor", color); // set the picking color uniform
        GL11.glDrawElements(GL11.GL_TRIANGLES, object.getMesh().getIndices().length, GL11.GL_UNSIGNED_INT, 0);
        shader.unbind();
        GL15.glBindBuffer(GL15.GL_ELEMENT_ARRAY_BUFFER, 0);
        GL30.glDisableVertexAttribArray(0);
        GL30.glBindVertexArray(0);
    }

}
