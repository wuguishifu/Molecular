package com.bramerlabs.molecular.molecule.default_molecules.default_organic_molecules;

import com.bramerlabs.engine.math.Matrix4f;
import com.bramerlabs.engine.math.Vector3f;
import com.bramerlabs.engine.math.Vector4f;
import com.bramerlabs.molecular.molecule.Molecule;
import com.bramerlabs.molecular.molecule.atom.Atom;
import com.bramerlabs.molecular.molecule.atom.organics_atoms.Carbon;
import com.bramerlabs.molecular.molecule.atom.organics_atoms.Hydrogen;
import com.bramerlabs.molecular.molecule.bond.Bond;

import java.util.ArrayList;
import java.util.Arrays;

public class Methane extends Molecule {

    // the C-H bond length
    public static float BOND_LENGTH = 1.09f;

    /**
     * constructor for specified atoms and bonds
     *
     * @param position - the position of the central atom
     * @param atoms    - the atoms making up this molecule
     * @param bonds    - the bonds making up this molecule
     */
    public Methane(Vector3f position, ArrayList<Atom> atoms, ArrayList<Bond> bonds) {
        super(position, atoms, bonds);
    }

    /**
     * creates an instance of a carbon dioxide molecule
     * @param position - the position of the central atom
     * @param axis - the axis that the molecule is on
     * @return - a new instance of a carbon dioxide molecule
     */
    public static Methane getInstance(Vector3f position, Vector3f axis) {

        // the distance from atom to atom
        float atomDistance = BOND_LENGTH + Carbon.ATOMIC_RADIUS + Hydrogen.ATOMIC_RADIUS;

        // the bond vector to make the bonds from
        Vector3f b1 = Vector3f.normalize(axis, atomDistance);

        // the central carbon atom
        Carbon c1 = new Carbon(position);

        // the 4 hydrogen atoms
        // create a normal vector between the axis and the x-z plane
        Vector3f v1 = new Vector3f(1, 0, 0);
        if (Vector3f.cross(v1, b1).equals(new Vector3f(0), 0.0001f)) {
            v1 = new Vector3f(1, 0, 1);
        }
        Vector3f b2 = Vector3f.cross(v1, b1);

        // the angle of rotation normal to the x axis
        float alpha = (float) Math.toDegrees(Math.acos(Vector3f.angleBetween(axis, new Vector3f(0, 0, 1))));

        // the angle of rotation normal to the z axis
        float beta = (float) Math.toDegrees(Math.acos(Vector3f.angleBetween(v1, new Vector3f(1, 0, 0))));

        // the angle of rotation normal to the y axis
        float gamma = (float) Math.toDegrees(Math.acos(Vector3f.angleBetween(b2, new Vector3f(0, 1, 0))));

        System.out.println(alpha + ", " + beta + ", " + gamma);

        // create a transform matrix
        Matrix4f transform = Matrix4f.transform(position, new Vector3f(alpha, beta, gamma), new Vector3f(1));

        // the bond vectors of the methane atom
        b1 = new Vector3f(1, 1, -1);
        b2 = new Vector3f(-1, 1, 1);
        Vector3f b3 = new Vector3f(-1, -1, -1);
        Vector3f b4 = new Vector3f(1, -1, 1);

        // transform the hydrogen directional bond vectors
        b1 = Matrix4f.multiply(transform, new Vector4f(b1, 1)).toVector3f();
        b2 = Matrix4f.multiply(transform, new Vector4f(b2, 1)).toVector3f();
        b3 = Matrix4f.multiply(transform, new Vector4f(b3, 1)).toVector3f();
        b4 = Matrix4f.multiply(transform, new Vector4f(b4, 1)).toVector3f();

        // normalize the bond lengths
        b1 = Vector3f.normalize(b1, atomDistance);
        b2 = Vector3f.normalize(b2, atomDistance);
        b3 = Vector3f.normalize(b3, atomDistance);
        b4 = Vector3f.normalize(b4, atomDistance);

        // creating the 4 hydrogen atoms
        Hydrogen h1 = new Hydrogen(Vector3f.add(position, b1));
        Hydrogen h2 = new Hydrogen(Vector3f.add(position, b2));
        Hydrogen h3 = new Hydrogen(Vector3f.add(position, b3));
        Hydrogen h4 = new Hydrogen(Vector3f.add(position, b4));

        // add the atoms to an array list
        ArrayList<Atom> atoms = new ArrayList<>(Arrays.asList(c1, h1, h2, h3, h4));

        // create the 4 bonds
        Bond bond1 = new Bond(c1, h1, 1);
        Bond bond2 = new Bond(c1, h2, 1);
        Bond bond3 = new Bond(c1, h3, 1);
        Bond bond4 = new Bond(c1, h4, 1);

        // add the bonds to an array list
        ArrayList<Bond> bonds = new ArrayList<>(Arrays.asList(bond1, bond2, bond3, bond4));

        return new Methane(position, atoms, bonds);

    }

    /**
     * creates an instance of a methane molecule using a default z axis
     * @param position - the position of the central atom
     * @return - a new instance of a methane molecule
     */
    public static Methane getInstance(Vector3f position) {
        return getInstance(position, new Vector3f(0, 0, 1));
    }
}
