package com.bramerlabs.molecular.molecule.bond;

import com.bramerlabs.engine.math.Vector3f;
import com.bramerlabs.engine.objects.shapes.Cylinder;
import com.bramerlabs.molecular.molecule.atom.Atom;
import com.bramerlabs.molecular.molecule.atom.data_compilers.BondDataCompiler;

import java.util.ArrayList;

public class Bond {

    // the atoms that this bond connects
    private Atom a1, a2;

    // the cylinders used to render this bond
    private ArrayList<Cylinder> cylinders = new ArrayList<>();

    // the bond order of this bond - default 1 (single bond)
    private int bondOrder = 1; // 2: double bond, 3: triple bond, etc

    // the radius of this bond - default 0.1f
    private float radius = 0.2f;

    // the color of this bond - default gray
    private Vector3f color = new Vector3f(0.3f);

    /**
     * constructor for bond between two atoms
     * @param a1 - atom 1
     * @param a2 - atom 2
     */
    public Bond(Atom a1, Atom a2) {
        this.a1 = a1;
        this.a2 = a2;
        makeCylinders();
    }

    /**
     * constructor for bond between two atoms
     * @param a1 - atom 1
     * @param a2 - atom 2
     * @param bondOrder - the bond order of this bond (1: single, 2: double, 3: triple, etc)
     */
    public Bond(Atom a1, Atom a2, int bondOrder) {
        this.a1 = a1;
        this.a2 = a2;
        this.bondOrder = bondOrder;
        makeCylinders();
    }

    /**
     * constructor for bond between two atoms
     * @param a1 - atom 1
     * @param a2 - atom 2
     * @param bondOrder - the bond order of this bond
     * @param radius - the radius of this bond
     */
    public Bond(Atom a1, Atom a2, int bondOrder, float radius) {
        this.a1 = a1;
        this.a2 = a2;
        this.bondOrder = bondOrder;
        this.radius = radius;
        makeCylinders();
    }

    /**
     * constructor for bond between two atoms
     * @param a1 - atom 1
     * @param a2 - atom 2
     * @param bondOrder - the bond order of this bond
     * @param radius - the radius of this bond
     * @param color - the color of this bond
     */
    public Bond(Atom a1, Atom a2, int bondOrder, float radius, Vector3f color) {
        this.a1 = a1;
        this.a2 = a2;
        this.bondOrder = bondOrder;
        this.radius = radius;
        this.color = color;
        makeCylinders();
    }

    /**
     * getter method
     * @return - the atoms that this bond connects
     */
    public ArrayList<Atom> getAtoms() {
        ArrayList<Atom> atoms = new ArrayList<>();
        atoms.add(a1);
        atoms.add(a2);
        return atoms;
    }

    /**
     * helper method - makes the cylinders used for rendering this bond
     */
    private void makeCylinders() {

        // make sure the bond order is less than 3 (for now)
        if (bondOrder > 3) {
            bondOrder = 1;
        }

        // if the bond order isn't 1, find a vector normal to the bond to create the parallel cylinders
        Vector3f normal = new Vector3f(0);
        if (bondOrder != 1) {
            // find the direction of the bond
            Vector3f bondDirection = Vector3f.subtract(a1.getPosition(), a2.getPosition());

            // generate a vector normal to the bond direction
            Vector3f v0 = new Vector3f(0, 1, 0);
            if (Vector3f.cross(bondDirection, v0).equals(new Vector3f(0), 0.00001f)) {
                v0 = new Vector3f(0, 0, 1);
            }
            normal = Vector3f.normalize(Vector3f.cross(bondDirection, v0), 0.8f);
        }

        // create the central bond if its a single or triple bond
        if (bondOrder == 1 || bondOrder == 3) {
            cylinders.add(Cylinder.makeCylinder(a1.getPosition(), a2.getPosition(), color, radius));
        }

        // create the outside 2 bonds if its a triple bond
        if (bondOrder == 3) {
            cylinders.add(Cylinder.makeCylinder(Vector3f.add(a1.getPosition(), normal), Vector3f.add(a2.getPosition(), normal), color, radius));
            cylinders.add(Cylinder.makeCylinder(Vector3f.subtract(a1.getPosition(), normal), Vector3f.subtract(a2.getPosition(), normal), color, radius));
        }

        // create the double bonds
        if (bondOrder == 2) {
            normal.scale(0.5f); // divide the normal vector in half
            cylinders.add(Cylinder.makeCylinder(Vector3f.add(a1.getPosition(), normal), Vector3f.add(a2.getPosition(), normal), color, radius));
            cylinders.add(Cylinder.makeCylinder(Vector3f.subtract(a1.getPosition(), normal), Vector3f.subtract(a2.getPosition(), normal), color, radius));
        }

        // create the meshes
        for (Cylinder cylinder : cylinders) {
            cylinder.createMesh();
        }
    }

    /**
     * getter method
     * @return - the cylinders that are used to render this bond
     */
    public ArrayList<Cylinder> getCylinders() {
        return this.cylinders;
    }

    /**
     * destroys the bond
     */
    public void destroy() {
        for (Cylinder c : cylinders) {
            c.destroy();
        }
    }

    /**
     * common bond lengths
     */
    // C-H bonds
    public final static float CH_BOND = BondDataCompiler.getCovalentBondLength(Atom.CARBON, Atom.HYDROGEN, 1);
    // C-C bonds
    public final static float CC_SINGLE_BOND = BondDataCompiler.getCovalentBondLength(Atom.CARBON, Atom.CARBON, 1);
    public final static float CC_DOUBLE_BOND = BondDataCompiler.getCovalentBondLength(Atom.CARBON, Atom.CARBON, 2);
    public final static float CC_TRIPLE_BOND = BondDataCompiler.getCovalentBondLength(Atom.CARBON, Atom.CARBON, 3);
    // C-O bonds
    public final static float CO_SINGLE_BOND = BondDataCompiler.getCovalentBondLength(Atom.CARBON, Atom.OXYGEN, 1);
    public final static float CO_DOUBLE_BOND = BondDataCompiler.getCovalentBondLength(Atom.CARBON, Atom.OXYGEN, 2);
    // O-H bonds
    public final static float OH_BOND = BondDataCompiler.getCovalentBondLength(Atom.OXYGEN, Atom.HYDROGEN, 1);

}
