package org.example;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class ButtonPanel extends JPanel {

    JButton buttonWinner, chooseFile;

    public ButtonPanel() {
        buttonWinner = new JButton("Найти победителя");
        chooseFile = new JButton("Выберите файл");

        var buttonStart = new ButtonStart();
        var buttonChooseFile = new ButtonChooseFile();

        buttonWinner.addActionListener(buttonStart);
        chooseFile.addActionListener(buttonChooseFile);

        add(buttonWinner);
        add(chooseFile);
    }

    public class ButtonStart implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {

        }
    }

    public class ButtonChooseFile implements ActionListener {
        String[] str;

        public String[] getArrayOfPar(Path path) {
            try {
                str = Files.readString(path).indent(0).split("\n");
            } catch (IOException e) {
                e.printStackTrace();
            }
            return str;
        }

        @Override
        public void actionPerformed(ActionEvent ae) {

            var chooser = new JFileChooser();

            int selected = chooser.showOpenDialog(chooseFile);
            if (selected == JFileChooser.APPROVE_OPTION) {
                File sourceFile = chooser.getSelectedFile();
                Path path = sourceFile.toPath();
                WinnerSearch.participantsList = getArrayOfPar(path);
                validate();
            }
        }
    }

}
