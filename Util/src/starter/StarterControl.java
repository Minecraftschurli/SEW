package starter;

import api.TaskRunner;
import au22.ClockControl;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class StarterControl implements ActionListener {

    private StarterView view;

    public StarterControl() {
        this.view = new StarterView(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (this.view.isButtonOK(e.getSource())) {
            switch (this.view.getChosenOption()) {
                case "ClockStandard":
                    TaskRunner.addScheduledTask(() -> ClockControl.main(new String[]{}));
                    this.view.setVisible(false);
                    break;
                case "ClockDark":
                    TaskRunner.addScheduledTask(() -> ClockControl.main(new String[]{"dark"}));
                    this.view.setVisible(false);
                    break;
                case "ClockRandom":
                    TaskRunner.addScheduledTask(() -> ClockControl.main(new String[]{"random"}));
                    this.view.setVisible(false);
                    break;
                case "ClockRandomDark":
                    TaskRunner.addScheduledTask(() -> ClockControl.main(new String[]{"random","dark"}));
                    this.view.setVisible(false);
                    break;
                default:
                    System.out.println("nope");
            }
        }
        if (this.view.isButtonClose(e.getSource())){
            System.exit(0);
        }
    }

    public static void main(String[] args) {
        StarterControl c = new StarterControl();
    }
}
