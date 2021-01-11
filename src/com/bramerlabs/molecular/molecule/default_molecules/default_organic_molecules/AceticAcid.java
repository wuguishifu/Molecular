package com.bramerlabs.molecular.molecule.default_molecules.default_organic_molecules;

import com.bramerlabs.engine.math.Vector3f;
import com.bramerlabs.molecular.molecule.Molecule;
import com.bramerlabs.molecular.molecule.atom.Atom;
import com.bramerlabs.molecular.molecule.bond.Bond;

import java.util.ArrayList;

public class AceticAcid extends Molecule {

    /**
     * constructor for specified atoms and bonds
     *
     * @param position - the position of the central atom
     * @param atoms    - the atoms making up this molecule
     * @param bonds    - the bonds making up this molecule
     */
    public AceticAcid(Vector3f position, ArrayList<Atom> atoms, ArrayList<Bond> bonds) {
        super(position, atoms, bonds);
    }
}
