package com.bramerlabs.molecular.molecule.default_molecules;

import com.bramerlabs.engine.math.Vector3f;
import com.bramerlabs.molecular.molecule.Molecule;
import com.bramerlabs.molecular.molecule.atom.Atom;
import com.bramerlabs.molecular.data_compilers.AtomicDataCompiler;
import com.bramerlabs.molecular.data_compilers.BondDataCompiler;

import java.util.ArrayList;

public class AceticAcid extends Molecule {

    // the value sqrt(2)/3
    private static final float R2O3 = 0.866025f;

    // the C-C bond length
    private static float CCBond = BondDataCompiler.getCovalentBondLength(Atom.CARBON, Atom.CARBON, 1);

    // the C-H bond length
    private static float CHBond = BondDataCompiler.getCovalentBondLength(Atom.CARBON, Atom.HYDROGEN, 1);

    // the C-O single bond length
    private static float COSingleBond = BondDataCompiler.getCovalentBondLength(Atom.CARBON, Atom.OXYGEN, 1);
    private static float CODoubleBond = BondDataCompiler.getCovalentBondLength(Atom.CARBON, Atom.OXYGEN, 2);

    // the O-H bond length
    private static float OHBond = BondDataCompiler.getCovalentBondLength(Atom.OXYGEN, Atom.HYDROGEN, 1);

    // carbon and hydrogen and oxygen Van der Waals radii
    private static float cRadius = AtomicDataCompiler.getVDWRadius(Atom.CARBON);
    private static float hRadius = AtomicDataCompiler.getVDWRadius(Atom.HYDROGEN);
    private static float oRadius = AtomicDataCompiler.getVDWRadius(Atom.OXYGEN);

    /**
     * constructor for specified atoms and bonds
     *
     * @param position - the position of the central atom
     */
    public AceticAcid(Vector3f position) {
        super(position, new ArrayList<>(), new ArrayList<>());

        // the carbon backbone

    }
}
