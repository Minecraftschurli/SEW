package network.data;


public class DataSet {
    /**
     *
     */
    private double[][][] data;

    /**
     * @param dataIn
     */
    public DataSet(double[][][] dataIn) {
        data = dataIn;
    }

    /**
     * @param i the index of the test
     * @return the testing data
     */
    public double[] getTest(int i) {
        return data[i][0];
    }

    /**
     * @param i      the index of the test
     * @param output the calculated output
     * @return the error value
     */
    public double calcError(int i, Double[] output) {
        double error = 0;
        for (int j = 0; j < output.length; j++) {
            error += 1.0 / 2.0 * Math.pow((data[i][1][j] - (output[j])), 2);
        }
        return error;
    }

    /**
     * @return the amount of tests
     */
    public int size() {
        return data.length;
    }

    /**
     * @param i the test index
     * @return the output it should be
     */
    public double[] getOutput(int i) {
        return data[i][1];
    }
}
