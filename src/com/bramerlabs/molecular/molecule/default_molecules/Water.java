package com.bramerlabs.molecular.molecule.default_molecules;

import com.bramerlabs.engine.math.Vector3f;
import com.bramerlabs.molecular.molecule.Molecule;
import com.bramerlabs.molecular.molecule.atom.Atom;
import com.bramerlabs.molecular.data_compilers.AtomicDataCompiler;
import com.bramerlabs.molecular.data_compilers.BondDataCompiler;
import com.bramerlabs.molecular.molecule.atom.organics_atoms.Hydrogen;
import com.bramerlabs.molecular.molecule.atom.organics_atoms.Oxygen;
import com.bramerlabs.molecular.molecule.bond.Bond;

import java.util.ArrayList;

public class Water extends Molecule {

    // the value sqrt(2)/3
    private static final float R2O3 = 0.866025f;

    // the vectors representing the positions of hydrogen atoms
    private static final Vector3f[] HYDROGEN_POSITIONS = new Vector3f[]{
            new Vector3f(-R2O3, -0.5f, 0),
            new Vector3f( R2O3, -0.5f, 0)
    };

    // the bond length
    private static float OHBond = BondDataCompiler.getCovalentBondLength(Atom.OXYGEN, Atom.HYDROGEN, 1);

    // oxygen and hydrogen Van der Waals radii
    private static float oRadius = AtomicDataCompiler.getVDWRadius(Atom.OXYGEN);
    private static float hRadius = AtomicDataCompiler.getVDWRadius(Atom.HYDROGEN);

    /**
     * constructs a water atom centralized at a certain position
     * @param position - the position of the oxygen atom
     */
    public Water(Vector3f position) {
        super(position, new ArrayList<>(), new ArrayList<>());

        // the central oxygen atom
        this.addAtom(new Oxygen(position));

        // add the hydrogen atoms
        for (Vector3f hPos : HYDROGEN_POSITIONS) {
            hPos = Vector3f.normalize(hPos, OHBond + oRadius + hRadius);
            this.addAtom(new Hydrogen(Vector3f.add(position, hPos)));
        }

        // add the O-H bonds
        for (int i = 1; i <= 2; i++) {
            this.addBond(new Bond(this.getAtoms().get(0), this.getAtoms().get(i)));
        }
    }

}
