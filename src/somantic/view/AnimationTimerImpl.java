package somantic.view;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import javafx.animation.AnimationTimer;
import javafx.scene.Group;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.effect.GaussianBlur;
import javafx.scene.effect.Light;
import javafx.scene.effect.Lighting;
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
    private int height;
    private int width;
    private final Group root;
    private final SomanticSynthesizer somanticSynthesizer = new SomanticSynthesizer();
    private final Map<String, Canvas> canvasRepository = new HashMap<>();

    public AnimationTimerImpl(AudioFFT fft, JFrame window, Group root) {
        this.fft = fft;
        this.window = window;
        this.root = root;
    }

    @Override
    public void handle(long now) {
        ArrayList<Integer> arrayOfAffects = fft.getArrayOfAffects();
        window.setVisible(State.isVisualisation());
        height = window.getHeight();
        width = window.getWidth();
        if (State.isVisualisation()) {
            visualise(arrayOfAffects);
        }
    }

    private void visualise(ArrayList<Integer> arrayOfAffects) {
        graphicsLineDrawer(arrayOfAffects);
        textWriter();
    }

    private GraphicsContext getGraphicContext(String element) {
        Canvas canvas = null;
        if (canvasRepository.containsKey(element)) {
            canvas = canvasRepository.get(element);
            if (canvas.getWidth() != width || canvas.getHeight() != height) {
                root.getChildren().remove(canvas);
                canvas = newCanvas(canvas);
            }
        } else {
            canvas = newCanvas(canvas);
        }
        canvasRepository.put(element, canvas);
        return canvas.getGraphicsContext2D();
    }

    private Canvas newCanvas(Canvas canvas) {
        canvas = new Canvas(width, height);
        root.getChildren().add(canvas);
        return canvas;
    }

    private void graphicsLineDrawer(ArrayList<Integer> arrayOfAffects) {
        GraphicsContext gc = getGraphicContext("line");
        fadeOut(gc);
        gc.setGlobalAlpha(0.20);
        int[] arr = new int[4];
        arrayOfAffects = prepareArrayOfAffects(arrayOfAffects);
        for (int i = 0; i < arrayOfAffects.size(); i++) {
            drawLines(i, arr, arrayOfAffects, gc);
            triggerSynthesizer(arrayOfAffects.get(i), i);
        }
    }

    private void textWriter() {
        GraphicsContext gc = getGraphicContext("text");
        fadeOut(gc);
        gc.setGlobalAlpha(0.80);
        gc.setFill(Color.WHITE);
        gc.fillText("state: " + State.getState(), 100, 100);
        gc.setFont(Font.font("Impact", width / 30));
        if (State.getWord() != null) {
            genrateWords(gc, width, height);
        }
    }

    private void fadeOut(GraphicsContext gc) {
        gc.clearRect(0, 0, width, height);
    }

    private void genrateWords(GraphicsContext gc, int w, int h) {
        gc.fillText(State.getWord().toString(), w / 4, h / 3);
        gc.setFont(Font.font("Arial", w / 100));
        gc.fillText(getDescription(), w / 4, (h / 3) + 30);
        String[] sentences = State.getSentences().split(" <br> ");
        gc.setGlobalAlpha(0.20);
        int line = 80;
        generatedSentences(sentences, line, gc, w, h);
        translatedWordsToCombine(gc);
        actualSentence(gc);
        cleanups();
    }

    private void cleanups() {
        if (State.getSentences().split(" <br> ").length > 20) {
            State.resetSentences();
        }
        if (State.getWords().length() > 200) {
            State.setWords(" ");
        }
    }

    private void actualSentence(GraphicsContext gc) {
        gc.fillText("sentence: " + State.getStringSentence().toLowerCase(), 100, 140);
    }

    private void translatedWordsToCombine(GraphicsContext gc) {
        gc.fillText("words: " + State.getWords().toLowerCase().toLowerCase(), 100, 120);
    }

    private void generatedSentences(String[] sentences,
            int line, GraphicsContext gc, int w, int h) {
        int first = sentences.length > 10 ? sentences.length - 10 : 0;
        for (int i = first; i < sentences.length; i++) {
            String sentence = sentences[i];
            line = line + 18;
            if (!sentence.isEmpty()) {
                gc.fillText(sentence, w / 10, (h / 3) + line, w - w / 10);
            }
        }
    }

    private static String getDescription() {
        if (State.getWord() == null
                || State.getWord().getDescription() == null) {
            return "";
        }
        return State.getWord().getDescription().replaceAll(";", "\n");
    }

    private void triggerSynthesizer(Integer zz, int i) {
        if (zz != null) {
            somanticSynthesizer.setFrequency(i);
            somanticSynthesizer.setVolume(zz);
            somanticSynthesizer.update();
        }
    }

    private void drawLines(int i, int[] arr,
            ArrayList<Integer> arrayOfAffects,
            GraphicsContext gc) {
        int width = (int) (this.width * 2);
        int height = (int) (this.height * 1.3);
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
                    odx = (odxA == 0 ? width / 2 : odxA);
                    ody = (odyA == 0 ? height / 2 : odyA);
                    dox = ((arr[2] % (width / 2)) + (width / 2)) + width / 2;
                    doy = (height - arr[3] + height / 2) % height;
                } else {
                    odx = (odxA == 0 ? width / 2 : odxA);
                    ody = (odyA == 0 ? height / 2 : odyA);
                    dox = (width / 2 - arr[2] % width) % width;
                    doy = (height - arr[3] % height / 2) + height / 2;
                }
                if (even) {
                    odxA = (odx + width / 2);
                    odyA = (ody + height / 2);
                    doxA = ((doxA * 5 + dox) / 6);
                    doyA = ((doyA * 5 + doy) / 6);
                }

                int middleWidth = width / 2;

                if (true) {
                    gc.strokeLine(odxA % (width * middleWidth), odyA % (middleWidth), dox % (middleWidth), doy % (middleWidth));
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
                    gc.strokeLine(dox + (middleWidth), doy + (middleWidth), doxA + (middleWidth), doyA + (middleWidth));
                }
            }
        }
    }

    private static double toColourValue(int x) throws NumberFormatException {
        double r = (Double.parseDouble(String.valueOf(x)) % 256D) / 256D;
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
