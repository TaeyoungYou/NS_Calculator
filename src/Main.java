import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

import static java.lang.Math.pow;

public class Main {
    static String[] hex = { "A", "B", "C", "D", "E", "F" };

    public static void main(String[] args) throws IOException, InterruptedException {
        int targetN, convertN;
        String num, result, answer;
        boolean rewrite;
        boolean restart=true;
        Scanner scan = new Scanner(System.in);

        while(restart){
            do {
                clearScreen();
                rewrite = false;
                System.out.println("\tHello, it is convert calculator");
                System.out.println("----------------------------------------");
                System.out.println("\t[2 | 8 | 10 | 16]");
                System.out.print("\t From: ");
                targetN = scan.nextInt();
                System.out.println("\t[2 | 8 | 10 | 16 | 0(ALL)]");
                System.out.print("\t To: ");
                convertN = scan.nextInt();
                System.out.print("\t Number: ");
                num = scan.next();
                if (!num.contains(".")) {           // Convert to int, removing fraction part
                    num +=".";
                }
                if (targetN == convertN) {
                    System.out.println("Error: Target Number and Convert Number are same");
                    Thread.sleep(2000);
                    rewrite = true;
                }else if(!checkAcceptNum(num,targetN)){
                    System.out.println("\tThat is not allowed number");
                    Thread.sleep(2000);
                    rewrite=true;
                }
            } while (rewrite);
            System.out.println("----------------------------------------");
            loadingScreen();
            clearScreen();
            System.out.println("----------------------------------------");
            if(convertN == 0){
                if(conToDecimal(targetN,num)%1==0.0){               // integer
                    if(targetN!=2){
                        System.out.format("\tBinary:\t\t| %s -> %s\n",num.substring(0,num.length()-1),conToNS(2, conToDecimal(targetN,num)));
                    }
                    if(targetN!=8){
                        System.out.format("\tOctal:\t\t| %s -> %s\n",num.substring(0,num.length()-1),conToNS(8, conToDecimal(targetN,num)));
                    }
                    if(targetN!=10){
                        System.out.format("\tDecimal:\t\t| %s -> %s\n",num.substring(0,num.length()-1),conToDecimal(targetN,num)+"");
                    }
                    if(targetN!=16){
                        System.out.format("\tHexadecimal:\t| %s -> %s\n",num.substring(0,num.length()-1),conToNS(16, conToDecimal(targetN,num)));
                    }
                }else{          // with fraction
                    if(targetN!=2){
                        System.out.format("\tBinary:\t\t| %s -> %s\n",num,conToNS(2,conToDecimal(targetN,num)));
                    }
                    if(targetN!=8){
                        System.out.format("\tOctal:\t\t| %s -> %s\n",num,conToNS(8,conToDecimal(targetN,num)));
                    }
                    if(targetN!=10){
                        System.out.format("\tDecimal:\t\t| %s -> %s\n",num,conToDecimal(targetN,num)+"");
                    }
                    if(targetN!=16){
                        System.out.format("\tHexadecimal:\t| %s -> %s\n",num,conToNS(16,conToDecimal(targetN,num)));
                    }
                }
            }else{
                if (convertN == 10) {                       // To is decimal
                    result = conToDecimal(targetN,num)+"";
                } else {
                    result = conToNS(convertN, conToDecimal(targetN, num));
                }
                if (conToDecimal(targetN,num) % 1 == 0.0) {           // Convert to int, removing fraction part
                    num = num.substring(0,num.length()-1);
                }
                System.out.format("\t%s -> %s\n", num, result);
            }
            System.out.println("----------------------------------------");
            System.out.print("\tRESTART? true / false\n\t-> ");
            answer = scan.next();
            if(answer.equals("false")){
                restart=false;
            }
        }
    }

    // 2024.01.15. version 2 updated : checking allowed number to the number system
    public static boolean checkAcceptNum(String n,int t){
        for(String i: n.split("")){
            if(t==16){
                for(String j: hex){
                    if(i.equals(j)){
                        i=i.charAt(0)-55+"";    // convert String to char and -55(from ASCII) and convert to String again
                    }
                }
            }
            if(!i.equals(".") && Integer.parseInt(i)/t>0){
                return false;
            }
        }
        return true;
    }
    public static double conToDecimal(int convert, String num) {
        double resultDecimal = 0;

        if(convert == 10){
            resultDecimal = Double.parseDouble(num);
        }else{
            String wholeN = num.substring(0, num.indexOf("."));
            String fractionN = num.substring(num.indexOf(".") + 1);
            wholeN = new StringBuffer(wholeN).reverse().toString();
            String[] wholNArray=wholeN.split("");
            for (int ex = 0; ex < wholeN.length(); ex++) {
                for(String i: hex){                                         // find out alpabet includes in hex : find hexadecimal number
                    if(i.equals(wholNArray[ex])){
                        wholNArray[ex]=wholNArray[ex].charAt(0)-55+"";
                    }
                }
                resultDecimal += Integer.parseInt(wholNArray[ex]) * pow(convert, ex);
            }
            for (int ex = 1; ex <= fractionN.length(); ex++) {
                resultDecimal += Character.getNumericValue(fractionN.charAt(ex - 1)) * pow(convert, ex * (-1));
            }
        }
        return resultDecimal;
    }

    public static String conToNS(int convert, double deci) {
        ArrayList<String> result = new ArrayList<>();
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
    public static void clearScreen() throws IOException, InterruptedException{
        new ProcessBuilder("cmd","/c","cls").inheritIO().start().waitFor();
    }
    public static void loadingScreen() throws InterruptedException {
        System.out.print("\tLoading");
        for(int i=0;i<5;i++){
            System.out.print("..");
            Thread.sleep(500);
        }
        System.out.println("Completed!");
        Thread.sleep(1000);
    }
}