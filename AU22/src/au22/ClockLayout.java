package au22;


import javax.swing.*;
import java.awt.*;

public class ClockLayout extends JFrame {

    private JButton buttonClose;
    private JButton buttonRedraw;
    private JButton buttonHourPlus;
    private JButton buttonHourMinus;
    private JButton buttonMinutePlus;
    private JButton buttonMinuteMinus;
    private Container buttonGroupEast;
    private Container buttonGroupWest;
    private ClockControl control;
    private ClockPanel panel;

    public ClockLayout(ClockPanel panel, ClockControl control){
        this.panel = panel;
        this.control = control;

        this.buttonGroupEast = new Container();
        this.buttonGroupWest = new Container();
        this.buttonClose = new JButton("Close");
        this.buttonRedraw = new JButton("Redraw");
        this.buttonHourPlus = new JButton("Hour++");
        this.buttonHourMinus = new JButton("Hour--");
        this.buttonMinutePlus = new JButton("Minute++");
        this.buttonMinuteMinus = new JButton("Minute--");

        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setLayout(new BorderLayout());
        this.buttonGroupWest.setLayout(new GridLayout(2,1));
        this.buttonGroupEast.setLayout(new GridLayout(2,1));

        this.buttonGroupWest.add(this.buttonHourPlus);
        this.buttonGroupWest.add(this.buttonMinutePlus);

        this.buttonGroupEast.add(this.buttonHourMinus);
        this.buttonGroupEast.add(this.buttonMinuteMinus);

        this.add(this.buttonRedraw,BorderLayout.NORTH);
        this.add(this.panel,BorderLayout.CENTER);
        this.add(this.buttonGroupWest,BorderLayout.WEST);
        this.add(this.buttonGroupEast,BorderLayout.EAST);
        this.add(this.buttonClose,BorderLayout.SOUTH);

        this.setMinimumSize(new Dimension(400,320));
        this.setVisible(true);

        this.buttonRedraw.addActionListener(this.control);
        this.buttonClose.addActionListener(this.control);
        this.buttonHourPlus.addActionListener(this.control);
        this.buttonMinutePlus.addActionListener(this.control);
        this.buttonHourMinus.addActionListener(this.control);
        this.buttonMinuteMinus.addActionListener(this.control);
    }

    public boolean isButtonClose(Object o){
        return o == this.buttonClose;
    }

    public boolean isButtonRedraw(Object o){
        return o == this.buttonRedraw;
    }

    public boolean isButtonHourPlus(Object o){
        return o == this.buttonHourPlus;
    }

    public boolean isButtonHourMinus(Object o){
        return o == this.buttonHourMinus;
    }

    public boolean isButtonMinutePlus(Object o){
        return o == this.buttonMinutePlus;
    }

    public boolean isButtonMinuteMinus(Object o){
        return o == this.buttonMinuteMinus;
    }

}
