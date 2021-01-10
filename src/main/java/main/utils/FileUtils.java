package main.utils;

import java.io.FileWriter;
import java.io.IOException;

public class FileUtils {
    public static void writeToFile(String line) {
        try {
            FileWriter myWriter = new FileWriter("src/main/resources/history.txt", true);
            myWriter.write(line + "\n");
            myWriter.close();
            System.out.println("Successfully wrote to the file.");
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }
}
