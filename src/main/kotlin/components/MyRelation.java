package components;

import javax.swing.*;
import java.awt.*;

public class MyRelation extends JLabel {
    private JTextArea originNode;
    private JTextArea destinationNode;

    public void paint(Graphics g) {
        Graphics2D graphic2d = (Graphics2D) g;
        graphic2d.setColor(Color.black);
        graphic2d.fillRect(0, 0, 100, 2);
    }

    public MyRelation(String text, JTextArea originNode, JTextArea destinationNode) {
        super(text);
        this.originNode = originNode;
        this.destinationNode = destinationNode;
    }

    public MyRelation(String text) {
        super(text);
    }
}
