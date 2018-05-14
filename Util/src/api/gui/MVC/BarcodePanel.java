package api.gui.MVC;

import javax.swing.*;
import java.awt.*;

import static api.Misc.bitsFromBytes;

public class BarcodePanel extends JPanel {

    private Boolean[] data;

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (data == null || data.length == 0) {
            data = new Boolean[]{false};
        }
        drawBarcode(g, data, 0, 10);
    }

    private void drawBarcode(Graphics g, Boolean[] data, int offsetX, int offsetY) {
        if (data.length == 1 && !data[0]) {
            return;
        }

        int width = (getWidth()/(data.length+6));
        width = width < 1 ? 1 : width;

        drawSafetyBit(g,offsetX,offsetY,width);
        offsetX += 3*width;

        for (int i = 0; i < data.length ; i++) {
            if (data[i]){
                g.fillRect(offsetX+i*width,offsetY,width,(getHeight()-(offsetY*2+getHeight()/10)));
                //g.drawLine(offsetX+i,offsetY,offsetX+i,(getHeight()-offsetY)-getHeight()/10);
            }
        }

        drawSafetyBit(g,offsetX+data.length*width,offsetY, width);
    }

    private void drawSafetyBit(Graphics g, int offsetX, int offsetY, int width) {
        g.fillRect(offsetX,offsetY,width,getHeight());
        g.fillRect(offsetX+2*width,offsetY,width,getHeight());
        g.drawLine(offsetX,offsetY,offsetX,getHeight());
        g.drawLine(offsetX+2,offsetY,offsetX+2,getHeight());
    }

    private void drawBarcode(Graphics g, boolean[] data, int offsetX, int offsetY, int height) {
        for (int i = 0; i < data.length ; i++) {
            if (data[i]){
                g.drawLine(offsetX+i,offsetY,offsetX+i,offsetY+height);
            }
        }
    }

    public int setInfo(String info) {
        if (info == null){
            return -1;
        }
        this.data = api.Boolean.convert(bitsFromBytes(info.getBytes()));
        return 0;
    }

    public void outputData(){
        for (Boolean b : this.data) {
            System.out.print(b?1:0);
        }
        System.out.println();
    }
}
