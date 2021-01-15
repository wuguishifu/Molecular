package com.bramerlabs.molecular.molecule.functional_groups;

import com.bramerlabs.engine.math.Vector3f;
import com.bramerlabs.molecular.molecule.Molecule;
import com.bramerlabs.molecular.molecule.atom.Atom;
import com.bramerlabs.molecular.molecule.atom.data_compilers.AtomicDataCompiler;
import com.bramerlabs.molecular.molecule.atom.data_compilers.BondDataCompiler;
import com.bramerlabs.molecular.molecule.atom.organics_atoms.carbon.Carbon;

import java.util.ArrayList;

public class Methyl extends Molecule {


    /**
     * constructor for specified atoms and bonds
     * @param connectedTo - the atom that this functional group is connected to
     * @param connectedDirection - the length of the bond this functional group is connected to
     */
    public Methyl(Atom connectedTo, Vector3f connectedDirection) {
        super(new Vector3f(0), new ArrayList<>(), new ArrayList<>());

        // the bond length between the methyl group
        float bondLength = BondDataCompiler.getCovalentBondLength(Atom.CARBON, connectedTo.getAtomicNumber(), 1) + AtomicDataCompiler.getVDWRadius(Atom.CARBON) + AtomicDataCompiler.getVDWRadius(connectedTo.getAtomicNumber());
        Vector3f carbonPosition = Vector3f.add(Vector3f.normalize(connectedDirection, bondLength), connectedTo.getPosition());

        // add the methyl carbon
        this.addAtom(new Carbon(carbonPosition));

        float CHBondLength = BondDataCompiler.getCovalentBondLength(Atom.HYDROGEN, Atom.CARBON, 1) + AtomicDataCompiler.getVDWRadius(Atom.CARBON) + AtomicDataCompiler.getVDWRadius(Atom.HYDROGEN);


    }
}
