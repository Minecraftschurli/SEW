package api;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class FileHelper {

    private static final Logger logger = Logger.registerNewLogger(FileHelper.class.getName());

    public static void saveToFile(String text, final File file) {
        try {
            FileWriter writer = new FileWriter(file);
            writer.write(text);
            writer.flush();
            writer.close();
        } catch (Exception e) {
            logger.log(e);
        }
    }

    public static void saveToFile(String text, final String path, final String fileName) {
        saveToFile(text, new File(path + fileName));
    }

    public static String readFile(final String path, final String fileName) {
        return readFile(new File(path + fileName));
    }

    public static String readFile(File file) {
        try {
            FileReader reader = new FileReader(file);
            char[] chars = new char[(int) file.length()];
            reader.read(chars);
            return new String(chars);
        } catch (IOException e) {
            logger.log(e);
            return null;
        }
    }
}
