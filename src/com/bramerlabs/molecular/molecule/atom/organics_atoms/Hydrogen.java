package com.bramerlabs.molecular.molecule.atom.organics_atoms;

import com.bramerlabs.engine.math.Vector3f;
import com.bramerlabs.molecular.molecule.atom.Atom;
import com.bramerlabs.molecular.data_compilers.AtomicDataCompiler;

public class Hydrogen extends Atom {

    // the Van der Waals atomic radius of oxygen
//    public static float ATOMIC_RADIUS = 1.09f;
    public static float ATOMIC_RADIUS = AtomicDataCompiler.getVDWRadius(Atom.HYDROGEN);

    // the atomic number of oxygen
    final static int ATOMIC_NUMBER = 1;

    // the name of the element
    final static String ELEMENT_NAME = "Hydrogen";
    final static String ELEMENT_NAME_ABBR = "H";

    /**
     * constructor for an oxygen atom
     * @param position - the position of the oxygen atom
     */
    public Hydrogen(Vector3f position) {
        super(position, ATOMIC_NUMBER);
    }
}