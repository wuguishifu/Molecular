package com.bramerlabs.molecular.molecule.default_molecules.default_organic_molecules;

import com.bramerlabs.engine.math.Matrix4f;
import com.bramerlabs.engine.math.Vector3f;
import com.bramerlabs.engine.math.Vector4f;
import com.bramerlabs.molecular.molecule.Molecule;
import com.bramerlabs.molecular.molecule.atom.Atom;
import com.bramerlabs.molecular.molecule.atom.data_compilers.BondDataCompiler;
import com.bramerlabs.molecular.molecule.atom.organics_atoms.Hydrogen;
import com.bramerlabs.molecular.molecule.atom.organics_atoms.Oxygen;
import com.bramerlabs.molecular.molecule.bond.Bond;

import java.util.ArrayList;
import java.util.Arrays;

public class Water extends Molecule {

    // the H-O bond length
    public static float BOND_LENGTH = BondDataCompiler.getCovalentBondLength(Atom.HYDROGEN, Atom.OXYGEN, 1);

    /**
     * constructor for specified atoms and bonds
     *
     * @param position - the position of the central atom
     * @param atoms    - the atoms making up this molecule
     * @param bonds    - the bonds making up this molecule
     */
    public Water(Vector3f position, ArrayList<Atom> atoms, ArrayList<Bond> bonds) {
        super(position, atoms, bonds);
    }

    /**
     * creates an instance of a water molecule
     * @param position - the position of the central atom
     * @param axis - the axis that the molecule is on
     * @return - a new instance of a water molecule
     */
    public static Water getInstance(Vector3f position, Vector3f axis) {

        // the distance from atom to atom
        float atomDistance = BOND_LENGTH + Oxygen.ATOMIC_RADIUS + Hydrogen.ATOMIC_RADIUS;

        // the central oxygen atom
        Oxygen o1 = new Oxygen(position);

        // the 2 hydrogen atoms
        // create a normal vector between the axis and the x-z plane
        Vector3f v1 = new Vector3f(1, 0, 0);
        if (Vector3f.cross(v1, axis).equals(new Vector3f(0), 0.00001f)) {
            v1 = new Vector3f(0, 0, 1);
        }
        Vector3f b2 = Vector3f.cross(v1, axis);

        // normalize the vectors to the distance between atoms
        Vector3f b1 = Vector3f.normalize(axis);
        v1 = Vector3f.normalize(v1);
        b2 = Vector3f.normalize(b2);

        // the angle of rotation normal to the x axis
        float alpha = (float) Math.toDegrees(Math.acos(Vector3f.angleBetween(b1, new Vector3f(0, 0, 1))));

        // the angle of rotation normal to the z axis
        float beta = (float) Math.toDegrees(Math.acos(Vector3f.angleBetween(v1, new Vector3f(1, 0, 0))));

        // the angle of rotation normal to the y axis
        float gamma = (float) Math.toDegrees(Math.acos(Vector3f.angleBetween(b2, new Vector3f(0, 1, 0))));

        // create a transform matrix
        Matrix4f transform = Matrix4f.transform(position, new Vector3f(alpha, beta, gamma), new Vector3f(1));

        // the bond vectors of the water atom
        b1 = new Vector3f((float) (-Math.sqrt(3)/2), -0.5f, 0);
        b2 = new Vector3f((float) (Math.sqrt(3)/2), -0.5f, 0);

        // transform the hydrogen directional bond vectors
        b1 = Matrix4f.multiply(transform, new Vector4f(b1, 1)).toVector3f();
        b2 = Matrix4f.multiply(transform, new Vector4f(b2, 1)).toVector3f();

        // normalize the bond lengths
        b1 = Vector3f.normalize(b1, atomDistance);
        b2 = Vector3f.normalize(b2, atomDistance);

        // creating the 2 hydrogen atoms
        Hydrogen h1 = new Hydrogen(Vector3f.add(position, b1));
        Hydrogen h2 = new Hydrogen(Vector3f.add(position, b2));

        // add the atoms to an array list
        ArrayList<Atom> atoms = new ArrayList<>(Arrays.asList(o1, h1, h2));

        // create the 2 bonds
        Bond bond1 = new Bond(o1, h1);
        Bond bond2 = new Bond(o1, h2);

        // add the bonds to an array list
        ArrayList<Bond> bonds = new ArrayList<>(Arrays.asList(bond1, bond2));

        return new Water(position, atoms, bonds);

    }

    /**
     * creates an instance of a water using a default z axis
     * @param position - the position of the central atom
     * @return - a new instance of a water
     */
    public static Water getInstance(Vector3f position) {
        return getInstance(position, new Vector3f(0, 0, 1));
    }
}
