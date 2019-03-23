package somantic.processors;

import java.awt.Dimension;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import javafx.animation.AnimationTimer;
import javafx.embed.swing.JFXPanel;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javax.swing.JFrame;
import javax.swing.JToggleButton;
import somantic.synthesizer.Synthesizer;

public class View {

    private JFrame window;
    private final JFXPanel jfxPanel;
    private final Scene scene;
    private final Group root;
    private AnimationTimer timer;
    private Canvas canvas;
    private JToggleButton visualiseToggle;
    private final Synthesizer synthesizer = new Synthesizer();
    
    public View() {
        jfxPanel = new JFXPanel();
        window = new JFrame();
        window.setMinimumSize(new Dimension(640, 480));
        window.setDefaultCloseOperation(JFrame.ICONIFIED);
        window.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                window.setVisible(false);
                Interface.setVisualisation(false);
                visualiseToggle.setSelected(false);
            }
        });
        window.add(jfxPanel);
        root = new Group();
        scene = new Scene(root, window.getWidth(), window.getHeight(), Color.BLACK);
        jfxPanel.setScene(scene);
    }

    public void start(AudioFFT fft) {
        synthesizer.play(fft);
        timer = new AnimationTimer() {
            ArrayList<Integer> arrayOfAffectsArch = null;
            private int doyA;
            private int doxA;
            private int odyA;
            private int odxA;
            @Override
            public void handle(long now) {
                ArrayList<Integer> arrayOfAffects = fft.getArrayOfAffects();
                window.setVisible(Interface.isVisualisation());
                if (Interface.isVisualisation()) {
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
            }

            private void textWriter(GraphicsContext gc, int w, int h, ArrayList<Integer> arrayOfAffects) {
                gc.setGlobalAlpha(0.80);
                gc.setFill(Color.WHITE);
                gc.fillText("state: " + Interface.getState(), 100, 100);
                gc.setFont(Font.font("Impact", w / 30));
                if (Interface.getWord() != null) {
                    gc.fillText(Interface.getWord().toString(), w / 4, h / 3);
                    gc.setFont(Font.font("Arial", w / 100));
                    gc.fillText(Interface.getWord().getDescription(), w / 4, (h / 3) + 30);
                    String[] sentences = Interface.getSentences().split(" <br> ");
                    gc.setGlobalAlpha(0.20);
                    int line = 40;
                    for (String sentence : sentences) {
                        line = line + 12;
                        if (!sentence.isEmpty()) {
                            gc.fillText(sentence, w / 10, (h / 3) + line, w - w / 10);
                        }
                    }
                    gc.fillText("words: " + Interface.getWords().toLowerCase().toLowerCase(), 100, 120);
                    gc.fillText("sentence: " + Interface.getStringSentence().toLowerCase(), 100, 140);
                    if (Interface.getWords().length() > 200) {
                        Interface.setWords(" ");
                    }
                }
            }

            private void graphicsLineDrawer(ArrayList<Integer> arrayOfAffects, int w, int h, GraphicsContext gc) {
                gc.setGlobalAlpha(0.20);
                if (arrayOfAffects.size() > 0) {
                    for (int i = 0; arrayOfAffects.size() < i; i++) {
                        arrayOfAffectsArch.add(i,
                                (arrayOfAffectsArch.get(i) + arrayOfAffects.get(i)) / 50);
                    }
                }
                if (arrayOfAffectsArch != null) {
                    arrayOfAffects = arrayOfAffectsArch;
                }
                int k = 0;
                int[] arr = new int[4];
                for (int i = 0; i < arrayOfAffects.size(); i++) {
                    boolean even = i % 2 > 0;
                    int j = i % 4;
                    if (j == 0) {
                        arr = new int[4];
                    }

                    if (arrayOfAffects.get(i) != null) {
                        int r = arrayOfAffects.get(i);
                        k = r + (i * j) / 10;
                        try {
                            arr[j] = k * 2 - i;
                        } catch (Exception e) {
                            arr[j] = 0;
                            System.err.println(e);
                        }
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

                            int f = w / 2;

                            if (odx != w / 2 || dox != w / 2) {
                                gc.strokeLine(odxA % (w * f), odyA % (f), dox % (f), doy % (f));
                                dox = even ? dox : -dox;
                                doy = even ? doy : -doy;
                                doxA = even ? doxA : -doxA;
                                doyA = even ? doyA : -doyA;
                                gc.strokeLine(dox % (f), doy % (f), doxA % (f), doyA % (f));
                            }
                        }
                    }
                }
            }
        };
        timer.start();
    }

    public void setVisualiseToggle(JToggleButton visualiseToggle) {
        this.visualiseToggle = visualiseToggle;
    }
}
