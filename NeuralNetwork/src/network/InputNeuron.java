package network;

public class InputNeuron extends Neuron {
    protected NeuralNetwork network;

    public InputNeuron(int id) {
        super(id);
        this.inputConnections = null;
        this.activationFunction = null;
        this.inputSummingFunction = null;
    }

    public void setNetwork(NeuralNetwork network) {
        this.network = network;
    }

    @Override
    public double calculateOutput() {
        return this.network.getInput(this.getId());
    }
}
