package rubacha.translation.util;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

public class FileUtil {


    /*Create util file cause it is not Translator responsibility to perform file reading*/
    public static Map<Integer, String> readFileToMap(File file) throws IOException {

        Map<Integer, String> resultMap = new HashMap<>();

        try (Scanner sc = new Scanner(file)) {
            while (sc.hasNextLine()) {
                resultMap.put(sc.nextInt(), sc.next());
            }
            return resultMap;
        } catch (FileNotFoundException e) {
            throw new IOException("File does not exist");
        }
    }

    public static List<String> readFileToList(File file) throws IOException {

        List<String> resultList = new ArrayList<>();

        try (Scanner sc = new Scanner(file)) {
            while (sc.hasNext()) {
                resultList.add(sc.next());
            }
            return resultList;
        } catch (FileNotFoundException e) {
            throw new IOException("File does not exist");
        }
    }
}
