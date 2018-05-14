package au22;


import javax.swing.*;
import java.awt.*;
import java.util.Random;


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
        String minutes = (this.time%60)+"";
        g.drawString((this.time/60)+":"+(minutes.length()==1?"0"+minutes:minutes),10,20);
    }

    private void drawClock(Graphics g, int xOffset, int yOffset) {
        this.radius = (getWidth()-(xOffset*2) > getHeight()-(yOffset*2) ? getHeight()-(yOffset*2) : getWidth()-(xOffset*2))/2;
        this.center = new Point(getWidth()/2,yOffset+radius);
        g.drawOval((getWidth()/2)-this.radius,yOffset,this.radius*2,this.radius*2);
        for (int i = 0; i < 12 * 5; i++) {
            if (i%5!=0)drawLine(g,angularMovedPoint(this.center,this.radius/16*15,i*6),angularMovedPoint(this.center,this.radius,i*6));
            else drawLine(g,angularMovedPoint(this.center,this.radius/6*5,i*30),angularMovedPoint(this.center,this.radius,i*30));
        }
        Point hourHandPos = getHourHandPos();
        drawLine(g,this.center,hourHandPos);
        Point minutesHandPos = getMinutesHandPos();
        drawLine(g,this.center,minutesHandPos);
    }

    private void drawLine(Graphics g, Point from, Point to) {
        g.drawLine(from.x,from.y,to.x,to.y);
    }

    private void drawVector(Graphics g, Point pos, double angle, int length){
        drawLine(g,pos,angularMovedPoint(pos,length,angle));
    }

    private Point angularMovedPoint(Point base, int distance, double angle){
        double x, y;
        double sin = x(angle) * distance;
        double cos = y(angle) * distance;

        x = base.x + sin;
        y = base.y - cos;

        return new Point((int)x,(int)y);
    }

    private double getMinuteDegrees(){
        return ((time%60.0)*6.0);
    }

    private double getHourDegrees(){
        return (time/60.0)*30.0;
    }

    private double x(double x){
        return Math.sin(Math.toRadians(x));
    }

    private double y(double y){
        return Math.cos(Math.toRadians(y));
    }

    private Point getHourHandPos() {
        return angularMovedPoint(this.center,this.radius/20*13,getHourDegrees());
    }

    private Point getMinutesHandPos() {
        return angularMovedPoint(this.center,this.radius,getMinuteDegrees());
    }

    public void calculateRandomTime() {
        Random rand = new Random();
        this.time = rand.nextInt(24*60);
    }

    public void addHour(int hour) {
        addMinute(hour*60);
    }

    public void addMinute(int minute) {
        this.time += minute;
        if (this.time >= 24*60){
            this.time -= 24*60;
        }
        if (this.time < 0){
            this.time += 24*60;
        }
    }
}
