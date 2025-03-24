package com.code.research.datastructures.image;

import lombok.extern.slf4j.Slf4j;
import net.sourceforge.tess4j.ITesseract;
import net.sourceforge.tess4j.Tesseract;
import net.sourceforge.tess4j.TesseractException;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

@Slf4j
public class OCRFormattedText {

    public static String fetchFormattedTextFromImage(String imagePath) throws TesseractException {
        ITesseract tesseract = new Tesseract();
        String outputBase = "output";
        // Set the datapath to the tessdata folder in your resources.
        tesseract.setDatapath("src/main/resources/tessdata");
        tesseract.setLanguage("eng");

        // Generate HOCR document(s) for the provided image.
        // This will create an HOCR file named outputBase.hocr in the working directory.
        String text = tesseract.doOCR(new File(imagePath));
        log.info(text);
        // Read and return the contents of the generated HOCR file.
        String hocrFilePath = outputBase + ".hocr";
        String resultString = "";

        try {
            resultString = new String(Files.readAllBytes(Paths.get(hocrFilePath)));
        } catch (IOException e) {
            log.error("Error reading hocr file", e);
        }

        return resultString;
    }

    public static void main(String[] args) {
        String imagePath = "c://ocrimage.jpg";  // Replace with your image file path.
        try {
            String formattedText = fetchFormattedTextFromImage(imagePath);
            log.info("Formatted text (HOCR):\n" + formattedText);
        } catch (TesseractException e) {
            log.error("Error during OCR processing: {}", e.getMessage());
        }
    }
}
