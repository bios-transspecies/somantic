package somantic.view;

import java.awt.Dimension;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javafx.animation.AnimationTimer;
import javafx.embed.swing.JFXPanel;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javax.swing.JFrame;
import javax.swing.JToggleButton;
import somantic.processors.AudioFFT;
import somantic.state.State;

public class View {

    private JFrame window;
    private final JFXPanel jfxPanel;
    private final Scene scene;
    private final Group root;
    private AnimationTimer timer;
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
                State.setVisualisation(false);
                visualiseToggle.setSelected(false);
            }
        });
        window.add(jfxPanel);
        root = new Group();
        scene = new Scene(root, window.getWidth(), window.getHeight(), Color.BLACK);
        jfxPanel.setScene(scene);
    }

    public void start(AudioFFT fft) {
        timer = new AnimationTimerImpl(fft, window, root);
        timer.start();
    }

    public void setVisualiseToggle(JToggleButton visualiseToggle) {
        this.visualiseToggle = visualiseToggle;
    }

}
