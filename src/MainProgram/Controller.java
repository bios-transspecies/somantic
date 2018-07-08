package MainProgram;

import WNprocess.SomanticFactory;
import java.awt.Color;
import java.awt.Component;
import java.io.File;
import java.util.concurrent.Callable;
import java.util.concurrent.Executors;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFileChooser;

public class Controller extends javax.swing.JFrame {

    static volatile boolean recording;
    private AudioFFT fft;
    private final View view;
    private final JFileChooser libraryFileChooser;
    private int scheduler = (1000 * 60 * 10); // (milisekundy, sekundy, minuty)
    private int newsCounter = 0;
    private boolean isnetwork = false;
    private final Thread liveActThread;
    private final SomanticFactory wNFactory;

    public Controller() {
        wNFactory = new SomanticFactory();
        Interface.setRitaFactory(wNFactory);
        initComponents();
        libraryFileChooser = new JFileChooser();
        libraryFileChooser.addActionListener((java.awt.event.ActionEvent evt) -> {
            libraryFileChooser.showSaveDialog(null);
            jFileChooser1ActionPerformed(evt);
        });
        jPanel1.setAutoscrolls(false);
        jPanel1.setSize(300, 350);
        fileManagerToggle.setText("file manager");
        listen();
        view = new View();
        view.start(fft);
        messages.setBackground(Color.GRAY);
        LiveAct liveAct = new LiveAct(liveToggleButton, communicationBox, stimulateToggle, translateToggle, this, isnetwork, newsCounter, scheduler);
        liveActThread = new Thread(liveAct, "liveActThread");
        liveActThread.setPriority(Thread.MIN_PRIORITY);
        Interface.setBufferedText(communicationBox.getText());
        Interface.setProgressBar(jProgressBar1);
    }

    private void listen() {
        fft = new AudioFFT();
        AudioRunnable audioRunnable = new AudioRunnable(fft);
        Thread audioThread = new Thread(audioRunnable, "audioThread");
        audioThread.setDaemon(true);
        audioThread.setPriority(Thread.MAX_PRIORITY);
        audioThread.start();
    }

    private void jFileChooser1ActionPerformed(java.awt.event.ActionEvent evt) {
        File file = libraryFileChooser.getSelectedFile();
        if (null != file && file.isFile()) {
            String res = file.getAbsolutePath();
            Interface.setLibraryFile(res);
            messages.setText("You are using file '" + Interface.getLibraryFile() + "' as database.");
        } else if (null != file) {
            messages.setText("You are going to create new file '" + Interface.getLibraryFile() + "' as database.");
        }
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        buttonLoginPassword = new javax.swing.JButton();
        inputLogin = new javax.swing.JTextField();
        stimulateToggle = new javax.swing.JToggleButton();
        jProgressBar1 = new javax.swing.JProgressBar();
        jScrollPane1 = new javax.swing.JScrollPane();
        communicationBox = new javax.swing.JTextArea();
        translateToggle = new javax.swing.JToggleButton();
        inputPassword = new javax.swing.JPasswordField();
        fileManagerToggle = new javax.swing.JToggleButton();
        jSeparator1 = new javax.swing.JSeparator();
        jLabel2 = new javax.swing.JLabel();
        liveToggleButton = new javax.swing.JToggleButton();
        visualiseToggle = new javax.swing.JToggleButton();
        saveButton = new javax.swing.JButton();
        loadButton = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        messages = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setMaximumSize(new java.awt.Dimension(600, 300));

        buttonLoginPassword.setText("Login");
        buttonLoginPassword.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                loginActionPerformed(evt);
            }
        });

        inputLogin.setText("plant@michalbrzezinski.org");
        inputLogin.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                inputLoginActionPerformed(evt);
            }
        });

        stimulateToggle.setText("Stimulate");
        stimulateToggle.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                speakAndStimulate(evt);
            }
        });

        jProgressBar1.setValue(0);
        jProgressBar1.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jProgressBar1.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));

        communicationBox.setColumns(20);
        communicationBox.setLineWrap(true);
        communicationBox.setRows(5);
        communicationBox.setToolTipText("");
        communicationBox.setWrapStyleWord(true);
        jScrollPane1.setViewportView(communicationBox);

        translateToggle.setText("Translate");
        translateToggle.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                translateToggleActionPerformed(evt);
            }
        });

        inputPassword.setText("PlantsPassword#1");
        inputPassword.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                inputPasswordActionPerformed(evt);
            }
        });

        fileManagerToggle.setText("hide file manager");
        fileManagerToggle.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                fileManagerToggleActionPerformed(evt);
            }
        });

        jLabel2.setText("BIOSemiotic Art Project by Michal Brzezinski");

        liveToggleButton.setText("live!");
        liveToggleButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                liveToggleButtonActionPerformed(evt);
            }
        });

        visualiseToggle.setText("Visualise");
        visualiseToggle.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                visualiseToggleActionPerformed(evt);
            }
        });

        saveButton.setText("save");
        saveButton.setToolTipText("save existing library (relation words to affects) for future sessions in selected file"); // NOI18N
        saveButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                saveButtonActionPerformed(evt);
            }
        });

        loadButton.setText("load");
        loadButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                loadButtonActionPerformed(evt);
            }
        });

        jLabel1.setText("::SOMANTIC::");

        messages.setText("Welcome to :: SOMANTIC ::  [SOMATIC / SEMANTIC] translator for affects to the English that could let to speak the plants, or tissues.");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(messages, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addComponent(messages)
                .addGap(0, 6, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jSeparator1)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                                .addComponent(fileManagerToggle)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(saveButton)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(loadButton)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(liveToggleButton)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(buttonLoginPassword)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(inputLogin, javax.swing.GroupLayout.PREFERRED_SIZE, 105, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(inputPassword, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(stimulateToggle)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(translateToggle)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(visualiseToggle)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jLabel2)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jProgressBar1, javax.swing.GroupLayout.PREFERRED_SIZE, 214, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.TRAILING))))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(2, 2, 2)
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(12, 12, 12)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(inputLogin, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(buttonLoginPassword)
                    .addComponent(inputPassword, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(fileManagerToggle)
                    .addComponent(liveToggleButton)
                    .addComponent(saveButton)
                    .addComponent(loadButton)
                    .addComponent(jLabel1))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 282, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(translateToggle)
                        .addComponent(stimulateToggle)
                        .addComponent(jLabel2)
                        .addComponent(visualiseToggle))
                    .addComponent(jProgressBar1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        stimulateToggle.getAccessibleContext().setAccessibleDescription("");
        loadButton.getAccessibleContext().setAccessibleDescription("load library (relations words to affects) from selected file"); // NOI18N

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void visualiseToggleActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_visualiseToggleActionPerformed
        Interface.setVisualisation(visualiseToggle.isSelected());
        view.setVisualiseToggle(visualiseToggle);
        messages.setText("Visuals are fine for fine art.");
    }//GEN-LAST:event_visualiseToggleActionPerformed

    private void liveToggleButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_liveToggleButtonActionPerformed
        if (liveToggleButton.isSelected()) {
            liveActThread.start();
        }else if(liveActThread.isAlive()){
            liveActThread.notify();
        }
        messages.setText("Live mode auto-switch between stimulation and translation mode.");
    }//GEN-LAST:event_liveToggleButtonActionPerformed

    private void fileManagerToggleActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_fileManagerToggleActionPerformed
        javax.swing.JToggleButton button = ((javax.swing.JToggleButton) evt.getSource());
        button.setSelected(!button.isSelected());
        libraryFileChooser.showOpenDialog((new Component() {
        }));
    }//GEN-LAST:event_fileManagerToggleActionPerformed

    private void inputPasswordActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_inputPasswordActionPerformed
        Interface.setPassword(String.copyValueOf(inputPassword.getPassword()));
    }//GEN-LAST:event_inputPasswordActionPerformed

    private void translateToggleActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_translateToggleActionPerformed
        if (evt != null) {
            liveToggleButton.setSelected(false);
        }
        stimulateToggle.setSelected(false);
        stimulateToggle.setText("Stimulate");
        Interface.setWords("");
        if (translateToggle.isSelected()) {
            translateToggle.setText("Translation");
            messages.setText("Translating the affests into words and trying to find some sentences.");
            Interface.setIsVisualising(true);
            Interface.setState("translate");
            recording = true;
            communicationBox.setText("");
            communicationBox.setEditable(false);
            TranslatorRunnable translatorRunnable = new TranslatorRunnable(translateToggle, communicationBox, fft, wNFactory);
            Thread translatorThread = new Thread(translatorRunnable, "translatorThread");
            translatorThread.setPriority(Thread.MIN_PRIORITY);
            translatorThread.start();
        } else {
            translateToggle.setText("Translate");
            messages.setText("Maybe we should to expand vocabulary and stimulate more?");
            Interface.setState("stopped");
            recording = false;
            Interface.setIsVisualising(false);
            communicationBox.setEditable(true);
        }
    }//GEN-LAST:event_translateToggleActionPerformed

    private void speakAndStimulate(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_speakAndStimulate
        if (evt != null) {
            liveToggleButton.setSelected(false);
        }
        translateToggle.setSelected(false);
        translateToggle.setText("Translate");
        if (stimulateToggle.isSelected()&&communicationBox.getText().length()>5) {
            messages.setText("Building relations between words and affects in progress.");
            stimulateToggle.setText("Stimulation");
            Interface.setState("Stimulate");
            recording = true;
            Interface.setIsVisualising(visualiseToggle.isSelected());
            String already = Interface.getStimulatedAlready();
            Interface.setBufferedText(normalize(communicationBox.getText()));
            Callable<Object> ca = Executors.callable(()->communicationBox.setText(Interface.getBufferedText()));
            try { ca.call(); Thread.sleep(10);} catch (Exception ex) { Logger.getLogger(Controller.class.getName()).log(Level.SEVERE, null, ex); }
            wNFactory.addTextToRepo(Interface.getBufferedText());
            StimulationRunnable stimulationRunnable = new StimulationRunnable(wNFactory, stimulateToggle, liveActThread, fft, liveToggleButton);
            Thread stimulationThread = new Thread(stimulationRunnable, "stimulationThread");
            stimulationThread.setPriority(Thread.MIN_PRIORITY);
            stimulationThread.start();
        }else if(communicationBox.getText().length()<5){
            messages.setText("To process stimmulation please copy and paste some text below.");
            stimulateToggle.setSelected(false);
        }else {
            stimulateToggle.setText("Stimulate");
            if (wNFactory.getRitaRepo() != null) {
                messages.setText("OK! To try to translate some affects to English push TRANSLATE button.");
            } else {
                messages.setText("Something went wrong. Library of affects still is empty. Try again!");
            }
            Interface.setState("stopped");
            recording = false;
        }
    }//GEN-LAST:event_speakAndStimulate

    private void inputLoginActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_inputLoginActionPerformed
        Interface.setLogin(inputLogin.getText());
    }//GEN-LAST:event_inputLoginActionPerformed

    private void loginActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_loginActionPerformed
        Interface.setPassword(String.copyValueOf(inputPassword.getPassword()));
        Interface.setLogin(inputLogin.getText());
        isnetwork = true;
    }//GEN-LAST:event_loginActionPerformed

    private void saveButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_saveButtonActionPerformed
        if (stimulateToggle.isSelected()) {
            messages.setText("Please stop the stimulation process to avoid errors.");
        } else if (wNFactory.getRitaRepo() != null) {
            try {
                wNFactory.saveRepo();
                messages.setBackground(Color.GRAY);
                messages.setText("OK! Saved. Objects saved " + wNFactory.getRitaRepo().size());
            } catch (Exception e) {
                messages.setText("Something went wrong! " + e.getMessage());
                System.err.println(e);
            }
        } else {
            messages.setBackground(Color.red);
            messages.setText("Repossitory is empty. Could not be saved. Repo size: " + wNFactory.getRitaRepo().size() + " objects.");
        }
    }//GEN-LAST:event_saveButtonActionPerformed

    private void loadButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_loadButtonActionPerformed
        try {
            wNFactory.loadRepo();
        } catch (Exception e) {
            messages.setText("ERROR: " + e.getLocalizedMessage() + " ACTUAL REPO SIZE " + wNFactory.getRitaRepo().size());
        }
        if (translateToggle.isSelected()) {
            messages.setText("Please stop the translation process first!");
        } else if (wNFactory.getRitaRepo() == null) {
            messages.setBackground(Color.red);
            messages.setText("Repossitory is empty. Could not be loaded. Please just try again.");
        } else {
            messages.setText("Loaded successfully! Objects count: " + wNFactory.getRitaRepo().size());
        }
    }//GEN-LAST:event_loadButtonActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton buttonLoginPassword;
    private static javax.swing.JTextArea communicationBox;
    private javax.swing.JToggleButton fileManagerToggle;
    private javax.swing.JTextField inputLogin;
    private javax.swing.JPasswordField inputPassword;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    public static javax.swing.JProgressBar jProgressBar1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JToggleButton liveToggleButton;
    private javax.swing.JButton loadButton;
    private javax.swing.JLabel messages;
    private javax.swing.JButton saveButton;
    private javax.swing.JToggleButton stimulateToggle;
    private javax.swing.JToggleButton translateToggle;
    private javax.swing.JToggleButton visualiseToggle;
    // End of variables declaration//GEN-END:variables

    void runTranslate() {
        translateToggleActionPerformed(null);
    }

    void runStimulate() {
        speakAndStimulate(null);
    }

    void liveActSleep(int scheduler) {
        this.scheduler = scheduler;
        synchronized (liveActThread) {
            try {
                liveActThread.sleep(scheduler);
            } catch (InterruptedException ex) {
                Logger.getLogger(Controller.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    private String normalize(String text) {
        return text.trim().replace("\n", " ").replace("\r", " ").replace("_", " ").replace("-", " ").replaceAll( "[^a-zA-Z0-9\\s\\.]", "" ).toLowerCase().replaceAll("  ", " ");
    }
}
