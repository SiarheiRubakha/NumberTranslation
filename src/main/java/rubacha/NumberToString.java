package rubacha;

import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Map;
import java.util.Scanner;
import java.util.TreeMap;

public class NumberToString {
    private int number;
    public static Map<Integer,String>translatedNumbers;

    public NumberToString(){

    }
    public NumberToString(int n){
        number = n;
    }

    public int getNumber() {
        return number;
    }
    public void setNumber(int number) {
        this.number = number;
    }
    public static Map<Integer, String> getTranslatedNumbers() {
        return translatedNumbers;
    }
    public static void setTranslatedNumbers() throws IOException{
        translatedNumbers = new TreeMap<>();
        FileReader fr = new FileReader("src\\DictionaryBeforeThousand");
        Scanner sc = new Scanner(fr);
        while(sc.hasNextLine()){
            translatedNumbers.put(sc.nextInt(),sc.next());
        }
        fr.close();
    }


    public String translate() throws IOException{

        StringBuffer res = new StringBuffer();

        //parsing number into array
        ArrayList<Integer> arrayList = new ArrayList<>();
        int buf1 = this.getNumber();
        int buf2 = this.getNumber();
        while (buf1!=0){
            buf1/=10;
            arrayList.add(buf2-buf1*10);
            buf2/=10;
        }

        while(arrayList.size()%3!=0)
            arrayList.add(0);

        int i=arrayList.size()-1;
        while(i>0){
            Integer unit = Integer.valueOf(arrayList.get(i-2));
            Integer ten = Integer.valueOf(arrayList.get(i-1));
            Integer hundred = Integer.valueOf(arrayList.get(i));
            res.insert(res.length(),translatePart(hundred,ten,unit,i/3-1));
            i-=3;
        }
        res.deleteCharAt(res.length()-1);
        return res.toString();

    }
    public StringBuffer translatePart(Integer hundred,Integer ten,Integer unit, int rank) throws IOException{
        StringBuffer res = new StringBuffer();

        //hundred
        if(!hundred.equals(0))
            res.insert(0,translatedNumbers.get(hundred*100)+" ");

        //ten, unit
        if(ten.equals(1))
            res.insert(res.length(),translatedNumbers.get(ten*10+unit)+" ");
        else {
            if(!ten.equals(0))
                res.insert(res.length() , translatedNumbers.get(ten * 10) + " ");
            //
            if(!unit.equals(0)) {
                if (rank == 0) { //for 1000
                    if (unit.equals(Integer.valueOf(1)))
                        res.insert(res.length(), "одна ");
                    else if (unit.equals(Integer.valueOf(2)))
                        res.insert(res.length() , "две ");
                    else
                        res.insert(res.length() , translatedNumbers.get(unit) + " ");
                } else
                    res.insert(res.length(), translatedNumbers.get(unit) + " ");

            }
        }

        //finding position in dictionary
        if(rank!=-1) {
            FileReader fr = new FileReader("src\\Dictionary");
            Scanner sc = new Scanner(fr);
            int position = rank * 3 + 2;
            if (unit.equals(1)&&!ten.equals(1))
                position -= 2;
            if ((unit.equals(2) || unit.equals(3) || unit.equals(4))&&!ten.equals(1))
                position -= 1;

            for (int i = 0; i < position; i++) {
                sc.next();
            }
            res.insert(res.length() , sc.next() + " ");

            fr.close();
        }

        return res;
    }
}

