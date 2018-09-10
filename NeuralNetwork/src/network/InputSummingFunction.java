package network;

import java.io.Serializable;
import java.util.List;

public interface InputSummingFunction extends Serializable {
    /**
     * Performs calculations based on the output values of the input neurons.
     *
     * @param inputConnections neuron's input connections
     * @return total input for the neuron having the input connections
     */
    double getOutput(List<Connection> inputConnections);
}
