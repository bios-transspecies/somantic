package WNprocess;

import edu.mit.jwi.item.IWord;
import java.util.ArrayList;
import java.util.List;
import rita.RiTa;

public class SomanticFactory {

    SomanticRepository repo = null;
    private SomanticAranger arranger;

    public SomanticFactory() {
        repo = SomanticRepository.getInstance();
    }

    public SomanticRepository getRitaRepo() {
        return repo;
    }

    public void makeWord() {

    }

    public SomanticWord getWord(String word) {
        SomanticWord r = repo.get(word);
        if (r == null) {
            r = new SomanticWord();
            repo.put(word, r);
        }
        return r;
    }

    public void saveRepo() {
        (new Thread(() -> Persistence.Persistence.save(repo))).run();
    }

    public void loadRepo() {
        (new Thread(() -> {
            SomanticRepository r = Persistence.Persistence.load();
            if (r != null) {
                repo = r;
            }
        })).run();
    }

    public void addTextToRepo(String textToSplit) {
        String[] tokenized = RiTa.tokenize(textToSplit);
        String[] tagged = RiTa.getPosTags(tokenized);
        List<SomanticWord> sentence = new ArrayList<>();
        SomanticWord previous = null;
        for (int i = 0; i < tokenized.length; i++) {
            IWord iWord = WordNetToolbox.stringToIWord(tokenized[i]);
            if (RiTa.containsWord(tokenized[i]) && iWord != null) {
                SomanticWord word = getWord(iWord.getLemma());
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
                sentence = new ArrayList<>();
            }
        }
    }

    public void addAffectToWord(String word, List<Integer> affect) {
        SomanticWord riWord = repo.get(WordNetToolbox.stem(word).get(0));
        if (riWord != null) {
            riWord.addAffect(affect);
        }
    }

    public SomanticAranger getArranger() {
        if (this.arranger == null) {
            return new SomanticAranger(this);
        }
        return arranger;
    }
}
