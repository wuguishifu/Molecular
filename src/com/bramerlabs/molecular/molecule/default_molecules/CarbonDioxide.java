package com.bramerlabs.molecular.molecule.default_molecules;

import com.bramerlabs.engine.math.Vector3f;
import com.bramerlabs.molecular.molecule.Molecule;
import com.bramerlabs.molecular.molecule.atom.Atom;
import com.bramerlabs.molecular.molecule.atom.data_compilers.AtomicDataCompiler;
import com.bramerlabs.molecular.molecule.atom.data_compilers.BondDataCompiler;
import com.bramerlabs.molecular.molecule.atom.organics_atoms.carbon.Carbon;
import com.bramerlabs.molecular.molecule.atom.organics_atoms.Oxygen;
import com.bramerlabs.molecular.molecule.bond.Bond;

import java.util.ArrayList;

public class CarbonDioxide extends Molecule {

    // the vectors representing the positions of the hydrogen atoms
    private static final Vector3f[] OXYGEN_POSITIONS = new Vector3f[]{
            new Vector3f(-1, 0, 0),
            new Vector3f(1, 0, 0)
    };

    // the bond length
    private static float COBond = BondDataCompiler.getCovalentBondLength(Atom.CARBON, Atom.OXYGEN, 1);

    // carbon and oxygen Van der Waals radii
    private static float cRadius = AtomicDataCompiler.getVDWRadius(Atom.CARBON);
    private static float oRadius = AtomicDataCompiler.getVDWRadius(Atom.OXYGEN);

    /**
     * creates a carbon dioxide molecule centered around a position
     * @param position - the position of the carbon atom
     */
    public CarbonDioxide(Vector3f position) {
        super(position, new ArrayList<>(), new ArrayList<>());

        // add the carbon atom
        this.addAtom(new Carbon(position));

        // add the oxygen atoms
        for (Vector3f oPos : OXYGEN_POSITIONS) {
            oPos = Vector3f.normalize(oPos, COBond + cRadius + oRadius);
            this.addAtom(new Oxygen(Vector3f.add(position, oPos)));
        }

        // add the C=O bonds
        for (int i = 1; i <= 2; i++) {
            this.addBond(new Bond(this.getAtoms().get(0), this.getAtoms().get(i), 2));
        }

    }

}
