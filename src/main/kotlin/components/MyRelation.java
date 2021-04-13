package components;

import javax.swing.*;
import java.awt.*;

public class MyRelation extends JLabel {
    private JTextArea originNode;
    private JTextArea destinationNode;

    public MyRelation(String text, JTextArea originNode, JTextArea destinationNode) {
        super(text);
        this.originNode = originNode;
        this.destinationNode = destinationNode;
    }

    public MyRelation(String text) {
        super(text);
    }
}
