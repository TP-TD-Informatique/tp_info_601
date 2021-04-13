package components;

import javax.swing.*;
import java.awt.*;

public class MyRelation extends JComponent {
    private MyNode destinationNode;
    private String label;

    public MyRelation(MyNode destinationNode, String label) {
        this.destinationNode = destinationNode;
        this.label = label;
    }
}
