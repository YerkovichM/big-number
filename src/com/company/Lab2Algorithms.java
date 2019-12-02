package com.company;

import java.math.BigInteger;
import java.util.*;

public class Lab2Algorithms {


    static BigNumber eulerFunc(BigNumber n) { //16 digit
        BigNumber result = new BigNumber(n);
        for (BigNumber i = new BigNumber("2"); !i.multiply(i).greater(n); i = i.add(BigNumber.ONE))
            if (n.mod(i).equals(BigNumber.ZERO)) {
                while (n.mod(i).equals(BigNumber.ZERO))
                    n = n.divide(i);
                result = result.minus(result.divide(i));
            }
        if (n.greater(BigNumber.ONE))
            result = result.minus(result.divide(n));
        return result;
    }

    static BigNumber mobiusFunc(BigNumber n) { //6 dgdt
        BigNumber p = BigNumber.ZERO;
        BigNumber two = new BigNumber("2");
        // Handling 2 separately
        if (n.mod(two).equals(BigNumber.ZERO)) {
            n = n.divide(two);
            p.add(BigNumber.ONE);

            // If 2^2 also divides N
            if (n.mod(two).equals(BigNumber.ZERO))
                return BigNumber.ZERO;
        }

        // Check for all other prine factors
        for (BigNumber i = new BigNumber("3"); !i.greater(n.sqrt()); i = i.add(two)) {
            // If i divides n
            if (n.pow(i).equals(BigNumber.ZERO)) {
                n = n.divide(i);
                p.add(BigNumber.ONE);

                // If i^2 also divides N
                if (n.mod(i).equals(BigNumber.ZERO))
                    return BigNumber.ZERO;
            }
        }

        return (p.mod(two).equals(BigNumber.ZERO)) ? new BigNumber("-1") : BigNumber.ONE;
    }

//    static BigNumber discreteLogarithmBSGS(BigNumber a, BigNumber b, BigNumber mod) {
//        HashMap<BigNumber, BigNumber> residues = new HashMap<>();
//        BigNumber n = mod.sqrt().add(BigNumber.ONE);
//        BigNumber base = a.powMod(n, mod);
//        BigNumber current = BigNumber.ONE;
//        BigNumber modMinus1 = mod.minus(BigNumber.ONE);
//        for (BigNumber i = BigNumber.ONE; !i.greater(n); i = i.add(BigNumber.ONE)) {
//            current = current.multiply(base);
//            current = current.mod(mod);
//            residues.put(current, i);
//        }
//        current = b;
//        for (BigNumber i = BigNumber.ZERO; !i.greater(n); i = i.add(BigNumber.ONE)) {
//            if (residues.containsKey(current)) {
//                BigNumber res = n.multiply(residues.get(current).minus(i));
//                return res.mod(modMinus1);
//            }
//            current = current.multiply(a);
//            current = current.mod(mod);
//        }
//        return new BigNumber("-1");
//    }

    static BigInteger discreteLogarithm(BigInteger a,BigInteger b, BigInteger m) {
        BigInteger n = m.sqrt().add(new BigInteger("1"));

        // Calculate a ^ n
        BigInteger an = BigInteger.ONE;
        for (BigInteger i = BigInteger.ZERO; i.compareTo(n) < 0; i =i.add(BigInteger.ONE))
            an = an.multiply(a).mod(m);

        Map<BigInteger, BigInteger> value = new HashMap<>();

        // Store all values of a^(n*i) of LHS
        for (BigInteger i = BigInteger.ONE, cur = an; i.compareTo(n) <= 0; i =i.add(BigInteger.ONE))
        {
            if (value.get(cur) == null || value.get(cur).equals(BigInteger.ZERO))
                value.put(cur, i);
            cur = cur.multiply(an).mod(m);
        }

        for (BigInteger i = BigInteger.ZERO, cur = b; i.compareTo(n) <= 0; i =i.add(BigInteger.ONE))
        {
            // Calculate (a ^ j) * b and check
            // for collision
            if (value.get(cur) != null && value.get(cur).compareTo(BigInteger.ZERO) > 0)
            {
                BigInteger ans = value.get(cur).multiply(n).subtract(i);
                if (ans.compareTo(m) < 0)
                    return ans;
            }
            cur = cur.multiply(a).mod(m);
        }
        return new BigInteger("-1");

    }

    static List<Pair> factorize(BigNumber n) {
        if (n.equals(BigNumber.ZERO) || n.equals(BigNumber.ONE)) {
            return Collections.emptyList();
        }
        Stack<BigNumber> s = new Stack<>();
        Map<BigNumber, BigNumber> map = new HashMap<>();
        s.push(n);
        while (!s.empty()) {
            BigNumber peek = s.pop();
            if (isPrime(peek, 100)) { // TODO
                map.putIfAbsent(peek, BigNumber.ZERO);
                map.put(peek, map.get(peek).add(BigNumber.ONE));
            } else {
                Pair factors = factor(peek);
                s.push(factors.left);
                s.push(factors.rigth);
            }
        }
        ArrayList<Pair> res = new ArrayList<>();
        for (Map.Entry<BigNumber, BigNumber> en : map.entrySet()) {
            res.add(new Pair(en.getKey(), en.getValue()));
        }
        return res;
    }


    static Pair factor(BigNumber n) {
        if (n.isEven()) {
            return new Pair(new BigNumber("2"), n.divide(new BigNumber("2")));
        }
        Random random = new Random();
        HashSet<Pair> set = new HashSet<>();
        int upperBound = n.less(new BigNumber("" + Integer.MAX_VALUE)) ? Integer.parseInt(n.toString()) - 1 : Integer.MAX_VALUE;
        BigNumber x_fast = new BigNumber("" + random.nextInt(upperBound));
        BigNumber x_slow = new BigNumber("" + random.nextInt(upperBound));
        BigNumber gcd;
        do {
            x_fast = x_fast.multiply(x_fast).add(BigNumber.ONE).mod(n);
            x_fast = x_fast.multiply(x_fast).add(BigNumber.ONE).mod(n);
            x_slow = x_slow.multiply(x_slow).add(BigNumber.ONE).mod(n);

            gcd = n.gcd((x_slow.minus(x_fast)).abs());
            BigNumber finalX_slow = x_slow;
            BigNumber finalX_fast = x_fast;
            boolean con = set.stream().anyMatch(pair -> pair.left.equals(finalX_slow) && pair.rigth.equals(finalX_fast));
            if (gcd == n || con) {
                x_slow = new BigNumber("" + random.nextInt(upperBound));
                ;
                x_fast = new BigNumber("" + random.nextInt(upperBound));
                set.clear();
            } else {
                set.add(new Pair(x_slow, x_fast));
            }
        } while (gcd.equals(BigNumber.ONE) || gcd.equals(n));
        return new Pair(gcd, n.divide(gcd));
    }

    static class Pair {
        public Pair(BigNumber left, BigNumber rigth) {
            this.left = left;
            this.rigth = rigth;
        }

        BigNumber left;
        BigNumber rigth;

        @Override
        public String toString() {
            return "Pair{" +
                    "left=" + left +
                    ", rigth=" + rigth +
                    '}';
        }
    }

    static int legendreSymbol(BigNumber a, BigNumber b) {
        if (!isPrime(b, 100)) {
            throw new NumberFormatException("Denominator is not prime, Legendre symbol is undefined");
        } else if (a.gcd(b).equals(b)) {
            return 0;
        }
        BigNumber res = a.powMod(b.minus(BigNumber.ONE).divide(new BigNumber("2")), b);
        if (!res.equals(BigNumber.ONE)) {
            res = res.minus(b);
        }
        return Integer.parseInt(res.toString());
    }

    static int jacobiSymbol(BigNumber a, BigNumber b) {
        BigNumber four = new BigNumber("4");
        BigNumber three = new BigNumber("3");
        if (b.isEven()) {
            throw new NumberFormatException("Denominator is even, Jacobi symbol is undefined");
        } else if (a.gcd(b).greater(BigNumber.ONE)) {
            return 0;
        } else if (b.equals(BigNumber.ONE)) {
            return 1;
        }
        int coef = 1;
        while (true) {
            a = a.mod(b);
            int counter = 0;
            while (a.isEven()) {
                counter++;
                a = a.divide(new BigNumber("2"));
            }
            counter = counter & 1;
            if (counter != 0) {
                long residue = (b.mod(new BigNumber("8")).value());
                if (residue == 3 || residue == 5) {
                    coef *= -1;
                }
            }
            if (a.equals(BigNumber.ONE)) {
                return coef;
            }
            if (a.mod(four).equals(three) && b.mod(four).equals(three)) {
                coef *= -1;
            }
            BigNumber temp = a;
            a = b;
            b = temp;
        }
    }

    static BigNumber discreteSquareRoot(BigNumber n, BigNumber p) {
        if (!isPrime(n, 100)) {
            throw new NumberFormatException("Modulo is not prime, Cipolla's algorithms doesn't work");
        } else if (legendreSymbol(n, p) != 1) {
            return new BigNumber("-1");
        }
        Random random = new Random();
        int upperBound = p.less(new BigNumber("" + Integer.MAX_VALUE)) ? Integer.parseInt(p.toString()) - 1 : Integer.MAX_VALUE;
        BigNumber a, square;
        do {
            a = new BigNumber("" + random.nextInt(upperBound));
            square = a.multiply(a).minus(n).mod(p);
        } while (isQuadraticResidue(square, p));
        Pair multiplier = new Pair(a, BigNumber.ONE);
        Pair res = new Pair(BigNumber.ONE, BigNumber.ONE);
        BigNumber power = p.add(BigNumber.ONE).divide(new BigNumber("2"));
        while (power.greater(BigNumber.ZERO)) {
            if (!power.isEven()) {
                res = multiply(res, multiplier, p, square);
            }
            power = power.divide(new BigNumber("2"));
            multiplier = multiply(multiplier, multiplier, p, square);
        }
        return res.left;
    }

    static Pair multiply(Pair p1, Pair p2, BigNumber p, BigNumber square) {
        return new Pair(p1.left.multiply(p2.left).add((p1.rigth.multiply(p2.rigth).multiply(square))).mod(p),
                p1.left.multiply(p2.rigth).add((p1.rigth.multiply(p2.left))).mod(p));
    }

    static boolean isQuadraticResidue(BigNumber n, BigNumber p) {
        return legendreSymbol(n, p) == 1;
    }

    static boolean isPrime(BigNumber n, int iteration)
    {
        /** base case **/
        if (n.equals(BigNumber.ZERO) || n.equals(BigNumber.ONE))
            return false;
        /** base case - 2 is prime **/
        BigNumber two = new BigNumber("2");
        if (n.equals(two))
            return true;
        /** an even number other than 2 is composite **/
        if (n.isEven())
            return false;
        BigNumber s = n.minus(BigNumber.ONE);
        while (s.mod(two).equals(BigNumber.ZERO))
            s = s.divide(two);

        Random rand = new Random();
        for (int i = 0; i < iteration; i++)
        {
            BigNumber r = new BigNumber("" + Math.abs(rand.nextLong()));
            BigNumber a = r.mod(n.minus(BigNumber.ONE)).add(BigNumber.ONE);
            BigNumber temp = s;
            BigNumber mod = a.powMod(temp, n);
            while (!temp.equals(n.minus(BigNumber.ONE)) && !mod.equals(BigNumber.ONE) && !mod.equals(n.minus(BigNumber.ONE)))
            {
                mod = mod.multiply(mod);
                mod = mod.mod(n);
                temp = temp.multiply(two);
            }
            if (!mod.equals(n.minus(BigNumber.ONE)) && temp.mod(two).equals(BigNumber.ZERO))
                return false;
        }
        return true;
    }
}
