package com.code.research.autoclosable;

import lombok.extern.slf4j.Slf4j;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.stream.Collectors;

@Slf4j
public class ReadCSVFile {
    public String doReadCSV(String fileName) {
        Path path = Path.of(fileName);
        try (BufferedReader reader = Files.newBufferedReader(path, StandardCharsets.UTF_8);) {
            return reader.lines().collect(
                    Collectors.joining("\n")
            );
        } catch (IOException e) {
            throw new UncheckedIOException("Failed to read CSV: " + path, e);
        }
    }

    public ReadCSVResult readCSV(Path filePath) {
        try{
            String content = Files.readString(filePath, StandardCharsets.UTF_8);
            ReadCSVResult r = new ReadCSVResult(content, null, null);
            return ReadCSVResult.ok(content);
        } catch(IOException e) {
            log.warn("Failed to read CSV: path={} ({})", filePath, e.toString(), e);
            return ReadCSVResult.err("I/O error reading " + filePath, e);
        }
    }
}
