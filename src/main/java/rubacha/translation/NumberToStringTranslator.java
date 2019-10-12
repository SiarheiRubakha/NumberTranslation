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

    public NumberToStringTranslator() {

        try {
            translatedNumbers = FileUtil.readFileToMap(new File(dictionaryBeforeThousandFilePath));
            dictionary = FileUtil.readFileToList(new File(dictionaryFilePath));
        } catch (IOException e) {
            throw new RuntimeException(e);

        }

    }

    public static Map<Integer, String> getTranslatedNumbers() {
        return translatedNumbers;
    }


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

        for (int i = arrayList.size() - 1; i > 0; i -= 3) {
            Integer unit = arrayList.get(i - 2);
            Integer ten = arrayList.get(i - 1);
            Integer hundred = arrayList.get(i);
            res.insert(res.length(), translatePart(hundred, ten, unit, i / 3 - 1));
        }

        res.deleteCharAt(res.length() - 1);
        return res.toString();

    }

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

        res.append(getFromDictionary(ten, unit, rank)).append(SPACE);

        return res.toString();
    }

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

