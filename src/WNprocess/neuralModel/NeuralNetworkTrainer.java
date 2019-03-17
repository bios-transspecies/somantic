package WNprocess.neuralModel;

import MainProgram.Interface;
import Persistence.Persistence;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
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
import org.neuroph.nnet.MultiLayerPerceptron;
import org.neuroph.nnet.learning.BackPropagation;
import org.neuroph.util.TransferFunctionType;

public class NeuralNetworkTrainer {

    private final MultiLayerPerceptron neuralNetwork;
    private static final String NEURAL_NETWORK_STORAGE_FILENAME = "neuralnetwork_state.nnet";
    private final DataSet trainingSet = new DataSet(100, 1);
    private final LearningEventListener listener;
    private final ExecutorService trainExecutor;
    private final AtomicBoolean busy = new AtomicBoolean();
    private ExecutorService askExecutor;
    private final BackPropagation backPropagation;

    public NeuralNetworkTrainer() {
        if (new File(NEURAL_NETWORK_STORAGE_FILENAME).exists()) {
            neuralNetwork = (MultiLayerPerceptron) NeuralNetwork.createFromFile(NEURAL_NETWORK_STORAGE_FILENAME);
            System.out.println("loading neural network from file: " + NEURAL_NETWORK_STORAGE_FILENAME);
        } else {
            neuralNetwork = new MultiLayerPerceptron(TransferFunctionType.GAUSSIAN, 100, 100, 50, 10, 1);
            neuralNetwork.randomizeWeights();
            System.out.println(" ---------> NEW instance of neural network created - no storage found!");
        }
        listener = (e) -> listener(e);
        backPropagation = (BackPropagation) neuralNetwork.getLearningRule();
        backPropagation.addListener(listener);
        backPropagation.setMaxIterations(10);
        trainExecutor = Executors.newSingleThreadExecutor();
        askExecutor = Executors.newSingleThreadExecutor();
    }

    public void useSomanticLibraryToLearn(JToggleButton b) {
        Executors.newSingleThreadExecutor().execute(() -> {
            setText("meditation");
            while (b.isSelected()) {
                if (!busy.get()) {
                    useSomanticLibraryToLearn();
                    setText(" [repeat] ");
                }
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException ex) {
                    Interface.setMessage(ex.getMessage());
                }
            }
        });
    }

    public int getSize() {
        return trainingSet.size();
    }

    public void addToLearningDataset(List<Integer> a, int hashCode) {
        trainExecutor.submit(() -> {
            addRowToLearningDataset(stringify(a), hashCode);
        });
    }

    public void stopLearning() {
        try {
            setText("stopping");
            neuralNetwork.stopLearning();
            backPropagation.stopLearning();
            trainExecutor.awaitTermination(2, TimeUnit.SECONDS);
            Persistence.saveNeuralNetwork(neuralNetwork, NEURAL_NETWORK_STORAGE_FILENAME);
        } catch (InterruptedException ex) {
            setText(ex.getMessage());
        }
    }

    public void learn() {
        setText("learn");
        if (!busy.get()) {
            trainExecutor.execute(() -> {
                setText("learning");
                neuralNetwork.learn(trainingSet, backPropagation);
                backPropagation.learn(trainingSet);
                trainingSet.clear();
                setText("done");
                Persistence.saveNeuralNetwork(neuralNetwork, NEURAL_NETWORK_STORAGE_FILENAME);
            });
        }
    }

    public Long ask(String affect) throws ExecutionException, InterruptedException {
        setText("ask");
        stopLearning();
        return askExecutor.submit(() -> {
            setText(" [asking] ");
            Interface.getNnStateLabel().setText("");
            Interface.setMessage("asking for: " + affect);
            neuralNetwork.setInput(stringToDoubleArray(affect));
            neuralNetwork.calculate();
            double[] networkOutput = neuralNetwork.getOutput();
            Double output = ((networkOutput[0] - 1) * Integer.MAX_VALUE) % Integer.MAX_VALUE;
            setText(" [output: " + output + "] ");
            Long response = output.longValue();
            Interface.setMessage("found response: " + response);
            setText("[response: " + response.toString() + "]");
            return response;
        }).get();
    }

    private void listener(LearningEvent e) {
        LearningEvent.Type t = e.getEventType();
        setText(" [" + t.name().toLowerCase().replace("_", " ") + "] ");
    }

    private String stringify(List<Integer> a) {
        return (new ArrayList<Integer>(a)).stream()
                .map(b -> String.valueOf(b))
                .collect(Collectors.joining(","));
    }

    private void addRowToLearningDataset(String affect, Integer somanticWordId) {
        setText("adding row to learning dataset");
        double[] affectToTrain = stringToDoubleArray(affect);
        double[] result = new double[1];
        result[0] = somanticWordId.doubleValue() / Integer.MAX_VALUE;
        trainingSet.addRow(new DataSetRow(affectToTrain, result));
    }

    private double[] stringToDoubleArray(String affect) {
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

    private void useSomanticLibraryToLearn(){
        try {
            trainExecutor.submit(() -> {
                Interface.getSomanticFacade()
                        .getSomanticRepo()
                        .entrySet()
                        .forEach(a -> a.getValue()
                                .getAffects()
                                .forEach(
                                        v -> addRowToLearningDataset(stringify(v), a.getValue().hashCode())
                                ));
                Interface.getNnStateLabel().setText("");
            }).get();
            learn();
        } catch (InterruptedException ex) {
            Logger.getLogger(NeuralNetworkTrainer.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ExecutionException ex) {
            Logger.getLogger(NeuralNetworkTrainer.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void setText(String text) {
        JLabel l = Interface.getNnStateLabel();
        String t = l.getText().concat(text);
        if (l.getText().equals(text)) {
            t = "";
        } else if (l.getText().contains(text)) {
            t = text;

        } else {
            System.out.println(t);
        }
        if (t.length() > 40) {
            l.setText(text);
        } else {
            l.setText(t.replace("  ", " "));
        }
    }
}
