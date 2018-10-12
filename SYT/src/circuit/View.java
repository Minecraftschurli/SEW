package circuit;

import javax.swing.*;

public class View extends JFrame {

    private final Controller c;
    private final Model m;
    private final CircuitPanel p;

    public View(Controller c, Model m) {
        this.c = c;
        this.m = m;
        this.p = new CircuitPanel(this.m);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.add(this.p);
        this.setVisible(true);
    }
}
