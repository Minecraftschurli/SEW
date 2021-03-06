package main;

import api.encryption.EncryptedWriter;
import starter.App;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.FileWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Timer;
import java.util.TimerTask;

import static main.CookieClickerView.upgrades;

public class CookieClickerControl extends App implements ActionListener, WindowListener, KeyEventDispatcher {

    private CookieClickerView view;
    private CookieClickerButton button;
    private final TimerTask ADD_CpS = new TimerTask() {
        @Override
        public void run() {
            upgrades.forEachUpgrade(upgrade -> {
                if (upgrade instanceof CookieClickerUpgrade.CpSUpgrade) {
                    view.addCookies((long) (((CookieClickerUpgrade.CpSUpgrade) upgrade).getCpS()), 1);
                }
            });
        }
    };
    private File saveFile;
    private Timer t = new Timer();
    private TimerTask autoClicker;
    private final TimerTask AUTO_CLICKER = new TimerTask() {
        @Override
        public void run() {
            button.doClick();
        }
    };
    private CookieClickerCookieStats stats;
    private JFrame loadSave;
    private DebugWindow debugWindow;
    private boolean debug;

    public CookieClickerControl(){
        this.button = new CookieClickerButton("Click");
        this.stats = new CookieClickerCookieStats();
        this.view = new CookieClickerView(this.button, this.stats, this);

        this.debugWindow = new DebugWindow("Debug", this);

        KeyboardFocusManager.getCurrentKeyboardFocusManager().addKeyEventDispatcher(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == this.button && !this.button.isTest()){
            this.view.addCookies(1, upgrades.getLevelForName("Multiplier") + 1);
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
        if (e.getSource() == this.view.getMenu()) {
            openDebugWindow();
        }
    }

    private void openDebugWindow() {
        debugWindow.setVisible(true);
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
        e.getWindow().dispose();
    }

    private void save() {
        try {
            if (this.saveFile == null){
                for (int saveNumber = 0;;saveNumber++) {
                    if (!Files.exists(Paths.get("./CookieClicker/resources/save" + saveNumber + ".cookie"))) {
                        this.saveFile = new File("./CookieClicker/resources/save"+saveNumber+".cookie");
                        break;
                    }
                }
            }
            if (this.saveFile.getName().endsWith(".txt")){
                FileWriter fileWriter = new FileWriter(this.saveFile);
                fileWriter.write((master.getTitle() != null && !master.getTitle().equals("") ? ("Name: " + master.getTitle() + "\n") : "") + "[\n" + this.view.toString() + "\n]" + view.getAdminOverrideSection());
                fileWriter.close();
            } else if (this.saveFile.getName().endsWith(".cookie")) {
                EncryptedWriter fileWriter = new EncryptedWriter(this.saveFile, 201L);
                fileWriter.write((master.getTitle() != null && !master.getTitle().equals("") ? ("Name: " + master.getTitle() + "\n") : "") + "[\n" + this.view.toString() + "\n]" + view.getAdminOverrideSection());
                fileWriter.close();
            }
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
        TimerTask cps = new TimerTask() {
            @Override
            public void run() {
                ADD_CpS.run();
            }
        };
        t.scheduleAtFixedRate(cps, 0, 1000);
    }

    public void addCpsToTimer() {
        TimerTask cps = new TimerTask() {
            @Override
            public void run() {
                ADD_CpS.run();
            }
        };
        t.scheduleAtFixedRate(cps, 0, 1000);
    }

    @Override
    public boolean dispatchKeyEvent(KeyEvent e) {
        if (KeyStroke.getKeyStrokeForEvent(e) == KeyStroke.getKeyStroke('a') && debug) {
            this.stats.addCookies(100000L);
        }
        return false;
    }

    @Override
    public JPanel getView() {
        return this.view;
    }

    @Override
    public void onOpened(){
        loadSave();
        this.view.setVisible(true);
        this.master.pack();
        this.master.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        this.master.addWindowListener(this);
        this.master.getJMenuBar().add(this.view.getMenu());
        this.view.reload();
    }

    @Override
    public void onClosed() {
        this.view.setVisible(false);
        save();
        this.master.removeWindowListener(this);
        this.master.getJMenuBar().remove(this.view.getMenu());
    }

    public boolean isLoggedIn() {
        return this.debug;
    }

    public void setLoggedIn(boolean loggedIn) {
        this.debug = loggedIn;
    }
}
