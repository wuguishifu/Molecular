package com.bramerlabs.molecular.molecule.default_molecules.default_organic_molecules;

import com.bramerlabs.engine.math.Vector3f;
import com.bramerlabs.molecular.molecule.Molecule;
import com.bramerlabs.molecular.molecule.atom.Atom;
import com.bramerlabs.molecular.molecule.atom.data_compilers.BondDataCompiler;
import com.bramerlabs.molecular.molecule.atom.organics_atoms.Carbon;
import com.bramerlabs.molecular.molecule.atom.organics_atoms.Oxygen;
import com.bramerlabs.molecular.molecule.bond.Bond;

import java.util.ArrayList;

public class CarbonDioxide extends Molecule {

    // the O-C bond length
    public static float BOND_LENGTH = BondDataCompiler.getCovalentBondLength(Atom.CARBON, Atom.OXYGEN, 2);

    /**
     * constructor for specified atoms and bonds
     *
     * @param position - the position of the central atom
     * @param atoms    - the atoms making up this molecule
     * @param bonds    - the bonds making up this molecule
     */
    public CarbonDioxide(Vector3f position, ArrayList<Atom> atoms, ArrayList<Bond> bonds) {
        super(position, atoms, bonds);
    }

    /**
     * creates an instance of a carbon dioxide molecule
     * @param position - the position of the central atom
     * @param axis - the axis that the molecule is on
     * @return - a new instance of a carbon dioxide molecule
     */
    public static CarbonDioxide getInstance(Vector3f position, Vector3f axis) {

        // the distance from atom to atom
        float atomDistance = BOND_LENGTH + Carbon.ATOMIC_RADIUS + Oxygen.ATOMIC_RADIUS;

        // the bond vector to make the bonds from
        Vector3f bondVector = Vector3f.normalize(axis, atomDistance);

        // the central carbon atom
        Carbon c1 = new Carbon(position);

        // the two oxygen atoms
        Oxygen o1 = new Oxygen(Vector3f.add(position, bondVector));
        Oxygen o2 = new Oxygen(Vector3f.add(position, Vector3f.scale(bondVector, -1)));

        // array list of atoms
        ArrayList<Atom> atoms = new ArrayList<>();
        atoms.add(c1);
        atoms.add(o1);
        atoms.add(o2);

        // array list of bonds
        ArrayList<Bond> bonds = new ArrayList<>();
        bonds.add(new Bond(c1, o1, 2));
        bonds.add(new Bond(c1, o2, 2));

        // returns a newly constructed carbon dioxide molecule
        return new CarbonDioxide(position, atoms, bonds);

    }

    /**
     * creates an instance of a carbon dioxide molecule using a default z axis
     * @param position - the position of the central atom
     * @return - a new instance of a carbon dioxide molecule
     */
    public static CarbonDioxide getInstance(Vector3f position) {
        return getInstance(position, new Vector3f(0, 0, 1));
    }
}