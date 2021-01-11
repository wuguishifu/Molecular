package com.bramerlabs.molecular.molecule.atom.data_compilers;

public class BondDataCompiler {

    /**
     * calculates the bond length between two covalent atoms
     * @param a1 - the first atom
     * @param a2 - the second atom
     * @param bondOrder - the bond order
     * @return - the length of the bond
     */
    public static float getCovalentBondLength(int a1, int a2, int bondOrder) {
        if ((a1 > 118 || a1 < 1) || (a2 > 118 || a2 < 1) || (bondOrder < 1 || bondOrder > 3)) {
            return 0;
        }

        // get the covalent bond radii
        float a1r = AtomicDataCompiler.getCovalentRadius(a1, bondOrder);
        float a2r = AtomicDataCompiler.getCovalentRadius(a2, bondOrder);

        if (a1r == 0 || a2r == 0) {
            return 0;
        }

        // add them together
        return (a1r + a2r);
    }

}