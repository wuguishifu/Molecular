package com.bramerlabs.molecular.file_io;

import com.bramerlabs.engine.math.Vector3f;
import com.bramerlabs.molecular.molecule.Molecule;
import com.bramerlabs.molecular.molecule.atom.Atom;
import com.bramerlabs.molecular.molecule.bond.Bond;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class MoleculeIO {

    // the amount of molecule data points
    private static final int NUM_MOLECULE_DATA = 3;
    private static final int MOLECULE_X = 1;
    private static final int MOLECULE_Y = 2;
    private static final int MOLECULE_Z = 3;

    // the amount of atom data points
    private static final int NUM_ATOM_DATA = 7;

    // the atomic data indices
    private static final int ATOM_ID = 1;
    private static final int ATOM_ATOMIC_NUMBER = 2;
    private static final int ATOM_CHARGE = 3;
    private static final int ATOM_NUM_NEUTRONS = 4;
    private static final int ATOM_X = 5;
    private static final int ATOM_Y = 6;
    private static final int ATOM_Z = 7;

    // the amount of bond data points
    private static final int NUM_BOND_DATA = 3;

    // the bond data indices
    private static final int BOND_ID = 1;
    private static final int BOND_ATOM_1_ID = 2;
    private static final int BOND_ATOM_2_ID = 3;
    private static final int BOND_ORDER = 4;

    // the default location
    private static final String DOCUMENT_PATH = new JFileChooser().getFileSystemView().getDefaultDirectory().toString();
    private static final String MOLECULAR_APP = "MolecularApp";
    private static final String MOLECULE_FILES = "MoleculeFiles";
    private static final String SEP = "\\";

    public static void saveMolecule(String fileName, Molecule molecule) {

        // see if the directory exists
        if (!(new File(DOCUMENT_PATH + SEP + MOLECULAR_APP).exists())) {
            File molecularAppDir = new File(DOCUMENT_PATH + SEP + MOLECULAR_APP);
            boolean bool = molecularAppDir.mkdir();
        }
        if (!(new File(DOCUMENT_PATH + SEP + MOLECULAR_APP + SEP + MOLECULE_FILES)).exists()) {
            File moleculeFilesDir = new File(DOCUMENT_PATH + SEP + MOLECULAR_APP + SEP + MOLECULE_FILES);
            boolean bool = moleculeFilesDir.mkdir();
        }

        // make the location
        String location = new JFileChooser().getFileSystemView().getDefaultDirectory().toString();
        File newFile = new File(location + "\\MolecularApp\\MoleculeFiles\\" + fileName);
        try {
            boolean bool = newFile.createNewFile();
            FileWriter writer = new FileWriter(newFile);

            // get the position of the molecule
            float moleculeX = molecule.getPosition().getX();
            float moleculeY = molecule.getPosition().getY();
            float moleculeZ = molecule.getPosition().getZ();
            writer.write("pos " + moleculeX + " " + moleculeY + " " + moleculeZ + "\n");

            // write the atoms
            for (Atom a : molecule.getAtoms()) {
                writer.write(a.toString() + "\n");
            }

            // write the bonds
            for (Bond b : molecule.getBonds()) {
                writer.write(b.toString() + "\n");
            }

            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * loads a molecule from a file
     * @param file - the file
     * @return - the molecule
     */
    @NotNull
    public static Molecule loadMolecule(File file) {
        // create a pointer towards a temp empty molecule
        Molecule molecule = new Molecule(new Vector3f(0), new ArrayList<>(), new ArrayList<>());

        // create the atoms
        try {
            Scanner input = new Scanner(file);
            while (input.hasNextLine()) {
                String data = input.nextLine();
                String[] pieces = data.split("\\s+");
                if (pieces[0].equals("pos")) {
                    float x = Float.parseFloat(pieces[MOLECULE_X]);
                    float y = Float.parseFloat(pieces[MOLECULE_Y]);
                    float z = Float.parseFloat(pieces[MOLECULE_Z]);
                    molecule.setPosition(new Vector3f(x, y, z));
                }
                if (pieces[0].equals("atom")) {
                    // get the atom data
                    float x = Float.parseFloat(pieces[ATOM_X]);
                    float y = Float.parseFloat(pieces[ATOM_Y]);
                    float z = Float.parseFloat(pieces[ATOM_Z]);
                    int atomID = Integer.parseInt(pieces[ATOM_ID]);
                    int atomicNumber = Integer.parseInt(pieces[ATOM_ATOMIC_NUMBER]);
                    int charge = Integer.parseInt(pieces[ATOM_CHARGE]);
                    int numNeutrons = Integer.parseInt(pieces[ATOM_NUM_NEUTRONS]);
                    // add the atom
                    Atom atom = new Atom(new Vector3f(x, y, z), atomicNumber, charge, numNeutrons, atomID);
                    molecule.addAtom(atom);
                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        // create the bonds
        try {
            Scanner input = new Scanner(file);
            while (input.hasNextLine()) {
                String data = input.nextLine();
                String[] pieces = data.split("\\s+");
                if (pieces[0].equals("bond")) {
                    // get the bond data
                    int bondID = Integer.parseInt(pieces[BOND_ID]);
                    Atom a1 = molecule.getAtomFromAtomID(Integer.parseInt(pieces[BOND_ATOM_1_ID]));
                    Atom a2 = molecule.getAtomFromAtomID(Integer.parseInt(pieces[BOND_ATOM_2_ID]));
                    int bondOrder = Integer.parseInt(pieces[BOND_ORDER]);
                    molecule.addBond(new Bond(a1, a2, bondOrder, bondID));
                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        return molecule;
    }

    /**
     * loads a molecule from a text file
     * @return - the molecule
     */
    public static Molecule loadMolecule(String pathToFile) {

        // create a temp empty pointer towards a molecule
        Molecule molecule = new Molecule(new Vector3f(0), new ArrayList<>(), new ArrayList<>());

        try {
            Scanner input = new Scanner(new File(pathToFile));
            while (input.hasNextLine()) {
                String data = input.nextLine();
                String[] pieces = data.split("\\s+");
                if (pieces[0].equals("pos")) {
                    float x = Float.parseFloat(pieces[MOLECULE_X]);
                    float y = Float.parseFloat(pieces[MOLECULE_Y]);
                    float z = Float.parseFloat(pieces[MOLECULE_Z]);
                    molecule.setPosition(new Vector3f(x, y, z));
                }
                if (pieces[0].equals("atom")) {
                    // get the atom data
                    float x = Float.parseFloat(pieces[ATOM_X]);
                    float y = Float.parseFloat(pieces[ATOM_Y]);
                    float z = Float.parseFloat(pieces[ATOM_Z]);
                    int atomID = Integer.parseInt(pieces[ATOM_ID]);
                    int atomicNumber = Integer.parseInt(pieces[ATOM_ATOMIC_NUMBER]);
                    int charge = Integer.parseInt(pieces[ATOM_CHARGE]);
                    int numNeutrons = Integer.parseInt(pieces[ATOM_NUM_NEUTRONS]);
                    // add the atom
                    Atom atom = new Atom(new Vector3f(x, y, z), atomicNumber, charge, numNeutrons, atomID);
                    molecule.addAtom(atom);
                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        try {
            Scanner input = new Scanner(new File(pathToFile));
            while (input.hasNextLine()) {
                String data = input.nextLine();
                String[] pieces = data.split("\\s+");
                if (pieces[0].equals("bond")) {
                    // get the bond data
                    int bondID = Integer.parseInt(pieces[BOND_ID]);
                    Atom a1 = molecule.getAtomFromAtomID(Integer.parseInt(pieces[BOND_ATOM_1_ID]));
                    Atom a2 = molecule.getAtomFromAtomID(Integer.parseInt(pieces[BOND_ATOM_2_ID]));
                    int bondOrder = Integer.parseInt(pieces[BOND_ORDER]);
                    molecule.addBond(new Bond(a1, a2, bondOrder, bondID));
                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        return molecule;
    }
}