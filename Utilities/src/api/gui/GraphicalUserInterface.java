package api.gui;

import javax.swing.*;
import java.awt.*;

public class GraphicalUserInterface {

    private JFrame window;
    private JPanel innerWindow;

    public GraphicalUserInterface(String title,JPanel window,int defaultCloseOperation){
        this.window = new JFrame(title);
        this.window.setDefaultCloseOperation(defaultCloseOperation);
        this.window.setLayout(new GridLayout());
        this.innerWindow = window;
        this.window.add(this.innerWindow);
        this.window.pack();
        this.window.setVisible(true);
    }

    public void changeWindow(JPanel window) {
        this.window.remove(this.innerWindow);
        this.innerWindow = window;
        this.window.add(this.innerWindow);
        this.reload();
    }

    private void reload() {
        this.window.repaint();
    }
}
