package com.bramerlabs.engine.io.gui.gui_render;

import com.bramerlabs.engine.graphics.Shader;
import com.bramerlabs.engine.io.gui.gui_object.GuiObject;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL13;
import org.lwjgl.opengl.GL15;
import org.lwjgl.opengl.GL30;

public class GuiRenderer {

    // the shader program
    private Shader shader;

    /**
     * default constructor
     * @param shader - the shader program to use to render
     */
    public GuiRenderer(Shader shader) {
        this.shader = shader;
    }

    /**
     * renders a mesh
     * @param object - the gui object
     */
    public void renderMesh(GuiObject object) {
        GuiMesh mesh = object.getState() == 0 ? object.getDefaultMesh() : object.getStateMesh();
        GL30.glBindVertexArray(mesh.getVAO());
        GL30.glEnableVertexAttribArray(0);
        GL30.glEnableVertexAttribArray(1);
        GL15.glBindBuffer(GL15.GL_ELEMENT_ARRAY_BUFFER, mesh.getIBO());
        GL13.glActiveTexture(GL13.GL_TEXTURE0);
        GL13.glBindTexture(GL11.GL_TEXTURE_2D, mesh.getMaterial().getTextureID());
        shader.bind();
        GL11.glDrawElements(GL11.GL_TRIANGLES, mesh.getIndices().length, GL11.GL_UNSIGNED_INT, 0);
        shader.unbind();
        GL15.glBindBuffer(GL15.GL_ELEMENT_ARRAY_BUFFER, 0);
        GL30.glDisableVertexAttribArray(0);
        GL30.glDisableVertexAttribArray(1);
        GL30.glBindVertexArray(0);
    }
}