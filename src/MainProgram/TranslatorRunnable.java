package MainProgram;

import RiTa.RiTaFactory;
import RiTa.RiTaRepo;
import RiTa.RiTaWord;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JTextArea;
import javax.swing.JToggleButton;

public class TranslatorRunnable implements Runnable {

    private final JToggleButton translateToggle;
    private final JTextArea communicationBox;
    private final AudioFFT fft;
    private final RiTaFactory riTaFactory;

    TranslatorRunnable(JToggleButton translateToggle, JTextArea communicationBox, AudioFFT fft, RiTaFactory riTaFactory) {
        this.translateToggle = translateToggle;
        this.communicationBox = communicationBox;
        this.fft = fft;
        this.riTaFactory = riTaFactory;
    }

    @Override
    public void run() {
        while (translateToggle.isSelected()) {
            List<Integer> recentAffects = fft.getMatrix();
            if (recentAffects.size() > 100) {
                RiTaRepo repo = riTaFactory.getRitaRepo();
                RiTaWord rezultat = null;
                for (Map.Entry<String, RiTaWord> entry : repo.entrySet()) {
                    int stopienPokrewienstwaMin = 0;
                    int stopienPokrewienstwa = 0;
                    RiTaWord riWord = entry.getValue();
                    List<List<Integer>> affectsInRepo = new ArrayList<>();
                    affectsInRepo.addAll(riWord.getAffects());
                    if (affectsInRepo.size() > 0) {
                        for (List<Integer> affectInRepo : affectsInRepo) {
                            int sizeDiff = Math.abs(affectInRepo.size() - recentAffects.size());
                            for (int i = 0; i < recentAffects.size(); i++) {
                                for (int j = 0; j < sizeDiff && sizeDiff > 0; j++) {
                                    if (recentAffects.size() > i + j&& affectInRepo.size() > i) {
                                        stopienPokrewienstwa = stopienPokrewienstwa + Math.abs(recentAffects.get(i+ j) - affectInRepo.get(i));
                                    }
                                }
                            }
                        }
                    }
                    
                    int threshold = Interface.getWords().contains(entry.getKey()) ? stopienPokrewienstwaMin *1000 : stopienPokrewienstwaMin;
                    if ( stopienPokrewienstwa > threshold && !Interface.getWords().contains(entry.getKey())) {
                        rezultat = riWord;
                        stopienPokrewienstwaMin = stopienPokrewienstwa;
                    } else if (stopienPokrewienstwaMin == 0) {
                        stopienPokrewienstwaMin = stopienPokrewienstwa;
                    }
                }
                if (rezultat != null) {
                    Interface.setWords(Interface.getWords() + " " + rezultat);
                    Speaker.start(rezultat.getWords().toString());
                    Interface.setWord(rezultat.getWords().toString());
                    System.out.println("MainProgram.TranslatorRunnable.run() REZULTAT '" + rezultat + "'");
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
