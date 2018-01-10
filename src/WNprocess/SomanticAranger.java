/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package WNprocess;

import WNprocess.Sentences.SentenceMapper;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

public class SomanticAranger {

    List<SomanticWord> riSentence;
    SomanticFactory ritaFactory;

    public SomanticAranger(SomanticFactory riact) {
        this.ritaFactory = riact;
    }

    private SomanticAranger setSentence(String sentence) {
        riSentence = new ArrayList<>();
        sentence = sentence.trim();
        String[] words = sentence.split(" ");
        for (String word : words) {
            SomanticWord x = ritaFactory.getWord(word);
            if (x != null) {
                riSentence.add(x);
            }
        }
        return this;
    }

    public String rewrite(String sentence) {
        setSentence(sentence);
        if (riSentence.size() < 3) {
            return sentence;
        }
        String reString = "";
        // generating various sentences by setting words in place co-related to other words
        Set<SomanticWord> tmpReSentence = new HashSet<>();
        List<Set<SomanticWord>> tmpReSentences = new ArrayList<>();
        for (int wariant = 0; wariant < riSentence.size(); wariant++) {
            for (int i = wariant; i < riSentence.size() + wariant; i++) {
                int index = i % riSentence.size();
                tmpReSentence.add(riSentence.get(index));
                for (int j = 0; j < riSentence.size(); j++) {
                    if (i != j && riSentence.get(j) != null) {
                        Set<SomanticWord> next = riSentence.get(index).getNext();
                        for (SomanticWord nextRiTaWord : next) {
                            if ((nextRiTaWord.getPOS() == null ? riSentence.get(index).getPOS() == null : nextRiTaWord.getPOS().equals(riSentence.get(index).getPOS())) && !tmpReSentence.contains(riSentence.get(j))) {
                                SomanticWord slowo = riSentence.get(j);
                                if (slowo != null && !tmpReSentence.contains(slowo)) {
                                    tmpReSentence.add(riSentence.get(j));
                                }
                            }
                        }
                    }
                }
            }

            // selecting most complex sentence
            Boolean add = true;
            if (!tmpReSentences.contains(tmpReSentence)) {
                for (Set<SomanticWord> tmpReSentence1 : tmpReSentences) {
                    if (add) {
                        add = tmpReSentence.size() > tmpReSentence1.size() && tmpReSentence.size() < 3;
                    }
                }
            }
            if (add) {
                tmpReSentences.add(tmpReSentence);
            }
        }

        if (!tmpReSentences.isEmpty()) {
            reString = tmpReSentences.get(tmpReSentences.size() - 1).stream().map(w -> w.getLemma()).collect(Collectors.joining(" "));
        } else {
            return sentence;
        }
        String r = "";
        try {
            r = makeSomeGrammar(tmpReSentences.get(tmpReSentences.size() - 1)).stream().map(w -> w.getWords().iterator().next()).collect(Collectors.joining(" "));
        } catch (Exception e) {
            r = sentence;
        }
        return r;
    }

    private Set<SomanticWord> makeSomeGrammar(Set<SomanticWord> sentence) {
        final ArrayList<Set<String>> sentencePOSes = sentence.stream().map(SomanticWord::getPOS).collect(Collectors.toCollection(ArrayList::new));
        List<SentenceMapper> sentencesMapped = new ArrayList<>();
        // words 
        for (SomanticWord somanticWord : sentence) {
            Set<List<SomanticWord>> contextSentences = somanticWord.getSentences();
            // context sentences make new variant
            contextSentences.stream().map((List<SomanticWord> contectSentence) -> {
                int counter = 0;
                Set<SomanticWord> variantSentence = new HashSet<>();
                SentenceMapper sentenceMapper = new SentenceMapper();
                // context words
                for (SomanticWord word : contectSentence) {
                    Set<String> contextPOSes = word.getPOS();
                    // context words POSes
                    for (String contextPOS : contextPOSes) {
                        // check
                        for (Set<String> wordPOSes : sentencePOSes) {
                            if (wordPOSes.contains(contextPOS)) {
                                counter++;
                                variantSentence.add(word);
                            }
                        }
                        sentenceMapper.setCounter(counter);
                        sentenceMapper.setSentence(variantSentence);
                    }
                }
                return sentenceMapper;
            }).forEachOrdered((sentenceMapper) -> {
                sentencesMapped.add(sentenceMapper);
            });

            Collections.sort(sentencesMapped);
            if (sentencesMapped.isEmpty()) {
                return sentence;
            }
            // TODO: test
            return sentencesMapped.get(0).getSentence();

        }
        return null;
    }

}
