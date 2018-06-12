package au27a_e;


import javax.swing.*;
import javax.swing.border.TitledBorder;
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
    private JCheckBox shaded;
    private JSlider sizeSlider;
    private JSlider rotationSlider;
    private JButton addButton;
    private JButton removeButton;
    private JButton saveButton;
    private JButton loadButton;
    private JButton zUp;
    private JButton zDown;
    private JColorChooser color;
    private ButtonGroup shape;
    private JPanel styleGroup;
    private JPanel shapeGroup;
    private JPanel rotationGroup;
    private JPanel sizeGroup;
    private JPanel colorGroup;
    private JPanel miscGroup;
    private JPanel zGroup;

    private JCheckBoxMenuItem styleMenu;
    private JCheckBoxMenuItem shapeMenu;
    private JCheckBoxMenuItem sizeMenu;
    private JCheckBoxMenuItem rotationMenu;
    private JCheckBoxMenuItem colorMenu;
    private JCheckBoxMenuItem zMenu;
    private JCheckBoxMenuItem miscMenu;

    public ControlWindow(Model m, Controller c) {
        this.m = m;
        this.c = c;

        this.setTitle("Control-Window");
        this.setSize(850, 150);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        this.menuBar = new JMenuBar();
        this.viewMenu = new JMenu("View");

        this.styleGroup = new JPanel();
        this.shapeGroup = new JPanel();
        this.shapeGroup.setLayout(new GridLayout(0, 5));
        this.rotationGroup = new JPanel();
        this.sizeGroup = new JPanel();
        this.colorGroup = new JPanel();
        this.miscGroup = new JPanel();
        this.zGroup = new JPanel();
        this.miscGroup.setLayout(new GridLayout(2, 2, 0, 5));
        this.styleGroup.setBorder(BorderFactory.createTitledBorder(null, "Style", TitledBorder.CENTER, TitledBorder.DEFAULT_POSITION));
        this.shapeGroup.setBorder(BorderFactory.createTitledBorder(null, "Shape", TitledBorder.CENTER, TitledBorder.DEFAULT_POSITION));
        this.rotationGroup.setBorder(BorderFactory.createTitledBorder(null, "Rotation", TitledBorder.CENTER, TitledBorder.DEFAULT_POSITION));
        this.sizeGroup.setBorder(BorderFactory.createTitledBorder(null, "Size", TitledBorder.CENTER, TitledBorder.DEFAULT_POSITION));
        this.colorGroup.setBorder(BorderFactory.createTitledBorder(null, "Color", TitledBorder.CENTER, TitledBorder.DEFAULT_POSITION));
        this.miscGroup.setBorder(BorderFactory.createTitledBorder(null, "Misc", TitledBorder.CENTER, TitledBorder.DEFAULT_POSITION));
        this.zGroup.setBorder(BorderFactory.createTitledBorder(null, "Z Index", TitledBorder.CENTER, TitledBorder.DEFAULT_POSITION));

        this.shapeMenu = new JCheckBoxMenuItem("Shape");
        this.styleMenu = new JCheckBoxMenuItem("Style");
        this.rotationMenu = new JCheckBoxMenuItem("Rotation");
        this.sizeMenu = new JCheckBoxMenuItem("Size");
        this.colorMenu = new JCheckBoxMenuItem("Color");
        this.zMenu = new JCheckBoxMenuItem("Z Index");
        this.miscMenu = new JCheckBoxMenuItem("Misc");

        items.put(this.colorMenu, this.colorGroup);
        items.put(this.sizeMenu, this.sizeGroup);
        items.put(this.rotationMenu, this.rotationGroup);
        items.put(this.styleMenu, this.styleGroup);
        items.put(this.shapeMenu, this.shapeGroup);
        items.put(this.zMenu, this.zGroup);
        items.put(this.miscMenu, this.miscGroup);

        this.filled = new JCheckBox("filled");
        this.shaded = new JCheckBox("shaded");

        this.addButton = new JButton("add");
        this.removeButton = new JButton("remove");

        this.saveButton = new JButton("save");
        this.loadButton = new JButton("load");

        this.zUp = new JButton("Up");
        this.zDown = new JButton("Down");

        this.sizeSlider = new JSlider(JSlider.HORIZONTAL);
        this.rotationSlider = new JSlider(JSlider.HORIZONTAL, 0, 360, 0);

        this.color = new JColorChooser(this.m.getObjColor());
        this.color.removeChooserPanel(this.color.getChooserPanels()[0]);
        this.color.removeChooserPanel(this.color.getChooserPanels()[3]);
        this.color.setPreviewPanel(new JPanel());

        this.shape = new ButtonGroup();

        this.setLayout(new FlowLayout(FlowLayout.CENTER));

        for (Model.Shape s : Model.Shape.values()) {
            if (s == Model.Shape.EMPTY) continue;
            JRadioButton tmp = new JRadioButton(s.name());
            this.shape.add(tmp);
        }

        this.styleGroup.add(this.filled);
        this.styleGroup.add(this.shaded);

        this.rotationGroup.add(this.rotationSlider);

        this.sizeGroup.add(this.sizeSlider);

        this.colorGroup.add(this.color);

        this.miscGroup.add(this.addButton);
        this.miscGroup.add(this.removeButton);
        this.miscGroup.add(this.saveButton);
        this.miscGroup.add(this.loadButton);

        this.zGroup.add(this.zUp);
        this.zGroup.add(this.zDown);

        Enumeration<AbstractButton> radioButtons = this.shape.getElements();
        while (radioButtons.hasMoreElements()) {
            AbstractButton b = radioButtons.nextElement();
            this.shapeGroup.add(b);
            b.addActionListener(this.c);
        }

        Container cont = new Container();
        cont.setLayout(new GridLayout(2, 1, 5, 0));
        cont.add(this.zGroup);
        cont.add(this.miscGroup);

        this.add(this.shapeGroup);
        this.add(this.styleGroup);
        this.add(this.rotationGroup);
        this.add(this.sizeGroup);
        this.add(this.colorGroup);
        this.add(cont);

        items.forEach((jCheckBoxMenuItem, jPanel) -> {
            viewMenu.add(jCheckBoxMenuItem);
            jCheckBoxMenuItem.addActionListener(c);
            jCheckBoxMenuItem.setState(true);
            jPanel.setVisible(jCheckBoxMenuItem.getState());
        });
        this.menuBar.add(this.viewMenu);

        this.setJMenuBar(this.menuBar);

        this.filled.addActionListener(this.c);
        this.shaded.addActionListener(this.c);
        this.sizeSlider.addChangeListener(this.c);
        this.rotationSlider.addChangeListener(this.c);
        this.addButton.addActionListener(this.c);
        this.removeButton.addActionListener(this.c);
        this.saveButton.addActionListener(this.c);
        this.loadButton.addActionListener(this.c);
        this.zDown.addActionListener(this.c);
        this.zUp.addActionListener(this.c);

        this.color.getSelectionModel().addChangeListener(this.c);

        this.setResizable(false);

        this.setVisible(true);
    }

    public boolean isFilledSelected() {
        return this.filled.isSelected();
    }

    public boolean isShadeSelected() {
        return this.shaded.isSelected();
    }

    public Model.Shape getShapeChosen(JRadioButton b) {
        return Model.Shape.parseShape(b.getText());
    }

    public void init() {
        this.c.actionPerformed(new ActionEvent(this.shape.getElements().nextElement(), 0, null));
    }

    public boolean isAddButton(JButton source) {
        return source == addButton;
    }

    public boolean isRemoveButton(JButton source) {
        return source == removeButton;
    }

    public boolean isSaveButton(JButton source) {
        return source == saveButton;
    }

    public void refresh() {
        int width = 30, height, widthX = 650;
        if (this.colorMenu.getState()) height = 410;
        else height = 160;
        if (this.rotationGroup.isVisible()) width += this.rotationGroup.getWidth();
        if (this.shapeGroup.isVisible()) width += this.shapeGroup.getWidth();
        if (this.sizeGroup.isVisible()) width += this.sizeGroup.getWidth();
        if (this.styleGroup.isVisible()) width += this.styleGroup.getWidth();
        if (this.miscGroup.isVisible()) {
            if (!this.colorGroup.isVisible()) {
                height += this.miscGroup.getHeight();
            } else {
                width += this.miscGroup.getWidth();
            }
            widthX += this.miscGroup.getWidth();
        }
        this.setSize(this.getWidth(), height);
        if (this.colorGroup.isVisible()) width = Math.max(width, widthX);
        else this.setSize(this.getWidth(), height + 60);
        this.setSize(width, this.getHeight());
        if (!this.rotationGroup.isVisible() && !this.shapeGroup.isVisible() && !this.sizeGroup.isVisible() && !this.styleGroup.isVisible() && !this.zGroup.isVisible() && this.colorGroup.isVisible()) {
            this.setSize(getWidth(), getHeight() - 60);
        }

        if (!this.rotationGroup.isVisible() && !this.shapeGroup.isVisible() && !this.sizeGroup.isVisible() && !this.styleGroup.isVisible() && this.zGroup.isVisible() && !this.colorGroup.isVisible()) {
            this.setSize(getWidth(), getHeight() - 60);
        }

        if (this.m.getSelected() != null) {
            this.color.setColor(this.m.getObjColor());
            this.sizeSlider.setValue(this.m.getSizePercent());
            this.rotationSlider.setValue(this.m.getRotation());
            this.filled.setSelected(this.m.isFilled());
            this.shaded.setSelected(this.m.isShaded());
            Enumeration<AbstractButton> e = this.shape.getElements();
            while (e.hasMoreElements()) {
                AbstractButton b = e.nextElement();
                b.setSelected(this.m.getShape() == Model.Shape.parseShape(b.getText()));
            }
        }

        this.repaint();
    }

    public boolean isZUpButton(JButton source) {
        return source == zUp;
    }

    public boolean isZDownButton(JButton source) {
        return source == zDown;
    }

    public boolean isLoadButton(JButton source) {
        return source == loadButton;
    }

    public boolean isRotationSlider(JSlider source) {
        return source == rotationSlider;
    }

    public boolean isSizeSlider(JSlider source) {
        return source == sizeSlider;
    }
}