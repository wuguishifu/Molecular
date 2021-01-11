package com.bramerlabs.molecular.molecule.default_molecules.default_organic_molecules;

import com.bramerlabs.engine.math.Vector3f;
import com.bramerlabs.molecular.molecule.Molecule;
import com.bramerlabs.molecular.molecule.atom.organics_atoms.Carbon;
import com.bramerlabs.molecular.molecule.atom.organics_atoms.Hydrogen;
import com.bramerlabs.molecular.molecule.bond.Bond;

import java.util.ArrayList;

public class Benzene extends Molecule {

    // vectors representing the direction of the bonds
    private final static float[][] positions = new float[][]{{0, 0, 1}, {-0.866025f, 0, 0.5f}, {-0.866025f, 0, -0.5f}, {0, 0, -1}, {0.866025f, 0, -0.5f}, {0.866025f, 0, 0.5f}};

    /**
     * constructor for specified values
     *
     * @param position - the position of the central molecule
     */
    public Benzene(Vector3f position) {
        super(position, new ArrayList<>(), new ArrayList<>());

        float CHBond = Carbon.ATOMIC_RADIUS + Hydrogen.ATOMIC_RADIUS + 1.09f;
        float CCBond = Carbon.ATOMIC_RADIUS + Carbon.ATOMIC_RADIUS + 1.54f;

        // add the carbon atoms and bonds
        for (float[] p : positions) {
            this.addAtom(new Carbon(Vector3f.add(Vector3f.normalize(new Vector3f(p), CCBond), position)));
        }
        this.addBond(new Bond(this.getAtoms().get(1), this.getAtoms().get(0), 1));
        this.addBond(new Bond(this.getAtoms().get(2), this.getAtoms().get(1), 2));
        this.addBond(new Bond(this.getAtoms().get(3), this.getAtoms().get(2), 1));
        this.addBond(new Bond(this.getAtoms().get(4), this.getAtoms().get(3), 2));
        this.addBond(new Bond(this.getAtoms().get(5), this.getAtoms().get(4), 1));
        this.addBond(new Bond(this.getAtoms().get(0), this.getAtoms().get(5), 2));

        // add the hydrogen atoms and bondsT
        for (float[] p : positions) {
            this.addAtom(new Hydrogen(Vector3f.add(Vector3f.normalize(new Vector3f(p), CCBond + CHBond), position)));
        }
        this.addBond(new Bond(this.getAtoms().get(0), this.getAtoms().get(6), 1));
        this.addBond(new Bond(this.getAtoms().get(1), this.getAtoms().get(7), 1));
        this.addBond(new Bond(this.getAtoms().get(2), this.getAtoms().get(8), 1));
        this.addBond(new Bond(this.getAtoms().get(3), this.getAtoms().get(9), 1));
        this.addBond(new Bond(this.getAtoms().get(4), this.getAtoms().get(10), 1));
        this.addBond(new Bond(this.getAtoms().get(5), this.getAtoms().get(11), 1));

    }
}