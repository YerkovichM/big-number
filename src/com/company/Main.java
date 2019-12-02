package com.company;

import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        BigNumberTest.testAll();
//        lab2();
    }

    private static void lab2() {
        BigNumber a = new BigNumber("1965");
        BigNumber b = BigNumber.ZERO;
        BigNumber mod = new BigNumber("2971");
        EllipticPoint generator = new EllipticPoint(8, 2123, a, mod);
        ElGamal gamal = new ElGamal(a, b, mod, generator, new BigNumber("1486"));
        List<Integer> message = Arrays.asList(223, 52, 62, 643, 283, 547, 887, 253);
        gamal.encryptAndDecrypt(message);
    }

    private void lab1() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("What you want? (system/calculate):");
        String comand = scanner.next();
        switch (comand) {
            case "system":
                system();
                break;
            case "calculate":
                calculate();
                break;
        }
    }

    private static void calculate() {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.println("Enter number:");
            BigNumber number = new BigNumber(scanner.next());
            System.out.println("Enter operation (/,*,+,-,^,%,sqrt,abs):");
            String operation = scanner.next();
            BigNumber second = new BigNumber("1");
            switch (operation) {
                case "+":
                case "-":
                case "/":
                case "*":
                case "^":
                case "%":
                    System.out.println("Enter second number:");
                    second = new BigNumber(scanner.next());
            }
            switch (operation) {
                case "+":
                    System.out.println(number.add(second));
                    break;
                case "-":
                    System.out.println(number.minus(second));
                    break;
                case "/":
                    System.out.println(number.divide(second));
                    break;
                case "*":
                    System.out.println(number.multiply(second));
                    break;
                case "^":
                    System.out.println(number.pow(second));
                    break;
                case "%":
                    System.out.println(number.mod(second));
                    break;
                case "sqrt":
                    System.out.println(number.sqrt());
                    break;
                case "abs":
                    System.out.println(number.abs());
                    break;
            }
        }
    }

    public static void system() {
        Scanner scanner = new Scanner(System.in);
        BigNumber m1 = new BigNumber(scanner.next());
        BigNumber a1 = new BigNumber(scanner.next());
        BigNumber m2 = new BigNumber(scanner.next());
        BigNumber a2 = new BigNumber(scanner.next());
        BigNumber m3 = new BigNumber(scanner.next());
        BigNumber a3 = new BigNumber(scanner.next());
        System.out.println(figureOut(m1, m2, m3, a1, a2, a3));
    }

    public static BigNumber figureOut(BigNumber m1, BigNumber m2, BigNumber m3,
                                      BigNumber a1, BigNumber a2, BigNumber a3) {
        BigNumber M0 = m1.multiply(m2).multiply(m3);
        BigNumber M1 = m2.multiply(m3);
        BigNumber M2 = m1.multiply(m3);
        BigNumber M3 = m1.multiply(m2);

        BigNumber modM1 = M1.mod(m1);
        BigNumber modM2 = M2.mod(m2);
        BigNumber modM3 = M3.mod(m3);

        BigNumber y1 = new BigNumber("1");
        BigNumber y2 = new BigNumber("1");
        BigNumber y3 = new BigNumber("1");

        for (BigNumber y = new BigNumber("1"); y.less(m1); y = y.add(new BigNumber("1"))) {
            if (modM1.multiply(y).mod(m1).equals(a1)) {
                y1 = y;
            }
        }

        for (BigNumber y = new BigNumber("1"); y.less(m2); y = y.add(new BigNumber("1"))) {
            if (modM2.multiply(y).mod(m2).equals(a2)) {
                y2 = y;
            }
        }

        for (BigNumber y = new BigNumber("1"); y.less(m3); y = y.add(new BigNumber("1"))) {
            if (modM3.multiply(y).mod(m3).equals(a3)) {
                y3 = y;
            }
        }

        return M1.multiply(y1).add(M2.multiply(y2)).add(M3.multiply(y3)).mod(M0);
    }
}
