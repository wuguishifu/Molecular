package com.bramerlabs.molecular.molecule.default_molecules.default_ring_molecules;

import com.bramerlabs.engine.math.Vector3f;
import com.bramerlabs.molecular.molecule.atom.Atom;
import com.bramerlabs.molecular.molecule.atom.DefaultAtom;
import com.bramerlabs.molecular.molecule.bond.Bond;

public class Benzaldehyde extends Benzene {
    /**
     * constructor for specified values
     *
     * @param position   - the position of the central molecule
     * @param bondLength - the length of the bonds in this molecule
     */
    public Benzaldehyde(Vector3f position, float bondLength) {
        super(position, bondLength);

        // add the constituent
        Vector3f sub = this.getAtoms().get(3).getPosition();

        Atom c1 = new DefaultAtom(Vector3f.add(Vector3f.normalize(new Vector3f(0, 0, -1), bondLength), sub), 2/3f, new Vector3f(0.4f));
        Atom o1 = new DefaultAtom(Vector3f.add(Vector3f.normalize(new Vector3f(-0.866025f, 0, -0.5f), bondLength), c1.getPosition()), 0.5f, new Vector3f(0.7f, 0, 0));
        Atom h1 = new DefaultAtom(Vector3f.add(Vector3f.normalize(new Vector3f(0.866025f, 0, -0.5f), bondLength), c1.getPosition()), 0.5f, new Vector3f(0.8f));

        this.addAtom(c1);
        this.addAtom(o1);
        this.addAtom(h1);

        this.addBond(new Bond(c1, o1, 2));
        this.addBond(new Bond(c1, h1, 1));

    }
}