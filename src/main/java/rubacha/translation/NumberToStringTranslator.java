package rubacha.translation;

import rubacha.translation.util.FileUtil;

import java.io.File;
import java.io.IOException;
import java.util.*;

import static rubacha.translation.util.CommonConstants.SPACE;

public class NumberToStringTranslator {

    private static final String dictionaryBeforeThousandFilePath = "src\\DictionaryBeforeThousand";
    private static final String dictionaryFilePath = "src\\Dictionary";
    private static Map<Integer, String> translatedNumbers;
    private List<String> dictionary;

    /*I think it is better not to pass number to parse directly in method*/
    /*Also in order to not to invoke setter, I think is is better to read file directly in constructor*/
    public NumberToStringTranslator() {

        try {
            translatedNumbers = FileUtil.readFileToMap(new File(dictionaryBeforeThousandFilePath));
            dictionary = FileUtil.readFileToList(new File(dictionaryFilePath));
        } catch (IOException e) {
            throw new RuntimeException(e);
            /*Some logging here*/
        }

    }

    public static Map<Integer, String> getTranslatedNumbers() {
        return translatedNumbers;
    }

    //parsing number into array
    /*Moved it to separate method for more readability*/
    private List<Integer> parseNumber(int number) {

        List<Integer> digits = new ArrayList<>();

        int buf1 = number;
        int buf2 = number;
        while (buf1 != 0) {
            buf1 /= 10;
            digits.add(buf2 - buf1 * 10);
            buf2 /= 10;
        }
        return digits;
    }

    public String translate(int number) {

        StringBuilder res = new StringBuilder();

        List<Integer> arrayList = parseNumber(number);

        while (arrayList.size() % 3 != 0)
            arrayList.add(0);

        /*Represented while loop as for loop*/
        for (int i = arrayList.size() - 1; i > 0; i -= 3) {
            Integer unit = arrayList.get(i - 2);
            Integer ten = arrayList.get(i - 1);
            Integer hundred = arrayList.get(i);
            res.insert(res.length(), translatePart(hundred, ten, unit, i / 3 - 1));
        }

        res.deleteCharAt(res.length() - 1);
        return res.toString();

    }

    /*I think it is better to return plane String than StringBuffer*/
    public String translatePart(Integer hundred,
                                Integer ten,
                                Integer unit,
                                Integer rank) {
        StringBuilder res = new StringBuilder();

        //hundred
        if (!hundred.equals(0))
            res.append(translatedNumbers.get(hundred * 100)).append(SPACE);

        //ten, unit
        if (ten.equals(1))
            res.append(translatedNumbers.get(ten * 10 + unit)).append(SPACE);
        else {
            if (!ten.equals(0))
                res.append(translatedNumbers.get(ten * 10)).append(SPACE);
            if (!unit.equals(0)) {
                if (rank == 0) { //for 1000
                    if (unit.equals(1)) {
                        res.append("одна ");
                    } else if (unit.equals(2)) {
                        res.append("две ");
                    } else {
                        res.append(translatedNumbers.get(unit)).append(SPACE);
                    }
                } else
                    res.append(translatedNumbers.get(unit)).append(SPACE);

            }
        }

        //finding position in dictionary
/*        if (rank != -1) {
            FileReader fr = new FileReader("src\\Dictionary");
            Scanner sc = new Scanner(fr);
            int position = rank * 3 + 2;
            if (unit.equals(1) && !ten.equals(1))
                position -= 2;
            if ((unit.equals(2) || unit.equals(3) || unit.equals(4)) && !ten.equals(1))
                position -= 1;

            for (int i = 0; i < position; i++) {
                sc.next();
            }

            *//*StringBuffer has method append to add to the end of it*//*
            res.append(sc.next()).append(SPACE);

            fr.close();
        }*/

        /*Moved to separate method for better readability*/
        res.append(getFromDictionary(ten, unit, rank)).append(SPACE);

        return res.toString();
    }

    /*I refactored it in this way:
    * I read dictionary to list when object constructs in order not to read file every time you want to translate number
    * To get needed string I just get it from this list by index you've calculated*/
    private String getFromDictionary(Integer ten,
                                     Integer unit,
                                     Integer rank) {

        if (rank != -1) {
            int position = rank * 3 + 2;
            if (unit.equals(1) && !ten.equals(1))
                position -= 2;
            if ((unit.equals(2) || unit.equals(3) || unit.equals(4)) && !ten.equals(1))
                position -= 1;

            return dictionary.get(position);
        }

        return "";
    }
}

