package com.company;

import java.math.BigInteger;
import java.util.Objects;

public class EllipticPoint {
    static final EllipticPoint O = new EllipticPoint(-1, -1, BigNumber.ZERO, BigNumber.ZERO);

    BigNumber x, y, a ,mod;

    public EllipticPoint(BigNumber x, BigNumber y, BigNumber a, BigNumber mod) {
        this.x = x;
        this.y = y;
        this.a = a;
        this.mod = mod;
    }

    public EllipticPoint(EllipticPoint o) {
        this.x = o.x;
        this.y = o.y;
        this.a = o.a;
        this.mod = o.mod;
    }

    EllipticPoint(long x, long y, BigNumber a, BigNumber mod) {
        this.x = new BigNumber("" + x);
        this.y = new BigNumber("" + y);
        this.a = a;
        this.mod = mod;
    }

    EllipticPoint plus(EllipticPoint p) {
        if (p == O) {
            return this;
        } else if (this.equals(O)) {
            return p;
        } else if (this.equals(p.negative())) {
            return O;
        } else {
            BigNumber m;
            if (this.equals(p)) {
                m = (x.multiply(x).multiply(new BigNumber("3")).add(a)).multiply(y.multiply(new BigNumber("2")).groupInverse(mod));
                m = m.mod(mod);
            } else {
                m = (y.minus(p.y)).multiply((x.minus(p.x)).groupInverse(mod));
                m = m.mod(mod);
            }
            BigNumber xRes = m.multiply(m).minus(x).minus(p.x);
            xRes = xRes.mod(mod);
            BigNumber yRes = p.y.add(m.multiply(xRes.minus(p.x)));
            yRes = yRes.multiply(new BigNumber("-1"));
            yRes = yRes.mod(mod);
            return new EllipticPoint(xRes, yRes, a, mod);
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        EllipticPoint that = (EllipticPoint) o;
        return Objects.equals(x, that.x) &&
                Objects.equals(y, that.y) &&
                Objects.equals(a, that.a) &&
                Objects.equals(mod, that.mod);
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y, a, mod);
    }

    EllipticPoint multiply(BigNumber i){
        BigNumber power = new BigNumber(i);
        EllipticPoint res = O;
        EllipticPoint multiplier = new EllipticPoint(this);
        while (power.greater(BigNumber.ZERO)) {
            if (!power.isEven()) {
                res = res.plus(multiplier);
            }
            power = power.divide(new BigNumber("2"));
            multiplier = multiplier.plus(multiplier);
        }
        return res;
    }

    boolean less(EllipticPoint p){
        return x.less(p.x) || (x.equals(p.x) && y.less(p.y));
    }

    EllipticPoint negative(){
        return new EllipticPoint(this.x, this.y.negative().mod(mod), a, mod);
    }

    @Override
    public String toString() {
        return "(x=" + x + ", y=" + y + ")";
    }
}
