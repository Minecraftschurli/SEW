package api.gui;

import api.Try;
import api.annotations.TestMethod;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;
import java.awt.*;

/**
 * @author georg
 *
 */
public class GUI {

    private JFrame frame;

    @TestMethod(name = "GUI")
    private static void test(){
        CustomPanel panel = new CustomPanel();
        panel.setComps((g, panel1) -> {
            g.drawLine(10,10,10,20);
        });
        new GUI("test",new Dimension(100,100),new Component[]{panel},new String[]{BorderLayout.CENTER},false,true).setVisible();
        Try.waitM(1000);
    }

    private GUI(){}

    public GUI(String title, Dimension d, @NotNull Component[] c, @NotNull String[] s, boolean sizeIsPreferred, boolean resizeable){
		if (c.length != s.length){
		    return;
        }
        this.frame = new JFrame();
		frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        if (sizeIsPreferred){
		    frame.setPreferredSize(d);
        } else {
            frame.setMinimumSize(d);
        }
        frame.setResizable(resizeable);
        frame.setTitle(title);
        for (int i = 0; i < c.length; i++){
            frame.add(c[i],s[i]);
        }
        frame.pack();
    }

    public void setVisible(){
        frame.setVisible(true);
    }
}
