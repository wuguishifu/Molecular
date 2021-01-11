package com.bramerlabs.molecular.molecule.default_molecules;

import com.bramerlabs.engine.math.Vector3f;
import com.bramerlabs.molecular.molecule.Molecule;
import com.bramerlabs.molecular.molecule.atom.Atom;
import com.bramerlabs.molecular.molecule.atom.data_compilers.AtomicDataCompiler;
import com.bramerlabs.molecular.molecule.atom.data_compilers.BondDataCompiler;
import com.bramerlabs.molecular.molecule.bond.Bond;

import java.util.ArrayList;

public class SulfurHexafluoride extends Molecule {

    // the vectors representing the positions of the fluorine atoms
    private static final Vector3f[] FLUORINE_POSITIONS = new Vector3f[]{
            new Vector3f(-1, 0, 0),
            new Vector3f( 1, 0, 0),
            new Vector3f(0, -1, 0),
            new Vector3f(0,  1, 0),
            new Vector3f(0, 0, -1),
            new Vector3f(0, 0,  1)
    };

    // the bond length
    private static float SFBond = BondDataCompiler.getCovalentBondLength(Atom.SULFUR, Atom.FLUORINE, 1);

    // sulfur and fluorine Van der Waals radii
    private static float sRadius = AtomicDataCompiler.getVDWRadius(Atom.SULFUR);
    private static float fRadius = AtomicDataCompiler.getVDWRadius(Atom.FLUORINE);

    public SulfurHexafluoride(Vector3f position) {
        super(position, new ArrayList<>(), new ArrayList<>());

        // add the central sulfur atom
        this.addAtom(new Atom(position, Atom.SULFUR));

        // add the fluorine atoms
        for (Vector3f fPos : FLUORINE_POSITIONS) {
            fPos = Vector3f.normalize(fPos, SFBond + sRadius + fRadius);
            this.addAtom(new Atom(Vector3f.add(position, fPos), Atom.FLUORINE));
        }

        // add the S-F bonds
        for (int i = 1; i <= 6; i++) {
            this.addBond(new Bond(this.getAtoms().get(0), this.getAtoms().get(i)));
        }

    }

}
