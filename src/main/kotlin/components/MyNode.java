package components;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class MyNode extends JTextArea {
    private Coordonnees coords;
    private ArrayList<MyNode> relations;

    public MyNode(String text, Coordonnees coords) {
        super(text);
        this.coords = coords;
        relations = new ArrayList<MyNode>();
    }

    public ArrayList<MyNode> getRelations() {
        return relations;
    }

    public void setRelations(MyNode node) {
        relations.add(node);
    }

    public Coordonnees getCoords() {
        return this.coords;
    }

    public Dimension getDimensions() {
        return getPreferredSize();
    }

    /*
    @Override
    public void paint(Graphics g) {
        Graphics2D graphic2d = (Graphics2D) g;
        graphic2d.setColor(Color.blue);
        graphic2d.fillRect(30, 30, 100, 100);
    }*/
}