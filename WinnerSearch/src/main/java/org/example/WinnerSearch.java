package org.example;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Map;
import java.util.random.RandomGenerator;
import javax.swing.*;

/**
 * Программа для поиска победителя розыгрыша. Список участников получает из файла в файловой системе.
 * Победитель определяются методом бинарного поиска. Визуализация осуществленна с помощью пары игральных кубиков D6.
 * Есть два варианта определения победителя. Автоматически и в ручную, эмуляцией каждого броска кубика.
 *
 * Примерный список участнико находится в папке static.
 */

public class WinnerSearch extends JPanel {
    private JList list;
    private DefaultListModel listModel;
    private boolean stillSearching = true;
    static final ImageIcon dice1 = new ImageIcon("WinnerSearch/src/static/1.png");
    static final ImageIcon dice2 = new ImageIcon("WinnerSearch/src/static/2.png");
    static final ImageIcon dice3 = new ImageIcon("WinnerSearch/src/static/3.png");
    static final ImageIcon dice4 = new ImageIcon("WinnerSearch/src/static/4.png");
    static final ImageIcon dice5 = new ImageIcon("WinnerSearch/src/static/5.png");
    static final ImageIcon dice6 = new ImageIcon("WinnerSearch/src/static/6.png");
    Map<Integer,ImageIcon> diceMap = Map.of(1,dice1,2,dice2,3,dice3,4,dice4,5,dice5,6,dice6);
    JLabel firstDice = new JLabel(dice1);
    JLabel secondDice = new JLabel(dice2);

    public WinnerSearch(){
        setLayout(new BorderLayout());
        firstDice.setPreferredSize(new Dimension(200,200));
        secondDice.setPreferredSize(new Dimension(200,200));
        listModel = new DefaultListModel();
        listModel.addElement("Выберите файл со списком участников");
        list = new JList(listModel);
        JScrollPane listScrollPane = new JScrollPane(list);
        listScrollPane.setPreferredSize(new Dimension(400,700));
        add (new JLabel("Список участников:"), BorderLayout.NORTH);
        add (listScrollPane, BorderLayout.WEST);
        add (new ButtonPanel(), BorderLayout.SOUTH);
        add (firstDice,BorderLayout.CENTER);
        add (secondDice, BorderLayout.EAST);

    }


    public class ButtonPanel extends JPanel {

    JButton buttonWinner, chooseFile, findWinnerManual;
    public String[] str;

    public ButtonPanel() {
        buttonWinner = new JButton("Найти победителя автоматически");
        findWinnerManual = new JButton("Найти победителя пошагово");
        chooseFile = new JButton("Выберите файл");

        var buttonStart = new ButtonStart();
        var buttonChooseFile = new ButtonChooseFile();
        var buttonFindWinnerManual = new ButtonFindWinnerManual();


        buttonWinner.addActionListener(buttonStart);
        chooseFile.addActionListener(buttonChooseFile);
        findWinnerManual.addActionListener(buttonFindWinnerManual);

        add(findWinnerManual);
        add(buttonWinner);
        add(chooseFile);
    }

    public class ButtonStart implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            int size = listModel.getSize();

                while (size > 1) {
                    int firstDiceRoll = RandomGenerator.getDefault().nextInt(1, 7);
                    int secondDiceRoll = RandomGenerator.getDefault().nextInt(1, 7);
                    firstDice.setIcon(diceMap.get(firstDiceRoll));
                    secondDice.setIcon(diceMap.get(secondDiceRoll));
                    if (firstDiceRoll != secondDiceRoll) {
                        if (firstDiceRoll > secondDiceRoll) {
                            listModel.removeRange((int) Math.floor(size / 2), size - 1);
                        } else {
                            listModel.removeRange(0, (int) Math.floor(size / 2)-1);
                        }
                    }
                    size = listModel.getSize();

                }
                listModel.addElement("");
                listModel.addElement("Победитель: " + listModel.getElementAt(0) + ". Поздравляем!!!");

        }
    }

    public class ButtonFindWinnerManual implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
            int size = listModel.getSize();
            int sizeToDelete = (int) Math.floor(listModel.getSize() / 2);

            if (stillSearching) {
                if (size > 1) {
                    int firstDiceRoll = RandomGenerator.getDefault().nextInt(1, 7);
                    int secondDiceRoll = RandomGenerator.getDefault().nextInt(1, 7);
                    firstDice.setIcon(diceMap.get(firstDiceRoll));
                    secondDice.setIcon(diceMap.get(secondDiceRoll));
                    if (firstDiceRoll != secondDiceRoll) {
                        if (firstDiceRoll > secondDiceRoll) {
                            listModel.removeRange(size - (int) Math.floor(listModel.getSize() / 2), size - 1);
                        } else {
                            listModel.removeRange(0, (int) Math.floor(listModel.getSize() / 2)-1);
                        }
                    }
                } else {
                    listModel.addElement("");
                    listModel.addElement("Победитель: " + listModel.getElementAt(0) + ". Поздравляем!!!");
                    stillSearching = false;
                }
            }
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
