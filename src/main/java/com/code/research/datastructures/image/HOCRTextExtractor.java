package com.code.research.datastructures.image;

import lombok.extern.slf4j.Slf4j;
import net.sourceforge.tess4j.ITesseract;
import net.sourceforge.tess4j.Tesseract;
import net.sourceforge.tess4j.TesseractException;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.List;

@Slf4j
public class HOCRTextExtractor {

    /**
     * Extracts HOCR formatted text from the given image.
     *
     * @param imagePath  The path to the input image.
     * @param outputBase The base name for the output file (e.g., "output" creates "output.hocr").
     * @return A String containing the contents of the generated HOCR file.
     * @throws TesseractException if OCR processing fails.
     * @throws IOException if reading the HOCR file fails.
     */
    public static String extractHOCRText(String imagePath, String outputBase) throws TesseractException, IOException {
        Tesseract tesseract = new Tesseract();
        // Set the path to your tessdata folder.
        tesseract.setDatapath("src/main/resources/tessdata");
        tesseract.setLanguage("eng");

        // Prepare the list of output formats (HOCR).
        List<ITesseract.RenderedFormat> formats = Collections.singletonList(ITesseract.RenderedFormat.HOCR);

        // Use the method that takes String arrays for input and output.
        tesseract.createDocuments(new String[]{imagePath}, new String[]{outputBase}, formats);

        // The generated HOCR file will be named as outputBase.hocr
        String hocrFilePath = outputBase + ".hocr";
        return new String(Files.readAllBytes(Paths.get(hocrFilePath)));
    }

    public static void main(String[] args) {
        String imagePath = "c://c1.jpg";  // Replace with your image file path.
        String outputBase = "output.txt";      // Base name for the HOCR output file.

        try {
            String hocrText = extractHOCRText(imagePath, outputBase);
            log.info("Generated HOCR Output:\n" + hocrText);
        } catch (TesseractException | IOException e) {
            log.error("Error extracting HOCR text: ", e);
        }
    }
}
