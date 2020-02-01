package somantic.controller;

import somantic.processors.AudioFFT;
import somantic.tasks.AudioRunnable;
import somantic.state.State;
import somantic.tasks.LiveAct;
import somantic.tasks.StimulationRunnable;
import somantic.tasks.TranslatorRunnable;
import somantic.view.View;
import somantic.library.SomanticFacade;
import java.awt.Color;
import java.awt.Component;
import java.io.File;
import javax.swing.JFileChooser;
import javax.swing.JToggleButton;
import somantic.persistence.Persistence;

public class Controller extends javax.swing.JFrame {

    public static volatile boolean recording;
    private AudioFFT fft;
    private final View view;
    private final JFileChooser libraryFileChooser;
    private int scheduler = 100; // (milisekundy, sekundy, minuty)
    private final SomanticFacade somanticFactory;

    public Controller() {
        somanticFactory = State.getSomanticFacade();
        initComponents();
        libraryFileChooser = new JFileChooser();
        libraryFileChooser.addActionListener((java.awt.event.ActionEvent evt) -> {
            libraryFileChooser.showSaveDialog(null);
            jFileChooser1ActionPerformed(evt);
        });
        jPanel1.setAutoscrolls(false);
        jPanel1.setSize(300, 350);
        fileManagerToggle.setText("load .txt or .pdf");
        listen();
        view = new View();
        view.start(fft);
        messages.setBackground(Color.GRAY);
        State.setBufferedText(communicationBox.getText());
        State.setProgressBar(jProgressBar1);
        State.subscribeMessages(this);
        State.setNnStateLabel(nnStateLabel);
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
        boolean error = false;
        if (null != file && file.isFile()) {
            String res = file.getAbsolutePath();
            State.setLiteratureLocation(res);
            messages.setText("Trying to load file '" + State.getLiteratureLocation() + "' as a stimulation text.");
            try {
                communicationBox.setText(normalize(Persistence.loadLiteraure(State.getLiteratureLocation())));
            } catch (Exception e) {
                error = true;
                messages.setText("couldn't load any text... " + e);
            }
            if (translateToggle.isSelected()) {
                messages.setText("Please stop the translation process first!");
            } else if (somanticFactory.getSomanticRepo() == null) {
                messages.setBackground(Color.red);
                messages.setText("Repossitory is empty. Could not be loaded. Please just try again.");
            } else if (!error) {
                messages.setText("Loaded successfully!");
            }
        } else if (null != file) {
            messages.setText("there is no file selected so I am not able to load anything...");
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
        jLabel1 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        messages = new javax.swing.JLabel();
        jToggleButton1 = new javax.swing.JToggleButton();
        nnStateLabel = new javax.swing.JLabel();
        filler1 = new javax.swing.Box.Filler(new java.awt.Dimension(0, 0), new java.awt.Dimension(0, 0), new java.awt.Dimension(32767, 0));

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setResizable(false);

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
        communicationBox.setMargin(new java.awt.Insets(10, 10, 10, 10));
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
        fileManagerToggle.setToolTipText("");
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

        jLabel1.setText("::SOMANTIC:: interspecies translator");

        messages.setText("Welcome to SOMANTIC [SOMATIC / SEMANTIC]. Affects to the English translator.");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(messages, javax.swing.GroupLayout.PREFERRED_SIZE, 551, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(121, 121, 121))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(messages, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        jToggleButton1.setText("meditate");
        jToggleButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jToggleButton1ActionPerformed(evt);
            }
        });

        nnStateLabel.setBackground(new java.awt.Color(51, 51, 51));
        nnStateLabel.setForeground(new java.awt.Color(255, 51, 51));
        nnStateLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        nnStateLabel.setText("learning...");
        nnStateLabel.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jSeparator1)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel1Layout.createSequentialGroup()
                        .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, 552, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(0, 0, Short.MAX_VALUE)
                                .addComponent(filler1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(nnStateLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 211, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel1Layout.createSequentialGroup()
                        .addComponent(stimulateToggle, javax.swing.GroupLayout.PREFERRED_SIZE, 138, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(translateToggle)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(visualiseToggle)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel2)
                        .addGap(18, 18, 18)
                        .addComponent(jProgressBar1, javax.swing.GroupLayout.PREFERRED_SIZE, 177, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(fileManagerToggle)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jToggleButton1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(liveToggleButton)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(buttonLoginPassword)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(inputLogin, javax.swing.GroupLayout.PREFERRED_SIZE, 105, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(inputPassword, javax.swing.GroupLayout.PREFERRED_SIZE, 95, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 781, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(2, 2, 2)
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(inputLogin, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(buttonLoginPassword)
                    .addComponent(inputPassword, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(fileManagerToggle)
                    .addComponent(liveToggleButton)
                    .addComponent(jLabel1)
                    .addComponent(jToggleButton1))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(filler1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(nnStateLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 228, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(translateToggle)
                        .addComponent(stimulateToggle)
                        .addComponent(jLabel2)
                        .addComponent(visualiseToggle))
                    .addComponent(jProgressBar1, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(224, 224, 224))
        );

        stimulateToggle.getAccessibleContext().setAccessibleDescription("");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 359, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(17, 17, 17))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jToggleButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jToggleButton1ActionPerformed
        JToggleButton b = (JToggleButton) evt.getSource();
        if (!b.isSelected()) {
            State.getNeuralNetworkTrainer().stopLearning();
        } else {
            State.getNeuralNetworkTrainer().useSomanticLibraryToLearn(b);
        }
    }//GEN-LAST:event_jToggleButton1ActionPerformed

    private void visualiseToggleActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_visualiseToggleActionPerformed
        State.setVisualisation(visualiseToggle.isSelected());
        view.setVisualiseToggle(visualiseToggle);
        messages.setText("Visuals are fine for fine art.");
    }//GEN-LAST:event_visualiseToggleActionPerformed

    private void liveToggleButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_liveToggleButtonActionPerformed
        if (liveToggleButton.isSelected()) {
            LiveAct liveAct = new LiveAct(liveToggleButton, stimulateToggle, translateToggle, this, scheduler);
            Thread liveActThread = new Thread(liveAct, "liveActThread");
            liveActThread.setPriority(Thread.MIN_PRIORITY);
            liveActThread.start();
            messages.setText("Live mode auto-switch between stimulation and translation mode.");
        } 
        else messages.setText("Live mode is turned off. Manual action only.");
    }//GEN-LAST:event_liveToggleButtonActionPerformed

    private void fileManagerToggleActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_fileManagerToggleActionPerformed
        javax.swing.JToggleButton button = ((javax.swing.JToggleButton) evt.getSource());
        button.setSelected(!button.isSelected());
        libraryFileChooser.showOpenDialog((new Component() {
        }));
    }//GEN-LAST:event_fileManagerToggleActionPerformed

    private void inputPasswordActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_inputPasswordActionPerformed
        State.setPassword(String.copyValueOf(inputPassword.getPassword()));
    }//GEN-LAST:event_inputPasswordActionPerformed

    private void translateToggleActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_translateToggleActionPerformed
        if (evt != null) {
            liveToggleButton.setSelected(false);
        }
        stimulateToggle.setSelected(false);
        stimulateToggle.setText("Stimulate");
        State.setWords("");
        if (translateToggle.isSelected()) {
            translate();
        } else {
            translated();
        }
    }//GEN-LAST:event_translateToggleActionPerformed

    private void speakAndStimulate(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_speakAndStimulate
        if (evt != null) {
            liveToggleButton.setSelected(false);
        }
        translateToggle.setSelected(false);
        translateToggle.setText("Translate");
        if (stimulateToggle.isSelected()) {
            stimulate();
        } else {
            stimulated();
        }
    }//GEN-LAST:event_speakAndStimulate

    private void inputLoginActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_inputLoginActionPerformed
        State.setLogin(inputLogin.getText());
    }//GEN-LAST:event_inputLoginActionPerformed

    private void loginActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_loginActionPerformed
        State.setPassword(String.copyValueOf(inputPassword.getPassword()));
        State.setLogin(inputLogin.getText());
    }//GEN-LAST:event_loginActionPerformed

    private void translated() {
        translateToggle.setText("Translate");
        communicationBox.setEditable(true);
        messages.setText("Maybe we should to expand vocabulary and stimulate more?");
        State.setState("stopped");
        recording = false;
        State.setIsVisualising(false);
        communicationBox.setEditable(true);
    }

    private void translate() {
        stimulated();
        translateToggle.setText("Translation");
        messages.setText("Translating the affests into words and trying to find some sentences.");
        State.setIsVisualising(true);
        State.setState("translate");
        recording = true;
        communicationBox.setText("");
        communicationBox.setEditable(false);
        TranslatorRunnable translatorRunnable = new TranslatorRunnable(translateToggle, communicationBox, fft, somanticFactory);
        Thread translatorThread = new Thread(translatorRunnable, "translatorThread");
        translatorThread.setPriority(Thread.MIN_PRIORITY);
        communicationBox.setEditable(false);
        translatorThread.start();
    }

    private void stimulated() {
        communicationBox.setEditable(true);
        stimulateToggle.setText("Stimulate");
        if (somanticFactory.getSomanticRepo() != null) {
            messages.setText("OK! To try to translate some affects to English push TRANSLATE button.");
            if (State.getNeuralNetworkTrainer().getSize() > 1) {
                new Thread(() -> State.getNeuralNetworkTrainer().learn()).start();
            }
        } else {
            messages.setText("Something went wrong. Library of affects still is empty. Try again!");
        }
        State.setState("stopped");
        recording = false;
    }

    private void stimulate() {
        messages.setText("Building relations between words and affects in progress.");
        stimulateToggle.setText("Stimulation");
        State.setState("Stimulate");
        recording = true;
        State.setIsVisualising(visualiseToggle.isSelected());
        State.setBufferedText(normalize(communicationBox.getText()));
        new Thread(() -> {
            communicationBox.setText(normalize(State.getBufferedText()));
            somanticFactory.addTextToRepo(normalize(State.getBufferedText()));
        }).start();
        communicationBox.setEditable(false);
        StimulationRunnable stimulationRunnable = new StimulationRunnable(somanticFactory, stimulateToggle, fft, communicationBox);
        Thread stimulationThread = new Thread(stimulationRunnable, "stimulationThread");
        stimulationThread.setPriority(Thread.MIN_PRIORITY);
        stimulationThread.start();
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton buttonLoginPassword;
    private static javax.swing.JTextArea communicationBox;
    private javax.swing.JToggleButton fileManagerToggle;
    private javax.swing.Box.Filler filler1;
    private javax.swing.JTextField inputLogin;
    private javax.swing.JPasswordField inputPassword;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    public static javax.swing.JProgressBar jProgressBar1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JToggleButton jToggleButton1;
    private javax.swing.JToggleButton liveToggleButton;
    private javax.swing.JLabel messages;
    private javax.swing.JLabel nnStateLabel;
    private javax.swing.JToggleButton stimulateToggle;
    private javax.swing.JToggleButton translateToggle;
    private javax.swing.JToggleButton visualiseToggle;
    // End of variables declaration//GEN-END:variables

    public void runTranslate() {
        translateToggleActionPerformed(null);
    }

    public void runStimulate() {
        speakAndStimulate(null);
    }

    public void setMessage(String message) {
        this.messages.setText(message);
    }

    private String normalize(String text) {
        return text.trim()
                .replace("\n", " ")
                .replace("\r", " ")
                .replace("_", " ")
                .replace("-", " ")
                .replaceAll("[^a-zA-Z\\s\\.]", "")
                .replace(" .", ". ")
                .replace(". .", ".")
                .replace("..", ".")
                .replace(". .", ".")
                .toLowerCase().replace("  ", " ");
    }
}
