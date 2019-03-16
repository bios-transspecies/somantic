package WNprocess.neuralModel;

import MainProgram.Interface;
import Persistence.Persistence;
import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import javax.swing.JLabel;
import javax.swing.JToggleButton;
import org.neuroph.core.NeuralNetwork;
import org.neuroph.core.data.DataSet;
import org.neuroph.core.data.DataSetRow;
import org.neuroph.core.events.LearningEvent;
import org.neuroph.core.events.LearningEventListener;
import org.neuroph.core.learning.LearningRule;
import org.neuroph.nnet.MultiLayerPerceptron;
import org.neuroph.nnet.learning.BackPropagation;
import org.neuroph.util.TransferFunctionType;

public class NeuralNetworkTrainer {

    private final NeuralNetwork neuralNetwork;
    private static final String NEURAL_NETWORK_STORAGE_FILENAME = "neuralnetwork_state.nnet";
    private final DataSet trainingSet = new DataSet(100, 1);
    private final LearningEventListener listener;
    private final LearningRule lr;
    private final ExecutorService executor;
    private final BackPropagation backPropagation = new BackPropagation();
    private final AtomicBoolean isLearning;

    public NeuralNetworkTrainer() {
        if (new File(NEURAL_NETWORK_STORAGE_FILENAME).exists()) {
            neuralNetwork = NeuralNetwork.createFromFile(NEURAL_NETWORK_STORAGE_FILENAME);
            System.out.println("loading neural network from file: " + NEURAL_NETWORK_STORAGE_FILENAME);
        } else {
            neuralNetwork = new MultiLayerPerceptron(TransferFunctionType.GAUSSIAN, 100, 100, 50, 10, 1);
            neuralNetwork.randomizeWeights();
            System.out.println(" ---------> NEW instance of neural network created - no storage found!");
        }
        listener = (e) -> listener(e);
        lr = neuralNetwork.getLearningRule();
        lr.addListener(listener);
        executor = Executors.newSingleThreadExecutor();
        backPropagation.setMaxIterations(100);
        isLearning = new AtomicBoolean(false);
    }

    private void useSomanticLibraryToLearn() {
        setText(" [meditating] ");
        if (!isLearning.get()) {
            Interface.getSomanticFacade()
                    .getSomanticRepo()
                    .entrySet()
                    .forEach(a -> a.getValue()
                    .getAffects()
                    .forEach(
                            v -> addToLearningDataset(v, a.getValue().hashCode())
                    ));
            learn();
            Interface.getNnStateLabel().setText("");
        }
    }

    private void listener(LearningEvent e) {
        LearningEvent.Type t = e.getEventType();
        setText(" ["+t.name().toLowerCase().replace("_", " ")+"] ");
        Persistence.saveNeuralNetwork(neuralNetwork, NEURAL_NETWORK_STORAGE_FILENAME);
    }

    public int getSize() {
        return trainingSet.size();
    }

    public void addToLearningDataset(List<Integer> a, int hashCode) {
        addRowToLearningDataset(stringify(a), hashCode);
    }

    public void stopLearning() {
        neuralNetwork.stopLearning();
        executor.submit(() -> trainingSet.clear());
    }

    public void learn() {
        if (!isLearning.get()) {
            executor.execute(() -> {
                isLearning.set(true);
                neuralNetwork.learn(trainingSet, backPropagation);
                lr.learn(trainingSet);
                trainingSet.clear();
                isLearning.set(false);
            });
        }
    }

    private void setText(String text) {
        JLabel l = Interface.getNnStateLabel();
        String t = l.getText().replace(text, "").concat(text);
        if(t.length()>40)
            l.setText(text);
        else
            l.setText(t.replace("  ", " "));
    }

    public Long ask(String affect) throws ExecutionException, InterruptedException {
        stopLearning();
        setText(" [asking] ");
        Interface.getNnStateLabel().setText("");
        Interface.setMessage("asking for: " + affect);
        neuralNetwork.setInput(affectToDoubleArray(affect));
        neuralNetwork.calculate();
        double[] networkOutput = neuralNetwork.getOutput();
        Double output = ((networkOutput[0] - 1) * Integer.MAX_VALUE) % Integer.MAX_VALUE; 
        setText(" [output: "+ output +"] ");
        Long response = output.longValue();
        Interface.setMessage("found response: " + response);
        setText("[response: " + response.toString() + "]");
        return response;
    }

    private String stringify(List<Integer> a) {
        return (new ArrayList<Integer>(a)).stream()
                .map(b -> String.valueOf(b))
                .collect(Collectors.joining(","));
    }

    private void addRowToLearningDataset(String affect, Integer somanticWordId) {
        executor.execute(() -> {
            double[] affectToTrain = affectToDoubleArray(affect);
            double[] result = new double[1];
            result[0] = somanticWordId.doubleValue() / Integer.MAX_VALUE;
            trainingSet.addRow(new DataSetRow(affectToTrain, result));
            //setText(" [dataset] " + result[0]);
            if (!isLearning.get()) {
                learn();
            }
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

    public void useSomanticLibraryToLearn(JToggleButton b) {
        Executors.newSingleThreadExecutor().execute(() -> {
            while (b.isSelected()) {
                if (!isLearning.get()) {
                    useSomanticLibraryToLearn();
                }
                Interface.getNnStateLabel().setText("");
                setText(" [repeat] ");
                try {
                    Thread.sleep(10);
                } catch (InterruptedException ex) {
                    Interface.setMessage(ex.getMessage());
                }
            }
        });
    }
}
