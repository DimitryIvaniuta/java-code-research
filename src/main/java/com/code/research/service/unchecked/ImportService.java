package com.code.research.service.unchecked;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

class ImportService {

    private final ObjectMapper mapper = new ObjectMapper();

    public List<Customer> importDir(Path dir) throws IOException {
        try (Stream<Path> files = Files.list(dir);) {
            return files
                    .filter(f -> f.toString().endsWith(".json"))
                    .map(Fn.unchecked(this::readCustomer))
                    .toList();
        }
    }

    public ImportReport<Customer> importDirLenient(Path dir) throws IOException {
        try (Stream<Path> files = Files.list(dir);) {
            List<ImportResult<Customer>> results = files
                    .filter(p -> p.toString().endsWith(".json"))
                    .map(p -> {
                                try {
                                    return ImportResult.ok(readCustomer(p), p);
                                } catch (IOException e) {
                                    return ImportResult.<Customer>err(p, e);
                                }
                            }
                    )
                    .toList();
            List<Customer> customers = results.stream()
                    .filter(ImportResult::isOk)
                    .map(ImportResult::value)
                    .toList();
            List<ImportResult<Customer>> customersFailed = results.stream()
                    .filter(c -> !c.isOk()).toList();

            return new ImportReport<>(customers, customersFailed);
        }
    }

    public List<Customer> importDirLoop(Path dir) throws IOException {
        var out = new ArrayList<Customer>();
        try (DirectoryStream<Path> ds = Files.newDirectoryStream(dir, "*.json")) {
            for (Path p : ds) {
                out.add(readCustomer(p)); // throws IOException; method contract is honest
            }
        }
        return out;
    }

    private Customer readCustomer(Path p) throws IOException {
        try (InputStream in = Files.newInputStream(p);) {
            return mapper.readValue(in, Customer.class);
        }
    }

}
