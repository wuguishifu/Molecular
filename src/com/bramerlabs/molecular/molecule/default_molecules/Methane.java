package com.bramerlabs.molecular.molecule.default_molecules;

import com.bramerlabs.engine.math.Vector3f;
import com.bramerlabs.molecular.molecule.Molecule;
import com.bramerlabs.molecular.molecule.atom.Atom;
import com.bramerlabs.molecular.molecule.atom.data_compilers.AtomicDataCompiler;
import com.bramerlabs.molecular.molecule.atom.data_compilers.BondDataCompiler;
import com.bramerlabs.molecular.molecule.atom.organics_atoms.carbon.Carbon;
import com.bramerlabs.molecular.molecule.atom.organics_atoms.Hydrogen;
import com.bramerlabs.molecular.molecule.bond.Bond;

import java.util.ArrayList;

public class Methane extends Molecule {

    // the value sqrt(2)/3
    private static final float R2O3 = 0.866025f;

    // the vectors representing the positions of the hydrogen atoms
    private static final Vector3f[] HYDROGEN_POSITIONS = new Vector3f[]{
            new Vector3f(-R2O3, -0.5f, 0),
            new Vector3f(R2O3, -0.5f, 0),
            new Vector3f(0, 0.5f, -R2O3),
            new Vector3f(0, 0.5f, R2O3)
    };

    // the bond length
    private static float CHBond = BondDataCompiler.getCovalentBondLength(Atom.CARBON, Atom.HYDROGEN, 1);

    // carbon and hydrogen Van der Waals radii
    private static float cRadius = AtomicDataCompiler.getVDWRadius(Atom.CARBON);
    private static float hRadius = AtomicDataCompiler.getVDWRadius(Atom.HYDROGEN);

    /**
     * constructs a methane atom centralized at a certain position
     * @param position - the position of the carbon atom
     */
    public Methane(Vector3f position) {
        super(position, new ArrayList<>(), new ArrayList<>());

        // add the carbon atom
        this.addAtom(new Carbon(position));

        // add the hydrogen atoms
        for (Vector3f hPos : HYDROGEN_POSITIONS) {
            hPos = Vector3f.normalize(hPos, CHBond + cRadius + hRadius);
            this.addAtom(new Hydrogen(Vector3f.add(position, hPos)));
        }

        // add the C-H bonds
        for (int i = 1; i <= 4; i++) {
            this.addBond(new Bond(this.getAtoms().get(0), this.getAtoms().get(i)));
        }

    }

    /**
     * updates the molecule - vibrational mode a1 of methane
     */
    @Override
    public void update(int time) {

        float distance = (float) (CHBond + cRadius + hRadius + 0.5 * Math.sin((time/5f)));
        Atom centralAtom = getAtoms().get(0);
        for (int i = 1; i <= 4; i++) {
            Atom a = getAtoms().get(i);
            Vector3f dir = Vector3f.subtract(a.getPosition(), centralAtom.getPosition());
            dir = Vector3f.normalize(dir, distance);
            a.moveTo(Vector3f.add(centralAtom.getPosition(), dir));
        }
    }
}