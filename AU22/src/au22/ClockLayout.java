package au22;


import javax.swing.*;
import java.awt.*;

public class ClockLayout extends JFrame {

    private JButton buttonClose;
    private JButton buttonRedraw;
    private Container buttonGroup;
    private ClockControl control;
    private ClockPanel panel;

    public ClockLayout(ClockPanel panel, ClockControl control){
        this.panel = panel;
        this.control = control;

        this.buttonGroup = new Container();
        this.buttonClose = new JButton("Close");
        this.buttonRedraw = new JButton("Redraw");

        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setLayout(new BorderLayout());
        this.buttonGroup.setLayout(new FlowLayout());

        this.buttonGroup.add(this.buttonRedraw);
        this.buttonGroup.add(this.buttonClose);

        this.add(this.panel,BorderLayout.CENTER);
        this.add(this.buttonGroup,BorderLayout.SOUTH);

        this.setMinimumSize(new Dimension(300,300));
        this.setVisible(true);

        this.buttonRedraw.addActionListener(this.control);
        this.buttonClose.addActionListener(this.control);
    }

    public boolean isButtonClose(Object o){
        return o == this.buttonClose;
    }

    public boolean isButtonRedraw(Object o){
        return o == this.buttonRedraw;
    }

}
