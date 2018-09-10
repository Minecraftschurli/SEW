package network;


import javafx.util.Pair;
import network.data.DataSet;

import java.io.*;
import java.util.ArrayList;
import java.util.Random;


public class Test {

    private static String PATH = "C:\\Users\\georg\\Documents\\Schule\\SEW\\NeuralNetwork\\resources\\";

    public static void main(String[] args) {
        String name = "BoolNet4";
        //region load
        File f = new File(PATH + name + ".nn");
        NeuralNetwork nn = null;
        if (!f.exists()) {
            nn = NeuralNetwork.createGenericNN(name, 2, new int[]{6, 8, 6}, 4);
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
        startTimedTrainingSession(30, nn);
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
            System.out.println("calculated: " + out[0] + " | " + out[1] + " | " + out[2] + " | " + out[3] + " | " + "wanted: " + ((b1 && b2) ? 1.0 : 0.0) + " | " + ((b1 || b2) ? 1.0 : 0.0) + " | " + (((b1 && !b2) || (!b1 && b2)) ? 1.0 : 0.0) + " | " + (((!b1 && !b2) || (b1 && b2)) ? 1.0 : 0.0));
        }
        //endregion
        //region save
        try {
            FileOutputStream writer = new FileOutputStream(new File(PATH + nn.name + ".nn"));
            ObjectOutputStream os = new ObjectOutputStream(writer);
            os.writeObject(nn);
            writer.close();
        } catch (IOException ignored) {
        }
        //endregion
    }

    private static void startTimedTrainingSession(int time, NeuralNetwork nn) {
        long startTime = System.currentTimeMillis();
        double[][][] data = new double[10][][];
        Random r = new Random();
        while (System.currentTimeMillis() < startTime + (time * 1000 * 60)) {
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

    private static void startTrainingSession(int length, NeuralNetwork neuralNetwork) {
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

    public static void runTestData(DataSet data, NeuralNetwork neuralNetwork) {
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
