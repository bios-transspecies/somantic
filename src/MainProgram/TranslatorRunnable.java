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
    private final JTextArea communicationBox;
    private final AudioFFT fft;
    private List<String> lines;
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
            String matrix = fft.getMatrix();
            if (matrix.length() > 100) {
                matrix = matrix.substring(0, 100);
                lines = riTaFactory.getLines();
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
                                fileMatrix = linia.substring(linia.indexOf(":"), linia.length()).trim().replace(",", "");
                            }
                            fileAffects = fileMatrix.split(" ");
                        } catch (Exception e) {
                            System.err.println(e);
                        }
                        if (fileAffects != null && fileAffects.length > 0) {
                            String[] recentAffects = matrix.replace(",", "").split(" ");
                            int sizeDiff = Math.abs(fileAffects.length - recentAffects.length);
                            for (int i = 0; i < recentAffects.length; i++) {
                                for (int a = 0; a < sizeDiff && sizeDiff > 0; a++) {
                                    if (recentAffects.length > i
                                            && fileAffects.length > i + a
                                            && recentAffects[i].trim().length() > 0
                                            && fileAffects[i + a].trim().length() > 0) {
                                        Float recent = (float) Integer.parseInt(recentAffects[i].trim());
                                        Float fileValue = (float) Integer.parseInt(fileAffects[i + a].trim());
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
                if (rezultat.trim().length() > 0) {
                    Interface.setWords(Interface.getWords() + " " + rezultat);
                    TTSimpl.start(rezultat);
                    Interface.setWord(rezultat);
                    System.out.println("MainProgram.TranslatorRunnable.run() REZULTAT '"+rezultat+"'");
                    String arranged = riTaFactory.getArranger().rewrite(Interface.getWords());
                    System.out.println("arranged "+arranged);
                    communicationBox.setText(arranged);
                }
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
