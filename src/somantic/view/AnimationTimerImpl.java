/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package somantic.view;

import java.util.ArrayList;
import javafx.animation.AnimationTimer;
import javafx.scene.Group;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.text.Font;
import javax.swing.JFrame;
import somantic.processors.AudioFFT;
import somantic.state.State;
import somantic.synthesizer.SomanticSynthesizer;

public class AnimationTimerImpl extends AnimationTimer {

    ArrayList<Integer> arrayOfAffectsArch = null;

    private final AudioFFT fft;
    private final JFrame window;
    private int doyA;
    private int doxA;
    private int odyA;
    private int odxA;
    private Canvas canvas;
    private final Group root;
    private final SomanticSynthesizer somanticSynthesizer = new SomanticSynthesizer();

    public AnimationTimerImpl(AudioFFT fft, JFrame window, Group root) {
        this.fft = fft;
        this.window = window;
        this.root = root;
    }

    @Override
    public void handle(long now) {
        ArrayList<Integer> arrayOfAffects = fft.getArrayOfAffects();
        window.setVisible(State.isVisualisation());
        if (State.isVisualisation()) {
            visualise(arrayOfAffects);
        }
    }

    private void visualise(ArrayList<Integer> arrayOfAffects) {
        canvas = new Canvas(window.getWidth(), window.getHeight());
        root.getChildren().clear();
        int h = window.getHeight();
        int w = window.getWidth();
        GraphicsContext gc = canvas.getGraphicsContext2D();
        gc.setFill(Color.BLACK);
        gc.setGlobalAlpha(0.20);
        gc.setStroke(Color.WHITE);
        root.getChildren().add(canvas);
        graphicsLineDrawer(arrayOfAffects, w, h, gc);
        textWriter(gc, w, h, arrayOfAffects);
        gc.restore();
    }

    private void textWriter(GraphicsContext gc, int w, int h, ArrayList<Integer> arrayOfAffects) {
        gc.setGlobalAlpha(0.80);
        gc.setFill(Color.WHITE);
        gc.fillText("state: " + State.getState(), 100, 100);
        gc.setFont(Font.font("Impact", w / 30));
        if (State.getWord() != null) {
            genrateWords(gc, w, h);
        }
    }

    private void genrateWords(GraphicsContext gc, int w, int h) {
        gc.fillText(State.getWord().toString(), w / 4, h / 3);
        gc.setFont(Font.font("Arial", w / 100));
        gc.fillText(getDescription(), w / 4, (h / 3) + 30);
        String[] sentences = State.getSentences().split(" <br> ");
        gc.setGlobalAlpha(0.20);
        int line = 40;
        for (String sentence : sentences) {
            line = line + 12;
            if (!sentence.isEmpty()) {
                gc.fillText(sentence, w / 10, (h / 3) + line, w - w / 10);
            }
        }
        gc.fillText("words: " + State.getWords().toLowerCase().toLowerCase(), 100, 120);
        gc.fillText("sentence: " + State.getStringSentence().toLowerCase(), 100, 140);
        if (State.getWords().length() > 200) {
            State.setWords(" ");
        }
    }

    private static String getDescription() {
        if(State.getWord()==null || State.getWord().getDescription() == null)
            return "";
        return State.getWord().getDescription().replaceAll(";", "\n");
    }

    private void graphicsLineDrawer(ArrayList<Integer> arrayOfAffects, 
            int w, int h, GraphicsContext gc) {
        gc.setGlobalAlpha(0.20);
        int k = 0;
        int[] arr = new int[4];
        arrayOfAffects = prepareArrayOfAffects(arrayOfAffects);
        for (int i = 0; i < arrayOfAffects.size(); i++) {
            drawLines(i, arr, arrayOfAffects, w, h, gc);
            Integer zz = arrayOfAffects.get(i);
            if(zz!=null){
                somanticSynthesizer.setFrequency(i);
                somanticSynthesizer.setVolume(zz);
                somanticSynthesizer.update();
            }
        }
    }

    private void drawLines(int i, int[] arr, ArrayList<Integer> arrayOfAffects, int w, int h, GraphicsContext gc) {
        int k;
        boolean even = isEven(i);
        int j = i % 4;
        if (j == 0) {
            arr = new int[4];
        }
        if (arrayOfAffects.get(i) != null) {
            int r = arrayOfAffects.get(i);
            k = r + (i * j) / 10;
            prepareArrJ(arr, j, k, i);
            int odx, ody, dox, doy;
            if (j == 3) {
                if (i % 3 == 0) {
                    odx = (odxA == 0 ? w / 2 : odxA);
                    ody = (odyA == 0 ? h / 2 : odyA);
                    dox = ((arr[2] % (w / 2)) + (w / 2)) % w;
                    doy = (h - arr[3] % h) % h;
                } else {
                    odx = (odxA == 0 ? w / 2 : odxA);
                    ody = (odyA == 0 ? h / 2 : odyA);
                    dox = (w / 2 - arr[2] % w) % w;
                    doy = (h - arr[3] % h) % h;
                }
                if (even) {
                    odxA = (odx + w / 2);
                    odyA = (ody + h / 2);
                    doxA = ((doxA * 5 + dox) / 6);
                    doyA = ((doyA * 5 + doy) / 6);
                }
                
                int middleWidth = w / 2;
                
                if (odx != middleWidth || dox != middleWidth) {
                    gc.strokeLine(odxA % (w * middleWidth), odyA % (middleWidth), dox % (middleWidth), doy % (middleWidth));
                    dox = even ? dox : -dox;
                    doy = even ? doy : -doy;
                    doxA = even ? doxA : -doxA;
                    doyA = even ? doyA : -doyA;
                    
                    Paint p = new Color(
                            toColourValue(Math.abs(dox)), 
                            toColourValue(Math.abs(doy)), 
                            toColourValue(Math.abs(doxA)), 
                            toColourValue(Math.abs(doyA)));
                    
                    gc.setStroke(p);
                    gc.strokeLine(dox % (middleWidth), doy % (middleWidth), doxA % (middleWidth), doyA % (middleWidth));
                }
            }
        }
    }

    private static double toColourValue(int x) throws NumberFormatException {
        double r = (Double.parseDouble(String.valueOf(x))%256D) / 256D;
        return r;
    }

    private void prepareArrJ(int[] arr, int j, int k, int i) {
        try {
            arr[j] = k * 2 - i;
        } catch (Exception e) {
            arr[j] = 0;
        }
    }

    private static boolean isEven(int i) {
        return i % 2 > 0;
    }

    private ArrayList<Integer> prepareArrayOfAffects(ArrayList<Integer> arrayOfAffects) {
        if (arrayOfAffects.size() > 0) {
            for (int i = 0; arrayOfAffects.size() < i; i++) {
                arrayOfAffectsArch.add(i,
                        (arrayOfAffectsArch.get(i) + arrayOfAffects.get(i)) / 50);
            }
        }
        if (arrayOfAffectsArch != null) {
            arrayOfAffects = arrayOfAffectsArch;
        }
        return arrayOfAffects;
    }
}
