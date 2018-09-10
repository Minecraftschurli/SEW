package network;


import network.data.ActivationFunction;
import network.data.DataSet;
import network.data.NNLog;
import network.neuron.BIASNeuron;
import network.neuron.Connection;
import network.neuron.InputNeuron;
import network.neuron.Neuron;
import network.structure.InputLayer;
import network.structure.NeuralNetLayer;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 *
 */
public class NeuralNetwork implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    /**
     *
     */
    private static final double LEARNING_RATE = 0.5;

    /**
     *
     */
    public final String name;

    /**
     *
     */
    public final List<Connection> connections;

    /**
     *
     */
    protected NNLog log;

    /**
     *
     */
    public final BIASNeuron biasNeuron;

    /**
     *
     */
    private InputLayer inputLayer;

    /**
     *
     */
    private List<NeuralNetLayer> hiddenLayers;

    /**
     *
     */
    private NeuralNetLayer outputLayer;

    /**
     *
     */
    private double[] input;

    /**
     * @param name
     * @param inputLayer
     * @param hiddenLayers
     * @param outputLayer
     */
    private NeuralNetwork(String name, InputLayer inputLayer, List<NeuralNetLayer> hiddenLayers, NeuralNetLayer outputLayer) {
        this.connections = new ArrayList<>();
        this.name = name;
        this.biasNeuron = new BIASNeuron();
        this.inputLayer = inputLayer;
        this.hiddenLayers = hiddenLayers;
        this.outputLayer = outputLayer;
        this.inputLayer.setNetwork(this);
        this.hiddenLayers.forEach(neuralNetLayer -> neuralNetLayer.setNetwork(this));
        this.outputLayer.setNetwork(this);
        this.inputLayer.populateConnections();
        this.hiddenLayers.forEach(NeuralNetLayer::populateConnections);
        this.outputLayer.populateConnections();
        log = new NNLog(this);
    }

    /**
     * @param id
     * @return
     */
    public NeuralNetLayer getLayer(int id) {
        if (id < 0) return null;
        if (id == 0) return this.inputLayer;
        if (id > hiddenLayers.size()) return this.outputLayer;
        for (NeuralNetLayer layer : hiddenLayers) {
            if (id == layer.id) return layer;
        }
        return null;
    }

    /**
     * @return
     */
    public int getLayerCount() {
        return this.hiddenLayers.size() + 2;
    }

    /**
     * @param input
     */
    public void sendData(double[] input) {
        this.input = input;
    }

    /**
     * @param id
     * @return
     */
    public double getInput(int id) {
        return input[id];
    }

    /**
     * @return
     */
    public Double[] getOutput() {
        List<Double> out = new ArrayList<>();
        outputLayer.neurons.forEach(o -> out.add(o.calculateOutput()));
        Double[] output = out.toArray(new Double[0]);
        return output;
    }

    /**
     * @param trainingData
     */
    public void train(DataSet trainingData) {
        double error = 0;
        for (int i = 0; i < trainingData.size(); i++) {
            double[] data = trainingData.getTest(i);
            this.sendData(data);
            Double[] output = this.getOutput();
            error = trainingData.calcError(i, output);
            this.adjustWeights(error, trainingData.getOutput(i), output);
        }
    }

    /**
     * @param name               the Name of the NeuralNetwork
     * @param inputNeuronCount   the amount of input Neurons
     * @param hiddenNeuronLayers the amounts of hidden Neurons in each layer
     * @param outputNeuronCount  the amount of output Neurons
     * @return the NeuralNetwork
     */
    @NotNull
    @Contract("_, _, _, _ -> new")
    public static NeuralNetwork createGenericNN(String name, int inputNeuronCount, int[] hiddenNeuronLayers, int outputNeuronCount) {
        List<InputNeuron> inputNeurons = new ArrayList<>();
        List<Neuron> outputNeurons = new ArrayList<>();
        List<NeuralNetLayer> hiddenLayers = new ArrayList<>();
        //region input neurons
        for (int i = 0; i < inputNeuronCount; i++) {
            inputNeurons.add(i, new InputNeuron(i));
        }
        //endregion
        //region hidden neurons
        for (int i = 0; i < hiddenNeuronLayers.length; i++) {
            List<Neuron> hiddenLayerNeurons = new ArrayList<>();
            for (int j = 0; j < hiddenNeuronLayers[i]; j++) {
                Neuron neuron = new Neuron(j);
                neuron.setActivationFunction(i % 2 == 0 ? ActivationFunction::sigmoid : ActivationFunction::ReLu);
                hiddenLayerNeurons.add(j, neuron);
            }
            hiddenLayers.add(i, new NeuralNetLayer(i + 1, hiddenLayerNeurons));
        }
        //endregion
        //region output neurons
        for (int i = 0; i < outputNeuronCount; i++) {
            Neuron neuron = new Neuron(i);
            neuron.setActivationFunction(ActivationFunction::sigmoid);
            outputNeurons.add(neuron);
        }
        //endregion
        NeuralNetLayer outputLayer = new NeuralNetLayer(hiddenLayers.size() + 1, outputNeurons);
        InputLayer inputLayer = new InputLayer(inputNeurons);
        return new NeuralNetwork(name, inputLayer, hiddenLayers, outputLayer);
    }

    /**
     * @param name
     * @param inputLayer
     * @param hiddenLayers
     * @param outputLayer
     * @return
     */
    @NotNull
    @Contract("_, _, _, _ -> new")
    public static NeuralNetwork createCustomNN(@NotNull String name, InputLayer inputLayer, List<NeuralNetLayer> hiddenLayers, NeuralNetLayer outputLayer) {
        return new NeuralNetwork(name, inputLayer, hiddenLayers, outputLayer);
    }

    /**
     * @param target
     * @param error
     * @param actual
     */
    private void adjustWeights(double error, double[] target, Double[] actual) {
        for (int i = this.getLayerCount(); i > 0; i--) {
            NeuralNetLayer l = this.getLayer(i);
            for (int j = 0; j < l.neurons.size(); j++) {
                Neuron n = l.neurons.get(j);
                for (int k = 0; k < n.inputConnections(); k++) {
                    Connection c = n.getConnection(k);
                    if (l == outputLayer) {
                        c.setDeltaWeight((-(actual[j] * c.getFromNeuron().getValue() * (1 - actual[j]) * (target[j] - actual[j]))));
                    } else {
                        double sum = 0;
                        for (Neuron neuron : getLayer(l.id + 1).neurons) {
                            sum += getConnection(n, neuron).getDeltaWeight();
                        }
                        c.setDeltaWeight(sum * (n.getValue() * (1 - n.getValue())) * c.getFromNeuron().getValue());
                    }
                    double d = c.getWeight() - LEARNING_RATE * c.getDeltaWeight();
                    c.setWeight(d);
                }
            }
        }
    }

    /**
     * @param neuron1
     * @param neuron2
     * @return
     */
    @Nullable
    private Connection getConnection(Neuron neuron1, Neuron neuron2) {
        for (Connection con : connections) {
            if ((con.getFromNeuron() == neuron1 && con.getToNeuron() == neuron2) || (con.getFromNeuron() == neuron2 && con.getToNeuron() == neuron1)) {
                return con;
            }
        }
        return null;
    }

}
