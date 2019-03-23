package somantic.tasks;

import somantic.controller.Controller;
import java.time.ZonedDateTime;
import javax.swing.JToggleButton;
import somantic.state.State;

public class LiveAct implements Runnable {

    private final JToggleButton liveToggleButton;
    private final JToggleButton stimulateToggle;
    private final JToggleButton translateToggle;
    private final Controller controller;
    private int scheduler;
    private ZonedDateTime then = ZonedDateTime.now();

    public LiveAct(JToggleButton liveToggleButton, JToggleButton stimulateToggle, JToggleButton translateToggle, Controller controller, int scheduler) {
        this.liveToggleButton = liveToggleButton;
        this.stimulateToggle = stimulateToggle;
        this.translateToggle = translateToggle;
        this.controller = controller;
        this.scheduler = scheduler;
    }

    @Override
    public void run() {
        while (liveToggleButton.isSelected()) {
            if (ZonedDateTime.now().isAfter(then.plusSeconds(scheduler))) {
                then = ZonedDateTime.now();
                if (State.getWords().length() > 0) {
                    stimulateToggle.setSelected(!stimulateToggle.isSelected());
                    translateToggle.setSelected(!stimulateToggle.isSelected());
                } else {
                    translateToggle.setSelected(true);
                }
                if (translateToggle.isSelected()) {
                    controller.runTranslate();
                } else if (stimulateToggle.isSelected()) {
                    State.setWords("");
                    controller.runStimulate();
                }
            }
        }
        liveToggleButton.setSelected(false);
        stimulateToggle.setSelected(false);
        translateToggle.setSelected(false);
    }
}
