package template;


import javax.swing.*;
import java.awt.*;

public class SteuerFenster extends JFrame {
    private Model m;
    private Controller c;
    private JCheckBox gefuellt;
    private JCheckBox schattig;

    public SteuerFenster(Model m, Controller c) {
        this.m = m;
        this.c = c;

        this.setTitle("Steuer-Fenster");
        this.setSize(300, 300);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        this.gefuellt = new JCheckBox("gefüllt");
        this.schattig = new JCheckBox("schattig");

        this.setLayout(new FlowLayout());

        this.add(this.gefuellt);
        this.add(this.schattig);

        this.gefuellt.addActionListener(this.c);
        this.schattig.addActionListener(this.c);

        this.setVisible(true);
    }

    public boolean istGefuelltSelected() {
        return this.gefuellt.isSelected();
    }

    public boolean istSchattigSelected() {
        return this.schattig.isSelected();
    }
}