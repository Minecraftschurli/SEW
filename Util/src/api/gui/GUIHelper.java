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

    public static void drawLine(Graphics g, Point from, Point to) {
        g.drawLine(from.x,from.y,to.x,to.y);
    }

    public static void drawVector(Graphics g, Point pos, double angle, int length){
        drawLine(g,pos,angularMovedPoint(pos,length,angle));
    }

    public static void drawRect(Graphics g,Point pos,Dimension dim){
        g.drawRect(pos.x,pos.y,dim.width,dim.height);
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

    public static void drawRect(Graphics g,Point pos,Dimension dim, Color color){
        Color old = g.getColor();
        g.setColor(color);
        g.drawRect(pos.x,pos.y,dim.width,dim.height);
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
