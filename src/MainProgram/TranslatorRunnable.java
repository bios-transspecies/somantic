package MainProgram;

import RiTa.RiArange;
import RiTa.RiTaFactory;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JTextArea;
import javax.swing.JToggleButton;

public class TranslatorRunnable implements Runnable {

    private final JToggleButton translateToggle;
    private final Library library;
    private final JTextArea communicationBox;
    private final AudioFFT fft;
    private List<String> lines;
    private RiArange arranger;
    

    TranslatorRunnable(JToggleButton translateToggle, Library library, JTextArea communicationBox, AudioFFT fft, RiTaFactory f) {
        this.translateToggle = translateToggle;
        this.library = library;
        this.communicationBox = communicationBox;
        this.fft = fft;
        this.arranger = new RiArange(f);
    }

    @Override
    public void run() {
        while (translateToggle.isSelected()) {
            String matrix = fft.getMatrix();
            if (matrix.length() > 100) {
                matrix = matrix.substring(0, 100);
                lines = library.getLines();
                int stopienPokrewienstwaMin = 0;
                int stopienPokrewienstwa = 0;
                String rezultat = "";
                if (matrix.contains(" ") && matrix.length() > 20) {
                    for (Iterator<String> iterator = lines.iterator(); iterator.hasNext();) {
                        String linia = iterator.next();
                        String fileMatrix = "";
                        String[] fileAffects = null;
                        try {
                            if (linia.length() > 0 && linia.indexOf(":") > 0) {
                                fileMatrix = linia.substring(linia.indexOf(":"), linia.length());
                            }
                            fileAffects = fileMatrix.split(" ");
                        } catch (Exception e) {
                            System.err.println(e);
                        }
                        if (fileAffects != null && fileAffects.length > 0) {
                            String[] recentAffects = matrix.split(" ");
                            int sizeDiff = Math.abs(fileAffects.length - recentAffects.length);
                            for (int i = 0; i < recentAffects.length; i++) {
                                for (int a = 0; a < sizeDiff && sizeDiff > 0; a++) {
                                    if (recentAffects.length > i
                                            && fileAffects.length > i + a
                                            && recentAffects[i].length() > 0
                                            && fileAffects[i + a].length() > 0) {
                                        Float recent = Float.parseFloat(recentAffects[i]);
                                        Float fileValue = Float.parseFloat(fileAffects[i + a]);
                                        stopienPokrewienstwa = stopienPokrewienstwa + Math.abs((int) (recent - fileValue));
                                    }
                                }
                            }
                        }
                        if (linia.length() > 0 && linia.indexOf(":") > 0) {
                            String slowo = linia.substring(0, linia.indexOf(":"));
                            String exists = Interface.getWords();
                            stopienPokrewienstwa = stopienPokrewienstwa + stopienPokrewienstwa * 1000 * exists.split(slowo).length;
                            if (stopienPokrewienstwa > 10
                                    && stopienPokrewienstwa < stopienPokrewienstwaMin) {
                                rezultat = slowo;
                                stopienPokrewienstwaMin = stopienPokrewienstwa;
                            } else if (stopienPokrewienstwaMin == 0) {
                                stopienPokrewienstwaMin = stopienPokrewienstwa;
                            }
                        }
                    }
                }
                if (rezultat.length() > 0) {
                    Interface.setWords(Interface.getWords() + " " + rezultat);
                    TTSimpl.start(rezultat);
                    Interface.setWord(rezultat);
                }
                communicationBox.setText(arranger.rewrite(Interface.getWords()));
            }
            synchronized (this) {
                try {
                    this.wait(1000);
                } catch (InterruptedException ex) {
                    Logger.getLogger(Controller.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }
}
