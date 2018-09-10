package network;

import java.io.Serializable;
import java.util.ArrayList;

public class BIASNeuron extends Neuron implements Serializable {

    /**
     * Default constructor
     */
    public BIASNeuron() {
        super(-1);
        inputConnections = new ArrayList<>();
        inputSummingFunction = null;
        activationFunction = null;
    }

    @Override
    public double calculateOutput() {
        return 1;
    }
}
