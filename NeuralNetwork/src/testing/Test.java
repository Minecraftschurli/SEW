package testing;


import javafx.util.Pair;
import network.NeuralNetwork;
import network.Visualisation;
import network.data.DataSet;
import org.intellij.lang.annotations.MagicConstant;
import org.jetbrains.annotations.NotNull;

import java.io.*;
import java.util.ArrayList;
import java.util.Random;


public class Test {

    private static final String PATH = "C:\\Users\\georg\\Documents\\Schule\\SEW\\NeuralNetwork\\resources\\";

    private static final int S = 1;
    private static final int M = S * 60;
    private static final int H = M * 60;
    private static final int D = H * 24;

    public static void main(String[] args) {
        String name = "BoolNet7";
        //region load
        File f = new File(PATH + name + ".nn");
        NeuralNetwork nn = null;
        if (!f.exists()) {
            nn = NeuralNetwork.createGenericNN(name, 2, new int[]{8, 12, 16, 12, 8}, 4);
        }
        else {
            try {
                FileInputStream reader = new FileInputStream(f);
                ObjectInputStream os = new ObjectInputStream(reader);
                nn = (NeuralNetwork) os.readObject();
            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
        if (nn == null) return;
        //endregion
        //region train
        startTimedTrainingSession(30, M, nn);
        //endregion
        //region test
        ArrayList<Pair<Boolean, Boolean>> bs = new ArrayList<>();
        bs.add(new Pair<>(false, false));
        bs.add(new Pair<>(false, true));
        bs.add(new Pair<>(true, false));
        bs.add(new Pair<>(true, true));
        for (Pair<Boolean, Boolean> b : bs) {
            boolean b1 = b.getKey(), b2 = b.getValue();
            nn.sendData(new double[]{b1 ? 1.0 : 0.0, b2 ? 1.0 : 0.0});
            Double[] out = nn.getOutput();
            System.out.println("calculated: " + (out[0] /*< 0.5 ? 0.0 : 1.0*/) + " | " + (out[1] /*< 0.5 ? 0.0 : 1.0*/) + " | " + (out[2] /*< 0.5 ? 0.0 : 1.0*/) + " | " + (out[3] /*< 0.5 ? 0.0 : 1.0*/) + " | " + "wanted: " + ((b1 && b2) ? 1.0 : 0.0) + " | " + ((b1 || b2) ? 1.0 : 0.0) + " | " + (((b1 && !b2) || (!b1 && b2)) ? 1.0 : 0.0) + " | " + (((!b1 && !b2) || (b1 && b2)) ? 1.0 : 0.0));
        }
        //endregion
        //region save
        try {
            FileOutputStream fos = new FileOutputStream(new File(PATH + nn.name + ".nn"));
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(nn);
            oos.close();
            fos.close();
        } catch (IOException ignored) {
        }
        //endregion
        //region visualize
        Visualisation.visualize(nn);
        //endregion
    }

    private static void startTimedTrainingSession(int timeIn, @MagicConstant(intValues = {S, M, H, D}) int type, @NotNull NeuralNetwork nn) {
        long startTime = System.currentTimeMillis();
        int time = timeIn * type;
        double[][][] data = new double[10][][];
        Random r = new Random();
        while (System.currentTimeMillis() < startTime + (time * 1000)) {
            //region calc
            for (int j = 0; j < data.length; j++) {
                double d1, d2, o1, o2, o3, o4;
                boolean b1, b2;
                b1 = r.nextBoolean();
                b2 = r.nextBoolean();
                d1 = b1 ? 1.0 : 0.0;
                d2 = b2 ? 1.0 : 0.0;
                o1 = (b1 && b2) ? 1.0 : 0.0;
                o2 = (b1 || b2) ? 1.0 : 0.0;
                o3 = ((b1 && !b2) || (!b1 && b2)) ? 1.0 : 0.0;
                o4 = ((!b1 && !b2) || (b1 && b2)) ? 1.0 : 0.0;
                data[j] = new double[][]{{d1, d2}, {o1, o2, o3, o4}};
            }
            //endregion
            DataSet dataSet = new DataSet(data);
            nn.train(dataSet);
        }
    }

    private static void startTrainingSession(int length, @NotNull NeuralNetwork neuralNetwork) {
        double[][][] data = new double[50][][];
        Random r = new Random();

        for (int i = 0; i < length; i++) {
            //region calc
            for (int j = 0; j < data.length; j++) {
                double d1, d2, o1, o2, o3, o4;
                boolean b1, b2;
                b1 = r.nextBoolean();
                b2 = r.nextBoolean();
                d1 = b1 ? 1.0 : 0.0;
                d2 = b2 ? 1.0 : 0.0;
                o1 = (b1 && b2) ? 1.0 : 0.0;
                o2 = (b1 || b2) ? 1.0 : 0.0;
                o3 = ((b1 && !b2) || (!b1 && b2)) ? 1.0 : 0.0;
                o4 = ((!b1 && !b2) || (b1 && b2)) ? 1.0 : 0.0;
                data[j] = new double[][]{{d1, d2}, {o1, o2, o3, o4}};
            }
            //endregion
            DataSet dataSet = new DataSet(data);
            neuralNetwork.train(dataSet);
        }
        /*for (int i = 0; i < length; i++) {
            for (int j = 0; j < data.length; j++) {
                Double d1,d2,d3;
                do {
                    double randomNum1 = r.nextInt(100);
                    d1 = randomNum1 / 100.0;
                    //boolean randomBool2 = r.nextBoolean();
                    double randomNum2 = r.nextInt(100);
                    d2 = randomNum2 / 100.0;
                    d3 = (randomNum1 / randomNum2) / 100.0;
                } while ((d1.isNaN()||d1.isInfinite())||(d2.isNaN()||d2.isInfinite())||(d3.isNaN()||d3.isInfinite()));
                data[j] = new double[][]{{d1, d2}, {d3}};
            }

            DataSet dataSet = new DataSet(data);
            neuralNetwork.train(dataSet);
        }*/
    }

    public static void runTestData(@NotNull DataSet data, @NotNull NeuralNetwork neuralNetwork) {
        int i = 0;
        double error;
        do {
            neuralNetwork.sendData(data.getTest(i));
            Double[] output = neuralNetwork.getOutput();
            error = data.calcError(i, output);
            i++;
        } while (error != 0 && i < data.size());
    }
}
