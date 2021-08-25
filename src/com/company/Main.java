package com.company;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import static java.lang.Math.abs;

public class Main {

    public static void main(String[] args) {
        // ввод данных от пользователя
//        Scanner param = new Scanner(System.in);
//        String inputString = param.nextLine();
        String inputString = "X/-III";
        correct_regex result_regex = new correct_regex();
        int sum = 0;
        int result_len = 0;
        String znak;

        // данные заносим в массив
        List<Integer> number = new ArrayList<Integer>();
        parameter data = new parameter();
//        String[] splitResult = inputString.split(delimiters);

        try {
            //******************* посторонние символы
            result_regex.ptrn = "[^-?IVX/*-+\\d]";
            result_regex.source = inputString;
            if (result_regex.bool_cahr_is_correct()){
                // есть посторонние символы, выкидываем исключение
                throw new Exception();
            }

            //******************* количество чисел (д.б = 2)
            result_regex.ptrn = "(-?\\d+|-?[IVX]+)";
            result_regex.source = inputString;
            String [] result = result_regex.str_regex_result();
            result_len = result.length;

            if (result_len != 2) throw new Exception();

            //******************* формат числа
            int i = 0;
            int num;
            String type = null;
            result_regex.ptrn = "[IVX]";

            while (i < result_len){
                result_regex.source = result[i];

                if (result_regex.bool_cahr_is_correct()){
                    if (type == null || type == "roman"){
                        // римские
                        num = roman_to_arab.transform_roman_to_arabic(result[i]);
                        // римские должны быть в интервале до 10 вкл
                        if (result[i].charAt(0) == '-'){
                            znak = "-";
                            num = -num;
                        }
                        if (num <= 10){
                            number.add(num);
                        } else {
                            throw new Exception();
                        }
                    }
                    type = "roman";

                } else{
                    if (type == null || type == "arabic"){
                        // арабские
                        num = Integer.parseInt(result[i]);
                        // арабские должны быть в интервале от -10 до 10
                        if (abs(num)<=10){
                            number.add(num);
                        } else {
                            throw new Exception();
                        }
                    }
                    type = "arabic";
                }
                i++;
            }

            if (number.size() != 2){
                throw new Exception();
            }

            System.out.println(number);

            //******************* определяем знак
            result_regex.ptrn = "[^-?IVX\\d]";
            result_regex.source = inputString;

            data.param1 = number.get(0);
            data.param2 = number.get(1);
            String[] symb = result_regex.str_regex_result();

            if (symb.length == 0){
                data.symbol = "-";
            } else {
                data.symbol = result_regex.str_regex_result()[0];
            }

            sum = data.getResult();
            if (type == "roman"){
                if (sum > 0) {
                    System.out.println(arab_to_roman.transform_arab_to_roman(sum));
                } else{
                    System.out.println("У римских отрицательный не может быть");
                }
            } else {
                System.out.println(sum);
            }

        }catch (Exception e) {
            System.out.println("допускается ввод двух целых чисел(арабские(+/-) либо римские) в интервале от 1 до 10 или от I до X");
        }
    }

}


class correct_regex{
    String ptrn;
    String source;
    String[] strArr = null;

    boolean bool_cahr_is_correct() {
        Pattern pattern = Pattern.compile(ptrn);
        Matcher match = pattern.matcher(source);
        if (match.find()) {
            return true;
        }
        return false;
    }

    String[] str_regex_result(){
        try{
            Pattern pattern = Pattern.compile(ptrn);
            Matcher match = pattern.matcher(source);
            List<String> strList = new ArrayList<String>();
            while (match.find()) {
                strList.add(match.group());
            }
            strArr = strList.toArray(new String[strList.size()]);
            return strArr;
        } catch (Exception e){
            System.out.println("ERROR: " + e.getMessage());
        }finally {
            return strArr;
        }
    }


}


class parameter{
    String symbol; //   *-+/
    int param1;
    int param2;

    int getResult(){
        switch (symbol){
            case "*":
                return (param1 * param2);
            case "/":
                return (param1 / param2);
            case "+":
                return (param1 + param2);
            case "-":
                return (param1 - param2);
        }
        return 0;
    }
}


class arab_to_roman{
    public static String transform_arab_to_roman(int number) {
        //https://coding.tools/ru/numbers-to-roman-numerals
        int[] roman_value_list = new int[]{1000, 900, 500, 400, 100, 90, 50, 40, 10, 9, 5, 4, 1};
        String[] roman_char_list = new String[]{"M", "CM", "D", "CD", "C", "XC", "L", "XL", "X", "IX", "V", "IV", "I"};
        StringBuilder res = new StringBuilder();
        for (int i = 0; i < roman_value_list.length; i += 1) {
            while (number >= roman_value_list[i]){
                number -= roman_value_list[i];
                res.append(roman_char_list[i]);
            }
        }
        return res.toString();
    }
}


class roman_to_arab {
    //    https://coderoad.ru/26488606/Java-преобразование-римских-цифр
    static int value(char a) {
        if (a=='m') return 1000;
        else if (a=='d') return 500;
        else if (a=='c') return 100;
        else if (a=='l') return 50;
        else if (a=='x') return 10;
        else if (a=='v') return 5;
        else if (a=='i') return 1;
        else return 0;
    }

    public static int transform_roman_to_arabic(String roman) {
        roman=roman.toLowerCase();
        int val;
        int val_next;
        int temp;
        int result=0;

        for (int i=0;i<roman.length();i++) {
            try{
                val=value(roman.charAt(i));
                if (i<roman.length()-1) {
                    val_next=value(roman.charAt(i+1));
                } else val_next=0;

                if (val_next==0) {
                    temp=val;
                } else {
                    if (val>val_next) temp=val;
                    else if (val<val_next) {
                        temp=val_next-val;
                        i++;
//                    } else if (val==val_next) temp=val;
                    } else temp=val;
                }
                result+=temp;
            }
            catch(Exception e) {
                System.out.println("ERROR:transform_roman_to_arabic "  + e);
            }

        }
//        System.out.println("Result is: " + result);
        return result;
    }
}