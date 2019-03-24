package somantic.neuralnetwork;

import static somantic.Main.MAX_WORDS_IN_REPOSITORY;
import somantic.state.State;
import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
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
import org.neuroph.core.Neuron;
import org.neuroph.core.data.DataSet;
import org.neuroph.core.data.DataSetRow;
import org.neuroph.core.events.LearningEvent;
import org.neuroph.core.events.LearningEventListener;
import org.neuroph.nnet.MultiLayerPerceptron;
import org.neuroph.nnet.learning.BackPropagation;
import org.neuroph.util.TransferFunctionType;
import somantic.persistence.Persistence;

public class NeuralNetworkTrainer {

    private static final String NEURAL_NETWORK_STORAGE_FILENAME = "neuralnetwork_state.nnet";
    private volatile MultiLayerPerceptron neuralNetwork;
    private volatile BackPropagation backPropagation;
    private volatile DataSet trainingSet = new DataSet(100, MAX_WORDS_IN_REPOSITORY);
    private final LearningEventListener listener;
    private final ExecutorService trainExecutor;
    private final AtomicBoolean busy = new AtomicBoolean();
    private final ExecutorService askExecutor;

    public NeuralNetworkTrainer() {
        if (new File(NEURAL_NETWORK_STORAGE_FILENAME).exists()) {
            // System.out.println("loading neural network from file: " + NEURAL_NETWORK_STORAGE_FILENAME);
            neuralNetwork = (MultiLayerPerceptron) NeuralNetwork.createFromFile(NEURAL_NETWORK_STORAGE_FILENAME);
        } else {
            // System.out.println(" ---------> NEW instance of neural network created - no storage found!");
            neuralNetwork = new MultiLayerPerceptron(
                    TransferFunctionType.GAUSSIAN, 100, 10, 50, MAX_WORDS_IN_REPOSITORY);
            neuralNetwork.randomizeWeights();
            // System.out.println("MAX_WORDS_IN_REPOSITORY: "+ MAX_WORDS_IN_REPOSITORY);
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
                    setText("repeat");
                }
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException ex) {
                    State.setMessage(ex.getMessage());
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
            trainingSet.clear();
            trainExecutor.awaitTermination(2, TimeUnit.SECONDS);
            askExecutor.awaitTermination(2, TimeUnit.SECONDS);
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
                trainingSet.clear();
                setText("done");
                Persistence.saveNeuralNetwork(neuralNetwork, NEURAL_NETWORK_STORAGE_FILENAME);
            });
        }
    }

    public Integer ask(String affect) throws ExecutionException, InterruptedException {
        setText("ask");
        stopLearning();
        return askExecutor.submit(() -> {
            setText("asking");
            State.getNnStateLabel().setText("");
            State.setMessage("asking for: " + affect);
            neuralNetwork.setInput(stringToDoubleArray(affect));
            neuralNetwork.calculate();
            Integer result = findWordId(neuralNetwork.getOutputNeurons());
            State.setMessage("found response: " + result);
            setText("response: " + result.toString());
            return result;
        }).get();
    }

    private Integer findWordId(Neuron[] networkOutput) {
        Integer result = 0;
        double lastvalue = Integer.MIN_VALUE;
        for (int i = 0; i < networkOutput.length; i++) {
            double value = networkOutput[i].getOutput();
            boolean yes = value > lastvalue;
            result = yes ? i : result;
            lastvalue = yes ? value : lastvalue;
            if (value>0)
                 System.out.println(value);
        }
        if(result>0)
             System.out.println("the winner is: "+result);
        return result;
    }

    private void listener(LearningEvent e) {
        LearningEvent.Type t = e.getEventType();
        // System.out.println(t);
        if(t==LearningEvent.Type.LEARNING_STOPPED)
            busy.set(false);
        setText(t.name().toLowerCase().replace("_", " "));
    }

    private String stringify(List<Integer> a) {
        return (new ArrayList<Integer>(a)).stream()
                .map(b -> String.valueOf(b))
                .collect(Collectors.joining(","));
    }

    private void addRowToLearningDataset(String affect, Integer somanticWordId) {
        setText("adding row to learning dataset");
        // System.out.println("somanticWordId: " + somanticWordId + " / affect: " + affect);
        double[] affectToTrain = stringToDoubleArray(affect);
        double[] result = new double[MAX_WORDS_IN_REPOSITORY];
        Arrays.fill(result, 0);
        result[Math.abs(somanticWordId)] = 1;
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

    public Integer ask(List<Integer> recentAffects) {
        try {
            return ask(stringify(recentAffects));
        } catch (ExecutionException ex) {
            Logger.getLogger(NeuralNetworkTrainer.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InterruptedException ex) {
            Logger.getLogger(NeuralNetworkTrainer.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    private void useSomanticLibraryToLearn() {
        try {
            Boolean res = trainExecutor.submit(() -> {
                State.getSomanticFacade()
                        .getSomanticRepo()
                        .entrySet()
                        .forEach(a -> a.getValue()
                                .getAffects()
                                .forEach(
                                        v -> addRowToLearningDataset(
                                                stringify(v), 
                                                a.getValue().getId()
                                        )
                                ));
                State.getNnStateLabel().setText("");
                return true;
            }).get();
           // if(res)
                learn();
        } catch (InterruptedException ex) {
            Logger.getLogger(NeuralNetworkTrainer.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ExecutionException ex) {
            Logger.getLogger(NeuralNetworkTrainer.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void setText(String text) {
        JLabel l = State.getNnStateLabel();
        // System.out.println(text);
        String t = l.getText().concat(" [" + text + "]");
        if (l.getText().equals("[" + text + "]")) {
            t = "";
        } else if (l.getText().contains(text)) {
            t = "[" + text + "]";
        } else {
           // // System.out.println(t);
        }
        if (t.length() > 40) {
            l.setText(text);
        } else {
            l.setText(t.replace("  ", " "));
        }
    }
}
