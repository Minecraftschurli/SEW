package main;

import javax.swing.*;
import java.awt.*;

public class UpgradeButton extends JButton {
    public UpgradeButton(String s) {
        super(s);
    }

    @Override
    protected void paintComponent(Graphics g) {
        g.setColor(Color.black);
        super.paintComponent(g);
    }
}
