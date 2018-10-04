package testing;


import javafx.util.Pair;
import network.NeuralNetwork;
import network.Visualisation;
import training.Trainer;
import training.TrainingDataGenerator;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.ArrayList;
import java.util.Random;

public class Test {

    //region time constants
    private static final int mS = 1;
    private static final int S = mS * 1000;
    private static final int M = S * 60;
    private static final int H = M * 60;
    private static final int D = H * 24;
    //endregion

    private static final String SAVE_PATH = "C:\\Users\\georg\\Documents\\Schule\\SEW\\NeuralNetwork\\resources\\";

    private static final String NAME = "BasicBoolNet2";
    private static final int TIME = (8 * H) + (10 * M);
    private static final int INPUT_NEURONS = 2;
    private static final int[] HIDDEN_NEURONS = {4, 4, 4};
    private static final int OUTPUT_NEURONS = 2;
    private static final TrainingDataGenerator DATA_GENERATOR = () ->
    {//generator
        Random r = new Random();
        boolean b1, b2;
        b1 = r.nextBoolean();
        b2 = r.nextBoolean();
        return new double[][]{{b1 ? 1.0 : 0.0, b2 ? 1.0 : 0.0}, {(b1 && b2) ? 1.0 : 0.0, (b1 || b2) ? 1.0 : 0.0}};
    };

    public static void main(String[] args) {
        //region load
        File f = new File(SAVE_PATH + NAME + ".nn");
        NeuralNetwork nn = null;
        if (!f.exists()) {
            nn = NeuralNetwork.createGenericNN(NAME, INPUT_NEURONS, HIDDEN_NEURONS, OUTPUT_NEURONS);
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
        Trainer t = new Trainer(SAVE_PATH, nn, DATA_GENERATOR);
        t.startTimedTrainingSession(TIME, (short) 10);
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
            System.out.println("calculated: " + (out[0] < 0.5 ? 0.0 : 1.0) + " | " + (out[1] < 0.5 ? 0.0 : 1.0) +/* " | " + (out[2] /*< 0.5 ? 0.0 : 1.0*//*) + " | " + (out[3] /*< 0.5 ? 0.0 : 1.0*//*) + */" | " + "wanted: " + ((b1 && b2) ? 1.0 : 0.0) + " | " + ((b1 || b2) ? 1.0 : 0.0) + " | " /*+ (((b1 && !b2) || (!b1 && b2)) ? 1.0 : 0.0) + " | " + (((!b1 && !b2) || (b1 && b2)) ? 1.0 : 0.0)*/);
        }
        //endregion
        //region save
        nn.save(SAVE_PATH, nn.name);
        //endregion
        //region visualize
        Visualisation.visualize(nn);
        //endregion
    }
}
