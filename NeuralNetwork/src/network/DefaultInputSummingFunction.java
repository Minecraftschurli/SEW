package network;

import network.neuron.Connection;

import java.util.List;

public class DefaultInputSummingFunction implements InputSummingFunction {
    @Override
    public double getOutput(List<Connection> inputConnections) {
        double sum = 0.0;
        for (Connection inputConnection : inputConnections) {
            double weightedInput = inputConnection.getWeightedInput();
            sum += weightedInput;
        }
        return sum / inputConnections.size();
    }
}
