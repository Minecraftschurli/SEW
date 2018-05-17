package au22;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.util.Random;

import static api.gui.GUIHelper.*;

@SuppressWarnings({"WeakerAccess", "SameParameterValue", "unused"})
public class ClockModel extends JPanel {

    private int radius;
    private int time;
    private int alarmTime;
    private Point center;
    private boolean initialized;
    private Color paintColor;
    private boolean isSetAlert;

    public ClockModel(){
        super();
        calculateRandomTime();
        initialized = false;
    }

    @Override
    protected void paintComponent(Graphics g) {
        checkAlarm();
        super.paintComponent(g);
        g.setColor(this.paintColor/*getInverseColor(this.getBackground())*/);
        drawClock(g,10,10);
        String seconds = (this.time%60)+"";
        String minutes = ((this.time/60)%60)+"";
        String hours = (this.time/60/60)+"";
        Point tmp = new Point(this.center);
        tmp.translate(-25,40);
        drawRectWithString(g,tmp,hours+":"+(minutes.length()==1?"0"+minutes:minutes)+":"+(seconds.length()==1?"0"+seconds:seconds));
    }

    private void checkAlarm() {
        if (this.time == this.alarmTime){
            try {
                AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(new File("./au22/resources/Das Sägewerk Bad Segeberg.wav").getAbsoluteFile());
                Clip clip = AudioSystem.getClip();
                clip.open(audioInputStream);
                clip.start();
            } catch(Exception ex) {
                System.out.println("Error with playing sound.");
                ex.printStackTrace();
            }
        }
    }

    private void drawClock(Graphics g, int xOffset, int yOffset) {
        this.radius = (getWidth()-(xOffset*2) > getHeight()-(yOffset*2) ? getHeight()-(yOffset*2) : getWidth()-(xOffset*2))/2;
        this.center = new Point(getWidth()/2,yOffset+radius);
        fillCircle(g,this.center,this.radius+1,new Color(0));
        fillCircle(g,this.center,this.radius-1,this.getBackground());
        for (int i = 0; i < 12 * 5; i++) {
            if (i%5!=0)drawLine(g,angularMovedPoint(this.center,this.radius/16*15,i*6),angularMovedPoint(this.center,this.radius,i*6));
            else drawLine(g,angularMovedPoint(this.center,this.radius/6*5,i*30),angularMovedPoint(this.center,this.radius,i*30));
        }
        drawVector(g,this.center,getHourDegrees(),this.radius/20*13);
        drawVector(g,this.center,getMinuteDegrees(),this.radius/10*9);
        drawVector(g,this.center,getSecondDegrees(),this.radius/20*18,new Color(0xC90A0A));
    }

    private double getSecondDegrees(){
        return (((isSetAlert?0:time)%60.0)*6.0);
    }

    private double getMinuteDegrees(){
        return ((((isSetAlert?alarmTime:time)/60.0)%60.0)*6.0);
    }

    private double getHourDegrees(){
        return ((((isSetAlert?alarmTime:time)/60.0)/60.0)*30.0);
    }

    public void calculateRandomTime() {
        if (!this.initialized) {
            Random rand = new Random();
            this.time = rand.nextInt(24*60*60);
        }
    }

    public void addHour(int hour) {
        addMinute(hour*60);
    }

    public void addMinute(int minute) {
        addSecond(minute*60);
    }

    public void addSecond(int seconds) {
        if (!isSetAlert) {
            this.time += seconds;
            if (this.time >= 24*60*60){
                this.time -= 24*60*60;
            }
            if (this.time < 0){
                this.time += 24*60*60;
            }
        } else {
            this.alarmTime += seconds;
            if (this.alarmTime >= 24*60*60){
                this.alarmTime -= 24*60*60;
            }
            if (this.alarmTime < 0){
                this.alarmTime += 24*60*60;
            }
        }
    }

    public void addSeconds(int seconds) {
            this.time += seconds;
            if (this.time >= 24*60*60){
                this.time -= 24*60*60;
            }
            if (this.time < 0){
                this.time += 24*60*60;
            }

    }

    public void setInitialTime(long time,int timezone) {
        if (!this.initialized) {
            int days = 0;
            while (time > 24*60*60){
                time -= 24*60*60;
                days ++;
            }
            time += timezone*60*60;
            this.time = (int)time;
            this.initialized = true;
        }
    }

    public void setPaintColor(Color c){
        this.paintColor = c;
    }

    public void setIsAlert(boolean b){
        this.isSetAlert = b;
    }
}
