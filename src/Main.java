import java.util.Scanner;
import java.util.regex.Pattern;
public class Main {
    private static final int[] arab = new int[] {1, 4, 5, 9, 10, 40, 50, 90, 100, 400, 500, 900, 1000};
    private static final String[] roman = new String [] {"I","IV","V","IX","X","XL","L","XC","C","CD","D","CM","M"};
    public static void main(String[] args) throws Exception {
        Scanner scanner = new Scanner(System.in);
        while(true) {
            System.out.println("Введите выражение или пустую строку для выхода:");
            String str = scanner.nextLine();
            if(str.equals("")) return;
            str = str.toUpperCase().replaceAll("\\s+", "");
            System.out.println("= "+calc(str));
        }
    }
    public static String calc(String input) throws Exception {
        String result,left,right;
        int a,b;
        boolean roman=false;
        if(Pattern.matches("-?[0-9IVXLCDM]+[-+/*]-?[0-9IVXLCDM]+",input)){
            int i = input.indexOf("*");
            if(i==-1) i = input.indexOf("/");
            if(i==-1) i = input.indexOf("+");
            if(i==-1) i = input.indexOf("-", 1);
            left = input.substring(0,i);
            right = input.substring(i+1);
            if(Pattern.matches("-?\\d+", left) && Pattern.matches("-?\\d+", right)) {
                a = Integer.parseInt(left);
                b = Integer.parseInt(right);
            } else if(Pattern.matches("^(M{0,3})(D?C{0,3}|C[DM])(L?X{0,3}|X[LC])(V?I{0,3}|I[VX])$", left) && Pattern.matches("^(M{0,3})(D?C{0,3}|C[DM])(L?X{0,3}|X[LC])(V?I{0,3}|I[VX])$", right)) {
                a = romanToArab(left);
                b = romanToArab(right);
                roman=true;
            } else throw new Exception("Используются одновременно разные системы счисления или неправильный формат римских чисел");
            if (a>10||b>10) throw new Exception("Одно из введённых чисел больше 10");
            a = switch (String.valueOf(input.charAt(i))) {
                case "*" -> a * b;
                case "/" -> a / b;
                case "+" -> a + b;
                case "-" -> a - b;
                default -> a;
            };
        } else throw new Exception("Не подходящее выражение");
        if(roman) result = arabToRoman(a);
        else result = String.valueOf(a);
        return result;
    }
    public static String arabToRoman(int number) throws Exception {
        String ret="";
        int i = arab.length-1;
        if(number>3999) throw new Exception("Числа более 3999 римскими цифрами I V X L C D M не поддерживаются");
        if(number<1) throw new Exception("В римской системе нет отрицательных чисел и ноля");
        while(number > 0){
            if(number >= arab[i]){
                ret = ret + (roman[i]);
                number -= arab[i];
            } else {
                i--;
            }
        }
        return ret;
    }
    public static int romanToArab(String str) throws Exception {
        int ret = 0;
        int i = arab.length - 1;
        int pos = 0;
        if(Pattern.matches("^(M{0,3})(D?C{0,3}|C[DM])(L?X{0,3}|X[LC])(V?I{0,3}|I[VX])$",str)) {
            while (i >= 0 && pos < str.length()) {
                if (str.startsWith(roman[i], pos)) {
                    ret += arab[i];
                    pos += roman[i].length();
                } else {
                    i--;
                }
            }
        }else throw new Exception("Не правильный формат римских чисел");
        return ret;
    }
}