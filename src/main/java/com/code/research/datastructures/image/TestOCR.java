package com.code.research.datastructures.image;

import lombok.extern.slf4j.Slf4j;
import net.sourceforge.tess4j.ITesseract;
import net.sourceforge.tess4j.Tesseract;
import net.sourceforge.tess4j.TesseractException;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

@Slf4j
public class TestOCR {

    public static void main(String[] args) {

        ITesseract tesseract = new Tesseract();

        try {

            // the path of your tess data folder inside the extracted file
            tesseract.setDatapath("c:\\tessdata");

            // path of your image file
            String text = tesseract.doOCR(new File("c:\\ocrimage.jpg"));
            log.info(text);

            // Create a FileWriter with the specified file path
            FileWriter fileWriter = new FileWriter("c:\\ocrimage.txt");

            // Wrap FileWriter in BufferedWriter for efficient writing
            BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);

            // Write the text to the file
            bufferedWriter.write(text);

            // Close the BufferedWriter to flush and release resources
            bufferedWriter.close();
            log.info("Text has been written to the file successfully.");

        } catch (TesseractException | IOException e) {
            log.error("OCR image processing error", e);
        }
    }

}
