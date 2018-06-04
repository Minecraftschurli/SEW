package template;

import javax.swing.*;
import java.awt.*;

public class AnzeigePanel extends JPanel {
    private Model m;

    public AnzeigePanel(Model m) {
        this.m = m;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (this.m.istGefuellt()) {
            g.fillOval(10, 10, 100, 100);
        } else {
            g.drawOval(10, 10, 100, 100);
        }
    }
}