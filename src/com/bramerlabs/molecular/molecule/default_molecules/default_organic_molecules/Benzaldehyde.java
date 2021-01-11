package com.bramerlabs.molecular.molecule.default_molecules.default_organic_molecules;

import com.bramerlabs.engine.math.Vector3f;
import com.bramerlabs.molecular.molecule.atom.Atom;
import com.bramerlabs.molecular.molecule.atom.data_compilers.BondDataCompiler;
import com.bramerlabs.molecular.molecule.atom.organics_atoms.Carbon;
import com.bramerlabs.molecular.molecule.atom.organics_atoms.Hydrogen;
import com.bramerlabs.molecular.molecule.atom.organics_atoms.Oxygen;
import com.bramerlabs.molecular.molecule.bond.Bond;

public class Benzaldehyde extends Benzene {
    /**
     * constructor for specified values
     *
     * @param position   - the position of the central molecule
     */
    public Benzaldehyde(Vector3f position) {
        super(position);

        // add the constituent
        Vector3f sub = this.getAtoms().get(3).getPosition();
        this.getAtoms().remove(this.getAtoms().get(9));

        float COBond = Carbon.ATOMIC_RADIUS + Oxygen.ATOMIC_RADIUS + BondDataCompiler.getCovalentBondLength(Atom.CARBON, Atom.OXYGEN, 2);
        float CHBond = Carbon.ATOMIC_RADIUS + Hydrogen.ATOMIC_RADIUS + BondDataCompiler.getCovalentBondLength(Atom.CARBON, Atom.HYDROGEN, 1);
        float CCBond = Carbon.ATOMIC_RADIUS + Carbon.ATOMIC_RADIUS + BondDataCompiler.getCovalentBondLength(Atom.CARBON, Atom.CARBON, 1);

        Atom c1 = new Carbon(Vector3f.add(Vector3f.normalize(new Vector3f(0, 0, -1), CCBond), sub));
        Atom o1 = new Oxygen(Vector3f.add(Vector3f.normalize(new Vector3f(-0.866025f, 0, -0.5f), COBond), c1.getPosition()));
        Atom h1 = new Hydrogen(Vector3f.add(Vector3f.normalize(new Vector3f(0.866025f, 0, -0.5f), CHBond), c1.getPosition()));

        this.addAtom(c1);
        this.addAtom(o1);
        this.addAtom(h1);

        this.addBond(new Bond(c1, o1, 2));
        this.addBond(new Bond(c1, h1, 1));

    }
}