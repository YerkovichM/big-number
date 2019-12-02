package com.company;

import java.math.BigInteger;

class BigNumberTest {

    public static void testAll() {
//        plusTest();
//        plusDifSignTest();
//        plusDifSign2Test();
//        plusNegativeTest();
//        plusLargeTest();
//
//        minusTest();
//        minusNegativeTest();
//        minusDifSignTest();
//        minusDifSign2Test();
//
//        multiplyTest();
//
//        divideTest();
//
//        modTest();
//        mod2Test();
//        mod3Test();
//
//        powTest();
//
//        sqrtTest();
//        powModTest();
//        eulerTest();

//        mobiusTest();
        BSGSTest();
//        factorizeTest();

//        jacobiTest();
//        lagrangeTest();

//        discreteSquareRootTest();
//        solovyov();
    }

    public static void plusTest() {
        BigNumber number = new BigNumber("123456");
        BigNumber sum = number.add(number);
        System.out.println("246912=\n" + sum.toString());
        System.out.println("----------------------------------------------------");
    }

    public static void plusDifSignTest() {
        BigNumber number1 = new BigNumber("246");
        BigNumber number2 = new BigNumber("-1234");
        BigNumber sum = number1.add(number2);
        System.out.println("-988=\n" + sum.toString());
        System.out.println("----------------------------------------------------");
    }

    public static void plusDifSign2Test() {
        BigNumber number1 = new BigNumber("1234");
        BigNumber number2 = new BigNumber("-246");
        BigNumber sum = number2.add(number1);
        System.out.println("988=\n" + sum.toString());
        System.out.println("----------------------------------------------------");
    }

    public static void plusNegativeTest() {
        BigNumber number1 = new BigNumber("-123456");
        BigNumber number2 = new BigNumber("-123456");
        BigNumber sum = number2.add(number1);
        System.out.println("-246912=\n" + sum.toString());
        System.out.println("----------------------------------------------------");
    }

    public static void plusLargeTest() {
        BigNumber number1 = new BigNumber("123456789012345678901234567890");
        BigNumber number2 = new BigNumber("123456789012345678901234567890");
        BigNumber sum = number2.add(number1);
        System.out.println("246913578024691357802469135780=\n" + sum.toString());
        System.out.println("----------------------------------------------------");
    }

    public static void minusTest() {
        BigNumber number1 = new BigNumber("1234");
        BigNumber number2 = new BigNumber("246");
        BigNumber sum = number1.minus(number2);
        System.out.println("988=\n" + sum.toString());
        System.out.println("----------------------------------------------------");
    }

    public static void minusNegativeTest() {
        BigNumber number1 = new BigNumber("-1234");
        BigNumber number2 = new BigNumber("-246");
        BigNumber sum = number1.minus(number2);
        System.out.println("-988=\n" + sum.toString());
        System.out.println("----------------------------------------------------");
    }

    public static void minusDifSignTest() {
        BigNumber number1 = new BigNumber("1234");
        BigNumber number2 = new BigNumber("-246");
        BigNumber sum = number1.minus(number2);
        System.out.println("1480=\n" + sum.toString());
        System.out.println("----------------------------------------------------");
    }

    public static void minusDifSign2Test() {
        BigNumber number1 = new BigNumber("-1234");
        BigNumber number2 = new BigNumber("246");
        BigNumber sum = number1.minus(number2);
        System.out.println("-1480=\n" + sum.toString());
        System.out.println("----------------------------------------------------");
    }

    public static void multiplyTest() {
        BigNumber number = new BigNumber("1234567890123");
        BigNumber sum = number.multiply(number);
        System.out.println("-1480=\n" + sum.toString());
        System.out.println("----------------------------------------------------");
    }

    public static void divideTest() {
        BigNumber number1 = new BigNumber("123456756756453454353");
        BigNumber number2 = new BigNumber("246123311");
        BigNumber sum = number1.divide(number2);
        System.out.println("501605297990=\n" + sum.toString());
        System.out.println("----------------------------------------------------");
    }

    public static void modTest() {
        BigNumber number1 = new BigNumber("-7");
        BigNumber number2 = new BigNumber("3");
        BigNumber sum = number1.mod(number2);
        System.out.println("2=\n" + sum.toString());
        System.out.println("----------------------------------------------------");
    }


    public static void mod2Test() {
        BigNumber number1 = new BigNumber("7");
        BigNumber number2 = new BigNumber("-3");
        BigNumber sum = number1.mod(number2);
        System.out.println("1=\n" + sum.toString());
        System.out.println("----------------------------------------------------");
    }

    public static void mod3Test() {
        BigNumber number1 = new BigNumber("-7");
        BigNumber number2 = new BigNumber("-3");
        BigNumber sum = number1.mod(number2);
        System.out.println("2=\n" + sum.toString());
        System.out.println("----------------------------------------------------");
    }

    public static void powTest() {
        BigNumber number1 = new BigNumber("2134");
        BigNumber number2 = new BigNumber("4");
        BigNumber sum = number1.pow(number2);
        System.out.println("20738515249936=\n" + sum.toString());
        System.out.println("----------------------------------------------------");
    }

    public static void sqrtTest() {
        BigNumber number1 = new BigNumber("324643634");
        BigNumber sum = number1.sqrt();
        System.out.println("18017=\n" + sum.toString());
        System.out.println("----------------------------------------------------");
    }

    public static void powModTest() {
        BigNumber number = new BigNumber("126");
        BigNumber power = new BigNumber("125");
        BigNumber mod = new BigNumber("345");
        BigNumber sum = number.powMod(power, mod);
        System.out.println("171=\n" + sum.toString());
        System.out.println("----------------------------------------------------");
    }

    public static void eulerTest() {
        BigNumber number = new BigNumber("1261231231231231");
        BigNumber result = Lab2Algorithms.eulerFunc(number);
        System.out.println("1138723144814304=\n" + result.toString());
        System.out.println("----------------------------------------------------");
    }

    public static void mobiusTest() {
        BigNumber number = new BigNumber("126123");
        BigNumber result = Lab2Algorithms.mobiusFunc(number);
        System.out.println("-1=\n" + result.toString());
        System.out.println("----------------------------------------------------");
    }

    public static void BSGSTest() {
        BigInteger a = new BigInteger("3");
        BigInteger b = new BigInteger("1");
        BigInteger mod = new BigInteger("196134577");

        BigInteger result = Lab2Algorithms.discreteLogarithm(a, b, mod);
        System.out.println("=\n" + result.toString());
        System.out.println("----------------------------------------------------");
    }

    public static void factorizeTest() {
        BigNumber a = new BigNumber("17348256187264213649126346457");
        System.out.println("=\n" + Lab2Algorithms.factorize(a));
        System.out.println("----------------------------------------------------");
    }

    public static void jacobiTest() {
        BigNumber a = new BigNumber("2423423424234132");
        BigNumber b = new BigNumber("29");
        System.out.println("Jac =\n" + Lab2Algorithms.jacobiSymbol(a, b));
        System.out.println("----------------------------------------------------");
    }

    public static void lagrangeTest() {
        BigNumber a = new BigNumber("2423423424234132");
        BigNumber b = new BigNumber("29");
        System.out.println("Leg =\n" + Lab2Algorithms.legendreSymbol(a,b));
        System.out.println("----------------------------------------------------");
    }

    public static void discreteSquareRootTest() {
        BigNumber a = new BigNumber("46456");
        BigNumber b = new BigNumber("43");
        System.out.println("DSR =\n" + Lab2Algorithms.discreteSquareRoot(a, b));
        System.out.println("----------------------------------------------------");
    }

    public static void solovyov() {
        BigNumber a = new BigNumber("184467440882994862139");
        int b = 100;
        System.out.println("Solovyov =\n" + Lab2Algorithms.isPrime(a,b));
        System.out.println("----------------------------------------------------");
    }
}