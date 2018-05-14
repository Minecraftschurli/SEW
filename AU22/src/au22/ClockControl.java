package au22;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ClockControl implements ActionListener {

    private ClockPanel panel;
    private ClockLayout layout;

    public ClockControl(){
        this.panel = new ClockPanel();
        this.layout = new ClockLayout(this.panel,this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (this.layout.isButtonRedraw(e.getSource())){
            this.panel.calculateRandomTime();
        }
        if (this.layout.isButtonHourPlus(e.getSource())){
            this.panel.addHour(1);
        }
        if (this.layout.isButtonHourMinus(e.getSource())){
            this.panel.addHour(-1);
        }
        if (this.layout.isButtonMinutePlus(e.getSource())){
            this.panel.addMinute(1);
        }
        if (this.layout.isButtonMinuteMinus(e.getSource())){
            this.panel.addMinute(-1);
        }
        if (this.layout.isButtonClose(e.getSource())){
            System.exit(0);
        }
        this.panel.repaint();
    }

    public static void main(String[] args) {
        ClockControl c = new ClockControl();
    }
}
