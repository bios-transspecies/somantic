package somantic.state;

import somantic.library.SomanticFacade;
import somantic.library.SomanticWord;
import java.util.Set;
import java.util.concurrent.ConcurrentSkipListSet;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.stream.Collectors;
import javax.swing.JLabel;
import javax.swing.JProgressBar;
import somantic.controller.Controller;
import somantic.neuralnetwork.NeuralNetworkTrainer;
import somantic.persistence.Persistence;

public class State {

    private static String affects = "";
    private static String words = "";
    private static SomanticWord word = new SomanticWord("");
    private static String state = "";
    private static boolean isSpeaking = false;
    private static boolean isListening = false;
    private static float volume = 0f;
    private static String libraryFile = "library.affect";
    private static String message = "";
    private static String sentences = "";
    private static AtomicBoolean isVisualising = new AtomicBoolean(false);
    private static float[] affectsArray = null;
    private static String login;
    private static String password;
    private static final String url = "https://art-hub.pl/webservice/tal";
    private static String bufferedText;
    private static final String generatedSentencesFilePath = "generated_sentences.txt";
    private static AtomicBoolean visualisation = new AtomicBoolean(false);
    private static SomanticFacade somanticFacade = SomanticFacade.getInstance();
    private static JProgressBar jProgressBar;
    private static Long minimalSimilarity = 0L;
    private static Set<SomanticWord> sentence = new ConcurrentSkipListSet<>();
    private static AtomicBoolean saving = new AtomicBoolean(false);
    private static String literatureLocation;
    private static NeuralNetworkTrainer neuralNetworkTrainer = new NeuralNetworkTrainer();
    private static Controller subscriber;
    private static JLabel nnStateLabel;
    private static AtomicBoolean liveactIsOn = new AtomicBoolean(false);

    public static String getMessage() {
        return message;
    }

    public static void setMessage(String message) {
        if (subscriber != null) {
            subscriber.setMessage(message);
        }
        State.message = message;
    }

    public static String getLibraryFile() {
        return libraryFile;
    }

    public static void setLibraryFile(String libraryFile) {
        State.libraryFile = libraryFile;
    }

    public static boolean isSpeaking() {
        return isSpeaking;
    }

    public static void setIsSpeaking(boolean isSpeaking) {
        State.isSpeaking = isSpeaking;
    }

    public static String getAffects() {
        return affects;
    }

    public static void setAffects(String affects) {
        State.affects = affects;
    }

    public static String getWords() {
        return words;
    }

    public static void setWords(String words) {
        State.words = words.toLowerCase();
    }

    public static String getState() {
        return state;
    }

    public static void setState(String state) {
        State.state = state;
    }

    public static boolean isListening() {
        return isListening;
    }

    public static void setIsListening(boolean aIsListening) {
        isListening = aIsListening;
    }

    public static float getVolume() {
        return volume;
    }

    public static void setVolume(float aVolume) {
        volume = aVolume;
    }

    public static Boolean getIsVisualising() {
        return isVisualising.get();
    }

    public static void setIsVisualising(Boolean aIsVisualising) {
        isVisualising.set(aIsVisualising);
    }

    public static float[] getAffectsArray() {
        return affectsArray;
    }

    public static void setAffectsArray(float[] affectsArray) {
        State.affectsArray = affectsArray;
    }

    public static SomanticWord getWord() {
        return word;
    }

    public static void setWord(SomanticWord word) {
        State.word = word;
    }

    public static String getLogin() {
        return login;
    }

    public static void setLogin(String aLogin) {
        login = aLogin;
    }

    public static String getPassword() {
        return password;
    }

    public static void setPassword(String aPassword) {
        password = aPassword;
    }

    public static String getUrl() {
        return url;
    }

    public static String getDefaultWords() {
        return bufferedText;
    }

    public static String getBufferedText() {
        return bufferedText.trim();
    }

    public static void setBufferedText(String aDefaultString) {
        bufferedText = aDefaultString.trim();
    }

    public static String getGeneratedSentencesFilePath() {
        return generatedSentencesFilePath;
    }

    public static String wordnetDir() {
        return "wordnet";
    }

    public static void setVisualisation(boolean selected) {
        State.visualisation.set(selected);
    }

    public static boolean isVisualisation() {
        return visualisation.get();
    }

    public static void setSomanticFactory(SomanticFacade riact) {
        if (somanticFacade == null) {
            State.somanticFacade = riact;
        }
    }

    public static SomanticFacade getSomanticFacade() {
        return State.somanticFacade;
    }

    public static void setProgressBar(JProgressBar jProgressBar) {
        State.jProgressBar = jProgressBar;
    }

    public static JProgressBar getProgressBar() {
        return jProgressBar;
    }

    public static Long getMinimalSimilarity() {
        return minimalSimilarity;
    }

    public static void setMinimalSimilarity(Long aMinimalSimilarity) {
        minimalSimilarity = aMinimalSimilarity;
    }

    public static void setSentence(Set<SomanticWord> arranged) {
        sentence = arranged;
    }

    static Set<SomanticWord> getSentence() {
        return sentence;
    }

    public static String getStringSentence() {
        return State.getSentence().stream().map(w -> {
            String r = "";
            if (!w.getWords().isEmpty() && w.getWords().iterator().hasNext()) {
                r = w.getWords().iterator().next();
            }
            return r;
        }).collect(Collectors.joining(" "));
    }

    public static void addSentence(String words) {
        words = words.trim();
        words = words.substring(0, 1).toUpperCase() + words.substring(1);
        State.sentences += words + ". <br> ";
        Persistence.saveNewSentence(words.replace("  ", ", ") + ". ");
    }

    public static String getSentences() {
        return State.sentences;
    }

    public static void resetSentences() {
        State.sentences = " ";
    }

    public static boolean getSaving() {
        return State.saving.get();
    }

    public static void setSaving(boolean b) {
        State.saving.set(b);
    }

    public static String getLiteratureLocation() {
        return State.literatureLocation;
    }

    public static void setLiteratureLocation(String literatureLocation) {
        State.literatureLocation = literatureLocation;
    }

    public static NeuralNetworkTrainer getNeuralNetworkTrainer() {
        return neuralNetworkTrainer;
    }

    public static void subscribeMessages(Controller aThis) {
        subscriber = aThis;
    }

    public static void setNnStateLabel(JLabel l) {
        nnStateLabel = l;
    }

    public static JLabel getNnStateLabel() {
        return nnStateLabel;
    }

    public static void setLiveactIsOn(boolean b) {
        liveactIsOn.set(b);
    }

    public static Boolean isLiveactOn() {
        return liveactIsOn.get();
    }

}
