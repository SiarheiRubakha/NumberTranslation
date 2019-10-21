import junitparams.JUnitParamsRunner;
import junitparams.Parameters;
import org.junit.*;
import org.junit.rules.TestWatcher;
import org.junit.runner.Description;
import org.junit.runner.RunWith;
import rubacha.translation.NumberToStringTranslator;


import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

import static junitparams.JUnitParamsRunner.$;
import static org.junit.Assert.*;

@RunWith(JUnitParamsRunner.class)
public class NumberToStringTest {

    private static final String TEST_RESULTS_PATH = "src\\TestDataRes";
    private static final String TEST_PATH = "src\\TestData";
    private static Map<Integer, String> dataMap = new HashMap<>();

    private static StringBuilder builder = new StringBuilder();

    private NumberToStringTranslator numberToStringTranslator;

    @Before
    public void setUp() throws Exception {
        numberToStringTranslator = new NumberToStringTranslator();
    }

    @AfterClass
    public static void afterClass() throws IOException {

        FileWriter fw = new FileWriter(TEST_RESULTS_PATH);

        int i = 0;
        for (Map.Entry<Integer, String> entry : dataMap.entrySet()) {
            if (builder.charAt(i) == '1') {
                fw.write("OK   ");
                System.out.print("OK   ");
            }
            else {
                fw.write("FAIL ");
                System.out.print("FAIL ");
            }
            fw.write(entry.getKey().toString() + " ");
            fw.write(entry.getValue() + "\n");
            System.out.print(entry.getKey().toString() + " ");
            System.out.println(entry.getValue());

            i++;
        }
        fw.close();
    }

    @Rule
    public TestWatcher watchman = new TestWatcher() {

        @Override
        protected void failed(Throwable e, Description description) {
            builder.append("0");
        }

        @Override
        protected void succeeded(Description description) {
            builder.append("1");
        }
    };

    @Test
    @Parameters(method = "setData")
    public void translate(int num, String res) {

        final String actual = numberToStringTranslator.translate(num);
        final String expected = res;
        assertEquals(expected.trim(), actual.trim());

    }

    public Object[] setData() throws IOException {

        FileReader fr = new FileReader(TEST_PATH);
        Scanner sc = new Scanner(fr);
        while (sc.hasNextLine()) {
            StringBuilder cur = new StringBuilder(sc.nextLine());
            int cut = cur.indexOf(" ");
            int numb = Integer.parseInt(cur.substring(0, cut));
            cur.delete(0, cut + 1);
            dataMap.put(numb, cur.toString());
        }
        fr.close();

        Object[] data = new Object[dataMap.size()];
        int i = 0;
        for (Map.Entry<Integer, String> entry : dataMap.entrySet()) {
            data[i] = $(entry.getKey(), entry.getValue());
            i++;
        }

        return data;
    }
}
