package au22;

import javax.swing.*;
import java.awt.*;
import java.util.Random;


@SuppressWarnings("all")
public class ClockPanel extends JPanel {

    private int radius;
    private int time;
    private Point center;

    public ClockPanel(){
        super();
        calculateRandomTime();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        drawClock(g,10,10);
    }

    private void drawClock(Graphics g, int xOffset, int yOffset) {
        this.radius = (getWidth()-(xOffset*2) > getHeight()-(yOffset*2) ? getHeight()-(yOffset*2) : getWidth()-(xOffset*2))/2;
        this.center = new Point(getWidth()/2,yOffset+radius);
        g.drawOval((getWidth()/2)-this.radius,yOffset,this.radius*2,this.radius*2);
        time = 1010;
        Point hourHandPos = getHourHandPos();
        drawLine(g,this.center,hourHandPos);
        Point minutesHandPos = getMinutesHandPos();
        drawLine(g,this.center,minutesHandPos);
    }

    private void drawLine(Graphics g, Point from, Point to) {
        g.drawLine(from.x,from.y,to.x,to.y);
    }

    private double x(double x){
        return Math.sin(Math.toRadians(x));
    }

    private double y(double y){
        return Math.cos(Math.toRadians(y));
    }

    private Point getHourHandPos() {
        double x = 0, y = 0;
        double tmp = 60*((int)time/60);
        double deg = (tmp/2.0);
        double sin = x(deg) * (this.radius/2);
        double cos = y(deg) * (this.radius/2);
        double h = this.time/2/60;
        int q = (int) (tmp%12)+1;

        switch (q){
            case 1:
                x = this.center.x + sin;
                y = this.center.y - cos;
                break;
            case 2:
                x = this.center.x + sin;
                y = this.center.y + cos;
                break;
            case 3:
                x = this.center.x - sin;
                y = this.center.y + cos;
                break;
            case 4:
                x = this.center.x - sin;
                y = this.center.y - cos;
                break;
        }

        return new Point((int)x,(int)y);
    }

    private Point getMinutesHandPos() {
        double x = 0, y = 0;
        int tmp = (time%60);
        int deg = (tmp*6);
        double sin = x(deg) * (this.radius);
        double cos = y(deg) * (this.radius);
        double h = this.time%60;
        int q = 3;

        switch (q){
            default:
                x = this.center.x + sin;
                y = this.center.y - cos;
                break;
            case 2:
                x = this.center.x + sin;
                y = this.center.y + cos;
                break;
            case 1:
                x = this.center.x - sin;
                y = this.center.y + cos;
                break;
            case 4:
                x = this.center.x - sin;
                y = this.center.y - cos;
                break;
        }

        System.out.println(tmp+"\n"+(int)(this.time/60)+":"+(int)(this.time%60)+"\n"+deg+"\n"+sin+"\n"+cos+"\n"+x+"\n"+y+"\n"+q+"\n");
        return new Point((int)x,(int)y);
    }

    public void calculateRandomTime() {
        Random rand = new Random();
        this.time = rand.nextInt(24*60);
    }
}
