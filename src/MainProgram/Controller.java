package MainProgram;

import WNprocess.SomanticFactory;
import java.awt.Color;
import java.awt.Component;
import java.io.File;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFileChooser;

public class Controller extends javax.swing.JFrame {

    static boolean recording;
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
        liveActThread = new Thread(liveAct);
        Interface.setBufferedText(communicationBox.getText());
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
        messages.setText("You are using file '" + Interface.getLibraryFile() + "' as database.");
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
        communicationBox.setText("The details of the origin of life are unknown, but the basic principles have been established. There are basically two schools of thought which are further divided into many about the origin of life. One suggests that organic components arrived on Earth from space, while the other argues that they originated on Earth.     10. Panspermia  Image Source Panspermia is the hypothesis that life exists throughout the Universe, distributed by meteoroids, asteroids and planetoids. Panspermia proposes that life that can survive the effects of space, such as extremophile bacteria, become trapped in debris that is ejected into space after collisions between planets that harbor life and Small Solar System Bodies (SSSB). Bacteria may travel dormant for an extended amount of time before colliding randomly with other planets or intermingling with protoplanetary disks. If met with ideal conditions on a new planets’ surfaces, the bacteria become active and the process of evolution begins.  Recent probes inside comets show it is overwhelmingly likely that life began in space, according to a new paper by Cardiff University scientists.     9. Biopoesis   Image Source  In natural science, abiogenesis or biopoesis is the study of how biological life arises from inorganic matter through natural processes, and the method by which life on Earth arose. Most amino acids, often called “the building blocks of life”, can form via natural chemical reactions unrelated to life, as demonstrated in the Miller–Urey experiment and similar experiments that involved simulating some of the conditions of the early Earth in a laboratory. In all living things, these amino acids are organized into proteins, and the construction of these proteins is mediated by nucleic acids, that are themselves synthesized through biochemical pathways catalysed by proteins. Which of these organic molecules first arose and how they formed the first life is the focus of abiogenesis.     8. Cosmogeny  Image Source Cosmogeny, is any theory concerning the coming into existence or origin of the universe, or about how reality came to be. In the specialized context of space science and astronomy, the term refers to theories of creation of (and study of) the Solar System. Attempts to create a naturalistic cosmogony are subject to two separate limitations. One is based in the philosophy of science and the epistemological constraints of science itself, especially with regards to whether scientific inquiry can ask questions of “why” the universe exists. Another more pragmatic problem is that there is no physical model that can explain the earliest moments of the universe’s existence  because of a lack of a testable theory of quantum gravity, although string theorists and researchers in loop quantum cosmology believe they have the formulas to describe it within their field equations.     7. Endosymbiosis  Image Source The endosymbiotic theory was first articulated by the Russian botanist Konstantin Mereschkowski in 1905. According to this theory, certain organelles originated as free-living bacteria that were taken inside another cell as endosymbionts. Mitochondria developed from proteobacteria (in particular, Rickettsiales or close relatives) and chloroplasts from cyanobacteria. It suggests that multiple forms of bacteria entered into symbiotic relationship to form the eukaryotic cell. The horizontal transfer of genetic material between bacteria promotes such symbiotic relationships, and thus many separate organisms may have contributed to building what has been recognised as the Last Universal Common Ancestor (LUCA) of modern organisms.     6. Spontaneous Generation  Image Source Until the early 19th century, people generally believed in the ongoing spontaneous generation of certain forms of life from non-living matter. This was paired with the belief in heterogenesis, e.g. that one form of life derived from a different form (e.g. bees from flowers). Classical notions of spontaneous generation, held that certain complex, living organisms are generated by decaying organic substances. According to Aristotle it was a readily observable truth that aphids arise from the dew which falls on plants, flies from putrid matter, mice from dirty hay, crocodiles from rotting logs at the bottom of bodies of water, and so on. Spontaneous generation or Equivocal generation is considered obsolete by many, regarding the origin of life from inanimate matter, which held that this process was a commonplace and everyday occurrence, as distinguished from univocal generation, or reproduction from parent(s). The theory was synthesized by Aristotle, who compiled and expanded the work of prior natural philosophers and the various ancient explanations of the appearance of organisms; it held sway for two millennia. It is generally accepted to have been ultimately disproven in the 19th Century by the experiments of Louis Pasteur. The disproof of ongoing spontaneous generation is no longer controversial, now that the life cycles of various life forms have been well documented. However, the question of biopoesis or abiogenesis, how living things originally arose from non-living material, remains relevant today     5. Clay Theory  Image Source A model for the origin of life based on clay was forwarded by A. Graham Cairns-Smith of the University of Glasgow in 1985 and explored as a plausible illustration by several other scientists, including Richard Dawkins. Clay theory postulates that complex organic molecules arose gradually on a pre-existing, non-organic replication platform—silicate crystals in solution. Complexity in companion molecules developed as a function of selection pressures on types of clay crystal is then exapted to serve the replication of organic molecules independently of their silicate “launch stage”.     4. Theory of Consecutive Creations  Image Source The idea of extinction paved the way for the theory of catastrophism or “consecutive creations”, one of the predecessors of the evolution theory. Catastrophism is the idea that Earth has been affected in the past by sudden, short-lived, violent events, possibly worldwide in scope. This view holds that the present is the key to the past, and that all things continue as they were from the beginning of the world. According to this theory, since each catastrophe completely destroyed the existing life, each new creation consisted of life form different from that of previous ones. French scientists Georges Cuvier (1769-1832) and Orbigney (1802 to 1837) were the main supporters of this theory.     3. Materialistic Theory  Image Source According this theory, the origin of life on earth is the result of a slow and gradual process of chemical evolution that probably occurred about 3.8 billion years ago. Chemical evolution refers to molecular evolution is the process of evolution at the scale of DNA, RNA, and proteins. Molecular evolution emerged as a scientific field in the 1960s as researchers from molecular biology, evolutionary biology and population genetics sought to understand recent discoveries on the structure and function of nucleic acids and protein. Some of the key topics that spurred development of the field have been the evolution of enzyme function, the use of nucleic acid divergence as a “molecular clock” to study species divergence, and the origin of noncoding DNA.     2. Organic Evolution  Image Source Speciation stretches back over 3.5 billion years during which life has existed on earth. It is thought to occur in multiple ways such as slowly, steadily and gradually over time or rapidly from one long static state to another. Evolution (also known as biological or organic evolution) is the change over time in one or more inherited traits found in populations of organisms. Inherited traits are particular distinguishing characteristics, including anatomical, biochemical or behavioural characteristics, that are passed on from one generation to the next. Evolution has led to the diversification of all living organisms, which are described by Charles Darwin as “endless forms most beautiful and most wonderful”.     1.  Theory of Special Creation  Image Source According to this theory, all the different forms of life that occur today on planet earth, have been created by God, the almighty. Adam and Eve were, according to the Book of Genesis, Bible and Quran the first man and woman created by the God. Life on earth began from them according to Christians, Muslims and Jews. The 3 religions have a common agreement on the fact God created the universe in seven days, reserving for his sixth-day labor the climax of creation: man and woman. On the seventh day God rests and so establishes the holiness of the Sabbath. God fashioned a man fom the dust and blows the breath of life into his nostrils, then planted a garden (the Garden of Eden) and caused to grow in the middle of the garden the Tree of Knowledge of Good and Evil and the Tree of Life. God set the man in the garden “to work it and watch over it,” permitting him to eat from all the trees in the garden except the Tree of Knowledge, “for on the day you eat of it you shall surely die.” God brought the animals to the man for him to name. None of them were found to be a suitable companion for the man, so God caused the man to sleep and created a woman from a part of his body (Tradition describes the part as a rib). The Quran says that Adam initiated the fruit eating and that both Adam and Eve (Hawa) ate the forbidden fruit, for which God later forgave them, and then sent both of them down to earth as his representatives. The Hadith (the prophetic narrations) and literature shed light on the Muslim view of the first couple. The concept of original sin does not exist in Islam, as Adam and Eve were forgiven after they repented on Earth, according to the Quran. One of the differences between the Qur’an and the book of Genesis is that it does not recount the Genesis narrative in which Eve leads Adam to transgress God’s laws; they are simply both held responsible and thus sent to earth. In the beginning when God created the heavens and the earth,  the earth was a formless void and darkness covered the face of the deep, while a wind from God swept over the face of the waters.  Then God said, \"Let there be light\"; and there was light.  And God saw that the light was good; and God separated the light from the darkness.  God called the light Day, and the darkness he called Night. And there was evening and there was morning, the first day.  And God said, \"Let there be a dome in the midst of the waters, and let it separate the waters from the waters.\"  So God made the dome and separated the waters that were under the dome from the waters that were above the dome. And it was so.  God called the dome Sky. And there was evening and there was morning, the second day.  And God said, \"Let the waters under the sky be gathered together into one place, and let the dry land appear.\" And it was so.  God called the dry land Earth, and the waters that were gathered together he called Seas. And God saw that it was good.  Then God said, \"Let the earth put forth vegetation plants yielding seed, and fruit trees of every kind on earth that bear fruit with the seed in it.\" And it was so.  The earth brought forth vegetation plants yielding seed of every kind, and trees of every kind bearing fruit with the seed in it. And God saw that it was good.  And there was evening and there was morning, the third day.  And God said, \"Let there be lights in the dome of the sky to separate the day from the night; and let them be for signs and for seasons and for days and years,  and let them be lights in the dome of the sky to give light upon the earth.\" And it was so.  God made the two great lights - the greater light to rule the day and the lesser light to rule the night - and the stars.  God set them in the dome of the sky to give light upon the earth,  to rule over the day and over the night, and to separate the light from the darkness. And God saw that it was good.  And there was evening and there was morning, the fourth day.  And God said, \"Let the waters bring forth swarms of living creatures, and let birds fly above the earth across the dome of the sky.\"  So God created the great sea monsters and every living creature that moves, of every kind, with which the waters swarm, and every winged bird of every kind. And God saw that it was good.  God blessed them, saying, \"Be fruitful and multiply and fill the waters in the seas, and let birds multiply on the earth.\"  And there was evening and there was morning, the fifth day.  And God said, \"Let the earth bring forth living creatures of every kind cattle and creeping things and wild animals of the earth of every kind.\" And it was so.  God made the wild animals of the earth of every kind, and the cattle of every kind, and everything that creeps upon the ground of every kind. And God saw that it was good.  Then God said, \"Let us make humankind in our image, according to our likeness; and let them have dominion over the fish of the sea, and over the birds of the air, and over the cattle, and over all the wild animals of the earth, and over every creeping thing that creeps upon the earth.\"  So God created humankind in his image, in the image of God he created them; male and female he created them.  God blessed them, and God said to them, \"Be fruitful and multiply, and fill the earth and subdue it; and have dominion over the fish of the sea and over the birds of the air and over every living thing that moves upon the earth.\"  God said, \"See, I have given you every plant yielding seed that is upon the face of all the earth, and every tree with seed in its fruit; you shall have them for food.  And to every beast of the earth, and to every bird of the air, and to everything that creeps on the earth, everything that has the breath of life, I have given every green plant for food.\" And it was so.  God saw everything that he had made, and indeed, it was very good. And there was evening and there was morning, the sixth day.      Thus the heavens and the earth were finished, and all their multitude.  And on the seventh day God finished the work that he had done, and he rested on the seventh day from all the work that he had done.  So God blessed the seventh day and hallowed it, because on it God rested from all the work that he had done in creation.  These are the generations of the heavens and the earth when they were created. In the day that the LORD God made the earth and the heavens,  when no plant of the field was yet in the earth and no herb of the field had yet sprung up - for the LORD God had not caused it to rain upon the earth, and there was no one to till the ground;  but a stream would rise from the earth, and water the whole face of the ground -  then the LORD God formed man from the dust of the ground, and breathed into his nostrils the breath of life; and the man became a living being.  And the LORD God planted a garden in Eden, in the east; and there he put the man whom he had formed.  Out of the ground the LORD God made to grow every tree that is pleasant to the sight and good for food, the tree of life also in the midst of the garden, and the tree of the knowledge of good and evil.  A river flows out of Eden to water the garden, and from there it divides and becomes four branches.  The name of the first is Pishon; it is the one that flows around the whole land of Havilah, where there is gold;  and the gold of that land is good; bdellium and onyx stone are there.  The name of the second river is Gihon; it is the one that flows around the whole land of Cush.  The name of the third river is Tigris, which flows east of Assyria. And the fourth river is the Euphrates.  The LORD God took the man and put him in the garden of Eden to till it and keep it.  And the LORD God commanded the man, \"You may freely eat of every tree of the garden;  but of the tree of the knowledge of good and evil you shall not eat, for in the day that you eat of it you shall die.\"  Then the LORD God said, \"It is not good that the man should be alone; I will make him a helper as his partner.\"  So out of the ground the LORD God formed every animal of the field and every bird of the air, and brought them to the man to see what he would call them; and whatever the man called every living creature, that was its name.  The man gave names to all cattle, and to the birds of the air, and to every animal of the field; but for the man there was not found a helper as his partner.  So the LORD God caused a deep sleep to fall upon the man, and he slept; then he took one of his ribs and closed up its place with flesh.  And the rib that the LORD God had taken from the man he made into a woman and brought her to the man.  Then the man said, \"This at last is bone of my bones and flesh of my flesh; this one shall be called Woman, for out of Man this one was taken.\"  Therefore a man leaves his father and his mother and clings to his wife, and they become one flesh.  And the man and his wife were both naked, and were not ashamed.      Now the serpent was more crafty than any other wild animal that the LORD God had made. He said to the woman, \"Did God say, 'You shall not eat from any tree in the garden'?\"  The woman said to the serpent, \"We may eat of the fruit of the trees in the garden;  but God said, 'You shall not eat of the fruit of the tree that is in the middle of the garden, nor shall you touch it, or you shall die. '\"  But the serpent said to the woman, \"You will not die;  for God knows that when you eat of it your eyes will be opened, and you will be like God, knowing good and evil.\"  So when the woman saw that the tree was good for food, and that it was a delight to the eyes, and that the tree was to be desired to make one wise, she took of its fruit and ate; and she also gave some to her husband, who was with her, and he ate.  Then the eyes of both were opened, and they knew that they were naked; and they sewed fig leaves together and made loincloths for themselves.  They heard the sound of the LORD God walking in the garden at the time of the evening breeze, and the man and his wife hid themselves from the presence of the LORD God among the trees of the garden.  But the LORD God called to the man, and said to him, \"Where are you?\"  He said, \"I heard the sound of you in the garden, and I was afraid, because I was naked; and I hid myself.\"  He said, \"Who told you that you were naked? Have you eaten from the tree of which I commanded you not to eat?\"  The man said, \"The woman whom you gave to be with me, she gave me fruit from the tree, and I ate.\"  Then the LORD God said to the woman, \"What is this that you have done?\" The woman said, \"The serpent tricked me, and I ate.\"  The LORD God said to the serpent, \"Because you have done this, cursed are you among all animals and among all wild creatures; upon your belly you shall go, and dust you shall eat all the days of your life.  I will put enmity between you and the woman, and between your offspring and hers; he will strike your head, and you will strike his heel.\"  To the woman he said, \"I will greatly increase your pangs in childbearing; in pain you shall bring forth children, yet your desire shall be for your husband, and he shall rule over you.\"  And to the man he said, \"Because you have listened to the voice of your wife, and have eaten of the tree about which I commanded you, 'You shall not eat of it,' cursed is the ground because of you; in toil you shall eat of it all the days of your life;  thorns and thistles it shall bring forth for you; and you shall eat the plants of the field.  By the sweat of your face you shall eat bread until you return to the ground, for out of it you were taken; you are dust, and to dust you shall return.\"  The man named his wife Eve, because she was the mother of all living.  And the LORD God made garments of skins for the man and for his wife, and clothed them.  Then the LORD God said, \"See, the man has become like one of us, knowing good and evil; and now, he might reach out his hand and take also from the tree of life, and eat, and live forever\"--  therefore the LORD God sent him forth from the garden of Eden, to till the ground from which he was taken.  He drove out the man; and at the east of the garden of Eden he placed the cherubim, and a sword flaming and turning to guard the way to the tree of life.      Now the man knew his wife Eve, and she conceived and bore Cain, saying, \"I have produced a man with the help of the LORD.\"  Next she bore his brother Abel. Now Abel was a keeper of sheep, and Cain a tiller of the ground.  In the course of time Cain brought to the LORD an offering of the fruit of the ground,  and Abel for his part brought of the firstlings of his flock, their fat portions. And the LORD had regard for Abel and his offering,  but for Cain and his offering he had no regard. So Cain was very angry, and his countenance fell.  The LORD said to Cain, \"Why are you angry, and why has your countenance fallen?  If you do well, will you not be accepted? And if you do not do well, sin is lurking at the door; its desire is for you, but you must master it.\"  Cain said to his brother Abel, \"Let us go out to the field.\" And when they were in the field, Cain rose up against his brother Abel, and killed him.  Then the LORD said to Cain, \"Where is your brother Abel?\" He said, \"I do not know; am I my brother's keeper?\"  And the LORD said, \"What have you done? Listen; your brother's blood is crying out to me from the ground!  And now you are cursed from the ground, which has opened its mouth to receive your brother's blood from your hand.  When you till the ground, it will no longer yield to you its strength; you will be a fugitive and a wanderer on the earth.\"  Cain said to the LORD, \"My punishment is greater than I can bear!  Today you have driven me away from the soil, and I shall be hidden from your face; I shall be a fugitive and a wanderer on the earth, and anyone who meets me may kill me.\"  Then the LORD said to him, \"Not so! Whoever kills Cain will suffer a sevenfold vengeance.\" And the LORD put a mark on Cain, so that no one who came upon him would kill him.  Then Cain went away from the presence of the LORD, and settled in the land of Nod, east of Eden.  Cain knew his wife, and she conceived and bore Enoch; and he built a city, and named it Enoch after his son Enoch.  To Enoch was born Irad; and Irad was the father of Mehujael, and Mehujael the father of Methushael, and Methushael the father of Lamech.  Lamech took two wives; the name of the one was Adah, and the name of the other Zillah.  Adah bore Jabal; he was the ancestor of those who live in tents and have livestock.  His brother's name was Jubal; he was the ancestor of all those who play the lyre and pipe.  Zillah bore Tubal-cain, who made all kinds of bronze and iron tools. The sister of Tubal-cain was Naamah.  Lamech said to his wives \"Adah and Zillah, hear my voice; you wives of Lamech, listen to what I say I have killed a man for wounding me, a young man for striking me.  If Cain is avenged sevenfold, truly Lamech seventy-sevenfold.\"  Adam knew his wife again, and she bore a son and named him Seth, for she said, \"God has appointed for me another child instead of Abel, because Cain killed him.\"  To Seth also a son was born, and he named him Enosh. At that time people began to invoke the name of the LORD.      This is the list of the descendants of Adam. When God created humankind, he made them in the likeness of God.  Male and female he created them, and he blessed them and named them \"Humankind\" when they were created.  When Adam had lived one hundred thirty years, he became the father of a son in his likeness, according to his image, and named him Seth.  The days of Adam after he became the father of Seth were eight hundred years; and he had other sons and daughters.  Thus all the days that Adam lived were nine hundred thirty years; and he died.  When Seth had lived one hundred five years, he became the father of Enosh.  Seth lived after the birth of Enosh eight hundred seven years, and had other sons and daughters.  Thus all the days of Seth were nine hundred twelve years; and he died.  When Enosh had lived ninety years, he became the father of Kenan.  Enosh lived after the birth of Kenan eight hundred fifteen years, and had other sons and daughters.  Thus all the days of Enosh were nine hundred five years; and he died.  When Kenan had lived seventy years, he became the father of Mahalalel.  Kenan lived after the birth of Mahalalel eight hundred and forty years, and had other sons and daughters.  Thus all the days of Kenan were nine hundred and ten years; and he died.  When Mahalalel had lived sixty-five years, he became the father of Jared.  Mahalalel lived after the birth of Jared eight hundred thirty years, and had other sons and daughters.  Thus all the days of Mahalalel were eight hundred ninety-five years; and he died.  When Jared had lived one hundred sixty-two years he became the father of Enoch.  Jared lived after the birth of Enoch eight hundred years, and had other sons and daughters.  Thus all the days of Jared were nine hundred sixty-two years; and he died.  When Enoch had lived sixty-five years, he became the father of Methuselah.  Enoch walked with God after the birth of Methuselah three hundred years, and had other sons and daughters.  Thus all the days of Enoch were three hundred sixty-five years.  Enoch walked with God; then he was no more, because God took him.  When Methuselah had lived one hundred eighty-seven years, he became the father of Lamech.  Methuselah lived after the birth of Lamech seven hundred eighty- two years, and had other sons and daughters.  Thus all the days of Methuselah were nine hundred sixty-nine years; and he died.  When Lamech had lived one hundred eighty-two years, he became the father of a son;  he named him Noah, saying, \"Out of the ground that the LORD has cursed this one shall bring us relief from our work and from the toil of our hands.\"  Lamech lived after the birth of Noah five hundred ninety-five years, and had other sons and daughters.  Thus all the days of Lamech were seven hundred seventy-seven years; and he died.  After Noah was five hundred years old, Noah became the father of Shem, Ham, and Japheth.      When people began to multiply on the face of the ground, and daughters were born to them,  the sons of God saw that they were fair; and they took wives for themselves of all that they chose.  Then the LORD said, \"My spirit shall not abide in mortals forever, for they are flesh; their days shall be one hundred twenty years.\"  The Nephilim were on the earth in those days - and also afterward - when the sons of God went in to the daughters of humans, who bore children to them. These were the heroes that were of old, warriors of renown.  The LORD saw that the wickedness of humankind was great in the earth, and that every inclination of the thoughts of their hearts was only evil continually.  And the LORD was sorry that he had made humankind on the earth, and it grieved him to his heart.  So the LORD said, \"I will blot out from the earth the human beings I have created - people together with animals and creeping things and birds of the air, for I am sorry that I have made them.\"  But Noah found favor in the sight of the LORD.  These are the descendants of Noah. Noah was a righteous man, blameless in his generation; Noah walked with God.  And Noah had three sons, Shem, Ham, and Japheth.  Now the earth was corrupt in God's sight, and the earth was filled with violence.  And God saw that the earth was corrupt; for all flesh had corrupted its ways upon the earth.  And God said to Noah, \"I have determined to make an end of all flesh, for the earth is filled with violence because of them; now I am going to destroy them along with the earth.  Make yourself an ark of cypress wood; make rooms in the ark, and cover it inside and out with pitch.  This is how you are to make it the length of the ark three hundred cubits, its width fifty cubits, and its height thirty cubits.  Make a roof for the ark, and finish it to a cubit above; and put the door of the ark in its side; make it with lower, second, and third decks.  For my part, I am going to bring a flood of waters on the earth, to destroy from under heaven all flesh in which is the breath of life; everything that is on the earth shall die.  But I will establish my covenant with you; and you shall come into the ark, you, your sons, your wife, and your sons' wives with you.  And of every living thing, of all flesh, you shall bring two of every kind into the ark, to keep them alive with you; they shall be male and female.  Of the birds according to their kinds, and of the animals according to their kinds, of every creeping thing of the ground according to its kind, two of every kind shall come in to you, to keep them alive.  Also take with you every kind of food that is eaten, and store it up; and it shall serve as food for you and for them.\"  Noah did this; he did all that God commanded him.");
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
                .addComponent(messages, javax.swing.GroupLayout.DEFAULT_SIZE, 746, Short.MAX_VALUE))
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
            Thread translatorThread = new Thread(translatorRunnable);
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
        if (stimulateToggle.isSelected()) {
            messages.setText("Building relations between words and affects in progress.");
            stimulateToggle.setText("Stimulation");
            Interface.setState("Stimulate");
            recording = true;
            Interface.setIsVisualising(visualiseToggle.isSelected());
            String already = Interface.getStimulatedAlready();
            Interface.setBufferedText(communicationBox.getText().replace(already.trim(), "").replace("\n", " ").replace("\r", " "));
            communicationBox.setText(Interface.getBufferedText());
            wNFactory.addTextToRepo(Interface.getBufferedText());
            StimulationRunnable stimulationRunnable = new StimulationRunnable(wNFactory, stimulateToggle, liveActThread, fft, liveToggleButton);
            Thread stimulationThread = new Thread(stimulationRunnable);
            stimulationThread.start();
        } else {
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
                messages.setText("OK! Saved.");
            } catch (Exception e) {
                messages.setText("Something went wrong!");
            }
        } else {
            messages.setBackground(Color.red);
            messages.setText("Repossitory is empty. Could not be saved.");
        }
    }//GEN-LAST:event_saveButtonActionPerformed

    private void loadButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_loadButtonActionPerformed
        wNFactory.loadRepo();
        if (translateToggle.isSelected()) {
            messages.setText("Please stop the translation process first!");
        }else if (wNFactory.getRitaRepo() == null) {
            messages.setBackground(Color.red);
            messages.setText("Repossitory is empty. Could not be loaded. Please just try again.");
        } else {
            messages.setText("Loaded successfully!");
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
}
