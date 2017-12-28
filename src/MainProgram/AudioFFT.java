package MainProgram;

import ddf.minim.AudioInput;
import ddf.minim.Minim;
import ddf.minim.analysis.FFT;
import ddf.minim.spi.AudioStream;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JProgressBar;

/**
 * Object of this class is able to deliver FFT results for sound, also like
 * array of affects based on 35hZ freq. *
 */
class AudioFFT {

    private final Minim m;
    private final AudioInput in;
    private final FFT fft;
    private int band;
    private List<Integer> matrix;
    private final Algorytm a;
    private ArrayList<Float> arrayOfAffects;

    public AudioFFT() {
        this.band = 0;
        matrix = new ArrayList<Integer>();
        m = new Minim(this);
        AudioStream is = m.getInputStream(1, 8, 44.100f, 16);
        m.debugOff();
        in = m.getLineIn(1);
        fft = new FFT(in.bufferSize(), in.sampleRate());
        a = new Algorytm();
        arrayOfAffects = new ArrayList();
    }

    /**
     * methode will prepare data based on FFT analyse of the audio signal for
     * the future purposes
     */
    public void analizuj(JProgressBar jp) {
        Interface.setIsListening(true);
        try {
            fft.forward(in.mix);
            float hx = a.licz(fft.getBand(35) * 1000);
            band = (int) hx;
            for (int i = 0; i < 1000; i++) {
                arrayOfAffects.add(fft.getBand(i) * 1000);
            }
            jp.setValue(band);
            Interface.setVolume(band);
            matrix.add(band);
        } catch (Exception e) {
            // System.err.println(e);
        }
        Interface.setIsListening(false);
    }

    public List<Integer> getMatrix() {
        List<Integer> m = matrix;
        matrix = new ArrayList<>();
        return m;
    }

    public ArrayList<Float> getArrayOfAffects() {
        ArrayList<Float> e = (ArrayList<Float>) arrayOfAffects.clone();
        arrayOfAffects.clear();
        return e;
    }

    public int getBand() {
        return Integer.parseInt(String.valueOf(band).replace(".", ""));
    }
}
