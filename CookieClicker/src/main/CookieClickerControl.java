package main;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.Timer;
import java.util.TimerTask;

public class CookieClickerControl implements ActionListener, WindowListener, KeyEventDispatcher {

    private CookieClickerView view;
    private CookieClickerButton button;
    private CookieClickerCookieBar bar;
    private File saveFile;
    private Timer t = new Timer();
    private TimerTask autoClicker;
    private final TimerTask AUTO_CLICKER = new TimerTask() {
        @Override
        public void run() {
            button.doClick();
        }
    };
    private JFrame loadSave;

    public CookieClickerControl(){
        this.button = new CookieClickerButton("Click");
        this.bar = new CookieClickerCookieBar(SwingConstants.HORIZONTAL);
        this.view = new CookieClickerView(this.button,this.bar,this);

        KeyboardFocusManager.getCurrentKeyboardFocusManager().addKeyEventDispatcher(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == this.button && !this.button.isTest()){
            this.view.addCookies(1);
        }
        while (this.bar.getValue() >= this.bar.getMaximum()){
            this.view.levelUp();
        }
        if (e.getSource() instanceof UpgradeButton){
            this.view.upgradeButtonClicked((UpgradeButton) e.getSource());
        }
        if (e.getSource() instanceof JFileChooser){
            if (e.getActionCommand().equals("CancelSelection")) {
                loadSave.setVisible(false);
            } else if (e.getActionCommand().equals("ApproveSelection")) {
                this.saveFile = ((JFileChooser)e.getSource()).getSelectedFile();
                this.view.processSaveFile(this.saveFile);
                loadSave.setVisible(false);
            }
        }
    }



    public static void main(String[] args) {
        CookieClickerControl c = new CookieClickerControl();
    }

    @Override
    public void windowOpened(WindowEvent e) {
        loadSave();
    }

    private void loadSave() {
        this.loadSave = new JFrame("load save");
        JFileChooser chooser = new JFileChooser("./CookieClicker/resources/");
        loadSave.add(chooser);
        loadSave.pack();
        loadSave.setVisible(true);

        chooser.addActionListener(this);
    }

    @Override
    public void windowClosing(WindowEvent e) {
        this.save();
    }

    private void save() {
        try {
            FileWriter fileWriter = new FileWriter(this.saveFile);
            fileWriter.write("{\n"+this.view.toString()+"\n}");
            fileWriter.close();
        } catch (Exception ignored) {}
    }

    @Override
    public void windowClosed(WindowEvent e) {

    }

    @Override
    public void windowIconified(WindowEvent e) {

    }

    @Override
    public void windowDeiconified(WindowEvent e) {

    }

    @Override
    public void windowActivated(WindowEvent e) {

    }

    @Override
    public void windowDeactivated(WindowEvent e) {

    }

    public void addAutoClicker(int level) {
        t.cancel();
        t = new Timer();
        autoClicker = new TimerTask() {
            @Override
            public void run() {
                AUTO_CLICKER.run();
            }
        };
        t.scheduleAtFixedRate(autoClicker,0,10000/level);
    }

    @Override
    public boolean dispatchKeyEvent(KeyEvent e) {
        if(KeyStroke.getKeyStrokeForEvent(e)==KeyStroke.getKeyStroke('a')){
            this.bar.setValue(this.bar.getMaximum());
        }
        return false;
    }
}
