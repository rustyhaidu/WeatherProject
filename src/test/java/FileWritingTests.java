import ro.mta.se.lab.utils.FileUtils;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class FileWritingTests {

    @Test
    public void writeToFileTest(){
        String crtMillis = String.valueOf(System.currentTimeMillis());
        FileUtils.writeToFile("Test " + crtMillis , "testFile.txt");
        String data = FileUtils.readFile("testFile.txt");

        assertTrue(data.contains("Test " + crtMillis ));
    }

}
