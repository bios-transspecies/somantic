package MainProgram;

import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JToggleButton;
import static MainProgram.Controller.recording;
import RiTa.RiTaRepo;
import RiTa.RiTaWord;

public class StimulationRunnable implements Runnable {

    String[] words;
    JToggleButton stimulateToggle;
    Library library;
    String stimulatedAlready;
    private final Thread live;
    private final AudioFFT fft;
    private final JToggleButton liveToggleButton;

    StimulationRunnable(RiTaRepo repo, JToggleButton stimulateToggle, Library library, Thread live, AudioFFT fft, JToggleButton liveToggleButton) {
        this.words = wordsArr(repo);
        this.stimulateToggle = stimulateToggle;
        this.library = library;
        this.live = live;
        this.fft = fft;
        this.liveToggleButton = liveToggleButton;
    }

    private String[] wordsArr(RiTaRepo repo){
        String[]wordsArr = new String[repo.size()];
        int in=0;
        for (RiTaWord word : repo) {
            wordsArr[in++] = word.getLemma();
        }
        return wordsArr;
    }
    
    public void run() {
        Interface.setStimulatedAlready("");
        for (int i = 0; i < words.length && stimulateToggle.isSelected() && recording; i++) {
            TTSimpl.start(words[i]);
            Interface.setWord(words[i]);
            synchronized (this) {
                try {
                    this.wait(1000);
                } catch (InterruptedException ex) {
                    Logger.getLogger(Controller.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            library.zapisz(words[i], fft.getMatrix());
            Interface.setStimulatedAlready(Interface.getStimulatedAlready() + words[i] + ' ');
        }
        if (liveToggleButton.isSelected()) {
            synchronized (live) {
                try {
                    live.notify();
                } catch (Exception ex) {
                    Logger.getLogger(Controller.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }
}
