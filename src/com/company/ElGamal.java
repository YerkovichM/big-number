package com.company;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

public class ElGamal {
    BigNumber a;
    BigNumber b;
    BigNumber p;
    EllipticPoint g;
    BigNumber N;

    public ElGamal(BigNumber a, BigNumber b, BigNumber p, EllipticPoint g, BigNumber n) {
        this.a = a;
        this.b = b;
        this.p = p;
        this.g = g;
        N = n;
    }

    void encryptAndDecrypt(String message) {
        ArrayList<Integer> mess = new ArrayList<>();
        message.chars().forEach(mess::add);
        encryptAndDecrypt(mess);
    }

    void encryptAndDecrypt(List<Integer> message) {
        System.out.print("Encrypting the message {");
        for (int i : message) {
            System.out.println("" + i + ", ");
        }
        System.out.println("}");
        System.out.println("Elliptic curve y^2 = x^3 + " + a + "x + " + b + " (mod " + p + ")");
        System.out.println("Generator point G = " + g + ", group order N = " + N);
        Random both = new Random();
        int upperBound = N.less(new BigNumber("" + Integer.MAX_VALUE)) ? Integer.parseInt(N.toString()) - 1 : Integer.MAX_VALUE;
        BigNumber power = new BigNumber("" + both.nextInt(upperBound));
        System.out.println("Secret number k = " + power);
        EllipticPoint gToPower = g.multiply(power);
        System.out.println("kG = Y = " + gToPower);
        BigNumber aliceNumber = new BigNumber("" + both.nextInt(upperBound));
        System.out.println("Alice generated number r = " + aliceNumber);
        EllipticPoint gToAlicePower = g.multiply(aliceNumber);
        EllipticPoint yToAlicePower = gToPower.multiply(aliceNumber);
        System.out.println("Encrypting the message");
        System.out.println("rG = " + gToAlicePower);
        System.out.println("(r*k)G = " + yToAlicePower);
        ArrayList<EllipticPoint> encr = new ArrayList<>();
        HashMap<EllipticPoint, Integer> charMap = new HashMap<>();
        for (int c : message) {
            BigNumber mult = new BigNumber("" + c);
            EllipticPoint mi = g.multiply(mult);
            EllipticPoint miEncr = mi.plus(yToAlicePower);
            System.out.println("" + c + " -> " + mi + " -> " + miEncr);
            encr.add(miEncr);
            charMap.put(mi, c);
        }
        System.out.println("Decrypting the message");
        EllipticPoint inverse = gToAlicePower.multiply(power);
        inverse = inverse.negative();

        System.out.println("-k(rG) = " + inverse);
        for (EllipticPoint point : encr) {
            EllipticPoint decr = point.plus(inverse);
            System.out.println("" + point + " - > " + decr + " -> " + charMap.get(decr));
        }
    }
}
