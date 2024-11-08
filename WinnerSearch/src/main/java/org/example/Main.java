package org.example;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import javax.swing.*;

public class Main {
    public static void main(String[] args){
        EventQueue.invokeLater(()-> {
            var frame = new JFrame("Поиск побелителя розыгрыша");
            frame.setSize(600,300);
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            Container panel = frame.getContentPane();
            panel.add (new JLabel("Список участников:"), BorderLayout.NORTH);
            panel.add (new ButtonPanel(), BorderLayout.SOUTH);
            frame.setVisible(true);
        });
    }
}


/*class Participants extends JComponent{
    public static String participants= "Hello";
    public void drawParticipants(Graphics g) {
        Graphics2D g2 =(Graphics2D)g;
        g2.drawString(participants,10,60);
    }
}
*/
class Table extends JTable{
    public static List<String> participansArr = new ArrayList<String>();
     public Table(){
         return new JTable(participansArr)
     }
}

class ButtonPanel extends JPanel {

    JButton buttonWinner, chooseFile;

    public ButtonPanel(){
        buttonWinner = new JButton("Найти победителя");
        chooseFile = new JButton("Выберите файл");

        var buttonStart = new ButtonStart();
        var buttonChooseFile = new ButtonChooseFile();

        buttonWinner.addActionListener(buttonStart);
        chooseFile.addActionListener(buttonChooseFile);

        add(buttonWinner);
        add(chooseFile);
    }

    private class ButtonStart implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {

        }
    }
    private class ButtonChooseFile implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent ae) {

            var chooser = new JFileChooser();
            int selected = chooser.showOpenDialog(chooseFile);
            if (selected==JFileChooser.APPROVE_OPTION) {
                File sourceFile = chooser.getSelectedFile();
                Path path = sourceFile.toPath();
                try {
                    //Participants.participants = Files.readString(path);
                    //Table.participansArr = Files.readString(path).split("/n");
                    Table.participansArr = new ArrayList<String>(Files.readAllLines(path));
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        }
    }

}