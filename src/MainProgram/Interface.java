package MainProgram;

import RiTa.RiTaFactory;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Interface {

    private static String affects = "";
    private static String words = "";
    private static String word = "";
    private static String state = "";
    private static boolean isSpeaking = false;
    private static boolean isListening = false;
    private static float volume = 0f;
    private static String libraryFile = "library.affect";
    private static String message = "";
    private static Boolean isVisualising = false;
    private static float[] affectsArray = null;
    private static String login;
    private static String password;
    private static final String url = "https://art-hub.pl/webservice/tal";
    private static String defaultString;
    private static String stimulatedAlready = "";
    private static final String generatedSentencesFilePath = "generated_sentences.txt";
    private static boolean visualisation;
    private static RiTaFactory ritaActions;

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

    public static String getWord() {
        return word;
    }

    public static void setWord(String word) {
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
        return defaultString;
    }

    public static String getDefaultString() {
        return defaultString.trim();
    }

    public static void setDefaultString(String aDefaultString) {
        defaultString = aDefaultString.trim();
    }

    public static String getStimulatedAlready() {
        return stimulatedAlready;
    }

    public static void setStimulatedAlready(String aStimulatedAlready) {
        stimulatedAlready = aStimulatedAlready;
    }

    static String getGeneratedSentencesFilePath() {
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

    public static void setRitaActions(RiTaFactory riact) {
        Interface.ritaActions = riact;
    }
    
    public static RiTaFactory getRitaActions() {
        return Interface.ritaActions;
    }
    
}
