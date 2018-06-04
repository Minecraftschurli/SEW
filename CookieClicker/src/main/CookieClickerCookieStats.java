package main;

import javax.swing.*;
import java.awt.*;
import java.text.DecimalFormat;

public class CookieClickerCookieStats extends JPanel {

    private long cookies;
    private int var1;
    private String[] names = {"", "thousand", "million", "billion", "trillion", "quadrillion", "quintillion", ""};
    private Font font;

    public CookieClickerCookieStats() {
        this.cookies = 0;
        this.font = new Font(Font.SANS_SERIF, Font.PLAIN, 12);
        this.setPreferredSize(new Dimension(100, getFontMetrics(font).getHeight()));
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.setFont(font);
        while (cookies / Math.pow(10, var1 * 3) > 1000) {
            var1++;
        }
        DecimalFormat format = new DecimalFormat(var1 > 0 ? "#,##0.000" : "#");
        String cookieString = (format.format(((double) cookies) / Math.pow(10, var1 * 3)) + names[var1]);
        g.drawString(cookieString.replace('.', ','), 10, getFontMetrics(getFont()).getHeight());
    }

    public void addCookies(long value) {
        this.cookies += value;
        this.repaint();
    }

    public long getCookies() {
        return cookies == -1L ? Long.MAX_VALUE : cookies;
    }

    public void setCookies(long n) {
        this.cookies = n;
        this.repaint();
    }
}
