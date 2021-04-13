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
        Border relationBorder = BorderFactory.createMatteBorder(0, 0, 10, 0, Color.BLACK);
        TitledBorder nodeTitleBorder = BorderFactory.createTitledBorder("Noeud");

        Coordonnees coordsNode1 = new Coordonnees(10,10);
        Coordonnees coordsNode2 = new Coordonnees(40,40);
        // MyNode = JTextarea
        MyNode node1 = new MyNode("Tom Cruise", coordsNode1);
        // MyRelation = JLabel
        MyRelation rel1 = new MyRelation("PLAYED_IN");
        rel1.setBorder(relationBorder);
        MyNode node2 = new MyNode("Top Gun\n1982", coordsNode2);

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
        g.setColor(new Color(54,34,56));
        for (int i = 0; i < nodes.size(); ++i) {
            MyNode actualNode = nodes.get(i);
            ArrayList<MyNode> relations = new ArrayList<MyNode>();
            relations = actualNode.getRelations();
            for (int j = 0; j < relations.size(); ++j) {
                drawLine(actualNode.getCoords(), relations.get(j).getCoords(), g2);
            }
        }
    }

    private void drawLine(Coordonnees coordA, Coordonnees coordB, Graphics2D g2) {
        JTextArea label = new JTextArea("nomRelation");
        label.setBounds(coordB.getX()-coordA.getX(), coordB.getY()-coordA.getY(), label.getPreferredSize().width, label.getPreferredSize().height);
        this.add(label);
        g2.setStroke(new BasicStroke(2));
        g2.drawLine(coordA.getX(),coordA.getY(),
                coordB.getX(),coordB.getY());
    }
}
