package MainProgram;

import static MainProgram.Controller.recording;
import WNprocess.SomanticFactory;
import WNprocess.WordNetToolbox;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JToggleButton;

public class StimulationRunnable implements Runnable {

    JToggleButton stimulateToggle;
    String stimulatedAlready;
    private final Thread live;
    private final AudioFFT fft;
    private final JToggleButton liveToggleButton;
    private final SomanticFactory riTaFactory;

    StimulationRunnable(SomanticFactory riTaFactory, JToggleButton stimulateToggle, Thread live, AudioFFT fft, JToggleButton liveToggleButton) {
        this.riTaFactory = riTaFactory;
        this.stimulateToggle = stimulateToggle;
        this.live = live;
        this.fft = fft;
        this.liveToggleButton = liveToggleButton;
    }

    public void run() {
        Interface.setStimulatedAlready("");
        String[] words = WordNetToolbox.tokenize(Interface.getBufferedText());
        for (int i = 0; i < words.length && stimulateToggle.isSelected() && recording; i++) {
            if (!WordNetToolbox.explain(words[i]).isEmpty()) {
                Speaker.start(words[i]);
                Interface.setWord(riTaFactory.getWord(words[i]));

                System.out.println(words[i] + " / " + System.nanoTime());

                synchronized (this) {
                    try {
                        this.wait(2000);
                    } catch (InterruptedException ex) {
                        Logger.getLogger(Controller.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
                System.out.println(words[i] + " / " + System.nanoTime() + " / " + fft.getMatrix());
                List<Integer> a = fft.getMatrix();
                riTaFactory.addAffectToWord(words[i], a);
                Interface.setStimulatedAlready(Interface.getStimulatedAlready() + words[i] + ' ');
            }
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
