package training;

import network.NeuralNetwork;
import network.data.DataSet;

import java.text.NumberFormat;

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

    public void startTimedTrainingSession(int time, short setSize) {
        if (this.network == null) return;
        NeuralNetwork nn = this.network;
        long x = 0L;
        long counter = 0L;
        long startTime = System.currentTimeMillis();
        double[][][] data = new double[setSize][][];
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
                if (System.currentTimeMillis() - startTime > (H + x)) {
                    nn.save(savePath, nn.name);
                    x += H;
                }
                counter++;
            }
            nn.save(savePath, nn.name);
            String counterS = NumberFormat.getNumberInstance().format(counter);
            System.out.println("Trained " + counterS + " times");
            return;
        } while (false);
        System.out.println("stopping after " + ((System.currentTimeMillis() - startTime) / 1000) + "seconds");
    }
}
