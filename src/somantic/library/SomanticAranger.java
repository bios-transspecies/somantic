package somantic.library;

import somantic.library.sentences.SentenceMapper;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class SomanticAranger {

    List<SomanticWord> riSentence;
    SomanticFacade ritaFactory;

    public SomanticAranger(SomanticFacade riact) {
        this.ritaFactory = riact;
    }

    private SomanticAranger setSentence(String sentence) {
        riSentence = new ArrayList<>();
        sentence = sentence.trim();
        String[] words = sentence.split(" ");
        for (String word : words) {
            SomanticWord x = ritaFactory.getOrCreateWord(word, null);
            if (x != null) {
                riSentence.add(x);
            }
        }
        return this;
    }

    public Set<SomanticWord> rewrite(String sentence) {
        setSentence(sentence);
        if (riSentence.size() < 3) {
            return new HashSet<SomanticWord>(riSentence);
        }
        String reString = "";
        // generating various sentences by setting words in place co-related to other words
        Set<SomanticWord> tmpReSentence = new HashSet<>();
        List<Set<SomanticWord>> tmpReSentences = new ArrayList<>();
        for (int wariant = 0; wariant < riSentence.size(); wariant++) {
            wariantsProcessor(wariant, tmpReSentence);
            Boolean add = true;
            add = mostComplexSentence(tmpReSentences, tmpReSentence, add);
            if (add) {
                tmpReSentences.add(tmpReSentence);
            }
        }
        Set<SomanticWord> r = tmpReSentences.get(tmpReSentences.size() - 1);
        try {
            return knownGrammarStructures(tmpReSentences.get(tmpReSentences.size() - 1));
        } catch (Exception e) {
            return r;
        }
    }

    private Boolean mostComplexSentence(List<Set<SomanticWord>> tmpReSentences, Set<SomanticWord> tmpReSentence, Boolean add) {
        if (!tmpReSentences.contains(tmpReSentence)) {
            for (Set<SomanticWord> tmpReSentence1 : tmpReSentences) {
                if (add) {
                    add = tmpReSentence.size() > tmpReSentence1.size() && tmpReSentence.size() < 3;
                }
            }
        }
        return add;
    }

    private void wariantsProcessor(int wariant, Set<SomanticWord> tmpReSentence) {
        for (int i = wariant; i < riSentence.size() + wariant; i++) {
            int index = i % riSentence.size();
            tmpReSentence.add(riSentence.get(index));
            for (int j = 0; j < riSentence.size(); j++) {
                if (i != j && riSentence.get(j) != null) {
                    Set<SomanticWord> next = riSentence.get(index).getNext();
                    processSomanticWords(next, index, tmpReSentence, j);
                }
            }
        }
    }

    private void processSomanticWords(Set<SomanticWord> next, int index, Set<SomanticWord> tmpReSentence, int j) {
        for (SomanticWord nextRiTaWord : next) {
            if ((nextRiTaWord.getPOS() == null ? riSentence.get(index).getPOS() == null : nextRiTaWord.getPOS().equals(riSentence.get(index).getPOS())) && !tmpReSentence.contains(riSentence.get(j))) {
                SomanticWord slowo = riSentence.get(j);
                if (slowo != null && !tmpReSentence.contains(slowo)) {
                    tmpReSentence.add(riSentence.get(j));
                }
            }
        }
    }

    private Set<SomanticWord> knownGrammarStructures(Set<SomanticWord> somanticSentence) {
        List<SentenceMapper> sentencesMapped = new ArrayList<>();
        // words 
        for (SomanticWord somanticWord : somanticSentence) {
            Set<SomanticSentence> contextSentences = somanticWord.getSentences();
            // context sentences make new variant
            contextSentences.stream().map((List<SomanticWord> contextSentence) -> {
                int counter = 0;
                Set<SomanticWord> variantSentence = new HashSet<>();
                SentenceMapper sentenceMapper = new SentenceMapper();
                // context words
                for (SomanticWord contextWord : contextSentence) {
                    Set<String> contextPOSes = contextWord.getPOS();
                    // context words POSes
                    for (String contextPOS : contextPOSes) {
                        // checksomanticWord
                        if (somanticWord.getPOS().contains(contextPOS)) {
                            if (somanticSentence.contains(contextWord)) {
                                counter++;
                            } else {
                                counter--;
                            }
                            variantSentence.add(contextWord);
                        }
                    }
                }
                sentenceMapper.setCounter(counter);
                sentenceMapper.setSentence(variantSentence);
                return sentenceMapper;
            }).forEachOrdered((sentenceMapper) -> {
                sentencesMapped.add(sentenceMapper);
            });
        }
        Collections.sort(sentencesMapped);
        for (SentenceMapper sentenceMapped : sentencesMapped) {
            if (sentenceMapped.getCounter() > -5 && sentenceMapped.getSentence().size() > 3 && Math.abs(sentenceMapped.getSentence().size() - somanticSentence.size()) < somanticSentence.size() / 5) {
                return sentenceMapped.getSentence();
            }
        }
        return somanticSentence;
    }
}
