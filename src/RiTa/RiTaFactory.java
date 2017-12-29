package RiTa;

import WordNet.RepositoryWordNet;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import rita.*;

public class RiTaFactory {

    RiTaWord previous = null;
    RiTaRepo repo = null;
    RepositoryWordNet wordnetRepo = null;
    List<RiTaWord> sentence = null;
    List<RiTaWord> lastSentence = new ArrayList<>();
    private RiArange arranger;

    public RiTaFactory() {
        wordnetRepo = new RepositoryWordNet();
    }

    public RiTaRepo getRitaRepo() {
        return repo;
    }

    public void saveRepo() {
        (new Thread(() -> Persistence.Persistence.save(repo))).run();
    }

    public void loadRepo() {
        (new Thread(() -> repo = Persistence.Persistence.load())).run();
    }

    public void loadRepoIfNotExists() {
        if (repo == null) {
            RiTaRepo tmp = Persistence.Persistence.load();
            if (tmp != null) {
                repo = tmp;
            } else {
                repo = new RiTaRepo();
            }
        }
    }

    public RiTaWord makeRiTaWord(String word) {
        RiTaWord res = repo.get(RiTa.stem(word));
        if (res != null) {
            res.addWord(word);
            return res;
        }
        if (RiTa.containsWord(word)) {
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

    public void addTextToRepo(String textToSplit) {
        String[] words = RiTa.tokenize(textToSplit.toLowerCase());
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
                    riTaWord.addContext(c);
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

    public void addAffectToWord(String word, List<Integer> affect) {
        RiTaWord riWord = repo.get(RiTa.stem(word));
        if (riWord != null) {
            riWord.addAffect(affect);
        }
    }

    public RiArange getArranger() {
        if (this.arranger == null) {
            return new RiArange(this);
        }
        return arranger;
    }
}
