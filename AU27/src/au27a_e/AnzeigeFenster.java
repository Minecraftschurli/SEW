package au27a_e;

import javax.swing.*;
import java.awt.*;

public class AnzeigeFenster extends JFrame {
    private Model m;
    private Controller c;
    private Panel ap;

    public AnzeigeFenster(Model m, Controller c) {
        this.m = m;
        this.c = c;

        this.setTitle("Anzeige-Fenster");
        this.setSize(400, 300);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        this.ap = new Panel(this.m);
        this.add(this.ap);

        this.ap.addMouseListener(this.c);
        this.ap.addMouseMotionListener(this.c);

        this.setVisible(true);
    }

    public Dimension getPanelSize() {
        return ap.getSize(null);
    }


    public void refresh() {
        if (this.m.getSelected() != null) {
            int i = Math.round(Math.min(getPanelSize().height, getPanelSize().width) / 100.0f * this.m.getSizePercent());
            Dimension newSize = new Dimension(i, i);
            this.m.setObjSize(newSize);
        }
        this.repaint();
    }

    public void draw(Shape shape) {
        this.ap.draw(shape);
    }

    public Point getPanelPos() {
        return this.ap.getLocation();
    }
}