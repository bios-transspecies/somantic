package WNprocess.neuralModel;

import Persistence.Persistence;
import java.io.File;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;
import org.neuroph.core.NeuralNetwork;
import org.neuroph.core.data.DataSet;
import org.neuroph.core.data.DataSetRow;
import org.neuroph.nnet.SupervisedHebbianNetwork;
import org.neuroph.util.TransferFunctionType;

public class NeuralNetworkTrainer {

    private final NeuralNetwork neuralNetwork;
    private static final String PERCEPTRONNNET = "Perceptron.nnet";
    private final DataSet trainingSet = new DataSet(100, 1);
    private final Object lock = new Object();
    private final ExecutorService executor;

    public NeuralNetworkTrainer() {
        if (new File(PERCEPTRONNNET).exists()) {
            neuralNetwork = NeuralNetwork.createFromFile(PERCEPTRONNNET);
            System.out.println(PERCEPTRONNNET);
        } else {
            neuralNetwork = new SupervisedHebbianNetwork(100, 1, TransferFunctionType.STEP);
            neuralNetwork.randomizeWeights();
            System.out.println("new instance");
        }
        executor = Executors.newSingleThreadExecutor();
    }

    public int getSize() {
        synchronized (lock) {
            return trainingSet.size();
        }
    }

    public void train(List<Integer> a, int hashCode) {
        synchronized (lock) {
            this.executor.execute(() -> train(a.stream().map(b -> String.valueOf(b)).collect(Collectors.joining(",")), hashCode));
        }
    }

    public void learn() {
        synchronized (lock) {
            this.executor.execute(() -> {
                System.out.println("start training");
                if (!trainingSet.isEmpty() && neuralNetwork != null) {
                    neuralNetwork.learn(trainingSet);
                    System.out.println("saving results");
                    trainingSet.clear();
                    Persistence.saveNeuralNetwork(neuralNetwork, PERCEPTRONNNET);
                }
            });
        }
    }

    private void train(String affect, Integer somanticWordId) {
        synchronized (lock) {
            this.executor.execute(() -> {
                System.out.println("preparing");
                System.out.println("somanticWordId " + somanticWordId);
                double[] affectToTrain = affectToTrainData(affect);
                double[] result = new double[1];
                result[0] = somanticWordId.doubleValue();
                trainingSet.addRow(new DataSetRow(affectToTrain, result));
            });
        }
    }

    public Long ask(String affect) {
        synchronized (lock) {
            neuralNetwork.setInput(affectToTrainData(affect));
            neuralNetwork.calculate();
            double[] networkOutput = neuralNetwork.getOutput();
            return new Double(networkOutput[0]).longValue();
        }
    }

    private double[] affectToTrainData(String affect) {
        System.out.println("affect " + affect);
        String[] arrayAffect = affect.split(",");
        double[] affectToTrain = new double[arrayAffect.length];
        for (int i = 0; i < arrayAffect.length; i++) {
            affectToTrain[i] = Double.parseDouble(arrayAffect[i].trim());
        }
        System.out.println("transformed to affectToTrain size: ");
        return affectToTrain;
    }
}
