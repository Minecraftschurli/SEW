package training;

import network.NeuralNetwork;
import network.data.DataSet;

import javax.swing.*;
import java.awt.*;
import java.text.NumberFormat;
import java.time.LocalTime;
import java.util.concurrent.atomic.AtomicLong;

/**
 *
 */
public class Trainer {

    private static final int H = 3600000;

    private final String savePath;
    private NeuralNetwork network;

    private TrainingDataGenerator g;

    /**
     *
     */
    public Trainer(final String savePath, NeuralNetwork nn, TrainingDataGenerator g) {
        this.g = g;
        this.savePath = savePath;
        this.network = nn;
    }

    public Trainer(final String savePath) {
        this.savePath = savePath;
        this.network = null;
    }

    public void setNetwork(NeuralNetwork network) {
        if (network != null) this.network = network;
    }

    public void startTimedTrainingSession(int time, byte setSize) {
        if (this.network == null) return;
        NeuralNetwork nn = this.network;
        AtomicLong x = new AtomicLong(0L);
        long counter = 0L;
        long startTime = System.currentTimeMillis();
        double[][][] data = new double[setSize][][];
        synchronized (nn) {
            Thread save = new Thread(() -> {
                if (System.currentTimeMillis() - startTime > (H + x.get())) {
                    nn.save(savePath, nn.name);
                    x.addAndGet(H);
                }
            });
            save.start();
            JFrame frame = new JFrame("Training");
            JProgressBar bar = new JProgressBar(JProgressBar.HORIZONTAL, 0, time);
            bar.setForeground(new Color(0x438200));
            bar.setStringPainted(true);
            frame.add(bar);
            frame.setAlwaysOnTop(true);
            frame.setVisible(true);
            Thread barThread = new Thread(() -> {
                while (true) {
                    bar.setValue((int) (System.currentTimeMillis() - startTime));
                    bar.setString(LocalTime.MIN.plusSeconds((((time - System.currentTimeMillis() + startTime) / 1000) + 1)).toString());
                }
            });
            barThread.start();
            l1:
            //noinspection LoopStatementThatDoesntLoop,ConstantConditions
            do {
                while (System.currentTimeMillis() < startTime + (time)) {
                    //region calc
                    for (int j = 0; j < data.length; j++) {
                        double[][] d = g.getIOMatrix();
                        if (d.length != 2 || d[0].length != nn.getInputNeuronCount() || d[1].length != nn.getOutputNeuronCount())
                            break l1;
                        data[j] = d;
                    }
                    //endregion
                    DataSet dataSet = new DataSet(data);
                    try {
                        nn.train(dataSet);
                    } catch (NeuralNetwork.AlreadyTrainedException e) {
                        break l1;
                    }

                    counter++;
                }
                save.interrupt();
                nn.save(savePath, nn.name);
                NumberFormat f = NumberFormat.getNumberInstance();
                System.out.println("Trained " + f.format(counter) + " times with " + f.format(counter / (time / 1000)) + " times/s");
                frame.setVisible(false);
                barThread.interrupt();
                frame.dispose();
                return;
            } while (false);
            save.interrupt();
            System.out.println("stopping after " + ((System.currentTimeMillis() - startTime) / 1000) + "seconds");
            frame.setVisible(false);
            barThread.interrupt();
            frame.dispose();
        }
    }
}
