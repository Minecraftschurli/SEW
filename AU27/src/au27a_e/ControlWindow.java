package au27a_e;


import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.Enumeration;
import java.util.HashMap;

public class ControlWindow extends JFrame {

    public final HashMap<JCheckBoxMenuItem, JPanel> items = new HashMap<>();

    private final JMenuBar menuBar;
    private final JMenu viewMenu;
    private Model m;
    private Controller c;
    private JCheckBox filled;
    private JCheckBox shade;
    private JRadioButton shapeCircle;
    private JRadioButton shapeSquare;
    private JRadioButton shapeLine;
    private JSlider sizeSlider;
    private JButton posLeft;
    private JButton posRight;
    private JButton posUp;
    private JButton posDown;
    private JColorChooser color;
    private ButtonGroup shape;
    private JPanel styleGroup;
    private JPanel shapeGroup;
    private JPanel posGroup;
    private JPanel sizeGroup;
    private JPanel colorGroup;

    private JCheckBoxMenuItem styleMenu;
    private JCheckBoxMenuItem shapeMenu;
    private JCheckBoxMenuItem sizeMenu;
    private JCheckBoxMenuItem posMenu;
    private JCheckBoxMenuItem colorMenu;

    public ControlWindow(Model m, Controller c) {
        this.m = m;
        this.c = c;

        this.setTitle("Control-Window");
        this.setSize(850, 380);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        this.menuBar = new JMenuBar();
        this.viewMenu = new JMenu("View");

        this.styleGroup = new JPanel();
        this.shapeGroup = new JPanel();
        this.posGroup = new JPanel();
        this.sizeGroup = new JPanel();
        this.colorGroup = new JPanel();
        this.styleGroup.setBorder(BorderFactory.createTitledBorder("Style:"));
        this.shapeGroup.setBorder(BorderFactory.createTitledBorder("Shape:"));
        this.posGroup.setBorder(BorderFactory.createTitledBorder("Position:"));
        this.sizeGroup.setBorder(BorderFactory.createTitledBorder("Size:"));
        this.colorGroup.setBorder(BorderFactory.createTitledBorder("Color:"));

        this.shapeMenu = new JCheckBoxMenuItem("Shape");
        this.styleMenu = new JCheckBoxMenuItem("Style");
        this.posMenu = new JCheckBoxMenuItem("Pos");
        this.sizeMenu = new JCheckBoxMenuItem("Size");
        this.colorMenu = new JCheckBoxMenuItem("Color");

        items.put(this.colorMenu, this.colorGroup);
        items.put(this.sizeMenu, this.sizeGroup);
        items.put(this.posMenu, this.posGroup);
        items.put(this.styleMenu, this.styleGroup);
        items.put(this.shapeMenu, this.shapeGroup);

        this.shapeCircle = new JRadioButton("Circle");
        this.shapeSquare = new JRadioButton("Square");
        this.shapeLine = new JRadioButton("Line");

        this.filled = new JCheckBox("filled");
        this.shade = new JCheckBox("shaded");

        this.posLeft = new JButton("<<");
        this.posRight = new JButton(">>");
        this.posUp = new JButton("^^");
        this.posDown = new JButton("vv");

        this.sizeSlider = new JSlider(JSlider.HORIZONTAL);

        this.color = new JColorChooser(this.m.getObjColor());
        this.color.removeChooserPanel(this.color.getChooserPanels()[0]);
        this.color.removeChooserPanel(this.color.getChooserPanels()[1]);
        this.color.removeChooserPanel(this.color.getChooserPanels()[1]);
        this.color.removeChooserPanel(this.color.getChooserPanels()[1]);
        this.color.setPreviewPanel(new JPanel());

        this.shape = new ButtonGroup();

        this.setLayout(new FlowLayout(FlowLayout.CENTER));

        this.shape.add(this.shapeCircle);
        this.shape.add(this.shapeSquare);
        this.shape.add(this.shapeLine);

        this.styleGroup.add(this.filled);
        this.styleGroup.add(this.shade);

        this.posGroup.add(this.posLeft);
        this.posGroup.add(this.posRight);
        this.posGroup.add(this.posUp);
        this.posGroup.add(this.posDown);

        this.sizeGroup.add(this.sizeSlider);

        this.colorGroup.add(this.color);

        Enumeration<AbstractButton> radioButtons = this.shape.getElements();
        while (radioButtons.hasMoreElements()) {
            AbstractButton b = radioButtons.nextElement();
            this.shapeGroup.add(b);
            b.addActionListener(this.c);
        }

        this.add(this.shapeGroup);
        this.add(this.styleGroup);
        this.add(this.posGroup);
        this.add(this.sizeGroup);
        this.add(this.colorGroup);

        items.forEach((jCheckBoxMenuItem, jPanel) -> {
            viewMenu.add(jCheckBoxMenuItem);
            jCheckBoxMenuItem.addActionListener(c);
            jCheckBoxMenuItem.setState(true);
            jPanel.setVisible(jCheckBoxMenuItem.getState());
        });
        this.menuBar.add(this.viewMenu);

        this.setJMenuBar(this.menuBar);

        this.filled.addActionListener(this.c);
        this.shade.addActionListener(this.c);
        this.posLeft.addActionListener(this.c);
        this.posRight.addActionListener(this.c);
        this.posUp.addActionListener(this.c);
        this.posDown.addActionListener(this.c);
        this.sizeSlider.addChangeListener(this.c);

        this.color.getSelectionModel().addChangeListener(this.c);

        this.shape.setSelected(this.shapeCircle.getModel(), true);

        this.setVisible(true);
    }

    public boolean isFilledSelected() {
        return this.filled.isSelected();
    }

    public boolean isShadeSelected() {
        return this.shade.isSelected();
    }

    public int getShapeChosen(JRadioButton b) {
        if (b == shapeCircle) {
            return Model.SHAPE_CIRCLE;
        } else if (b == shapeSquare) {
            return Model.SHAPE_SQUARE;
        } else if (b == shapeLine) {
            return Model.SHAPE_LINE;
        } else {
            return Model.SHAPE_EMPTY;
        }
    }

    public void init() {
        this.c.actionPerformed(new ActionEvent(this.shapeCircle, 0, null));
    }

    public boolean isPosUp(JButton source) {
        return source == posUp;
    }

    public boolean isPosDown(JButton source) {
        return source == posDown;
    }

    public boolean isPosLeft(JButton source) {
        return source == posLeft;
    }

    public boolean isPosRight(JButton source) {
        return source == posRight;
    }
}