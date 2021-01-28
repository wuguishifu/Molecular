package com.bramerlabs.engine.io.file_util;

import com.bramerlabs.molecular.file_io.MoleculeIO;
import com.bramerlabs.molecular.molecule.Molecule;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;

public class FileSelector extends JPanel implements ActionListener {

    // the size of the window displayed
    private int width = 800;
    private int height = 600;

    private static String selectedFile;

    // the go button
    JButton go;

    // the JFileChooser and the title
    JFileChooser chooser;
    String chooserTitle;

    /**
     * default constructor
     */
    public FileSelector() {
        go = new JButton("Select a file...");
        go.addActionListener(this);
        add(go);
    }

    /**
     * getter method
     * @return - the selected file path
     */
    public static String getSelectedFilePath() {
        return selectedFile;
    }

    public static void selectFile() {
        JFrame frame = new JFrame("Please select a file.");
        FileSelector panel = new FileSelector();
        frame.addWindowListener(new WindowAdapter() {});
        frame.getContentPane().add(panel, "Center");
        frame.setSize(panel.getPreferredSize());
        frame.setVisible(true);
    }

    /**
     * gets the preferred dimension of the window
     * @return - the preferred size, default 800x600
     */
    public Dimension getPreferredSize() {
        return new Dimension(800, 600);
    }

    /**
     * runs when the go button is clicked
     * @param actionEvent - the action event handed down by the button
     */
    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        chooser = new JFileChooser();
        chooser.setCurrentDirectory(new java.io.File("."));
        chooser.setDialogTitle(chooserTitle);
        chooser.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
        chooser.setAcceptAllFileFilterUsed(true); // enable the "All Files" option
        if (chooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
            selectedFile = chooser.getSelectedFile().toString();
            Molecule molecule = MoleculeIO.loadMolecule(selectedFile);
            System.out.println(molecule);
        }
    }
}
