package WNprocess.neuralModel;

import MainProgram.Interface;
import Persistence.Persistence;
import java.io.File;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;
import org.neuroph.core.NeuralNetwork;
import org.neuroph.core.data.DataSet;
import org.neuroph.core.data.DataSetRow;
import org.neuroph.core.events.LearningEvent;
import org.neuroph.core.events.LearningEventListener;
import org.neuroph.core.learning.LearningRule;
import org.neuroph.nnet.SupervisedHebbianNetwork;
import org.neuroph.util.TransferFunctionType;

public class NeuralNetworkTrainer {

    private final NeuralNetwork neuralNetwork;
    private static final String NEURAL_NETWORK_STORAGE_FILENAME = "neuralnetwork_state.nnet";
    private final DataSet trainingSet = new DataSet(100, 1);
    private final LearningEventListener listener;
    private final LearningRule lr;
    private final ExecutorService executor;

    public NeuralNetworkTrainer() {
        if (new File(NEURAL_NETWORK_STORAGE_FILENAME).exists()) {
            neuralNetwork = NeuralNetwork.createFromFile(NEURAL_NETWORK_STORAGE_FILENAME);
            System.out.println("loading neural network from file: " + NEURAL_NETWORK_STORAGE_FILENAME);
        } else {
            neuralNetwork = new SupervisedHebbianNetwork(100, 1, TransferFunctionType.STEP);
            neuralNetwork.randomizeWeights();
            System.out.println(" ---------> NEW instance of neural network created - no storage found!");
        }
        listener = (e) -> listenerImpl(e);
        lr = neuralNetwork.getLearningRule();
        lr.addListener(listener);
        executor = Executors.newSingleThreadExecutor();
    }

    public void useSomanticLibraryToLearn() {
        Interface.getSomanticFactory()
                .getSomanticRepo()
                .entrySet()
                .forEach( a -> a.getValue()
                                .getAffects()
                                .forEach(
                                        v -> addToLearningDataset(v, a.hashCode())));
        learn();
    }

    private void listenerImpl(LearningEvent e) {
        LearningEvent.Type t = e.getEventType();
        System.out.println(" listener triggered " + t.name());
        System.out.println(e.toString());
        System.out.println(" HURRA! ----------------------- > saving results");
        Persistence.saveNeuralNetwork(neuralNetwork, NEURAL_NETWORK_STORAGE_FILENAME);
    }

    public int getSize() {
        return trainingSet.size();
    }

    public void addToLearningDataset(List<Integer> a, int hashCode) {
        addRowToLearningDataset(process(a), hashCode);
    }

    public void stopLearning() {
        if (!lr.isStopped()) {
            lr.stopLearning();
        }
    }

    public void learn() {
        stopLearning();
        executor.execute(() -> {
            lr.learn(trainingSet);
        });
    }

    public Long ask(String affect) throws ExecutionException, InterruptedException {
        return executor.submit(() -> {
            neuralNetwork.setInput(affectToTrainData(affect));
            neuralNetwork.calculate();
            double[] networkOutput = neuralNetwork.getOutput();
            return new Double(networkOutput[0]).longValue();
        }).get();
    }

    private String process(List<Integer> a) {
        return a.stream()
                .map(b -> String.valueOf(b))
                .collect(Collectors.joining(","));
    }

    private void addRowToLearningDataset(String affect, Integer somanticWordId) {
        stopLearning();
        executor.execute(() -> {
            System.out.println("preparing");
            System.out.println("somanticWordId " + somanticWordId);
            double[] affectToTrain = affectToTrainData(affect);
            double[] result = new double[1];
            result[0] = somanticWordId.doubleValue();
            trainingSet.addRow(new DataSetRow(affectToTrain, result));
        });
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
