package view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MainView extends JFrame {
    private JPanel mainPanel;
    private JButton envoyerButton;
    private JTextField textField1;

    public MainView(String title) {
        super(title);

        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setContentPane(mainPanel);
        this.setMinimumSize(new Dimension(400,400));
        this.setSize(600,600);


        envoyerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                envoyerButton.setForeground(Color.GREEN);
            }
        });
    }

    public static void main(String[] args) {

    }
}
