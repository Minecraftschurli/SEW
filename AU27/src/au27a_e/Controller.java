package au27a_e;

import javax.swing.*;
import javax.swing.colorchooser.ColorSelectionModel;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Controller implements ActionListener, ChangeListener {
    private Model m;
    private AnzeigeFenster af;
    private ControlWindow sf;

    public Controller() {
        this.m = new Model();
        this.af = new AnzeigeFenster(this.m, this);
        this.sf = new ControlWindow(this.m, this);

        this.sf.setLocationRelativeTo(this.af);
        this.sf.setLocation(this.af.getWidth() - 15, 0);
        this.af.setLocation(0, 0);

        this.sf.init();
    }

    public void actionPerformed(ActionEvent e) {
        //System.out.println("" + e.getSource());

        this.m.setFilled(this.sf.isFilledSelected());
        this.m.setShaded(this.sf.isShadeSelected());

        if (e.getSource() instanceof JRadioButton) {
            this.m.setShape(this.sf.getShapeChosen((JRadioButton) e.getSource()));
        } else if (e.getSource() instanceof JButton) {
            JButton b = (JButton) e.getSource();
            if (this.sf.isPosUp(b)) {
                this.m.moveObj(0, -10);
            }
            if (this.sf.isPosDown(b)) {
                this.m.moveObj(0, 10);
                System.out.println(this.sf.getSize().toString());
            }
            if (this.sf.isPosLeft(b)) {
                this.m.moveObj(-10, 0);
            }
            if (this.sf.isPosRight(b)) {
                this.m.moveObj(10, 0);
            }
        } else if (e.getSource() instanceof JCheckBoxMenuItem) {
            JCheckBoxMenuItem i = (JCheckBoxMenuItem) e.getSource();
            this.sf.items.get(i).setVisible(i.getState());
        }
        this.af.refresh();
        this.sf.refresh();
    }

    @Override
    public void stateChanged(ChangeEvent e) {
        if (e.getSource() instanceof JSlider) {
            this.m.setSizePercent(((JSlider) e.getSource()).getValue());
        } else if (e.getSource() instanceof ColorSelectionModel) {
            this.m.setObjColor(((ColorSelectionModel) e.getSource()).getSelectedColor());
        }
        this.af.refresh();
    }
}