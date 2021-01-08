package com.bramerlabs.engine.graphics;

import com.bramerlabs.engine.io.window.Window;
import com.bramerlabs.engine.math.Matrix4f;
import com.bramerlabs.engine.math.Vector3f;
import com.bramerlabs.engine.objects.Camera;
import com.bramerlabs.engine.objects.RenderObject;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL15;
import org.lwjgl.opengl.GL30;

public class Renderer {
    // the shader program
    private Shader shader;

    // the window to render to
    private Window window;

    /**
     * default constructor
     * @param window - the specified window to render to
     * @param shader - the shader program to use to render
     */
    public Renderer(Window window, Shader shader) {
        this.shader = shader;
        this.window = window;
    }

    /**
     * renders the mesh
     * @param object - the object to be rendered
     * @param camera - the camera perspective
     */
    public void renderMesh(RenderObject object, Camera camera, Vector3f lightPosition, boolean isSelected) {
        GL30.glBindVertexArray(object.getMesh().getVAO());
        GL30.glEnableVertexAttribArray(0);
        GL30.glEnableVertexAttribArray(1);
        GL30.glEnableVertexAttribArray(2);
        GL15.glBindBuffer(GL15.GL_ELEMENT_ARRAY_BUFFER, object.getMesh().getIBO());
        shader.bind();
        shader.setUniform("model", Matrix4f.transform(object.getPosition(), object.getRotation(), object.getScale()));
        shader.setUniform("view", Matrix4f.view(camera.getPosition(), camera.getRotation()));
        shader.setUniform("projection", window.getProjectionMatrix());
        shader.setUniform("lightPos", lightPosition);
        shader.setUniform("lightLevel", 0.3f);
        shader.setUniform("viewPos", camera.getPosition());
        shader.setUniform("alpha", isSelected ? 0.5f : 1.0f);
        GL11.glDrawElements(GL11.GL_TRIANGLES, object.getMesh().getIndices().length, GL11.GL_UNSIGNED_INT, 0);
        shader.unbind();
        GL15.glBindBuffer(GL15.GL_ELEMENT_ARRAY_BUFFER, 0);
        GL30.glDisableVertexAttribArray(0);
        GL30.glDisableVertexAttribArray(1);
        GL30.glDisableVertexAttribArray(2);
        GL30.glBindVertexArray(0);
    }
}