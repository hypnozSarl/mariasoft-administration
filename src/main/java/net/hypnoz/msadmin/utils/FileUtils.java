package net.hypnoz.msadmin.utils;

import org.springframework.core.io.ClassPathResource;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class FileUtils {
    private FileUtils() {
        throw new IllegalStateException("Utility class");
    }

    public static String getJsonfromFile(String path)  {
        try {
            return Files.readString(Paths.get(new ClassPathResource(path).getURI()));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
