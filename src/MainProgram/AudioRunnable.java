package MainProgram;

import java.util.logging.Level;
import java.util.logging.Logger;

public class AudioRunnable implements Runnable {

    private final AudioFFT fft;

    AudioRunnable(AudioFFT fft) {
        this.fft = fft;
    }

    @Override
    public void run() {
        while (true) {
            fft.analizuj(Interface.getProgressBar());
            synchronized (this) {
                try {
                    this.wait(10);
                } catch (InterruptedException ex) {
                    Logger.getLogger(Controller.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }
}
