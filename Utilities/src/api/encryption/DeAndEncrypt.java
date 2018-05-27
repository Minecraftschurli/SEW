package api.encryption;

import api.gui.GraphicalUserInterface;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

public class DeAndEncrypt {

    private JPanel frame;
    private Container c;
    private JButton buttonEncrypt;
    private JButton buttonDecrypt;
    private JButton buttonClose;
    private JButton buttonBack;
    private Checkbox chooser;
    private JLabel text;

    private File file;

    public static void deAndEncrypt(File file){
        new DeAndEncrypt().deAndEncrypt1(file);
    }


    private void deAndEncrypt1(File file){
        this.file = file;
        this.frame = new JPanel();
        this.c = new Container();
        this.buttonEncrypt = new JButton("Encrypt");
        this.buttonDecrypt = new JButton("Decrypt");
        this.buttonClose = new JButton("Close");
        this.buttonBack = new JButton("Back");
        this.text = new JLabel();
        this.text.setHorizontalAlignment(JLabel.CENTER);
        this.chooser = new Checkbox("Advanced");
        Listener l = new Listener();
        buttonEncrypt.addActionListener(l);
        buttonDecrypt.addActionListener(l);
        buttonClose.addActionListener(l);
        buttonBack.addActionListener(l);
        c.setLayout(new FlowLayout());
        c.add(buttonEncrypt);
        c.add(buttonDecrypt);
        c.add(chooser,FlowLayout.RIGHT);
        frame.add(text);
        frame.add(c,BorderLayout.SOUTH);
        frame.setSize(350,200);
        GraphicalUserInterface i = new GraphicalUserInterface("",frame,JFrame.EXIT_ON_CLOSE);
        //frame.setVisible(true);
    }

    private class Listener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            c.remove(buttonDecrypt);
            c.remove(buttonEncrypt);
            c.remove(chooser);
            c.repaint();
            frame.repaint();
            if (e.getSource() == buttonBack){
                c.remove(buttonBack);
                c.remove(buttonClose);
                c.add(buttonDecrypt);
                c.add(buttonEncrypt);
                c.add(chooser);
                frame.repaint();
            }
            if (e.getSource() == buttonDecrypt){
                text.setText("");
                try {
                    if (chooser.getValue()) {
                        new AdvDecrypter(1155869325).decryptFile(file);
                    } else {
                        new BasicDecrypter(1155869325).decryptFile(file);
                    }
                    text.setText("<html><font color=\"green\">Success</font></html>");
                    frame.repaint();
                } catch (Exception e1) {
                    text.setText("<html><font color=\"red\">Failed</font></html>");
                    frame.repaint();
                }
                c.add(buttonBack);
                c.add(buttonClose);
                frame.repaint();
            }
            if (e.getSource() == buttonEncrypt){
                text.setText("");
                try {
                    if (chooser.getValue()) {
                        new AdvEncrypter(1L).encryptFile(file);
                    } else {
                        Encrypter.STANDARD_CESAR_ENCRYPTER.encryptFile(file);
                    }
                    text.setText("<html><font color=\"green\">Success</font></html>");
                    frame.repaint();
                } catch (Exception e1) {
                    text.setText("<html><font color=\"red\">Failed</font></html>");
                    frame.repaint();
                }
                c.add(buttonBack);
                c.add(buttonClose);
                frame.repaint();
            }
            if (e.getSource() == buttonClose){
                System.exit(0);
            }
        }
    }

    private class Checkbox extends JCheckBox{
        private boolean value;
        public Checkbox(String s){
            super(s);
            addActionListener(e -> value = !value);
        }

        public boolean getValue(){
            return value;
        }
    }
}
