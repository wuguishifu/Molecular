package com.bramerlabs.engine.io.screenshots;

import org.lwjgl.opengl.GL11;
import org.lwjglx.BufferUtils;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.ByteBuffer;

public class ScreenshotTaker {

    // the path to the documents folder
    private static final String DOCUMENT_PATH = new JFileChooser().getFileSystemView().getDefaultDirectory().toString();
    private static final String SCREENSHOTS_FOLDER = "Screenshots";
    private static final String SEP = "\\";
    private static final String FORMAT = "PNG";

    /**
     * takes a screenshot
     */
    public static void takeScreenshot(int width, int height) {
        // get the front buffer
        GL11.glReadBuffer(GL11.GL_FRONT);

        // the amount of bytes per pixel
        int bpp = 4; // assumes 32-bit display with RGBA

        ByteBuffer buffer = BufferUtils.createByteBuffer(width * height * bpp);
        GL11.glReadPixels(0, 0, width, height, GL11.GL_RGBA, GL11.GL_UNSIGNED_BYTE, buffer);
        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);

        // make the buffered image
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                int i = (y * width + x) * bpp;
                int r = buffer.get(i) & 0xFF;
                int g = buffer.get(i + 1) & 0xFF;
                int b = buffer.get(i + 2) & 0xFF;
                image.setRGB(x, height - (y + 1), (0xFF << 24) | (r << 16) | (g << 8) | b);
            }
        }

        // if the screenshots directory doesn't exist, make one
        if (!(new File(DOCUMENT_PATH + SEP + SCREENSHOTS_FOLDER).exists())) {
            File screenshotsDir = new File(DOCUMENT_PATH + SEP + SCREENSHOTS_FOLDER);
            boolean bool = screenshotsDir.mkdir();
        }

        // make the file
        String fileName = "Screenshot " + System.currentTimeMillis() + ".png";
        if (!new File(DOCUMENT_PATH + SEP + SCREENSHOTS_FOLDER + SEP + fileName).exists()) {
            // if the file doesn't already exist, make a new file
            File newFile = new File(DOCUMENT_PATH + SEP + SCREENSHOTS_FOLDER + SEP + fileName);
            try {
                // attempt to create the new file
                boolean bool = newFile.createNewFile();

                ImageIO.write(image, FORMAT, newFile);

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}
