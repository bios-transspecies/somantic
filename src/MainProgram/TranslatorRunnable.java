package MainProgram;

import WNprocess.SomanticFactory;
import WNprocess.SomanticRepository;
import WNprocess.SomanticWord;
import java.util.ArrayList;
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
            Long stopienPokrewienstwaMin = Interface.getMinimalSimilarity();
            if (recentAffects.size() > 99) {
                SomanticRepository repo = riTaFactory.getRitaRepo();
                SomanticWord rezultat = null;
                for (Map.Entry<String, SomanticWord> entry : repo.entrySet()) {
                    stopienPokrewienstwa = 0L;
                    stopienPokrewienstwaMin = Interface.getMinimalSimilarity();
                    SomanticWord riWord = entry.getValue();
                    List<List<Integer>> affectsInRepo = new ArrayList<>();
                    affectsInRepo.addAll(riWord.getAffects());
                    if (affectsInRepo.size() > 0) {
                        for (List<Integer> affectInRepo : affectsInRepo) {
                            for (int i = 0; i < recentAffects.size(); i++) {
                                if (recentAffects.size() > i+1 && affectInRepo.size() > i+1) {
                                    stopienPokrewienstwa = stopienPokrewienstwa + Math.abs(recentAffects.get(i) - affectInRepo.get(i));
                                }
                            }
                        }
                    }

                    Long threshold = Interface.getWords().contains(entry.getKey()) ? stopienPokrewienstwaMin * 2 : stopienPokrewienstwaMin;
                    //System.err.println(" stopienPokrewienstwa "+stopienPokrewienstwa+ " threshold "+threshold+ " stopienPokrewienstwaMin " + stopienPokrewienstwaMin);
                    if (stopienPokrewienstwa > threshold && !Interface.getWords().contains(entry.getKey())) {
                        rezultat = riWord;
                        stopienPokrewienstwaMin = stopienPokrewienstwa;
                    }
                    if (stopienPokrewienstwa > 0) {
                        Interface.setMinimalSimilarity((10 * Interface.getMinimalSimilarity() + stopienPokrewienstwaMin) / 11);
                    }
                }
                if (rezultat != null) {
                    Interface.setWords(Interface.getWords() + " " + rezultat.getWords().iterator().next());
                    Speaker.start(rezultat.getWords().toString());
                    Interface.setWord(rezultat.getWords().toString());
                    String arranged = riTaFactory.getArranger().rewrite(Interface.getWords());
                    System.out.println("arranged " + arranged);
                    communicationBox.setText(arranged);
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
