import junitparams.JUnitParamsRunner;
import junitparams.Parameters;
import org.junit.*;
import org.junit.rules.TestWatcher;
import org.junit.runner.Description;
import org.junit.runner.RunWith;
import rubacha.NumberToString;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

import static junitparams.JUnitParamsRunner.$;
import static org.junit.Assert.*;

@RunWith(JUnitParamsRunner.class)
public class NumberToStringTest {

    static Map<Integer,String> dataMap =new HashMap<>();
    private static StringBuilder builder = new StringBuilder();

    @Before
    public void setUp() throws Exception {
        NumberToString.setTranslatedNumbers();
    }

    @AfterClass
    public static void afterClass() throws IOException{
        System.out.print(builder);
        FileWriter fw = new FileWriter("src\\TestDataRes");

        int i=0;
        for (Map.Entry<Integer,String> entry:dataMap.entrySet()){
            if(builder.charAt(i)=='1')
                fw.write("OK ");
            else
                fw.write("FAIL ");
            fw.write(entry.getKey().toString()+" ");
            fw.write(entry.getValue()+"\n");

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

        try {
            final NumberToString numberToString = new NumberToString(num);
            final String actual = numberToString.translate();
            final String expected = res;
            assertEquals(expected, actual);
        }catch(IOException io){
            io.printStackTrace();
        }
    }

    public Object[] setData()throws IOException{

        FileReader fr = new FileReader("src\\TestData");
        Scanner sc = new Scanner(fr);
        while (sc.hasNextLine()) {
            //int nextInt = sc.nextInt();
            StringBuilder cur = new StringBuilder(sc.nextLine());
            int cut = cur.indexOf(" ");
            int numb =Integer.parseInt(cur.substring(0,cut));
            cur.delete(0,cut+1);
            dataMap.put(numb, cur.toString());
        }
        fr.close();

        Object[] data = new Object[dataMap.size()];
        int i=0;
        for(Map.Entry<Integer,String> entry:dataMap.entrySet()){
            data[i]=$(entry.getKey(),entry.getValue());
            i++;
        }

        return data;
    }
}