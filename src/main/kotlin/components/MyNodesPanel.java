package components;

import javax.swing.*;
import java.awt.*;

public class MyNodesPanel extends JPanel {
    private int xCoords[] = {3, 4, 6, 89, 9};
    private int yCoords[] = {5, 6, 68, 56, 54};

    public MyNodesPanel() {
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
    }

    @Override
    public void paintComponents(Graphics g) {
        super.paintComponents(g);
        g.setColor(new Color(54,34,56));

        for(int i = 1; i < xCoords.length; i++)
        {
            g.drawLine(xCoords[i-1], yCoords[i-1], xCoords[i], yCoords[i]);
        }
    }
}
