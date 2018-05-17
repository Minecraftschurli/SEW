package au22;


import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Timer;
import java.util.TimerTask;

public class ClockControl extends AbstractAction implements ActionListener, KeyEventDispatcher {

    private ClockModel model;
    private ClockView view;
    private final Timer t = new Timer();

    public ClockControl(boolean b,boolean dark) {
        this.model = new ClockModel();
        this.view = new ClockView(this.model,this);
        if (!b){
            this.view.realClock();
            this.model.setInitialTime(System.currentTimeMillis()/1000,2);
            t.scheduleAtFixedRate(new TimerTask() {
                @Override
                public void run() {
                    model.addSeconds(1);
                    view.refresh();
                }
            },1000,1000);
        }
        this.view.setDarkMode(dark);

        KeyboardFocusManager.getCurrentKeyboardFocusManager().addKeyEventDispatcher(this);
        //this.view.setCustomColor(new Color(0xC6DE03));
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (this.view.isButtonRedraw(e.getSource())){
            this.model.calculateRandomTime();
        }
        if (this.view.isButtonHourPlus(e.getSource())){
            this.model.addHour(1);
        }
        if (this.view.isButtonHourMinus(e.getSource())){
            this.model.addHour(-1);
        }
        if (this.view.isButtonMinutePlus(e.getSource())){
            this.model.addMinute(1);
        }
        if (this.view.isButtonMinuteMinus(e.getSource())){
            this.model.addMinute(-1);
        }
        if (this.view.isButtonClose(e.getSource())){
            System.exit(0);
        }
        if (this.view.isButtonEditOK(e.getSource())){
            this.view.edit.setVisible(false);
            this.model.setIsAlert(false);
        }
        this.model.repaint();
    }

    public static void main(String[] args) {
        boolean random = false,dark = false;
        if (args.length==1&&args[0]!=null){
            if (args[0].equals("random")){
                random = true;
            }
            if (args[0].equals("dark")){
                dark = true;
            }
        }
        if (args.length==2&&args[0]!=null&&args[1]!=null){
            if (args[0].equals("random")||args[1].equals("random")){
                random = true;
            }
            if (args[0].equals("dark")||args[1].equals("dark")){
                dark = true;
            }
        }
        @SuppressWarnings("unused") ClockControl c = new ClockControl(random,dark);
    }

    private void openEditWindow() {
        //this.view.setVisible(false);
        this.view.edit.setVisible(true);
        this.model.setIsAlert(true);
    }


    @Override
    public boolean dispatchKeyEvent(KeyEvent e) {
        if (e.getID() == KeyEvent.KEY_PRESSED) {
            if (KeyStroke.getKeyStrokeForEvent(e) == KeyStroke.getKeyStroke(69,InputEvent.CTRL_DOWN_MASK)){
                openEditWindow();
            }
        }
        return false;
    }
}
