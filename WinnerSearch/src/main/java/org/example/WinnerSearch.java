package org.example;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import javax.swing.*;

public class WinnerSearch {

    public static String[] participantsList ={"Hello","World!"};
    public static JList<String> list = new JList<>(participantsList);
    public static void main(String[] args){
        var frame = new JFrame("Поиск побелителя розыгрыша");
        frame.setSize(1200,600);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        Container panel = frame.getContentPane();
        panel.add (new JLabel("Список участников:"), BorderLayout.NORTH);
        panel.add(new JScrollPane(list));
        panel.add (new ButtonPanel(), BorderLayout.SOUTH);
        frame.pack();
        frame.setVisible(true);
        /*EventQueue.invokeLater(()-> {

        });*/
    }
}

