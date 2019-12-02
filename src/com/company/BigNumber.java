package com.company;

import java.util.*;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public final class BigNumber implements Comparable<BigNumber> {
    private final boolean isNegative;
    private final List<Integer> digits;

    public final static BigNumber ZERO = new BigNumber("0");
    public final static BigNumber ONE = new BigNumber("1");

    public BigNumber(String number) {
        checkFormat(number);
        boolean tempIsNegative = checkIfNegative(number);
        if (tempIsNegative) {
            number = number.substring(1);
        }
        digits = number.chars()
                .map(code -> code - 48)
                .boxed()
                .collect(Collectors.toList());
        Collections.reverse(digits);
        trim(digits);
        if (digits.get(digits.size() - 1) == 0) {
            isNegative = false;
        } else {
            isNegative = tempIsNegative;
        }
    }

    private BigNumber(boolean isNegative, List<Integer> digits) {
        this.isNegative = isNegative;
        this.digits = new ArrayList<>(digits);
    }

    public BigNumber(BigNumber bigNumber) {
        this.isNegative = bigNumber.isNegative;
        this.digits = new ArrayList<>(bigNumber.digits);
    }

    public boolean isEven() {
        return digits.get(0) % 2 == 0;
    }

    public BigNumber gcd(BigNumber a) {
        BigNumber result = this, b = a;
        while (b.greater(BigNumber.ZERO)) {
            BigNumber t = b;
            b = result.mod(b);
            result = t;
        }
        return result;
    }

    public BigNumber minus(BigNumber another) {
        if (isNegative != another.isNegative) {
            return this.add(new BigNumber(!another.isNegative, another.digits));
        }
        BigNumber minuend = isNegative ? another.withOpSign() : this;
        BigNumber subtrahend = isNegative ? this.withOpSign() : another;
        boolean resIsNegative = subtrahend.greater(minuend);
        if (resIsNegative) {
            BigNumber temp = minuend;
            minuend = subtrahend;
            subtrahend = temp;
        }
        ArrayList<Integer> result = new ArrayList<>();
        int temp = 0;
        for (int i = 0; i < minuend.digits.size(); i++) {
            int a = minuend.getDigitOrZero(i);
            int b = subtrahend.getDigitOrZero(i);
            if (a - b - temp < 0) {
                result.add(a + 10 - b - temp);
                temp = 1;
            } else {
                result.add(a - b - temp);
                temp = 0;
            }
        }
        trim(result);
        return new BigNumber(resIsNegative, result);
    }

    private BigNumber withOpSign() {
        return new BigNumber(!isNegative, digits);
    }

    private void trim(List<Integer> digits) {
        for (int i = digits.size() - 1; i > 0; i--) {
            if (digits.get(i) == 0) {
                digits.remove(i);
            } else {
                break;
            }
        }
    }

    public BigNumber add(BigNumber another) {
        if (isNegative != another.isNegative) {
            if (isNegative) {
                return another.minus(new BigNumber(false, digits));
            } else {
                return this.minus(new BigNumber(false, another.digits));
            }
        }
        ArrayList<Integer> result = new ArrayList<>();
        int longer = Math.max(digits.size(), another.digits.size());
        int temp = 0;
        for (int i = 0; i < longer; i++) {
            int a = getDigitOrZero(i);
            int b = another.getDigitOrZero(i);
            int sum = a + b + temp;
            temp = sum / 10;
            result.add(sum % 10);
        }
        if (temp != 0) {
            result.add(temp);
        }
        return new BigNumber(isNegative, result);
    }

    public boolean greater(BigNumber another) {
        if (this.isNegative != another.isNegative) {
            return !this.isNegative;
        }
        BigNumber op1 = isNegative ? another : this;
        BigNumber op2 = isNegative ? this : another;
        if (op1.digits.size() != op2.digits.size()) {
            return op1.digits.size() > op2.digits.size();
        }
        for (int i = op1.digits.size() - 1; i >= 0; i--) {
            if (op1.digits.get(i) != op2.digits.get(i)) {
                return op1.digits.get(i) > op2.digits.get(i);
            }
        }
        return false;
    }

    @Override
    public boolean equals(Object o) {
        BigNumber another = (BigNumber) o;
        if (isNegative != another.isNegative) {
            return false;
        }
        return digits.equals(another.digits);
    }

    public BigNumber multiply(BigNumber another) {
        boolean resNegative = isNegative != another.isNegative;
        BigNumber resNumber = new BigNumber("0");
        for (int i = 0; i < another.digits.size(); i++) {
            int digit = another.digits.get(i);
            ArrayList<Integer> tempDigits = new ArrayList<>();
            fillWithZero(tempDigits, i);
            int temp = 0;
            for (int j = 0; j < digits.size(); j++) {
                int a = digits.get(j);
                int res = a * digit + temp;
                tempDigits.add(res % 10);
                temp = res / 10;
            }
            tempDigits.add(temp);
            trim(tempDigits);
            resNumber = resNumber.add(new BigNumber(false, tempDigits));
        }
        return new BigNumber(resNegative, resNumber.digits);
    }

    public BigNumber divide(BigNumber another) {
        boolean resNegative = isNegative != another.isNegative;
        BigNumber divided = this.abs();
        BigNumber divider = another.abs();
        if (divided.less(divider)) {
            return new BigNumber("0");
        }
        ArrayList<Integer> resDigits = new ArrayList<>();
        BigNumber oper = new BigNumber("0");
        for (int i = divided.digits.size() - 1; i >= 0; i--) {
            oper = new BigNumber(oper.toString() + divided.digits.get(i));
            if (oper.less(divider)) {
                resDigits.add(0, 0);
            } else {
                int digit = 9;
                for (int j = 2; j <= 9; j++) {
                    BigNumber multiplied = divider.multiply(new BigNumber("" + j));
                    if (oper.less(multiplied)) {
                        digit = --j;
                        break;
                    }
                }
                resDigits.add(0, digit);
                oper = oper.minus(divider.multiply(new BigNumber("" + digit)));
            }
        }
        trim(resDigits);
        return new BigNumber(resNegative, resDigits);
    }

    public BigNumber mod(BigNumber another) {
        BigNumber d = this.divide(another);
        BigNumber m = d.multiply(another);
        if (equals(m)) {
            return new BigNumber("0");
        }
        if (isNegative) {
            if (another.isNegative) {
                return this.minus(m.add(another));
            } else {
                return this.minus(m.minus(another));
            }
        } else {
            return this.minus(m);
        }
    }

    public BigNumber pow(BigNumber power) {
        if (power.isNegative) {
            throw new NumberFormatException();
        }
        Map<BigNumber, BigNumber> powMap = new HashMap<>();
        if (power.equals(new BigNumber("0"))) {
            return new BigNumber("1");
        }
        BigNumber currentPower = new BigNumber("1");
        BigNumber maxPow = new BigNumber("1");
        BigNumber result = new BigNumber(this);
        powMap.put(currentPower, result);
        while (!currentPower.equals(power)) {
            BigNumber futurePower = currentPower.add(maxPow);
            if (!futurePower.greater(power)) {
                result = result.multiply(powMap.get(maxPow));
                currentPower = futurePower;
                maxPow = currentPower;
                powMap.put(maxPow, result);
            } else {
                break;
            }
        }
        while (!currentPower.equals(power)) {
            maxPow = maxPow.divide(new BigNumber("2"));
            BigNumber futurePower = currentPower.add(maxPow);
            if (!futurePower.greater(power)) {
                result = result.multiply(powMap.get(maxPow));
                currentPower = futurePower;
            }
        }
        return result;
    }

    public BigNumber powMod(BigNumber power, BigNumber mod) {
        if (power.isNegative) {
            throw new NumberFormatException();
        }
        Map<BigNumber, BigNumber> powMap = new HashMap<>();
        if (power.equals(new BigNumber("0"))) {
            return new BigNumber("1");
        }
        BigNumber currentPower = new BigNumber("1");
        BigNumber maxPow = new BigNumber("1");
        BigNumber result = new BigNumber(this);
        powMap.put(currentPower, result);
        while (!currentPower.equals(power)) {
            BigNumber futurePower = currentPower.add(maxPow);
            if (!futurePower.greater(power)) {
                result = result.multiply(powMap.get(maxPow));
                result = result.mod(mod);
                currentPower = futurePower;
                maxPow = currentPower;
                powMap.put(maxPow, result);
            } else {
                break;
            }
        }
        while (!currentPower.equals(power)) {
            maxPow = maxPow.divide(new BigNumber("2"));
            BigNumber futurePower = currentPower.add(maxPow);
            if (!futurePower.greater(power)) {
                result = result.multiply(powMap.get(maxPow));
                result = result.mod(mod);
                currentPower = futurePower;
            }
        }
        return result;
    }

    public BigNumber sqrt() {
        if (equals(new BigNumber("0")) || equals(new BigNumber("1"))) {
            return this;
        }
        BigNumber previous = new BigNumber("1");
        BigNumber next = this.divide(previous).add(previous).divide(new BigNumber("2"));
        ;
        do {
            previous = next;
            next = this.divide(next).add(next).divide(new BigNumber("2"));
        } while (previous.minus(next).greater(new BigNumber("0")));
        return next;
    }

    public BigNumber abs() {
        return new BigNumber(false, digits);
    }

    private void fillWithZero(ArrayList<Integer> digits, int i) {
        for (int j = 1; j <= i; j++) {
            digits.add(0);
        }
    }

    public boolean less(BigNumber another) {
        boolean greater = greater(another);
        boolean equal = equals(another);
        return !equal && !greater;
    }

    private int getDigitOrZero(int index) {
        if (digits.size() <= index) {
            return 0;
        }
        return digits.get(index);
    }

    private boolean checkIfNegative(String number) {
        return number.charAt(0) == '-';
    }

    private void checkFormat(String number) {
        Pattern numberPattern = Pattern.compile("-?[0-9]+");
        if (!numberPattern.matcher(number).matches()) {
            throw new NumberFormatException();
        }
    }

    @Override
    public String toString() {
        StringBuilder s = new StringBuilder(isNegative ? "-" : "");
        for (int i = digits.size() - 1; i >= 0; i--) {
            s.append(digits.get(i));
        }
        return s.toString();
    }

    @Override
    public int hashCode() {
        return digits.hashCode();
    }

    @Override
    public int compareTo(BigNumber o) {
        return 0;
    }

    public long value() {
        return Long.parseLong(toString());
    }

    BigNumber groupInverse(BigNumber modulo) {
        Lab2Algorithms.Pair res = groupInverse(mod(modulo), modulo);
        return res.rigth.mod(modulo);
    }

    Lab2Algorithms.Pair groupInverse(BigNumber a, BigNumber b) {
        if (b.equals(ZERO)) {
            if (!a.equals(ONE)) {
                throw new NumberFormatException("Inverse element doesn't exist: " + a.toString() + " and " + b.toString());
            } else {
                return new Lab2Algorithms.Pair(ONE, ZERO);
            }
        } else {
            BigNumber mod = a.mod(b);
            Lab2Algorithms.Pair res = groupInverse(b, mod);
            return new Lab2Algorithms.Pair(res.rigth, res.left.minus(a.divide(b).multiply(res.rigth)));
        }
    }

    public BigNumber negative() {
        return new BigNumber(!isNegative, new ArrayList<>(digits));
    }

    public boolean isCoPrime(BigNumber bigNumber) {
        return gcd(bigNumber).equals(ONE);
    }
}
