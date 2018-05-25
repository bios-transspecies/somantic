package MainProgram;

import Persistence.Persistence;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JTextArea;
import javax.swing.JToggleButton;

class LiveAct implements Runnable {

    private final JToggleButton liveToggleButton;
    private final JTextArea communicationBox;
    private final JToggleButton stimulateToggle;
    private final JToggleButton translateToggle;
    private final Controller controller;
    private final boolean network;
    private int newsCounter;
    private int scheduler;
    private final Object liveActLock = new Object();

    LiveAct(JToggleButton liveToggleButton, JTextArea communicationBox, JToggleButton stimulateToggle, JToggleButton translateToggle, Controller controller, boolean network, int newsCounter, int scheduler) {
        this.liveToggleButton = liveToggleButton;
        this.communicationBox = communicationBox;
        this.stimulateToggle = stimulateToggle;
        this.translateToggle = translateToggle;
        this.controller = controller;
        this.network = network;
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
                if (network) {
                    try {
                        c.sendPost(communicationBox.getText());
                        Interface.setWords(c.sendGet(newsCounter++));
                        communicationBox.setText(Interface.getWords());
                    } catch (Exception ex) {
                        Logger.getLogger(Controller.class.getName()).log(Level.SEVERE, null, ex);
                    }
                } else {
                    if (newsCounter > 0) {
                        Persistence.saveNewLineInFile(communicationBox.getText());
                        communicationBox.setText(Interface.getDefaultWords());
                    }
                    newsCounter++;
                }
                controller.runStimulate();
            }
            controller.liveActSleep(scheduler);
            scheduler = 1000 * 60 * 2;
        }
    }
}
