package template;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Controller implements ActionListener {
    private Model m;
    private AnzeigeFenster af;
    private SteuerFenster sf;

    public Controller() {
        this.m = new Model();
        this.af = new AnzeigeFenster(this.m, this);
        this.sf = new SteuerFenster(this.m, this);
    }

    public void actionPerformed(ActionEvent e) {
        //System.out.println("" + e.getSource());

        if (this.sf.istGefuelltSelected()) {
            this.m.setGefuellt(true);
        } else {
            this.m.setGefuellt(false);
        }
        this.af.repaint();
    }
}