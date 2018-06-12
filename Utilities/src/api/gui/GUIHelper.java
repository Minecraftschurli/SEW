package api.gui;

import java.awt.*;

public class GUIHelper {

    public static Color getContrastColor(Color color) {
        double y = ((299 * color.getRed()) + (587 * color.getGreen()) + (114 * color.getBlue())) / 1000.0;
        return y >= 128 ? Color.black : Color.white;
    }

    public static Color getInverseColor(Color color){
        int newR = 255 - color.getRed();
        int newG = 255 - color.getGreen();
        int newB = 255 - color.getBlue();
        return new Color(newR, newG, newB);
    }

    public static Color subtractColor(Color c1, Color c2) {
        int newR, newG, newB;
        for (newR = c1.getRed() - c2.getRed(); newR < 0; newR++) {
        }
        for (newG = c1.getGreen() - c2.getGreen(); newG < 0; newG++) {
        }
        for (newB = c1.getBlue() - c2.getBlue(); newB < 0; newB++) {
        }
        return new Color(newR, newG, newB);
    }

    public static Color brighter(Color c, float by) {
        float[] hsl = rgbToHsl(c.getRGB(), null);
        hsl[2] += by;
        return new Color(hslToRgb(hsl));
    }

    public static float[] rgbToHsl(int rgb, float[] hsl) {
        if (hsl == null) hsl = new float[3];
        float r = ((0x00ff0000 & rgb) >> 16) / 255.f;
        float g = ((0x0000ff00 & rgb) >> 8) / 255.f;
        float b = ((0x000000ff & rgb)) / 255.f;
        float max = Math.max(Math.max(r, g), b);
        float min = Math.min(Math.min(r, g), b);
        float c = max - min;

        float h_ = 0.f;
        if (c == 0) {
            h_ = 0;
        } else if (max == r) {
            h_ = (g - b) / c;
            if (h_ < 0) h_ += 6.f;
        } else if (max == g) {
            h_ = (b - r) / c + 2.f;
        } else if (max == b) {
            h_ = (r - g) / c + 4.f;
        }
        float h = 60.f * h_;

        float l = (max + min) * 0.5f;

        float s;
        if (c == 0) {
            s = 0.f;
        } else {
            s = c / (1 - Math.abs(2.f * l - 1.f));
        }

        hsl[0] = h;
        hsl[1] = s;
        hsl[2] = l;
        return hsl;
    }

    public static int hslToRgb(float[] hsl) {
        float h = hsl[0];
        float s = hsl[1];
        float l = hsl[2];

        float c = (1 - Math.abs(2.f * l - 1.f)) * s;
        float h_ = h / 60.f;
        float h_mod2 = h_;
        if (h_mod2 >= 4.f) h_mod2 -= 4.f;
        else if (h_mod2 >= 2.f) h_mod2 -= 2.f;

        float x = c * (1 - Math.abs(h_mod2 - 1));
        float r_, g_, b_;
        if (h_ < 1) {
            r_ = c;
            g_ = x;
            b_ = 0;
        } else if (h_ < 2) {
            r_ = x;
            g_ = c;
            b_ = 0;
        } else if (h_ < 3) {
            r_ = 0;
            g_ = c;
            b_ = x;
        } else if (h_ < 4) {
            r_ = 0;
            g_ = x;
            b_ = c;
        } else if (h_ < 5) {
            r_ = x;
            g_ = 0;
            b_ = c;
        } else {
            r_ = c;
            g_ = 0;
            b_ = x;
        }

        float m = l - (0.5f * c);
        int r = (int) ((r_ + m) * (255.f) + 0.5f);
        int g = (int) ((g_ + m) * (255.f) + 0.5f);
        int b = (int) ((b_ + m) * (255.f) + 0.5f);
        return r << 16 | g << 8 | b;
    }

    public static void drawCircle(Graphics g,Point center,int radius){
        g.drawOval(center.x-radius,center.y-radius,radius*2,radius*2);
    }

    public static void drawCircle(Graphics g,Point center,int radius,Color color){
        Color old = g.getColor();
        g.setColor(color);
        g.drawOval(center.x-radius,center.y-radius,radius*2,radius*2);
        g.setColor(old);
    }

    public static void fillCircle(Graphics g,Point center,int radius){
        g.fillOval(center.x-radius,center.y-radius,radius*2,radius*2);
    }

    public static void fillCircle(Graphics g,Point center,int radius,Color color){
        Color old = g.getColor();
        g.setColor(color);
        g.fillOval(center.x-radius,center.y-radius,radius*2,radius*2);
        g.setColor(old);
    }

    public static void drawOval(Graphics g, Point pos, Dimension size) {
        g.drawOval(pos.x, pos.y, size.width, size.height);
    }

    public static void drawOval(Graphics g, Point pos, Dimension size, Color color) {
        Color old = g.getColor();
        g.setColor(color);
        g.drawOval(pos.x, pos.y, size.width, size.height);
        g.setColor(old);
    }

    public static void fillOval(Graphics g, Point pos, Dimension size) {
        g.fillOval(pos.x, pos.y, size.width, size.height);
    }

    public static void fillOval(Graphics g, Point pos, Dimension size, Color color) {
        Color old = g.getColor();
        g.setColor(color);
        g.fillOval(pos.x, pos.y, size.width, size.height);
        g.setColor(old);
    }

    public static void drawLine(Graphics g, Point from, Point to) {
        g.drawLine(from.x,from.y,to.x,to.y);
    }

    public static void drawVector(Graphics g, Point pos, double angle, int length){
        drawLine(g,pos,angularMovedPoint(pos,length,angle));
    }

    public static void drawString(Graphics g, String s, Point pos) {
        g.drawString(s,pos.x,pos.y);
    }

    public static void drawRectWithString(Graphics g, Point pos, String s) {
        drawRect(g,pos,new Dimension(s.length()*6+5,14));
        pos.translate(3,12);
        drawString(g,s,pos);
    }

    public static void drawLine(Graphics g, Point from, Point to,Color color) {
        Color old = g.getColor();
        g.setColor(color);
        g.drawLine(from.x,from.y,to.x,to.y);
        g.setColor(old);
    }

    public static void drawVector(Graphics g, Point pos, double angle, int length, Color color){
        drawLine(g,pos,angularMovedPoint(pos,length,angle),color);
    }

    public static void drawRect(Graphics g, Point pos, Dimension dim) {
        g.drawRect(pos.x, pos.y, dim.width, dim.height);
    }

    public static void drawRect(Graphics g,Point pos,Dimension dim, Color color){
        Color old = g.getColor();
        g.setColor(color);
        g.drawRect(pos.x,pos.y,dim.width,dim.height);
        g.setColor(old);
    }

    public static void fillRect(Graphics g, Point pos, Dimension dim) {
        g.fillRect(pos.x, pos.y, dim.width, dim.height);
    }

    public static void fillRect(Graphics g, Point pos, Dimension dim, Color color) {
        Color old = g.getColor();
        g.setColor(color);
        g.fillRect(pos.x, pos.y, dim.width, dim.height);
        g.setColor(old);
    }

    public static void drawString(Graphics g, String s, Point pos,Color color) {
        Color old = g.getColor();
        g.setColor(color);
        g.drawString(s,pos.x,pos.y);
        g.setColor(old);
    }

    public static void drawRectWithString(Graphics g, Point pos, String s,Color rectColor,Color stringColor) {
        drawRect(g,pos,new Dimension(s.length()*6+6,14),rectColor);
        pos.translate(3,12);
        drawString(g,s,pos,stringColor);
    }

    public static void fillShape(Graphics g, Shape shape, Color color) {
        if (g instanceof Graphics2D) {
            Graphics2D g2 = (Graphics2D) g;
            Color old = g2.getColor();
            g2.setColor(color);
            g2.fill(shape);
            g2.setColor(old);
        }
    }

    public static void drawShape(Graphics g, Shape shape) {
        if (g instanceof Graphics2D) {
            Graphics2D g2 = (Graphics2D) g;
            g2.draw(shape);
        }
    }

    public static Point angularMovedPoint(Point base, int distance, double angle){
        double x, y;
        double sin = sin(angle) * distance;
        double cos = cos(angle) * distance;

        x = base.x + sin;
        y = base.y - cos;

        return new Point((int)x,(int)y);
    }
    
    public static double sin(double x){
        return Math.sin(Math.toRadians(x));
    }

    public static double cos(double y){
        return Math.cos(Math.toRadians(y));
    }
}
