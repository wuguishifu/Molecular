package com.bramerlabs.engine.io.file_util.file_saver;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class FileChooser extends JPanel implements ActionListener {

    private JButton go;

    // the java file chooser
    private JFileChooser chooser;

    // the title
    private static final String chooserTitle = "Please select a location to save your file to.";

    /**
     * main constructor
     */
    public FileChooser() {
        go = new JButton("Select Location");
        go.addActionListener(this);
        this.add(go);
    }

    /**
     * performs an action
     * @param e - the action event
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        chooser = new JFileChooser();
        chooser.setCurrentDirectory(new java.io.File("."));
        chooser.setDialogTitle(chooserTitle);
        chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);

        // disable the "all files" option
        chooser.setAcceptAllFileFilterUsed(false);

        if (chooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
            System.out.println("getCurrentDirectory(): " + chooser.getCurrentDirectory());
            System.out.println("getSelectedFile(): " + chooser.getSelectedFile());
        } else {
            System.out.println("No selection.");
        }
    }

    /**
     * getter method
     * @return - the preferred size of this file chooser
     */
    public Dimension getPreferredSize() {
        return new Dimension(200, 200);
    }

    public static void main(String[] s) {
        JFrame frame = new JFrame("");
        FileChooser panel = new FileChooser();
        frame.addWindowListener(
                new WindowAdapter() {
                    public void windowClosing(WindowEvent e) {
                        System.exit(0);
                    }
                }
        );
        frame.getContentPane().add(panel,"Center");
        frame.setSize(panel.getPreferredSize());
        frame.setVisible(true);
    }
}
