package com.bramerlabs.Molecular;

import java.util.ArrayList;

public class Molecule {

    // list of atoms that this molecule is made up of
    private ArrayList<Atom> atoms;

    // position stored in the form [x, y, z] of the center of the molecule
    private float[] position;

    // default position
    private final float[] defaultPosition = {0, 0, 0};

    public Molecule(float[] position) {
        if (position.length == 3) {
            this.position = position;
        } else {
            this.position = defaultPosition;
        }
    }

    public void addAtom(Atom atom) {
        this.atoms.add(atom);
    }
}
