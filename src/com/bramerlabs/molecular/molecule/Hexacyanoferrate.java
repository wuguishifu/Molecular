package com.bramerlabs.molecular.molecule;

import com.bramerlabs.engine.math.Vector3f;
import com.bramerlabs.molecular.data_compilers.AtomicDataCompiler;
import com.bramerlabs.molecular.data_compilers.BondDataCompiler;
import com.bramerlabs.molecular.molecule.atom.Atom;
import com.bramerlabs.molecular.molecule.atom.organics_atoms.Nitrogen;
import com.bramerlabs.molecular.molecule.atom.organics_atoms.carbon.Carbon;
import com.bramerlabs.molecular.molecule.bond.Bond;

import java.util.ArrayList;

public class Hexacyanoferrate extends Molecule {

    // the Fe-C bond length
    private static float FeCBond = BondDataCompiler.getCovalentBondLength(Atom.IRON, Atom.CARBON, 1) + Carbon.ATOMIC_RADIUS + AtomicDataCompiler.getCovalentRadius(Atom.IRON, 1);

    // the C-N bond length
    private static float CN = BondDataCompiler.getCovalentBondLength(Atom.CARBON, Atom.NITROGEN, 3) + Carbon.ATOMIC_RADIUS + Nitrogen.ATOMIC_RADIUS;

    // the positions of the direction
    private final static float[][] positions = new float[][]{{1, 0, 0}, {0, 1, 0}, {0, 0, 1}, {-1, 0, 0}, {0, -1, 0}, {0, 0, -1}};

    /**
     * constructor for specified atoms and bonds
     *
     * @param position - the position of the central atom
     */
    public Hexacyanoferrate(Vector3f position) {
        super(position, new ArrayList<>(), new ArrayList<>());

        // add iron
        this.addAtom(new Atom(position, Atom.IRON, 0, 0));

        for (float[] p : positions) {
            this.addAtom(new Carbon(Vector3f.add(Vector3f.normalize(new Vector3f(p), CN), position)));
        }
        this.addBond(new Bond(this.getAtoms().get(1), this.getAtoms().get(0), 1));
        this.addBond(new Bond(this.getAtoms().get(2), this.getAtoms().get(0), 1));
        this.addBond(new Bond(this.getAtoms().get(3), this.getAtoms().get(0), 1));
        this.addBond(new Bond(this.getAtoms().get(4), this.getAtoms().get(0), 1));
        this.addBond(new Bond(this.getAtoms().get(5), this.getAtoms().get(0), 1));
        this.addBond(new Bond(this.getAtoms().get(6), this.getAtoms().get(0), 1));

        for (float[] p : positions) {
            this.addAtom(new Nitrogen(Vector3f.add(Vector3f.normalize(new Vector3f(p), CN + FeCBond), position)));
        }
        this.addBond(new Bond(this.getAtoms().get(1), this.getAtoms().get(7), 3));
        this.addBond(new Bond(this.getAtoms().get(2), this.getAtoms().get(8), 3));
        this.addBond(new Bond(this.getAtoms().get(3), this.getAtoms().get(9), 3));
        this.addBond(new Bond(this.getAtoms().get(4), this.getAtoms().get(10), 3));
        this.addBond(new Bond(this.getAtoms().get(5), this.getAtoms().get(11), 3));
        this.addBond(new Bond(this.getAtoms().get(6), this.getAtoms().get(12), 3));

    }
}
