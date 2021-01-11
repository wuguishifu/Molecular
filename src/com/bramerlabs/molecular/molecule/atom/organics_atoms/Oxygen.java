package com.bramerlabs.molecular.molecule.atom.organics_atoms;

import com.bramerlabs.engine.math.Vector3f;
import com.bramerlabs.molecular.molecule.atom.Atom;

public class Oxygen extends Atom {

    // the Van der Waals atomic radius of oxygen
    public static float ATOMIC_RADIUS = 1.52f;

    // the atomic number of oxygen
    final static int ATOMIC_NUMBER = 8;

    // the name of the element
    final static String ELEMENT_NAME = "Oxygen";
    final static String ELEMENT_NAME_ABBR = "O";

    /**
     * constructor for an oxygen atom
     * @param position - the position of the oxygen atom
     */
    public Oxygen(Vector3f position) {
        super(position, ATOMIC_RADIUS, ATOMIC_NUMBER);
    }

    @Override
    public float getAtomicRadius() {
        return ATOMIC_RADIUS;
    }
}