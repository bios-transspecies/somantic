package MainProgram;

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

public class View {

    private JFrame window;
    private JFXPanel jfxPanel;
    private Scene scene;
    private Group root;
    private AnimationTimer timer;
    private Canvas canvas;
    private JToggleButton visualiseToggle;

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
        timer = new AnimationTimer() {
            ArrayList<Float> arrayOfAffectsArch = null;

            @Override
            public void handle(long now) {
                window.setVisible(Interface.isVisualisation());
                if (Interface.isVisualisation()) {
                    canvas = new Canvas(window.getWidth(), window.getHeight());
                    root.getChildren().clear();
                    int h = window.getHeight();
                    int w = window.getWidth();
                    GraphicsContext gc = canvas.getGraphicsContext2D();
                    gc.setFill(Color.BLACK);
                    gc.setGlobalAlpha(0.20);
                    gc.setStroke(Color.ALICEBLUE);
                    root.getChildren().add(canvas);
                    ArrayList<Float> arrayOfAffects = fft.getArrayOfAffects();
                    if (arrayOfAffects.size() > 0) {
                        arrayOfAffectsArch = arrayOfAffects;
                    } else if (arrayOfAffectsArch != null) {
                        arrayOfAffects = arrayOfAffectsArch;
                    }
                    int[] arr = new int[4];
                    for (int i = 0; i < arrayOfAffects.size(); i++) {
                        int j = i % 4;
                        if (j == 0) {
                            arr = new int[4];
                        }
                        Float r = arrayOfAffects.get(i);
                        if (r == null) {
                            arr[j] = 0;
                        } else {
                            try {
                                arr[j] = (int) r.intValue() * j * 2;
                            } catch (Exception e) {
                                arr[j] = 0;
                                System.err.println(e);
                            }
                        }
                        int odx, ody, dox, doy;
                        if (j == 3) {
                            if (i % 3 == 0) {
                                odx = (arr[0] % w + w / 2) % w;
                                ody = (h - arr[1] % h) % h;
                                dox = ((arr[2] % (w / 2)) + (w / 2)) % w;
                                doy = (h - arr[3] % h) % h;
                            } else {
                                odx = (w / 2 - arr[0] % w) % w;
                                ody = (h - arr[1] % h) % h;
                                dox = (w / 2 - arr[2] % w) % w;
                                doy = (h - arr[3] % h) % h;
                            }
                            if (odx != w / 2 || dox != w / 2) {
                                gc.strokeLine(odx, ody, dox, doy);
                            }
                        }
                    }
                    gc.setFill(Color.WHITESMOKE);
                    gc.fillText("state: " + Interface.getState(), 100, 100);
                    gc.setFont(Font.font("Arial", w / 10));
                    gc.fillText(Interface.getWord(), w / 2, h / 3);
                    gc.setFont(Font.font("Arial", w / 100));
                    for (int i = 0; i < arrayOfAffects.size() && i < Interface.getWords().split(" ").length; i++) {
                        String word = "";
                        if (Interface.getWords().contains(" ")) {
                            word = Interface.getWords().split(" ")[i];
                        }
                        Float fa = 0f;
                        if (arrayOfAffects.size() >= i) {
                            try {
                                fa = arrayOfAffects.get(i);
                            } catch (Exception e) {
                                fa = 0f;
                            }
                        }
                        Float fz = 0f;
                        if (arrayOfAffects.size() >= i + 10) {
                            fz = arrayOfAffects.get(i + 10);
                        }
                        try {
                            gc.fillText(word, fa % w, fz % h);
                        } catch (Exception e) {

                        }
                    }
                    gc.fillText(Interface.getWords(), 10, h - 100);
                    if (Interface.getWords().length() > 200) {
                        Interface.setWords(" ");
                    }
                    gc.restore();
                }
            }
        };
        timer.start();
    }

    void setVisualiseToggle(JToggleButton visualiseToggle) {
        this.visualiseToggle = visualiseToggle;
    }
}
