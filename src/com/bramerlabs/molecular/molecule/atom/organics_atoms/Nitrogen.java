package com.bramerlabs.molecular.molecule.atom.organics_atoms;

import com.bramerlabs.engine.math.Vector3f;
import com.bramerlabs.molecular.molecule.atom.Atom;

public class Nitrogen extends Atom {

    // the Van der Waals atomic radius of oxygen
    public static float ATOMIC_RADIUS = 1.55f;

    // the atomic number of oxygen
    final static int ATOMIC_NUMBER = 7;

    // the name of the element
    final static String ELEMENT_NAME = "Nitrogen";
    final static String ELEMENT_NAME_ABBR = "N";

    /**
     * constructor for an oxygen atom
     * @param position - the position of the oxygen atom
     */
    public Nitrogen(Vector3f position) {
        super(position, ATOMIC_RADIUS, ATOMIC_NUMBER);
    }

    @Override
    public float getAtomicRadius() {
        return ATOMIC_RADIUS;
    }
}