package starter;

import javax.swing.*;
import java.awt.*;

public class StarterView extends JFrame {

    private JButton buttonClose;
    private JButton buttonOK;
    private JComboBox<String> dropdown;
    private JPanel panel;
    private Container buttonGroup;
    private StarterControl control;
    public final String[] label = {"CookieClicker","ClockStandard","ClockDark","ClockRandom","ClockRandomDark"};

    public StarterView(StarterControl control){
        this.control = control;
        this.setBackground(Color.WHITE);
        this.buttonGroup = new Container();
        this.buttonClose = new JButton("Close");
        this.buttonOK = new JButton("OK");
        this.panel = new JPanel();
        this.buttonGroup.setLayout(new GridLayout(1,2));
        this.buttonGroup.add(this.buttonOK);
        this.buttonGroup.add(this.buttonClose);
        this.dropdown = new JComboBox<>();
        this.panel.setMaximumSize(new Dimension(Integer.MAX_VALUE,10));

        for (String l:label){
            this.dropdown.addItem(l);
        }
        this.panel.add(this.dropdown);
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        this.setLayout(new BorderLayout());
        this.add(this.panel,BorderLayout.CENTER);
        this.add(this.buttonGroup,BorderLayout.SOUTH);
        this.pack();
        this.setVisible(true);

        this.buttonOK.addActionListener(this.control);
        this.buttonClose.addActionListener(this.control);
    }



    public boolean isButtonClose(Object o){
        return o == this.buttonClose;
    }

    public boolean isButtonOK(Object o){
        return o == this.buttonOK;
    }

    public String getChosenOption() {
        return this.dropdown.getItemAt(this.dropdown.getSelectedIndex());
    }
}
