package components;

import javax.swing.*;
import java.awt.*;

public class MyNode extends JComponent {
    private String name;
    private String surname;

    public MyNode(String name, String surname) {
        this.name = name;
        this.surname = surname;
        this.setSize(50, 50);
        this.setBackground(Color.BLUE);
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);

        g.setColor(Color.GREEN);
        g.fillRect(0, 0, 50, 50);
    }
}