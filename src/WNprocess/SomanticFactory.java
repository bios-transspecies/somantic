package WNprocess;

import Persistence.Persistence;
import edu.mit.jwi.item.IWord;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;
import rita.RiTa;

public class SomanticFactory {

    private SomanticRepository repo;
    private SomanticAranger arranger;

    public SomanticFactory() {
        repo = SomanticRepository.getInstance();
    }

    public SomanticRepository getRitaRepo() {
        return repo;
    }

    public void makeWord() {

    }

    public SomanticWord getOrCreateWord(String word) {
        SomanticWord r = repo.get(word);
        if (r == null) {
            r = new SomanticWord();
            repo.put(word, r);
        }
        return r;
    }

    public void saveRepo() throws IOException, Exception {
        Persistence.save(repo);
    }

    public void loadRepo() throws IOException, FileNotFoundException, ClassNotFoundException {
        SomanticRepository r = Persistence.loadRepository();
        if (r != null) {
            repo = r;
        }
    }

    public void addTextToRepo(String textToSplit) {
        String[] tokenized = RiTa.tokenize(textToSplit);
        String[] tagged = RiTa.getPosTags(tokenized);
        SomanticSentence sentence = new SomanticSentence();
        SomanticWord previous = null;
        for (int i = 0; i < tokenized.length; i++) {
            IWord iWord = WordNetToolbox.stringToIWord(tokenized[i].trim().toLowerCase());
            // if string contains word... 
            if (iWord != null) {
                SomanticWord word = getOrCreateWord(iWord.getLemma());
                word.addTag(tagged[i]);
                word.addWord(tokenized[i]);
                word.addPOS(iWord.getPOS().name());
                word.setLemma(iWord.getLemma());
                word.setDescription(iWord.getSynset().getGloss());
                if (previous != null) {
                    previous.addNext(word);
                    word.addPrevious(previous);
                }
                previous = word;
                sentence.add(word);
            } else if (tokenized[i].contains(".") || tokenized[i].contains("?") || tokenized[i].contains("!") || tokenized[i].contains(";")) {
                for (SomanticWord somanticWord : sentence) {
                    somanticWord.addSentence(sentence);
                }
                sentence = new SomanticSentence();
            } else {
                //System.err.println("WORDNET CAN'T FIND WORD: " + tokenized[i].trim().toLowerCase());
                previous = null;
            }
        }
    }

    public Integer addAffectToWord(String word, List<Integer> affect) {
        SomanticAffect s = new SomanticAffect(affect);
        String w = WordNetToolbox.stem(word).get(0);
        SomanticWord riWord = repo.get(w);
        if (riWord != null) {
            riWord.addAffect(s);
            riWord.addWord(word);
            return riWord.hashCode();
        }
        return null;
    }

    public SomanticAranger getArranger() {
        if (this.arranger == null) {
            return new SomanticAranger(this);
        }
        return arranger;
    }
}
