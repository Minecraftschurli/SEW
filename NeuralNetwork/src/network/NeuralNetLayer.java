package network;

import java.io.Serializable;
import java.util.List;

/**
 * Neural networks can be composed of several linked layers, forming the
 * so-called multilayer networks. A layer can be defined as a set of neurons
 * comprising a single neural net's layer.
 */
public class NeuralNetLayer implements Serializable {
    /**
     * Layer's identifier
     */
    public final int id;
    /**
     * The network this is part of
     */
    protected NeuralNetwork network;
    /**
     * Collection of neurons in this layer
     */
    protected List<? extends Neuron> neurons;

    /**
     * Creates a layer with a list of neurons and an id.
     *
     * @param id      the layer's identifier
     * @param neurons list of neurons to be added to the layer
     */
    public NeuralNetLayer(int id, List<? extends Neuron> neurons) {
        this.id = id;
        this.neurons = neurons;
    }

    /**
     * Sets the network this is part of
     *
     * @param network The network this is part of
     */
    public void setNetwork(NeuralNetwork network) {
        this.network = network;
    }

    /**
     *
     */
    protected void populateConnections() {
        if (id > 0) this.populateInputConnections();
        //if (id < network.getLayerCount()-2) this.populateOutputConnections();
    }

    /**
     *
     */
    private void populateOutputConnections() {
        this.neurons.forEach(neuron -> this.network.getLayer(id + 1).neurons.forEach(neuron1 -> neuron.outputConnections.add(new Connection(neuron, neuron1))));
    }

    /**
     *
     */
    private void populateInputConnections() {
        this.neurons.forEach(neuron -> network.getLayer(id - 1).neurons.forEach(neuron1 -> neuron.inputConnections.add(new Connection(neuron1, neuron))));
        this.neurons.forEach(neuron -> neuron.inputConnections.add(new Connection(network.biasNeuron, neuron)));
    }
}
