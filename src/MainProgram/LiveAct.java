package MainProgram;

import Persistence.Persistence;
import javax.swing.JTextArea;
import javax.swing.JToggleButton;

class LiveAct implements Runnable {

    private final JToggleButton liveToggleButton;
    private final JTextArea communicationBox;
    private final JToggleButton stimulateToggle;
    private final JToggleButton translateToggle;
    private final Controller controller;
    private int newsCounter;
    private int scheduler;
    private final Object liveActLock = new Object();

    LiveAct(JToggleButton liveToggleButton, JTextArea communicationBox, JToggleButton stimulateToggle, JToggleButton translateToggle, Controller controller, int newsCounter, int scheduler) {
        this.liveToggleButton = liveToggleButton;
        this.communicationBox = communicationBox;
        this.stimulateToggle = stimulateToggle;
        this.translateToggle = translateToggle;
        this.controller = controller;
        this.newsCounter = newsCounter;
        this.scheduler = scheduler;
    }

    public Object getLiveActLock() {
        return liveActLock;
    }

    @Override
    public void run() {

        HttpConnector c = new HttpConnector(Interface.getLogin(), Interface.getPassword(), Interface.getUrl());

        while (liveToggleButton.isSelected()) {

            stimulateToggle.setSelected(!stimulateToggle.isSelected());
            translateToggle.setSelected(!stimulateToggle.isSelected());

            if (translateToggle.isSelected()) {
                System.err.println("tranclate");
                Interface.setWords("");
                controller.runTranslate();
            } else if (stimulateToggle.isSelected()) {
                System.err.println("stimulate");
                if (newsCounter > 0) {
                    Persistence.saveNewSentence(communicationBox.getText());
                    communicationBox.setText(Interface.getDefaultWords());
                }
                newsCounter++;
                controller.runStimulate();
            }
            controller.liveActSleep(scheduler);
            if(scheduler>1000 * 30 * 1)
            scheduler = scheduler / 2;
        }
        liveToggleButton.setSelected(false);
        stimulateToggle.setSelected(false);
        translateToggle.setSelected(false);
    }
}
