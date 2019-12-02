package com.company;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

public class FirstOrderSystem {

    List<BigNumber> moduli;
    List<BigNumber> residues;
    int n;

    public FirstOrderSystem(ArrayList<BigNumber> moduli, ArrayList<BigNumber> residues) {
        this.moduli = moduli;
        this.residues = residues;
        this.n = moduli.size();
    }

    public List<BigNumber> getModuli() {
        return moduli;
    }

    public List<BigNumber> getResidues() {
        return residues;
    }

    public int getN() {
        return n;
    }



//    std::istream &operator>>(std::istream &is, algorithm::FirstOrderSystem &system) {
//        int n;
//        is >> n;
//        BigInteger t;
//        std::vector<BigInteger> moduli, residues;
//        for (int i = 0; i < n; i++) {
//            is >> t;
//            moduli.emplace_back(t);
//        }
//        for (int i = 0; i < n; i++) {
//            is >> t;
//            residues.emplace_back(t);
//        }
//        system.assign(moduli, residues);
//        return is;
//    }

    @Override
    public String toString() {
        String res =  "First order linear system with " + n + " equations:\n";
        for (int i = 0; i < n; i++) {
            res =+ (i + 1) + ". x = " + residues.get(i) + " (mod " + moduli.get(i) + ")\n";
        }
        return res;
    }

    void assign(List<BigNumber> moduli, List<BigNumber> residues) {
        this.residues = residues;
        this.moduli = moduli;
        this.n = residues.size();
    }

}
