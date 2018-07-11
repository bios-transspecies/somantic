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

    synchronized public static String getMessage() {
        return message;
    }

    synchronized public static void setMessage(String message) {
        Interface.message = message;
    }

    synchronized public static String getLibraryFile() {
        return libraryFile;
    }

    synchronized public static void setLibraryFile(String libraryFile) {
        Interface.libraryFile = libraryFile;
    }

    synchronized public static boolean isSpeaking() {
        return isSpeaking;
    }

    synchronized public static void setIsSpeaking(boolean isSpeaking) {
        Interface.isSpeaking = isSpeaking;
    }

    synchronized public static String getAffects() {
        return affects;
    }

    synchronized public static void setAffects(String affects) {
        Interface.affects = affects;
    }

    synchronized public static String getWords() {
        return words;
    }

    synchronized public static void setWords(String words) {
        Interface.words = words;
    }

    synchronized public static String getState() {
        return state;
    }

    synchronized public static void setState(String state) {
        Interface.state = state;
    }

    synchronized public static boolean isListening() {
        return isListening;
    }

    synchronized public static void setIsListening(boolean aIsListening) {
        isListening = aIsListening;
    }

    synchronized public static float getVolume() {
        return volume;
    }

    synchronized public static void setVolume(float aVolume) {
        volume = aVolume;
    }

    synchronized public static Boolean getIsVisualising() {
        return isVisualising;
    }

    synchronized public static void setIsVisualising(Boolean aIsVisualising) {
        isVisualising = aIsVisualising;
    }

    synchronized public static float[] getAffectsArray() {
        return affectsArray;
    }

    synchronized public static void setAffectsArray(float[] affectsArray) {
        Interface.affectsArray = affectsArray;
    }

    synchronized public static SomanticWord getWord() {
        return word;
    }

    synchronized public static void setWord(SomanticWord word) {
        Interface.word = word;
    }

    synchronized public static String getLogin() {
        return login;
    }

    synchronized public static void setLogin(String aLogin) {
        login = aLogin;
    }

    synchronized public static String getPassword() {
        return password;
    }

    synchronized public static void setPassword(String aPassword) {
        password = aPassword;
    }

    synchronized public static String getUrl() {
        return url;
    }

    synchronized static String getDefaultWords() {
        return bufferedText;
    }

    synchronized public static String getBufferedText() {
        return bufferedText.trim();
    }

    synchronized public static void setBufferedText(String aDefaultString) {
        bufferedText = aDefaultString.trim();
    }

    synchronized public static String getStimulatedAlready() {
        return stimulatedAlready;
    }

    synchronized public static void setStimulatedAlready(String aStimulatedAlready) {
        stimulatedAlready = aStimulatedAlready;
    }

    synchronized public static String getGeneratedSentencesFilePath() {
        return generatedSentencesFilePath;
    }

    synchronized public static String wordnetDir() {
        return "wordnet";
    }

    synchronized static void setVisualisation(boolean selected) {
        Interface.visualisation = selected;
    }

    synchronized public static boolean isVisualisation() {
        return visualisation;
    }

    synchronized public static void setRitaFactory(SomanticFactory riact) {
        if (ritaFactory == null) {
            Interface.ritaFactory = riact;
        }
    }

    synchronized public static SomanticFactory getRitaFactory() {
        return Interface.ritaFactory;
    }

    synchronized static void setProgressBar(JProgressBar jProgressBar) {
        Interface.jProgressBar = jProgressBar;
    }

    synchronized public static JProgressBar getProgressBar() {
        return jProgressBar;
    }

    synchronized public static Long getMinimalSimilarity() {
        return minimalSimilarity;
    }

    synchronized public static void setMinimalSimilarity(Long aMinimalSimilarity) {
        minimalSimilarity = aMinimalSimilarity;
    }

    synchronized static void setSentence(Set<SomanticWord> arranged) {
        sentence = arranged;
    }

    synchronized static Set<SomanticWord> getSentence() {
        return sentence;
    }

    synchronized static String getStringSentence() {
        return Interface.getSentence().stream().map(w -> {
            String r = "";
            if (!w.getWords().isEmpty() && w.getWords().iterator().hasNext()) {
                r = w.getWords().iterator().next();
            }
            return r;
        }).collect(Collectors.joining(" "));
    }

    synchronized static void addSentence(String words) {
        words = words.trim();
        words = words.substring(0, 1).toUpperCase() + words.substring(1);
        Interface.sentences += words + ". <br> ";
        Persistence.saveNewLineInFile(words.replace("  ", ", ") + ". ");
    }

    static String getSentences() {
        return Interface.sentences;
    }
}
