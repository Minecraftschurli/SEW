package starter;

import javax.swing.*;
import java.awt.*;

public abstract class App {

    public JFrame master;
    protected boolean viewed;
    public KeyboardFocusManager keyboardFocusManager;

    protected App(){
        keyboardFocusManager = KeyboardFocusManager.getCurrentKeyboardFocusManager();
    }

    public abstract JPanel getView();

    public void setViewed(boolean b){
        viewed = b;
    }

    public abstract void onOpened();

    public abstract void onClosed();

    public void beforeOpening() {
        master.setMinimumSize(this.getView().getMinimumSize());
        master.setPreferredSize(this.getView().getPreferredSize());
        master.setMaximumSize(this.getView().getMaximumSize());
        master.repaint();
    }
}
