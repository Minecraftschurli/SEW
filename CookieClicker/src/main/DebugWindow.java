package main;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.concurrent.atomic.AtomicReference;

public class DebugWindow extends JFrame implements ActionListener {

    private final AtomicReference<Component> actuall = new AtomicReference<>();
    private final HashMap<String, char[]> users = new HashMap<>();
    private JPanel login;
    private JTextField username;
    private JPasswordField passwordField;
    private JButton checkButton;
    private JButton logout;
    private Container fields;
    private CookieClickerControl cc;

    public DebugWindow(String text, CookieClickerControl cc) {
        super(text);

        this.login = new JPanel();

        this.cc = cc;

        users.put("admin", "1234".toCharArray());

        username = new JTextField();
        passwordField = new JPasswordField();
        checkButton = new JButton("Login");
        fields = new Container();
        logout = new JButton("Logout");

        login.setLayout(new BorderLayout());
        fields.setLayout(new GridLayout(2, 2));
        fields.add(new JLabel("Username: "));
        fields.add(username);
        fields.add(new JLabel("Password: "));
        fields.add(passwordField);
        login.add(fields, BorderLayout.CENTER);
        login.add(checkButton, BorderLayout.SOUTH);

        checkButton.addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == checkButton) {
            if (passwordField.getPassword().length == users.get(username.getText()).length) {
                yes:
                {
                    for (int i = 0; i < passwordField.getPassword().length; i++) {
                        if (passwordField.getPassword()[i] != users.get(username.getText())[i]) {
                            break yes;
                        }
                    }
                    cc.setLoggedIn(true);
                    passwordField.setText("");
                    username.setText("");
                    this.setVisible(false);
                }
            }
        }
        if (e.getSource() == logout) {
            cc.setLoggedIn(false);
            this.setVisible(false);
        }
    }

    @Override
    public void setVisible(boolean b) {
        if (b) {
            if (cc.isLoggedIn()) {
                changeTo(logout);
            } else {
                changeTo(login);
            }
        }
        super.setVisible(b);
        this.repaint();
    }

    private void changeTo(Component newC) {
        newC.setVisible(false);
        if (actuall.get() != null) actuall.get().setVisible(false);
        if (actuall.get() != null) this.remove(actuall.get());
        actuall.set(newC);
        this.add(newC);
        newC.setVisible(true);
    }
}
