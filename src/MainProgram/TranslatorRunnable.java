package MainProgram;

import somantic.controller.Controller;
import WNprocess.SomanticFacade;
import WNprocess.SomanticRepository;
import WNprocess.SomanticWord;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JTextArea;
import javax.swing.JToggleButton;

public class TranslatorRunnable implements Runnable {

    private final JToggleButton translateToggle;
    private final JTextArea communicationBox;
    private final AudioFFT fft;
    private final SomanticFacade somanticRepo;
    private final Object translatorRunnablelock = new Object();

    public Object getTranslatorRunnablelock() {
        return translatorRunnablelock;
    }

    public TranslatorRunnable(JToggleButton translateToggle, JTextArea communicationBox, AudioFFT fft, SomanticFacade riTaFactory) {
        this.translateToggle = translateToggle;
        this.communicationBox = communicationBox;
        this.fft = fft;
        this.somanticRepo = riTaFactory;
    }

    @Override
    public void run() {
        synchronized (translatorRunnablelock) {
            List<String> skip = new ArrayList<>();
            while (translateToggle.isSelected()) {
                List<Integer> recentAffects = fft.getMatrix();
                if (Interface.getWords().split(" ").length > 15) {
                    Interface.addSentence(Interface.getStringSentence());
                    Interface.setWords(" ");
                }
                if (recentAffects.size() > 99) {
                    long stopienPokrewienstwaMax = 0L;
                    long stopienPokrewienstwaMin = 0L;

                    SomanticWord rezultat = null;
                    Long neuralResult = Interface.getNeuralNetworkTrainer().ask(recentAffects);
                    Optional<SomanticWord> opt = somanticRepo.getWordByHashcode(neuralResult);
                    if (opt.isPresent()) {
                        rezultat = opt.get();
                        Interface.setMessage(SUCCESS_NEURAL_NETWORK_HAD_FOUND_MATCH);
                    } else {
                        Interface.setMessage(NEED_TO_TRAIN_NN__TRYING_ALGORYTHMICAL_WA);
                        SomanticRepository repo = somanticRepo.getSomanticRepo();
                        for (Map.Entry<String, SomanticWord> entry : repo.entrySet()) {
                            long stopienPokrewienstwa = 0L;
                            if (entry.getValue().getAffects().size() > 0) {
                                for (List<Integer> affectInRepo : entry.getValue().getAffects()) {
                                    long last = 0;
                                    long lastproportions = 1;
                                    for (int i = 0; i < recentAffects.size(); i++) {
                                        if (recentAffects.size() > i + 1 && affectInRepo.size() > i + 1) {
                                            long actual = Math.abs(recentAffects.get(i) - affectInRepo.get(i));
                                            long actualproportions = last / (actual > 1 ? actual : 1);
                                            stopienPokrewienstwa = Math.abs(stopienPokrewienstwa + Math.abs(lastproportions - actualproportions) * Math.abs(last - actual)) / 2;
                                            last = actual;
                                            lastproportions = actualproportions;
                                        }
                                    }
                                }
                            }
                            if (stopienPokrewienstwa > stopienPokrewienstwaMin
                                    && !skip.contains(entry.getKey())) {
                                stopienPokrewienstwaMax = stopienPokrewienstwaMin = Math.abs(stopienPokrewienstwa);
                                rezultat = entry.getValue();
                                skip.add(entry.getKey());
                            }
                        }
                    }

                    if (rezultat != null) {
                        stopienPokrewienstwaMin = stopienPokrewienstwaMax;
                        if (rezultat.getWords().iterator().hasNext()) {
                            Interface.getNeuralNetworkTrainer().addToLearningDataset(recentAffects, rezultat.hashCode());
                            Interface.getNeuralNetworkTrainer().learn();
                            Interface.setWords(Interface.getWords() + " " + rezultat.getWords().iterator().next());
                            Speaker.start(rezultat.getWords().iterator().next());
                            Interface.setWord(rezultat);
                            Interface.setSentence(somanticRepo.getArranger().rewrite(Interface.getWords()));
                            communicationBox.setText(Interface.getStringSentence());
                        }
                        rezultat = null;
                    } else {
                        stopienPokrewienstwaMin--;
                    }
                }
                try {
                    translatorRunnablelock.wait(1500);
                } catch (InterruptedException ex) {
                    Logger.getLogger(Controller.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }
    private static final String NEED_TO_TRAIN_NN__TRYING_ALGORYTHMICAL_WA = "we need to train your Neuro Network - finding right word in algorythmical way... and supervising NN";
    private static final String SUCCESS_NEURAL_NETWORK_HAD_FOUND_MATCH = "SUCCESS! neural network had found match!";
}
