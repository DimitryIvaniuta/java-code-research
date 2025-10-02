package com.code.research.autoclosable;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;

public class ResultDemoApplication {

    public static void main(String[] args) {
        // 1) OK path: parse an even number, then double it
        FileResult<Integer> okChain = FileResults.ok("24")
                .flatMap(ResultDemoApplication::safeParseInt)  // "24" -> 24
                .flatMap(ResultDemoApplication::ensureEven)     // ok
                .map(n -> n * 2);                               // 24 -> 48

        System.out.println("[OK   ] chain: " + okChain);
        System.out.println("[OK   ] value orElse 0 = " + okChain.orElse(0));

        // 2) ERR path: parse a non-number, ensureEven/map won't run
        FileResult<Integer> errChain = FileResults.ok("NaN")
                .flatMap(ResultDemoApplication::safeParseInt)   // -> Err(NumberFormatException)
                .flatMap(ResultDemoApplication::ensureEven)     // skipped
                .map(n -> n * 2);                               // skipped

        System.out.println("[ERR  ] chain: " + errChain);
        System.out.println("[ERR  ] value orElse 0 = " + errChain.orElse(0));
        errChain.err().ifPresent(ex -> System.out.println("        cause: " + ex));

        // 3) File reading: OK case (create a temp file)
        Path okFile = writeTemp("customers-ok.csv", "id,name\n1,Alice\n2,Bob\n");
        FileResult<String> csvOk = readCsv(okFile);
        System.out.println("[CSV  ] ok: len=" + csvOk.value().map(String::length).orElse(0));

        // 4) File reading: ERR case (nonexistent file)
        Path missing = okFile.resolveSibling("does-not-exist.csv");
        FileResult<String> csvErr = readCsv(missing);
        System.out.println("[CSV  ] err: orElse fallback\n" + csvErr.orElse("id,name\n"));
        csvErr.err().ifPresent(ex -> System.out.println("        cause: " + ex));
    }

    /**
     * Safe integer parse into Result.
     */
    static FileResult<Integer> safeParseInt(String s) {
        try {
            return FileResults.ok(Integer.parseInt(s));
        } catch (NumberFormatException e) {
            return FileResults.err("Invalid integer: '" + s + "'", e);
        }
    }

    /**
     * Ensure number is even; otherwise return an Err.
     */
    static FileResult<Integer> ensureEven(int n) {
        if ((n & 1) == 0) return FileResults.ok(n);
        return FileResults.err("Expected even number, got: " + n, new IllegalArgumentException("odd"));
    }

    /**
     * Recovering CSV read: never throws; returns Result<String>.
     */
    static FileResult<String> readCsv(Path path) {
        try {
            return FileResults.ok(Files.readString(path, StandardCharsets.UTF_8));
        } catch (IOException e) {
            // Log the path + reason (System.err here; replace with SLF4J in your app)
            System.err.println("[WARN ] Failed to read CSV: path=" + path + " (" + e + ")");
            return FileResults.err("I/O error reading " + path, e);
        }
    }

    private static Path writeTemp(String name, String content) {
        try {
            Path p = Files.createTempFile(name, null);
            Files.writeString(p, content, StandardCharsets.UTF_8);
            p.toFile().deleteOnExit();
            return p;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
