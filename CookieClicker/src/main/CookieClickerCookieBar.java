package main;

import javax.swing.*;
import java.awt.*;

public class CookieClickerCookieBar extends JProgressBar {
    
    public CookieClickerCookieBar(int orientation){
        super(orientation,0,1);
        setForeground(new Color(0x874F0B));
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
    }

    public void addValue(int value) {
        this.setValue(this.getValue()+value);
        this.repaint();
    }
}
