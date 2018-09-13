package starter;


import au22.ClockControl;
import main.CookieClickerControl;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.atomic.AtomicReference;

public class AllInOneWindow extends JFrame implements ActionListener {

    public static final HashMap<String,Class<? extends App>> apps = new HashMap<>();
    private final HashMap<String,App> windows = new HashMap<>();
    private final ArrayList<JMenuItem> menuItems = new ArrayList<>();
    private final JMenu appsMenu = new JMenu("Apps");
    private final AtomicReference<App> actualApp = new AtomicReference<>();
    private JMenuBar bar;

    public AllInOneWindow(){
        bar = new JMenuBar();
        apps.forEach((s, aClass) -> {
            try {
                windows.put(s, aClass.getDeclaredConstructor().newInstance());
            } catch (InvocationTargetException | IllegalAccessException | InstantiationException | NoSuchMethodException ignored) {
            }
        });
        windows.forEach((s, app) -> {
            app.master = this;
            JMenuItem tmp = new JMenuItem(s);
            tmp.setName(s);
            tmp.addActionListener(this);
            this.appsMenu.add(tmp);
            this.menuItems.add(tmp);
        });
        bar.add(appsMenu);
        this.setJMenuBar(bar);
        this.setMinimumSize(new Dimension(300,300));
        this.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        windows.forEach((s, app) -> {
            if(((JMenuItem)e.getSource()).getName().equals(s)){
                if (actualApp.get()!=null) {
                    actualApp.get().onClosed();
                    actualApp.get().setViewed(false);
                    this.remove(actualApp.get().getView());
                }
                actualApp.set(app);
                actualApp.get().beforeOpening();
                this.add(actualApp.get().getView());
                actualApp.get().setViewed(true);
                actualApp.get().onOpened();
            }
        });
    }

    public static void main(String[] args) {
        AllInOneWindow.apps.put("Cookie",CookieClickerControl.class);
        AllInOneWindow.apps.put("Clock",ClockControl.class);
        //AllInOneWindow.apps.put("Draw",)
        AllInOneWindow w = new AllInOneWindow();
    }
}
