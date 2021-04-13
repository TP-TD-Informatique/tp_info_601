package view;

import components.Coordonnees;
import components.MyNodesPanel;
import components.MyRelation;
import components.MyNode;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MainView extends JFrame {
    private JPanel mainPanel;
    private JTextField textField1;
    private JButton envoyerButton;

    public MainView(String title) {
        super(title);

        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setContentPane(mainPanel);
        this.setMinimumSize(new Dimension(400, 400));
        setLayout(new BorderLayout());
        MyNodesPanel p = new MyNodesPanel();
        add(p, BorderLayout.CENTER);

        this.setSize(600, 600);
        //MyNodesPanel nodesPanel = new MyNodesPanel();
        //nodesPanel.createUIComponents();

        // Event listener du bouton en haut à droite "envoyer"
        envoyerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                envoyerButton.setForeground(Color.GREEN);
            }
        });
    }
}
