package WNprocess;

import edu.mit.jwi.*;
import edu.mit.jwi.item.IIndexWord;
import edu.mit.jwi.item.IWord;
import edu.mit.jwi.item.IWordID;
import edu.mit.jwi.item.POS;
import edu.mit.jwi.morph.WordnetStemmer;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author michal
 */
public class WordNetToolbox {

    static private Dictionary dictionary;
    private static WordnetStemmer stemmer;

    static public void init() {
        try {
            dictionary = new Dictionary(new URL("file", null, "wordnet" + File.separator + "dict"));
            dictionary.open();
            stemmer = new WordnetStemmer(dictionary);
        } catch (MalformedURLException ex) {
            Logger.getLogger(WordNetToolbox.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(WordNetToolbox.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    static public Dictionary getDictionary() {
        if (dictionary == null) {
            WordNetToolbox.init();
        }
        return dictionary;
    }

    static public WordnetStemmer getWordnetStemmer() {
        if (stemmer == null) {
            WordNetToolbox.init();
        }
        return stemmer;
    }

    static public List<String> stem(String word) {
        List<String> r = new ArrayList<>();
        for (POS pos : POS.values()) {
            List<String> a = getWordnetStemmer().findStems(word, pos);
            if (a != null && !a.isEmpty()) {
                r.addAll(a);
            }
        }
        return r;
    }

    static public Set<IWord> explain(String word) {
        Set<IWord> res = new HashSet<>();
        List<String> stems = stem(word);
        stems.stream().map((stem) -> id(stem)).forEachOrdered((ids) -> {
            ids.forEach((id) -> {
                res.add(getDictionary().getWord(id));
            });
        });
        return res;
    }

    static public List<IWordID> id(String word) {
        List<IWordID> r = new ArrayList<>();
        for (POS pos : POS.values()) {
            IIndexWord a = getDictionary().getIndexWord(word, pos);
            if (a != null) {
                r.addAll(a.getWordIDs());
            }
        }
        return r;
    }

    static public String[] tokenize(String textToSplit) {
        String[] words = textToSplit.split("[^\\w]");
        List<String> wordslist = new ArrayList<>();
        for (String word : words) {
            if (!word.isEmpty()) {
                wordslist.add(word.toLowerCase());
            }
        }
        return wordslist.toArray(new String[wordslist.size()]);
    }

    private static IWord bestOne(Map<Integer, IWord> orderpost) {
        IWord res = null;
        int recent = 0;
        for (Map.Entry<Integer, IWord> entry : orderpost.entrySet()) {
            Integer key = entry.getKey();
            IWord value = entry.getValue();
            if (key > recent) {
                res = value;
            }
        }
        return res;
    }

    static public IWord stringToIWord(String word) {
        return mostSimilar(explain(word), word);
    }

    static public IWord mostSimilar(Set<IWord> words, String word) {
        Map<Integer, IWord> orderpost = new HashMap<>();
        for (IWord next : words) {
            String lemma = next.getLemma();
            int degree = 0;
            for (int i = 0; i < lemma.length(); i++) {
                String seq = lemma.substring(0, i);
                if (word.contains(seq)) {
                    degree++;
                }
            }
            orderpost.put(degree, next);
        }
        return bestOne(orderpost);
    }

    static public List<Map<String, Set<IWord>>> analyse(String textToSplit) {
        String[] sentences = textToSplit.split(".");
        List<Map<String, Set<IWord>>> res = new ArrayList<>();
        Map<String, Set<IWord>> map = new HashMap<>();
        for (String sentence : sentences) {
            String[] parts = sentence.split(",");
            for (String part : parts) {
                String[] words = part.split("[^\\w]");
                for (String word : words) {
                    map.put(word, explain(word));
                    res.add(map);
                }
            }
        }
        return res;
    }

    static IWord getWordById(IWordID id) {
        return dictionary.getWord(id);
    }

}
