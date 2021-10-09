package pe.com.tarjetaoh.app.util;

import lombok.extern.log4j.Log4j2;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

@Log4j2
public class FileUtil {

    private static final String testCasesFolder = "testcases";

    public static String readTestFile(String filePath) {
        return readFile(testCasesFolder+filePath);
    }

    public static String readFile(String filePath) {
        String response = null;
        ClassLoader classLoader = FileUtil.class.getClassLoader();
        File file = new File(classLoader.getResource(filePath).getFile());
        try {
            response = new String(Files.readAllBytes(file.toPath()));
        } catch (IOException iOException) {
            log.error(iOException,iOException);
        }
        return response;
    }

}