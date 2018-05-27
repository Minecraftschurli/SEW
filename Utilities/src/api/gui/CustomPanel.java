package api.gui;

import javax.swing.*;
import java.awt.*;

public class CustomPanel extends JPanel {

    private IComps comps;

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        this.comps.draw(g,this);
    }

    public void setComps(IComps c){
        this.comps = c;
    }

    public interface IComps {
        void draw(Graphics g, CustomPanel panel);
    }
}
