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
        MyNode node1 = new MyNode("ACTEUR :\nTom Cruise\n1967", coordsNode1);
        // MyRelation = JLabel
        MyRelation rel1 = new MyRelation("PLAYED_IN");
        // useless :
        // rel1.setBorder(relationBorder);
        MyNode node2 = new MyNode("FILM :\nTop Gun\n1982", coordsNode2);

        nodes.add(node1);
        nodes.add(node2);

        node1.setRelations(node2);
        //node1.setLocation(node1.getCoords().getX(), node1.getCoords().getY());
        //node2.setLocation(node2.getCoords().getX(), node2.getCoords().getY());
        node1.setBounds(node1.getCoords().getX(), node1.getCoords().getY(), node1.getDimensions().width, node1.getDimensions().height);
        node2.setBounds(node2.getCoords().getX(), node2.getCoords().getY(), node2.getDimensions().width, node2.getDimensions().height);
        this.add(node1);
        this.add(node2);
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
                drawLine(actualNode, relations.get(j), g2);
            }
        }
    }

    private void drawLine(MyNode node1, MyNode node2, Graphics2D g2) {
        // Le label qui indique le nom de la liaison
        JTextField label = new JTextField("nomRelation");
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
