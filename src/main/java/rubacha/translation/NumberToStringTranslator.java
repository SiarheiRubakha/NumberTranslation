package rubacha.translation;

import rubacha.translation.util.FileUtil;

import java.io.File;
import java.io.IOException;
import java.util.*;

import static rubacha.translation.util.CommonConstants.*;

public class NumberToStringTranslator {

    private static final String DICTIONARY_BEFORE_THOUSAND_FILE_PATH = "src\\DictionaryBeforeThousand";
    private static final String DICTIONARY_FILE_PATH = "src\\Dictionary";
    private static Map<Long, String> translatedNumbers;
    private List<String> dictionary;

    public NumberToStringTranslator() {

        try {
            translatedNumbers = FileUtil.readFileToMap(new File(DICTIONARY_BEFORE_THOUSAND_FILE_PATH));
            dictionary = FileUtil.readFileToList(new File(DICTIONARY_FILE_PATH));
        } catch (IOException e) {
            throw new RuntimeException(e);

        }

    }

    public static Map<Long, String> getTranslatedNumbers() {
        return translatedNumbers;
    }


    private List<Long> parseNumberReverse(long number) {

        List<Long> digits = new ArrayList<>();

        long subtrahend = number;
        long minuend = number;
        while (subtrahend != 0) {
            subtrahend /= 10;
            digits.add(minuend - subtrahend * 10);
            minuend /= 10;
        }
        return digits;
    }

    public String translate(long number) {

        StringBuilder result = new StringBuilder();

        List<Long> digits = parseNumberReverse(number);

        while (digits.size() % 3 != 0)
            digits.add(0L);

        for (int i = digits.size() - 1; i > 0; i -= 3) {
            Long unit = digits.get(i - 2);
            Long ten = digits.get(i - 1);
            Long hundred = digits.get(i);
            result.insert(result.length(), translatePart(hundred, ten, unit, i / 3 - 1));
        }

        result.deleteCharAt(result.length() - 1);
        return result.toString();

    }

    private String translatePart(Long hundred,
                                Long ten,
                                Long unit,
                                Integer rank) {
        StringBuilder result = new StringBuilder();

        //hundred
        if (!hundred.equals(0L))
            result.append(translatedNumbers.get(hundred * 100)).append(SPACE);

        //ten, unit
        if (ten.equals(1L))
            result.append(translatedNumbers.get(ten * 10 + unit)).append(SPACE);
        else {
            if (!ten.equals(0L))
                result.append(translatedNumbers.get(ten * 10)).append(SPACE);
            if (!unit.equals(0L)) {
                result.append(unitForThousand(unit, rank)).append(SPACE);
            }
        }

        if(isZero(hundred, ten, unit))
            result.append(SPACE);
        else
            result.append(getFromDictionary(ten, unit, rank)).append(SPACE);

        return result.toString();
    }

    private String getFromDictionary(Long ten,
                                     Long unit,
                                     Integer rank) {

        if (rank != -1) {
            int position = rank * 3 + 2;
            if (isNominative(ten, unit))
                position -= 2;
            if (isGenitive(ten, unit))
                position -= 1;

            return dictionary.get(position);
        }

        return "";
    }

    private boolean isZero (Long hundred,Long ten, Long unit){
        return(hundred.equals(0L)&&ten.equals(0L)&&unit.equals(0L));
    }
    private boolean isGenitive(Long ten,Long unit){
        return (unit.equals(2L) || unit.equals(3L) || unit.equals(4L)) && !ten.equals(1L);
    }
    private boolean isNominative(Long ten,Long unit){
        return unit.equals(1L) && !ten.equals(1L);
    }

    private String unitForThousand(Long unit, Integer rank){
        if(rank.equals(0)) {
            if (unit.equals(1L))
                return "одна ";
            if (unit.equals(2L))
                return "две ";
        }
        return translatedNumbers.get(unit);
    }
}

