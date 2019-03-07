package WNprocess.neuralModel;

import java.io.File;
import org.neuroph.core.NeuralNetwork;
import org.neuroph.core.data.DataSet;
import org.neuroph.core.data.DataSetRow;
import org.neuroph.nnet.MultiLayerPerceptron;
import org.neuroph.util.TransferFunctionType;

public class NeuralNetworkTrainer {

    private final NeuralNetwork neuralNetwork;
    private static final String PERCEPTRONNNET = "Perceptron.nnet";


    public NeuralNetworkTrainer() {
        if (new File(PERCEPTRONNNET).exists()) {
            neuralNetwork = NeuralNetwork.createFromFile(PERCEPTRONNNET);
        } else {
            neuralNetwork = new MultiLayerPerceptron(TransferFunctionType.GAUSSIAN, 100, 30, 10, 1);
        }
    }
    public void train(String affect, Integer somanticWordId) {
        double[] affectToTrain = affectToTrainData(affect);
        double[] result = new double[1];
        result[0] = somanticWordId.doubleValue();
        DataSet trainingSet = new DataSet(100, 1);
        trainingSet.addRow(new DataSetRow(affectToTrain, result));
        neuralNetwork.learn(trainingSet);
        neuralNetwork.save(PERCEPTRONNNET);
    }

    public Long ask(String affect) {
            neuralNetwork.setInput(affectToTrainData(affect));
            neuralNetwork.calculate();
            double[] networkOutput = neuralNetwork.getOutput();
            return new Double(networkOutput[0]).longValue();
    }

    private double[] affectToTrainData(String affect) {
        String[] arrayAffect = affect.split(",");
        double[] affectToTrain = new double[arrayAffect.length];
        for (int i = 0; i < arrayAffect.length; i++) {
            affectToTrain[i] = Double.parseDouble(arrayAffect[i].trim());
        }
        return affectToTrain;
    }
}