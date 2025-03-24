package com.code.research.datastructures.image;

import lombok.extern.slf4j.Slf4j;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;

@Slf4j
public class ImageResizer {

    /**
     * Resizes an image to a specified width while maintaining its aspect ratio.
     *
     * @param inputImagePath  The file path of the original image.
     * @param outputImagePath The file path to save the resized image.
     * @param newWidth        The new width for the resized image.
     */
    public static void resizeImage(String inputImagePath, String outputImagePath, int newWidth) {
        try {
            // Read the original image from disk.
            File inputFile = new File(inputImagePath);
            BufferedImage originalImage = ImageIO.read(inputFile);
            if (originalImage == null) {
                throw new IllegalArgumentException("Invalid image file: " + inputImagePath);
            }

            // Calculate the new height to maintain the aspect ratio.
            int originalWidth = originalImage.getWidth();
            int originalHeight = originalImage.getHeight();
            int newHeight = (newWidth * originalHeight) / originalWidth;

            // Create a new image with the new dimensions.
            BufferedImage resizedImage = new BufferedImage(newWidth, newHeight, originalImage.getType());

            // Draw the original image, scaled to the new size.
            Graphics2D g2d = resizedImage.createGraphics();
            g2d.drawImage(originalImage, 0, 0, newWidth, newHeight, null);
            g2d.dispose();

            // Determine the output image format (e.g., jpg, png) based on the output file extension.
            String formatName = outputImagePath.substring(outputImagePath.lastIndexOf(".") + 1);

            // Write the resized image to disk.
            ImageIO.write(resizedImage, formatName, new File(outputImagePath));
            log.info("Image resized successfully: {}", outputImagePath);
        } catch (Exception e) {
            log.error("Error resizing the image: {}", e.getMessage());
        }
    }

    public static void main(String[] args) {
        // Update these file paths as needed.
        String inputImagePath = args.length > 0 ? args[0] : "c://c1.jpg";         // Original image path.
        String outputImagePath = args.length > 1 ? args[1] : "c://resized_output2.jpg"; // Resized image output path.
        int newWidth = 100;                            // Desired width in pixels.

        resizeImage(inputImagePath, outputImagePath, newWidth);
    }
}
