package org.example;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import javax.swing.*;
import javax.swing.event.ListSelectionListener;

public class WinnerSearch extends JPanel {
    private JList list;
    private DefaultListModel listModel;

    public WinnerSearch(){
        super(new BorderLayout());
        listModel = new DefaultListModel();
        listModel.addElement("Выберите файл со списком участников");
        list = new JList(listModel);
        JScrollPane listScrollPane = new JScrollPane(list);
        add (new JLabel("Список участников:"), BorderLayout.NORTH);
        add (listScrollPane);
        add (new ButtonPanel(), BorderLayout.SOUTH);
    }


    public class ButtonPanel extends JPanel {

    JButton buttonWinner, chooseFile;
    public String[] str;

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

        private String[] str;
        public void getArrayOfPar(Path path) {
            try {
                str = Files.readString(path).indent(0).split("\n");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void actionPerformed(ActionEvent ae) {

            var chooser = new JFileChooser();

            int selected = chooser.showOpenDialog(chooseFile);
            if (selected == JFileChooser.APPROVE_OPTION) {
                File sourceFile = chooser.getSelectedFile();
                Path path = sourceFile.toPath();
                getArrayOfPar(path);
                listModel.removeAllElements();
                for (int i=0; i<str.length; i++){
                    listModel.addElement(str[i]);
                }
            }
        }
    }
}

    private static void createApp(){
        JFrame frame = new JFrame("Поиск побелителя розыгрыша");
        JComponent contentPane = new WinnerSearch();
        contentPane.setOpaque(true);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setSize(600,600);
        frame.setContentPane(contentPane);
        frame.pack();
        frame.setVisible(true);
    }
    public static void main(String[] args){
        EventQueue.invokeLater(()-> {
            createApp();
        });
    }
}
