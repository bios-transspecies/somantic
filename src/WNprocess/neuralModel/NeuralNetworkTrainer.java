package WNprocess.neuralModel;

import MainProgram.Interface;
import Persistence.Persistence;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.logging.Level;
import java.util.logging.Logger;
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
        executor.execute(() -> {
            Interface.getSomanticFacade()
                    .getSomanticRepo()
                    .entrySet()
                    .forEach(a -> a.getValue()
                    .getAffects()
                    .forEach(
                            v -> addToLearningDataset(v, a.hashCode())));
        });
    }

    private void listenerImpl(LearningEvent e) {
        LearningEvent.Type t = e.getEventType();
        Persistence.saveNeuralNetwork(neuralNetwork, NEURAL_NETWORK_STORAGE_FILENAME);
    }

    public int getSize() {
        return trainingSet.size();
    }

    public void addToLearningDataset(List<Integer> a, int hashCode) {
        addRowToLearningDataset(stringify(a), hashCode);
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
        stopLearning();
        System.out.println("asking for: "+ affect);
        neuralNetwork.setInput(affectToDoubleArray(affect));
        neuralNetwork.calculate();
        double[] networkOutput = neuralNetwork.getOutput();
        Long response = new Double(networkOutput[0]).longValue();
        System.out.println("response: "+ response);
        return response;
    }

    private String stringify(List<Integer> a) {
        return (new ArrayList<Integer>(a)).stream()
                .map(b -> String.valueOf(b))
                .collect(Collectors.joining(","));
    }

    private void addRowToLearningDataset(String affect, Integer somanticWordId) {
        stopLearning();
        executor.execute(() -> {
            System.out.println("preparing");
            System.out.println("somanticWordId " + somanticWordId);
            double[] affectToTrain = affectToDoubleArray(affect);
            double[] result = new double[1];
            result[0] = somanticWordId.doubleValue();
            trainingSet.addRow(new DataSetRow(affectToTrain, result));
            lr.learn(trainingSet);
        });
    }

    private double[] affectToDoubleArray(String affect) {
        String[] arrayAffect = affect.split(",");
        double[] doublearray = new double[arrayAffect.length];
        for (int i = 0; i < arrayAffect.length; i++) {
            doublearray[i] = Double.parseDouble(arrayAffect[i].trim());
        }
        return doublearray;
    }

    public Long ask(List<Integer> recentAffects) {
        try {
            return ask(stringify(recentAffects));
        } catch (ExecutionException ex) {
            Logger.getLogger(NeuralNetworkTrainer.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InterruptedException ex) {
            Logger.getLogger(NeuralNetworkTrainer.class.getName()).log(Level.SEVERE, null, ex);
        }
        return 0L;
    }
}
