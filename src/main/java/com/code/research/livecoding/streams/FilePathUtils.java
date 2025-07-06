package com.code.research.livecoding.streams;

import java.nio.file.Paths;
import java.util.List;

public class FilePathUtils {

    /**
     * From a list of file‐path strings, extracts the filename portion,
     * filters to keep only .java files, and returns them as a list.
     *
     * @param paths the input list of file paths
     * @return List of filenames ending with ".java"
     */
    public static List<String> getJavaFileNames(List<String> paths) {
        return paths.stream()
                .map(p -> Paths.get(p).getFileName().toString())
                .filter(name->name.endsWith(".java"))
                .toList();
    }

    // Example usage
    public static void main(String[] args) {
        List<String> paths = List.of(
                "/home/user/project/src/Main.java",
                "/home/user/project/src/Utils.kt",
                "C:\\Users\\Dev\\App\\Service.java",
                "/tmp/readme.txt",
                "Relative/Path/Helper.java"
        );

        List<String> javaFiles = getJavaFileNames(paths);
        System.out.println(javaFiles);
        // prints: [Main.java, Service.java, Helper.java]
    }

}
