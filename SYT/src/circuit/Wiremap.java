package circuit;

import org.jetbrains.annotations.NotNull;

import java.awt.*;
import java.awt.geom.Line2D;
import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicInteger;

public class Wiremap {
    ArrayList<Wire> wires = new ArrayList<>();
    private CircuitMap cm;
    private int x, y;

    public Wiremap(CircuitMap circuitMap, int x, int y) {
        this.cm = circuitMap;
        this.x = x;
        this.y = y;
    }

    public synchronized void paint(Graphics2D g2D, Point pos) {
        wires.forEach(wire -> paintWire(g2D, wire, pos));
    }

    private synchronized void paintWire(Graphics2D g2D, @NotNull Wire wire, Point pos) {
        boolean flag = false;
        Circuit f = cm.get(wire.getFrom(), x, y);
        Point from;
        ArrayList<Point> to = new ArrayList<>();
        for (int i = 0; i < wire.getTo().size(); i++) {
            Circuit t = cm.get(wire.getTo(i), x, y);
            if (t == null) {
                flag = true;
                continue;
            }
            Point p = new Point(t.getConnection(wire.getTo(i).inverse(), wire.getToIndex(i)));
            if (wire.getTo(i) == Wire.Side.TOP || wire.getTo(i) == Wire.Side.BOTTOM) p.translate(0, -10);
            to.add(p);
        }
        if (f == null) {
            from = new Point(to.get(0));
            from.translate(-50, 0);
        } else {
            from = new Point(f.getConnection(wire.getFrom().inverse(), wire.getFromIndex()));
        }
        if (flag) {
            Point t = new Point(from);
            t.translate(50, 0);
            to.add(t);
        }
        AtomicInteger i = new AtomicInteger(0);
        to.forEach(point -> {
            Point to1 = new Point(point);
            Point from1 = new Point(from);
            Point pos1 = new Point(pos);
            if ((cm.get(wire.getFrom(), x, y) != Circuit.WIRE && cm.get(wire.getTo(i.get()), x, y) != Circuit.WIRE) || (from1.x == to1.x || from1.y == to1.y)) {
                Point tmp1 = new Point(to1.x / 2, from1.y);
                Point tmp2 = new Point(to1.x / 2, to1.y);
                tmp1.translate(pos1.x, pos1.y);
                tmp2.translate(pos1.x, pos1.y);
                from1.translate(pos1.x, pos1.y);
                to1.translate(pos1.x, pos1.y);
                Line2D line = new Line2D.Double(from1, tmp1);
                g2D.draw(line);
                line = new Line2D.Double(tmp1, tmp2);
                g2D.draw(line);
                line = new Line2D.Double(tmp2, to1);
                g2D.draw(line);
            }
            i.incrementAndGet();
        });
    }
}
