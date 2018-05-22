package main;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.Ellipse2D;

import static api.gui.GUIHelper.drawCircle;
import static api.gui.GUIHelper.fillCircle;

public class CookieClickerButton extends JButton {

    private Dimension size;
    private Point center;

    // Hit detection.
    Shape shape;
    private boolean test;


    public CookieClickerButton(String label){
        super(label);

        setBackground(Color.lightGray);
        setFocusable(false);

    /*
     These statements enlarge the button so that it
     becomes a circle rather than an oval.
    */
        this.size = getPreferredSize();
        size.width = size.height = Math.max(size.width, size.height);
        setPreferredSize(size);

    /*
     This call causes the JButton not to paint the background.
     This allows us to paint a round background.
    */
        setContentAreaFilled(false);

        this.center = new Point(getWidth()/2,getHeight()/2);
    }

    protected void paintComponent(Graphics g) {
        this.center.x = getWidth()/2;
        this.center.y = getHeight()/2;
        if (getModel().isArmed()) {
            g.setColor(Color.gray);
        } else {
            g.setColor(getBackground());
        }
        fillCircle(g,this.center,(getSize().width>getSize().height ? getSize().height : getSize().width)/2-1);

        super.paintComponent(g);
    }

    protected void paintBorder(Graphics g) {
        this.center.x = getWidth()/2;
        this.center.y = getHeight()/2;
        drawCircle(g,this.center,(getSize().width>getSize().height ? getSize().height : getSize().width)/2-1,Color.darkGray);
    }

    public boolean contains(int x, int y) {
        // If the button has changed size,  make a new shape object.
        if (shape == null || !shape.getBounds().equals(getBounds())) {
            shape = new Ellipse2D.Float(0, 0, getWidth(), getHeight());
        }
        return shape.contains(x, y);
    }

    public void test(){
        this.test = true;
        doClick();
        this.test = false;
    }

    public boolean isTest() {
        return test;
    }
}
