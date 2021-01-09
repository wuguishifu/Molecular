package com.bramerlabs.molecular.molecule.atom;

public class AtomicDataCompiler {

    // the abbreviations of the atom names
    final static String[] atomicAbbreviations = new String[]{
        "H", "He", "Li", "Be", "B", "C", "N", "O", "F", "Ne", "Na", "Mg", "Al", "Si", "P", "S", "Cl", "Ar", "K",
        "Ca", "Sc", "Ti", "V", "Cr", "Mn", "Fe", "Co", "Ni", "Cu", "Zn", "Ga", "Ge", "As", "Se", "Br", "Kr", "Rb",
        "Sr", "Y", "Zr", "Nb", "Mo", "Tc", "Ru", "Rh", "Pd", "Ag", "Cd", "In", "Sn", "Sb", "Te", "I", "Xe", "Cs",
        "Ba", "La", "Ce", "Pr", "Nd", "Pm", "Sm", "Eu", "Gd", "Tb", "Dy", "Ho", "Er", "Tm", "Yb", "Lu", "Hf", "Ta",
        "W", "Re", "Os", "Ir", "Pt", "Au", "Hg", "Tl", "Pb", "Bi", "Po", "At", "Rn", "Fr", "Ra", "Ac", "Th", "Pa",
        "U", "Np", "Pu", "Am", "Cm", "Bk", "Cf", "Es", "Fm", "Md", "No", "Lr", "Rf", "Db", "Sg", "Bh", "Hs", "Mt",
        "Ds", "Rg", "Cn", "Nh", "Fl", "Mc", "Lv", "Ts", "Og"
    };

    // the number of atoms that exist
    final static int numAtoms = 118;

    /**
     * retrieves the atom name abbreviation of an atom with a specified atomic number
     * @param atomicNumber - the atomic number
     * @return - the name abbreviation of the specified atom
     */
    public static String getAtomAbbrName(int atomicNumber) {
        if (atomicNumber > 0 && atomicNumber <= 118) { // get the atom for an index i within [1, 118]
            return atomicAbbreviations[atomicNumber - 1];
        } else {
            return "Uuq";
        }
    }

}