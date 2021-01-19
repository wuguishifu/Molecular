package com.bramerlabs.molecular.molecule.functional_groups;

import com.bramerlabs.engine.math.Vector3f;
import com.bramerlabs.molecular.molecule.Molecule;
import com.bramerlabs.molecular.molecule.atom.Atom;
import com.bramerlabs.molecular.molecule.atom.data_compilers.AtomicDataCompiler;
import com.bramerlabs.molecular.molecule.atom.data_compilers.BondDataCompiler;
import com.bramerlabs.molecular.molecule.atom.organics_atoms.Hydrogen;
import com.bramerlabs.molecular.molecule.atom.organics_atoms.carbon.Carbon;
import com.bramerlabs.molecular.molecule.bond.Bond;
import com.bramerlabs.molecular.molecule.vsepr.Tetrahedral;

import java.util.ArrayList;

public class Methyl extends Molecule {

    private Tetrahedral t;

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
        Atom methylCarbon = new Carbon(carbonPosition);

        // extend the connected direction vector through the atom to find the plane opposite to the connection bond
        float CHBondLength = BondDataCompiler.getCovalentBondLength(Atom.HYDROGEN, Atom.CARBON, 1) + AtomicDataCompiler.getVDWRadius(Atom.CARBON) + AtomicDataCompiler.getVDWRadius(Atom.HYDROGEN);
        float bondAngle = (float) Math.toRadians(109.5f); // the bond angle in methane
        float extensionLength = (float) Math.cos(bondAngle/2) * CHBondLength;
        float axisLength = (float) Math.sin(bondAngle/2) * CHBondLength;
        Vector3f extendedVector = Vector3f.add(Vector3f.normalize(connectedDirection, extensionLength), carbonPosition); // the vector that extends to the plane behind the methyl
        Vector3f n1 = Vector3f.cross(extendedVector, new Vector3f(0, 0, 1));
        if (n1.equals(new Vector3f(0), 0.0001f)) {
            n1 = Vector3f.cross(extendedVector, new Vector3f(1, 0, 1));
        }
        n1 = Vector3f.normalize(n1, axisLength);
        Vector3f b1 = Vector3f.add(n1, extendedVector);
        // the first hydrogen atom
        Atom H1 = new Hydrogen(b1);

        // add the atoms and bonds
        this.addAtom(methylCarbon);
        this.addAtom(H1);
        this.addBond(new Bond(methylCarbon, H1));
    }
}