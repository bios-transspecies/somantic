package MainProgram;

import RiTa.RiTaFactory;
import RiTa.RiTaRepo;
import java.awt.Component;
import java.io.File;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFileChooser;

public class Controller extends javax.swing.JFrame {

    static boolean recording;
    private AudioFFT fft;
    private Library library;
    private final View view;
    private final JFileChooser libraryFileChooser;
    private int scheduler = (1000 * 60 * 10); // (milisekundy, sekundy, minuty)
    private int newsCounter = 0;
    private boolean isnetwork = false;
    private final Thread liveActThread;
    private final RiTaFactory riact;
    private RiTaRepo repo;

    public Controller() {
        library = new Library();
        initComponents();
        libraryFileChooser = new JFileChooser();
        libraryFileChooser.addActionListener((java.awt.event.ActionEvent evt) -> {
            jFileChooser1ActionPerformed(evt);
        });
        jPanel1.setAutoscrolls(false);
        jPanel1.setSize(300, 350);
        fileManagerToggle.setText("file manager");
        listen();
        view = new View();
        view.start(fft);
        LiveAct liveAct = new LiveAct(liveToggleButton, communicationBox, stimulateToggle, translateToggle, this, isnetwork, newsCounter, scheduler, library);
        liveActThread = new Thread(liveAct);
        riact = new RiTaFactory();
        Interface.setDefaultString(communicationBox.getText());
        Interface.setRitaActions(riact);
        Interface.setProgressBar(jProgressBar1);
    }

    private void listen() {
        fft = new AudioFFT();
        AudioRunnable audioRunnable = new AudioRunnable(fft);
        Thread audioThread = new Thread(audioRunnable);
        audioThread.start();
    }

    private void jFileChooser1ActionPerformed(java.awt.event.ActionEvent evt) {
        File file = libraryFileChooser.getSelectedFile();
        String res = "";
        if (null != file && file.isFile()) {
            res = file.getAbsolutePath();
        }
        if (res.length() > 0) {
            Interface.setLibraryFile(res);
        }
        library.load();
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
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        liveToggleButton = new javax.swing.JToggleButton();
        visualiseToggle = new javax.swing.JToggleButton();

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
        communicationBox.setText(" In the beginning when God created the heavens and the earth,\n the earth was a formless void and darkness covered the face of the deep, while a wind from God swept over the face of the waters.\n Then God said, \"Let there be light\"; and there was light.\n And God saw that the light was good; and God separated the light from the darkness.\n God called the light Day, and the darkness he called Night. And there was evening and there was morning, the first day.\n And God said, \"Let there be a dome in the midst of the waters, and let it separate the waters from the waters.\"\n So God made the dome and separated the waters that were under the dome from the waters that were above the dome. And it was so.\n God called the dome Sky. And there was evening and there was morning, the second day.\n And God said, \"Let the waters under the sky be gathered together into one place, and let the dry land appear.\" And it was so.\n God called the dry land Earth, and the waters that were gathered together he called Seas. And God saw that it was good.\n Then God said, \"Let the earth put forth vegetation plants yielding seed, and fruit trees of every kind on earth that bear fruit with the seed in it.\" And it was so.\n The earth brought forth vegetation plants yielding seed of every kind, and trees of every kind bearing fruit with the seed in it. And God saw that it was good.\n And there was evening and there was morning, the third day.\n And God said, \"Let there be lights in the dome of the sky to separate the day from the night; and let them be for signs and for seasons and for days and years,\n and let them be lights in the dome of the sky to give light upon the earth.\" And it was so.\n God made the two great lights - the greater light to rule the day and the lesser light to rule the night - and the stars.\n God set them in the dome of the sky to give light upon the earth,\n to rule over the day and over the night, and to separate the light from the darkness. And God saw that it was good.\n And there was evening and there was morning, the fourth day.\n And God said, \"Let the waters bring forth swarms of living creatures, and let birds fly above the earth across the dome of the sky.\"\n So God created the great sea monsters and every living creature that moves, of every kind, with which the waters swarm, and every winged bird of every kind. And God saw that it was good.\n God blessed them, saying, \"Be fruitful and multiply and fill the waters in the seas, and let birds multiply on the earth.\"\n And there was evening and there was morning, the fifth day.\n And God said, \"Let the earth bring forth living creatures of every kind cattle and creeping things and wild animals of the earth of every kind.\" And it was so.\n God made the wild animals of the earth of every kind, and the cattle of every kind, and everything that creeps upon the ground of every kind. And God saw that it was good.\n Then God said, \"Let us make humankind in our image, according to our likeness; and let them have dominion over the fish of the sea, and over the birds of the air, and over the cattle, and over all the wild animals of the earth, and over every creeping thing that creeps upon the earth.\"\n So God created humankind in his image, in the image of God he created them; male and female he created them.\n God blessed them, and God said to them, \"Be fruitful and multiply, and fill the earth and subdue it; and have dominion over the fish of the sea and over the birds of the air and over every living thing that moves upon the earth.\"\n God said, \"See, I have given you every plant yielding seed that is upon the face of all the earth, and every tree with seed in its fruit; you shall have them for food.\n And to every beast of the earth, and to every bird of the air, and to everything that creeps on the earth, everything that has the breath of life, I have given every green plant for food.\" And it was so.\n God saw everything that he had made, and indeed, it was very good. And there was evening and there was morning, the sixth day.\n\n \n\n Thus the heavens and the earth were finished, and all their multitude.\n And on the seventh day God finished the work that he had done, and he rested on the seventh day from all the work that he had done.\n So God blessed the seventh day and hallowed it, because on it God rested from all the work that he had done in creation.\n These are the generations of the heavens and the earth when they were created. In the day that the LORD God made the earth and the heavens,\n when no plant of the field was yet in the earth and no herb of the field had yet sprung up - for the LORD God had not caused it to rain upon the earth, and there was no one to till the ground;\n but a stream would rise from the earth, and water the whole face of the ground -\n then the LORD God formed man from the dust of the ground, and breathed into his nostrils the breath of life; and the man became a living being.\n And the LORD God planted a garden in Eden, in the east; and there he put the man whom he had formed.\n Out of the ground the LORD God made to grow every tree that is pleasant to the sight and good for food, the tree of life also in the midst of the garden, and the tree of the knowledge of good and evil.\n A river flows out of Eden to water the garden, and from there it divides and becomes four branches.\n The name of the first is Pishon; it is the one that flows around the whole land of Havilah, where there is gold;\n and the gold of that land is good; bdellium and onyx stone are there.\n The name of the second river is Gihon; it is the one that flows around the whole land of Cush.\n The name of the third river is Tigris, which flows east of Assyria. And the fourth river is the Euphrates.\n The LORD God took the man and put him in the garden of Eden to till it and keep it.\n And the LORD God commanded the man, \"You may freely eat of every tree of the garden;\n but of the tree of the knowledge of good and evil you shall not eat, for in the day that you eat of it you shall die.\"\n Then the LORD God said, \"It is not good that the man should be alone; I will make him a helper as his partner.\"\n So out of the ground the LORD God formed every animal of the field and every bird of the air, and brought them to the man to see what he would call them; and whatever the man called every living creature, that was its name.\n The man gave names to all cattle, and to the birds of the air, and to every animal of the field; but for the man there was not found a helper as his partner.\n So the LORD God caused a deep sleep to fall upon the man, and he slept; then he took one of his ribs and closed up its place with flesh.\n And the rib that the LORD God had taken from the man he made into a woman and brought her to the man.\n Then the man said, \"This at last is bone of my bones and flesh of my flesh; this one shall be called Woman, for out of Man this one was taken.\"\n Therefore a man leaves his father and his mother and clings to his wife, and they become one flesh.\n And the man and his wife were both naked, and were not ashamed.\n\n \n\n Now the serpent was more crafty than any other wild animal that the LORD God had made. He said to the woman, \"Did God say, 'You shall not eat from any tree in the garden'?\"\n The woman said to the serpent, \"We may eat of the fruit of the trees in the garden;\n but God said, 'You shall not eat of the fruit of the tree that is in the middle of the garden, nor shall you touch it, or you shall die. '\"\n But the serpent said to the woman, \"You will not die;\n for God knows that when you eat of it your eyes will be opened, and you will be like God, knowing good and evil.\"\n So when the woman saw that the tree was good for food, and that it was a delight to the eyes, and that the tree was to be desired to make one wise, she took of its fruit and ate; and she also gave some to her husband, who was with her, and he ate.\n Then the eyes of both were opened, and they knew that they were naked; and they sewed fig leaves together and made loincloths for themselves.\n They heard the sound of the LORD God walking in the garden at the time of the evening breeze, and the man and his wife hid themselves from the presence of the LORD God among the trees of the garden.\n But the LORD God called to the man, and said to him, \"Where are you?\"\n He said, \"I heard the sound of you in the garden, and I was afraid, because I was naked; and I hid myself.\"\n He said, \"Who told you that you were naked? Have you eaten from the tree of which I commanded you not to eat?\"\n The man said, \"The woman whom you gave to be with me, she gave me fruit from the tree, and I ate.\"\n Then the LORD God said to the woman, \"What is this that you have done?\" The woman said, \"The serpent tricked me, and I ate.\"\n The LORD God said to the serpent, \"Because you have done this, cursed are you among all animals and among all wild creatures; upon your belly you shall go, and dust you shall eat all the days of your life.\n I will put enmity between you and the woman, and between your offspring and hers; he will strike your head, and you will strike his heel.\"\n To the woman he said, \"I will greatly increase your pangs in childbearing; in pain you shall bring forth children, yet your desire shall be for your husband, and he shall rule over you.\"\n And to the man he said, \"Because you have listened to the voice of your wife, and have eaten of the tree about which I commanded you, 'You shall not eat of it,' cursed is the ground because of you; in toil you shall eat of it all the days of your life;\n thorns and thistles it shall bring forth for you; and you shall eat the plants of the field.\n By the sweat of your face you shall eat bread until you return to the ground, for out of it you were taken; you are dust, and to dust you shall return.\"\n The man named his wife Eve, because she was the mother of all living.\n And the LORD God made garments of skins for the man and for his wife, and clothed them.\n Then the LORD God said, \"See, the man has become like one of us, knowing good and evil; and now, he might reach out his hand and take also from the tree of life, and eat, and live forever\"--\n therefore the LORD God sent him forth from the garden of Eden, to till the ground from which he was taken.\n He drove out the man; and at the east of the garden of Eden he placed the cherubim, and a sword flaming and turning to guard the way to the tree of life.\n\n \n\n Now the man knew his wife Eve, and she conceived and bore Cain, saying, \"I have produced a man with the help of the LORD.\"\n Next she bore his brother Abel. Now Abel was a keeper of sheep, and Cain a tiller of the ground.\n In the course of time Cain brought to the LORD an offering of the fruit of the ground,\n and Abel for his part brought of the firstlings of his flock, their fat portions. And the LORD had regard for Abel and his offering,\n but for Cain and his offering he had no regard. So Cain was very angry, and his countenance fell.\n The LORD said to Cain, \"Why are you angry, and why has your countenance fallen?\n If you do well, will you not be accepted? And if you do not do well, sin is lurking at the door; its desire is for you, but you must master it.\"\n Cain said to his brother Abel, \"Let us go out to the field.\" And when they were in the field, Cain rose up against his brother Abel, and killed him.\n Then the LORD said to Cain, \"Where is your brother Abel?\" He said, \"I do not know; am I my brother's keeper?\"\n And the LORD said, \"What have you done? Listen; your brother's blood is crying out to me from the ground!\n And now you are cursed from the ground, which has opened its mouth to receive your brother's blood from your hand.\n When you till the ground, it will no longer yield to you its strength; you will be a fugitive and a wanderer on the earth.\"\n Cain said to the LORD, \"My punishment is greater than I can bear!\n Today you have driven me away from the soil, and I shall be hidden from your face; I shall be a fugitive and a wanderer on the earth, and anyone who meets me may kill me.\"\n Then the LORD said to him, \"Not so! Whoever kills Cain will suffer a sevenfold vengeance.\" And the LORD put a mark on Cain, so that no one who came upon him would kill him.\n Then Cain went away from the presence of the LORD, and settled in the land of Nod, east of Eden.\n Cain knew his wife, and she conceived and bore Enoch; and he built a city, and named it Enoch after his son Enoch.\n To Enoch was born Irad; and Irad was the father of Mehujael, and Mehujael the father of Methushael, and Methushael the father of Lamech.\n Lamech took two wives; the name of the one was Adah, and the name of the other Zillah.\n Adah bore Jabal; he was the ancestor of those who live in tents and have livestock.\n His brother's name was Jubal; he was the ancestor of all those who play the lyre and pipe.\n Zillah bore Tubal-cain, who made all kinds of bronze and iron tools. The sister of Tubal-cain was Naamah.\n Lamech said to his wives \"Adah and Zillah, hear my voice; you wives of Lamech, listen to what I say I have killed a man for wounding me, a young man for striking me.\n If Cain is avenged sevenfold, truly Lamech seventy-sevenfold.\"\n Adam knew his wife again, and she bore a son and named him Seth, for she said, \"God has appointed for me another child instead of Abel, because Cain killed him.\"\n To Seth also a son was born, and he named him Enosh. At that time people began to invoke the name of the LORD.\n\n \n\n This is the list of the descendants of Adam. When God created humankind, he made them in the likeness of God.\n Male and female he created them, and he blessed them and named them \"Humankind\" when they were created.\n When Adam had lived one hundred thirty years, he became the father of a son in his likeness, according to his image, and named him Seth.\n The days of Adam after he became the father of Seth were eight hundred years; and he had other sons and daughters.\n Thus all the days that Adam lived were nine hundred thirty years; and he died.\n When Seth had lived one hundred five years, he became the father of Enosh.\n Seth lived after the birth of Enosh eight hundred seven years, and had other sons and daughters.\n Thus all the days of Seth were nine hundred twelve years; and he died.\n When Enosh had lived ninety years, he became the father of Kenan.\n Enosh lived after the birth of Kenan eight hundred fifteen years, and had other sons and daughters.\n Thus all the days of Enosh were nine hundred five years; and he died.\n When Kenan had lived seventy years, he became the father of Mahalalel.\n Kenan lived after the birth of Mahalalel eight hundred and forty years, and had other sons and daughters.\n Thus all the days of Kenan were nine hundred and ten years; and he died.\n When Mahalalel had lived sixty-five years, he became the father of Jared.\n Mahalalel lived after the birth of Jared eight hundred thirty years, and had other sons and daughters.\n Thus all the days of Mahalalel were eight hundred ninety-five years; and he died.\n When Jared had lived one hundred sixty-two years he became the father of Enoch.\n Jared lived after the birth of Enoch eight hundred years, and had other sons and daughters.\n Thus all the days of Jared were nine hundred sixty-two years; and he died.\n When Enoch had lived sixty-five years, he became the father of Methuselah.\n Enoch walked with God after the birth of Methuselah three hundred years, and had other sons and daughters.\n Thus all the days of Enoch were three hundred sixty-five years.\n Enoch walked with God; then he was no more, because God took him.\n When Methuselah had lived one hundred eighty-seven years, he became the father of Lamech.\n Methuselah lived after the birth of Lamech seven hundred eighty- two years, and had other sons and daughters.\n Thus all the days of Methuselah were nine hundred sixty-nine years; and he died.\n When Lamech had lived one hundred eighty-two years, he became the father of a son;\n he named him Noah, saying, \"Out of the ground that the LORD has cursed this one shall bring us relief from our work and from the toil of our hands.\"\n Lamech lived after the birth of Noah five hundred ninety-five years, and had other sons and daughters.\n Thus all the days of Lamech were seven hundred seventy-seven years; and he died.\n After Noah was five hundred years old, Noah became the father of Shem, Ham, and Japheth.\n\n \n\n When people began to multiply on the face of the ground, and daughters were born to them,\n the sons of God saw that they were fair; and they took wives for themselves of all that they chose.\n Then the LORD said, \"My spirit shall not abide in mortals forever, for they are flesh; their days shall be one hundred twenty years.\"\n The Nephilim were on the earth in those days - and also afterward - when the sons of God went in to the daughters of humans, who bore children to them. These were the heroes that were of old, warriors of renown.\n The LORD saw that the wickedness of humankind was great in the earth, and that every inclination of the thoughts of their hearts was only evil continually.\n And the LORD was sorry that he had made humankind on the earth, and it grieved him to his heart.\n So the LORD said, \"I will blot out from the earth the human beings I have created - people together with animals and creeping things and birds of the air, for I am sorry that I have made them.\"\n But Noah found favor in the sight of the LORD.\n These are the descendants of Noah. Noah was a righteous man, blameless in his generation; Noah walked with God.\n And Noah had three sons, Shem, Ham, and Japheth.\n Now the earth was corrupt in God's sight, and the earth was filled with violence.\n And God saw that the earth was corrupt; for all flesh had corrupted its ways upon the earth.\n And God said to Noah, \"I have determined to make an end of all flesh, for the earth is filled with violence because of them; now I am going to destroy them along with the earth.\n Make yourself an ark of cypress wood; make rooms in the ark, and cover it inside and out with pitch.\n This is how you are to make it the length of the ark three hundred cubits, its width fifty cubits, and its height thirty cubits.\n Make a roof for the ark, and finish it to a cubit above; and put the door of the ark in its side; make it with lower, second, and third decks.\n For my part, I am going to bring a flood of waters on the earth, to destroy from under heaven all flesh in which is the breath of life; everything that is on the earth shall die.\n But I will establish my covenant with you; and you shall come into the ark, you, your sons, your wife, and your sons' wives with you.\n And of every living thing, of all flesh, you shall bring two of every kind into the ark, to keep them alive with you; they shall be male and female.\n Of the birds according to their kinds, and of the animals according to their kinds, of every creeping thing of the ground according to its kind, two of every kind shall come in to you, to keep them alive.\n Also take with you every kind of food that is eaten, and store it up; and it shall serve as food for you and for them.\"\n Noah did this; he did all that God commanded him.");
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

        jLabel1.setText("Transspecies Language Translator 1.0");

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
                            .addComponent(jScrollPane1)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                                .addComponent(fileManagerToggle)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jLabel1)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(liveToggleButton)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(buttonLoginPassword)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(inputLogin, javax.swing.GroupLayout.PREFERRED_SIZE, 105, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(inputPassword, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(stimulateToggle)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(translateToggle)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(visualiseToggle)
                                .addGap(18, 18, 18)
                                .addComponent(jLabel2)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jProgressBar1, javax.swing.GroupLayout.PREFERRED_SIZE, 214, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(2, 2, 2)
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(12, 12, 12)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(inputLogin, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(buttonLoginPassword)
                            .addComponent(inputPassword, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(fileManagerToggle)
                            .addComponent(liveToggleButton)))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jLabel1)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 220, Short.MAX_VALUE)
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
    }//GEN-LAST:event_visualiseToggleActionPerformed

    private void liveToggleButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_liveToggleButtonActionPerformed
        if (liveToggleButton.isSelected()) {
            liveActThread.start();
        }
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
        library.load();
        if (translateToggle.isSelected()) {
            translateToggle.setText("Translation");
            Interface.setIsVisualising(true);
            Interface.setState("translate");
            recording = true;
            communicationBox.setText("");
            communicationBox.setEditable(false);
            fft.getMatrix();
            TranslatorRunnable translatorRunnable = new TranslatorRunnable(translateToggle, library, communicationBox, fft, riact);
            Thread translatorThread = new Thread(translatorRunnable);
            translatorThread.start();
        } else {
            translateToggle.setText("Translate");
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
        library.load();
        if (stimulateToggle.isSelected()) {
            stimulateToggle.setText("Stimulation");
            Interface.setState("Stimulate");
            recording = true;
            Interface.setIsVisualising(recording);
            String already = Interface.getStimulatedAlready();
            Interface.setDefaultString(communicationBox.getText().replace(already.trim(), ""));
            communicationBox.setText(Interface.getDefaultString());
            repo = riact.explain(Interface.getDefaultString());
            StimulationRunnable stimulationRunnable = new StimulationRunnable(repo, stimulateToggle, library, liveActThread, fft, liveToggleButton);
            Thread stimulationThread = new Thread(stimulationRunnable);
            stimulationThread.start();
        } else {
            stimulateToggle.setText("Stimulate");
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

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton buttonLoginPassword;
    private javax.swing.JTextArea communicationBox;
    private javax.swing.JToggleButton fileManagerToggle;
    private javax.swing.JTextField inputLogin;
    private javax.swing.JPasswordField inputPassword;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JProgressBar jProgressBar1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JToggleButton liveToggleButton;
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
}
