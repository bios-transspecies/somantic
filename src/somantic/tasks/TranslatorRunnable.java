package somantic.tasks;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.atomic.AtomicLong;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JTextArea;
import javax.swing.JToggleButton;
import static somantic.Main.MILISECONDS_TO_WAIT_WHEN_TRANSLATING;
import somantic.controller.Controller;
import somantic.library.SomanticFacade;
import somantic.library.SomanticRepository;
import somantic.library.SomanticWord;
import somantic.processors.AudioFFT;
import somantic.state.State;

public class TranslatorRunnable implements Runnable {

    private static final String NEED_TO_TRAIN_WITH_TRYING_ALGORYTHMICAL_RESULT = "we need to train your Neuro Network - finding right word in algorythmical way... and supervising NN";
    private static final String SUCCESS_NEURAL_NETWORK_HAD_FOUND_MATCH = "SUCCESS! neural network had found match: ";
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
            final List<String> skip = new ArrayList<>();
            while (translateToggle.isSelected()) {
                processWithWait(skip);
            }
        }
    }

    private void processWithWait(
            final List<String> skip) {
        List<Integer> recentAffects = prepare();
        if (recentAffects.size() > 99) {
            singleProcess(recentAffects, skip);
        }
        try {
            translatorRunnablelock.wait(MILISECONDS_TO_WAIT_WHEN_TRANSLATING);
        } catch (InterruptedException ex) {
            Logger.getLogger(Controller.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void singleProcess(
            final List<Integer> recentAffects,
            final List<String> skip) {
        final AtomicLong stopienPokrewienstwaMax = new AtomicLong(0);
        final AtomicLong stopienPokrewienstwaMin = new AtomicLong(0);
        SomanticWord rezultat = null;
        Integer neuralResult = State.getNeuralNetworkTrainer().ask(recentAffects);
        SomanticWord opt = somanticRepo.getWordById(neuralResult);
        if (opt != null) {
            rezultat = opt;
            State.setMessage(SUCCESS_NEURAL_NETWORK_HAD_FOUND_MATCH + rezultat);
        } else {
            State.setMessage(NEED_TO_TRAIN_WITH_TRYING_ALGORYTHMICAL_RESULT);
            SomanticRepository repo = somanticRepo.getSomanticRepo();
            rezultat = compreAllAffects(repo, recentAffects, stopienPokrewienstwaMin, skip, stopienPokrewienstwaMax, rezultat);
        }
        countStopienPokrewienstwaMin(rezultat, stopienPokrewienstwaMin, stopienPokrewienstwaMax, recentAffects);
    }

    private SomanticWord compreAllAffects(
            final SomanticRepository repo,
            final List<Integer> recentAffects,
            final AtomicLong stopienPokrewienstwaMin,
            final List<String> skip,
            final AtomicLong stopienPokrewienstwaMax,
            SomanticWord recentrezultat) {
        SomanticWord rezultat = null;
        for (Map.Entry<String, SomanticWord> entry : repo.entrySet()) {
            final AtomicLong stopienPokrewienstwa = new AtomicLong(0);
            if (entry.getValue().getAffects().size() > 0) {
                obliczStopienPokrewienstwa(entry, recentAffects, stopienPokrewienstwa);
            }
            if (shouldConcider(stopienPokrewienstwa, stopienPokrewienstwaMin, skip, entry)) {
                rezultat = setResult(stopienPokrewienstwaMin, stopienPokrewienstwa, stopienPokrewienstwaMax, entry, skip);
                recentrezultat = rezultat;
            } else {
                rezultat = recentrezultat;
            }
        }
        return rezultat;
    }

    private SomanticWord setResult(
            final AtomicLong stopienPokrewienstwaMin,
            final AtomicLong stopienPokrewienstwa,
            final AtomicLong stopienPokrewienstwaMax,
            final Entry<String, SomanticWord> entry,
            final List<String> skip) {
        SomanticWord rezultat;
        stopienPokrewienstwaMin.set(Math.abs(stopienPokrewienstwa.get()));
        stopienPokrewienstwaMax.set(stopienPokrewienstwaMin.get());
        rezultat = result(entry, skip);
        return rezultat;
    }

    private static boolean shouldConcider(final AtomicLong stopienPokrewienstwa, final AtomicLong stopienPokrewienstwaMin, List<String> usedWordsToSkip, Entry<String, SomanticWord> entry) {
        return stopienPokrewienstwa.get() > stopienPokrewienstwaMin.get()
                && !usedWordsToSkip.contains(entry.getKey());
    }

    private SomanticWord result(
            final Map.Entry<String, SomanticWord> entry,
            final List<String> skip) {
        SomanticWord rezultat;
        rezultat = entry.getValue();
        skip.add(entry.getKey());
        return rezultat;
    }

    private void countStopienPokrewienstwaMin(
            final SomanticWord rezultat,
            final AtomicLong stopienPokrewienstwaMin,
            final AtomicLong stopienPokrewienstwaMax,
            final List<Integer> recentAffects) {

        if (rezultat != null) {
            stopienPokrewienstwaMin.set(stopienPokrewienstwaMax.get());
            if (rezultat.getWords().iterator().hasNext()) {
                setState(recentAffects, rezultat);
            }
        } else {
            stopienPokrewienstwaMin.set(stopienPokrewienstwaMin.get() - 1);
        }
    }

    private void setState(List<Integer> recentAffects, SomanticWord rezultat) {
        State.getNeuralNetworkTrainer().addToLearningDataset(recentAffects, rezultat.getId());
        State.getNeuralNetworkTrainer().learn();
        State.setWords(State.getWords() + " " + rezultat.getWords().iterator().next());
        Speaker.start(rezultat.getWords().iterator().next());
        State.setWord(rezultat);
        State.setSentence(somanticRepo.getArranger().rewrite(State.getWords()));
        communicationBox.setText(State.getStringSentence());
    }

    private void obliczStopienPokrewienstwa(
            Entry<String, SomanticWord> entry,
            List<Integer> recentAffects,
            AtomicLong stopienPokrewienstwa) {
        for (List<Integer> affectInRepo : entry.getValue().getAffects()) {
            Long last = new Long(0);
            Long lastproportions = new Long(1);
            countProportions(recentAffects, affectInRepo, last, stopienPokrewienstwa, lastproportions);
        }
    }

    private void countProportions(List<Integer> recentAffects, List<Integer> affectInRepo, Long last, AtomicLong stopienPokrewienstwa, Long lastproportions) {
        for (int i = 0; i < recentAffects.size(); i++) {
            if (recentAffects.size() > i + 1 && affectInRepo.size() > i + 1) {
                Long actual = new Long(Math.abs(recentAffects.get(i) - affectInRepo.get(i)));
                Long actualproportions = new Long(last / (actual > 1 ? actual : 1));
                stopienPokrewienstwa.addAndGet((lastproportions * Math.abs(last - actual)) / 2);
                last = actual;
                lastproportions = actualproportions;
            }
        }
    }

    private List<Integer> prepare() {
        List<Integer> recentAffects = fft.getMatrix();
        if (State.getWords().split(" ").length > 15) {
            State.addSentence(State.getStringSentence());
            State.setWords(" ");
        }
        return recentAffects;
    }
}
