package au22;

import api.gui.GUIHelper;

import javax.swing.*;
import java.awt.*;

@SuppressWarnings({"WeakerAccess", "FieldCanBeLocal"})
public class ClockView extends JPanel {

    JFrame edit;
    private JButton buttonEditOK;
    private JButton buttonClose;
    private JButton buttonRedraw;
    private JButton buttonHPlus;
    private JButton buttonHMinus;
    private JButton buttonMPlus;
    private JButton buttonMMinus;
    private Container buttonGroupEast;
    private Container buttonGroupWest;
    private ClockControl control;
    private ClockModel model;

    public ClockView(ClockModel model, ClockControl control){
        this.model = model;
        this.control = control;

        this.edit = new JFrame("edit");
        this.edit.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
        this.edit.setLayout(new BorderLayout());
        this.edit.pack();
        this.edit.setLocationRelativeTo(this);
        this.edit.setMinimumSize(new Dimension(185,200));
        this.edit.setLocation(this.getX()-this.getWidth(),this.getY());

        this.buttonGroupEast = new Container();
        this.buttonGroupWest = new Container();
        this.buttonClose = new JButton("Close");
        this.buttonRedraw = new JButton("Redraw");
        this.buttonHPlus = new JButton("Hour++");
        this.buttonHMinus = new JButton("Hour--");
        this.buttonMPlus = new JButton("Minute++");
        this.buttonMMinus = new JButton("Minute--");
        this.buttonEditOK = new JButton("OK");

        this.setLayout(new BorderLayout());
        this.buttonGroupWest.setLayout(new GridLayout(2,1));
        this.buttonGroupEast.setLayout(new GridLayout(2,1));

        this.buttonGroupWest.add(this.buttonHPlus);
        this.buttonGroupWest.add(this.buttonMPlus);

        this.buttonGroupEast.add(this.buttonHMinus);
        this.buttonGroupEast.add(this.buttonMMinus);

        this.edit.add(this.buttonGroupEast,BorderLayout.EAST);
        this.edit.add(this.buttonGroupWest,BorderLayout.WEST);
        this.edit.add(this.buttonEditOK,BorderLayout.SOUTH);

        //this.add(this.buttonRedraw,BorderLayout.NORTH);
        this.add(this.model,BorderLayout.CENTER);
        //this.add(this.buttonGroupWest,BorderLayout.WEST);
        //this.add(this.buttonGroupEast,BorderLayout.EAST);
        this.add(this.buttonClose,BorderLayout.SOUTH);

        this.setMinimumSize(new Dimension(400,320));
        this.setVisible(true);

        this.buttonRedraw.addActionListener(this.control);
        this.buttonClose.addActionListener(this.control);
        this.buttonHPlus.addActionListener(this.control);
        this.buttonMPlus.addActionListener(this.control);
        this.buttonHMinus.addActionListener(this.control);
        this.buttonMMinus.addActionListener(this.control);
        this.buttonEditOK.addActionListener(this.control);


        this.model.setPaintColor(GUIHelper.getInverseColor(this.model.getBackground()));
    }

    public void refresh(){
        this.model.repaint();
    }

    public boolean isButtonClose(Object o){
        return o == this.buttonClose;
    }

    public boolean isButtonRedraw(Object o){
        return o == this.buttonRedraw;
    }

    public boolean isButtonHourPlus(Object o){
        return o == this.buttonHPlus;
    }

    public boolean isButtonHourMinus(Object o){
        return o == this.buttonHMinus;
    }

    public boolean isButtonMinutePlus(Object o){
        return o == this.buttonMPlus;
    }

    public boolean isButtonMinuteMinus(Object o){
        return o == this.buttonMMinus;
    }

    public boolean isButtonEditOK(Object o){
        return o == this.buttonEditOK;
    }

    public void realClock() {
        this.remove(this.buttonGroupEast);
        this.remove(this.buttonGroupWest);
        this.remove(this.buttonRedraw);
    }

    public void setDarkMode(boolean darkMode){
        this.model.setBackground(darkMode?Color.black:Color.white);
    }

    public void setCustomColor(Color c){
        this.model.setPaintColor(c);
    }
}
