package org.example;

import javax.swing.*;

public class Frame extends JFrame {

    public Frame (String title, Panel panel){
        setTitle(title);
        add(panel);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }

    public static void gameStopped(String message, Frame frame){
        JOptionPane.showMessageDialog(frame, message);
    }
}
