package MainProgram;

import Persistence.Persistence;
import WNprocess.SomanticFactory;
import WNprocess.SomanticWord;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;
import javax.swing.JProgressBar;

public class Interface {

    private static String affects = "";
    private static String words = "";
    private static SomanticWord word = new SomanticWord();
    private static String state = "";
    private static boolean isSpeaking = false;
    private static boolean isListening = false;
    private static float volume = 0f;
    private static String libraryFile = "library.affect";
    private static String message = "";
    private static String sentences = "";
    private static Boolean isVisualising = false;
    private static float[] affectsArray = null;
    private static String login;
    private static String password;
    private static final String url = "https://art-hub.pl/webservice/tal";
    private static String bufferedText;
    private static String stimulatedAlready = "";
    private static final String generatedSentencesFilePath = "generated_sentences.txt";
    private static boolean visualisation;
    private static SomanticFactory ritaFactory;
    private static JProgressBar jProgressBar;
    private static Long minimalSimilarity = 0L;
    private static Set<SomanticWord> sentence = new HashSet<>();
    private static boolean saving;

    public static String getMessage() {
        return message;
    }

    public static void setMessage(String message) {
        Interface.message = message;
    }

    public static String getLibraryFile() {
        return libraryFile;
    }

    public static void setLibraryFile(String libraryFile) {
        Interface.libraryFile = libraryFile;
    }

    public static boolean isSpeaking() {
        return isSpeaking;
    }

    public static void setIsSpeaking(boolean isSpeaking) {
        Interface.isSpeaking = isSpeaking;
    }

    public static String getAffects() {
        return affects;
    }

    public static void setAffects(String affects) {
        Interface.affects = affects;
    }

    public static String getWords() {
        return words;
    }

    public static void setWords(String words) {
        Interface.words = words;
    }

    public static String getState() {
        return state;
    }

    public static void setState(String state) {
        Interface.state = state;
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
        return isVisualising;
    }

    public static void setIsVisualising(Boolean aIsVisualising) {
        isVisualising = aIsVisualising;
    }

    public static float[] getAffectsArray() {
        return affectsArray;
    }

    public static void setAffectsArray(float[] affectsArray) {
        Interface.affectsArray = affectsArray;
    }

    public static SomanticWord getWord() {
        return word;
    }

    public static void setWord(SomanticWord word) {
        Interface.word = word;
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

    static String getDefaultWords() {
        return bufferedText;
    }

    public static String getBufferedText() {
        return bufferedText.trim();
    }

    public static void setBufferedText(String aDefaultString) {
        bufferedText = aDefaultString.trim();
    }

    public static String getStimulatedAlready() {
        return stimulatedAlready;
    }

    public static void setStimulatedAlready(String aStimulatedAlready) {
        stimulatedAlready = aStimulatedAlready;
    }

    public static String getGeneratedSentencesFilePath() {
        return generatedSentencesFilePath;
    }

    public static String wordnetDir() {
        return "wordnet";
    }

    static void setVisualisation(boolean selected) {
        Interface.visualisation = selected;
    }

    public static boolean isVisualisation() {
        return visualisation;
    }

    public static void setRitaFactory(SomanticFactory riact) {
        if (ritaFactory == null) {
            Interface.ritaFactory = riact;
        }
    }

    public static SomanticFactory getRitaFactory() {
        return Interface.ritaFactory;
    }

    static void setProgressBar(JProgressBar jProgressBar) {
        Interface.jProgressBar = jProgressBar;
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

    static void setSentence(Set<SomanticWord> arranged) {
        sentence = arranged;
    }

    static Set<SomanticWord> getSentence() {
        return sentence;
    }

    static String getStringSentence() {
        return Interface.getSentence().stream().map(w -> {
            String r = "";
            if (!w.getWords().isEmpty() && w.getWords().iterator().hasNext()) {
                r = w.getWords().iterator().next();
            }
            return r;
        }).collect(Collectors.joining(" "));
    }

    static void addSentence(String words) {
        words = words.trim();
        words = words.substring(0, 1).toUpperCase() + words.substring(1);
        Interface.sentences += words + ". <br> ";
        Persistence.saveNewLineInFile(words.replace("  ", ", ") + ". ");
    }

    static String getSentences() {
        return Interface.sentences;
    }

    static boolean getSaving() {
        return Interface.saving;
    }

    static void setSaving(boolean b) {
        Interface.saving = b;
    }
}
