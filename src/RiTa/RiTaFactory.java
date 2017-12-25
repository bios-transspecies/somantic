package RiTa;

import WordNet.RepositoryWordNet;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import rita.*;

public class RiTaFactory {

    RiTaWord previous = null;
    RiTaRepo repo = new RiTaRepo();
    RepositoryWordNet wordnetRepo = null;
    List<RiTaWord> sentence = null;
    List<RiTaWord> lastSentence = new ArrayList<>();
    private RiArange arranger;

    public RiTaFactory() {
        wordnetRepo = new RepositoryWordNet();
    }

    public RiTaRepo getRitaRepo(){
        return repo;
    }
    
    public void loadRepo(){
        RiTaRepo tmp = Persistence.Persistence.load();
        if(tmp!=null)
            repo = tmp;
    }
    
    public RiTaWord makeRiTaWord(String word) {
        if (RiTa.containsWord(word)) {
            RiTaWord res = repo.get(word);
            if (res != null) {
                res.addWord(word);
                return res;
            }
            word = word.trim();
            res = new RiTaWord();
            res.addWord(word);
            res.setLemma(RiTa.stem(word));
            if (RiTa.getPosTags(word).length > 0) {
                res.setSimpleTag(RiTa.getPosTags(word)[0]);
            }
            res.setPennTag((new RiString(word)).posAt(0));
            return res;
        }
        return null;
    }

    public void addToRepo(String textToUnderstand) {
        String[] words = RiTa.tokenize(textToUnderstand.toLowerCase());
        sentence = new ArrayList<>();
        for (String word : words) {
            if (RiTa.containsWord(word)) {
                RiTaWord ritaWord = makeRiTaWord(word);
                sentence.add(ritaWord);
                lastSentence.add(ritaWord);
                if (previous != null) { 
                    previous.addNext(ritaWord);
                    ritaWord.addPrevious(previous);
                }
                previous = ritaWord;
                if (!repo.containsValue(ritaWord)) {
                    repo.put(ritaWord.getLemma(), ritaWord);
                }
            } else if (word.contains(".")) {
                RiWoContext c = new RiWoContext();
                for (RiTaWord riTaWord : sentence) {
                    c.setPostPennTags(sentence.subList(sentence.indexOf(riTaWord) + 1, sentence.size()).stream().map(w -> w.getPennTag()).collect(Collectors.toList()));
                    c.setPostSimpleTags(sentence.subList(sentence.indexOf(riTaWord) + 1, sentence.size()).stream().map(w -> w.getSimpleTag()).collect(Collectors.toList()));
                    c.setPrePennTags(sentence.subList(0, sentence.indexOf(riTaWord)).stream().map(w -> w.getPennTag()).collect(Collectors.toList()));
                    c.setPreSimpleTags(sentence.subList(0, sentence.indexOf(riTaWord)).stream().map(w -> w.getSimpleTag()).collect(Collectors.toList()));
                    c.setWord(riTaWord);
                    Set<RiWoContext> cs = riTaWord.getContexts();
                    cs.add(c);
                    riTaWord.setContexts(cs);
                    riTaWord.addSentence(sentence);
                }
                sentence = new ArrayList<RiTaWord>();
            }
        }
    }

    public List<RiTaWord> getListOfRiwords() {
        List<RiTaWord> s = lastSentence;
        lastSentence = new ArrayList<>();
        return s;
    }

    public List<String> getLines() {
        List<String> lines = new ArrayList<>();
        repo.forEach((lemma, word) -> lines.add(lemma + ":" + word.getAffects()));
        return lines;
    }

    public void save(String word, String affect) {
        repo.get(word).addAffect(affect);
        Persistence.Persistence.save(repo);
    }

    public RiArange getArranger() {
        if(this.arranger == null)
            return new RiArange(this);
        return arranger;
    }
}
