/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package WNprocess;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
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
                            if ((nextRiTaWord.getPOS()== null ? riSentence.get(index).getPOS() == null : nextRiTaWord.getPOS().equals(riSentence.get(index).getPOS())) && !tmpReSentence.contains(riSentence.get(j))) {
                                SomanticWord slowo = riSentence.get(j);
                                if (slowo != null && !tmpReSentence.contains(slowo)) {
                                    tmpReSentence.add(riSentence.get(j));
                                }
                            }
                        }
                    }
                }
            }
            
//            List<RiTaWord> sentenceContextualised = new ArrayList<>();
//            for (RiTaWord riTaWord : tmpReSentence) {
//                Set<RiWoContext> contexts = riTaWord.getContexts();
//                for (RiWoContext context : contexts) {
//                    List<String> tags = context.getPostSimpleTags();
//                    if (sentenceContextualised.isEmpty()) {
//                        for (String tag : tags) {
//                            for (RiTaWord riTaWordContextualised : tmpReSentence) {
//                                if (riTaWordContextualised.getPennTag() == tag) {
//                                    System.err.println(riTaWordContextualised);
//                                    sentenceContextualised.add(riTaWordContextualised);
//                                }
//                            }
//                        }
//                    }
//                    if (tags.size() > 3 && tags.size() - sentenceContextualised.size() < 5) {
//                        String res = sentenceContextualised.stream().map(w->w.getLemma()).collect(Collectors.joining(" "));
//                        System.err.println("-------------------------------------------------------------------");
//                        System.err.println("---"+ res +"---");
//                        System.err.println("-------------------------------------------------------------------");
//                        return res;
//                    }
//                }
//            }
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
        return reString;
    }
}
