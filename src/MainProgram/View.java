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
            private int doyA;
            private int doxA;
            private int odyA;
            private int odxA;

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
                        boolean even = i % 2 > 0;
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
                                odx = (odxA==0 ? w / 2 : odxA);
                                ody = (odyA==0 ? h / 2 : odyA);
                                dox = ((arr[2] % (w / 2)) + (w / 2)) % w;
                                doy = (h - arr[3] % h) % h;
                            } else {
                                odx = (odxA==0 ? w / 2 : odxA);
                                ody = (odyA==0 ? h / 2 : odyA);
                                dox = (w / 2 - arr[2] % w) % w;
                                doy = (h - arr[3] % h) % h;
                            }
                            if(even){
                            odxA = (odx + w / 2) /2; 
                            odyA = (ody + h / 2) /2;
                            doxA = dox; 
                            doyA = doy;}
                            if (odx != w / 2 || dox != w / 2) {
                                gc.strokeLine((odx + odxA) / 2 , (ody + odyA) /2, dox, doy);
                                dox = even ? dox : -dox ;
                                dox = even ? doy : -doy ;
                                gc.strokeLine(doxA , doyA, dox, doy);
                            }
                        }
                    }
                    gc.setFill(Color.WHITESMOKE);
                    gc.fillText("state: " + Interface.getState(), 100, 100);
                    gc.setFont(Font.font("Times New Roman", w / 10));
                    gc.fillText(Interface.getWord(), w / 2, h / 3);
                    gc.setFont(Font.font("Arial", w / 100));
                    for (int i = 0; i < arrayOfAffects.size() && i < Interface.getWords().split(" ").length; i++) {
                        boolean even = i % 2 > 0;
                        String word = "";
                        if (Interface.getWords().contains(" ")) {
                            word = Interface.getWords().split(" ")[i];
                        }
                        Float fa = 0f;
                        if (arrayOfAffects.size() >= i) {
                            try {
                                fa = arrayOfAffects.get(i) * 2;
                            } catch (Exception e) {
                                fa = 0f;
                            }
                        }
                        Float fz = 0f;
                        if (arrayOfAffects.size() > i + 10) {
                            if (arrayOfAffects.get(i+10)>0)
                                fz = arrayOfAffects.get(i+10) * 2;
                        }
                        try {
                            
                            if(even){
                                fa = -fa * 2;
                                fz = -fz * 2;
                            }
                            fa = ((w/2) + fa) % w;
                            fz = ((h/2) + fz) % h;
                            gc.fillText(word, fa * 1.2 , fz * 1.2);
                        } catch (Exception e) {

                        }
                    }
                    gc.fillText(Interface.getWords(), 10 , h - 100);
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
