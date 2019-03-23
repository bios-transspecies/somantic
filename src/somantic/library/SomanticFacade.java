package somantic.library;

import somantic.library.wordnet.WordNetToolbox;
import edu.mit.jwi.item.IWord;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;
import java.util.Optional;
import rita.RiTa;
import somantic.persistence.Persistence;

public class SomanticFacade {

    private SomanticRepository repo;
    private SomanticAranger arranger;
    private static SomanticFacade facade;

    private SomanticFacade() {
        repo = SomanticRepository.getInstance();
    }

    public static SomanticFacade getInstance() {
        if (facade == null) {
            facade = new SomanticFacade();
        }
        return facade;
    }

    public SomanticRepository getSomanticRepo() {
        return SomanticRepository.getInstance();
    }

    public SomanticWord getOrCreateWord(String word, IWord oprionalIword) {
        word = word.toLowerCase();
        SomanticWord sw = repo.get(word);
        if (sw == null) {
            IWord iWord = oprionalIword == null ? getIword(word) : oprionalIword;
            sw = new SomanticWord(word);
            sw.addWord(word);
            sw.addPOS(iWord.getPOS().name());
            sw.setLemma(iWord.getLemma());
            sw.setDescription(iWord.getSynset().getGloss());
            sw = repo.put(word, sw);
        }
        return sw;
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
        String[] words = RiTa.tokenize(textToSplit);
        String[] tagged = RiTa.getPosTags(words);
        SomanticSentence sentence = new SomanticSentence();
        SomanticWord previous = null;
        for (int i = 0; i < words.length; i++) {
            IWord iWord = getIword(words[i]);
            if (iWord != null) {
                SomanticWord word = getOrCreateWord(iWord.getLemma(), iWord);
                setContext(word, tagged, i, previous, sentence);
            } else if (words[i].contains(".") || words[i].contains("?") || words[i].contains("!") || words[i].contains(";")) {
                for (SomanticWord somanticWord : sentence) {
                    if(somanticWord!=null)
                    somanticWord.addSentence(sentence);
                }
                sentence = new SomanticSentence();
            } else {
                //System.err.println("WORDNET CAN'T FIND WORD: " + tokenized[i].trim().toLowerCase());
                previous = null;
            }
        }
    }

    private void setContext(SomanticWord word, String[] tagged, int i, SomanticWord previous, SomanticSentence sentence) {
        if (word != null) {
            word.addTag(tagged[i]);
            if (previous != null) {
                previous.addNext(word);
                word.addPrevious(previous);
            }
            word = repo.put(word.getLemma(), word);
            previous = word;
            sentence.add(word);
        }
    }

    private IWord getIword(String word) {
        IWord iWord = WordNetToolbox.stringToIWord(word.toLowerCase());
        return iWord;
    }

    public Integer addAffectToWord(String word, List<Integer> affect) {
        word = word.toLowerCase();
        SomanticAffect s = new SomanticAffect(affect);
        String w = WordNetToolbox.stem(word).get(0);
        SomanticWord riWord = repo.get(w);
        System.out.println(w);
        if (riWord != null) {
            riWord.addAffect(s);
            riWord.addWord(word);
            return riWord.getId();
        }
        return null;
    }

    public SomanticAranger getArranger() {
        if (this.arranger == null) {
            return new SomanticAranger(this);
        }
        return arranger;
    }

    public SomanticWord getWordById(Integer id) {
        if(id==null) return null;
        return repo.entrySet().stream()
                .map(s -> s.getValue())
                .filter(
                        (s) -> s.getId() == id
                        && s.getLemma().trim().length() > 0)
                .findFirst().orElseGet(()->null);
    }
}
