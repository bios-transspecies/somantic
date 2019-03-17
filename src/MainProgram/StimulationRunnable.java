package MainProgram;

import somantic.controller.Controller;
import static somantic.controller.Controller.recording;
import WNprocess.SomanticFacade;
import WNprocess.neuralModel.wordnet.WordNetToolbox;
import WNprocess.neuralModel.NeuralNetworkTrainer;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import javax.swing.JTextArea;
import javax.swing.JToggleButton;

public class StimulationRunnable implements Runnable {

    JToggleButton stimulateToggle;
    String stimulatedAlready;
    private final AudioFFT fft;
    private final SomanticFacade somanticFactory;
    private final Object stimulationRunnableLock = new Object();
    private final JTextArea communicationBox;
    private final NeuralNetworkTrainer networkTrainer = Interface.getNeuralNetworkTrainer();
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
            String[] words = WordNetToolbox.tokenize(Interface.getBufferedText());
            if (words.length < 1) {
                words = collectWordsWithoutAffect();
                if (words.length > 1) {
                   // Interface.setBufferedText(Arrays.asList(words).stream().reduce((a, b) -> a + " " + b).get());
                    Interface.setMessage(YOU_HAD_NOT_PROVIDED_TEST_TO_STIMULATE);
                } else {
                    stimulateToggle.setSelected(false);
                    Interface.setMessage(TO_PROCESS_STIMMULATION_PLEASE_IMPORT_TXT);
                }
            }
            for (int i = 0; i < words.length && stimulateToggle.isSelected() && recording; i++) {
                cutOffFromBuffer(words, i);
                Speaker.start(words[i]);
                if (!WordNetToolbox.explain(words[i]).isEmpty()) {
                    Interface.setWord(somanticFactory.getOrCreateWord(words[i], null));
                    try {
                        stimulationRunnableLock.wait(2500);
                    } catch (InterruptedException ex) {
                        Logger.getLogger(Controller.class.getName()).log(Level.SEVERE, null, ex);
                        Interface.setMessage(ex.getMessage());
                    }
                    List<Integer> affect = fft.getMatrix();
                    Integer wordId = somanticFactory.addAffectToWord(words[i], affect);
                    if (wordId != null) {
                        networkTrainer.addToLearningDataset(affect, wordId);
                        networkTrainer.learn();
                    }
                }
                if (Interface.getSaving() == false) {
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            try {
                                Interface.setSaving(true);
                                somanticFactory.saveRepo();
                            } catch (Exception e) {
                                Interface.setMessage(e.getMessage());
                            } finally {
                                Interface.setSaving(false);
                            }
                        }
                    }).start();
                }
            }
        }
    }

    private String[] collectWordsWithoutAffect() {
        String[] words;
        words = Interface.getSomanticFacade().getSomanticRepo().entrySet().stream()
                .filter((w) -> w.getValue().getAffects().isEmpty())
                .map(w -> w.getValue().getSentences())
                .flatMap(w -> w.stream())
                .collect(Collectors.toList()).stream()
                .flatMap(w -> w.stream())
                .map(w -> w.getWords())
                .flatMap(w -> w.stream())
                .collect(Collectors.joining(" "))
                .split(" ");
        return words;
    }

    private void cutOffFromBuffer(String[] words, int i) {
        Interface.setBufferedText(Interface.getBufferedText().replaceFirst(words[i], "").trim().replace("  ", " "));
        if (Interface.getBufferedText().length() > 0 && Interface.getBufferedText().charAt(0) == '.') {
            Interface.setBufferedText(Interface.getBufferedText().replaceFirst(".", "").trim());
        }
        if (Interface.getBufferedText().length() > 10000) {
            communicationBox.setText(Interface.getBufferedText().substring(0, Interface.getBufferedText().substring(0, 10000).lastIndexOf(".")));
        } else {
            communicationBox.setText(Interface.getBufferedText());
        }
    }
}
