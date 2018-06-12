package au27a_e;

import javax.swing.*;
import java.awt.*;
import java.util.TimerTask;

import static api.gui.GUIHelper.*;
import static au27a_e.Model.calcPolygon;

public class Panel extends JPanel {
    private final java.util.Timer t = new java.util.Timer();
    private Model m;
    private boolean flag;

    public Panel(Model m) {
        this.m = m;
        this.m.getSelected().setEasteregg(() -> {
            flag = true;
            t.schedule(new TimerTask() {
                @Override
                public void run() {
                    flag = false;
                    repaint();
                }
            }, 10000);
        });
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        this.m.forEachObj((index, obj) -> {
            int i = Math.round(Math.min(this.getSize().height, this.getSize().width) / 100.0f * obj.getSizePercent());
            Dimension newSize = new Dimension(i, i);
            obj.setObjSize(newSize);
            Color color = obj.getColor();
            Point pos = obj.getPos();
            Dimension size = obj.getObjSize();
            Point pos2 = new Point(pos);
            pos2.translate(10, 10);
            switch (obj.getShape()) {
                case EMPTY:
                    break;
                case CIRCLE:
                    if (obj.isFilled()) {
                        if (obj.isShaded()) {
                            fillOval(g, pos2, size, Color.darkGray);
                        }
                        fillOval(g, pos, size, color);
                    } else {
                        drawOval(g, pos, size);
                    }
                    break;
                case SQUARE: {
                    if (obj.isFilled()) {
                        if (obj.isShaded()) {
                            fillRect(g, pos2, size, Color.darkGray);
                        }
                        fillRect(g, pos, size, color);
                    } else {
                        drawRect(g, pos, size);
                    }
                }
                break;
                case TRIANGLE:
                case PENTAGON:
                case HEXAGON:
                case HEPTAGON:
                case OCTAGON:
                case NONAGON:
                case DODECAGON:
                case LINE: {
                    Polygon p = calcPolygon(obj.getShape().polygonSides, size.width / 2.0, pos, obj.getRotation());
                    Polygon p2 = calcPolygon(obj.getShape().polygonSides, size.width / 2.0, pos2, obj.getRotation());
                    if (obj.isFilled()) {
                        if (obj.isShaded()) {
                            fillShape(g, p2, Color.darkGray);
                        }
                        fillShape(g, p, color);
                    } else {
                        drawShape(g, p);
                    }
                }
                break;
            }
        });
        if (flag) {
            g.setFont(new Font("Sans-Serif", Font.PLAIN, 20));
            g.drawString("42", 10, g.getFontMetrics().getHeight() + 10);

        }
    }

    public void draw(Shape shape) {
        ((Graphics2D) this.getGraphics()).draw(shape);
    }
}