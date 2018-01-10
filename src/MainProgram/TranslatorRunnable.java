package MainProgram;

import WNprocess.SomanticFactory;
import WNprocess.SomanticRepository;
import WNprocess.SomanticWord;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JTextArea;
import javax.swing.JToggleButton;

public class TranslatorRunnable implements Runnable {

    private final JToggleButton translateToggle;
    private final JTextArea communicationBox;
    private final AudioFFT fft;
    private final SomanticFactory riTaFactory;

    TranslatorRunnable(JToggleButton translateToggle, JTextArea communicationBox, AudioFFT fft, SomanticFactory riTaFactory) {
        this.translateToggle = translateToggle;
        this.communicationBox = communicationBox;
        this.fft = fft;
        this.riTaFactory = riTaFactory;
    }

    @Override
    public void run() {
        while (translateToggle.isSelected()) {
            List<Integer> recentAffects = fft.getMatrix();
            Long stopienPokrewienstwa;
            Long stopienPokrewienstwaMin = -0L;
            if (Interface.getWords().split(" ").length > 15) {
                Interface.setWords(" ");
            }
            if (recentAffects.size() > 99) {
                SomanticRepository repo = riTaFactory.getRitaRepo();
                SomanticWord rezultat = null;
                stopienPokrewienstwa = 0L;
                for (Map.Entry<String, SomanticWord> entry : repo.entrySet()) {
                    if (entry.getValue().getAffects().size() > 0) {
                        for (List<Integer> affectInRepo : entry.getValue().getAffects()) {
                            for (int i = 0; i < recentAffects.size(); i++) {
                                if (recentAffects.size() > i + 1 && affectInRepo.size() > i + 1) {
                                    stopienPokrewienstwa = (stopienPokrewienstwa + Math.abs(recentAffects.get(i) - affectInRepo.get(i))) / 2 + stopienPokrewienstwa;
                                }
                            }
                        }
                        if (stopienPokrewienstwa > stopienPokrewienstwaMin) {
                            rezultat = entry.getValue();
                            stopienPokrewienstwaMin = stopienPokrewienstwa + 1;
                        }
                        Interface.setMinimalSimilarity(stopienPokrewienstwaMin);
                    }
                }

                if (rezultat != null) {
                    Interface.setWords(Interface.getWords() + " " + rezultat.getWords().iterator().next());
                    Speaker.start(rezultat.getWords().iterator().next());
                    Interface.setWord(rezultat.getWords().iterator().next());
                    String arranged = riTaFactory.getArranger().rewrite(Interface.getWords());
                    Interface.setSentence(arranged);
                    communicationBox.setText(arranged);
                } else {
                    stopienPokrewienstwaMin = (999 * stopienPokrewienstwaMin + stopienPokrewienstwa) / 1000;
                }
            }

            synchronized (this) {
                try {
                    this.wait(1500);
                } catch (InterruptedException ex) {
                    Logger.getLogger(Controller.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }
}
