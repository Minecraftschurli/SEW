package network;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Represents a neuron model comprised of: </br>
 * <ul>
 * <li>Summing part  - input summing function</li>
 * <li>Activation function</li>
 * <li>Input connections</li>
 * <li>Output connections</li>
 * </ul>
 */
public class Neuron implements Serializable {
    /**
     * Collection of neuron's input connections (connections to this neuron)
     */
    protected List<Connection> inputConnections;
    /**
     * Collection of neuron's output connections (connections from this to other
     * neurons)
     */
    protected List<Connection> outputConnections;
    /**
     * Input summing function for this neuron
     */
    protected InputSummingFunction inputSummingFunction;
    /**
     * Activation function for this neuron
     */
    protected ActivationFunction activationFunction;
    protected double value;
    /**
     * Neuron's identifier
     */
    private int id;

    /**
     * Default constructor
     */
    public Neuron(int id) {
        this.id = id;
        this.inputConnections = new ArrayList<>();
        this.outputConnections = new ArrayList<>();
    }

    /**
     * Calculates the neuron's output
     */
    public double calculateOutput() {
        double totalInput = inputSummingFunction.getOutput(inputConnections);
        this.value = activationFunction.getOutput(totalInput);
        return this.value;
    }

    protected int getId() {
        return id;
    }

    public double getValue() {
        return value;
    }

    @Override
    public String toString() {
        return "NeuronID=" + this.id;
    }
}