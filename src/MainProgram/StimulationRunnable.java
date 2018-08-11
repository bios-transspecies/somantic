package MainProgram;

import static MainProgram.Controller.recording;
import WNprocess.SomanticFactory;
import WNprocess.WordNetToolbox;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JTextArea;
import javax.swing.JToggleButton;

public class StimulationRunnable implements Runnable {

    JToggleButton stimulateToggle;
    String stimulatedAlready;
    private final Thread live;
    private final AudioFFT fft;
    private final JToggleButton liveToggleButton;
    private final SomanticFactory somanticFactory;
    private final Object stimulationRunnableLock = new Object();
    private final JTextArea communicationBox;

    public Object getStimulationRunnableLock() {
        return stimulationRunnableLock;
    }

    StimulationRunnable(SomanticFactory riTaFactory, JToggleButton stimulateToggle, Thread live, AudioFFT fft, JToggleButton liveToggleButton, JTextArea communicationBox) {
        this.somanticFactory = riTaFactory;
        this.stimulateToggle = stimulateToggle;
        this.live = live;
        this.fft = fft;
        this.liveToggleButton = liveToggleButton;
        this.communicationBox = communicationBox;
    }

    public void run() {
        synchronized (stimulationRunnableLock) {
            String[] words = WordNetToolbox.tokenize(Interface.getBufferedText());
            for (int i = 0; i < words.length && stimulateToggle.isSelected() && recording; i++) {
                cutOffFromBuffer(words, i);
                Speaker.start(words[i]);
                if (!WordNetToolbox.explain(words[i]).isEmpty()) {
                    Interface.setWord(somanticFactory.getOrCreateWord(words[i]));
                    try {
                        stimulationRunnableLock.wait(2500);
                    } catch (InterruptedException ex) {
                        Logger.getLogger(Controller.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    List<Integer> a = fft.getMatrix();
                    somanticFactory.addAffectToWord(words[i], a);
                }
                if (Interface.getSaving() == false) {
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            try {
                                Interface.setSaving(true);
                                somanticFactory.saveRepo();
                            }catch(Exception e){
                                
                            } finally {
                                Interface.setSaving(false);
                            }
                        }
                    }).start();
                }
            }
            if (liveToggleButton.isSelected()) {
                try {
                    live.notify();
                } catch (Exception ex) {
                    Logger.getLogger(Controller.class.getName()).log(Level.SEVERE, null, ex.getLocalizedMessage());
                }
            }
        }
    }

    private void cutOffFromBuffer(String[] words, int i) {
        Interface.setBufferedText(Interface.getBufferedText().replaceFirst(words[i], "").trim().replace("  ", " "));
        if(Interface.getBufferedText().charAt(0)=='.')
            Interface.setBufferedText(Interface.getBufferedText().replaceFirst(".", "").trim());
        if(Interface.getBufferedText().length()>10000)
            communicationBox.setText(Interface.getBufferedText().substring(0, Interface.getBufferedText().substring(0, 10000).lastIndexOf(".")));
        else
            communicationBox.setText(Interface.getBufferedText());
    }
}
