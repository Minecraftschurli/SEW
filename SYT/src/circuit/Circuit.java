package circuit;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.awt.*;
import java.awt.geom.Line2D;
import java.util.HashMap;

public enum Circuit {
    AND(50, 50, "AND"),
    OR(50, 50, "OR"),
    NAND(50, 50, "NAND"),
    NOR(50, 50, "NOR"),
    XOR(50, 50, "XOR"),
    XNOR(50, 50, "XNOR"),
    NOT(50, 50, "NOT"),
    WIRE(50, 50, "WIRE");

    private final int width;
    private final int height;
    private final HashMap<Wire.Side, HashMap<Integer, Point>> connections = new HashMap<>();
    private int x;
    private int y;

    Circuit(int width, int height, @NotNull String s) {
        this.width = width;
        this.height = height;
        HashMap<Integer, Point> map = new HashMap<>();
        switch (s) {
            case "AND":
            case "OR":
            case "NAND":
            case "NOR":
            case "XOR":
            case "XNOR":
                map.put(0, new Point(width, 15));
                map.put(1, new Point(width, 25));
                connections.put(Wire.Side.LEFT, new HashMap<>(map));
                map = new HashMap<>();
                map.put(0, new Point(0, 20));
                connections.put(Wire.Side.RIGHT, new HashMap<>(map));
                map = new HashMap<>();
                connections.put(Wire.Side.TOP, new HashMap<>(map));
                map = new HashMap<>();
                connections.put(Wire.Side.BOTTOM, new HashMap<>(map));
                break;
            case "NOT":
                map.put(0, new Point(width, 20));
                connections.put(Wire.Side.LEFT, new HashMap<>(map));
                map = new HashMap<>();
                map.put(0, new Point(0, 20));
                connections.put(Wire.Side.RIGHT, new HashMap<>(map));
                map = new HashMap<>();
                connections.put(Wire.Side.TOP, new HashMap<>(map));
                map = new HashMap<>();
                connections.put(Wire.Side.BOTTOM, new HashMap<>(map));
                break;
            case "WIRE":
                map.put(0, new Point(width, 10));
                map.put(1, new Point(width, 20));
                map.put(2, new Point(width, 30));
                connections.put(Wire.Side.LEFT, new HashMap<>(map));
                map = new HashMap<>();
                map.put(0, new Point(0, 10));
                map.put(1, new Point(0, 20));
                map.put(2, new Point(0, 30));
                connections.put(Wire.Side.RIGHT, new HashMap<>(map));
                map = new HashMap<>();
                map.put(0, new Point(10, height));
                map.put(1, new Point(20, height));
                map.put(2, new Point(30, height));
                connections.put(Wire.Side.TOP, new HashMap<>(map));
                map = new HashMap<>();
                map.put(0, new Point(10, 0));
                map.put(1, new Point(20, 0));
                map.put(2, new Point(30, 0));
                connections.put(Wire.Side.BOTTOM, new HashMap<>(map));
                break;
        }
    }

    public synchronized void paint(Graphics2D g2D, final Point pos, final Wiremap wiremap) {
        Point p = new Point(pos);
        p.translate(5, 5);
        switch (this) {
            case AND:
            case OR:
            case NAND:
            case NOR:
            case XOR:
            case XNOR:
                g2D.draw(new Rectangle(p.x, p.y, 30, 30));
                g2D.draw(new Line2D.Double(pos.x, p.y + 10, p.x, p.y + 10));
                g2D.draw(new Line2D.Double(pos.x, p.y + 20, p.x, p.y + 20));
                switch (this) {
                    case AND:
                    case OR:
                    case XOR:
                        g2D.draw(new Line2D.Double(p.x + 30, p.y + 15, p.x + 45, p.y + 15));
                        break;
                    case NAND:
                    case NOR:
                    case XNOR:
                        g2D.drawOval(p.x + 30, p.y + 10, 10, 10);
                        g2D.draw(new Line2D.Double(p.x + 40, p.y + 15, p.x + 45, p.y + 15));
                        break;
                }
                switch (this) {
                    case AND:
                    case NAND:
                        g2D.drawString("&", p.x + 10, p.y + 20);
                        break;
                    case OR:
                    case NOR:
                        g2D.drawString("\u22651", p.x + 5, p.y + 20);
                        break;
                    case XOR:
                    case XNOR:
                        g2D.drawString("=1", p.x + 5, p.y + 20);
                        break;
                }
                break;
            case NOT:
                g2D.draw(new Rectangle(p.x, p.y, 30, 30));
                g2D.draw(new Line2D.Double(pos.x, p.y + 15, p.x, p.y + 15));
                g2D.drawString("1", p.x + 10, p.y + 20);
                g2D.drawOval(p.x + 30, p.y + 10, 10, 10);
                g2D.draw(new Line2D.Double(p.x + 40, p.y + 15, p.x + 45, p.y + 15));
                break;
            case WIRE:
                wiremap.paint(g2D, pos);
                break;
        }
    }

    @Contract(pure = true)
    public int getWidth() {
        return width;
    }

    @Contract(pure = true)
    public int getHeight() {
        return height;
    }

    public Point getConnection(Wire.Side side, int index) {
        return connections.get(side).get(index);
    }
}
