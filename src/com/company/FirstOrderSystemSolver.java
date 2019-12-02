package com.company;

import java.math.BigInteger;
import java.util.List;

public class FirstOrderSystemSolver {
    FirstOrderSystem system;

    FirstOrderSystemSolver(FirstOrderSystem system){
        this.system = system;
    }

    BigNumber solve(){
        checkSystem();
        int n = system.getN();
        List<BigNumber> moduli = system.getModuli();
        List<BigNumber> residues = system.getResidues();
        BigNumber big_modulo = new BigNumber("1");
        BigNumber x = BigNumber.ZERO;
        for (BigNumber modulo : moduli) {
            big_modulo = big_modulo.multiply(modulo);
        }
        for (int i = 0; i < n; i++) {
            if (!residues.get(i).equals(BigNumber.ZERO)) {
                BigNumber mod = moduli.get(i);
                BigNumber M = big_modulo.divide(mod);
//                BigNumber mMod = M.mod(mod);
//                BigNumber groupInverse = mMod.groupInverse(moduli.get(i));
                BigNumber addendum = residues.get(i).multiply(M).multiply(M.groupInverse(moduli.get(i)));
                x = x.add(addendum);
            }
        }
        return x.mod(big_modulo);
    }

    void checkSystem(){
        int n = system.getN();
        List<BigNumber> moduli = system.getModuli();
        List<BigNumber> residues = system.getResidues();
        if (moduli.size() != residues.size()) {
            throw new NumberFormatException("Number of moduli isn't equal to the number of residues");
        }
        for (int i = 0; i < n; i++) {
            if (!residues.get(i).less(moduli.get(i))) {
                throw new NumberFormatException("Residues must be smaller than moduli");
            }
        }
        for (int i = 1; i < n; i++) {
            for (int j = 0; j < i; j++) {
                if (!moduli.get(i).isCoPrime(moduli.get(j))) {
                    throw new NumberFormatException("Moduli must be pairwise co-prime");
                }
            }
        }
    }

}
