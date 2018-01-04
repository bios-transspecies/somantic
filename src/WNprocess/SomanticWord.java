package WNprocess;
import java.io.Serializable;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

public class SomanticWord implements Serializable {

    private String lemma = "";
    private Set<String> POS= new HashSet<>();
    private Set<String> words = new HashSet<>();
    private Set<SomanticWord> previous = new HashSet<>();
    private Set<SomanticWord> next = new HashSet<>();
    private Set<List<SomanticWord>> sentences = new HashSet<>();
    private Set<List<Integer>> affects = new HashSet<>();
    private String description;
    private Set<String> tags = new HashSet<>();

    public SomanticWord() {
    }
    
    public Set<String> getPOS() {
        return POS;
    }

    public void setPOS(Set<String> POS) {
        this.POS = POS;
    }

    public void addPOS(String POS){
        this.POS.add(POS);
    }
    
    public String getLemma() {
        return lemma;
    }

    public void setLemma(String lemma) {
        this.lemma = lemma;
    }

    @Override
    public String toString() {
        return lemma;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 89 * hash + Objects.hashCode(this.lemma);
        return hash;
    }
    
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final SomanticWord other = (SomanticWord) obj;
        if (!Objects.equals(this.lemma, other.lemma)) {
            return false;
        }
        return true;
    }

    public Set<List<Integer>> getAffects() {
        return affects;
    }

    public void setAffects(Set<List<Integer>> affects) {
        this.affects = affects;
    }

    void addAffect(List<Integer> affect) {
        this.affects.add(affect);
    }

    void setDescription(String description) {
        this.description = description;
    }
    String getDescription() {
        return description;
    }

    public Set<String> getWords() {
        return words;
    }

    public void setWords(Set<String> words) {
        this.words = words;
    }

    public void addWord(String word) {
        this.words.add(word);
    }

    public Set<SomanticWord> getPrevious() {
        return previous;
    }

    public void setPrevious(Set<SomanticWord> previous) {
        this.previous = previous;
    }

    public void addPrevious(SomanticWord previous) {
        this.previous.add(previous);
    }

    public Set<SomanticWord> getNext() {
        return next;
    }

    public void setNext(Set<SomanticWord> next) {
        this.next = next;
    }
    
    public void addNext(SomanticWord next) {
        this.next.add(next);
    }

    public Set<List<SomanticWord>> getSentences() {
        return sentences;
    }

    public void setSentences(Set<List<SomanticWord>> sentence) {
        this.sentences = sentence;
    }

    public void addSentence(List<SomanticWord> sentence) {
        this.sentences.add(sentence);
    }

    void addTag(String tag) {
        this.tags.add(tag);
    }
    
    Set<String> getTags() {
    return tags;
    }
    
    void setTags(Set<String> tags) {
    this.tags = tags;
    }
}
