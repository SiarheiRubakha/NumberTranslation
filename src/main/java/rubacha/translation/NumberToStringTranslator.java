package rubacha.translation;

import rubacha.translation.util.FileUtil;

import java.io.File;
import java.io.IOException;
import java.util.*;

import static rubacha.translation.util.CommonConstants.SPACE;

public class NumberToStringTranslator {

    private static final String dictionaryBeforeThousandFilePath = "src\\DictionaryBeforeThousand";
    private static final String dictionaryFilePath = "src\\Dictionary";
    private static Map<Long, String> translatedNumbers;
    private List<String> dictionary;

    public NumberToStringTranslator() {

        try {
            translatedNumbers = FileUtil.readFileToMap(new File(dictionaryBeforeThousandFilePath));
            dictionary = FileUtil.readFileToList(new File(dictionaryFilePath));
        } catch (IOException e) {
            throw new RuntimeException(e);

        }

    }

    public static Map<Long, String> getTranslatedNumbers() {
        return translatedNumbers;
    }


    private List<Long> parseNumber(long number) {

        List<Long> digits = new ArrayList<>();

        long buf1 = number;
        long buf2 = number;
        while (buf1 != 0) {
            buf1 /= 10;
            digits.add(buf2 - buf1 * 10);
            buf2 /= 10;
        }
        return digits;
    }

    public String translate(long number) {

        StringBuilder res = new StringBuilder();

        List<Long> arrayList = parseNumber(number);

        while (arrayList.size() % 3 != 0)
            arrayList.add(Long.parseLong("0"));

        for (int i = arrayList.size() - 1; i > 0; i -= 3) {
            Long unit = arrayList.get(i - 2);
            Long ten = arrayList.get(i - 1);
            Long hundred = arrayList.get(i);
            res.insert(res.length(), translatePart(hundred, ten, unit, i / 3 - 1));
        }

        res.deleteCharAt(res.length() - 1);
        return res.toString();

    }

    public String translatePart(Long hundred,
                                Long ten,
                                Long unit,
                                Integer rank) {
        StringBuilder res = new StringBuilder();

        //hundred
        if (!hundred.equals(Long.parseLong("0")))
            res.append(translatedNumbers.get(hundred * 100)).append(SPACE);

        //ten, unit
        if (ten.equals(Long.parseLong("1")))
            res.append(translatedNumbers.get(ten * 10 + unit)).append(SPACE);
        else {
            if (!ten.equals(Long.parseLong("0")))
                res.append(translatedNumbers.get(ten * 10)).append(SPACE);
            if (!unit.equals(Long.parseLong("0"))) {
                if (rank == 0) { //for 1000
                    if (unit.equals(Long.parseLong("1"))) {
                        res.append("одна ");
                    } else if (unit.equals(Long.parseLong("2"))) {
                        res.append("две ");
                    } else {
                        res.append(translatedNumbers.get(unit)).append(SPACE);
                    }
                } else
                    res.append(translatedNumbers.get(unit)).append(SPACE);

            }
        }

        if(hundred.equals(Long.parseLong("0"))&&ten.equals(Long.parseLong("0"))&&unit.equals(Long.parseLong("0")))
            res.append(SPACE);
        else
            res.append(getFromDictionary(ten, unit, rank)).append(SPACE);

        return res.toString();
    }

    private String getFromDictionary(Long ten,
                                     Long unit,
                                     Integer rank) {

        if (rank != -1) {
            int position = rank * 3 + 2;
            if (unit.equals(Long.parseLong("1")) && !ten.equals(Long.parseLong("1")))
                position -= 2;
            if ((unit.equals(Long.parseLong("2")) || unit.equals(Long.parseLong("3")) || unit.equals(Long.parseLong("4"))) && !ten.equals(Long.parseLong("1")))
                position -= 1;

            return dictionary.get(position);
        }

        return "";
    }
}

