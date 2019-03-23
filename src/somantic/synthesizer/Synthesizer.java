package somantic.synthesizer;

import com.jsyn.JSyn;
import com.jsyn.devices.AudioDeviceFactory;
import com.jsyn.devices.AudioDeviceManager;
import com.jsyn.unitgen.Circuit;
import com.jsyn.unitgen.LineOut;
import com.jsyn.unitgen.PulseOscillator;
import java.util.List;
import java.util.Random;
import somantic.processors.AudioFFT;

public class Synthesizer {

    private final AudioDeviceManager audioManager;
    private final com.jsyn.Synthesizer synth;
    private List<Integer> matrix;
    private PulseOscillator posc;

    public Synthesizer() {
        audioManager = AudioDeviceFactory.createAudioDeviceManager(true);
        synth = JSyn.createSynthesizer(audioManager);
        synth.add(new LineOut());
        synth.start();
    }
    
    public void close(){
        synth.stop();
    }
    
    public void play(AudioFFT fft){
        matrix = fft.getMatrix();
        synth.setRealTime(true);
        posc = new PulseOscillator();
        synth.add(posc);
        synth.addAudioTask(()->playMusic());
    }

    private void playMusic() {
        Circuit crct = new Circuit();
        Random random = new Random();
        posc.generate(matrix.get(100), matrix.get(1000));
        synth.add(posc);
        crct.generate(matrix.get(random.nextInt() % 1000), matrix.get(random.nextInt()%100));
        crct.start();
        posc.setCircuit(crct);
    }
    
}
