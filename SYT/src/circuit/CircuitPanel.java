package circuit;

import javax.swing.*;
import java.awt.*;

public class CircuitPanel extends JPanel {

    private final Model m;

    public CircuitPanel(Model m) {
        this.m = m;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (g instanceof Graphics2D) {
            Graphics2D g2D = (Graphics2D) g;
            m.map.paint(g2D);
        }
    }
}
