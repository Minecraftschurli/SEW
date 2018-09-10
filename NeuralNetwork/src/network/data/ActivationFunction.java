package network.data;

import org.jetbrains.annotations.Contract;

import java.io.Serializable;

/**
 * Neural networks activation function interface.
 */
public interface ActivationFunction extends Serializable {

    /**
     * @param x
     * @return
     */
    @Contract(pure = true)
    static double sigmoid(double x) {
        return 1 / (1 + Math.pow(Math.E, -x));
    }

    /**
     *
     * @param x
     * @return
     */
    @Contract(pure = true)
    static double ReLu(double x) {
        return Math.max(0, x);
    }

    /**
     *
     * @param x
     * @return
     */
    @Contract(pure = true)
    static double tanh(double x) {
        return 2 * sigmoid(2 * x) - 1;
    }

    /**
     * Performs calculation based on the sum of input neurons output.
     *
     * @param summedInput neuron's sum of outputs respectively inputs for the connected
     *                    neuron
     * @return Output's calculation based on the sum of inputs
     */
    double getOutput(double summedInput);
}