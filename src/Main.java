import java.util.ArrayList;
import java.util.Scanner;

import static java.lang.Math.pow;
import static java.lang.Math.round;

public class Main {
    public static void main(String[] args) throws InterruptedException {
        int targetN, convertN;
        String num, result;
        boolean rewrite;
        Scanner scan = new Scanner(System.in);

        do {
            rewrite = false;
            System.out.println("\tHello, it is convert calculator");
            System.out.println("----------------------------------------");
            System.out.println("\t[2 | 8 | 10 | 16]");
            System.out.print("\t From: ");
            targetN = scan.nextInt();
            System.out.println("\t[2 | 8 | 10 | 16]");
            System.out.print("\t To: ");
            convertN = scan.nextInt();
            System.out.print("\t Number: ");
            num = Double.parseDouble(scan.next()) + "";
            if (targetN == convertN) {
                System.out.println("Error: Target Number and Convert Number are same");
                Thread.sleep(2000);
                rewrite = true;
            }
        } while (rewrite);
        System.out.println("----------------------------------------");
        if (targetN == 10) {
            result = conToNS(convertN, Double.parseDouble(num));
        } else {
            if (convertN == 10) {
                result = conToDecimal(targetN, num) + "";
            } else {
                result = conToNS(convertN, conToDecimal(targetN, num));
            }
        }
        if (Double.parseDouble(num) % 1 == 0.0) {
            num = round(Double.parseDouble(num)) + "";
        }
        System.out.format("\t%s -> %s\n", num, result);
    }

    public static double conToDecimal(int convert, String num) {
        double resultDecimal = 0;

        String wholeN = num.substring(0, num.indexOf("."));
        String fractionN = num.substring(num.indexOf(".") + 1);
        wholeN = new StringBuffer(wholeN).reverse().toString();
        for (int ex = 0; ex < wholeN.length(); ex++) {
            resultDecimal += Character.getNumericValue(wholeN.charAt(ex)) * pow(convert, ex);
        }
        for (int ex = 1; ex <= fractionN.length(); ex++) {
            resultDecimal += Character.getNumericValue(fractionN.charAt(ex - 1)) * pow(convert, ex * (-1));
        }
        return resultDecimal;
    }

    public static String conToNS(int convert, double deci) {
        ArrayList<String> result = new ArrayList<>();
        String[] hex = { "A", "B", "C", "D", "E", "F" };
        int wholeN = (int) deci;
        double fractionN = deci - wholeN;

        while (wholeN != 0) {
            int t = wholeN % convert;
            if (t > 9) {
                result.add(0, hex[t - 10]);
            } else {
                result.add(0, t + "");
            }
            wholeN = wholeN / convert;
        }
        if (fractionN != 0) {
            result.add(".");
            while (fractionN != 0.0) {
                int t = (int) (fractionN * convert);
                if (t > 9) {
                    result.add(hex[t - 10]);
                } else {
                    result.add(t + "");
                }
                fractionN = (fractionN * convert) - t;
            }
        }
        return String.join("", result);
    }
}