package network;

import network.neuron.InputNeuron;

import java.util.List;

public class InputLayer extends NeuralNetLayer {
    /**
     * Creates a layer with a list of neurons and an id.
     *
     * @param neurons a List of input neurons
     */
    public InputLayer(List<InputNeuron> neurons) {
        super(0, neurons);
    }

    @Override
    public void setNetwork(NeuralNetwork network) {
        super.setNetwork(network);
        this.neurons.forEach(o -> ((InputNeuron) o).setNetwork(this.network));
    }
}
