package rubacha.translation;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;
import static org.powermock.api.mockito.PowerMockito.when;
import static org.powermock.api.support.membermodification.MemberMatcher.method;

public class NumberToStringTranslatorTest {

    NumberToStringTranslator number = new NumberToStringTranslator();

    @Test()
    public void translate_out_of_bounds_long()throws Exception{
        long numberToTranslate = Long.MAX_VALUE;
        String expected = "сто двадцать три тысячи пятьсот шестьдесят восемь";
        Method method = NumberToStringTranslator.class.getDeclaredMethod("translate", long.class);
        method.setAccessible(true);
        String actual = (String)method.invoke(number,numberToTranslate+1L);

    }
    @Test
    public void translate() throws Exception{
        long numberToTranslate = 123568;
        String expected = "сто двадцать три тысячи пятьсот шестьдесят восемь";
        Method method = NumberToStringTranslator.class.getDeclaredMethod("translate", long.class);
        method.setAccessible(true);
        String actual = (String)method.invoke(number,numberToTranslate);

        assertEquals(actual.trim(),expected.trim());
    }

    @Test
    public void parseNumberReverse() throws Exception{

        long numberToParse = 123567;
        List<Long> digits = new ArrayList<>();
        Collections.addAll(digits,7L,6L,5L,3L,2L,1L);
        Method method = NumberToStringTranslator.class.getDeclaredMethod("parseNumberReverse", long.class);
        method.setAccessible(true);
        ArrayList<Long> result = (ArrayList<Long>)method.invoke(number,numberToParse);
        assertEquals(result,digits);

    }

    @Test
    public void translatePart()throws Exception{
        long hundred = 2;
        long ten = 3;
        long unit = 4;
        int rank = 3;

        String expected = "двести тридцать четыре триллиона ";
        Method method = NumberToStringTranslator.class.getDeclaredMethod("translatePart", Long.class, Long.class, Long.class, Integer.class);
        method.setAccessible(true);
        String actual = (String)method.invoke(number,hundred,ten,unit,rank);
        assertEquals(actual,expected);
    }

    @Test
    public void getFromDictionary() throws Exception{
        long ten = 3;
        long unit = 4;
        int rank = 3;
        String expected = "триллиона";
        Method method = NumberToStringTranslator.class.getDeclaredMethod("getFromDictionary", Long.class, Long.class, Integer.class);
        method.setAccessible(true);
        String actual = (String)method.invoke(number,ten,unit,rank);
        assertEquals(actual,expected);
    }

    @Test
    public void unitForThousand()throws Exception{
        long unit = 4;
        int rank = 0;
        String expected = "четыре";
        Method method = NumberToStringTranslator.class.getDeclaredMethod("unitForThousand", Long.class, Integer.class);
        method.setAccessible(true);
        String actual = (String)method.invoke(number,unit,rank);
        assertEquals(actual,expected);
    }
}