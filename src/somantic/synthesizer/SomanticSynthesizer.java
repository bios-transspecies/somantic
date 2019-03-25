/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package somantic.synthesizer;

import java.util.concurrent.atomic.AtomicReference;
import net.beadsproject.beads.core.AudioContext;
import net.beadsproject.beads.data.Buffer;
import net.beadsproject.beads.ugens.Gain;
import net.beadsproject.beads.ugens.Reverb;
import net.beadsproject.beads.ugens.WavePlayer;
import somantic.processors.Algorytm;

public class SomanticSynthesizer {

    private final AudioContext ac = new AudioContext();
    private final AtomicReference<Float> wave = new AtomicReference<>(30f);
    private final WavePlayer wp = new WavePlayer(ac, wave.get(), Buffer.SINE);
    private final Gain g = new Gain(ac, 1, 0.2f);
    private final Algorytm a = new Algorytm();
    private final Reverb reverb = new Reverb(ac);


    public SomanticSynthesizer() {
        wp.start();
        g.addInput(wp);
        reverb.addInput(g);
        ac.out.addInput(reverb);
        ac.start();
    }
    
    public void setFrequency(float s){
        wave.set(s);
        wp.setFrequency(wave.get());
    }
    
    public float getFrequency(){
        return a.licz(wp.getFrequency());
    }
    
    public void stop(){
        wp.kill();
    }

    public void setVolume(Integer zz) {
        float in = Float.valueOf(String.valueOf(zz)) / 10;
        if(in > 1) in = 1;
        g.setGain(a.licz(in));
    }

    public void update() {   
        g.update();
    }
    
}
