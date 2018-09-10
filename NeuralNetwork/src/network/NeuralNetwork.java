package network;


import network.neuron.BIASNeuron;
import network.neuron.Connection;
import network.neuron.InputNeuron;
import network.neuron.Neuron;
import org.jetbrains.annotations.NotNull;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 *
 */
public class NeuralNetwork implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     *
     */
    private static final double LEARNING_RATE = 0.05;

    public final String name;
    /**
     *
     */
    public boolean consoleLog = false;
    protected NNLog log;
    /**
     *
     */
    protected BIASNeuron biasNeuron;
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
     * @param name               the Name of the NeuralNetwork
     * @param inputNeuronCount   the amount of input Neurons
     * @param hiddenNeuronLayers the amounts of hidden Neurons in each layer
     * @param outputNeuronCount  the amount of output Neurons
     * @return the NeuralNetwork
     */
    public static NeuralNetwork createGenericNN(String name, int inputNeuronCount, int[] hiddenNeuronLayers, int outputNeuronCount) {
        List<InputNeuron> inputNeurons = new ArrayList<>();
        List<Neuron> outputNeurons = new ArrayList<>();
        List<NeuralNetLayer> hiddenLayers = new ArrayList<>();

        for (int i = 0; i < inputNeuronCount; i++) {
            inputNeurons.add(i, new InputNeuron(i));
        }

        for (int i = 0; i < hiddenNeuronLayers.length; i++) {
            List<Neuron> hiddenLayerNeurons = new ArrayList<>();
            for (int j = 0; j < hiddenNeuronLayers[i]; j++) {
                Neuron neuron = new Neuron(j);
                neuron.setActivationFunction(ActivationFunction::sigmoid);
                hiddenLayerNeurons.add(j, neuron);
            }
            hiddenLayers.add(i, new NeuralNetLayer(i + 1, hiddenLayerNeurons));
        }

        for (int i = 0; i < outputNeuronCount; i++) {
            Neuron neuron = new Neuron(i);
            neuron.setActivationFunction(ActivationFunction::sigmoid);
            outputNeurons.add(neuron);
        }

        NeuralNetLayer outputLayer = new NeuralNetLayer(hiddenLayers.size() + 1, outputNeurons);
        InputLayer inputLayer = new InputLayer(inputNeurons);
        return new NeuralNetwork(name, inputLayer, hiddenLayers, outputLayer);
    }

    public static NeuralNetwork createCustomNN(@NotNull String name, InputLayer inputLayer, List<NeuralNetLayer> hiddenLayers, NeuralNetLayer outputLayer) {
        return new NeuralNetwork(name, inputLayer, hiddenLayers, outputLayer);
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
     * @param target
     * @param error
     * @param actual
     */
    private void adjustWeights(double error, double[] target, Double[] actual) {
        //String s, ss;
        for (int i = this.getLayerCount(); i > 0; i--) {
            NeuralNetLayer l = this.getLayer(i);
            for (int j = 0; j < l.neurons.size(); j++) {
                Neuron n = l.neurons.get(j);
                for (int k = 0; k < n.inputConnections(); k++) {
                    Connection c = n.getConnection(k);
                    //s = "Changing: " + c.toString() + "\n" + "From: " + c.getWeight() + "\n";
                    if (l == outputLayer) {
                        Double d = c.getWeight() - LEARNING_RATE * (-(actual[j] * c.getFromNeuron().getValue() * (1 - actual[j]) * (target[j] - actual[j])));
                        if (d.isInfinite() || d.isNaN()) {
                            //ss = d + "\n" + c.getWeight() + "\n" + actual[j] + " | " + c.getFromNeuron().getValue() + " | " + target[j] + "\n" + (-(actual[j] * c.getFromNeuron().getValue() * (1 - actual[j]) * (target[j] - actual[j])));
                            //log.log(ss);
                            //if (consoleLog) System.out.println(ss);
                        }
                        c.setWeight(d);
                    } else {

                    }
                    //s += "To: " + c.getWeight() + "\n";
                    //log.log(s);
                    //if (consoleLog) System.out.println(s);
                }
            }
        }
    }
}
