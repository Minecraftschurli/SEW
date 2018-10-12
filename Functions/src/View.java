import javax.swing.*;
import javax.swing.border.TitledBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;

public class View extends JFrame implements ChangeListener {

    private Model m;
    private JSlider sA, sB, sC;

    public View(final String str) {
        // a*((x+c)^n)+b
        // a*(x^2)+b*x+c
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        this.m = new Model(str, 5);
        JPanel c = new JPanel();
        c.setLayout(new GridLayout(1, 0));
        sA = new JSlider(JSlider.HORIZONTAL, -100, 100, 0);
        sB = new JSlider(JSlider.HORIZONTAL, -100, 100, 0);
        sC = new JSlider(JSlider.HORIZONTAL, -100, 100, 0);
        sA.addChangeListener(this);
        sB.addChangeListener(this);
        sC.addChangeListener(this);
        sA.setBorder(BorderFactory.createTitledBorder(null, "a", TitledBorder.TOP, TitledBorder.CENTER));
        sB.setBorder(BorderFactory.createTitledBorder(null, "b", TitledBorder.TOP, TitledBorder.CENTER));
        sC.setBorder(BorderFactory.createTitledBorder(null, "c", TitledBorder.TOP, TitledBorder.CENTER));
        c.add(sA);
        c.add(sB);
        c.add(sC);
        this.add(c, BorderLayout.NORTH);
        this.add(m, BorderLayout.CENTER);
        this.setMinimumSize(new Dimension(800, 600));
        this.setVisible(true);
    }

    public static void main(String[] args) {
        new View(JOptionPane.showInputDialog("give a function"));
    }

    @Override
    public void stateChanged(ChangeEvent e) {
        double v = sA.getValue() * 5 / 100.0;
        double d = Math.pow(v, 3);
        m.setA(-d);
        m.setB(sB.getValue());
        m.setC(sC.getValue());
        this.repaint();
    }
}
