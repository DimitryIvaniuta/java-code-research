package com.code.research.datastructures.image;

import lombok.extern.slf4j.Slf4j;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;

@Slf4j
public class GrayscaleImageProcessor {

    /**
     * Converts a colored image to grayscale and saves the result.
     *
     * @param inputImagePath  The path to the input colored image.
     * @param outputImagePath The path where the grayscale image will be saved.
     */
    public static void convertToGrayscale(String inputImagePath, String outputImagePath) {
        try {
            // Load the input image file.
            File inputFile = new File(inputImagePath);
            BufferedImage coloredImage = ImageIO.read(inputFile);

            // Create a new BufferedImage to hold the grayscale image.
            int width = coloredImage.getWidth();
            int height = coloredImage.getHeight();
            BufferedImage grayImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);

            // Process each pixel to convert to grayscale.
            for (int y = 0; y < height; y++) {
                for (int x = 0; x < width; x++) {
                    // Get RGB components of the pixel.
                    int rgb = coloredImage.getRGB(x, y);
                    Color color = new Color(rgb);
                    int red = color.getRed();
                    int green = color.getGreen();
                    int blue = color.getBlue();

                    // Compute grayscale using the luminance formula.
                    int grayLevel = (int)(0.299 * red + 0.587 * green + 0.114 * blue);
                    Color grayColor = new Color(grayLevel, grayLevel, grayLevel);

                    // Set the pixel in the grayscale image.
                    grayImage.setRGB(x, y, grayColor.getRGB());
                }
            }

            // Save the grayscale image to disk.
            File outputFile = new File(outputImagePath);
            ImageIO.write(grayImage, "jpg", outputFile);
            log.info("Grayscale image created successfully at: {}", outputImagePath);

        } catch (Exception e) {
            log.error("Error processing the image:", e);
        }
    }

    public static void main(String[] args) {
        // Specify the input and output image file paths.
        String inputImagePath = "c://c1.jpg";         // Replace with your input image path.
        String outputImagePath = "c://grayscale_output.jpg"; // Replace with desired output path.

        convertToGrayscale(inputImagePath, outputImagePath);
    }

}
