package au27a_e;

import api.gui.GUIHelper;
import api.gui.MouseListener;

import javax.swing.*;
import javax.swing.colorchooser.ColorSelectionModel;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.geom.Ellipse2D;
import java.util.concurrent.atomic.AtomicReference;

import static api.FileHelper.readFile;
import static api.FileHelper.saveToFile;
import static au27a_e.Model.calcPolygon;

public class Controller implements ActionListener, ChangeListener, MouseListener, KeyEventDispatcher {
    private Model m;
    private AnzeigeFenster af;
    private ControlWindow sf;
    private final AtomicReference<Color> old = new AtomicReference<>();
    private boolean drag;
    private Point dragStart;
    private boolean flag1;
    private boolean flag = false;

    public Controller() {
        this.m = new Model();
        this.af = new AnzeigeFenster(this.m, this);
        this.sf = new ControlWindow(this.m, this);

        this.sf.setLocationRelativeTo(this.af);
        this.sf.setLocation(this.af.getWidth() - 15, 0);
        this.af.setLocation(0, 0);

        this.sf.init();

        KeyboardFocusManager.getCurrentKeyboardFocusManager().addKeyEventDispatcher(this);
    }

    public void actionPerformed(ActionEvent e) {
        //System.out.println("" + e.getSource());

        if (this.m.getSelected() != null) {
            this.m.setFilled(this.sf.isFilledSelected());
            this.m.setShaded(this.sf.isShadeSelected());
        }

        if (e.getSource() instanceof JRadioButton) {
            if (this.m.getSelected() != null) this.m.setShape(this.sf.getShapeChosen((JRadioButton) e.getSource()));
        } else if (e.getSource() instanceof JButton) {
            JButton b = (JButton) e.getSource();
            if (this.sf.isAddButton(b)) {
                this.m.addObj(new Model.GeomObj());
            }
            if (this.sf.isRemoveButton(b)) {
                if (this.m.getSelected() != null) this.m.removeObj();
            }
            if (this.sf.isSaveButton(b)) {
                String fileName = "img";
                saveToFile(this.m.toString(), "C:/Users/georg/OneDrive/Desktop/", fileName + ".txt");
            }
            if (this.sf.isLoadButton(b)) {
                String fileName = "img";
                String content = readFile("C:/Users/georg/OneDrive/Desktop/", fileName + ".txt");
                String[] lines;
                lines = content.split("\n");
                String[][] fields = new String[lines.length][];
                for (int i = 0; i < lines.length; i++) {
                    fields[i] = lines[i].split(",");
                }
                this.m.loadFromFields(fields);
            }
            if (this.sf.isZUpButton(b)) {
                this.m.moveZUp();
            }
            if (this.sf.isZDownButton(b)) {
                this.m.moveZDown();
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
        if (this.m.getSelected() != null) {
            if (e.getSource() instanceof JSlider) {
                if (this.sf.isRotationSlider((JSlider) e.getSource())) {
                    this.m.setRotation(((JSlider) e.getSource()).getValue());
                } else if (this.sf.isSizeSlider((JSlider) e.getSource())) {
                    this.m.setSizePercent(((JSlider) e.getSource()).getValue());
                }
            } else if (e.getSource() instanceof ColorSelectionModel) {
                this.m.setObjColor(((ColorSelectionModel) e.getSource()).getSelectedColor());
            }
        }
        this.af.refresh();
        this.sf.refresh();
    }

    @Override
    public void mouseClicked(MouseEvent e) {

    }

    @Override
    public void mousePressed(MouseEvent e) {
        Point mousePos = e.getPoint();
        flag1 = true;
        for (int i = this.m.objCount() - 1; i >= 0; i--) {
            Model.GeomObj geomObj = this.m.get(i);
            Shape shape;
            Point pos = geomObj.getPos();
            Dimension size = geomObj.getObjSize();
            switch (geomObj.getShape()) {
                case TRIANGLE:
                case PENTAGON:
                case HEXAGON:
                case HEPTAGON:
                case OCTAGON:
                case NONAGON:
                case DODECAGON:
                    shape = calcPolygon(geomObj.getShape().polygonSides, size.width / 2.0, pos, geomObj.getRotation());
                    break;
                case CIRCLE:
                    shape = new Ellipse2D.Double(pos.getX(), pos.getY(), size.getWidth(), size.getHeight());
                    break;
                case SQUARE:
                default:
                    shape = new Rectangle(pos, size);
            }
            if (shape.contains(mousePos) && flag1) {
                flag1 = false;
                this.m.setSelected(i);
                old.set(this.m.getObjColor());
                this.drag = true;
                this.m.setObjColor(GUIHelper.brighter(old.get(), 0.2F));
                this.dragStart = new Point(mousePos);
            }
        }
        if (e.getClickCount() == 2 && this.m.getSelected().hasEasteregg()) {
            this.m.getSelected().easteregg();
        }
        this.sf.refresh();
        this.af.refresh();
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        if (drag) {
            drag = false;
            this.m.setObjColor(old.get());
        }
        this.sf.refresh();
        this.af.refresh();
    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {
        if (drag) {
            drag = false;
            this.m.setObjColor(old.get());
        }
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        if (drag) {
            Point p = e.getPoint();
            p.translate(-dragStart.x, -dragStart.y);
            this.m.moveObj(p.x, p.y);
            this.af.refresh();
            dragStart = e.getPoint();
        }
    }

    @Override
    public void mouseMoved(MouseEvent e) {

    }

    @Override
    public boolean dispatchKeyEvent(KeyEvent e) {
        if (flag && e.paramString().contains("KEY_PRESSED") && e.getKeyChar() == '\u007f') {
            flag = false;
            this.m.removeObj();
        } else if (e.paramString().contains("KEY_RELEASED") && e.getKeyChar() == '\u007f') {
            flag = true;
        }
        if (e.paramString().contains("KEY_PRESSED")) {
            if (e.getKeyCode() == 37) {
                this.m.moveObj(-1, 0);
            }
            if (e.getKeyCode() == 38) {
                this.m.moveObj(0, -1);
            }
            if (e.getKeyCode() == 39) {
                this.m.moveObj(1, 0);
            }
            if (e.getKeyCode() == 40) {
                this.m.moveObj(0, 1);
            }
        }
        this.af.refresh();
        return false;
    }
}