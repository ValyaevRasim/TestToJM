package com.company;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

//String[] r = {"I", "II", "III", "IV", "V", "VI", "VII", "VIII", "IX", "X"};

public class Main {
    public static void main(String[] args) {
        // ввод данных
//        Scanner param = new Scanner(System.in);
//        String inputString = param.nextLine();
        String inputString = "X/F";
        int sum = 0;
        String delimiters = "[/*-+]+";
        String[] splitResult = inputString.split(delimiters);
        try {
//            Pattern pattern = Pattern.compile("(-?[0-9])+[/*-+](-?[0-9]+)");
//            String delimiters = "(-?[0-9]+)[/*-+](-?[0-9]+)";
            System.out.println(Arrays.toString(splitResult));
            System.out.println(splitResult.length);

            System.out.println(Integer.parseInt(splitResult[0]));
            System.out.println(Integer.parseInt(splitResult[1]));
            if (splitResult.length > 2){
                System.out.println("Количество чисел больше 2");
                System.exit(0);
            }

        }catch (NumberFormatException e) {
            try {
                String parametr1 = splitResult[0];
                Pattern pattern = Pattern.compile("[I,V,X]");
                Matcher match = pattern.matcher(parametr1);
                while (match.find()) {
                    System.out.println(match.group());
                }
                /*
                String[] splitResult = inputString.split(delimiters);
                System.out.println(Arrays.toString(splitResult));
                System.out.println(splitResult.length);
                 */
            } catch (Exception r){
                System.out.println(e.getMessage());
            }





            System.out.println("Ошибка: Числа должны быть целыми");
        } catch (Exception e) {
            System.out.println("Ошибка: " + e.getMessage());
        }
    }

}

