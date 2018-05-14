package au22;

import javax.swing.*;
import java.awt.*;
import java.util.Random;

import static java.lang.Math.PI;

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
        this.center = new Point(getWidth()/2,getHeight()/2);
        g.drawOval((getWidth()/2)-this.radius,yOffset,this.radius*2,this.radius*2);
        Point hourHandPos = getHourHandPos();
        g.drawLine(this.center.x,this.center.y,hourHandPos.x,hourHandPos.y);
    }

    private Point getHourHandPos() {
        double x = 0, y = 0;
        Double rad = (this.time/360/360*PI); //Why is this allways 0.0
        double sin = Math.sin(rad) * (this.radius/2);
        double cos = Math.cos(rad) * (this.radius/2);
        double h = this.time/2/60;
        int q = h > 9 ? 4 : h > 6 ? 3 : h > 3 ? 2 : 1;

        switch (q){
            case 1:
                x = this.center.x - cos;
                y = this.center.y - sin;
                break;
            case 2:
                x = this.center.x + cos;
                y = this.center.y - sin;
                break;
            case 3:
                x = this.center.x - cos;
                y = this.center.y + sin;
                break;
            case 4:
                x = this.center.x + cos;
                y = this.center.y + sin;
                break;
        }

        System.out.println((int)(this.time/60)+":"+(int)(this.time%60)+"\n"+rad+"\n"+sin+"\n"+cos+"\n"+x+"\n"+y+"\n"+q+"\n");
        return new Point((int)x,(int)y);
    }

    public void calculateRandomTime() {
        Random rand = new Random();
        this.time = rand.nextInt(24*60);
    }
}
