package MainProgram;

import Persistence.Persistence;
import WNprocess.SomanticFacade;
import WNprocess.SomanticWord;
import WNprocess.neuralModel.NeuralNetworkTrainer;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;
import javax.swing.JProgressBar;
import somantic.controller.Controller;

public class Interface {

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
    private static Boolean isVisualising = false;
    private static float[] affectsArray = null;
    private static String login;
    private static String password;
    private static final String url = "https://art-hub.pl/webservice/tal";
    private static String bufferedText;
    private static final String generatedSentencesFilePath = "generated_sentences.txt";
    private static boolean visualisation;
    private static SomanticFacade somanticFacade = SomanticFacade.getInstance();
    private static JProgressBar jProgressBar;
    private static Long minimalSimilarity = 0L;
    private static Set<SomanticWord> sentence = new HashSet<>();
    private static boolean saving;
    private static String literatureLocation;
    private static NeuralNetworkTrainer neuralNetworkTrainer = new NeuralNetworkTrainer();
    private static Controller subscriber;

    public static String getMessage() {
        return message;
    }

    public static void setMessage(String message) {
        if(subscriber!=null)
            subscriber.setMessage(message);
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

    public static String getGeneratedSentencesFilePath() {
        return generatedSentencesFilePath;
    }

    public static String wordnetDir() {
        return "wordnet";
    }

    public static void setVisualisation(boolean selected) {
        Interface.visualisation = selected;
    }

    public static boolean isVisualisation() {
        return visualisation;
    }

    public static void setSomanticFactory(SomanticFacade riact) {
        if (somanticFacade == null) {
            Interface.somanticFacade = riact;
        }
    }

    public static SomanticFacade getSomanticFacade() {
        return Interface.somanticFacade;
    }

    public static void setProgressBar(JProgressBar jProgressBar) {
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
        Persistence.saveNewSentence(words.replace("  ", ", ") + ". ");
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

    public static String getLiteratureLocation() {
        return Interface.literatureLocation;
    }

    public static void setLiteratureLocation(String literatureLocation) {
        Interface.literatureLocation = literatureLocation;
    }

    public static NeuralNetworkTrainer getNeuralNetworkTrainer() {
        return neuralNetworkTrainer;
    }

    public static void subscribeMessages(Controller aThis) {
        subscriber = aThis;
    }

}
