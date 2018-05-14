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
            this.panel.repaint();
        }
        if (this.layout.isButtonClose(e.getSource())){
            System.exit(0);
        }
    }

    public static void main(String[] args) {
        ClockControl c = new ClockControl();
    }
}
