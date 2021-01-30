package com.bramerlabs.molecular.molecule.default_molecules;

import com.bramerlabs.engine.math.Vector3f;
import com.bramerlabs.molecular.molecule.Molecule;
import com.bramerlabs.molecular.molecule.atom.Atom;
import com.bramerlabs.molecular.data_compilers.AtomicDataCompiler;
import com.bramerlabs.molecular.data_compilers.BondDataCompiler;
import com.bramerlabs.molecular.molecule.bond.Bond;

import java.util.ArrayList;

public class BoronTrifluoride extends Molecule {

    // the value sqrt(2)/3
    private static final float R2O3 = 0.866025f;

    // the vectors representing the positions of the fluoride atoms
    private static final Vector3f[] FLUORINE_POSITIONS = new Vector3f[]{
            new Vector3f(0, 0, -1),
            new Vector3f(-R2O3, 0, 0.5f),
            new Vector3f(R2O3, 0, 0.5f)
    };

    // the bond length
    private static float BFBond = BondDataCompiler.getCovalentBondLength(Atom.BORON, Atom.FLUORINE, 1);

    // boron and fluorine Van der Waals radii
    private static float bRadius = AtomicDataCompiler.getVDWRadius(Atom.BORON);
    private static float fRadius = AtomicDataCompiler.getVDWRadius(Atom.FLUORINE);

    /**
     * constructs a boron trifluoride atom centralized at a certain position
     * @param position - the position of the central boron atom
     */
    public BoronTrifluoride(Vector3f position) {
        super(position, new ArrayList<>(), new ArrayList<>());

        // add the central boron atom
        this.addAtom(new Atom(position, Atom.BORON));

        // add the fluorine atoms
        for (Vector3f fPos : FLUORINE_POSITIONS) {
            fPos = Vector3f.normalize(fPos, bRadius + fRadius + BFBond);
            this.addAtom(new Atom(Vector3f.add(position, fPos), Atom.FLUORINE));
        }

        // add the B-F bonds
        for (int i = 1; i <= 3; i++) {
            this.addBond(new Bond(this.getAtoms().get(0), this.getAtoms().get(i)));
        }

    }

}
