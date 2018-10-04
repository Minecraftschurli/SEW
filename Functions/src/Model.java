import javax.swing.*;
import java.awt.*;
import java.util.HashMap;

public class Model extends JPanel {

    private final double scale;
    HashMap<String, Double> v = new HashMap<>();
    private MathFunction function;
    private int length;
    private double a, b, c;
    private int n;

    public Model(String function, double scale) {
        this.n = 2;
        this.v.put("a", a);
        this.v.put("b", b);
        this.v.put("c", c);
        this.v.put("n", (double) n);
        this.function = MathFunction.getFromString(function, v);
        this.length = (1000);
        this.scale = scale;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (g instanceof Graphics2D) {
            Graphics2D g2d = (Graphics2D) g;
            drawFunctionCross(g2d);
            drawFunction(g2d, function);
        }
    }

    private synchronized void drawFunction(Graphics2D g2d, MathFunction function) {
        int[][] points = getPoints(function);
        g2d.drawPolyline(points[0], points[1], points[0].length);
    }

    private synchronized void drawFunctionCross(Graphics2D g2d) {
        int width = this.getWidth(), height = this.getHeight();
        //draw Y-Axis
        g2d.drawLine(width / 2, 0, width / 2, height);
        g2d.drawString("Y", width / 2 - 10, 10);
        //draw X-Axis
        g2d.drawLine(0, height / 2, width, height / 2);
        g2d.drawString("X", width - 10, height / 2 + 10);
    }

    private synchronized int[][] getPoints(MathFunction f) {
        Point[] points = new Point[length * 2];
        for (int i = -length; i < length; i++) {
            Point p = new Point((i), (-f.calc(i)));
            p.translate(this.getWidth() / 2, this.getHeight() / 2);
            points[i + length] = p;
        }
        int[][] values = new int[2][points.length];
        for (int i = 0; i < points.length; i++) {
            values[0][i] = points[i].x;
            values[1][i] = points[i].y;
        }
        return values;
    }

    public void setA(double a) {
        this.a = a * scale;
        this.v.put("a", this.a);
    }

    public void setB(double b) {
        this.b = b * scale;
        this.v.put("b", this.b);
    }

    public void setC(double c) {
        this.c = c * scale;
        this.v.put("c", this.c);
    }

    public void setN(int n) {
        this.n = n;
        this.v.put("n", (double) this.n);
    }
}
