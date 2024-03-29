package rubacha.translation.util;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.*;

public class FileUtil {

    public static Map<Long, String> readFileToMap(File file) throws IOException {

        Map<Long, String> resultMap = new HashMap<>();

        try (Scanner sc = new Scanner(file)) {
            while (sc.hasNextLine()) {
                resultMap.put(sc.nextLong(), sc.next());
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
