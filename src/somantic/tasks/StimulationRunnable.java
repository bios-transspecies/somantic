package somantic.tasks;

import somantic.controller.Controller;
import static somantic.controller.Controller.recording;
import somantic.library.SomanticFacade;
import somantic.library.wordnet.WordNetToolbox;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import javax.swing.JTextArea;
import javax.swing.JToggleButton;
import somantic.neuralnetwork.NeuralNetworkTrainer;
import somantic.processors.AudioFFT;
import somantic.state.State;

public class StimulationRunnable implements Runnable {

    JToggleButton stimulateToggle;
    String stimulatedAlready;
    private final AudioFFT fft;
    private final SomanticFacade somanticFactory;
    private final Object stimulationRunnableLock = new Object();
    private final JTextArea communicationBox;
    private final NeuralNetworkTrainer networkTrainer = State.getNeuralNetworkTrainer();
    private static final String TO_PROCESS_STIMMULATION_PLEASE_IMPORT_TXT
            = "To process stimulation please import txt file or pdf or copy and paste some text below.";
    private static final String YOU_HAD_NOT_PROVIDED_TEST_TO_STIMULATE
            = "You had not provided text to stimulate so we are filling gaps in existing vocabulary.";

    public Object getStimulationRunnableLock() {
        return stimulationRunnableLock;
    }

    public StimulationRunnable(SomanticFacade riTaFactory, JToggleButton stimulateToggle, AudioFFT fft, JTextArea communicationBox) {
        this.somanticFactory = riTaFactory;
        this.stimulateToggle = stimulateToggle;
        this.fft = fft;
        this.communicationBox = communicationBox;
    }

    public void run() {
        synchronized (stimulationRunnableLock) {
            String[] words = WordNetToolbox.tokenize(State.getBufferedText());
            if (words.length < 1) {
                words = collectWordsWithoutAffect();
                if (words.length > 1) {
                    // State.setBufferedText(Arrays.asList(words).stream().reduce((a, b) -> a + " " + b).get());
                    State.setMessage(YOU_HAD_NOT_PROVIDED_TEST_TO_STIMULATE);
                } else {
                    stimulateToggle.setSelected(false);
                    State.setMessage(TO_PROCESS_STIMMULATION_PLEASE_IMPORT_TXT);
                }
            }
            for (int i = 0; i < words.length && stimulateToggle.isSelected() && recording; i++) {
                cutOffFromBuffer(words, i);
                Speaker.start(words[i]);
                if (!WordNetToolbox.explain(words[i]).isEmpty()) {
                    State.setWord(somanticFactory.getOrCreateWord(words[i], null));
                    try {
                        stimulationRunnableLock.wait(2500);
                    } catch (InterruptedException ex) {
                        Logger.getLogger(Controller.class.getName()).log(Level.SEVERE, null, ex);
                        State.setMessage(ex.getMessage());
                    }
                    List<Integer> affect = fft.getMatrix();
                    Integer wordId = somanticFactory.addAffectToWord(words[i], affect);
                    if (wordId != null) {
                        networkTrainer.addToLearningDataset(affect, wordId);
                        networkTrainer.learn();
                    }
                }
                if (State.getSaving() == false) {
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            try {
                                State.setSaving(true);
                                somanticFactory.saveRepo();
                            } catch (Exception e) {
                                State.setMessage(e.getMessage());
                            } finally {
                                State.setSaving(false);
                            }
                        }
                    }).start();
                }
            }
        }
    }

    private String[] collectWordsWithoutAffect() {
        String[] words;
        words = State.getSomanticFacade().getSomanticRepo().entrySet().stream()
                .filter((w) -> w.getValue().getAffects().isEmpty())
                .map(w -> w.getValue().getSentences())
                .flatMap(w -> w.stream())
                .collect(Collectors.toList()).stream()
                .flatMap(w -> w.stream())
                .map(w -> w!=null ? w.getWords() : new ArrayList<String>())
                .flatMap(w -> w.stream())
                .collect(Collectors.joining(" "))
                .split(" ");
        return words;
    }

    private void cutOffFromBuffer(String[] words, int i) {
        State.setBufferedText(State.getBufferedText().replaceFirst(words[i], "").trim().replace("  ", " "));
        if (State.getBufferedText().length() > 0 && State.getBufferedText().charAt(0) == '.') {
            State.setBufferedText(State.getBufferedText().replaceFirst(".", "").trim());
        }
        if (State.getBufferedText().length() > 10000) {
            communicationBox.setText(State.getBufferedText().substring(0, State.getBufferedText().substring(0, 10000).lastIndexOf(".")));
        } else {
            communicationBox.setText(State.getBufferedText());
        }
    }
}
