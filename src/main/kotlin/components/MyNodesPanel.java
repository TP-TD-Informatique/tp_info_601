package components;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.util.ArrayList;

public class MyNodesPanel extends JPanel {
    private ArrayList<MyNode> nodes;

    public MyNodesPanel() {
        setLayout(null);
        nodes = new ArrayList<MyNode>();
        createUIComponents();
    }

    private void createUIComponents() {
        // TODO: place custom component creation code here
        // useless :
        // Border relationBorder = BorderFactory.createMatteBorder(0, 0, 10, 0, Color.BLACK);
        // TitledBorder nodeTitleBorder = BorderFactory.createTitledBorder("Noeud");

        Coordonnees coordsNode1 = new Coordonnees(10, 10);
        Coordonnees coordsNode2 = new Coordonnees(200, 200);
        // MyNode = JTextarea
        MyNode node1 = new MyNode("ACTEUR :\nTom Cruise\n1967", new Coordonnees(10, 10));
        // MyRelation = JLabel
        MyNode node2 = new MyNode("FILM :\nTop Gun\n1982", new Coordonnees(200, 200));
        MyNode node3 = new MyNode("FILM :\nMy neighbour\n1987", new Coordonnees(200, 400));

        nodes.add(node1);
        nodes.add(node2);
        nodes.add(node3);

        node1.addRelation(node2);
        node1.addRelation(node3);

        // Ajout de tous les nodes sur le canvas
        for (int i = 0; i < nodes.size(); i++) {
            // Hitbox
            nodes.get(i).setBounds(nodes.get(i).getCoords().getX(),
                    nodes.get(i).getCoords().getY(),
                    nodes.get(i).getDimensions().width,
                    nodes.get(i).getDimensions().height);
            // Ajout
            this.add(nodes.get(i));
        }
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        Graphics2D g2 = (Graphics2D) g;
        g.setColor(new Color(54, 34, 56));
        for (int i = 0; i < nodes.size(); ++i) {
            MyNode actualNode = nodes.get(i);
            ArrayList<MyNode> relations = new ArrayList<MyNode>();
            relations = actualNode.getRelations();
            for (int j = 0; j < relations.size(); ++j) {
                drawLine(actualNode, relations.get(j), "nom Relation", g2);
            }
        }
    }

    private void drawLine(MyNode node1, MyNode node2, String nomRelation, Graphics2D g2) {
        // Le label qui indique le nom de la liaison
        JTextField label = new JTextField(nomRelation);
        int labelXpos = (node2.getCoords().getX() + node1.getCoords().getX()) / 2;
        int labelYpos = (node2.getCoords().getY() + node1.getCoords().getY()) / 2;
        label.setBackground(null);
        label.setBorder(null);
        label.setBounds(labelXpos, labelYpos, label.getPreferredSize().width, label.getPreferredSize().height);

        // La ligne
        g2.setStroke(new BasicStroke(2));
        g2.drawLine(node1.getCoords().getX() + (node1.getDimensions().width) / 2,
                node1.getCoords().getY() + (node1.getDimensions().height) / 2,
                node2.getCoords().getX(),
                node2.getCoords().getY());
        this.add(label);
    }
}
