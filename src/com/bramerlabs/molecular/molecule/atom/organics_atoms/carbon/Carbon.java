package com.bramerlabs.molecular.molecule.atom.organics_atoms.carbon;

import com.bramerlabs.engine.math.Vector3f;
import com.bramerlabs.molecular.molecule.atom.Atom;
import com.bramerlabs.molecular.data_compilers.AtomicDataCompiler;

public class Carbon extends Atom {

    // the Van der Waals atomic radius of carbon
//    public static float ATOMIC_RADIUS = 1.7f;
    public static float ATOMIC_RADIUS = AtomicDataCompiler.getVDWRadius(Atom.CARBON);

    // the atomic number of carbon
    final static int ATOMIC_NUMBER = 6;

    // the name of the element
    final static String ELEMENT_NAME = "Carbon";
    final static String ELEMENT_NAME_ABBR = "C";

    /**
     * constructor for a carbon atom
     * @param position - the position of the carbon atom
     */
    public Carbon(Vector3f position) {
        super(position, ATOMIC_NUMBER);
    }
}