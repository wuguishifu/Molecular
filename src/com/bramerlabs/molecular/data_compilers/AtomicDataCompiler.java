package com.bramerlabs.molecular.data_compilers;

import com.bramerlabs.engine.math.Vector3f;

import java.awt.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

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

    // the CPK color of each atom
    final static String[] atomicCPKHexColors = new String[]{
            "FFFFFF", "D9FFFF", "CC80FF", "C2FF00", "FFB5B5", "909090", "3050F8", "FF0D0D", "90E050", "B3E3F5",
            "AB5CF2", "8AFF00", "BFA6A6", "F0C8A0", "FF8000", "FFFF30", "1FF01F", "80D1E3", "8F40D4", "3DFF00",
            "E6E6E6", "BFC2C7", "A6A6AB", "8A99C7", "9C7AC7", "E06633", "F090A0", "50D050", "C88033", "7D80B0",
            "C28F8F", "668F8F", "BD80E3", "FFA100", "A62929", "5CB8D1", "7A2EB0", "00FF00", "94FFFF", "94E0E0",
            "73C2C9", "54B5B5", "3B9E9E", "248F8F", "0A7D8C", "006985", "C0C0C0", "FFD98F", "A67573", "668080",
            "9E63B5", "D47A00", "940094", "429EB0", "57178F", "00C900", "70D4FF", "FFFFC7", "D9FFC7", "C7FFC7",
            "A3FFC7", "8FFFC7", "61FFC7", "45FFC7", "30FFC7", "1FFFC7", "00FF9C", "00E675", "00D452", "00BF38",
            "00AB24", "4DC2FF", "4DA6FF", "2194D6", "267DAB", "266696", "175487", "D0D0E0", "FFD123", "B8B8D0",
            "A6544D", "575961", "9E4FB5", "AB5C00", "754F45", "428296", "420066", "007D00", "70ABFA", "00BAFF",
            "00A1FF", "008FFF", "0080FF", "006BFF", "545CF2", "785CE3", "8A4FE3", "A136D4", "B31FD4", "B31FBA",
            "B30DA6", "BD0D87", "C70066", "CC0059", "D1004F", "D90045", "E00038", "E6002E", "EB0026", "BFC2C7",
            "BFC2C7", "BFC2C7", "BFC2C7", "BFC2C7", "BFC2C7", "BFC2C7", "BFC2C7", "BFC2C7"
    };

    // the covalent radii of each atom
    public static ArrayList<int[]> covalentRadii = new ArrayList<>();

    // the Van der Waals radii of each atom
    public static ArrayList<Float> VDWRadii = new ArrayList<>();

    /**
     * initialize the compiler
     */
    public static void init() {
        compileCovalentRadii();
        compileVDWRadii();
    }

    /**
     * compile the Van der Waals radii
     */
    private static void compileVDWRadii() {
        try {
            Scanner input = new Scanner(new File("resources/atomic_data/Atom Van der Waals Radii"));
            for (int i = 0; i < 118; i++) {
                VDWRadii.add(input.nextFloat());
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    /**
     * gets the Van der Waals radius
     * @param atomicNumber - the atomic number
     * @return - the VDW radius
     */
    public static float getVDWRadius(int atomicNumber) {
        if (atomicNumber < 1 || atomicNumber > 118) {
            return 0;
        } else {
            return VDWRadii.get(atomicNumber - 1) == 0 ? 0.5f : VDWRadii.get(atomicNumber - 1); // if the VDW radius is 0, return 2.0f
        }
    }

    /**
     * compile the covalent radii
     */
    private static void compileCovalentRadii() {
        try {
            Scanner input = new Scanner(new File("resources/atomic_data/Atom Covalent Radii"));
            for (int i = 0; i < 118; i++) {
                covalentRadii.add(new int[]{input.nextInt(), input.nextInt(), input.nextInt(), input.nextInt()});
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    /**
     * gets the covalent radius of an atom
     * @param atomicNumber - the atomic number of the atom
     * @param bondOrder - the order of the bond
     * @return - the covalent radius
     */
    public static float getCovalentRadius(int atomicNumber, int bondOrder) {
        if (bondOrder == 1) {
            if (covalentRadii.get(atomicNumber - 1)[0] == 0) {
                return covalentRadii.get(atomicNumber - 1)[1] / 100.0f;
            } else {
                return covalentRadii.get(atomicNumber - 1)[0] / 100.0f;
            }
        } else if (bondOrder == 2) {
            return covalentRadii.get(atomicNumber - 1)[2] / 100.0f;
        } else if (bondOrder == 3) {
            return covalentRadii.get(atomicNumber - 1)[3] / 100.0f;
        }
        return 0;
    }

    /**
     * retrieves the atom name abbreviation of an atom with a specified atomic number
     * @param atomicNumber - the atomic number
     * @return - the name abbreviation of the specified atom
     */
    public static String getAtomAbbrName(int atomicNumber) {
        try {
            return atomicAbbreviations[atomicNumber - 1];
        } catch (IndexOutOfBoundsException e) {
            return "Null";
        }
    }

    /**
     * retrieves the CPK color of an atom with a specified atomic number
     * @param atomicNumber - the atomic number
     * @return - a Vector3f representing the color in the form [r, g, b]
     */
    public static Vector3f getCPKColor(int atomicNumber) {
        try {
            Color CPKColor = Color.decode("#" + atomicCPKHexColors[atomicNumber - 1]);
            return Vector3f.divide(new Vector3f(CPKColor.getRed(), CPKColor.getGreen(), CPKColor.getBlue()), new Vector3f(255));
        } catch (IndexOutOfBoundsException e) {
            return new Vector3f(0);
        }
    }

}