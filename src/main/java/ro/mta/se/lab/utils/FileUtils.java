package ro.mta.se.lab.utils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class FileUtils {

    public static void writeToFile(String line) {
        writeToFile(line, "history.txt");
    }

    public static void writeToFile(String line, String fileName) {
        try {
            FileWriter myWriter = new FileWriter("src/main/resources/" + fileName, true);
            myWriter.write(line + "\n");
            myWriter.close();
            System.out.println("Successfully wrote to the file.");
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }

    public static String readFile(String fileName) {
        StringBuilder data = new StringBuilder();
        try {
            File myObj = new File("src/main/resources/" + fileName);
            Scanner myReader = new Scanner(myObj);
            while (myReader.hasNextLine()) {
                data.append(myReader.nextLine());
            }
            myReader.close();
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
        return data.toString();
    }
}
