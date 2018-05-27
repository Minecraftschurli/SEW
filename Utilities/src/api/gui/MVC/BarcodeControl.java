package api.gui.MVC;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class BarcodeControl {
    private JFrame frame;
    private JButton[] buttons;
    private Container buttonPanel;
    private Container container;
    private BarcodePanel panel;
    private JTextField textField;

    public BarcodeControl(){
        this.panel = new BarcodePanel();
        this.buttons = new JButton[2];
        this.textField = new JTextField();
        this.buttons[0] = new JButton("Redraw!");
        this.buttons[1] = new JButton("Close");
        this.frame = new JFrame("Hello Barcode");
        this.buttonPanel = new Container();
        this.buttonPanel.setLayout(new FlowLayout());
        this.buttonPanel.add(this.buttons[0]);
        this.buttonPanel.add(this.buttons[1]);
        this.frame.setLayout(new BorderLayout());
        this.frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        this.frame.setSize(350,200);
        this.frame.add(this.textField, BorderLayout.NORTH);
        this.frame.add(this.panel, BorderLayout.CENTER);
        this.frame.add(this.buttonPanel, BorderLayout.SOUTH);
        this.frame.setVisible(true);

        CustomActionListener listener = new CustomActionListener();

        this.textField.addActionListener(listener);

        for (JButton b : this.buttons) {
            b.addActionListener(listener);
        }
    }

    private class CustomActionListener implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent e) {
            if (e.getSource() == textField || e.getSource() == buttons[0]){
                panel.setInfo(textField.getText());
                panel.outputData();
                panel.repaint();
            }
            if (e.getSource() == buttons[1]){
                System.exit(0);
            }
        }
    }

    public static void main(String[] args) {
        BarcodeControl control = new BarcodeControl();
    }
}
