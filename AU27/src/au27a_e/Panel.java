package au27a_e;

import javax.swing.*;
import java.awt.*;

import static api.gui.GUIHelper.*;

public class Panel extends JPanel {
    private Model m;

    public Panel(Model m) {
        this.m = m;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        int i = Math.round(Math.min(this.getSize().height, this.getSize().width) / 100.0f * this.m.getSizePercent());
        Dimension newSize = new Dimension(i, i);
        this.m.setObjSize(newSize);
        Color color = m.getObjColor();
        Point pos = m.getObjPos();
        Dimension size = m.getObjSize();
        Point pos2 = new Point(pos);
        pos2.translate(10, 10);
        switch (this.m.getShape()) {
            case Model.SHAPE_EMPTY:
                break;
            case Model.SHAPE_CIRCLE:
                if (this.m.isFilled()) {
                    if (this.m.isShaded()) {
                        fillOval(g, pos2, size, Color.darkGray);
                    }
                    fillOval(g, pos, size, color);
                } else {
                    drawOval(g, pos, size);
                }
                break;
            case Model.SHAPE_SQUARE:
                if (this.m.isFilled()) {
                    if (this.m.isShaded()) {
                        fillRect(g, pos2, size, Color.darkGray);
                    }
                    fillRect(g, pos, size, color);
                } else {
                    drawRect(g, pos, size);
                }
                break;
            case Model.SHAPE_LINE:
                Point pos3 = new Point(pos);
                pos3.translate(size.width, size.height);
                drawLine(g, pos, pos3);
                break;
        }
    }
}