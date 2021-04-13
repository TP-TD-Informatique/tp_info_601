package view;

import components.MyRelation;
import components.MyNode;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MainView extends JFrame {
    private JPanel mainPanel;
    private JTextField textField1;
    private JButton envoyerButton;
    private JPanel nodesPanel;

    public MainView(String title) {
        super(title);

        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setContentPane(mainPanel);
        this.setMinimumSize(new Dimension(400,400));

        this.setSize(600,600);
        createUIComponents();

        // Event listener du bouton en haut à droite "envoyer"
        envoyerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                envoyerButton.setForeground(Color.GREEN);
            }
        });
    }

    private void createUIComponents() {
        // TODO: place custom component creation code here
        MyNode node1 = new MyNode("Tom Cruise");
        MyRelation rel1 = new MyRelation("PLAYED_IN");
        MyNode node2 = new MyNode("Top Gun\n1982");

        // Askip il faut utiliser la méthode paintComponent() de JPanel
        nodesPanel.add(node1);
        nodesPanel.add(rel1);
        nodesPanel.add(node2);
    }
}
