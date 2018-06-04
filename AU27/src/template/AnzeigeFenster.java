package template;

import javax.swing.*;

public class AnzeigeFenster extends JFrame {
    private Model m;
    private Controller c;
    private AnzeigePanel ap;

    public AnzeigeFenster(Model m, Controller c) {
        this.m = m;
        this.c = c;

        this.setTitle("Anzeige-Fenster");
        this.setSize(400, 300);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        this.ap = new AnzeigePanel(this.m);
        this.add(this.ap);
        this.setVisible(true);
    }
}